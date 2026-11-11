package com.google.android.gms.internal.measurement;

import com.google.android.gms.measurement.b;

/* JADX INFO: loaded from: classes.dex */
final class zzht implements Runnable {
    private final /* synthetic */ zzhm zzaop;
    private final /* synthetic */ b zzaot;

    zzht(zzhm zzhmVar, b bVar) {
        this.zzaop = zzhmVar;
        this.zzaot = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zzb(this.zzaot);
    }
}
