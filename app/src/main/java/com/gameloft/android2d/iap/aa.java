package com.gameloft.android2d.iap;

import android.content.DialogInterface;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class aa implements DialogInterface.OnClickListener {
    final /* synthetic */ z ble;

    aa(z zVar) {
        this.ble = zVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        b.setResult(10);
        dialogInterface.cancel();
    }
}
