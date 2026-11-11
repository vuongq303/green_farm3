package com.gameloft.android2d.iap;

import android.view.View;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class w implements View.OnClickListener {
    final /* synthetic */ u bld;

    w(u uVar) {
        this.bld = uVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        b.bjp.bjJ.dismiss();
        b.setResult(2);
        b.mB(-100);
    }
}
