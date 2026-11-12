"""Check Windows/Unix tool selection and argv preservation without executions."""

from pathlib import Path
import sys
import tempfile
from types import SimpleNamespace
import unittest
from unittest import mock


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
import android_build_tools as tools


class AndroidBuildToolsTest(unittest.TestCase):
    def setUp(self):
        temporary = tempfile.TemporaryDirectory(prefix='Android tools with spaces ')
        self.addCleanup(temporary.cleanup)
        self.root = Path(temporary.name)
        self.jdk = self.root / 'Java Home & JDK'
        self.sdk = self.root / 'Android SDK (local)'

    def file(self, path):
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(b'test placeholder; never execute')
        return path

    def sdk_files(self, platform_name):
        directory = self.sdk / 'build-tools/35.0.0'
        align = self.file(directory / ('zipalign.exe' if platform_name == 'nt' else 'zipalign'))
        signer = self.file(directory / 'lib/apksigner.jar')
        java = self.file(self.jdk / 'bin' / ('java.exe' if platform_name == 'nt' else 'java'))
        return align, signer, java

    def test_windows_and_unix_use_checked_native_zipalign_and_java_signer_jar(self):
        for platform_name in ('nt', 'posix'):
            with self.subTest(platform_name=platform_name):
                align, jar, java = self.sdk_files(platform_name)
                # Deliberately leave a tempting SDK launcher in place. The
                # returned command must use the JAR, including on Windows.
                self.file(jar.parent.parent / 'apksigner.bat')
                simulated_os = SimpleNamespace(name=platform_name, environ={'JAVA_HOME': str(self.jdk)})
                with mock.patch.object(tools, 'os', simulated_os), \
                        mock.patch.object(tools.shutil, 'which') as search:
                    selected = tools.signing_tools(self.sdk)
                self.assertEqual(selected.zipalign, align)
                self.assertEqual(selected.apksigner, (str(java), '-jar', str(jar)))
                self.assertFalse(any(value.lower().endswith(('.bat', '.cmd'))
                                     for value in selected.apksigner))
                search.assert_not_called()

    def test_java_home_and_path_fallback_select_the_platform_jdk_executable(self):
        windows_java = self.file(self.root / 'PATH JDK/bin/java.exe')
        with mock.patch.object(tools.os, 'environ', {'JAVA_HOME': str(self.jdk)}), \
                mock.patch.object(tools.shutil, 'which', return_value=str(windows_java)) as search:
            self.assertEqual(tools.jdk_tool('java', platform_name='nt'), windows_java)
            search.assert_called_once_with('java.exe')
        for name in ('java', 'javac', 'keytool'):
            with self.subTest(name=name):
                expected = self.file(self.jdk / 'bin' / (name + '.exe'))
                with mock.patch.object(tools.shutil, 'which') as search:
                    self.assertEqual(tools.jdk_tool(name, java_home=self.jdk, platform_name='nt'), expected)
                search.assert_not_called()

    def test_missing_sdk_files_and_jdk_fail_without_running_commands(self):
        align, jar, java = self.sdk_files('nt')
        for missing in (align, jar, java):
            with self.subTest(missing=missing.name):
                contents = missing.read_bytes()
                missing.unlink()
                with mock.patch.object(tools.shutil, 'which', return_value=None), \
                        mock.patch.object(tools.subprocess, 'run') as run:
                    with self.assertRaisesRegex(ValueError, 'is missing|JDK java.exe is required'):
                        tools.signing_tools(self.sdk, java_home=self.jdk, platform_name='nt')
                run.assert_not_called()
                missing.write_bytes(contents)
        launcher = self.file(self.root / 'untrusted-java.cmd')
        with mock.patch.object(tools.shutil, 'which', return_value=str(launcher)):
            with self.assertRaisesRegex(ValueError, 'JDK java.exe is required'):
                tools.jdk_tool('java', java_home=self.root / 'missing', platform_name='nt')

    def test_debug_key_creation_uses_argv_and_reuses_existing_key(self):
        keytool = self.file(self.jdk / 'bin/keytool.exe')
        keystore = self.root / 'project with spaces & chars/.local/debug.keystore'

        def create_key(command, **kwargs):
            self.assertIsInstance(command, list)
            self.assertEqual(command[0], str(keytool))
            self.assertEqual(command[command.index('-keystore') + 1], str(keystore.resolve()))
            self.assertEqual(kwargs, {'check': True})
            self.assertFalse(any(value.lower().endswith(('.bat', '.cmd')) for value in command))
            keystore.write_bytes(b'test key placeholder')

        with mock.patch.object(tools.subprocess, 'run', side_effect=create_key) as run:
            self.assertEqual(tools.ensure_debug_keystore(keystore, java_home=self.jdk,
                                                         platform_name='nt'), keystore.resolve())
            run.assert_called_once()
        before = keystore.read_bytes()
        with mock.patch.object(tools, 'jdk_tool') as resolver, \
                mock.patch.object(tools.subprocess, 'run') as run:
            tools.ensure_debug_keystore(keystore, platform_name='nt')
            resolver.assert_not_called()
            run.assert_not_called()
        self.assertEqual(keystore.read_bytes(), before)

    def test_success_without_a_created_key_is_rejected(self):
        self.file(self.jdk / 'bin/keytool')
        keystore = self.root / 'missing-output/debug.keystore'
        with mock.patch.object(tools.subprocess, 'run'):
            with self.assertRaisesRegex(ValueError, 'did not create'):
                tools.ensure_debug_keystore(keystore, java_home=self.jdk, platform_name='posix')


if __name__ == '__main__':
    unittest.main()
