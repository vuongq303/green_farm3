package com.gameloft.android.ANMP.GloftGF2F;

import android.content.DialogInterface;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class af implements DialogInterface.OnCancelListener {
    final /* synthetic */ ad Dr;

    af(ad adVar) {
        this.Dr = adVar;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        this.Dr.Dn.Dj.gG();
    }
}
