package com.google.android.gms.internal.ads;

import com.gameloft.glads.AndroidWebView;

/* JADX INFO: loaded from: classes.dex */
final class zzvb implements Runnable {
    private final /* synthetic */ String zzbpt;
    private final /* synthetic */ zzuw zzbpu;

    zzvb(zzuw zzuwVar, String str) {
        this.zzbpu = zzuwVar;
        this.zzbpt = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbpu.zzbnd.loadData(this.zzbpt, "text/html", AndroidWebView.UTF_8);
    }
}
