package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzpq implements Runnable {
    private final /* synthetic */ zzpd zzbki;
    private final /* synthetic */ zzpp zzbkj;

    zzpq(zzpp zzppVar, zzpd zzpdVar) {
        this.zzbkj = zzppVar;
        this.zzbki = zzpdVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbkj.zza(this.zzbki);
    }
}
