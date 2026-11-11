package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzak implements Runnable {
    private final /* synthetic */ zzai zzvc;
    private final /* synthetic */ boolean zzvd;

    zzak(zzai zzaiVar, boolean z) {
        this.zzvc = zzaiVar;
        this.zzvd = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvc.zzva.zzdi();
    }
}
