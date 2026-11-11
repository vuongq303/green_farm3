package com.gameloft.glads;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
class AndroidUtils {
    static Activity activity = null;
    static ViewGroup parentView = null;
    static int gameOrientation = -1;

    public static native boolean nativeIsUnityMode();

    public static native void nativeRestoreOrientation();

    public static native void nativeSetOrientation(int i);

    AndroidUtils() {
    }

    class BlockingRunnable implements Runnable {
        boolean finished = false;
        Runnable runnable;

        BlockingRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.runnable.run();
            this.finished = true;
        }

        public void Wait() {
            while (!this.finished) {
                try {
                    Thread.sleep(10L);
                } catch (Exception e) {
                }
            }
        }
    }

    public static void RunOnMainThread(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
            return;
        }
        BlockingRunnable blockingRunnable = new BlockingRunnable(runnable);
        new Handler(Looper.getMainLooper()).post(blockingRunnable);
        blockingRunnable.Wait();
    }

    public static void RunOnMainThreadAsync(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            GetParentView().post(runnable);
        }
    }

    static void SetParentView(ViewGroup viewGroup) {
        parentView = viewGroup;
    }

    static ViewGroup GetParentView() {
        return parentView;
    }

    static void SetActivity(Activity activity2) {
        activity = activity2;
        activity.setVolumeControlStream(3);
    }

    static Activity GetActivity() {
        return activity;
    }

    static Context GetContext() {
        return parentView.getContext();
    }

    public static void SetOrientation(final int i) {
        if (IsUnityMode()) {
            try {
                nativeSetOrientation(i);
                return;
            } catch (Exception e) {
                return;
            } catch (UnsatisfiedLinkError e2) {
                return;
            }
        }
        RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidUtils.1
            @Override // java.lang.Runnable
            public void run() {
                if (AndroidUtils.gameOrientation == -1) {
                    AndroidUtils.gameOrientation = AndroidUtils.GetActivity().getRequestedOrientation();
                }
                if (i == 2) {
                    AndroidUtils.GetActivity().setRequestedOrientation(6);
                } else if (i == 1) {
                    AndroidUtils.GetActivity().setRequestedOrientation(7);
                } else if (i == 0) {
                    AndroidUtils.GetActivity().setRequestedOrientation(10);
                }
            }
        });
    }

    public static void RestoreOrientation() {
        if (IsUnityMode()) {
            try {
                nativeRestoreOrientation();
                return;
            } catch (Exception e) {
                return;
            } catch (UnsatisfiedLinkError e2) {
                return;
            }
        }
        RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidUtils.2
            @Override // java.lang.Runnable
            public void run() {
                AndroidUtils.GetActivity().setRequestedOrientation(AndroidUtils.gameOrientation);
            }
        });
    }

    public static boolean IsUnityMode() {
        try {
            return nativeIsUnityMode();
        } catch (Exception e) {
            return false;
        } catch (UnsatisfiedLinkError e2) {
            return false;
        }
    }
}
