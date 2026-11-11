package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zzhr implements Runnable {
    private final /* synthetic */ AtomicReference zzaoo;
    private final /* synthetic */ zzhm zzaop;

    zzhr(zzhm zzhmVar, AtomicReference atomicReference) {
        this.zzaop = zzhmVar;
        this.zzaoo = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zzfx().zza(this.zzaoo);
    }
}
