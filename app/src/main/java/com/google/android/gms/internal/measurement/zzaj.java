package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzaj implements Runnable {
    private final /* synthetic */ int zzvb;
    private final /* synthetic */ zzai zzvc;

    zzaj(zzai zzaiVar, int i) {
        this.zzvc = zzaiVar;
        this.zzvb = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvc.zzva.zzg(((long) this.zzvb) * 1000);
    }
}
