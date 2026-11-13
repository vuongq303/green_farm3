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
