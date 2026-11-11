package com.google.android.gms.measurement;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.google.android.gms.internal.measurement.zzjf;
import com.google.android.gms.internal.measurement.zzjj;

/* JADX INFO: loaded from: classes.dex */
public final class AppMeasurementJobService extends JobService implements zzjj {
    private zzjf<AppMeasurementJobService> bXL;

    private final zzjf<AppMeasurementJobService> afX() {
        if (this.bXL == null) {
            this.bXL = new zzjf<>(this);
        }
        return this.bXL;
    }

    @Override // com.google.android.gms.internal.measurement.zzjj
    public final boolean callServiceStopSelfResult(int i) {
        throw new UnsupportedOperationException();
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

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        return afX().onStartJob(jobParameters);
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        return afX().onUnbind(intent);
    }

    @Override // com.google.android.gms.internal.measurement.zzjj
    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }

    @Override // com.google.android.gms.internal.measurement.zzjj
    public final void zzb(Intent intent) {
    }
}
