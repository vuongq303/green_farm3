"""Locate a pinned, test/audit-only ASM JAR in the existing Gradle cache.

No download or Android packaging occurs here. AGP resolves this dependency when
the app is built; GREENFARM_ASM_JAR may select an existing verified copy.
"""

import hashlib
import os
from pathlib import Path


ASM_VERSION = '9.7.1'
# Retrieved over verified HTTPS from this Maven Central sidecar checksum:
ASM_CHECKSUM_URL = 'https://repo.maven.apache.org/maven2/org/ow2/asm/asm/9.7.1/asm-9.7.1.jar.sha256'
ASM_SHA256 = '8cadd43ac5eb6d09de05faecca38b917a040bb9139c7edeb4cc81c740b713281'


def verify_asm_jar(path):
    path = Path(path).expanduser().resolve()
    if not path.is_file():
        raise ValueError('ASM JAR does not exist: ' + str(path))
    if hashlib.sha256(path.read_bytes()).hexdigest() != ASM_SHA256:
        raise ValueError(f'ASM {ASM_VERSION} JAR SHA-256 mismatch: {path}')
    return path


def asm_jar(*, environ=None, user_home=None):
    environ = os.environ if environ is None else environ
    override = environ.get('GREENFARM_ASM_JAR')
    if override:
        return verify_asm_jar(override)
    home = Path.home() if user_home is None else Path(user_home)
    gradle_home = Path(environ.get('GRADLE_USER_HOME') or home / '.gradle').expanduser()
    cache = gradle_home / 'caches/modules-2/files-2.1/org.ow2.asm/asm' / ASM_VERSION
    candidates = sorted(cache.glob(f'*/asm-{ASM_VERSION}.jar'))
    if not candidates:
        raise ValueError(f'ASM {ASM_VERSION} JAR is missing from {cache}; run :app:assembleDebug '
                         'first or set GREENFARM_ASM_JAR to the verified JAR')
    return verify_asm_jar(candidates[0])
