"""Exercise the actual Groovy URL validator shipped with the Gradle build."""
import os
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool


CASES = r'''
def validator = new GroovyClassLoader().parseClass(new File(args[0]))
assert validator.normalize('http://10.37.233.55:8787') == 'http://10.37.233.55:8787/'
assert validator.normalize('https://192.168.1.12/') == 'https://192.168.1.12/'
assert validator.normalize('HTTP://172.26.91.236:18787/') == 'http://172.26.91.236:18787/'
assert validator.normalize('https://8.8.8.8:443/') == 'https://8.8.8.8:443/'
def invalid = [null, '', 'http://localhost:8787/', 'http://0.0.0.0:8787/',
    'http://127.1.2.3/', 'http://10.0.2.2:8787/', 'http://169.254.1.2/',
    'http://224.0.0.1/', 'http://255.255.255.255/', 'http://1.2.3.256/',
    'http://1.2.03.4/', 'http://[::1]/', 'http://example.org/', 'ftp://10.37.233.55/',
    'http://10.37.233.55:0/', 'http://10.37.233.55:65536/', 'http://10.37.233.55:/',
    'http://secret:do-not-echo@10.37.233.55/', 'http://10.37.233.55/api/',
    'http://10.37.233.55/?q=1', 'http://10.37.233.55/#x', '//10.37.233.55/',
    'http://10.37.233.55\\evil/', 'http://10.37.233.55/ bad']
invalid.each { value ->
    try { validator.normalize(value); assert false: 'Unexpectedly accepted invalid URL' }
    catch (IllegalArgumentException error) {
        assert error.message.contains('BACKEND_BASE_URL')
        assert error.message.contains('root gradle.properties')
        assert !error.message.contains('do-not-echo')
    }
}
println('PASS: 4 canonical origins and 24 invalid/missing URL cases')
'''


class BackendBaseUrlTest(unittest.TestCase):
    def test_actual_gradle_validator_canonicalizes_origins_and_rejects_bad_configuration(self):
        homes = [Path(os.environ.get('GRADLE_USER_HOME', Path.home() / '.gradle')),
                 Path('/workspace/tooling/gradle-home')]
        jars = [jar for home in homes
                for jar in (home / 'wrapper/dists').glob('*/*/*/lib/groovy-[0-9]*.jar')]
        if not jars:
            self.skipTest('Requires the Groovy runtime from an installed Gradle Wrapper distribution')
        with tempfile.TemporaryDirectory(prefix='backend-base-url-') as directory:
            script = Path(directory) / 'cases.groovy'
            script.write_text(CASES)
            result = subprocess.run([str(jdk_tool('java')), '-cp', str(sorted(jars)[-1]),
                                     'groovy.ui.GroovyMain', str(script),
                                     str(ROOT / 'buildSrc/src/main/groovy/greenfarm/BackendBaseUrl.groovy')],
                                    capture_output=True, text=True, timeout=60)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertIn('PASS:', result.stdout)


if __name__ == '__main__':
    unittest.main()
