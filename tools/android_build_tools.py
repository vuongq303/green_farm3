"""Resolve checked JDK/Android tools without batch files or shell parsing."""

from dataclasses import dataclass
import os
from pathlib import Path
import shutil
import subprocess


BUILD_TOOLS_VERSION = '35.0.0'


def executable_name(name, platform_name=None):
    platform_name = os.name if platform_name is None else platform_name
    return name + '.exe' if platform_name == 'nt' else name


def jdk_tool(name, *, java_home=None, platform_name=None):
    """Prefer JAVA_HOME, falling back to the platform executable on PATH."""
    if name not in ('java', 'javac', 'keytool'):
        raise ValueError('Unsupported JDK tool: ' + name)
    executable = executable_name(name, platform_name)
    home = os.environ.get('JAVA_HOME') if java_home is None else java_home
    if home:
        candidate = Path(home) / 'bin' / executable
        if candidate.is_file():
            return candidate
    found = shutil.which(executable)
    if found and Path(found).is_file() and Path(found).suffix.lower() not in ('.bat', '.cmd'):
        return Path(found)
    raise ValueError(f'JDK {executable} is required; set JAVA_HOME or add the JDK bin directory to PATH')


@dataclass(frozen=True)
class SigningTools:
    zipalign: Path
    apksigner: tuple


def signing_tools(sdk, *, java_home=None, platform_name=None):
    """Use the same apksigner JAR on Windows and Unix; never invoke .bat."""
    directory = Path(sdk) / 'build-tools' / BUILD_TOOLS_VERSION
    zipalign = directory / executable_name('zipalign', platform_name)
    signer_jar = directory / 'lib/apksigner.jar'
    for path in (zipalign, signer_jar):
        if not path.is_file():
            raise ValueError(f'Android SDK Build Tools {BUILD_TOOLS_VERSION} file is missing: {path}')
    java = jdk_tool('java', java_home=java_home, platform_name=platform_name)
    return SigningTools(zipalign, (str(java), '-jar', str(signer_jar)))


def ensure_debug_keystore(path, *, java_home=None, platform_name=None):
    """Create the reusable local debug key once; preserve an existing key."""
    keystore = Path(path).resolve()
    if keystore.is_file():
        return keystore
    if keystore.exists():
        raise ValueError('Debug keystore path is not a file: ' + str(keystore))
    keytool = jdk_tool('keytool', java_home=java_home, platform_name=platform_name)
    keystore.parent.mkdir(parents=True, exist_ok=True)
    subprocess.run([str(keytool), '-genkeypair', '-keystore', str(keystore),
                    '-storepass', 'android', '-alias', 'androiddebugkey', '-keypass', 'android',
                    '-keyalg', 'RSA', '-keysize', '2048', '-validity', '10000',
                    '-dname', 'CN=Android Debug,O=Android,C=US'], check=True)
    if not keystore.is_file():
        raise ValueError('JDK keytool did not create the debug keystore: ' + str(keystore))
    return keystore
