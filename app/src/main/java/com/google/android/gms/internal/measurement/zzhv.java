package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zzhv implements Runnable {
    private final /* synthetic */ String zzaoa;
    private final /* synthetic */ String zzaob;
    private final /* synthetic */ String zzaoc;
    private final /* synthetic */ AtomicReference zzaoo;
    private final /* synthetic */ zzhm zzaop;

    zzhv(zzhm zzhmVar, AtomicReference atomicReference, String str, String str2, String str3) {
        this.zzaop = zzhmVar;
        this.zzaoo = atomicReference;
        this.zzaoc = str;
        this.zzaoa = str2;
        this.zzaob = str3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zzacr.zzfx().zza(this.zzaoo, this.zzaoc, this.zzaoa, this.zzaob);
    }
}
