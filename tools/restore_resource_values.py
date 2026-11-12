#!/usr/bin/env python3
"""Restore decoded numeric Android style values in the generated resource tree.

Only the explicit mappings below are supported. Every replacement is checked
against the selected Android SDK's official framework attrs.xml before use;
unknown numeric enum/flag values fail instead of inventing a symbolic spelling.
Call after restore_ninepatch.py. The supplied tree must be generated output,
not app/src/main/res. Original resources remain unchanged. Public id entries
also become explicit id declarations: public.xml fixes visibility/identity,
but does not itself define ids that are absent from layouts (for example a
library's keyed view tag). Actual numeric identities still come from the
existing stable-id configuration, never from this generator.
"""

import argparse
from pathlib import Path
import re
import xml.etree.ElementTree as ET


# Exact meanings from Android 28 data/res/values/attrs.xml. Avoid arbitrary
# bit decomposition: gravity flags overlap, so unknown combinations need review.
MAPPINGS = {
    "typeface": {0: "normal", 1: "sans", 2: "serif", 3: "monospace"},
    "textStyle": {0: "normal", 1: "bold", 2: "italic", 3: "bold|italic"},
    "orientation": {0: "horizontal", 1: "vertical"},
    "gravity": {0x11: "center", 0x03: "left"},
    "layout_gravity": {0x11: "center", 0x51: "bottom|center_horizontal"},
    "layout_width": {-1: "match_parent", -2: "wrap_content"},
    "layout_height": {-1: "match_parent", -2: "wrap_content"},
}

STYLE_OR_COMMENT = re.compile(r"<!--.*?-->|<style\b[^>]*>.*?</style\s*>", re.S)
ITEM_OR_COMMENT = re.compile(
    r"<!--.*?-->|(?P<open><item\b[^>]*\bname=(?P<quote>[\"'])android:"
    r"(?P<name>[A-Za-z_]+)(?P=quote)[^>]*>)(?P<space>\s*)"
    r"(?P<value>-?(?:0[xX][0-9a-fA-F]+|[0-9]+))(?P<endspace>\s*)"
    r"(?P<close></item\s*>)", re.S)


def validate_mappings(framework_attrs):
    definitions = {}
    for attr in ET.parse(framework_attrs).getroot().iter("attr"):
        symbols = {child.get("name"): int(child.get("value"), 0)
                   for child in attr if child.tag in ("flag", "enum")}
        if symbols:
            definitions.setdefault(attr.get("name"), {}).update(symbols)
    for name, values in MAPPINGS.items():
        symbols = definitions.get(name, {})
        for number, spelling in values.items():
            actual = 0
            for symbol in spelling.split("|"):
                if symbol not in symbols:
                    raise ValueError(f"SDK has no {name} symbol {symbol}")
                actual |= symbols[symbol]
            if actual != number:
                raise ValueError(f"SDK disagrees with {name}={spelling}: {actual} != {number}")


def restore_style_values(text):
    ET.fromstring(text)  # Reject malformed input before changing any file.
    count = 0

    def restore_style(style):
        if style.group().startswith("<!--"):
            return style.group()

        def restore_item(item):
            nonlocal count
            name = item.group("name")
            if name not in MAPPINGS:
                return item.group()
            value = item.group("value")
            number = int(value, 16 if "x" in value.lower() else 10)
            if number not in MAPPINGS[name]:
                raise ValueError(f"Unsupported decoded android:{name}={value}; review framework meaning")
            count += 1
            return (item.group("open") + item.group("space") + MAPPINGS[name][number]
                    + item.group("endspace") + item.group("close"))

        return ITEM_OR_COMMENT.sub(restore_item, style.group())

    restored = STYLE_OR_COMMENT.sub(restore_style, text)
    ET.fromstring(restored)
    return restored, count


def restore_id_declarations(public_xml):
    """Declare precisely the id names present in decoded public.xml."""
    declarations = ET.Element("resources")
    names = set()
    for public in ET.fromstring(public_xml).findall("public"):
        if public.get("type") != "id":
            continue
        name = public.get("name")
        if not name or not re.fullmatch(r"[A-Za-z_][A-Za-z0-9_]*", name):
            raise ValueError(f"Invalid public id name: {name!r}")
        if name in names:
            raise ValueError(f"Duplicate public id declaration: {name}")
        names.add(name)
        ET.SubElement(declarations, "item", {"type": "id", "name": name})
    ET.indent(declarations, space="    ")
    return '<?xml version="1.0" encoding="utf-8"?>\n' + ET.tostring(
        declarations, encoding="unicode") + "\n", len(names)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("generated_res", type=Path)
    parser.add_argument("--framework-attrs", type=Path, required=True)
    args = parser.parse_args()
    root = args.generated_res.resolve()
    if not root.is_dir() or "build" not in root.parts or "src" in root.parts:
        raise ValueError("Supply a generated resource directory under build, never original src resources")
    validate_mappings(args.framework_attrs)
    total = 0
    changed = []
    for path in sorted(root.glob("values*/*.xml")):
        text = path.read_text(encoding="utf-8")
        restored, count = restore_style_values(text)
        if count:
            changed.append((path, restored))
            total += count
    public_xml = (root / "values/public.xml").read_text(encoding="utf-8")
    id_declarations, id_count = restore_id_declarations(public_xml)
    # All files are checked first: an unsupported value cannot leave half the
    # generated tree converted.
    for path, restored in changed:
        path.write_text(restored, encoding="utf-8")
    (root / "values/restored_ids.xml").write_text(id_declarations, encoding="utf-8")
    print(f"Restored {total} Android style values using SDK-verified symbols; "
          f"declared {id_count} public ids from public.xml; originals unchanged.")


if __name__ == "__main__":
    main()
