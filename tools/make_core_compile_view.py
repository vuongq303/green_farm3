#!/usr/bin/env python3
"""Make a compile-only view of one unrepresentable DEX package/class collision.

Original DEX contains both the public interface javax.microedition.a.a and the
package javax.microedition.a.a (with FileConnection interface a). Java resolves
the public interface first and cannot name the package. Hiding that interface
from source lookup solves compilation while leaving every JVM name, descriptor
and instruction unchanged. NEVER package this compile-only jar in the game.
"""

import argparse
import hashlib
import json
from pathlib import Path
import struct
import zipfile


CONNECTION = "javax/microedition/a/a.class"


def class_access_offset(data):
    if data[:4] != b"\xca\xfe\xba\xbe":
        raise ValueError("Not a JVM class file")
    count = struct.unpack_from(">H", data, 8)[0]
    offset, index = 10, 1
    while index < count:
        tag = data[offset]
        offset += 1
        if tag == 1:
            offset += 2 + struct.unpack_from(">H", data, offset)[0]
        elif tag in (3, 4): offset += 4
        elif tag in (5, 6): offset += 8; index += 1
        elif tag in (7, 8, 16, 19, 20): offset += 2
        elif tag in (9, 10, 11, 12, 17, 18): offset += 4
        elif tag == 15: offset += 3
        else: raise ValueError(f"Unknown constant pool tag {tag}")
        index += 1
    return offset


def generate(source, output):
    if source.resolve() == output.resolve():
        raise ValueError("The original runtime jar must never be overwritten")
    source_hash = hashlib.sha256(source.read_bytes()).hexdigest()
    output.parent.mkdir(parents=True, exist_ok=True)
    temporary = output.with_suffix(output.suffix + ".tmp")
    changed = []
    try:
        with zipfile.ZipFile(source) as archive, zipfile.ZipFile(temporary, "w") as result:
            if archive.namelist().count(CONNECTION) != 1:
                raise ValueError("Expected exactly one original Connection interface")
            for entry in archive.infolist():
                original = archive.read(entry)
                data = original
                if entry.filename == CONNECTION:
                    offset = class_access_offset(data)
                    flags = struct.unpack_from(">H", data, offset)[0]
                    if not flags & 0x0200:
                        raise ValueError("Connection must be an interface")
                    data = data[:offset] + struct.pack(">H", flags & ~0x0001) + data[offset + 2:]
                    changed.append({"class": CONNECTION, "access_flags_before": flags,
                                    "access_flags_after": flags & ~0x0001,
                                    "original_sha256": hashlib.sha256(original).hexdigest(),
                                    "compile_view_sha256": hashlib.sha256(data).hexdigest()})
                result.writestr(entry, data)
        temporary.replace(output)
    finally:
        if temporary.exists(): temporary.unlink()
    if hashlib.sha256(source.read_bytes()).hexdigest() != source_hash:
        raise ValueError("Original jar changed during compile-view generation")
    report = {"purpose": "compile-only; never use as runtime dependency",
              "source_sha256": source_hash, "changes": changed}
    output.with_suffix(output.suffix + ".json").write_text(json.dumps(report, indent=2) + "\n")
    return report


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("source", type=Path)
    parser.add_argument("output", type=Path)
    args = parser.parse_args()
    print(json.dumps(generate(args.source, args.output), indent=2))
