package com.google.android.gms.internal.measurement;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.measurement.zzjj;

/* JADX INFO: loaded from: classes.dex */
public final class zzjf<T extends Context & zzjj> {
    private final T zzabh;

    public zzjf(T t) {
        bf.checkNotNull(t);
        this.zzabh = t;
    }

    public static boolean zza(Context context, boolean z) {
        bf.checkNotNull(context);
        return Build.VERSION.SDK_INT >= 24 ? zzjv.zzc(context, "com.google.android.gms.measurement.AppMeasurementJobService") : zzjv.zzc(context, "com.google.android.gms.measurement.AppMeasurementService");
    }

    private final void zzb(Runnable runnable) {
        zzgl zzglVarZzg = zzgl.zzg(this.zzabh);
        zzglVarZzg.zzgg();
        zzglVarZzg.zzgf().zzc(new zzji(this, zzglVarZzg, runnable));
    }

    private final zzfg zzgg() {
        return zzgl.zzg(this.zzabh).zzgg();
    }

    public final IBinder onBind(Intent intent) {
        if (intent == null) {
            zzgg().zzil().log("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzgq(zzgl.zzg(this.zzabh));
        }
        zzgg().zzin().zzg("onBind received unknown action", action);
        return null;
    }

    public final void onCreate() {
        zzgl.zzg(this.zzabh).zzgg().zzir().log("Local AppMeasurementService is starting up");
    }

    public final void onDestroy() {
        zzgl.zzg(this.zzabh).zzgg().zzir().log("Local AppMeasurementService is shutting down");
    }

    public final void onRebind(Intent intent) {
        if (intent == null) {
            zzgg().zzil().log("onRebind called with null intent");
        } else {
            zzgg().zzir().zzg("onRebind called. action", intent.getAction());
        }
    }

    public final int onStartCommand(final Intent intent, int i, final int i2) {
        final zzfg zzfgVarZzgg = zzgl.zzg(this.zzabh).zzgg();
        if (intent == null) {
            zzfgVarZzgg.zzin().log("AppMeasurementService started with null intent");
        } else {
            String action = intent.getAction();
            zzfgVarZzgg.zzir().zze("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
            if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
                zzb(new Runnable(this, i2, zzfgVarZzgg, intent) { // from class: com.google.android.gms.internal.measurement.zzjg
                    private final int zzabk;
                    private final zzjf zzaqk;
                    private final zzfg zzaql;
                    private final Intent zzaqm;

                    {
                        this.zzaqk = this;
                        this.zzabk = i2;
                        this.zzaql = zzfgVarZzgg;
                        this.zzaqm = intent;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        this.zzaqk.zza(this.zzabk, this.zzaql, this.zzaqm);
                    }
                });
            }
        }
        return 2;
    }

    public final boolean onStartJob(final JobParameters jobParameters) {
        final zzfg zzfgVarZzgg = zzgl.zzg(this.zzabh).zzgg();
        String string = jobParameters.getExtras().getString(NativeProtocol.WEB_DIALOG_ACTION);
        zzfgVarZzgg.zzir().zzg("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zzb(new Runnable(this, zzfgVarZzgg, jobParameters) { // from class: com.google.android.gms.internal.measurement.zzjh
            private final JobParameters zzabn;
            private final zzjf zzaqk;
            private final zzfg zzaqn;

            {
                this.zzaqk = this;
                this.zzaqn = zzfgVarZzgg;
                this.zzabn = jobParameters;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzaqk.zza(this.zzaqn, this.zzabn);
            }
        });
        return true;
    }

    public final boolean onUnbind(Intent intent) {
        if (intent == null) {
            zzgg().zzil().log("onUnbind called with null intent");
        } else {
            zzgg().zzir().zzg("onUnbind called for intent. action", intent.getAction());
        }
        return true;
    }

    final /* synthetic */ void zza(int i, zzfg zzfgVar, Intent intent) {
        if (this.zzabh.callServiceStopSelfResult(i)) {
            zzfgVar.zzir().zzg("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzgg().zzir().log("Completed wakeful intent.");
            this.zzabh.zzb(intent);
        }
    }

    final /* synthetic */ void zza(zzfg zzfgVar, JobParameters jobParameters) {
        zzfgVar.zzir().log("AppMeasurementJobService processed last upload request.");
        this.zzabh.zza(jobParameters, false);
    }
}
