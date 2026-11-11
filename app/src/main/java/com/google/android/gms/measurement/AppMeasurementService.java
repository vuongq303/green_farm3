package com.google.android.gms.measurement;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.internal.measurement.zzjf;
import com.google.android.gms.internal.measurement.zzjj;

/* JADX INFO: loaded from: classes.dex */
public final class AppMeasurementService extends Service implements zzjj {
    private zzjf<AppMeasurementService> bXL;

    private final zzjf<AppMeasurementService> afX() {
        if (this.bXL == null) {
            this.bXL = new zzjf<>(this);
        }
        return this.bXL;
    }

    @Override // com.google.android.gms.internal.measurement.zzjj
    public final boolean callServiceStopSelfResult(int i) {
        return stopSelfResult(i);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return afX().onBind(intent);
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        afX().onCreate();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        afX().onDestroy();
        super.onDestroy();
    }

    @Override // android.app.Service
    public final void onRebind(Intent intent) {
        afX().onRebind(intent);
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        return afX().onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        return afX().onUnbind(intent);
    }

    @Override // com.google.android.gms.internal.measurement.zzjj
    public final void zza(JobParameters jobParameters, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.measurement.zzjj
    public final void zzb(Intent intent) {
        AppMeasurementReceiver.completeWakefulIntent(intent);
    }
}
