#!/usr/bin/env python3
"""Keep the original APK's resource IDs during AAPT2 linking.

Format verified with `aapt2 link --emit-ids` and a --stable-ids round trip:
    package.name:type/resource.name = 0x7f010000
Names retain their XML spelling (including dots), not Java R field escaping.
This preserves inlined IDs in decompiled Java and potentially native binaries.
"""

from __future__ import annotations

import argparse
from pathlib import Path
import xml.etree.ElementTree as ET


APP_PACKAGE = "com.gameloft.android.ANMP.GloftGF2F"


def render(public_xml: Path, package: str = APP_PACKAGE) -> str:
    ids: dict[int, str] = {}
    names: dict[str, int] = {}
    for entry in ET.parse(public_xml).getroot().findall("public"):
        value = int(entry.attrib["id"], 0)
        name = f"{package}:{entry.attrib['type']}/{entry.attrib['name']}"
        if value >> 24 != 0x7F:
            raise ValueError(f"Unexpected app resource package ID: {value:#x}")
        if value in ids and ids[value] != name:
            raise ValueError(f"Resource ID {value:#x} has conflicting names")
        if name in names and names[name] != value:
            raise ValueError(f"Resource {name} has conflicting IDs")
        ids[value] = name
        names[name] = value
    if not ids:
        raise ValueError("No public resource IDs found")
    return "".join(f"{name} = 0x{value:08x}\n" for value, name in sorted(ids.items()))


def generate(project_root: Path, output: Path) -> int:
    content = render(project_root / "app/src/main/res/values/public.xml")
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(content)
    return len(content.splitlines())


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--project-root", type=Path, default=Path(__file__).resolve().parents[1])
    parser.add_argument("--output", type=Path)
    parser.add_argument("--package", default=APP_PACKAGE)
    args = parser.parse_args()
    output = args.output or args.project_root / "app/build/generated/restored-resources/stable-ids.txt"
    content = render(args.project_root / "app/src/main/res/values/public.xml", args.package)
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(content)
    count = len(content.splitlines())
    print(f"Preserved {count} original resource IDs in {output}")


if __name__ == "__main__":
    main()
