package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zzhp implements Runnable {
    private final /* synthetic */ AtomicReference zzaoo;
    private final /* synthetic */ zzhm zzaop;
    private final /* synthetic */ boolean zzaos;

    zzhp(zzhm zzhmVar, AtomicReference atomicReference, boolean z) {
        this.zzaop = zzhmVar;
        this.zzaoo = atomicReference;
        this.zzaos = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zzfx().zza(this.zzaoo, this.zzaos);
    }
}
