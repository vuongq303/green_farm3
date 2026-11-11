package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzvc implements Runnable {
    private final /* synthetic */ zzuw zzbpu;
    private final /* synthetic */ String zzbpv;

    zzvc(zzuw zzuwVar, String str) {
        this.zzbpu = zzuwVar;
        this.zzbpv = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbpu.zzbnd.loadUrl(this.zzbpv);
    }
}
