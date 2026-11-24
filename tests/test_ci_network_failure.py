"""Execute the actual callback predicate against network errors with no message.

Only the predicate is isolated; loading the complete game class would initialize
Android/game state unrelated to failure classification. A mutation removing the
null guards must reproduce the crash, so this is not a source-presence check.
"""

from pathlib import Path
import subprocess
import sys
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
from game_source_context import legacy_core_source, current_source_path

sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool


def actual_predicate():
    source = legacy_core_source('ci')
    callback = source.index('public void a(int i, String str, String str2, int i2, String str3, Exception exc)')
    start = source.index('String exceptionMessage = exc.getMessage();', callback)
    declaration = source[start:source.index('\n', start)].strip()
    condition = source.index('if (', start) + len('if (')
    depth = 1
    end = condition
    while depth:
        if source[end] == '(':
            depth += 1
        elif source[end] == ')':
            depth -= 1
        end += 1
    return declaration, source[condition:end - 1]


class CINetworkFailureTest(unittest.TestCase):
    def test_actual_predicate_handles_null_messages_and_preserves_known_errors(self):
        declaration, condition = actual_predicate()
        guard = 'exceptionMessage != null && '
        first_mutant = condition.replace(guard, '', 1)
        second_mutant = ''.join(condition.rsplit(guard, 1))
        self.assertNotEqual(condition, first_mutant)
        self.assertNotEqual(condition, second_mutant)
        self.assertNotEqual(first_mutant, second_mutant)
        fixture = r'''
public class NetworkFailureProbe {
  static boolean current(Exception exc) { %DECL% return %CURRENT%; }
  static boolean firstMutant(Exception exc) { %DECL% return %FIRST_MUTANT%; }
  static boolean secondMutant(Exception exc) { %DECL% return %SECOND_MUTANT%; }
  static void check(Exception error, boolean expected) {
    if (current(error) != expected) throw new AssertionError(error.getClass().getName());
  }
  static void rejectsMutation(Exception error, boolean first) {
    try {
      if (first) firstMutant(error); else secondMutant(error);
    } catch (NullPointerException expected) { return; }
    throw new AssertionError("Missing null guard was not detected");
  }
  public static void main(String[] args) {
    check(new java.net.UnknownHostException((String)null), true);
    check(new java.net.ConnectException((String)null), true);
    check(new SecurityException((String)null), true);
    check(new java.net.SocketException((String)null), true);
    check(new java.io.IOException((String)null), false);
    check(new java.net.SocketTimeoutException((String)null), false);
    check(new RuntimeException((String)null), false);
    check(new java.io.IOException("Unknown error 7"), true);
    check(new Exception("Could not initialize network interface"), true);
    check(new Exception("Network not available"), true);
    check(new java.io.IOException("Other failure"), false);
    check(new java.net.SocketTimeoutException("Read timed out"), false);
    check(new Exception("Received authentication challenge is null"), false);
    rejectsMutation(new java.io.IOException((String)null), true);
    rejectsMutation(new java.net.SocketTimeoutException((String)null), false);
    System.out.println("PASS: 13 actual classifications; both lost-null-guard mutations detected");
  }
}
'''.replace('%DECL%', declaration).replace('%CURRENT%', condition).replace('%FIRST_MUTANT%', first_mutant).replace('%SECOND_MUTANT%', second_mutant)
        with tempfile.TemporaryDirectory(prefix='ci-network-failure-') as temporary:
            directory = Path(temporary)
            source = directory / 'NetworkFailureProbe.java'
            source.write_text(fixture)
            subprocess.run([str(jdk_tool('javac')), str(source)], check=True, capture_output=True, text=True)
            result = subprocess.run([str(jdk_tool('java')), '-cp', str(directory), 'NetworkFailureProbe'],
                                    check=True, capture_output=True, text=True)
        self.assertIn('PASS: 13 actual classifications', result.stdout)


if __name__ == '__main__':
    unittest.main()
