package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
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
            synchronized(selectionLock) {
                if (restartState != IDLE || (language == FarmGameEngine.aYU && !ProgressSaveController.hasDeferredLanguage()) || !isSupported(language)) return
                requestedLanguage = language
                // Capture the CURRENT locale before the proposed choice is applied.
                currentLanguageDialogText = textFor(FarmGameEngine.aYU)
                selectionGeneration++
                selectionTicket = selectionGeneration
                restartState = CONFIRMING
            }
            // Saving starts on the next game frame; the success popup is shown
            // only after gameplay and the selected language header are durable.
            synchronized(selectionLock) {
                if (selectionTicket == selectionGeneration) restartState = ACCEPTED
            }
        }

        /** Pointer callbacks may arrive outside the game update. */
        @JvmStatic fun blocksGameplay(): Boolean = SaveTransferGate.isPreparingTransfer() ||
            (restartState != IDLE && !(GameNotification.isVisible() && FarmGameEngine.currentEngineState() == 20))

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

        private fun tutorialOptionsAvailable(): Boolean = synchronized(FarmGameEngine::class.java) {
            val currentEngineState = FarmGameEngine.currentEngineState()
            ProgressSaveController.isWorldReadyForMenu() && FarmGameEngine.aZB == 0 &&
                (FarmGameEngine.currentTutorialStepIndex > 0 || (FarmGameEngine.awV && FarmGameEngine.aYS > 0)) &&
                (currentEngineState == 18 || currentEngineState == 19) &&
                FarmGameEngine.avD == -1 && FarmGameEngine.apA == -1
        }

        /** Consume UI decisions on the game thread; do not serialize on the UI thread. */
        @JvmStatic fun frameStarted(): Boolean {
            if (SaveTransferGate.isPreparingTransfer()) {
                clearInput()
                return true
            }
            // Let engine20 finish its pending pop before saving/reopening a
            // notification. Otherwise a retry would stack another GUI4 modal.
            if (restartState != IDLE && !GameNotification.isVisible() &&
                FarmGameEngine.currentEngineState() == 20 && FarmGameEngine.apA != -1) {
                FarmGameEngine.qG()
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
                FarmGameEngine.fE(15)
            }
            if (action != IDLE && !GameNotification.isVisible()) clearInput()
            when (action) {
                ACCEPTED -> {
                    try {
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
            return action != IDLE && !GameNotification.isVisible()
        }

        private fun clearInput() {
            GameRuntime.lb()
            GameRuntime.lb()
            GameRuntime.lb()
            GameRuntime.la()
            FarmGameEngine.aAG = -1
            FarmGameEngine.aAH = -1
            FarmGameEngine.aAI = -1
            FarmGameEngine.aAJ = -1
            FarmGameEngine.aAK = -1
            FarmGameEngine.aZn = null
            FarmGameEngine.aZp = 0
            FarmGameEngine.aAi = false
            FarmGameEngine.aAj = false
        }

        private fun checkpointFinished(selectionTicket: Int, language: Int, success: Boolean) {
            synchronized(selectionLock) {
                if (selectionTicket != selectionGeneration || restartState != SAVING) return
                if (!success) {
                    restartState = IDLE
                    restartHandoff?.cancel()
                    restartHandoff = null
                } else {
                    // Leave the live locale/font packs untouched until restart.
                    ProgressSaveController.deferLanguageUntilRestart(language)
                    restartState = if (language == FarmGameEngine.aYU) IDLE else CONFIRMING
                }
            }
            if (success) {
                // Choosing the already-loaded locale cancels a deferred choice;
                // no restart or account operation is needed in that case.
                if (restartState == CONFIRMING) showSaved(selectionTicket)
            } else showFailure(selectionTicket, false)
        }

        private fun showSaved(selectionTicket: Int) {
            val text = currentLanguageDialogText!!
            GameNotification.show(text[0], text[1], text[2], text[3]) { restartNow ->
                synchronized(selectionLock) {
                    if (selectionTicket == selectionGeneration && restartState == CONFIRMING) {
                        restartState = if (restartNow) RETRY_RESTART else IDLE
                    }
                }
            }
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
            val text = currentLanguageDialogText ?: return
            synchronized(selectionLock) {
                if (selectionTicket != selectionGeneration || restartPromptVisible || GameNotification.isVisible()) return
                restartPromptVisible = true
            }
            GameNotification.show(text[0], text[if (saved) 6 else 5], text[4], text[3]) { retry ->
                synchronized(selectionLock) {
                    restartPromptVisible = false
                    if (selectionTicket == selectionGeneration) {
                        restartState = if (retry) {
                            if (saved) RETRY_RESTART else ACCEPTED
                        } else IDLE
                    }
                }
            }
        }

        private fun foregroundActivity(): Activity? = MIDlet.cnz ?: MIDlet.cnA

        @JvmStatic fun isSupported(language: Int): Boolean = when (language) {
            0, 1, 2, 3, 4, 5, 7, 9, 10, 11, 12, 15, 16, 20 -> true
            else -> false
        }

        /** The fourteen shipped text packs and the original fM(30) whitelist. */
        @JvmStatic fun textFor(language: Int): Array<String> = when (language) {
            1 -> arrayOf("Sprache ändern", "Die Sprache wurde gespeichert. Starte das Spiel neu, um sie anzuwenden. Dein Fortschritt wurde gespeichert.", "Neu starten", "Später", "Erneut versuchen", "Der Spielfortschritt konnte nicht gespeichert werden. Deine Sprache und die aktuelle Spielsitzung bleiben unverändert. Versuche es erneut.", "Dein Spielfortschritt wurde gespeichert, aber das Spiel konnte nicht neu gestartet werden. Versuche es erneut.")
            2 -> arrayOf("Changer de langue", "La langue a été enregistrée. Redémarrez le jeu pour l’appliquer. Votre progression a été sauvegardée.", "Redémarrer", "Plus tard", "Réessayer", "Impossible de sauvegarder la progression. La langue et la session de jeu en cours sont inchangées. Réessayez.", "Votre progression a été sauvegardée, mais le jeu n’a pas pu redémarrer. Réessayez.")
            3 -> arrayOf("Cambia lingua", "La lingua è stata salvata. Riavvia il gioco per applicarla. I tuoi progressi sono stati salvati.", "Riavvia", "Più tardi", "Riprova", "Impossibile salvare i progressi. La lingua e la sessione di gioco attuale sono rimaste invariate. Riprova.", "I tuoi progressi sono stati salvati, ma non è stato possibile riavviare il gioco. Riprova.")
            4 -> arrayOf("Cambiar idioma", "El idioma se ha guardado. Reinicia el juego para aplicarlo. Tu progreso está guardado.", "Reiniciar", "Más tarde", "Reintentar", "No se pudo guardar el progreso. El idioma y la sesión actual se han conservado. Inténtalo de nuevo.", "Tu progreso se ha guardado, pero no se pudo reiniciar el juego. Inténtalo de nuevo.")
            5 -> arrayOf("Alterar idioma", "O idioma foi salvo. Reinicie o jogo para aplicar a alteração. Seu progresso foi salvo.", "Reiniciar", "Mais tarde", "Tentar novamente", "Não foi possível salvar o progresso. O idioma e a sessão atual foram mantidos. Tente novamente.", "Seu progresso foi salvo, mas não foi possível reiniciar o jogo. Tente novamente.")
            7 -> arrayOf("言語の変更", "言語設定を保存しました。適用するにはゲームを再起動してください。進行状況は保存されています。", "再起動", "後で", "再試行", "進行状況を保存できませんでした。言語と現在のプレイ状態は変更されていません。もう一度お試しください。", "進行状況は保存されましたが、ゲームを再起動できませんでした。もう一度お試しください。")
            9 -> arrayOf("언어 변경", "언어 설정을 저장했습니다. 적용하려면 게임을 다시 시작하세요. 진행 상황은 저장되었습니다.", "다시 시작", "나중에", "다시 시도", "진행 상황을 저장하지 못했습니다. 언어와 현재 게임 상태가 그대로 유지됩니다. 다시 시도해 주세요.", "진행 상황은 저장되었지만 게임을 다시 시작하지 못했습니다. 다시 시도해 주세요.")
            10 -> arrayOf("Смена языка", "Язык сохранён. Перезапустите игру, чтобы применить его. Ваш прогресс сохранён.", "Перезапустить", "Позже", "Повторить", "Не удалось сохранить прогресс. Язык и текущий сеанс игры остались без изменений. Повторите попытку.", "Ваш прогресс сохранён, но не удалось перезапустить игру. Повторите попытку.")
            11 -> arrayOf("Dili değiştir", "Dil ayarı kaydedildi. Uygulamak için oyunu yeniden başlatın. İlerlemeniz kaydedildi.", "Yeniden başlat", "Daha sonra", "Tekrar dene", "İlerleme kaydedilemedi. Diliniz ve mevcut oyun oturumunuz korundu. Tekrar deneyin.", "İlerlemeniz kaydedildi ancak oyun yeniden başlatılamadı. Tekrar deneyin.")
            12 -> arrayOf("Zmiana języka", "Język został zapisany. Uruchom grę ponownie, aby go zastosować. Postęp został zapisany.", "Uruchom ponownie", "Później", "Spróbuj ponownie", "Nie udało się zapisać postępu. Język i bieżąca sesja gry pozostały bez zmian. Spróbuj ponownie.", "Twój postęp został zapisany, ale nie udało się ponownie uruchomić gry. Spróbuj ponownie.")
            15 -> arrayOf("เปลี่ยนภาษา", "บันทึกภาษาแล้ว กรุณาเริ่มเกมใหม่เพื่อใช้ภาษาใหม่ บันทึกความคืบหน้าแล้ว", "เริ่มใหม่", "ภายหลัง", "ลองอีกครั้ง", "ไม่สามารถบันทึกความคืบหน้าได้ ภาษาและการเล่นปัจจุบันของคุณยังคงเดิม โปรดลองอีกครั้ง", "ความคืบหน้าของคุณถูกบันทึกแล้ว แต่ไม่สามารถเริ่มเกมใหม่ได้ โปรดลองอีกครั้ง")
            16 -> arrayOf("Thay đổi ngôn ngữ", "Đã lưu thay đổi ngôn ngữ thành công. Bạn cần khởi động lại trò chơi để áp dụng thay đổi. Tiến độ nông trại đã được lưu.", "Khởi động lại", "Để sau", "Thử lại", "Không thể lưu tiến độ. Ngôn ngữ và phiên chơi hiện tại của bạn vẫn được giữ nguyên. Vui lòng thử lại.", "Tiến độ của bạn đã được lưu, nhưng không thể khởi động lại trò chơi. Vui lòng thử lại.")
            20 -> arrayOf("Cambiar idioma", "El idioma se guardó. Reinicia el juego para aplicarlo. Tu progreso está guardado.", "Reiniciar", "Más tarde", "Reintentar", "No se pudo guardar el progreso. El idioma y la sesión actual se conservaron. Inténtalo de nuevo.", "Tu progreso se guardó, pero no se pudo reiniciar el juego. Inténtalo de nuevo.")
            else -> arrayOf("Change language", "The language setting has been saved. Restart the game to apply it. Your farm progress has been saved.", "Restart now", "Later", "Retry", "Could not save progress. Your language and current session have been kept. Try again.", "Your progress has been saved, but the game could not restart. Try again.")
        }
    }
}
