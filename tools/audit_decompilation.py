#!/usr/bin/env python3
"""Inventory unresolved JADX markers without changing or interpreting game code."""

import json
from pathlib import Path
import re


ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / 'app/src/main/java'
UNKNOWN_TYPE = re.compile(r'^\s*\?\?\s+\w+', re.MULTILINE)
MISSING_METHOD = re.compile(r'throw new UnsupportedOperationException\("Method not decompiled: ([^"\n]+)"\);')


def audit():
    files = sorted(JAVA.rglob('*.java'))
    missing, unknown = [], []
    for path in files:
        text = path.read_text()
        relative = path.relative_to(ROOT).as_posix()
        for match in MISSING_METHOD.finditer(text):
            missing.append({'file': relative, 'line': text.count('\n', 0, match.start()) + 1,
                            'method': match.group(1)})
        for match in UNKNOWN_TYPE.finditer(text):
            unknown.append({'file': relative, 'line': text.count('\n', 0, match.start()) + 1})
    return {'java_files': len(files), 'missing_methods': missing, 'unresolved_type_declarations': unknown,
            'note': 'Marker counts are a lower bound, not a successful compile or behavior test.'}


if __name__ == '__main__':
    print(json.dumps(audit(), ensure_ascii=False, indent=2))
