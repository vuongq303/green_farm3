#!/usr/bin/env python3
"""Relocate identities in a lookup-only JVM jar; never use this as runtime code.

Package/class relocation rewrites CONSTANT_Utf8 contents and ZIP entry names;
the count, indices, bytecode and attributes stay intact and round-trip exactly.
Optional verified member renames append scoped name/NAT constants, preserve all
original indices and class-body bytes, and prove only approved refs changed.
Raw UTF-8 rewriting preserves JVM modified UTF-8, including NUL/surrogates.
"""
from pathlib import Path
import copy
import json
import re
import struct
import tempfile
import zipfile

OLD = 'com/gameloft/android/ANMP/GloftGF2F'
NEW = 'com/quanhoang/greenfarm3'
SYMBOL_CLASS_NAMES = {'ci': 'FarmGameEngine', 'ch': 'FarmEntity', 'GLLib': 'GameRuntime'}


def map_class_names(value, reverse=False):
    for old, new in SYMBOL_CLASS_NAMES.items():
        before, after = (new, old) if reverse else (old, new)
        for root, delimiter in ((NEW, '/'), (NEW.replace('/', '.'), '.')):
            prefix = root + delimiter + 'S800x480' + delimiter
            value = re.sub(re.escape((prefix + before).encode()) + rb'(?=[$;.<]|$)',
                           lambda _: (prefix + after).encode(), value)
    return value


def map_bytes(value, reverse=False, rename_symbols=False):
    before, after = (NEW, OLD) if reverse else (OLD, NEW)
    if rename_symbols and reverse:
        value = map_class_names(value, True)
    value = value.replace(before.encode(), after.encode()).replace(
        before.replace('/', '.').encode(), after.replace('/', '.').encode())
    return map_class_names(value) if rename_symbols and not reverse else value


def rewrite_class(data, reverse=False, rename_symbols=False):
    if data[:4] != b'\xca\xfe\xba\xbe':
        raise ValueError('Invalid compiler-view JVM class')
    count = struct.unpack_from('>H', data, 8)[0]
    output = bytearray(data[:10])
    offset, index = 10, 1
    while index < count:
        start = offset
        tag = data[offset]
        offset += 1
        if tag == 1:
            length = struct.unpack_from('>H', data, offset)[0]
            value = data[offset + 2:offset + 2 + length]
            if len(value) != length:
                raise ValueError('Truncated compiler-view UTF8 constant')
            changed = map_bytes(value, reverse, rename_symbols)
            if len(changed) > 65535:
                raise ValueError('Relocated JVM UTF8 constant exceeds limit')
            output.extend(bytes((tag,)) + struct.pack('>H', len(changed)) + changed)
            offset += 2 + length
        else:
            if tag in (3, 4): offset += 4
            elif tag in (5, 6): offset += 8; index += 1
            elif tag in (7, 8, 16, 19, 20): offset += 2
            elif tag in (9, 10, 11, 12, 17, 18): offset += 4
            elif tag == 15: offset += 3
            else: raise ValueError(f'Unknown JVM constant-pool tag: {tag}')
            if offset > len(data):
                raise ValueError('Truncated compiler-view constant pool')
            output.extend(data[start:offset])
        index += 1
    output.extend(data[offset:])
    return bytes(output)


def _constant_pool(data):
    """Return raw entries (indices retained, including long/double holes)."""
    count = struct.unpack_from('>H', data, 8)[0]
    entries = [None] * count
    offset, index = 10, 1
    while index < count:
        start = offset
        tag = data[offset]; offset += 1
        if tag == 1: offset += 2 + struct.unpack_from('>H', data, offset)[0]
        elif tag in (3, 4): offset += 4
        elif tag in (5, 6): offset += 8
        elif tag in (7, 8, 16, 19, 20): offset += 2
        elif tag in (9, 10, 11, 12, 17, 18): offset += 4
        elif tag == 15: offset += 3
        else: raise ValueError('Unknown classfile constant-pool tag')
        if offset > len(data): raise ValueError('Truncated classfile constant pool')
        entries[index] = data[start:offset]
        index += 2 if tag in (5, 6) else 1
    return entries, offset


def rewrite_engine_member_references(data):
    """Rename qualified engine refs without changing a shared qD/aYR UTF8.

    The lookup jar excludes the engine definitions themselves. A JVM method/field
    ref points to an owner and NameAndType; clone that NAT only for the verified
    owner+descriptor. Other.qD and unrelated aYR remain byte-for-byte intact.
    Original indices and method/attribute bytes do not move logically; new UTF8
    and NAT constants append at the end of the pool.
    """
    entries, tail = _constant_pool(data)
    original = list(entries)
    changes = []

    def utf8(index):
        entry = entries[index]
        if entry is None or entry[0] != 1: raise ValueError('Expected UTF8 constant')
        return entry[3:]

    owner_name = (NEW + '/S800x480/FarmGameEngine').encode()
    mapping = {(9, b'aYR', b'I'): b'currentTutorialStepIndex',
               (9, b'baa', b'Z'): b'hasUnsavedGameChanges',
               (10, b'qD', b'()I'): b'currentEngineState',
               (10, b'qE', b'()I'): b'previousEngineState'}
    for index in range(1, len(original)):
        entry = original[index]
        if entry is None or entry[0] not in (9, 10): continue
        owner_index, nat_index = struct.unpack_from('>HH', entry, 1)
        owner = entries[owner_index]
        if owner is None or owner[0] != 7: raise ValueError('Expected owner Class constant')
        if utf8(struct.unpack_from('>H', owner, 1)[0]) != owner_name: continue
        nat = entries[nat_index]
        if nat is None or nat[0] != 12: raise ValueError('Expected NameAndType constant')
        name_index, descriptor_index = struct.unpack_from('>HH', nat, 1)
        before, descriptor = utf8(name_index), utf8(descriptor_index)
        after = mapping.get((entry[0], before, descriptor))
        if after is None: continue
        if len(entries) + 2 > 65535: raise ValueError('Member rename exceeds classfile constant-pool limit')
        new_name_index = len(entries)
        entries.append(b'\x01' + struct.pack('>H', len(after)) + after)
        new_nat_index = len(entries)
        entries.append(b'\x0c' + struct.pack('>HH', new_name_index, descriptor_index))
        entries[index] = bytes((entry[0],)) + struct.pack('>HH', owner_index, new_nat_index)
        changes.append({'constant_index': index, 'kind': 'field' if entry[0] == 9 else 'method',
                        'owner': owner_name.decode(), 'before': before.decode(),
                        'after': after.decode(), 'descriptor': descriptor.decode()})
    output = data[:8] + struct.pack('>H', len(entries)) + b''.join(e for e in entries[1:] if e is not None) + data[tail:]
    after_entries, after_tail = _constant_pool(output)
    changed_indices = {entry['constant_index'] for entry in changes}
    if output[after_tail:] != data[tail:]: raise ValueError('Member rename changed class body/attributes')
    for index in range(1, len(original)):
        if index not in changed_indices and original[index] != after_entries[index]:
            raise ValueError('Member rename changed an unrelated constant-pool entry')
    # Replacing only approved refs and trimming appended entries must restore
    # every original class byte. This also proves original CP indices are stable.
    restored = output[:8] + struct.pack('>H', len(original)) + b''.join(e for e in original[1:] if e is not None) + output[after_tail:]
    if restored != data: raise ValueError('Scoped member rewrite did not round-trip')
    return output, changes


def relocate(source, output, rename_symbols=False):
    source, output = Path(source), Path(output)
    if source.resolve() == output.resolve():
        raise ValueError('Compiler-view relocation must not overwrite input')
    output.parent.mkdir(parents=True, exist_ok=True)
    changed = []
    member_changes = []
    with tempfile.TemporaryDirectory(prefix='relocated-compile-view-', dir=output.parent) as temp:
        pending = Path(temp) / 'lookup-only.jar'
        with zipfile.ZipFile(source) as before, zipfile.ZipFile(pending, 'w') as after:
            names = before.namelist()
            if len(names) != len(set(names)):
                raise ValueError('Duplicate compiler-view jar entries')
            written = set()
            for info in before.infolist():
                name = map_bytes(info.filename.encode(), rename_symbols=rename_symbols).decode()
                if name in written:
                    raise ValueError('Compiler-view relocation creates duplicate entry: ' + name)
                written.add(name)
                data = before.read(info)
                if name.endswith('.class'):
                    mapped = rewrite_class(data, rename_symbols=rename_symbols)
                    if rewrite_class(mapped, True, rename_symbols) != data:
                        raise ValueError('Compiler-view class did not round-trip: ' + name)
                    if rename_symbols:
                        mapped, scoped = rewrite_engine_member_references(mapped)
                        member_changes.extend({'class_file': name, **entry} for entry in scoped)
                    if mapped != data:
                        changed.append(info.filename)
                    data = mapped
                target = copy.copy(info)
                target.filename = name
                after.writestr(target, data)
        pending.replace(output)
    return {'purpose': 'compile-only; never package', 'from_package': OLD.replace('/', '.'),
            'to_package': NEW.replace('/', '.'), 'changed_class_count': len(changed),
            'game_symbols_renamed': rename_symbols,
            'all_class_bytes_round_trip_verified': True,
            'scoped_member_references': member_changes,
            'class_body_bytes_preserved_during_member_rename': True}


if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('source', type=Path)
    parser.add_argument('output', type=Path)
    parser.add_argument('--rename-symbols', action='store_true')
    args = parser.parse_args()
    print(json.dumps(relocate(args.source, args.output, args.rename_symbols), indent=2))
