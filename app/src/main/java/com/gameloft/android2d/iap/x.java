package com.gameloft.android2d.iap;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class x implements DialogInterface.OnKeyListener {
    final /* synthetic */ u bld;

    x(u uVar) {
        this.bld = uVar;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getAction() == 1) {
            b.bjp.bjJ.dismiss();
            b.setResult(2);
            b.mB(-100);
        }
        return true;
    }
}
