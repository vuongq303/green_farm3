package com.google.android.gms.common.api.a;

import android.app.Dialog;

/* JADX INFO: loaded from: classes.dex */
final class dc extends q {
    private final /* synthetic */ Dialog bMq;
    private final /* synthetic */ db bMr;

    dc(db dbVar, Dialog dialog) {
        this.bMr = dbVar;
        this.bMq = dialog;
    }

    @Override // com.google.android.gms.common.api.a.q
    public final void zzv() {
        this.bMr.bMp.zzt();
        if (this.bMq.isShowing()) {
            this.bMq.dismiss();
        }
    }
}
