"""Source-only language restart contracts; does not compile, build, or use ADB."""

import json
from pathlib import Path
import re
import unittest


ROOT = Path(__file__).resolve().parents[1]
CORE = ROOT / "app/src/main/java/com/gameloft/android/ANMP/GloftGF2F/S800x480"
GAME = (CORE / "ci.java").read_text(encoding="utf-8")
CONTROLLER = (ROOT / "app/src/main/kotlin/com/gameloft/android/ANMP/GloftGF2F/S800x480/LanguageRestartController.kt").read_text(encoding="utf-8")
LOCALES = {0: "EN", 1: "DE", 2: "FR", 3: "IT", 4: "ES", 5: "BR",
           7: "JP", 9: "KR", 10: "RU", 11: "TR", 12: "PL", 15: "TH",
           16: "VI", 20: "LATAM"}


def method(source, signature):
    """Get one method while ignoring braces inside Java strings and comments."""
    masked = re.sub(r'//[^\n]*|/\*.*?\*/|"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'',
                    lambda match: " " * len(match.group()), source, flags=re.S)
    start = source.index(signature)
    opening = masked.index("{", start)
    depth = 1
    for index in range(opening + 1, len(masked)):
        depth += (masked[index] == "{") - (masked[index] == "}")
        if depth == 0:
            return source[opening + 1:index]
    raise AssertionError("Unclosed method: " + signature)


class LanguageRestartSourceTests(unittest.TestCase):
    def test_every_menu_choice_defers_mutation(self):
        choices = re.findall(r"case (17039\d+):\s*LanguageRestartController.select\((\d+)\);\s*break;", GAME)
        self.assertEqual(len(choices), 14)
        self.assertEqual({int(language) for _, language in choices}, set(LOCALES))
        self.assertEqual(len({event for event, _ in choices}), 14)
        self.assertNotRegex(GAME, r"case 17039\d+:\s*aYU\s*=")

    def test_current_language_is_captured_before_confirmation(self):
        select = method(CONTROLLER, "fun select(language: Int)")
        self.assertIn("currentLanguageDialogText = textFor(ci.aYU)", select)
        self.assertNotIn("textFor(language)", select)
        self.assertNotRegex(select, r"ci\.aYU\s*=")
        self.assertIn("language == ci.aYU", select)
        self.assertNotIn("requestLanguageCheckpoint", select)
        cancel = method(CONTROLLER, "private fun cancel(selectionTicket: Int)")
        self.assertIn("restartState = IDLE", cancel)
        self.assertNotIn("ci.", cancel)

    def test_translations_match_shipped_text_packs(self):
        text = method(CONTROLLER, "fun textFor(language: Int)")
        rows = {0 if language == "else" else int(language): json.loads("[" + contents + "]")
                for language, contents in re.findall(r"^\s*(\d+|else) -> arrayOf\((.*?)\)\s*$", text, re.M)}
        self.assertEqual(set(rows), set(LOCALES))
        packs = {path.name for path in (ROOT / "app/src/main/assets/800x480").iterdir()
                 if re.fullmatch(r"[A-Z]+", path.name)}
        self.assertEqual(packs, set(LOCALES.values()))
        for language, values in rows.items():
            with self.subTest(language=language):
                self.assertEqual(len(values), 7)
                self.assertTrue(all(value.strip() for value in values))
                self.assertNotEqual(values[3], values[4])

    def test_success_is_the_only_place_that_changes_live_language(self):
        finished = method(CONTROLLER, "private fun checkpointFinished(selectionTicket: Int, language: Int, success: Boolean)")
        before_success, success = finished.split("} else {", 1)
        self.assertNotRegex(before_success, r"ci\.aYU\s*=")
        self.assertNotIn("TutorialFlowController.invalidateSession", before_success)
        self.assertIn("restartState = IDLE", before_success)
        self.assertIn("ci.aYU = language", success)
        self.assertIn("TutorialFlowController.invalidateSession", success)
        self.assertIn("if (success) restart(selectionTicket) else showFailure(selectionTicket, false)", finished)
        self.assertEqual(len(re.findall(r"ci\.aYU\s*=(?!=)", CONTROLLER)), 1)

    def test_only_game_thread_consumes_accept_and_retries(self):
        frame = method(CONTROLLER, "fun frameStarted(): Boolean")
        self.assertIn("if (action == ACCEPTED) restartState = SAVING", frame)
        self.assertLess(frame.index("RestartHandoff.prepare"), frame.index("requestLanguageCheckpoint"))
        self.assertIn("checkpointFinished(selectionTicket, language, success)", frame)
        self.assertIn("RETRY_RESTART -> restart(selectionTicket)", frame)
        self.assertNotIn("requestLanguageCheckpoint", method(CONTROLLER, "private fun restart(selectionTicket: Int)"))

    def test_pointer_events_freeze_and_android_cancel_still_cleans_up(self):
        for signature in ("protected void V(int i, int i2)", "protected void W(int i, int i2)"):
            self.assertTrue(method(GAME, signature).strip().startswith(
                "if (LanguageRestartController.blocksGameplay()) return;"))
        release = method(GAME, "protected void U(int i, int i2)")
        self.assertLess(release.index("i == -1 && i2 == -1"), release.index("LanguageRestartController.blocksGameplay()"))
        self.assertLess(release.index("LanguageRestartController.blocksGameplay()"), release.index("super.U(i, i2)"))

    def test_relaunch_removes_old_process_after_scheduling(self):
        restart = method(CONTROLLER, "fun restart()")
        self.assertLess(restart.index("alarms.setExact"), restart.index("Process.killProcess"))
        self.assertRegex(restart, r"catch\s*\([^)]*:\s*SecurityException\)")
        self.assertIn("alarms.set(", restart)
        self.assertIn('"com.gameloft.android.ANMP.GloftGF2F.Start"', CONTROLLER)
        self.assertIn("PendingIntent.FLAG_IMMUTABLE", CONTROLLER)
        self.assertNotIn(".recreate(", CONTROLLER)
        self.assertNotIn(".select(", method(GAME, "static void fM(int i)"))


if __name__ == "__main__":
    unittest.main()
