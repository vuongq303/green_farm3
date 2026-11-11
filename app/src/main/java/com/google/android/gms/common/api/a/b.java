package com.google.android.gms.common.api.a;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class b implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final b bIW = new b();
    private final AtomicBoolean bIX = new AtomicBoolean();
    private final AtomicBoolean bIY = new AtomicBoolean();

    @GuardedBy("sInstance")
    private final ArrayList<c> bIZ = new ArrayList<>();

    @GuardedBy("sInstance")
    private boolean bJa = false;

    private b() {
    }

    public static b YT() {
        return bIW;
    }

    public static void a(Application application) {
        synchronized (bIW) {
            if (!bIW.bJa) {
                application.registerActivityLifecycleCallbacks(bIW);
                application.registerComponentCallbacks(bIW);
                bIW.bJa = true;
            }
        }
    }

    private final void onBackgroundStateChanged(boolean z) {
        synchronized (bIW) {
            ArrayList<c> arrayList = this.bIZ;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                c cVar = arrayList.get(i);
                i++;
                cVar.onBackgroundStateChanged(z);
            }
        }
    }

    public final void a(c cVar) {
        synchronized (bIW) {
            this.bIZ.add(cVar);
        }
    }

    public final boolean bY(boolean z) {
        if (!this.bIY.get()) {
            if (!com.google.android.gms.common.d.s.abx()) {
                return z;
            }
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (!this.bIY.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                this.bIX.set(true);
            }
        }
        return isInBackground();
    }

    public final boolean isInBackground() {
        return this.bIX.get();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean zCompareAndSet = this.bIX.compareAndSet(true, false);
        this.bIY.set(true);
        if (zCompareAndSet) {
            onBackgroundStateChanged(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        boolean zCompareAndSet = this.bIX.compareAndSet(true, false);
        this.bIY.set(true);
        if (zCompareAndSet) {
            onBackgroundStateChanged(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        if (i == 20 && this.bIX.compareAndSet(false, true)) {
            this.bIY.set(true);
            onBackgroundStateChanged(true);
        }
    }
}
