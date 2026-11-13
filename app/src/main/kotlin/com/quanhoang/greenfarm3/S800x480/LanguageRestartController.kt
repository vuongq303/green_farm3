package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Process
import android.os.SystemClock
import javax.microedition.midlet.MIDlet
import com.quanhoang.greenfarm3.migration.SaveTransferGate

/** A language choice becomes visible only after its progress checkpoint commits. */
class LanguageRestartController private constructor() {
    interface SaveCompletion {
        /** Called on the game thread after the writer finishes its transaction. */
        fun completed(success: Boolean)
    }

    private class RestartHandoff(private val alarms: AlarmManager, private val launch: PendingIntent) {
        fun cancel() = launch.cancel()

        fun restart() {
            val restartAtElapsedRealtimeMillis = SystemClock.elapsedRealtime() + 350L
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    alarms.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, restartAtElapsedRealtimeMillis, launch)
                } catch (restrictedExactAlarm: SecurityException) {
                    // Restart needs no exact-alarm permission prompt on new Android.
                    alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, restartAtElapsedRealtimeMillis, launch)
                }
            } else {
                alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, restartAtElapsedRealtimeMillis, launch)
            }
            // Finish every game/network worker and MIDlet static, not only the
            // Activity: recreating it would retain the old GloftGF2F.acw loop.
            Process.killProcess(Process.myPid())
        }

        companion object {
            fun prepare(activity: Activity?): RestartHandoff {
                if (activity == null || activity.isFinishing) throw IllegalStateException("No foreground game")
                val intent = Intent(Intent.ACTION_MAIN)
                    .addCategory(Intent.CATEGORY_LAUNCHER)
                    .setComponent(ComponentName(activity.packageName, "com.quanhoang.greenfarm3.Start"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                if (activity.packageManager.resolveActivity(intent, 0) == null) {
                    throw IllegalStateException("Game launcher is unavailable")
                }
                val alarms = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    ?: throw IllegalStateException("Restart service is unavailable")
                var flags = PendingIntent.FLAG_CANCEL_CURRENT
                if (Build.VERSION.SDK_INT >= 23) flags = flags or PendingIntent.FLAG_IMMUTABLE
                val launch = PendingIntent.getActivity(activity, 0x47464C, intent, flags)
                    ?: throw IllegalStateException("Cannot prepare game restart")
                return RestartHandoff(alarms, launch)
            }
        }
    }

    companion object {
        private const val IDLE = 0
        private const val CONFIRMING = 1
        private const val ACCEPTED = 2
        private const val SAVING = 3
        private const val RESTARTING = 4
        private const val RESTART_FAILED = 5
        private const val RETRY_RESTART = 6
        private val selectionLock = Any()
        @Volatile private var restartState = IDLE
        private var selectionGeneration = 0
        private var requestedLanguage = 0
        private var currentLanguageDialogText: Array<String>? = null
        private var restartHandoff: RestartHandoff? = null
        private var restartPromptVisible = false
        private var menuKeyHeld = false
        private var menuOpenRequested = false

        /** Only the interactive menu calls select; reading saved settings does not. */
        @JvmStatic fun select(language: Int) {
            val selectionTicket: Int
            val text: Array<String>
            synchronized(selectionLock) {
                if (restartState != IDLE || language == ci.aYU || !isSupported(language)) return
                requestedLanguage = language
                // Capture the CURRENT locale before the proposed choice is applied.
                currentLanguageDialogText = textFor(ci.aYU)
                text = currentLanguageDialogText!!
                selectionGeneration++
                selectionTicket = selectionGeneration
                restartState = CONFIRMING
            }
            val activity = foregroundActivity()
            if (activity == null) {
                cancel(selectionTicket)
                return
            }
            activity.runOnUiThread(Runnable {
                synchronized(selectionLock) {
                    if (selectionTicket != selectionGeneration || restartState != CONFIRMING) return@Runnable
                }
                if (activity.isFinishing) {
                    cancel(selectionTicket)
                    return@Runnable
                }
                try {
                    AlertDialog.Builder(activity)
                        .setTitle(text[0])
                        .setMessage(text[1])
                        .setPositiveButton(text[2], DialogInterface.OnClickListener { _, _ ->
                            synchronized(selectionLock) {
                                if (selectionTicket == selectionGeneration && restartState == CONFIRMING) restartState = ACCEPTED
                            }
                        })
                        .setNegativeButton(text[3], DialogInterface.OnClickListener { _, _ -> cancel(selectionTicket) })
                        .setOnCancelListener(DialogInterface.OnCancelListener { cancel(selectionTicket) })
                        .show()
                } catch (failure: RuntimeException) {
                    cancel(selectionTicket)
                }
            })
        }

        private fun cancel(selectionTicket: Int) {
            synchronized(selectionLock) {
                if (selectionTicket == selectionGeneration && restartState == CONFIRMING) restartState = IDLE
            }
        }

        /** Pointer callbacks may arrive outside the game update. */
        @JvmStatic fun blocksGameplay(): Boolean = restartState != IDLE || SaveTransferGate.isPreparingTransfer()

        /** Keep the existing settings reachable while tutorial HUD buttons are hidden. */
        @JvmStatic fun menuKeyDown(): Boolean {
            synchronized(selectionLock) {
                if (menuKeyHeld) return true
            }
            if (!tutorialOptionsAvailable()) return false
            synchronized(selectionLock) {
                if (restartState != IDLE) return false
                menuKeyHeld = true
                return true
            }
        }

        @JvmStatic fun menuKeyUp(): Boolean {
            synchronized(selectionLock) {
                if (!menuKeyHeld) return false
                menuKeyHeld = false
            }
            if (tutorialOptionsAvailable()) {
                synchronized(selectionLock) {
                    if (restartState == IDLE) menuOpenRequested = true
                }
            }
            return true
        }

        private fun tutorialOptionsAvailable(): Boolean = synchronized(ci::class.java) {
            val currentEngineState = ci.qD()
            ProgressSaveController.isWorldReadyForMenu() && ci.aZB == 0 &&
                (ci.aYR > 0 || (ci.awV && ci.aYS > 0)) &&
                (currentEngineState == 18 || currentEngineState == 19) &&
                ci.avD == -1 && ci.apA == -1
        }

        /** Consume UI decisions on the game thread; do not serialize on the UI thread. */
        @JvmStatic fun frameStarted(): Boolean {
            if (SaveTransferGate.isPreparingTransfer()) {
                clearInput()
                return true
            }
            val selectionTicket: Int
            val language: Int
            val action: Int
            val openMenu: Boolean
            synchronized(selectionLock) {
                selectionTicket = selectionGeneration
                language = requestedLanguage
                action = restartState
                openMenu = menuOpenRequested
                menuOpenRequested = false
                if (action == ACCEPTED) restartState = SAVING
                else if (action == RETRY_RESTART) restartState = RESTARTING
            }
            if (openMenu && action == IDLE && tutorialOptionsAvailable()) {
                clearInput()
                // Original Options engine15 pushes Language engine5 (GUI26).
                ci.fE(15)
            }
            if (action != IDLE) clearInput()
            when (action) {
                ACCEPTED -> {
                    try {
                        // Prepare the Android handoff BEFORE changing storage.
                        restartHandoff = RestartHandoff.prepare(foregroundActivity())
                        if (!ProgressSaveController.requestLanguageCheckpoint(language, object : SaveCompletion {
                            override fun completed(success: Boolean) = checkpointFinished(selectionTicket, language, success)
                        })) checkpointFinished(selectionTicket, language, false)
                    } catch (failure: RuntimeException) {
                        checkpointFinished(selectionTicket, language, false)
                    }
                }
                RETRY_RESTART -> restart(selectionTicket)
                RESTART_FAILED -> showFailure(selectionTicket, true)
            }
            return action != IDLE
        }

        private fun clearInput() {
            GLLib.lb()
            GLLib.lb()
            GLLib.lb()
            GLLib.la()
            ci.aAG = -1
            ci.aAH = -1
            ci.aAI = -1
            ci.aAJ = -1
            ci.aAK = -1
            ci.aZn = null
            ci.aZp = 0
            ci.aAi = false
            ci.aAj = false
        }

        private fun checkpointFinished(selectionTicket: Int, language: Int, success: Boolean) {
            synchronized(selectionLock) {
                if (selectionTicket != selectionGeneration || restartState != SAVING) return
                if (!success) {
                    restartState = IDLE
                    restartHandoff?.cancel()
                    restartHandoff = null
                } else {
                    TutorialFlowController.invalidateSession()
                    // Later lifecycle snapshots must match the committed header.
                    // Keep gameplay blocked; never reload old text packs in place.
                    ci.aYU = language
                    ci.aOD = language
                    ci.auy = language
                    restartState = RESTARTING
                }
            }
            if (success) restart(selectionTicket) else showFailure(selectionTicket, false)
        }

        private fun restart(selectionTicket: Int) {
            try {
                val preparedHandoff = restartHandoff ?: RestartHandoff.prepare(foregroundActivity()).also { restartHandoff = it }
                preparedHandoff.restart()
            } catch (failure: RuntimeException) {
                synchronized(selectionLock) {
                    if (selectionTicket != selectionGeneration) return
                    restartState = RESTART_FAILED
                }
                // A post-commit handoff failure retries only handoff, not rewards/save.
                showFailure(selectionTicket, true)
            }
        }

        private fun showFailure(selectionTicket: Int, saved: Boolean) {
            val activity = foregroundActivity() ?: return
            val text: Array<String>
            synchronized(selectionLock) {
                if (selectionTicket != selectionGeneration || (saved && restartPromptVisible)) return
                if (saved) restartPromptVisible = true
                text = currentLanguageDialogText!!
            }
            activity.runOnUiThread(Runnable {
                synchronized(selectionLock) {
                    if (selectionTicket != selectionGeneration || (if (saved) restartState != RESTART_FAILED else restartState != IDLE)) {
                        if (saved) restartPromptVisible = false
                        return@Runnable
                    }
                }
                if (activity.isFinishing) {
                    synchronized(selectionLock) { restartPromptVisible = false }
                    return@Runnable
                }
                try {
                    val builder = AlertDialog.Builder(activity)
                        .setTitle(text[0])
                        .setMessage(text[if (saved) 6 else 5])
                        .setPositiveButton(text[4], DialogInterface.OnClickListener { _, _ ->
                            synchronized(selectionLock) {
                                if (selectionTicket != selectionGeneration) return@OnClickListener
                                restartPromptVisible = false
                                if (saved && restartState == RESTART_FAILED) restartState = RETRY_RESTART
                                else if (!saved && restartState == IDLE) restartState = ACCEPTED
                            }
                        })
                    if (saved) builder.setCancelable(false)
                    else builder.setNegativeButton(text[3], null)
                    builder.show()
                } catch (failure: RuntimeException) {
                    synchronized(selectionLock) { restartPromptVisible = false }
                }
            })
        }

        private fun foregroundActivity(): Activity? = MIDlet.cnz ?: MIDlet.cnA

        @JvmStatic fun isSupported(language: Int): Boolean = when (language) {
            0, 1, 2, 3, 4, 5, 7, 9, 10, 11, 12, 15, 16, 20 -> true
            else -> false
        }

        /** The fourteen shipped text packs and the original fM(30) whitelist. */
        @JvmStatic fun textFor(language: Int): Array<String> = when (language) {
            1 -> arrayOf("Spiel neu starten?", "Wenn du die Sprache änderst, wird das Spiel neu gestartet. Dein Spielfortschritt wird gespeichert. Fortfahren?", "OK", "Abbrechen", "Erneut versuchen", "Der Spielfortschritt konnte nicht gespeichert werden. Deine Sprache und die aktuelle Spielsitzung bleiben unverändert. Versuche es erneut.", "Dein Spielfortschritt wurde gespeichert, aber das Spiel konnte nicht neu gestartet werden. Versuche es erneut.")
            2 -> arrayOf("Redémarrer le jeu ?", "Changer de langue redémarrera le jeu. Votre progression sera sauvegardée. Continuer ?", "OK", "Annuler", "Réessayer", "Impossible de sauvegarder la progression. La langue et la session de jeu en cours sont inchangées. Réessayez.", "Votre progression a été sauvegardée, mais le jeu n’a pas pu redémarrer. Réessayez.")
            3 -> arrayOf("Riavviare il gioco?", "Cambiare lingua riavvierà il gioco. I tuoi progressi verranno salvati. Continuare?", "OK", "Annulla", "Riprova", "Impossibile salvare i progressi. La lingua e la sessione di gioco attuale sono rimaste invariate. Riprova.", "I tuoi progressi sono stati salvati, ma non è stato possibile riavviare il gioco. Riprova.")
            4 -> arrayOf("¿Reiniciar el juego?", "Cambiar el idioma reiniciará el juego. Tu progreso se guardará. ¿Quieres continuar?", "Aceptar", "Cancelar", "Reintentar", "No se pudo guardar el progreso. El idioma y la sesión actual se han conservado. Inténtalo de nuevo.", "Tu progreso se ha guardado, pero no se pudo reiniciar el juego. Inténtalo de nuevo.")
            5 -> arrayOf("Reiniciar o jogo?", "Alterar o idioma reinicia o jogo. Seu progresso será salvo. Deseja continuar?", "OK", "Cancelar", "Tentar novamente", "Não foi possível salvar o progresso. O idioma e a sessão atual foram mantidos. Tente novamente.", "Seu progresso foi salvo, mas não foi possível reiniciar o jogo. Tente novamente.")
            7 -> arrayOf("ゲームを再起動しますか？", "言語を変更するとゲームが再起動します。進行状況は保存されます。続行しますか？", "OK", "キャンセル", "再試行", "進行状況を保存できませんでした。言語と現在のプレイ状態は変更されていません。もう一度お試しください。", "進行状況は保存されましたが、ゲームを再起動できませんでした。もう一度お試しください。")
            9 -> arrayOf("게임을 다시 시작할까요?", "언어를 변경하면 게임이 다시 시작됩니다. 진행 상황이 저장됩니다. 계속하시겠습니까?", "확인", "취소", "다시 시도", "진행 상황을 저장하지 못했습니다. 언어와 현재 게임 상태가 그대로 유지됩니다. 다시 시도해 주세요.", "진행 상황은 저장되었지만 게임을 다시 시작하지 못했습니다. 다시 시도해 주세요.")
            10 -> arrayOf("Перезапустить игру?", "При смене языка игра перезапустится. Ваш прогресс будет сохранён. Продолжить?", "ОК", "Отмена", "Повторить", "Не удалось сохранить прогресс. Язык и текущий сеанс игры остались без изменений. Повторите попытку.", "Ваш прогресс сохранён, но не удалось перезапустить игру. Повторите попытку.")
            11 -> arrayOf("Oyun yeniden başlatılsın mı?", "Dil değiştirildiğinde oyun yeniden başlatılır. İlerlemeniz kaydedilecektir. Devam edilsin mi?", "Tamam", "İptal", "Tekrar dene", "İlerleme kaydedilemedi. Diliniz ve mevcut oyun oturumunuz korundu. Tekrar deneyin.", "İlerlemeniz kaydedildi ancak oyun yeniden başlatılamadı. Tekrar deneyin.")
            12 -> arrayOf("Uruchomić grę ponownie?", "Zmiana języka spowoduje ponowne uruchomienie gry. Twój postęp zostanie zapisany. Kontynuować?", "OK", "Anuluj", "Spróbuj ponownie", "Nie udało się zapisać postępu. Język i bieżąca sesja gry pozostały bez zmian. Spróbuj ponownie.", "Twój postęp został zapisany, ale nie udało się ponownie uruchomić gry. Spróbuj ponownie.")
            15 -> arrayOf("เริ่มเกมใหม่หรือไม่?", "การเปลี่ยนภาษาจะเริ่มเกมใหม่ ความคืบหน้าของคุณจะถูกบันทึก ต้องการดำเนินการต่อหรือไม่?", "ตกลง", "ยกเลิก", "ลองอีกครั้ง", "ไม่สามารถบันทึกความคืบหน้าได้ ภาษาและการเล่นปัจจุบันของคุณยังคงเดิม โปรดลองอีกครั้ง", "ความคืบหน้าของคุณถูกบันทึกแล้ว แต่ไม่สามารถเริ่มเกมใหม่ได้ โปรดลองอีกครั้ง")
            16 -> arrayOf("Khởi động lại trò chơi?", "Đổi ngôn ngữ sẽ khởi động lại trò chơi. Tiến độ của bạn sẽ được lưu. Bạn có muốn tiếp tục?", "Đồng ý", "Hủy", "Thử lại", "Không thể lưu tiến độ. Ngôn ngữ và phiên chơi hiện tại của bạn vẫn được giữ nguyên. Vui lòng thử lại.", "Tiến độ của bạn đã được lưu, nhưng không thể khởi động lại trò chơi. Vui lòng thử lại.")
            20 -> arrayOf("¿Reiniciar el juego?", "Cambiar el idioma reiniciará el juego. Tu progreso se guardará. ¿Quieres continuar?", "Aceptar", "Cancelar", "Reintentar", "No se pudo guardar el progreso. El idioma y la sesión actual se conservaron. Inténtalo de nuevo.", "Tu progreso se guardó, pero no se pudo reiniciar el juego. Inténtalo de nuevo.")
            else -> arrayOf("Restart game?", "Changing the language restarts the game. Your progress will be saved. Continue?", "OK", "Cancel", "Retry", "Could not save progress. Your language and current session have been kept. Try again.", "Your progress has been saved, but the game could not restart. Try again.")
        }
    }
}
