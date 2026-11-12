#!/usr/bin/env python3
"""Measure installed-APK storage with isolated real-payload fixtures.

This is not a gameplay benchmark. Delta mode changes opaque encoded bytes and
must never be loaded as a game save. No APK install/build or player DB writes.
"""

import argparse
from datetime import datetime, timezone
import hashlib
import json
import os
from pathlib import Path
import re
import shlex
import sqlite3
import struct
import subprocess
import uuid

from android_build_tools import BUILD_TOOLS_VERSION, executable_name, jdk_tool
from run_game_save_probe import compiled_classes, checked, process
from verify_runtime_apk import dex_classes


ROOT = Path(__file__).resolve().parents[1]
PACKAGE = 'com.gameloft.android.ANMP.GloftGF2F'
RECORDS = ('RMSHeader', 'save_gameplay', 'save_farm0', 'pending_rewards_v1', 'entity_pose_v1')


def sha(data):
    return hashlib.sha256(data).hexdigest()


def capture(database, output):
    """Read a consistent local SQLite snapshot, not the emulator player DB."""
    connection = sqlite3.connect(database.resolve().as_uri() + '?mode=ro', uri=True)
    try:
        connection.execute('BEGIN')
        if connection.execute('PRAGMA integrity_check').fetchone()[0] != 'ok':
            raise ValueError('Local fixture database failed integrity_check')
        generation = connection.execute('SELECT generation FROM save_slots WHERE slot_id=?', ('device-local',)).fetchone()[0]
        blob = bytearray(struct.pack('>II', 0x47465031, len(RECORDS)))
        metadata = {'source_generation': generation, 'records': []}
        for name in RECORDS:
            row = connection.execute('SELECT payload,checksum,deleted,generation FROM save_records WHERE slot_id=? AND store_name=? AND record_id=1', ('device-local', name)).fetchone()
            if not row or row[2] or hashlib.sha256(row[0]).digest() != row[1]:
                raise ValueError('Missing or invalid source payload: ' + name)
            encoded = name.encode('ascii')
            blob.extend(struct.pack('>H', len(encoded)) + encoded + struct.pack('>I', len(row[0])) + row[0])
            metadata['records'].append({'name': name, 'bytes': len(row[0]), 'sha256': sha(row[0]), 'generation': row[3]})
        output.parent.mkdir(parents=True, exist_ok=True)
        output.write_bytes(blob)
        metadata['fixture_sha256'] = sha(blob)
        output.with_suffix('.json').write_text(json.dumps(metadata, indent=2) + '\n')
        return metadata
    finally:
        connection.close()


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--serial', default='emulator-5554')
    parser.add_argument('--sdk', type=Path, default=os.environ.get('ANDROID_HOME') or os.environ.get('ANDROID_SDK_ROOT'))
    parser.add_argument('--classes-root', type=Path)
    parser.add_argument('--fixture', type=Path, required=True)
    parser.add_argument('--capture-local-db', type=Path)
    parser.add_argument('--runtime-apk', type=Path, help='Optional preserved baseline APK; never installed')
    parser.add_argument('--variant', choices=('unchanged', 'delta'), default='delta')
    parser.add_argument('--delivery', choices=('synchronous', 'async'), default='synchronous',
                        help='Async mode requires the new capture API and actual AsyncSaveQueue in the APK')
    parser.add_argument('--count', type=int, choices=range(50, 101), default=100)
    parser.add_argument('--interval-ms', type=int, choices=range(101), default=20)
    parser.add_argument('--prefix', default='p_' + uuid.uuid4().hex[:12])
    parser.add_argument('--timeout', type=int, choices=range(5, 181), default=90,
                        help='Owned app_process deadline in seconds (default: 90)')
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    if not re.fullmatch(r'p_[A-Za-z0-9_-]{1,30}', args.prefix):
        raise ValueError('Unsafe run prefix')
    if args.sdk is None:
        raise ValueError('Set ANDROID_HOME or pass --sdk')
    metadata = capture(args.capture_local_db, args.fixture) if args.capture_local_db else None
    fixture_bytes = args.fixture.read_bytes()
    sidecar = args.fixture.with_suffix('.json')
    if metadata is None and sidecar.is_file():
        metadata = json.loads(sidecar.read_text())
    if metadata is not None and metadata.get('fixture_sha256') != sha(fixture_bytes):
        raise ValueError('Fixture metadata hash mismatch')
    sdk = args.sdk.resolve()
    android = sdk / 'platforms/android-28/android.jar'
    d8 = sdk / 'build-tools' / BUILD_TOOLS_VERSION / 'lib/d8.jar'
    adb = sdk / 'platform-tools' / executable_name('adb')
    classes_root = compiled_classes(args.classes_root)
    work = ROOT / '.local/game-save-performance' / args.prefix
    if work.exists():
        raise ValueError('Run prefix already exists')
    classes = work / 'classes'
    dex_output = work / 'dex'
    classes.mkdir(parents=True)
    dex_output.mkdir()
    view_jar = ROOT / 'app/build/original-dependencies/compile-view.jar'
    classpath = os.pathsep.join(map(str, (android, classes_root, view_jar)))
    checked([jdk_tool('javac'), '-source', '8', '-target', '8', '-cp', classpath, '-d', classes, ROOT / 'tools/GameSavePerformanceProbe.java'])
    programs = sorted(classes.glob('GameSavePerformanceProbe*.class'))
    if not programs or len(list(classes.rglob('*.class'))) != len(programs):
        raise RuntimeError('Unexpected probe program classes')
    checked([jdk_tool('java'), '-cp', d8, 'com.android.tools.r8.D8', '--debug', '--min-api', '16', '--lib', android,
             '--classpath', classes_root, '--classpath', view_jar, '--output', dex_output, *programs])
    dex = (dex_output / 'classes.dex').read_bytes()
    definitions = dex_classes(dex)
    if not definitions or any(not item.startswith('LGameSavePerformanceProbe') for item in definitions):
        raise RuntimeError('Probe accidentally contains copied production code')
    adb_command = [adb, '-s', args.serial]

    def shell(command, data=None):
        return checked([*adb_command, 'shell', command], timeout=60, data=data)[0]

    directory = 'files/sqlite-performance-code-' + args.prefix
    shell(shlex.join(['run-as', PACKAGE, 'mkdir', '-p', directory]))

    def upload(name, data):
        path = directory + '/' + name
        shell(shlex.join(['run-as', PACKAGE, 'sh', '-c', 'cat > ' + shlex.quote(path)]), data)
        if shell(shlex.join(['run-as', PACKAGE, 'sha256sum', path])).split()[0] != sha(data):
            raise RuntimeError('Upload hash mismatch')
        return path

    remote_dex = upload('probe.dex', dex)
    remote_fixture = upload('fixture.bin', fixture_bytes)
    private_dir = shell(shlex.join(['run-as', PACKAGE, 'pwd'])).strip()
    if args.runtime_apk:
        apk_bytes = args.runtime_apk.read_bytes()
        apk_path = private_dir + '/' + upload('runtime.apk', apk_bytes)
        apk_hash = sha(apk_bytes)
    else:
        paths = shell(shlex.join(['pm', 'path', PACKAGE])).splitlines()
        if len(paths) != 1 or not paths[0].startswith('package:/data/app/'):
            raise RuntimeError('Expected one complete installed APK')
        apk_path = paths[0].removeprefix('package:')
        apk_hash = shell(shlex.join(['sha256sum', apk_path])).split()[0]
    runtime = private_dir + '/' + remote_dex + ':' + apk_path
    command = shlex.join(['run-as', PACKAGE, 'env', 'CLASSPATH=' + runtime, 'app_process', '/system/bin',
                         'GameSavePerformanceProbe', remote_fixture.removeprefix('files/'), args.prefix,
                         str(args.count), str(args.interval_ms), args.variant, args.delivery])
    args.output.parent.mkdir(parents=True, exist_ok=True)
    try:
        result, stdout, stderr = process([*adb_command, 'shell', command], timeout=args.timeout)
    except subprocess.TimeoutExpired as timeout:
        stdout = (timeout.stdout or b'').decode('utf-8', errors='replace')
        stderr = (timeout.stderr or b'').decode('utf-8', errors='replace')
        args.output.with_suffix('.log').write_text(stdout + stderr)
        args.output.write_text(json.dumps({'passed': False, 'error': 'Owned app_process deadline exceeded',
                                         'timeout_seconds': args.timeout, 'apk_sha256': apk_hash,
                                         'fixture_sha256': sha(fixture_bytes)}, indent=2) + '\n')
        raise RuntimeError('Performance probe timed out; retained output: ' + str(args.output)) from timeout
    args.output.with_suffix('.log').write_text(stdout + stderr)
    markers = [json.loads(line) for line in stdout.splitlines() if line.startswith('{')]
    if result.returncode or len(markers) != 1 or not markers[0].get('passed'):
        raise RuntimeError('Performance probe failed; see ' + str(args.output.with_suffix('.log')))
    report = {'measured_at_utc': datetime.now(timezone.utc).isoformat(), 'device': args.serial,
              'apk_sha256': apk_hash, 'fixture_sha256': sha(fixture_bytes), 'probe_dex_sha256': sha(dex),
              'storage_implementation': 'GameSaveStore from APK; Android SQLite; WAL/FULL',
              'gameplay_serializer_exercised': False, 'controller_batch_policy_exercised': False,
              'render_frames_measured': False,
              'fixture_metadata': metadata, **markers[0]}
    args.output.write_text(json.dumps(report, indent=2) + '\n')
    print(json.dumps({key: value for key, value in report.items() if key != 'caller_samples_ms'}))


if __name__ == '__main__':
    main()
