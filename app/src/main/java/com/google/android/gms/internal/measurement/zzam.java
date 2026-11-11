package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzam implements Runnable {
    private final /* synthetic */ zzai zzvc;
    private final /* synthetic */ zzch zzvg;

    zzam(zzai zzaiVar, zzch zzchVar) {
        this.zzvc = zzaiVar;
        this.zzvg = zzchVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        this.zzvc.zzva.zza(this.zzvg);
    }
}
