"""Production restore diagnostics retain diagnosis without printing saved/user data."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool


class SaveRestoreDiagnosticsTest(unittest.TestCase):
    def test_wrapped_failures_log_types_codes_and_frames_without_messages(self):
        source = ROOT / 'app/src/main/java/com/quanhoang/greenfarm3/S800x480/SaveRestoreException.java'
        probe = r'''
import com.quanhoang.greenfarm3.S800x480.SaveRestoreException;
public class DiagnosticProbe {
  static void check(boolean value, String reason) { if (!value) throw new AssertionError(reason); }
  static class PrivateFailure extends IllegalStateException {
    @Override public String getMessage() { throw new AssertionError("message must never be inspected"); }
    @Override public String toString() { throw new AssertionError("throwable must never be formatted"); }
  }
  public static void main(String[] args) {
    String privateText = "password=do-not-log token=do-not-log cookie=do-not-log /private/player-save";
    Throwable root = new java.io.IOException(privateText);
    root.setStackTrace(new StackTraceElement[]{
      new StackTraceElement("game.RecordReader", "decode", privateText, 41)});
    SaveRestoreException wrapped = new SaveRestoreException(
      SaveRestoreException.Code.CHECKSUM_MISMATCH, privateText, root);
    String report = SaveRestoreException.diagnostics(wrapped, SaveRestoreException.Code.SQLITE_OPEN_FAILED);
    check(report.contains("result=CHECKSUM_MISMATCH"), "specific result code lost");
    check(report.contains("java.io.IOException"), "original exception type lost");
    check(report.contains("game.RecordReader.decode:41"), "failing method lost");
    for (String secret : new String[]{"do-not-log", "password", "token", "cookie", "/private/"})
      check(!report.contains(secret), "diagnostics leaked exception message or filename");
    check(SaveRestoreException.diagnostics(new PrivateFailure(), SaveRestoreException.Code.SQLITE_READ_FAILED)
      .contains("SQLITE_READ_FAILED"), "fallback diagnostic missing");
    Throwable first = new RuntimeException(privateText), second = new RuntimeException(privateText);
    first.initCause(second); second.initCause(first);
    check(SaveRestoreException.diagnostics(first, SaveRestoreException.Code.ENGINE_RESTORE_FAILED)
      .contains("cause_cycle=true"), "cyclic cause was not bounded");
    check(SaveRestoreException.recordsForLoadStep(38).equals("RMSHeader"), "header stage mapping");
    check(SaveRestoreException.recordsForLoadStep(44).equals("save_gameplay"), "gameplay stage mapping");
    check(SaveRestoreException.recordsForLoadStep(47).contains("save_farm0"), "farm stage mapping");
    System.out.println("PASS safe restore types/codes/methods, private message exclusion and bounded cause chain");
  }
}
'''
        with tempfile.TemporaryDirectory(prefix='restore-diagnostics-') as directory:
            folder = Path(directory)
            (folder / 'DiagnosticProbe.java').write_text(probe)
            compiled = subprocess.run([str(jdk_tool('javac')), '-d', str(folder), str(source),
                                       str(folder / 'DiagnosticProbe.java')], capture_output=True, text=True)
            self.assertEqual(compiled.returncode, 0, compiled.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', str(folder), 'DiagnosticProbe'],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stderr)
            self.assertIn('PASS safe restore', result.stdout)


if __name__ == '__main__':
    unittest.main()
