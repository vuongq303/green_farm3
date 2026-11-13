package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.app.AlertDialog
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicLong
import javax.microedition.midlet.MIDlet

/** Game-thread snapshots, bounded batching, and one detached SQLite writer. */
object ProgressSaveController {
    private val requestedSequence = AtomicLong()
    private val completedWrites = ConcurrentLinkedQueue<AsyncSaveQueue.Result>()
    private val batchPolicy = SaveBatchPolicy()
    private var sqliteWriteQueue: AsyncSaveQueue? = null
    private var lastObservedRequestSequence = 0L
    private var durableRequestSequence = 0L
    private var lastCaptureUptimeMillis = 0L
    private var lastCaptureAttemptUptimeMillis = 0L
    private var pendingUnloadSaveSequence = -1L
    private var pendingUnloadAction = 0
    private var pendingUnloadTargetState = 0
    private var pendingUnloadStackDepth = 0
    private var snapshotBoundary = false
    private var forcedCheckpoint = false
    private var captureFailed = false
    @Volatile private var saveFailure = false
    @Volatile private var globalLoaded = false
    @Volatile private var ownFarmLoaded = false
    @Volatile private var worldReady = false
    @Volatile private var loadBlocked = false
    @Volatile private var retryLoad = false
    @Volatile private var loadError: String? = null
    private var failedLoadStep = -1
    private var promptVisible = false
    private var errorNoticeVisible = false
    private var languageCheckpoint: LanguageCheckpoint? = null
    private var captureCount = 0L
    private var totalCaptureNanos = 0L
    private var legacyCallbackSequence = 0L

    private class LanguageCheckpoint(
        val language: Int,
        val completion: LanguageRestartController.SaveCompletion
    ) {
        val previousLanguage = FarmGameEngine.aYU
        var sequence = 0L
        var preferencePhase = false
        var preferenceCommitted = false
        var preferencePublished = false
        var rollingBack = false
        var rollbackSequence = -1L
        var rollbackAttempt = 0L
    }

    private class LanguageRollback(val language: LanguageCheckpoint) : AsyncSaveQueue.RetryableCheckpoint

    @JvmStatic
    fun initialize() {
        sqliteWriteQueue?.let { previousWriter ->
            check(previousWriter.awaitIdle(10000L)) { "Previous save writer is still active" }
            previousWriter.close()
        }
        GameSaveStore.setWriteDispatcher(null)
        GameSaveStore.initialize()
        GameSaveStore.beginLoading()
        PendingRewardState.resetForLoad()
        EntityPoseState.resetForLoad()
        TutorialFlowController.invalidateSession()
        completedWrites.clear()
        sqliteWriteQueue = AsyncSaveQueue { result -> completedWrites.add(result) }
        retryLoad = false
        loadBlocked = false
        worldReady = false
        ownFarmLoaded = false
        globalLoaded = false
        loadError = null
        failedLoadStep = -1
        lastCaptureAttemptUptimeMillis = 0L
        lastCaptureUptimeMillis = 0L
        pendingUnloadSaveSequence = -1L
        captureFailed = false
        errorNoticeVisible = false
        saveFailure = false
        languageCheckpoint = null
        lastObservedRequestSequence = requestedSequence.get()
        durableRequestSequence = lastObservedRequestSequence
        legacyCallbackSequence = durableRequestSequence
        batchPolicy.captured()
    }

    @JvmStatic
    fun requestSave() {
        requestedSequence.incrementAndGet()
    }

    @JvmStatic fun atSnapshotBoundary(): Boolean = snapshotBoundary
    @JvmStatic fun isForcedCheckpoint(): Boolean = forcedCheckpoint
    @JvmStatic fun canSerialize(): Boolean = worldEligible() && GameSaveStore.isCaptureAvailable()

    /** The Android Menu key may expose existing settings during a tutorial. */
    @JvmStatic
    fun isWorldReadyForMenu(): Boolean = worldReady && globalLoaded && ownFarmLoaded && !loadBlocked &&
        languageCheckpoint == null

    private fun worldEligible(): Boolean {
        val engineState = FarmGameEngine.currentEngineState()
        return worldReady && !loadBlocked && FarmGameEngine.avD == -1 && engineState != -1 &&
            engineState != 1 && engineState != 2 && engineState != 4
    }

    @JvmStatic
    fun globalLoadFinished(existed: Boolean, loaded: Boolean) {
        check(!existed || loaded) { "Existing progress could not be restored; original save retained" }
        globalLoaded = true
    }

    @JvmStatic fun farmLoadFinished() { ownFarmLoaded = true }

    @JvmStatic
    fun enterWorld() {
        if (!globalLoaded || !ownFarmLoaded || loadBlocked) return
        try {
            GameSaveStore.finishLoading()
        } catch (failure: RuntimeException) {
            loadingFailed(failure)
            return
        }
        worldReady = true
        pendingUnloadSaveSequence = -1L
        GameSaveStore.setWriteDispatcher(object : GameSaveStore.WriteDispatcher {
            override fun changed(mutation: GameSaveStore.Snapshot) {
                val sequence = requestedSequence.incrementAndGet()
                // Detached auxiliary SDK/settings records share the same writer
                // without serializing the mutable farm on this callback thread.
                sqliteWriteQueue!!.submit(mutation, sequence, null)
            }
        })
        requestSave()
    }

    @JvmStatic
    fun allowLoadStep(): Boolean {
        if (!loadBlocked) return true
        if (!retryLoad) return false
        retryLoad = false
        return try {
            check(sqliteWriteQueue!!.awaitIdle(10000L)) { "Pending save has not completed" }
            GameSaveStore.setWriteDispatcher(null)
            GameSaveStore.close()
            GameSaveStore.initialize()
            GameSaveStore.beginLoading()
            if (failedLoadStep == 47) {
                FarmEntity.oL()
                FarmGameEngine.aZi = null
                FarmGameEngine.aZj = null
                FarmGameEngine.FN()
                EntityPoseState.resetForLoad()
                PendingRewardState.resetForLoad()
            }
            loadBlocked = false
            loadError = null
            true
        } catch (failure: RuntimeException) {
            loadingFailed(failedLoadStep, failure)
            false
        }
    }

    @JvmStatic fun isLoadBlocked(): Boolean = loadBlocked

    @JvmStatic
    fun isCriticalLoadFailure(step: Int, failure: Exception?): Boolean =
        step == 38 || step == 44 || step == 47 || GameSaveStore.status() == GameSaveStore.STATUS_FAILED

    @JvmStatic fun loadingFailed(failure: Throwable) { loadingFailed(-1, failure) }

    @JvmStatic
    fun loadingFailed(step: Int, failure: Throwable) {
        failedLoadStep = step
        loadBlocked = true
        worldReady = false
        loadError = "Không thể đọc tiến độ. Bản lưu cũ được giữ nguyên."
        Log.e("GreenFarmSave", "load_failed step=$step", failure)
        showLoadError()
    }

    @JvmStatic
    fun showStartupError() {
        val activity = foregroundActivity() ?: return
        activity.runOnUiThread {
            if (activity.isFinishing || promptVisible) return@runOnUiThread
            promptVisible = true
            AlertDialog.Builder(activity)
                .setTitle("Không thể mở dữ liệu trò chơi")
                .setMessage("Không thể đọc bản lưu. Dữ liệu không bị đặt lại.")
                .setCancelable(false)
                .setPositiveButton("Thử lại") { _, _ ->
                    promptVisible = false
                    GloftGF2F.acx?.mj()
                }.show()
        }
    }

    private fun showLoadError() {
        val activity = foregroundActivity() ?: return
        activity.runOnUiThread {
            if (activity.isFinishing || promptVisible) return@runOnUiThread
            promptVisible = true
            AlertDialog.Builder(activity)
                .setTitle("Không thể tải tiến độ")
                .setMessage(loadError)
                .setCancelable(false)
                .setPositiveButton("Thử lại") { _, _ ->
                    promptVisible = false
                    retryLoad = true
                }.show()
        }
    }

    /** Called once after completed simulation commands, never from a writer. */
    @JvmStatic
    fun frameFinished() {
        drainCompletions()
        if (worldReady && languageCheckpoint == null && sqliteWriteQueue!!.isIdle() &&
            !hasPendingChanges() && legacyCallbackSequence < durableRequestSequence) {
            legacyCallbackSequence = durableRequestSequence
            try {
                FarmGameEngine.progressSaveCommitted()
            } catch (legacyFailure: RuntimeException) {
                Log.w("GreenFarmSave", "legacy_save_callback_failed", legacyFailure)
            }
        }
        if (loadBlocked && retryLoad && failedLoadStep == -1 && globalLoaded && ownFarmLoaded) {
            try {
                if (!sqliteWriteQueue!!.isIdle()) return
                retryLoad = false
                GameSaveStore.reopen()
                GameSaveStore.finishLoading()
                loadBlocked = false
                loadError = null
                worldReady = true
                requestSave()
            } catch (failure: RuntimeException) {
                loadingFailed(-1, failure)
            }
        }
        if (loadBlocked) {
            showLoadError()
            return
        }
        val checkpoint = languageCheckpoint
        if (checkpoint != null && checkpoint.rollingBack && checkpoint.rollbackSequence < 0L &&
            SystemClock.uptimeMillis() - checkpoint.rollbackAttempt >= 1000L) {
            enqueueLanguageRollback(checkpoint)
        }
        if (!worldReady || languageCheckpoint != null) return
        val now = SystemClock.uptimeMillis()
        observeChanges(now)
        val periodic = !batchPolicy.isDirty() && now - lastCaptureUptimeMillis >= 8000L
        if (canSerialize() && (batchPolicy.due(now) || periodic) &&
            (!captureFailed || now - lastCaptureAttemptUptimeMillis >= 1000L)) {
            capture(false, null)
        }
    }

    private fun observeChanges(now: Long) {
        // Transfer dirty flags to a monotonic request before clearing them.
        // Commit A must never clear changes accepted while A was writing.
        if (FarmGameEngine.hasUnsavedGameChanges || FarmGameEngine.bab) {
            requestedSequence.incrementAndGet()
            FarmGameEngine.bab = false
            FarmGameEngine.hasUnsavedGameChanges = false
        }
        val current = requestedSequence.get()
        if (current != lastObservedRequestSequence) {
            batchPolicy.changed(now, current - lastObservedRequestSequence)
            lastObservedRequestSequence = current
        }
    }

    private fun capture(force: Boolean, checkpoint: Any?): Long {
        if (!canSerialize()) return -1L
        val sequence = requestedSequence.get()
        val startNanos = System.nanoTime()
        lastCaptureAttemptUptimeMillis = SystemClock.uptimeMillis()
        snapshotBoundary = true
        forcedCheckpoint = force
        val previousLanguage = FarmGameEngine.aYU
        try {
            if (checkpoint is LanguageCheckpoint && checkpoint.preferencePhase) {
                FarmGameEngine.aYU = checkpoint.language
            }
            GameSaveStore.beginCapture(FarmGameEngine.aZB == 0)
            if (FarmGameEngine.aZB == 0) FarmGameEngine.av(false) else FarmGameEngine.rs()
            val snapshot = GameSaveStore.finishCapture()
            sqliteWriteQueue!!.submit(snapshot, sequence, checkpoint)
            batchPolicy.captured()
            captureFailed = false
            lastCaptureUptimeMillis = SystemClock.uptimeMillis()
            captureCount++
            val durationNanos = System.nanoTime() - startNanos
            totalCaptureNanos += durationNanos
            Log.d("GreenFarmSave", "capture seq=$sequence us=${durationNanos / 1000L}" +
                " records=${snapshot.recordCount()} bytes=${snapshot.byteCount()}")
            return sequence
        } catch (failure: RuntimeException) {
            GameSaveStore.abortCapture()
            captureFailed = true
            saveFailed()
            Log.e("GreenFarmSave", "capture_failed", failure)
            return -1L
        } finally {
            FarmGameEngine.aYU = previousLanguage
            forcedCheckpoint = false
            snapshotBoundary = false
        }
    }

    private fun drainCompletions() {
        while (true) {
            val result = completedWrites.poll() ?: break
            Log.d("GreenFarmSave", "write seq=${result.sequence} us=${result.durationNanos / 1000L}" +
                " ok=${result.failure == null}")
            if (result.failure == null) {
                durableRequestSequence = maxOf(durableRequestSequence, result.sequence)
                errorNoticeVisible = false
                saveFailure = false
            } else {
                saveFailed()
                Log.e("GreenFarmSave", "write_failed; absolute snapshot retained for retry", result.failure)
            }
            val checkpoint = result.checkpoint
            if (checkpoint is LanguageRollback) {
                val language = checkpoint.language
                if (language === languageCheckpoint && result.failure == null) {
                    languageCheckpoint = null
                    if (worldReady) requestSave()
                    language.completion.completed(false)
                }
                continue
            }
            if (checkpoint is LanguageCheckpoint && checkpoint === languageCheckpoint) {
                val language = checkpoint
                if (result.failure != null) {
                    failLanguageCheckpoint(language)
                } else {
                    if (language.preferencePhase) language.preferenceCommitted = true
                    observeChanges(SystemClock.uptimeMillis())
                    if (!language.preferencePhase && requestedSequence.get() <= language.sequence) {
                        // Gameplay becomes durable before preference publication.
                        language.preferencePhase = true
                        language.sequence = enqueueLanguagePreference(language)
                        if (language.sequence < 0L) failLanguageCheckpoint(language)
                    } else if (requestedSequence.get() > language.sequence) {
                        // Genuine SDK callbacks accepted during the write must
                        // also become durable before restarting the session.
                        language.sequence = requestLanguageCapture(language)
                        if (language.sequence < 0L) failLanguageCheckpoint(language)
                    } else {
                        languageCheckpoint = null
                        language.completion.completed(true)
                    }
                }
            }
        }
    }

    private fun failLanguageCheckpoint(language: LanguageCheckpoint) {
        if (!language.preferencePhase && !language.preferenceCommitted) {
            languageCheckpoint = null
            requestSave()
            language.completion.completed(false)
            return
        }
        // An uncertain commit needs durable compensation before promising the
        // old-language session is safe to resume.
        language.rollingBack = true
        enqueueLanguageRollback(language)
    }

    private fun enqueueLanguageRollback(language: LanguageCheckpoint) {
        language.rollbackAttempt = SystemClock.uptimeMillis()
        try {
            val sequence = requestedSequence.incrementAndGet()
            sqliteWriteQueue!!.submit(GameSaveStore.captureLanguageSetting(language.previousLanguage),
                sequence, LanguageRollback(language))
            language.rollbackSequence = sequence
        } catch (failure: RuntimeException) {
            Log.e("GreenFarmSave", "language_rollback_capture_failed", failure)
            saveFailed()
        }
    }

    /** No routine saving/saved banner, overlay, touch target or per-action toast. */
    @JvmStatic
    fun saveFailed() {
        saveFailure = true
        if (errorNoticeVisible) return
        errorNoticeVisible = true
        val activity = foregroundActivity() ?: return
        activity.runOnUiThread {
            if (!activity.isFinishing) Toast.makeText(activity,
                "Progress could not be saved. Check free storage; retrying.", Toast.LENGTH_LONG).show()
        }
    }

    @JvmStatic
    fun checkpointBeforeMove() {
        synchronized(FarmGameEngine::class.java) {
            val previousMoveMode = FarmGameEngine.avD
            if (previousMoveMode == 3 && FarmGameEngine.avE == null) FarmGameEngine.avD = -1
            try {
                observeChanges(SystemClock.uptimeMillis())
                capture(true, null)
            } finally {
                FarmGameEngine.avD = previousMoveMode
            }
        }
    }

    @JvmStatic
    fun flushForLifecycle(): Boolean = synchronized(FarmGameEngine::class.java) {
        FarmGameEngine.cancelMovePreviewForInterruption()
        if (sqliteWriteQueue == null) return@synchronized true
        drainCompletions()
        if (languageCheckpoint == null && worldEligible()) {
            observeChanges(SystemClock.uptimeMillis())
            if (capture(true, null) < 0L) return@synchronized false
        }
        if (!sqliteWriteQueue!!.awaitIdle(10000L)) return@synchronized false
        drainCompletions()
        !saveFailure && sqliteWriteQueue!!.durableSequence() >= requestedSequence.get()
    }

    @JvmStatic
    fun prepareWorldUnload(): Boolean = synchronized(FarmGameEngine::class.java) {
        drainCompletions()
        if (loadBlocked || languageCheckpoint != null) return@synchronized false
        if (!worldReady) return@synchronized sqliteWriteQueue?.isIdle() ?: true
        if (pendingUnloadSaveSequence >= 0L && (pendingUnloadAction != FarmGameEngine.apA || pendingUnloadTargetState != FarmGameEngine.apz ||
                pendingUnloadStackDepth != FarmGameEngine.apB)) pendingUnloadSaveSequence = -1L
        if (pendingUnloadSaveSequence >= 0L) {
            observeChanges(SystemClock.uptimeMillis())
            if (!sqliteWriteQueue!!.isIdle() || sqliteWriteQueue!!.durableSequence() < pendingUnloadSaveSequence) return@synchronized false
            if (requestedSequence.get() <= pendingUnloadSaveSequence) return@synchronized true
        }
        FarmGameEngine.cancelMovePreviewForInterruption()
        if (!canSerialize()) return@synchronized false
        FarmEntity.oM()
        observeChanges(SystemClock.uptimeMillis())
        pendingUnloadSaveSequence = capture(true, null)
        pendingUnloadAction = FarmGameEngine.apA
        pendingUnloadTargetState = FarmGameEngine.apz
        pendingUnloadStackDepth = FarmGameEngine.apB
        if (!FarmGameEngine.pendingTransitionUnloadsWorld()) pendingUnloadSaveSequence = -1L
        false // Resume the pending command after its durable acknowledgement.
    }

    @JvmStatic
    fun worldUnloaded() {
        worldReady = false
        pendingUnloadSaveSequence = -1L
        GameSaveStore.setWriteDispatcher(null)
        TutorialFlowController.invalidateSession()
    }

    @JvmStatic
    fun requestLanguageCheckpoint(language: Int, completion: LanguageRestartController.SaveCompletion): Boolean {
        if (languageCheckpoint != null || loadBlocked || sqliteWriteQueue == null) return false
        val request = LanguageCheckpoint(language, completion)
        languageCheckpoint = request
        request.sequence = requestLanguageCapture(request)
        if (request.sequence < 0L) {
            if (request.preferencePublished) {
                // Rejected enqueue still needs compensation for its published
                // logical setting, keeping this barrier owned until durable.
                failLanguageCheckpoint(request)
                return true
            }
            languageCheckpoint = null
            return false
        }
        return true
    }

    private fun requestLanguageCapture(request: LanguageCheckpoint): Long {
        FarmGameEngine.cancelMovePreviewForInterruption()
        if (worldEligible()) {
            observeChanges(SystemClock.uptimeMillis())
            lastObservedRequestSequence = requestedSequence.incrementAndGet()
            return capture(true, request)
        }
        request.preferencePhase = true
        return enqueueLanguagePreference(request)
    }

    private fun enqueueLanguagePreference(request: LanguageCheckpoint): Long = try {
        val snapshot = GameSaveStore.captureLanguageSetting(request.language)
        request.preferencePublished = true
        val sequence = requestedSequence.incrementAndGet()
        lastObservedRequestSequence = sequence
        sqliteWriteQueue!!.submit(snapshot, sequence, request)
        sequence
    } catch (failure: RuntimeException) {
        Log.e("GreenFarmSave", "language_checkpoint_failed", failure)
        -1L
    }

    @JvmStatic fun captureCount(): Long = captureCount
    @JvmStatic fun captureNanos(): Long = totalCaptureNanos

    @JvmStatic
    fun hasPendingChanges(): Boolean = requestedSequence.get() > durableRequestSequence ||
        (sqliteWriteQueue?.let { !it.isIdle() } ?: false) || FarmGameEngine.hasUnsavedGameChanges || FarmGameEngine.bab

    private fun foregroundActivity(): Activity? = MIDlet.cnz ?: MIDlet.cnA
}
