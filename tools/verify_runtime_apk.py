#!/usr/bin/env python3
"""Reject incomplete launch APKs using DEX definitions and the packaged manifest.

This checks launch-class/superclass availability, not executable instructions or
all dependencies needed by gameplay. It never installs or changes an APK.
"""
import argparse
import hashlib
import json
from pathlib import Path
import re
import struct
import subprocess
import zipfile
import zlib
from urllib.parse import urlsplit

from android_build_tools import BUILD_TOOLS_VERSION, executable_name

LEGACY_PACKAGE = 'com.gameloft.android.ANMP.GloftGF2F'
APPLICATION_PACKAGE = 'com.quanhoang.greenfarm3'
GAME = 'L' + LEGACY_PACKAGE.replace('.', '/') + '/'
REQUIRED = ('Ljavax/microedition/midlet/App;', 'Ljavax/microedition/midlet/MIDlet;',
            GAME + 'Start;', GAME + 'S800x480/GloftGF2F;')
DEX_NAME = re.compile(r'classes(?:[2-9]|[1-9][0-9]+)?\.dex$')


def dex_classes(data):
    """Read actual class_def_item entries; strings alone never define classes."""
    size = len(data)
    def fail(message):
        raise ValueError('DEX ' + message)
    def span(offset, count, width=1, alignment=1):
        if offset < 112 or offset % alignment or offset > size or count > (size - offset) // width:
            fail('offset/range is invalid')
    def uint(offset):
        return struct.unpack_from('<I', data, offset)[0]
    if size < 112 or data[:8] not in [b'dex\n' + v + b'\0' for v in (b'035', b'037', b'038', b'039', b'040')]:
        fail('header/version is unsupported or truncated')
    if uint(32) != size or uint(36) != 112 or uint(40) != 0x12345678:
        fail('file size/header size/endianness is invalid')
    if data[12:32] != hashlib.sha1(data[32:]).digest() or uint(8) != zlib.adler32(data[12:]):
        fail('checksum/signature mismatch')
    tables, ranges = [], []
    for header, width in ((56, 4), (64, 4), (72, 12), (80, 8), (88, 8), (96, 32), (104, 1), (44, 1)):
        count, offset = struct.unpack_from('<II', data, header)
        if count:
            span(offset, count, width, 1 if header in (44, 104) else 4)
            ranges.append((offset, offset + count * width))
        elif offset:
            fail('empty table has a nonzero offset')
        tables.append((count, offset))
    ordered = sorted(ranges)
    if any(left[1] > right[0] for left, right in zip(ordered, ordered[1:])):
        fail('table ranges overlap')
    data_count, data_offset = tables[6]
    if data_offset + data_count != size:
        fail('data range does not reach file end')
    if uint(52):
        span(uint(52), 1, 4, 4)
        if uint(52) < data_offset:
            fail('map lies outside data section')
        span(uint(52) + 4, uint(uint(52)), 12)
    strings = []
    for index in range(tables[0][0]):
        offset = uint(tables[0][1] + index * 4)
        if offset < data_offset:
            fail('string data lies outside data section')
        length = 0
        for shift in range(0, 35, 7):
            span(offset, 1)
            byte = data[offset]; offset += 1
            if shift == 28 and byte > 15:
                fail('string length is invalid')
            length |= (byte & 127) << shift
            if byte < 128:
                break
        else:
            fail('string length is unterminated')
        end = data.find(b'\0', offset)
        if end < 0:
            fail('string is unterminated')
        try:
            value = data[offset:end].replace(b'\xc0\x80', b'\0').decode('utf-8', 'surrogatepass')
            if len(value.encode('utf-16-le', 'surrogatepass')) // 2 != length:
                fail('string length mismatch')
        except UnicodeError as error:
            raise ValueError('DEX string encoding is invalid') from error
        strings.append(value)
    def item(values, index):
        if index >= len(values):
            fail('table reference is invalid')
        return values[index]
    types = [item(strings, uint(tables[1][1] + index * 4)) for index in range(tables[1][0])]
    def type_list(offset):
        if offset:
            span(offset, 1, 4, 4)
            if offset < data_offset:
                fail('type list lies outside data section')
            count = uint(offset); span(offset + 4, count, 2)
            for index in range(count):
                item(types, struct.unpack_from('<H', data, offset + 4 + index * 2)[0])
    for index in range(tables[2][0]):
        shorty, result, parameters = struct.unpack_from('<III', data, tables[2][1] + index * 12)
        item(strings, shorty); item(types, result); type_list(parameters)
    for table, targets in ((tables[3], types), (tables[4], range(tables[2][0]))):
        for index in range(table[0]):
            owner, target, name = struct.unpack_from('<HHI', data, table[1] + index * 8)
            item(types, owner); item(targets, target); item(strings, name)
    classes = {}
    for index in range(tables[5][0]):
        values = struct.unpack_from('<8I', data, tables[5][1] + index * 32)
        name = item(types, values[0])
        superclass = None if values[2] == 0xffffffff else item(types, values[2])
        if (not name.startswith('L') or not name.endswith(';')
                or (superclass is None and name != 'Ljava/lang/Object;')
                or (superclass is not None and not (superclass.startswith('L') and superclass.endswith(';')))):
            fail('class descriptor is invalid')
        if name in classes:
            fail('duplicate class: ' + name)
        type_list(values[3])
        if values[4] != 0xffffffff:
            item(strings, values[4])
        for offset in values[5:]:
            if offset:
                span(offset, 1)
                if offset < data_offset:
                    fail('class data lies outside data section')
        classes[name] = superclass
    return classes


def manifest_tree(apk, sdk):
    aapt2 = Path(sdk) / 'build-tools' / BUILD_TOOLS_VERSION / executable_name('aapt2')
    result = subprocess.run([str(aapt2), 'dump', 'xmltree', str(apk), '--file', 'AndroidManifest.xml'],
                            check=True, capture_output=True, text=True, timeout=30)
    stack, nodes = [], []
    for line in result.stdout.splitlines():
        element = re.match(r'(\s*)E: ([\w-]+)', line)
        if element:
            depth = len(element[1])
            while stack and stack[-1][0] >= depth:
                stack.pop()
            node = {'tag': element[2], 'attributes': {}, 'children': []}
            (stack[-1][1]['children'] if stack else nodes).append(node)
            stack.append((depth, node))
        attribute = re.match(r'\s*A: (.*?)=(?:"([^"]*)"|(\S+))', line)
        if attribute and stack:
            key = attribute[1].split('(')[0].rsplit(':', 1)[-1]
            stack[-1][1]['attributes'][key] = attribute[2] if attribute[2] is not None else attribute[3]
    if len(nodes) != 1 or nodes[0]['tag'] != 'manifest':
        raise ValueError('Packaged manifest is missing')
    return nodes[0]


def manifest(apk, sdk):
    root = manifest_tree(apk, sdk); package = root['attributes'].get('package', '')
    applications = [n for n in root['children'] if n['tag'] == 'application']
    if not package or len(applications) != 1:
        raise ValueError('Packaged manifest package/application is invalid')
    application = applications[0]; launchers = []
    for activity in application['children']:
        if activity['tag'] == 'activity':
            for intent in activity['children']:
                names = {(n['tag'], n['attributes'].get('name')) for n in intent['children']}
                if intent['tag'] == 'intent-filter' and {('action', 'android.intent.action.MAIN'), ('category', 'android.intent.category.LAUNCHER')} <= names:
                    launchers.append(activity['attributes'].get('name', ''))
    def qualify(name):
        return package + name if name.startswith('.') else package + '.' + name if '.' not in name else name
    if len(launchers) != 1 or not launchers[0] or not application['attributes'].get('name'):
        raise ValueError('Packaged manifest application/launcher is invalid')
    return package, qualify(application['attributes']['name']), qualify(launchers[0])


def network_policy(apk, sdk, backend_base_url):
    """Inspect the installed artifact's binary manifest, not just source XML."""
    root = manifest_tree(apk, sdk)
    application = next((n for n in root['children'] if n['tag'] == 'application'), None)
    if application is None:
        raise ValueError('Packaged manifest application is missing')
    attributes = application['attributes']
    internet = any(n['tag'] == 'uses-permission'
                   and n['attributes'].get('name') == 'android.permission.INTERNET'
                   and 'maxSdkVersion' not in n['attributes'] for n in root['children'])
    if not internet:
        raise ValueError('Final APK lacks unrestricted android.permission.INTERNET')
    if attributes.get('debuggable') != 'true':
        raise ValueError('Configured backend pipeline must publish a debug APK manifest')
    if attributes.get('networkSecurityConfig') is not None:
        raise ValueError('Final APK has an unreviewed networkSecurityConfig override')
    scheme = urlsplit(backend_base_url).scheme.lower()
    if scheme not in ('http', 'https'):
        raise ValueError('BACKEND_BASE_URL must use HTTP(S)')
    cleartext = attributes.get('usesCleartextTraffic') == 'true'
    if scheme == 'http' and not cleartext:
        raise ValueError('Final debug APK blocks HTTP traffic to BACKEND_BASE_URL')
    return {'internet_permission': internet, 'debuggable': True,
            'uses_cleartext_traffic': cleartext, 'network_security_config': None}


def verify_apk(apk, original, sdk, metadata=None, backend_base_url=None,
               application_id=None, game_package=None):
    apk, original = Path(apk), Path(original)
    classes, dex_count = {}, 0
    with zipfile.ZipFile(original) as source, zipfile.ZipFile(apk) as output:
        for archive in (source, output):
            if len(archive.namelist()) != len(set(archive.namelist())):
                raise ValueError('APK contains duplicate entries')
        if 'classes.dex' not in output.namelist():
            raise ValueError('APK lacks primary classes.dex')
        for name in output.namelist():
            if DEX_NAME.fullmatch(name):
                found = dex_classes(output.read(name)); dex_count += 1
                if classes.keys() & found.keys():
                    raise ValueError('DEX duplicate class across files')
                classes.update(found)
        detected_package = (APPLICATION_PACKAGE if
                            'L' + APPLICATION_PACKAGE.replace('.', '/') + '/Start;' in classes
                            else LEGACY_PACKAGE)
        if game_package is not None and game_package != detected_package:
            raise ValueError('Game class namespace differs from the expected package')
        game = 'L' + detected_package.replace('.', '/') + '/'
        required_classes = REQUIRED[:2] + (game + 'Start;', game + 'S800x480/GloftGF2F;')
        if detected_package == APPLICATION_PACKAGE and any(name.startswith(GAME) for name in classes):
            raise ValueError('Legacy game class definitions remain after package relocation')
        for required in required_classes:
            if required not in classes:
                raise ValueError('Missing runtime class definition: ' + required)
        payload = lambda archive: {n for n in archive.namelist() if n.startswith(('assets/', 'lib/')) and not n.endswith('/')}
        names = payload(source)
        if names != payload(output):
            raise ValueError('Assets/native file set changed')
        for name in sorted(names):
            if source.read(name) != output.read(name) or source.getinfo(name).compress_type != output.getinfo(name).compress_type:
                raise ValueError('Assets/native bytes or compression changed: ' + name)
    package, application, launcher = manifest(apk, sdk)
    original_package, original_application, original_launcher = manifest(original, sdk)
    expected_package = application_id or (APPLICATION_PACKAGE if detected_package == APPLICATION_PACKAGE else original_package)
    expected_launcher = original_launcher.replace(LEGACY_PACKAGE, detected_package, 1)
    if (package, application, launcher) != (expected_package, original_application, expected_launcher):
        raise ValueError('Packaged manifest package/application/launcher differs from the reviewed configuration')
    for entry in (*required_classes, 'L' + application.replace('.', '/') + ';', 'L' + launcher.replace('.', '/') + ';'):
        seen = set()
        while entry and not (entry.startswith(('Landroid/', 'Ljava/'))
                             and not entry.startswith(('Landroid/support/', 'Landroid/arch/'))):
            if entry in seen:
                raise ValueError('Runtime superclass cycle: ' + entry)
            seen.add(entry)
            if entry not in classes:
                raise ValueError('Missing runtime superclass definition: ' + entry)
            entry = classes[entry]
    if metadata:
        metadata = Path(metadata); values = json.loads(metadata.read_text())
        elements = values.get('elements', []) if isinstance(values, dict) else []
        if (not isinstance(elements, list) or len(elements) != 1 or not isinstance(elements[0], dict)
                or not isinstance(elements[0].get('outputFile'), str)
                or values.get('applicationId') != package
                or (metadata.parent / elements[0]['outputFile']).resolve() != apk.resolve()):
            raise ValueError('APK metadata does not point to this APK/package')
    result = {'apk': str(apk.resolve()), 'package': package, 'application': application, 'launcher': launcher,
              'dex_count': dex_count, 'class_count': len(classes), 'payload_count': len(names),
              'game_package': detected_package,
              'legacy_game_class_definitions_remaining': sum(name.startswith(GAME) for name in classes)}
    if backend_base_url:
        result['network_policy'] = network_policy(apk, sdk, backend_base_url)
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ('apk', 'original', 'sdk'):
        parser.add_argument('--' + name, type=Path, required=True)
    parser.add_argument('--metadata', type=Path)
    parser.add_argument('--backend-base-url')
    parser.add_argument('--application-id', choices=(LEGACY_PACKAGE, APPLICATION_PACKAGE))
    parser.add_argument('--game-package', choices=(LEGACY_PACKAGE, APPLICATION_PACKAGE))
    args = parser.parse_args()
    print(json.dumps(verify_apk(args.apk, args.original, args.sdk, args.metadata, args.backend_base_url,
                              args.application_id, args.game_package), indent=2))


if __name__ == '__main__':
    main()
