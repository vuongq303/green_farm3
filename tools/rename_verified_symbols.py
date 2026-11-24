#!/usr/bin/env python3
"""Apply the reviewed core names through javac symbols and guarded Kotlin refs.

This is a one-time source migration, never a build-time text substitution. Java
resolution must succeed before editing. Kotlin helpers only refer to these same-
package Java types; shadowed/unresolved bindings and interpolated references are
rejected instead of guessed. String/comment payloads are preserved verbatim.
"""
from __future__ import annotations

import argparse
from dataclasses import dataclass
import json
import os
from pathlib import Path
import re
import subprocess

from android_build_tools import jdk_tool

ROOT = Path(__file__).resolve().parents[1]
TYPE_NAMES = {'ci': 'FarmGameEngine', 'ch': 'FarmEntity', 'GLLib': 'GameRuntime'}
FIELD_NAMES = {'aYR': 'currentTutorialStepIndex', 'baa': 'hasUnsavedGameChanges'}
METHOD_NAMES = {'qD': 'currentEngineState', 'qE': 'previousEngineState'}


@dataclass(frozen=True)
class Token:
    start: int
    end: int
    text: str
    kind: str = 'code'


def kotlin_tokens(source):
    """Retain offsets while excluding literal/comment data from symbol edits."""
    index = 0
    while index < len(source):
        start = index
        if source[index].isspace():
            index += 1
            continue
        if source.startswith('//', index):
            end = source.find('\n', index + 2)
            index = len(source) if end < 0 else end
            continue
        if source.startswith('/*', index):
            depth = 1
            index += 2
            while depth and index < len(source):
                if source.startswith('/*', index):
                    depth += 1
                    index += 2
                elif source.startswith('*/', index):
                    depth -= 1
                    index += 2
                else:
                    index += 1
            if depth:
                raise ValueError('Unterminated Kotlin comment')
            continue
        if source.startswith('"""', index):
            end = source.find('"""', index + 3)
            if end < 0:
                raise ValueError('Unterminated Kotlin raw string')
            index = end + 3
            yield Token(start, index, source[start:index], 'literal')
            continue
        if source[index] in '\"\'':
            quote = source[index]
            index += 1
            closed = False
            while index < len(source):
                char = source[index]
                index += 1
                if char == '\\' and index < len(source):
                    index += 1
                elif char == quote:
                    closed = True
                    break
            if not closed:
                raise ValueError('Unterminated Kotlin quoted literal')
            yield Token(start, index, source[start:index], 'literal')
            continue
        if source[index] == '`':
            end = source.find('`', index + 1)
            if end < 0:
                raise ValueError('Unterminated Kotlin escaped identifier')
            index = end + 1
            yield Token(start, index, source[start:index])
            continue
        if source[index].isalpha() or source[index] in '_$':
            index += 1
            while index < len(source) and (source[index].isalnum() or source[index] in '_$'):
                index += 1
        elif source[index:index + 2] in ('::', '->', '?.'):
            index += 2
        else:
            index += 1
        yield Token(start, index, source[start:index])


def renamed_kotlin_text(source, package_name):
    tokens = list(kotlin_tokens(source))
    for token in tokens:
        if token.kind == 'literal' and re.search(r'\$\{[^}]*\b(?:ci|ch|GLLib)\b|\$(?:ci|ch|GLLib)\b', token.text):
            raise ValueError('A reviewed type appears in string interpolation; resolve that template explicitly')
    code = [token for token in tokens if token.kind == 'code']
    # This subset deliberately accepts only resolved same-package helper calls.
    # Every ambiguous binding aborts before Java or Kotlin source is written.
    edits = []
    for index, token in enumerate(code):
        name = token.text
        previous = code[index - 1].text if index else ''
        following = code[index + 1].text if index + 1 < len(code) else ''
        if name in TYPE_NAMES:
            if previous in ('val', 'var', 'fun', 'class', 'object', 'interface', 'typealias', 'as') or following in (':', '->', 'in'):
                raise ValueError(f'Type name {name} has a shadowed or unresolved Kotlin binding')
            if previous in ('.', '?.'):
                # A property named obj.ci is not the class. Only the exact fully
                # qualified reviewed package can qualify a type here.
                cursor = index - 2
                qualifier = []
                while cursor >= 0:
                    qualifier.insert(0, code[cursor].text)
                    if cursor < 2 or code[cursor - 1].text != '.':
                        break
                    cursor -= 2
                if '.'.join(qualifier) != package_name:
                    continue
            edits.append((token.start, token.end, TYPE_NAMES[name]))
        elif (name in FIELD_NAMES and previous == '.' or name in METHOD_NAMES and previous in ('.', '::')) and index >= 2:
            owner = code[index - 2].text
            if name in METHOD_NAMES and previous != '::':
                if following != '(' or index + 2 >= len(code) or code[index + 2].text != ')':
                    continue
            if owner in ('ci', 'FarmGameEngine'):
                owner_index = index - 2
                if owner_index > 0 and code[owner_index - 1].text in ('.', '?.'):
                    cursor = owner_index - 2
                    qualifier = []
                    while cursor >= 0:
                        qualifier.insert(0, code[cursor].text)
                        if cursor < 2 or code[cursor - 1].text != '.':
                            break
                        cursor -= 2
                    if '.'.join(qualifier) != package_name:
                        continue
                edits.append((token.start, token.end, (FIELD_NAMES if name in FIELD_NAMES else METHOD_NAMES)[name]))
    result = source
    for start, end, replacement in reversed(edits):
        result = result[:start] + replacement + result[end:]
    return result, len(edits)


def migrate(source_root, kotlin_root, package_name, classpath, report, apply=False, tool_directory=None):
    source_root, kotlin_root, report = map(Path, (source_root, kotlin_root, report))
    kotlin_updates = []
    if kotlin_root.is_dir():
        for path in sorted(kotlin_root.glob('*.kt')):
            source = path.read_text(encoding='utf-8')
            if not re.search(r'^package\s+' + re.escape(package_name) + r'\s*$', source, re.MULTILINE):
                raise ValueError(f'Unexpected Kotlin package: {path}')
            changed, count = renamed_kotlin_text(source, package_name)
            if count:
                kotlin_updates.append((path, changed, count))
    tool_directory = Path(tool_directory or ROOT / '.local/verified-symbol-renamer')
    tool_directory.mkdir(parents=True, exist_ok=True)
    subprocess.run([str(jdk_tool('javac')), '-d', str(tool_directory), str(ROOT / 'tools/RenameVerifiedSymbols.java')], check=True)
    command = [str(jdk_tool('java')), '-Xmx2g', '-cp', str(tool_directory), 'RenameVerifiedSymbols',
               '--source-root', str(source_root), '--package', package_name,
               '--classpath', classpath, '--report', str(report)]
    if apply:
        command.append('--apply')
    subprocess.run(command, check=True)
    result = json.loads(report.read_text(encoding='utf-8'))
    result['kotlin_binding_policy'] = 'same-package reviewed Java type references; reject shadows/interpolation; qualified field owners only'
    result['kotlin_files'] = [{'source': path.name, 'guarded_reference_edits': count} for path, _, count in kotlin_updates]
    result['class_names'] = TYPE_NAMES
    result['engine_fields'] = FIELD_NAMES
    result['engine_methods_()I'] = METHOD_NAMES
    result['unresolved_retained'] = {'ci.bab': 'Second dirty flag has overlapping call sites; distinct original meaning not proven'}
    if apply:
        for path, changed, _ in kotlin_updates:
            path.write_text(changed, encoding='utf-8')
    report.write_text(json.dumps(result, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--source-root', type=Path, required=True)
    parser.add_argument('--kotlin-root', type=Path, required=True)
    parser.add_argument('--package', default='com.quanhoang.greenfarm3.S800x480')
    parser.add_argument('--classpath', required=True, help='Javac classpath of built Kotlin/SDK peers')
    parser.add_argument('--report', type=Path, required=True)
    parser.add_argument('--apply', action='store_true', help='Default is a resolving dry-run')
    args = parser.parse_args()
    migrate(args.source_root, args.kotlin_root, args.package, args.classpath, args.report, args.apply)


if __name__ == '__main__':
    main()
