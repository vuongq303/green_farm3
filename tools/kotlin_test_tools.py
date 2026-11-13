"""Compile production Kotlin together with small Java test peers.

Kotlinc resolves the Java declarations first; javac then emits those Java peers
against the emitted Kotlin classes. This exercises the same JVM entry points as
Java/DEX callers, rather than translating Kotlin back into a test-only copy.
"""
from pathlib import Path
from functools import lru_cache
import os
import subprocess

from android_build_tools import jdk_tool

ROOT = Path(__file__).resolve().parents[1]
KOTLIN_VERSION = '2.4.21'


@lru_cache(maxsize=1)
def _libraries():
    configured = os.environ.get('KOTLIN_COMPILER_HOME')
    local = Path(configured) if configured else ROOT / '.local/kotlin-compiler'
    library_dir = local / 'lib'
    if (library_dir / f'kotlin-compiler-embeddable-{KOTLIN_VERSION}.jar').is_file():
        if not configured:
            from ensure_kotlin_compiler import ensure_compiler
            library_dir = ensure_compiler(ROOT) / 'lib'
        return sorted(library_dir.glob('*.jar'))
    cache = Path(os.environ.get('GRADLE_USER_HOME', Path.home() / '.gradle')) / 'caches/modules-2/files-2.1'
    dependencies = (
        ('org.jetbrains.kotlin', 'kotlin-compiler-embeddable', KOTLIN_VERSION),
        ('org.jetbrains.kotlin', 'kotlin-build-tools-api', KOTLIN_VERSION),
        ('org.jetbrains.kotlin', 'kotlin-stdlib', KOTLIN_VERSION),
        ('org.jetbrains.kotlin', 'kotlin-script-runtime', KOTLIN_VERSION),
        ('org.jetbrains.kotlin', 'kotlin-daemon-embeddable', KOTLIN_VERSION),
        ('org.jetbrains.kotlin', 'kotlin-reflect', '1.6.10'),
        ('org.jetbrains.kotlinx', 'kotlinx-coroutines-core-jvm', '1.8.0'),
        ('org.jetbrains', 'annotations', '13.0'),
    )
    libraries = []
    for group, artifact, version in dependencies:
        matches = sorted((cache / group / artifact / version).glob(f'*/{artifact}-{version}.jar'))
        if not matches:
            if configured:
                raise RuntimeError(f'Kotlin {KOTLIN_VERSION} compiler dependency {artifact}:{version} is missing; '
                                   'check KOTLIN_COMPILER_HOME')
            from ensure_kotlin_compiler import ensure_compiler
            return sorted((ensure_compiler(ROOT) / 'lib').glob('*.jar'))
        libraries.append(matches[0])
    return libraries


def kotlin_runtime_jars():
    libraries = _libraries()
    return [path for path in libraries if path.name == f'kotlin-stdlib-{KOTLIN_VERSION}.jar']


def runtime_classpath(output_dir, *additional_paths):
    return os.pathsep.join(str(path) for path in (output_dir, *kotlin_runtime_jars(), *additional_paths))


def compile_mixed(kotlin_sources, java_sources, output_dir, classpath=()):
    """Emit Kotlin and Java sources; raise with full diagnostics on either error."""
    kotlin_sources = list(map(Path, kotlin_sources))
    java_sources = list(map(Path, java_sources))
    output_dir = Path(output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)
    compile_classpath = runtime_classpath(output_dir, *classpath)
    if kotlin_sources:
        command = [str(jdk_tool('java')), '-cp', os.pathsep.join(map(str, _libraries())),
                   'org.jetbrains.kotlin.cli.jvm.K2JVMCompiler', '-no-stdlib', '-no-reflect',
                   '-jvm-target', '1.8', '-classpath', compile_classpath,
                   '-d', str(output_dir), *map(str, kotlin_sources), *map(str, java_sources)]
        result = subprocess.run(command, capture_output=True, text=True, encoding='utf-8', timeout=180)
        if result.returncode:
            raise AssertionError('Kotlin compilation failed:\n' + result.stdout + result.stderr)
    if java_sources:
        command = [str(jdk_tool('javac')), '-encoding', 'UTF-8', '-source', '8', '-target', '8',
                   '-cp', compile_classpath, '-d', str(output_dir), *map(str, java_sources)]
        result = subprocess.run(command, capture_output=True, text=True, encoding='utf-8', timeout=180)
        if result.returncode:
            raise AssertionError('Java peer compilation failed:\n' + result.stdout + result.stderr)
