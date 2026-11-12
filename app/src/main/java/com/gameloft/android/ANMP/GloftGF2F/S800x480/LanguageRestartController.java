package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import javax.microedition.midlet.MIDlet;

/** A language choice becomes visible only after its progress checkpoint commits. */
public final class LanguageRestartController {
    public interface SaveCompletion {
        /** Called on the game thread after the writer has finished its transaction. */
        void completed(boolean success);
    }

    private static final int IDLE = 0;
    private static final int CONFIRMING = 1;
    private static final int ACCEPTED = 2;
    private static final int SAVING = 3;
    private static final int RESTARTING = 4;
    private static final int RESTART_FAILED = 5;
    private static final int RETRY_RESTART = 6;
    private static final Object LOCK = new Object();
    private static volatile int state = IDLE;
    private static int generation;
    private static int selectedLanguage;
    private static String[] dialogText;
    private static RestartHandoff handoff;
    private static boolean restartPromptVisible;
    private static boolean menuHeld;
    private static boolean menuRequested;

    private LanguageRestartController() {}

    /** Only the interactive language menu calls this; loading a saved language does not. */
    public static void select(int language) {
        final int ticket;
        final String[] text;
        synchronized (LOCK) {
            if (state != IDLE || language == ci.aYU || !isSupported(language)) return;
            selectedLanguage = language;
            // Capture the CURRENT language before the proposed choice can be applied.
            dialogText = textFor(ci.aYU);
            text = dialogText;
            ticket = ++generation;
            state = CONFIRMING;
        }
        final Activity activity = foregroundActivity();
        if (activity == null) {
            cancel(ticket);
            return;
        }
        activity.runOnUiThread(new Runnable() {
            public void run() {
                synchronized (LOCK) {
                    if (ticket != generation || state != CONFIRMING) return;
                }
                if (activity.isFinishing()) {
                    cancel(ticket);
                    return;
                }
                try {
                    new AlertDialog.Builder(activity)
                            .setTitle(text[0])
                            .setMessage(text[1])
                            .setPositiveButton(text[2], new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    synchronized (LOCK) {
                                        if (ticket == generation && state == CONFIRMING) state = ACCEPTED;
                                    }
                                }
                            })
                            .setNegativeButton(text[3], new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) { cancel(ticket); }
                            })
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) { cancel(ticket); }
                            }).show();
                } catch (RuntimeException failure) {
                    cancel(ticket);
                }
            }
        });
    }

    private static void cancel(int ticket) {
        synchronized (LOCK) {
            if (ticket == generation && state == CONFIRMING) state = IDLE;
        }
    }

    /** Also guards pointer callbacks, which can arrive outside the game update. */
    public static boolean blocksGameplay() { return state != IDLE; }

    /** Keep the normal settings menu reachable while tutorial HUD buttons are hidden. */
    public static boolean menuKeyDown() {
        synchronized (LOCK) {
            if (menuHeld) return true;
        }
        if (!tutorialOptionsAvailable()) return false;
        synchronized (LOCK) {
            if (state != IDLE) return false;
            menuHeld = true;
            return true;
        }
    }

    public static boolean menuKeyUp() {
        synchronized (LOCK) {
            if (!menuHeld) return false;
            menuHeld = false;
        }
        if (tutorialOptionsAvailable()) {
            synchronized (LOCK) {
                if (state == IDLE) menuRequested = true;
            }
        }
        return true;
    }

    private static boolean tutorialOptionsAvailable() {
        synchronized (ci.class) {
            int current = ci.qD();
            return ProgressSaveController.isWorldReadyForMenu() && ci.aZB == 0
                    && (ci.aYR > 0 || (ci.awV && ci.aYS > 0))
                    && (current == 18 || current == 19)
                    && ci.avD == -1 && ci.apA == -1;
        }
    }

    /** Consumes UI decisions on the game thread; never serializes on the UI thread. */
    public static boolean frameStarted() {
        final int ticket;
        final int language;
        final int action;
        final boolean openMenu;
        synchronized (LOCK) {
            ticket = generation;
            language = selectedLanguage;
            action = state;
            openMenu = menuRequested;
            menuRequested = false;
            if (action == ACCEPTED) state = SAVING;
            else if (action == RETRY_RESTART) state = RESTARTING;
        }
        if (openMenu && action == IDLE && tutorialOptionsAvailable()) {
            clearInput();
            // Engine state 15 is Options; its existing Language row pushes
            // engine state 5 (GUI layer 26), with the original Back behavior.
            ci.fE(15);
        }
        if (action != IDLE) clearInput();
        if (action == ACCEPTED) {
            try {
                // Resolve the launcher and prepare its pending intent BEFORE changing storage.
                handoff = RestartHandoff.prepare(foregroundActivity());
                if (!ProgressSaveController.requestLanguageCheckpoint(language, new SaveCompletion() {
                    public void completed(boolean success) { checkpointFinished(ticket, language, success); }
                })) checkpointFinished(ticket, language, false);
            } catch (RuntimeException failure) {
                checkpointFinished(ticket, language, false);
            }
        } else if (action == RETRY_RESTART) {
            restart(ticket);
        } else if (action == RESTART_FAILED) {
            showFailure(ticket, true);
        }
        return action != IDLE;
    }

    private static void clearInput() {
        GLLib.lb();
        GLLib.lb();
        GLLib.lb();
        GLLib.la();
        ci.aAG = ci.aAH = ci.aAI = ci.aAJ = ci.aAK = -1;
        ci.aZn = null;
        ci.aZp = 0;
        ci.aAi = false;
        ci.aAj = false;
    }

    private static void checkpointFinished(int ticket, int language, boolean success) {
        synchronized (LOCK) {
            if (ticket != generation || state != SAVING) return;
            if (!success) {
                state = IDLE;
                if (handoff != null) handoff.cancel();
                handoff = null;
            } else {
                TutorialFlowController.invalidateSession();
                // Keep all later lifecycle snapshots consistent with the committed header.
                // Gameplay stays blocked, and old text packs are never reloaded in place.
                ci.aYU = language;
                ci.aOD = language;
                ci.auy = language;
                state = RESTARTING;
            }
        }
        if (success) restart(ticket); else showFailure(ticket, false);
    }

    private static void restart(int ticket) {
        try {
            if (handoff == null) handoff = RestartHandoff.prepare(foregroundActivity());
            handoff.restart();
        } catch (RuntimeException failure) {
            synchronized (LOCK) {
                if (ticket != generation) return;
                state = RESTART_FAILED;
            }
            // A post-commit Android handoff error retries only the handoff, never rewards or saving.
            showFailure(ticket, true);
        }
    }

    private static void showFailure(final int ticket, final boolean saved) {
        final Activity activity = foregroundActivity();
        if (activity == null) return;
        final String[] text;
        synchronized (LOCK) {
            if (ticket != generation || (saved && restartPromptVisible)) return;
            if (saved) restartPromptVisible = true;
            text = dialogText;
        }
        activity.runOnUiThread(new Runnable() {
            public void run() {
                synchronized (LOCK) {
                    if (ticket != generation || (saved ? state != RESTART_FAILED : state != IDLE)) {
                        if (saved) restartPromptVisible = false;
                        return;
                    }
                }
                if (activity.isFinishing()) {
                    synchronized (LOCK) { restartPromptVisible = false; }
                    return;
                }
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                            .setTitle(text[0])
                            .setMessage(text[saved ? 6 : 5])
                            .setPositiveButton(text[4], new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    synchronized (LOCK) {
                                        if (ticket != generation) return;
                                        restartPromptVisible = false;
                                        if (saved && state == RESTART_FAILED) state = RETRY_RESTART;
                                        else if (!saved && state == IDLE) state = ACCEPTED;
                                    }
                                }
                            });
                    if (saved) builder.setCancelable(false);
                    else builder.setNegativeButton(text[3], null);
                    builder.show();
                } catch (RuntimeException failure) {
                    synchronized (LOCK) { restartPromptVisible = false; }
                }
            }
        });
    }

    private static Activity foregroundActivity() {
        Activity activity = MIDlet.cnz;
        return activity != null ? activity : MIDlet.cnA;
    }

    private static final class RestartHandoff {
        private final AlarmManager alarms;
        private final PendingIntent launch;

        private RestartHandoff(AlarmManager alarms, PendingIntent launch) {
            this.alarms = alarms;
            this.launch = launch;
        }

        static RestartHandoff prepare(Activity activity) {
            if (activity == null || activity.isFinishing()) throw new IllegalStateException("No foreground game");
            Intent intent = new Intent(Intent.ACTION_MAIN)
                    .addCategory(Intent.CATEGORY_LAUNCHER)
                    .setComponent(new ComponentName(activity.getPackageName(),
                            "com.gameloft.android.ANMP.GloftGF2F.Start"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            if (activity.getPackageManager().resolveActivity(intent, 0) == null) {
                throw new IllegalStateException("Game launcher is unavailable");
            }
            AlarmManager alarms = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
            if (alarms == null) throw new IllegalStateException("Restart service is unavailable");
            int flags = PendingIntent.FLAG_CANCEL_CURRENT;
            if (Build.VERSION.SDK_INT >= 23) flags |= PendingIntent.FLAG_IMMUTABLE;
            PendingIntent launch = PendingIntent.getActivity(activity, 0x47464C, intent, flags);
            if (launch == null) throw new IllegalStateException("Cannot prepare game restart");
            return new RestartHandoff(alarms, launch);
        }

        void cancel() { launch.cancel(); }

        void restart() {
            long when = SystemClock.elapsedRealtime() + 350;
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    alarms.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, when, launch);
                } catch (SecurityException restrictedExactAlarm) {
                    // New Android versions may restrict exact alarms; restarting needs no permission prompt.
                    alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, when, launch);
                }
            } else {
                alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, when, launch);
            }
            // Relaunch only after every game/network worker and MIDlet static has died.
            // Recreating the Activity alone would leave GloftGF2F.acw and its old loop alive.
            Process.killProcess(Process.myPid());
        }
    }

    static boolean isSupported(int language) {
        switch (language) {
            case 0: case 1: case 2: case 3: case 4: case 5: case 7:
            case 9: case 10: case 11: case 12: case 15: case 16: case 20: return true;
            default: return false;
        }
    }

    /** Matches the fourteen shipped text packs and the fM(30) language whitelist. */
    static String[] textFor(int language) {
        switch (language) {
            case 1: return new String[]{"Spiel neu starten?", "Wenn du die Sprache änderst, wird das Spiel neu gestartet. Dein Spielfortschritt wird gespeichert. Fortfahren?", "OK", "Abbrechen", "Erneut versuchen", "Der Spielfortschritt konnte nicht gespeichert werden. Deine Sprache und die aktuelle Spielsitzung bleiben unverändert. Versuche es erneut.", "Dein Spielfortschritt wurde gespeichert, aber das Spiel konnte nicht neu gestartet werden. Versuche es erneut."};
            case 2: return new String[]{"Redémarrer le jeu ?", "Changer de langue redémarrera le jeu. Votre progression sera sauvegardée. Continuer ?", "OK", "Annuler", "Réessayer", "Impossible de sauvegarder la progression. La langue et la session de jeu en cours sont inchangées. Réessayez.", "Votre progression a été sauvegardée, mais le jeu n’a pas pu redémarrer. Réessayez."};
            case 3: return new String[]{"Riavviare il gioco?", "Cambiare lingua riavvierà il gioco. I tuoi progressi verranno salvati. Continuare?", "OK", "Annulla", "Riprova", "Impossibile salvare i progressi. La lingua e la sessione di gioco attuale sono rimaste invariate. Riprova.", "I tuoi progressi sono stati salvati, ma non è stato possibile riavviare il gioco. Riprova."};
            case 4: return new String[]{"¿Reiniciar el juego?", "Cambiar el idioma reiniciará el juego. Tu progreso se guardará. ¿Quieres continuar?", "Aceptar", "Cancelar", "Reintentar", "No se pudo guardar el progreso. El idioma y la sesión actual se han conservado. Inténtalo de nuevo.", "Tu progreso se ha guardado, pero no se pudo reiniciar el juego. Inténtalo de nuevo."};
            case 5: return new String[]{"Reiniciar o jogo?", "Alterar o idioma reinicia o jogo. Seu progresso será salvo. Deseja continuar?", "OK", "Cancelar", "Tentar novamente", "Não foi possível salvar o progresso. O idioma e a sessão atual foram mantidos. Tente novamente.", "Seu progresso foi salvo, mas não foi possível reiniciar o jogo. Tente novamente."};
            case 7: return new String[]{"ゲームを再起動しますか？", "言語を変更するとゲームが再起動します。進行状況は保存されます。続行しますか？", "OK", "キャンセル", "再試行", "進行状況を保存できませんでした。言語と現在のプレイ状態は変更されていません。もう一度お試しください。", "進行状況は保存されましたが、ゲームを再起動できませんでした。もう一度お試しください。"};
            case 9: return new String[]{"게임을 다시 시작할까요?", "언어를 변경하면 게임이 다시 시작됩니다. 진행 상황이 저장됩니다. 계속하시겠습니까?", "확인", "취소", "다시 시도", "진행 상황을 저장하지 못했습니다. 언어와 현재 게임 상태가 그대로 유지됩니다. 다시 시도해 주세요.", "진행 상황은 저장되었지만 게임을 다시 시작하지 못했습니다. 다시 시도해 주세요."};
            case 10: return new String[]{"Перезапустить игру?", "При смене языка игра перезапустится. Ваш прогресс будет сохранён. Продолжить?", "ОК", "Отмена", "Повторить", "Не удалось сохранить прогресс. Язык и текущий сеанс игры остались без изменений. Повторите попытку.", "Ваш прогресс сохранён, но не удалось перезапустить игру. Повторите попытку."};
            case 11: return new String[]{"Oyun yeniden başlatılsın mı?", "Dil değiştirildiğinde oyun yeniden başlatılır. İlerlemeniz kaydedilecektir. Devam edilsin mi?", "Tamam", "İptal", "Tekrar dene", "İlerleme kaydedilemedi. Diliniz ve mevcut oyun oturumunuz korundu. Tekrar deneyin.", "İlerlemeniz kaydedildi ancak oyun yeniden başlatılamadı. Tekrar deneyin."};
            case 12: return new String[]{"Uruchomić grę ponownie?", "Zmiana języka spowoduje ponowne uruchomienie gry. Twój postęp zostanie zapisany. Kontynuować?", "OK", "Anuluj", "Spróbuj ponownie", "Nie udało się zapisać postępu. Język i bieżąca sesja gry pozostały bez zmian. Spróbuj ponownie.", "Twój postęp został zapisany, ale nie udało się ponownie uruchomić gry. Spróbuj ponownie."};
            case 15: return new String[]{"เริ่มเกมใหม่หรือไม่?", "การเปลี่ยนภาษาจะเริ่มเกมใหม่ ความคืบหน้าของคุณจะถูกบันทึก ต้องการดำเนินการต่อหรือไม่?", "ตกลง", "ยกเลิก", "ลองอีกครั้ง", "ไม่สามารถบันทึกความคืบหน้าได้ ภาษาและการเล่นปัจจุบันของคุณยังคงเดิม โปรดลองอีกครั้ง", "ความคืบหน้าของคุณถูกบันทึกแล้ว แต่ไม่สามารถเริ่มเกมใหม่ได้ โปรดลองอีกครั้ง"};
            case 16: return new String[]{"Khởi động lại trò chơi?", "Đổi ngôn ngữ sẽ khởi động lại trò chơi. Tiến độ của bạn sẽ được lưu. Bạn có muốn tiếp tục?", "Đồng ý", "Hủy", "Thử lại", "Không thể lưu tiến độ. Ngôn ngữ và phiên chơi hiện tại của bạn vẫn được giữ nguyên. Vui lòng thử lại.", "Tiến độ của bạn đã được lưu, nhưng không thể khởi động lại trò chơi. Vui lòng thử lại."};
            case 20: return new String[]{"¿Reiniciar el juego?", "Cambiar el idioma reiniciará el juego. Tu progreso se guardará. ¿Quieres continuar?", "Aceptar", "Cancelar", "Reintentar", "No se pudo guardar el progreso. El idioma y la sesión actual se conservaron. Inténtalo de nuevo.", "Tu progreso se guardó, pero no se pudo reiniciar el juego. Inténtalo de nuevo."};
            case 0:
            default: return new String[]{"Restart game?", "Changing the language restarts the game. Your progress will be saved. Continue?", "OK", "Cancel", "Retry", "Could not save progress. Your language and current session have been kept. Try again.", "Your progress has been saved, but the game could not restart. Try again."};
        }
    }
}
