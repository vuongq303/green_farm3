package com.google.android.gms.internal.measurement;

import android.content.ComponentName;

/* JADX INFO: loaded from: classes.dex */
final class zzjd implements Runnable {
    private final /* synthetic */ zziz zzaqi;

    zzjd(zziz zzizVar) {
        this.zzaqi = zzizVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaqi.zzapy.onServiceDisconnected(new ComponentName(this.zzaqi.zzapy.getContext(), "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
