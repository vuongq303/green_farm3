#!/usr/bin/env python3
"""Inventory original game identities separately from active source/SDK payloads.

This never opens a player database. JADX files excluded by the app source profile
are explicitly reference source, not evidence of a successful source rebuild.
"""
from collections import Counter
from pathlib import Path
import argparse
import hashlib
import json
import re
import subprocess
import zipfile

from verify_runtime_apk import dex_classes
from prepare_core_dependencies import verify_original

ORIGINAL = 'com/gameloft/android/ANMP/GloftGF2F'
TARGET = 'com/quanhoang/greenfarm3'
RENAMED = {'ci': 'FarmGameEngine', 'ch': 'FarmEntity', 'GLLib': 'GameRuntime'}


def generate(root):
    root = Path(root)
    verify_original(root / 'green-farm-3.apk')
    main = root / 'app/src/main'
    with zipfile.ZipFile(root / 'green-farm-3.apk') as archive:
        classes = dex_classes(archive.read('classes.dex'))
        game = sorted(c for c in classes if c.startswith('L' + ORIGINAL + '/'))
        records = []
        for descriptor in game:
            original_relative = descriptor[1:-1]
            relocated_relative = original_relative.replace(ORIGINAL + '/', TARGET + '/')
            outer = original_relative.split('$')[0]
            leaf = outer.rsplit('/', 1)[-1]
            renamed_relative = relocated_relative
            if '/S800x480/' in original_relative and leaf in RENAMED:
                candidate = relocated_relative.rsplit('/', 1)[0] + '/' + RENAMED[leaf]
                if any((main / language / (candidate + extension)).is_file()
                       for language, extension in [('java', '.java'), ('kotlin', '.kt')]):
                    renamed_relative = candidate + original_relative[len(outer):]
            active = None
            # Main Java compilation intentionally includes only the recovered
            # engine, while Kotlin source roots include launcher/migration code.
            for identity in (renamed_relative, relocated_relative, original_relative):
                path = main / 'kotlin' / (identity.split('$')[0] + '.kt')
                if path.is_file():
                    active = ('Kotlin', path.relative_to(root).as_posix())
                    break
                path = main / 'java' / (identity.split('$')[0] + '.java')
                if '/S800x480/' in identity and leaf not in ('l', 'g') and path.is_file():
                    active = ('Java', path.relative_to(root).as_posix())
                    break
            reference = next((p.relative_to(root).as_posix()
                    for identity in (original_relative, relocated_relative, renamed_relative)
                    for p in (main / 'java' / (identity.split('$')[0] + '.java'),)
                    if p.is_file()), None)
            records.append({'original_descriptor': descriptor,
                            'target_descriptor': 'L' + renamed_relative + ';',
                            'subsystem': 'engine' if '/S800x480/' in descriptor else 'android-integration',
                            'runtime_source': active[0] if active else 'original DEX',
                            'active_source': active[1] if active else None,
                            'jadx_reference_source': reference})
        natives = []
        for name in sorted(n for n in archive.namelist() if n.endswith('.so')):
            data = archive.read(name)
            literals = re.findall(rb'[\x20-\x7e]{4,}', data)
            native_entries = sorted(set(v.decode() for v in literals
                                       if v.startswith(b'Java_') or v == b'JNI_OnLoad'))
            old_refs = [v.decode() for v in literals if b'GloftGF2F' in v or b'ANMP' in v]
            natives.append({'path': name, 'size_bytes': len(data), 'sha256': hashlib.sha256(data).hexdigest(),
                            'game_package_string_references': old_refs,
                            'jni_entry_names': native_entries})
        payloads = [{'path': n, 'bytes': archive.getinfo(n).file_size,
                     'sha256': hashlib.sha256(archive.read(n)).hexdigest()}
                    for n in sorted(archive.namelist()) if n.startswith(('assets/', 'lib/')) and not n.endswith('/')]
        strings = re.findall(rb'[\x20-\x7e]{6,}', archive.read('classes.dex'))
        package_strings = sorted(set(s.decode() for s in strings if ORIGINAL.encode() in s
                                  or ORIGINAL.replace('/', '.').encode() in s))
        # Kept separately from the236 real definitions: the original manifest
        # names this activity, but the supplied original DEX has no such class.
        keyboard_name = ORIGINAL.replace('/', '.') + '.SoftKeyBoardActivity'
        unresolved_components = [{'name': keyboard_name,
                 'declared_in_original_manifest': True,
                 'defined_in_original_dex': 'L' + keyboard_name.replace('.', '/') + ';' in classes,
                 'full_name_literal_in_original_dex': keyboard_name.encode() in archive.read('classes.dex'),
                 'class_suffix_literal_in_original_dex': b'SoftKeyBoardActivity' in archive.read('classes.dex'),
                 'status': 'Known dangling original manifest component; dynamic name construction/keyboard UI not verified; no replacement stub'}]
    groups = Counter()
    for descriptor in classes:
        if descriptor.startswith('L' + ORIGINAL + '/'):
            continue
        components = descriptor[1:].split('/')
        group = '/'.join(components[:3]) if len(components) >= 3 else '/'.join(components[:-1])
        groups[group] += 1
    return {'schema_version': 1, 'original_apk': 'green-farm-3.apk',
            'original_apk_sha256': hashlib.sha256((root / 'green-farm-3.apk').read_bytes()).hexdigest(),
            'original_defined_classes': len(classes), 'original_game_classes': len(records),
            'original_engine_classes': sum(r['subsystem'] == 'engine' for r in records),
            'game_runtime_source_counts': dict(Counter(r['runtime_source'] for r in records)),
            'engine_runtime_source_counts': dict(Counter(r['runtime_source'] for r in records if r['subsystem'] == 'engine')),
            'game_classes': records,
            'original_unresolved_manifest_components': unresolved_components,
            'external_namespace_class_counts': dict(sorted(groups.items())),
            'opaque_gameloft_service_descriptors': sorted(c for c in classes if c.startswith('Lcom/gameloft/a/')),
            'retained_midlet_runtime_descriptors': sorted(c for c in classes if c.startswith('Ljavax/microedition/')),
            'classification_boundary': 'The236 game namespace classes are a complete namespace list, not proof that every class outside it is third-party-only. Opaque Gameloft services and MIDlet/wrapper bridges are retained and require separate behavioral audit.',
            'native_libraries': natives, 'original_assets_native_payloads': payloads,
            'original_game_identity_strings': package_strings,
            'runtime_source_classification_note': 'Engine Java is active source, sibling JADX Java remains DEX-only reference. Kotlin active roots are recorded separately. No Java-to-Kotlin claim is inferred from package relocation.',
            'save': {'database': 'greenfarm-progress.db', 'schema_version': 4,
                     'payload_codec': 'original opaque game/RMS records with checksum, pose and pending-reward records',
                     'durability': 'WAL and synchronous FULL',
                     'new_application_private_storage_requires_transfer': True}}


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--project-root', type=Path, default=Path(__file__).resolve().parents[1])
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    report = generate(args.project_root)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(report, indent=2, ensure_ascii=False) + '\n')
    print(json.dumps({key: report[key] for key in ('original_defined_classes', 'original_game_classes',
              'original_engine_classes', 'game_runtime_source_counts', 'engine_runtime_source_counts')}, indent=2))
