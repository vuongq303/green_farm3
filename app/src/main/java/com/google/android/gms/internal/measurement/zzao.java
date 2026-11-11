package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzao implements Runnable {
    private final /* synthetic */ zzai zzvc;
    private final /* synthetic */ zzca zzvh;

    zzao(zzai zzaiVar, zzca zzcaVar) {
        this.zzvc = zzaiVar;
        this.zzvh = zzcaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvc.zzva.zzb(this.zzvh);
    }
}
