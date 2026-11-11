package com.gameloft.android2d.iap;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class ab implements DialogInterface.OnKeyListener {
    final /* synthetic */ z ble;

    ab(z zVar) {
        this.ble = zVar;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getAction() == 1) {
            b.setResult(10);
            dialogInterface.cancel();
        }
        return true;
    }
}
