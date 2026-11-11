package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.google.android.gms.common.d.s;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzgg {
    private final Object zzaho = new Object();

    @GuardedBy("mActivityTrackerLock")
    private zzgh zzahp = null;

    @GuardedBy("mActivityTrackerLock")
    private boolean zzahq = false;

    public final Activity getActivity() {
        Activity activity = null;
        synchronized (this.zzaho) {
            if (s.abv() && this.zzahp != null) {
                activity = this.zzahp.getActivity();
            }
        }
        return activity;
    }

    public final Context getContext() {
        Context context = null;
        synchronized (this.zzaho) {
            if (s.abv() && this.zzahp != null) {
                context = this.zzahp.getContext();
            }
        }
        return context;
    }

    public final void initialize(Context context) {
        synchronized (this.zzaho) {
            if (!this.zzahq) {
                if (!s.abv()) {
                    return;
                }
                if (!((Boolean) zzkb.zzik().zzd(zznk.zzayg)).booleanValue()) {
                    return;
                }
                Context applicationContext = context.getApplicationContext();
                if (applicationContext == null) {
                    applicationContext = context;
                }
                Application application = applicationContext instanceof Application ? (Application) applicationContext : null;
                if (application == null) {
                    zzakb.zzdk("Can not cast Context to Application");
                    return;
                }
                if (this.zzahp == null) {
                    this.zzahp = new zzgh();
                }
                this.zzahp.zza(application, context);
                this.zzahq = true;
            }
        }
    }

    public final void zza(zzgj zzgjVar) {
        synchronized (this.zzaho) {
            if (s.abv()) {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzayg)).booleanValue()) {
                    if (this.zzahp == null) {
                        this.zzahp = new zzgh();
                    }
                    this.zzahp.zza(zzgjVar);
                }
            }
        }
    }
}
