"""Pin the test-only ASM lookup without downloads or global test skips."""

import hashlib
from pathlib import Path
import sys
import tempfile
import unittest
from unittest import mock


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
import asm_tools


class AsmToolsTest(unittest.TestCase):
    def setUp(self):
        temporary = tempfile.TemporaryDirectory(prefix='ASM lookup with spaces ')
        self.addCleanup(temporary.cleanup)
        self.root = Path(temporary.name)
        self.contents = b'fixture JAR contents; no execution'
        pin = mock.patch.object(asm_tools, 'ASM_SHA256', hashlib.sha256(self.contents).hexdigest())
        pin.start()
        self.addCleanup(pin.stop)

    def jar(self, path):
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(self.contents)
        return path

    def test_explicit_jar_is_verified_and_does_not_fall_back_on_mismatch(self):
        selected = self.jar(self.root / 'external ASM/asm.jar')
        self.assertEqual(asm_tools.asm_jar(environ={'GREENFARM_ASM_JAR': str(selected)}), selected)
        selected.write_bytes(b'changed JAR')
        with self.assertRaisesRegex(ValueError, 'SHA-256 mismatch'):
            asm_tools.asm_jar(environ={'GREENFARM_ASM_JAR': str(selected)})

    def test_gradle_user_home_and_default_user_cache_are_supported(self):
        for gradle_home, environ in ((self.root / 'Gradle Home', {'GRADLE_USER_HOME': str(self.root / 'Gradle Home')}),
                                     (self.root / '.gradle', {})):
            with self.subTest(gradle_home=str(gradle_home)):
                selected = self.jar(gradle_home / 'caches/modules-2/files-2.1/org.ow2.asm/asm/9.7.1'
                                    / 'cache-key/asm-9.7.1.jar')
                self.assertEqual(asm_tools.asm_jar(environ=environ, user_home=self.root), selected)
                selected.write_bytes(b'corrupt cached JAR')
                with self.assertRaisesRegex(ValueError, 'SHA-256 mismatch'):
                    asm_tools.asm_jar(environ=environ, user_home=self.root)

    def test_missing_jar_fails_with_actionable_cache_or_override_message(self):
        with self.assertRaisesRegex(ValueError, 'assembleDebug.*GREENFARM_ASM_JAR'):
            asm_tools.asm_jar(environ={}, user_home=self.root)
        with self.assertRaisesRegex(ValueError, 'does not exist'):
            asm_tools.asm_jar(environ={'GREENFARM_ASM_JAR': str(self.root / 'missing.jar')})


if __name__ == '__main__':
    unittest.main()
