package com.gameloft.android.ANMP.GloftGF2F;

import android.content.DialogInterface;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class ac implements DialogInterface.OnCancelListener {
    final /* synthetic */ aa Dp;

    ac(aa aaVar) {
        this.Dp = aaVar;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        this.Dp.Do.Dn.Dj.gG();
    }
}
