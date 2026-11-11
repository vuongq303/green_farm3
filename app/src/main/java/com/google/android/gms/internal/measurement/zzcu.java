package com.google.android.gms.internal.measurement;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.e.a;
import com.google.android.gms.internal.measurement.zzcy;

/* JADX INFO: loaded from: classes.dex */
public final class zzcu<T extends Context & zzcy> {
    private static Boolean zzabi;
    private final Handler handler;
    private final T zzabh;

    public zzcu(T t) {
        bf.checkNotNull(t);
        this.zzabh = t;
        this.handler = new Handler();
    }

    private final void zzb(Runnable runnable) {
        zzat.zzc(this.zzabh).zzby().zza(new zzcx(this, runnable));
    }

    public static boolean zze(Context context) {
        bf.checkNotNull(context);
        if (zzabi != null) {
            return zzabi.booleanValue();
        }
        boolean zZzc = zzdd.zzc(context, "com.google.android.gms.analytics.AnalyticsService");
        zzabi = Boolean.valueOf(zZzc);
        return zZzc;
    }

    public final void onCreate() {
        zzat.zzc(this.zzabh).zzbu().zzq("Local AnalyticsService is starting up");
    }

    public final void onDestroy() {
        zzat.zzc(this.zzabh).zzbu().zzq("Local AnalyticsService is shutting down");
    }

    public final int onStartCommand(Intent intent, int i, final int i2) {
        try {
            synchronized (zzct.lock) {
                a aVar = zzct.zzabg;
                if (aVar != null && aVar.isHeld()) {
                    aVar.release();
                }
            }
        } catch (SecurityException e) {
        }
        final zzcm zzcmVarZzbu = zzat.zzc(this.zzabh).zzbu();
        if (intent == null) {
            zzcmVarZzbu.zzt("AnalyticsService started with null intent");
        } else {
            String action = intent.getAction();
            zzcmVarZzbu.zza("Local AnalyticsService called. startId, action", Integer.valueOf(i2), action);
            if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
                zzb(new Runnable(this, i2, zzcmVarZzbu) { // from class: com.google.android.gms.internal.measurement.zzcv
                    private final zzcu zzabj;
                    private final int zzabk;
                    private final zzcm zzabl;

                    {
                        this.zzabj = this;
                        this.zzabk = i2;
                        this.zzabl = zzcmVarZzbu;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        this.zzabj.zza(this.zzabk, this.zzabl);
                    }
                });
            }
        }
        return 2;
    }

    public final boolean onStartJob(final JobParameters jobParameters) {
        final zzcm zzcmVarZzbu = zzat.zzc(this.zzabh).zzbu();
        String string = jobParameters.getExtras().getString(NativeProtocol.WEB_DIALOG_ACTION);
        zzcmVarZzbu.zza("Local AnalyticsJobService called. action", string);
        if (!"com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(string)) {
            return true;
        }
        zzb(new Runnable(this, zzcmVarZzbu, jobParameters) { // from class: com.google.android.gms.internal.measurement.zzcw
            private final zzcu zzabj;
            private final zzcm zzabm;
            private final JobParameters zzabn;

            {
                this.zzabj = this;
                this.zzabm = zzcmVarZzbu;
                this.zzabn = jobParameters;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzabj.zza(this.zzabm, this.zzabn);
            }
        });
        return true;
    }

    final /* synthetic */ void zza(int i, zzcm zzcmVar) {
        if (this.zzabh.callServiceStopSelfResult(i)) {
            zzcmVar.zzq("Local AnalyticsService processed last dispatch request");
        }
    }

    final /* synthetic */ void zza(zzcm zzcmVar, JobParameters jobParameters) {
        zzcmVar.zzq("AnalyticsJobService processed last dispatch request");
        this.zzabh.zza(jobParameters, false);
    }
}
