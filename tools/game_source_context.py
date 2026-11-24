"""Locate the current source checkpoint and adapt fixture-only compatibility names.

This helper never rewrites application source. Original APK identity is retained
separately for bytecode-reference comparisons. Symbol substitutions here apply
only to artificial test peers with known declarations.
"""
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
ORIGINAL_PACKAGE = 'com.gameloft.android.ANMP.GloftGF2F.S800x480'
TYPE_NAMES = {'ci': 'FarmGameEngine', 'ch': 'FarmEntity', 'GLLib': 'GameRuntime'}
OTHER_NAMES = {'aYR': 'currentTutorialStepIndex', 'baa': 'hasUnsavedGameChanges',
               'qD': 'currentEngineState', 'qE': 'previousEngineState'}


def game_source():
    java = ROOT / 'app/src/main/java'
    for package in ('com/quanhoang/greenfarm3/S800x480', ORIGINAL_PACKAGE.replace('.', '/')):
        for name in ('FarmGameEngine.java', 'ci.java'):
            path = java / package / name
            if path.is_file():
                return path
    raise FileNotFoundError('Current game engine source was not found')


def qualified_package():
    return re.search(r'^package\s+([\w.]+)\s*;', game_source().read_text(), re.M).group(1)


def core_source_directory():
    return game_source().parent


def kotlin_source_directory():
    return ROOT / 'app/src/main/kotlin' / qualified_package().replace('.', '/')


def uses_verified_names():
    return game_source().name == 'FarmGameEngine.java'


def type_name(original):
    return TYPE_NAMES.get(original, original) if uses_verified_names() else original


def adapt_fixture(source):
    source = source.replace(ORIGINAL_PACKAGE, qualified_package())
    if uses_verified_names():
        names = TYPE_NAMES | OTHER_NAMES
        source = re.sub(r'\b(?:' + '|'.join(map(re.escape, names)) + r')\b',
                        lambda token: names[token.group()], source)
    return source


def legacy_fixture_names(source):
    """Normalize only reviewed names for existing extraction/assertion helpers.

    The resulting text is a fixture input, never a source file. Composed Java
    peers are mapped forward again before compiling with real Kotlin classes.
    """
    if uses_verified_names():
        reverse = {value: key for key, value in (TYPE_NAMES | OTHER_NAMES).items()}
        source = re.sub(r'\b(?:' + '|'.join(map(re.escape, reverse)) + r')\b',
                        lambda token: reverse[token.group()], source)
    return source


def current_source_path(original):
    return core_source_directory() / (type_name(original) + '.java')


def current_compiled_path(original):
    return (ROOT / 'app/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes'
            / qualified_package().replace('.', '/') / (type_name(original) + '.class'))


def current_staged_path(original):
    return (ROOT / 'app/build/generated/core-java' / qualified_package().replace('.', '/')
            / (type_name(original) + '.java'))


def legacy_core_source(original):
    """Read current Java methods for an old-identity-only temporary JVM fixture.

    Only reviewed type/member identities and the app namespace are normalized.
    Algorithms, branches, literal values unrelated to that namespace and the
    original-bytecode oracle are preserved. This never writes application files.
    """
    source = legacy_fixture_names(current_source_path(original).read_text())
    current_base = qualified_package().rsplit('.', 1)[0]
    original_base = ORIGINAL_PACKAGE.rsplit('.', 1)[0]
    return source.replace(current_base, original_base)


def legacy_class_bytes(data):
    """Normalize emitted class owners for old-identity bytecode fixture scanners."""
    from relocate_compile_view import rewrite_class, OLD, NEW
    current_base = qualified_package().rsplit('.', 1)[0].replace('.', '/')
    if current_base == NEW:
        return rewrite_class(data, reverse=True, rename_symbols=uses_verified_names())
    if current_base == OLD and uses_verified_names():
        return rewrite_class(rewrite_class(data), reverse=True, rename_symbols=True)
    if current_base != OLD:
        raise AssertionError('Unexpected current source namespace: ' + current_base)
    return data
