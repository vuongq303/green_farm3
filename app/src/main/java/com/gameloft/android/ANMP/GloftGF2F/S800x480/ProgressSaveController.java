package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.concurrent.atomic.AtomicLong;
import javax.microedition.midlet.MIDlet;

/** Coordinates the original serializers at completed game-thread boundaries. */
public final class ProgressSaveController {
    private static final AtomicLong requests = new AtomicLong();
    private static long savedRequest;
    private static volatile long lastAttempt;
    private static long lastCommit;
    private static long snapshotRequest;
    private static long commitSequence;
    private static volatile boolean saveFailure;
    private static volatile boolean globalLoaded;
    private static volatile boolean ownFarmLoaded;
    private static volatile boolean worldReady;
    private static volatile boolean loadBlocked;
    private static volatile boolean retryLoad;
    private static volatile String loadError;
    private static int failedLoadStep = -1;
    private static boolean snapshotBoundary;
    private static boolean forcedCheckpoint;
    private static boolean promptVisible;
    private static TextView statusView;
    private static Activity statusActivity;
    private static String displayedStatus;

    private ProgressSaveController() {}

    public static void initialize() {
        GameSaveStore.initialize();
        GameSaveStore.beginLoading();
        PendingRewardState.resetForLoad();
        EntityPoseState.resetForLoad();
        globalLoaded = false;
        ownFarmLoaded = false;
        worldReady = false;
        loadBlocked = false;
        retryLoad = false;
        loadError = null;
        failedLoadStep = -1;
        displayedStatus = null;
        lastAttempt = 0;
        lastCommit = 0;
        saveFailure = false;
        savedRequest = requests.get();
    }

    public static void requestSave() { requests.incrementAndGet(); }

    public static boolean atSnapshotBoundary() { return snapshotBoundary; }

    public static boolean isForcedCheckpoint() { return forcedCheckpoint; }

    public static boolean canSerialize() {
        return worldEligible() && GameSaveStore.isReady();
    }

    private static boolean worldEligible() {
        int state = ci.qD();
        return worldReady && !loadBlocked
                && ci.avD == -1 && state != -1 && state != 1 && state != 2 && state != 4;
    }

    public static void globalLoadFinished(boolean existed, boolean loaded) {
        if (existed && !loaded) {
            throw new IllegalStateException("Existing progress could not be restored; original save retained");
        }
        globalLoaded = true; // A verified missing record is the new-player branch.
    }

    public static void farmLoadFinished() { ownFarmLoaded = true; }

    public static void enterWorld() {
        if (!globalLoaded || !ownFarmLoaded || loadBlocked) return;
        try { GameSaveStore.finishLoading(); }
        catch (RuntimeException failure) {
            loadingFailed(failure);
            return;
        }
        worldReady = true;
        requestSave();
    }

    public static boolean allowLoadStep() {
        if (!loadBlocked) return true;
        if (!retryLoad) return false;
        retryLoad = false;
        try {
            GameSaveStore.close();
            GameSaveStore.initialize();
            GameSaveStore.beginLoading();
            if (failedLoadStep == 47) {
                // Step 47 appends entities. A failed decode may already have
                // constructed part of the farm; discard that partial attempt
                // before running the same loader step again.
                ch.oL();
                ci.aZi = null;
                ci.aZj = null;
                ci.FN();
                EntityPoseState.resetForLoad();
                PendingRewardState.resetForLoad();
            }
            loadBlocked = false;
            loadError = null;
            return true;
        } catch (RuntimeException failure) {
            loadingFailed(failedLoadStep, failure);
            return false;
        }
    }

    public static boolean isLoadBlocked() { return loadBlocked; }

    public static boolean isCriticalLoadFailure(int step, Exception failure) {
        return step == 38 || step == 44 || step == 47
                || GameSaveStore.status() == GameSaveStore.STATUS_FAILED;
    }

    public static void loadingFailed(Throwable failure) {
        loadingFailed(-1, failure);
    }

    public static void loadingFailed(int step, Throwable failure) {
        failedLoadStep = step;
        loadBlocked = true;
        worldReady = false;
        loadError = "Không thể đọc tiến độ. Bản lưu cũ được giữ nguyên.";
        showLoadError();
    }

    public static void showStartupError() {
        final Activity activity = foregroundActivity();
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (activity.isFinishing() || promptVisible) return;
                promptVisible = true;
                new AlertDialog.Builder(activity)
                        .setTitle("Không thể mở dữ liệu trò chơi")
                        .setMessage("Không thể mở SQLite hoặc đọc bản lưu. Dữ liệu không bị đặt lại.")
                        .setCancelable(false)
                        .setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                promptVisible = false;
                                if (GloftGF2F.acx != null) GloftGF2F.acx.mj();
                            }
                        }).show();
            }
        });
    }

    private static void showLoadError() {
        final Activity activity = foregroundActivity();
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (activity.isFinishing() || promptVisible) return;
                promptVisible = true;
                new AlertDialog.Builder(activity)
                        .setTitle("Không thể tải tiến độ")
                        .setMessage(loadError)
                        .setCancelable(false)
                        .setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                promptVisible = false;
                                retryLoad = true;
                            }
                        }).show();
            }
        });
    }

    public static void frameFinished() {
        // Completion can fail after the last loader step changed qD from 4
        // to gameplay. That branch has no qV() call to consume a retry.
        if (loadBlocked && retryLoad && failedLoadStep == -1 && globalLoaded && ownFarmLoaded) {
            retryLoad = false;
            try {
                GameSaveStore.reopen();
                GameSaveStore.finishLoading();
                loadBlocked = false;
                loadError = null;
                worldReady = true;
                requestSave();
            } catch (RuntimeException failure) {
                loadingFailed(-1, failure);
            }
        }
        if (loadBlocked) { showLoadError(); return; }
        if (!worldReady) return;
        long now = SystemClock.uptimeMillis();
        boolean dirty = ci.baa || ci.bab || requests.get() != savedRequest;
        boolean timedCheckpoint = now - lastCommit >= 8000;
        if (worldEligible() && (dirty || timedCheckpoint) && now - lastAttempt >= 500
                && ((!saveFailure && GameSaveStore.status() != GameSaveStore.STATUS_FAILED) || now - lastAttempt >= 8000)) {
            if (recoverStoreForSave() && canSerialize()) saveAtBoundary();
        }
        updateStatus();
    }

    private static boolean saveAtBoundary() {
        return saveAtBoundary(false);
    }

    private static boolean saveAtBoundary(boolean force) {
        lastAttempt = SystemClock.uptimeMillis();
        snapshotRequest = requests.get();
        long before = commitSequence;
        snapshotBoundary = true;
        forcedCheckpoint = force;
        try {
            if (ci.aZB == 0) ci.av(false); else ci.rs();
        } finally { snapshotBoundary = false; forcedCheckpoint = false; }
        // A serializer can decline a checkpoint (for example while its legacy
        // reentrancy guard is set). That is not a completed durable save.
        return commitSequence != before && !saveFailure
                && GameSaveStore.status() != GameSaveStore.STATUS_FAILED;
    }

    private static boolean recoverStoreForSave() {
        if (GameSaveStore.isReady()) return true;
        lastAttempt = SystemClock.uptimeMillis();
        try {
            GameSaveStore.reopen();
            // RAM is the already-loaded, dirty world. Reopening storage must
            // not reinterpret that world as a new player or reload defaults.
            GameSaveStore.finishLoading();
            return true;
        } catch (RuntimeException failure) {
            saveFailed();
            return false;
        }
    }

    public static void committed() {
        commitSequence++;
        savedRequest = snapshotRequest;
        saveFailure = false;
        lastCommit = SystemClock.uptimeMillis();
        ci.baa = false;
        ci.bab = false;
        updateStatus();
    }

    public static void saveFailed() {
        saveFailure = true;
        requestSave();
        updateStatus();
    }

    /** Save the last confirmed pose before the game inserts a move preview. */
    public static void checkpointBeforeMove() {
        synchronized (ci.class) {
            // The move caller may select mode 3 immediately before assigning
            // avE. At this point no preview position has been changed yet.
            int mode = ci.avD;
            if (mode == 3 && ci.avE == null) ci.avD = -1;
            try { if (worldEligible() && recoverStoreForSave() && canSerialize()) saveAtBoundary(true); }
            finally { ci.avD = mode; }
        }
    }

    /** The caller drains completed commands first; an unconfirmed move is canceled. */
    public static boolean flushForLifecycle() {
        synchronized (ci.class) {
            ci.cancelMovePreviewForInterruption();
            if (!worldReady || loadBlocked) return true;
            if (!worldEligible()) return true;
            if (!recoverStoreForSave()) return false;
            if (!canSerialize()) return false;
            return saveAtBoundary(true);
        }
    }

    /** Keep the pending state-machine command intact until RAM is durable. */
    public static boolean prepareWorldUnload() {
        synchronized (ci.class) {
            if (loadBlocked) return false;
            if (!worldReady) return true;
            long now = SystemClock.uptimeMillis();
            boolean failed = saveFailure || GameSaveStore.status() == GameSaveStore.STATUS_FAILED;
            if (lastAttempt != 0 && now - lastAttempt < (failed ? 8000 : 500)) return false;
            ci.cancelMovePreviewForInterruption();
            if (!worldEligible()) return false;
            // jM(5) settles these effects before discarding the entity lists.
            // Settle them before the checkpoint as well; anX prevents a second
            // award in the original cleanup or after a failed-write retry.
            ch.oM();
            if (!recoverStoreForSave() || !canSerialize()) return false;
            return saveAtBoundary(true);
        }
    }

    /** Called only after the state machine actually discarded the world. */
    public static void worldUnloaded() {
        worldReady = false;
        // qV begins the protected read phase before its first loader step.
        // Until enterWorld completes, neither timer nor lifecycle may serialize
        // the empty/partially reconstructed entity lists.
    }

    public static void saving() { updateStatus(); }

    private static Activity foregroundActivity() {
        // Start is a launcher Activity; the running game lives in MIDlet.cnz.
        return MIDlet.cnz != null ? MIDlet.cnz : MIDlet.cnA;
    }

    private static void updateStatus() {
        int status = GameSaveStore.status();
        final String message;
        if (saveFailure || status == GameSaveStore.STATUS_FAILED) message = "Lưu thất bại · Chạm để thử lại";
        else if (status == GameSaveStore.STATUS_SAVING) message = "Đang lưu…";
        else if (requests.get() != savedRequest || ci.baa || ci.bab) message = "Chờ lưu…";
        else if (lastCommit != 0 && SystemClock.uptimeMillis() - lastCommit < 2500) message = "Đã lưu";
        else message = "";
        final Activity activity = foregroundActivity();
        if (activity == null) return;
        if (message.equals(displayedStatus) && statusActivity == activity) return;
        displayedStatus = message;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (activity.isFinishing()) return;
                View root = activity.getWindow().getDecorView();
                if (!(root instanceof FrameLayout)) return;
                if (statusView == null || statusActivity != activity) {
                    statusActivity = activity;
                    statusView = new TextView(activity);
                    statusView.setTextColor(Color.WHITE);
                    statusView.setTextSize(12);
                    statusView.setBackgroundColor(0xb0000000);
                    statusView.setPadding(8, 3, 8, 3);
                    statusView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            if (saveFailure || GameSaveStore.status() == GameSaveStore.STATUS_FAILED) {
                                lastAttempt = 0;
                                requestSave();
                            }
                        }
                    });
                    FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                    ((FrameLayout) root).addView(statusView, layout);
                }
                statusView.setText(message);
                statusView.setClickable(saveFailure || GameSaveStore.status() == GameSaveStore.STATUS_FAILED);
                statusView.setVisibility(message.length() == 0 ? View.GONE : View.VISIBLE);
            }
        });
    }
}
