#!/usr/bin/env python3
"""Fetch the pinned JVM compiler used by source/ABI tests, never by the APK.

Gradle resolves its own Kotlin plugin. This small bootstrap makes direct JVM
regression fixtures reproducible on a clean checkout without an IDE install.
Only the Kotlin standard library is an Android runtime dependency; the compiler,
reflect and coroutines jars below remain host-side test tools under .local/.
"""

from __future__ import annotations

import concurrent.futures
import hashlib
from pathlib import Path
import tempfile
import urllib.request


KOTLIN_VERSION = '2.4.21'
ARTIFACTS = (
    ('org.jetbrains.kotlin', 'kotlin-compiler-embeddable', KOTLIN_VERSION,
     'ef19419c765e7ac8404465fa026ca8fc4dbb8a822de0fc79aa53c8dce29f1d02'),
    ('org.jetbrains.kotlin', 'kotlin-build-tools-api', KOTLIN_VERSION,
     'c5cd6a49bd072b529ae10809111c16a7aa0f92147e95d288354017fd65799461'),
    ('org.jetbrains.kotlin', 'kotlin-stdlib', KOTLIN_VERSION,
     'bd8250210584cb659847dce9cda660f8e9906e5def7fbebcea93dc6dcb6a88a3'),
    ('org.jetbrains.kotlin', 'kotlin-script-runtime', KOTLIN_VERSION,
     'a658cf5902a1525a06c264a5e42b437e97a6679ddeef8ca641246f270cf6a013'),
    ('org.jetbrains.kotlin', 'kotlin-daemon-embeddable', KOTLIN_VERSION,
     '7b3ef24674e5bcffcadfc4e43202ea314194939ecbcdd90a4770b2f1b04e0d07'),
    ('org.jetbrains.kotlin', 'kotlin-reflect', '1.6.10',
     '3277ac102ae17aad10a55abec75ff5696c8d109790396434b496e75087854203'),
    ('org.jetbrains.kotlinx', 'kotlinx-coroutines-core-jvm', '1.8.0',
     '9860906a1937490bf5f3b06d2f0e10ef451e65b95b269f22daf68a3d1f5065c5'),
    ('org.jetbrains', 'annotations', '13.0',
     'ace2a10dc8e2d5fd34925ecac03e4988b2c0f851650c94b8cef49ba1bd111478'),
)


def _sha256(path):
    digest = hashlib.sha256()
    with path.open('rb') as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b''):
            digest.update(block)
    return digest.hexdigest()


def ensure_compiler(project_root=None):
    """Return compiler home containing lib/*.jar after verifying every SHA-256."""
    root = Path(project_root or Path(__file__).resolve().parents[1])
    home = root / '.local/kotlin-compiler'
    library_dir = home / 'lib'
    library_dir.mkdir(parents=True, exist_ok=True)

    def fetch(coordinate):
        group, artifact, version, digest = coordinate
        filename = f'{artifact}-{version}.jar'
        destination = library_dir / filename
        if destination.is_file() and _sha256(destination) == digest:
            return destination
        url = ('https://repo.maven.apache.org/maven2/' + group.replace('.', '/')
               + f'/{artifact}/{version}/{filename}')
        with tempfile.NamedTemporaryFile(prefix=artifact + '-', suffix='.pending',
                                         dir=library_dir, delete=False) as temporary:
            pending = Path(temporary.name)
        try:
            # Use inherited HTTPS proxy and CA trust; no credentials or insecure
            # TLS overrides, and no executable downloaded from an arbitrary URL.
            with urllib.request.urlopen(url, timeout=60) as source, pending.open('wb') as target:
                for block in iter(lambda: source.read(1024 * 1024), b''):
                    target.write(block)
            if _sha256(pending) != digest:
                raise ValueError('Kotlin test tool SHA-256 mismatch: ' + filename)
            pending.replace(destination)
            return destination
        finally:
            pending.unlink(missing_ok=True)

    with concurrent.futures.ThreadPoolExecutor(max_workers=4) as pool:
        list(pool.map(fetch, ARTIFACTS))
    return home


if __name__ == '__main__':
    print(ensure_compiler())
