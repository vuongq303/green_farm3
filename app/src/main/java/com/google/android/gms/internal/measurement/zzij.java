package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzij implements Runnable {
    private final /* synthetic */ zzih zzapo;
    private final /* synthetic */ zzik zzapp;

    zzij(zzih zzihVar, zzik zzikVar) {
        this.zzapo = zzihVar;
        this.zzapp = zzikVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzapo.zza(this.zzapp);
        this.zzapo.zzapc = null;
        this.zzapo.zzfx().zza((zzig) null);
    }
}
