package com.gameloft.android2d.iap;

import android.view.View;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class v implements View.OnClickListener {
    final /* synthetic */ u bld;

    v(u uVar) {
        this.bld = uVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.bld.blc.eP(this.bld.blc.RY());
        b.bjp.bjJ.dismiss();
    }
}
