package com.google.android.gms.ads.internal;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class ap implements View.OnClickListener {
    private final /* synthetic */ am bDw;
    private final /* synthetic */ zzx bDx;

    ap(am amVar, zzx zzxVar) {
        this.bDw = amVar;
        this.bDx = zzxVar;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.bDx.recordClick();
        if (this.bDw.bDu != null) {
            this.bDw.bDu.zzpi();
        }
    }
}
