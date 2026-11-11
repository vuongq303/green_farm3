package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class zzfu implements Application.ActivityLifecycleCallbacks {
    private final Application zzagd;
    private final WeakReference<Application.ActivityLifecycleCallbacks> zzagv;
    private boolean zzagw = false;

    public zzfu(Application application, Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        this.zzagv = new WeakReference<>(activityLifecycleCallbacks);
        this.zzagd = application;
    }

    private final void zza(zzgc zzgcVar) {
        try {
            Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = this.zzagv.get();
            if (activityLifecycleCallbacks != null) {
                zzgcVar.zza(activityLifecycleCallbacks);
            } else if (!this.zzagw) {
                this.zzagd.unregisterActivityLifecycleCallbacks(this);
                this.zzagw = true;
            }
        } catch (Exception e) {
            zzakb.zzb("Error while dispatching lifecycle callback.", e);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        zza(new zzfv(this, activity, bundle));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        zza(new zzgb(this, activity));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        zza(new zzfy(this, activity));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        zza(new zzfx(this, activity));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zza(new zzga(this, activity, bundle));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
        zza(new zzfw(this, activity));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
        zza(new zzfz(this, activity));
    }
}
