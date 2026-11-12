package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import javax.microedition.midlet.MIDlet;

/** Game-thread snapshots, bounded batching, and one detached SQLite writer. */
public final class ProgressSaveController {
    private static final AtomicLong requests = new AtomicLong();
    private static final ConcurrentLinkedQueue<AsyncSaveQueue.Result> completions =
            new ConcurrentLinkedQueue<AsyncSaveQueue.Result>();
    private static final SaveBatchPolicy batch = new SaveBatchPolicy();
    private static AsyncSaveQueue writer;
    private static long observedRequest;
    private static long savedRequest;
    private static long lastCapture;
    private static long lastAttempt;
    private static long unloadSequence = -1L;
    private static int unloadAction;
    private static int unloadTarget;
    private static int unloadDepth;
    private static boolean snapshotBoundary;
    private static boolean forcedCheckpoint;
    private static boolean captureFailed;
    private static volatile boolean saveFailure;
    private static volatile boolean globalLoaded;
    private static volatile boolean ownFarmLoaded;
    private static volatile boolean worldReady;
    private static volatile boolean loadBlocked;
    private static volatile boolean retryLoad;
    private static volatile String loadError;
    private static int failedLoadStep = -1;
    private static boolean promptVisible;
    private static boolean errorNoticeVisible;
    private static LanguageCheckpoint languageCheckpoint;
    private static long captureCount;
    private static long totalCaptureNanos;
    private static long acknowledgedEffects;

    private static final class LanguageCheckpoint {
        final int language;
        final int previousLanguage;
        final LanguageRestartController.SaveCompletion completion;
        long sequence;
        boolean preferencePhase;
        boolean preferenceCommitted;
        boolean preferencePublished;
        boolean rollingBack;
        long rollbackSequence = -1L;
        long rollbackAttempt;
        LanguageCheckpoint(int language, LanguageRestartController.SaveCompletion completion) {
            this.language = language;
            previousLanguage = ci.aYU;
            this.completion = completion;
        }
    }

    private static final class LanguageRollback implements AsyncSaveQueue.RetryableCheckpoint {
        final LanguageCheckpoint language;
        LanguageRollback(LanguageCheckpoint language) { this.language = language; }
    }

    private ProgressSaveController() {}

    public static void initialize() {
        if (writer != null) {
            if (!writer.awaitIdle(10000L)) throw new IllegalStateException("Previous save writer is still active");
            writer.close();
        }
        GameSaveStore.setWriteDispatcher(null);
        GameSaveStore.initialize();
        GameSaveStore.beginLoading();
        PendingRewardState.resetForLoad();
        EntityPoseState.resetForLoad();
        TutorialFlowController.invalidateSession();
        completions.clear();
        writer = new AsyncSaveQueue(new AsyncSaveQueue.Listener() {
            public void completed(AsyncSaveQueue.Result result) { completions.add(result); }
        });
        globalLoaded = ownFarmLoaded = worldReady = loadBlocked = retryLoad = false;
        loadError = null;
        failedLoadStep = -1;
        lastCapture = lastAttempt = 0L;
        unloadSequence = -1L;
        saveFailure = errorNoticeVisible = captureFailed = false;
        languageCheckpoint = null;
        savedRequest = observedRequest = requests.get();
        acknowledgedEffects = savedRequest;
        batch.captured();
    }

    public static void requestSave() { requests.incrementAndGet(); }
    public static boolean atSnapshotBoundary() { return snapshotBoundary; }
    public static boolean isForcedCheckpoint() { return forcedCheckpoint; }
    public static boolean canSerialize() { return worldEligible() && GameSaveStore.isCaptureAvailable(); }

    private static boolean worldEligible() {
        int state = ci.qD();
        return worldReady && !loadBlocked && ci.avD == -1
                && state != -1 && state != 1 && state != 2 && state != 4;
    }

    public static void globalLoadFinished(boolean existed, boolean loaded) {
        if (existed && !loaded) throw new IllegalStateException("Existing progress could not be restored; original save retained");
        globalLoaded = true;
    }

    public static void farmLoadFinished() { ownFarmLoaded = true; }

    public static void enterWorld() {
        if (!globalLoaded || !ownFarmLoaded || loadBlocked) return;
        try { GameSaveStore.finishLoading(); }
        catch (RuntimeException failure) { loadingFailed(failure); return; }
        worldReady = true;
        unloadSequence = -1L;
        GameSaveStore.setWriteDispatcher(new GameSaveStore.WriteDispatcher() {
            public void changed(GameSaveStore.Snapshot mutation) {
                long sequence = requests.incrementAndGet();
                // These are detached auxiliary SDK/settings records, not live
                // farm serialization. The same worker owns their SQLite writes.
                writer.submit(mutation, sequence, null);
            }
        });
        requestSave();
    }

    public static boolean allowLoadStep() {
        if (!loadBlocked) return true;
        if (!retryLoad) return false;
        retryLoad = false;
        try {
            if (!writer.awaitIdle(10000L)) throw new IllegalStateException("Pending save has not completed");
            GameSaveStore.setWriteDispatcher(null);
            GameSaveStore.close();
            GameSaveStore.initialize();
            GameSaveStore.beginLoading();
            if (failedLoadStep == 47) {
                ch.oL(); ci.aZi = null; ci.aZj = null; ci.FN();
                EntityPoseState.resetForLoad(); PendingRewardState.resetForLoad();
            }
            loadBlocked = false;
            loadError = null;
            return true;
        } catch (RuntimeException failure) { loadingFailed(failedLoadStep, failure); return false; }
    }

    public static boolean isLoadBlocked() { return loadBlocked; }
    public static boolean isCriticalLoadFailure(int step, Exception failure) {
        return step == 38 || step == 44 || step == 47 || GameSaveStore.status() == GameSaveStore.STATUS_FAILED;
    }
    public static void loadingFailed(Throwable failure) { loadingFailed(-1, failure); }
    public static void loadingFailed(int step, Throwable failure) {
        failedLoadStep = step; loadBlocked = true; worldReady = false;
        loadError = "Không thể đọc tiến độ. Bản lưu cũ được giữ nguyên.";
        Log.e("GreenFarmSave", "load_failed step=" + step, failure);
        showLoadError();
    }

    public static void showStartupError() {
        final Activity activity = foregroundActivity();
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() { public void run() {
            if (activity.isFinishing() || promptVisible) return;
            promptVisible = true;
            new AlertDialog.Builder(activity).setTitle("Không thể mở dữ liệu trò chơi")
                    .setMessage("Không thể đọc bản lưu. Dữ liệu không bị đặt lại.")
                    .setCancelable(false).setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            promptVisible = false;
                            if (GloftGF2F.acx != null) GloftGF2F.acx.mj();
                        }
                    }).show();
        }});
    }

    private static void showLoadError() {
        final Activity activity = foregroundActivity();
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() { public void run() {
            if (activity.isFinishing() || promptVisible) return;
            promptVisible = true;
            new AlertDialog.Builder(activity).setTitle("Không thể tải tiến độ").setMessage(loadError)
                    .setCancelable(false).setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { promptVisible = false; retryLoad = true; }
                    }).show();
        }});
    }

    /** Called once after completed simulation commands, never from a writer. */
    public static void frameFinished() {
        drainCompletions();
        if (worldReady && languageCheckpoint == null && writer.isIdle()
                && !hasPendingChanges() && acknowledgedEffects < savedRequest) {
            acknowledgedEffects = savedRequest;
            try { ci.progressSaveCommitted(); }
            catch (RuntimeException legacyFailure) { Log.w("GreenFarmSave", "legacy_save_callback_failed", legacyFailure); }
        }
        if (loadBlocked && retryLoad && failedLoadStep == -1 && globalLoaded && ownFarmLoaded) {
            try {
                if (!writer.isIdle()) return;
                retryLoad = false;
                GameSaveStore.reopen(); GameSaveStore.finishLoading();
                loadBlocked = false; loadError = null; worldReady = true; requestSave();
            } catch (RuntimeException failure) { loadingFailed(-1, failure); }
        }
        if (loadBlocked) { showLoadError(); return; }
        if (languageCheckpoint != null && languageCheckpoint.rollingBack
                && languageCheckpoint.rollbackSequence < 0L
                && SystemClock.uptimeMillis() - languageCheckpoint.rollbackAttempt >= 1000L) {
            enqueueLanguageRollback(languageCheckpoint);
        }
        if (!worldReady || languageCheckpoint != null) return;
        long now = SystemClock.uptimeMillis();
        observeChanges(now);
        boolean periodic = !batch.isDirty() && now - lastCapture >= 8000L;
        if (canSerialize() && (batch.due(now) || periodic)
                && (!captureFailed || now - lastAttempt >= 1000L)) capture(false, null);
    }

    private static void observeChanges(long now) {
        // Transfer dirty flags to a monotonic request before clearing them.
        // Commit A must never clear flags/resources changed while A was writing.
        if (ci.baa || ci.bab) {
            requests.incrementAndGet();
            ci.baa = ci.bab = false;
        }
        long current = requests.get();
        if (current != observedRequest) {
            batch.changed(now, current - observedRequest);
            observedRequest = current;
        }
    }

    private static long capture(boolean force, Object checkpoint) {
        if (!canSerialize()) return -1L;
        long sequence = requests.get();
        long start = System.nanoTime();
        lastAttempt = SystemClock.uptimeMillis();
        snapshotBoundary = true;
        forcedCheckpoint = force;
        int oldLanguage = ci.aYU;
        try {
            if (checkpoint instanceof LanguageCheckpoint
                    && ((LanguageCheckpoint) checkpoint).preferencePhase) {
                ci.aYU = ((LanguageCheckpoint) checkpoint).language;
            }
            GameSaveStore.beginCapture(ci.aZB == 0);
            if (ci.aZB == 0) ci.av(false); else ci.rs();
            GameSaveStore.Snapshot snapshot = GameSaveStore.finishCapture();
            writer.submit(snapshot, sequence, checkpoint);
            batch.captured();
            captureFailed = false;
            lastCapture = SystemClock.uptimeMillis();
            captureCount++;
            long nanos = System.nanoTime() - start;
            totalCaptureNanos += nanos;
            Log.d("GreenFarmSave", "capture seq=" + sequence + " us=" + nanos / 1000L
                    + " records=" + snapshot.recordCount() + " bytes=" + snapshot.byteCount());
            return sequence;
        } catch (RuntimeException failure) {
            GameSaveStore.abortCapture();
            captureFailed = true;
            saveFailed();
            Log.e("GreenFarmSave", "capture_failed", failure);
            return -1L;
        } finally {
            ci.aYU = oldLanguage;
            snapshotBoundary = forcedCheckpoint = false;
        }
    }

    private static void drainCompletions() {
        AsyncSaveQueue.Result result;
        while ((result = completions.poll()) != null) {
            Log.d("GreenFarmSave", "write seq=" + result.sequence + " us=" + result.durationNanos / 1000L
                    + " ok=" + (result.failure == null));
            if (result.failure == null) {
                savedRequest = Math.max(savedRequest, result.sequence);
                saveFailure = errorNoticeVisible = false;
            } else {
                saveFailed();
                Log.e("GreenFarmSave", "write_failed; absolute snapshot retained for retry", result.failure);
            }
            if (result.checkpoint instanceof LanguageRollback) {
                LanguageCheckpoint language = ((LanguageRollback) result.checkpoint).language;
                if (language == languageCheckpoint && result.failure == null) {
                    languageCheckpoint = null;
                    if (worldReady) requestSave();
                    language.completion.completed(false);
                }
                continue;
            }
            if (result.checkpoint instanceof LanguageCheckpoint && result.checkpoint == languageCheckpoint) {
                LanguageCheckpoint language = languageCheckpoint;
                if (result.failure != null) {
                    failLanguageCheckpoint(language);
                } else {
                    if (language.preferencePhase) language.preferenceCommitted = true;
                    observeChanges(SystemClock.uptimeMillis());
                    if (!language.preferencePhase && requests.get() <= language.sequence) {
                        // Gameplay is durable first. Change the preference only
                        // at the final barrier, never during earlier retries.
                        language.preferencePhase = true;
                        language.sequence = enqueueLanguagePreference(language);
                        if (language.sequence < 0L) failLanguageCheckpoint(language);
                    } else if (requests.get() > language.sequence) {
                        // A genuine SDK callback may arrive while SQLite is
                        // writing. Include its newly dirty values before restart.
                        language.sequence = requestLanguageCapture(language);
                        if (language.sequence < 0L) {
                            failLanguageCheckpoint(language);
                        }
                    } else {
                        languageCheckpoint = null;
                        language.completion.completed(true);
                    }
                }
            }
        }
    }

    private static void failLanguageCheckpoint(LanguageCheckpoint language) {
        if (!language.preferencePhase && !language.preferenceCommitted) {
            languageCheckpoint = null;
            requestSave();
            language.completion.completed(false);
            return;
        }
        // A commit error can have an uncertain outcome. Never promise the old
        // setting is restored until its compensating transaction is durable.
        language.rollingBack = true;
        enqueueLanguageRollback(language);
    }

    private static void enqueueLanguageRollback(LanguageCheckpoint language) {
        language.rollbackAttempt = SystemClock.uptimeMillis();
        try {
            long sequence = requests.incrementAndGet();
            writer.submit(GameSaveStore.captureLanguageSetting(language.previousLanguage),
                    sequence, new LanguageRollback(language));
            language.rollbackSequence = sequence;
        } catch (RuntimeException failure) {
            Log.e("GreenFarmSave", "language_rollback_capture_failed", failure);
            saveFailed();
        }
    }

    /** No routine saving/saved banner, overlay, touch target or per-action toast. */
    public static void saveFailed() {
        saveFailure = true;
        if (errorNoticeVisible) return;
        errorNoticeVisible = true;
        final Activity activity = foregroundActivity();
        if (activity != null) activity.runOnUiThread(new Runnable() { public void run() {
            if (!activity.isFinishing()) Toast.makeText(activity,
                    "Progress could not be saved. Check free storage; retrying.", Toast.LENGTH_LONG).show();
        }});
    }

    public static void checkpointBeforeMove() {
        synchronized (ci.class) {
            int mode = ci.avD;
            if (mode == 3 && ci.avE == null) ci.avD = -1;
            try { observeChanges(SystemClock.uptimeMillis()); capture(true, null); }
            finally { ci.avD = mode; }
        }
    }

    public static boolean flushForLifecycle() {
        synchronized (ci.class) {
            ci.cancelMovePreviewForInterruption();
            if (writer == null) return true;
            drainCompletions();
            if (languageCheckpoint == null && worldEligible()) {
                observeChanges(SystemClock.uptimeMillis());
                if (capture(true, null) < 0L) return false;
            }
            if (!writer.awaitIdle(10000L)) return false;
            drainCompletions();
            return !saveFailure && writer.durableSequence() >= requests.get();
        }
    }

    public static boolean prepareWorldUnload() {
        synchronized (ci.class) {
            drainCompletions();
            if (loadBlocked || languageCheckpoint != null) return false;
            if (!worldReady) return writer == null || writer.isIdle();
            if (unloadSequence >= 0L && (unloadAction != ci.apA
                    || unloadTarget != ci.apz || unloadDepth != ci.apB)) unloadSequence = -1L;
            if (unloadSequence >= 0L) {
                observeChanges(SystemClock.uptimeMillis());
                if (!writer.isIdle() || writer.durableSequence() < unloadSequence) return false;
                if (requests.get() <= unloadSequence) return true;
            }
            ci.cancelMovePreviewForInterruption();
            if (!canSerialize()) return false;
            ch.oM();
            observeChanges(SystemClock.uptimeMillis());
            unloadSequence = capture(true, null);
            unloadAction = ci.apA;
            unloadTarget = ci.apz;
            unloadDepth = ci.apB;
            if (!ci.pendingTransitionUnloadsWorld()) unloadSequence = -1L;
            return false; // Resume the pending command after its durable ack.
        }
    }

    public static void worldUnloaded() {
        worldReady = false;
        unloadSequence = -1L;
        GameSaveStore.setWriteDispatcher(null);
        TutorialFlowController.invalidateSession();
    }

    public static boolean requestLanguageCheckpoint(int language, LanguageRestartController.SaveCompletion completion) {
        if (languageCheckpoint != null || loadBlocked || writer == null) return false;
        LanguageCheckpoint request = new LanguageCheckpoint(language, completion);
        languageCheckpoint = request;
        request.sequence = requestLanguageCapture(request);
        if (request.sequence < 0L) {
            if (request.preferencePublished) {
                // A rejected enqueue must undo its accepted logical setting as
                // well; keep this barrier owned until compensation completes.
                failLanguageCheckpoint(request);
                return true;
            }
            languageCheckpoint = null;
            return false;
        }
        return true;
    }

    private static long requestLanguageCapture(LanguageCheckpoint request) {
        ci.cancelMovePreviewForInterruption();
        if (worldEligible()) {
            observeChanges(SystemClock.uptimeMillis());
            observedRequest = requests.incrementAndGet();
            return capture(true, request);
        }
        request.preferencePhase = true;
        return enqueueLanguagePreference(request);
    }

    private static long enqueueLanguagePreference(LanguageCheckpoint request) {
        try {
            GameSaveStore.Snapshot snapshot = GameSaveStore.captureLanguageSetting(request.language);
            request.preferencePublished = true;
            long sequence = requests.incrementAndGet();
            observedRequest = sequence;
            writer.submit(snapshot, sequence, request);
            return sequence;
        } catch (RuntimeException failure) { Log.e("GreenFarmSave", "language_checkpoint_failed", failure); return -1L; }
    }

    public static long captureCount() { return captureCount; }
    public static long captureNanos() { return totalCaptureNanos; }
    public static boolean hasPendingChanges() {
        return requests.get() > savedRequest || (writer != null && !writer.isIdle()) || ci.baa || ci.bab;
    }
    private static Activity foregroundActivity() { return MIDlet.cnz != null ? MIDlet.cnz : MIDlet.cnA; }
}
