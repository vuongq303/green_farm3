"""Reject missing/incompatible JVM entry points in the small-core Kotlin group.

Uses actual class-file descriptors from the verified original converted APK,
including package-visible methods/fields and synthetic constructors. Visibility
may widen for Kotlin interoperability. Added companion/accessor members do not
replace required original members.
"""
import argparse
import json
from pathlib import Path
import struct
import re
import zipfile

ORIGINAL_PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
CLASSES = '''ab ad ae af ag ai aj al an ap aq ar as au av ax az ba bb bd bg bh bi bj bk bm bn bo bp br bu bw c ca cb cc cd ce cf cj ck cl cm cn co cp cq cr cs ct cu cv cw cx cz d da db dc dd h o p q r u v y'''.split()


def read_abi(data):
    if data[:4] != b'\xca\xfe\xba\xbe':
        raise ValueError('Expected a JVM class file')
    pool = {}
    pool_size = struct.unpack_from('>H', data, 8)[0]
    offset, index = 10, 1
    while index < pool_size:
        tag = data[offset]
        offset += 1
        if tag == 1:
            length = struct.unpack_from('>H', data, offset)[0]
            offset += 2
            pool[index] = data[offset:offset + length].decode('utf-8', errors='replace')
            offset += length
        elif tag in (3, 4):
            offset += 4
        elif tag in (5, 6):
            offset += 8
            index += 1
        elif tag in (7, 8, 16, 19, 20):
            pool[index] = (tag, struct.unpack_from('>H', data, offset)[0])
            offset += 2
        elif tag in (9, 10, 11, 12, 17, 18):
            offset += 4
        elif tag == 15:
            offset += 3
        else:
            raise ValueError('Unexpected class-pool tag: ' + str(tag))
        index += 1
    flags, _, superclass, interface_count = struct.unpack_from('>HHHH', data, offset)
    offset += 8
    interfaces = struct.unpack_from('>' + 'H' * interface_count, data, offset) if interface_count else ()
    offset += 2 * interface_count
    result = {'class_flags': flags,
              'superclass': pool[pool[superclass][1]] if superclass else None,
              'interfaces': [pool[pool[entry][1]] for entry in interfaces]}
    for kind in ('fields', 'methods'):
        count = struct.unpack_from('>H', data, offset)[0]
        offset += 2
        members = {}
        for _ in range(count):
            access, name, descriptor, attributes = struct.unpack_from('>HHHH', data, offset)
            offset += 8
            if not (access & 0x2) and pool[name] != '<clinit>':
                members[pool[name] + ':' + pool[descriptor]] = access
            for _ in range(attributes):
                length = struct.unpack_from('>I', data, offset + 2)[0]
                offset += 6 + length
        result[kind] = members
    return result


def check(original_jar, classes_directory, compiled_package=ORIGINAL_PACKAGE, rename_core_symbols=False):
    compiled_package = compiled_package.replace('.', '/').strip('/')
    def relocate_descriptor(value):
        value = value.replace(ORIGINAL_PACKAGE, compiled_package)
        if rename_core_symbols:
            for before, after in {'ci': 'FarmGameEngine', 'ch': 'FarmEntity', 'GLLib': 'GameRuntime'}.items():
                value = re.sub(re.escape(compiled_package + '/' + before) + r'(?=[$;]|$)',
                               lambda _: compiled_package + '/' + after, value)
        return value
    failures = []
    method_count = field_count = 0
    with zipfile.ZipFile(original_jar) as archive:
        for name in CLASSES:
            before = read_abi(archive.read(ORIGINAL_PACKAGE + '/' + name + '.class'))
            target = Path(classes_directory) / compiled_package / (name + '.class')
            if not target.is_file():
                failures.append(name + ': missing compiled replacement class')
                continue
            after = read_abi(target.read_bytes())
            expected_superclass = relocate_descriptor(before['superclass'])
            expected_interfaces = [relocate_descriptor(entry) for entry in before['interfaces']]
            if expected_superclass != after['superclass'] or set(expected_interfaces) != set(after['interfaces']):
                failures.append(name + ': superclass/interface contract changed')
            if before['class_flags'] & 0x600 != after['class_flags'] & 0x600:
                failures.append(name + ': class/interface/abstract kind changed')
            if not (before['class_flags'] & 0x10) and after['class_flags'] & 0x10:
                failures.append(name + ': originally extensible class became final')
            for kind in ('fields', 'methods'):
                if kind == 'methods':
                    method_count += len(before[kind])
                else:
                    field_count += len(before[kind])
                for original_key, old_flags in before[kind].items():
                    key = relocate_descriptor(original_key)
                    new_flags = after[kind].get(key)
                    if new_flags is None:
                        failures.append(name + ': missing ' + kind + ' ' + key)
                        continue
                    if old_flags & 0x8 != new_flags & 0x8:
                        failures.append(name + ': static/instance mismatch ' + key)
                    if kind == 'fields' and old_flags & 0x10 != new_flags & 0x10:
                        failures.append(name + ': field mutability changed ' + key)
                    if kind == 'methods' and not (old_flags & 0x10) and new_flags & 0x10 and not (old_flags & 0x8) and not (before['class_flags'] & 0x10):
                        failures.append(name + ': originally overridable method became final ' + key)
                    if old_flags & 0x1 and not (new_flags & 0x1):
                        failures.append(name + ': originally public entry point became inaccessible ' + key)
    return {'classes': len(CLASSES), 'original_methods': method_count, 'original_fields': field_count,
            'original_package': ORIGINAL_PACKAGE, 'compiled_package': compiled_package,
            'verified_core_names': rename_core_symbols, 'passed': not failures, 'failures': failures}


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--original-jar', type=Path, required=True)
    parser.add_argument('--classes-directory', type=Path, required=True)
    parser.add_argument('--compiled-package', default=ORIGINAL_PACKAGE)
    parser.add_argument('--rename-core-symbols', action='store_true')
    arguments = parser.parse_args()
    report = check(arguments.original_jar, arguments.classes_directory, arguments.compiled_package, arguments.rename_core_symbols)
    print(json.dumps(report, indent=2))
    raise SystemExit(0 if report['passed'] else 1)
