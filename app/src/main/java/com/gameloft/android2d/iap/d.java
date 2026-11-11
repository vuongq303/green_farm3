package com.gameloft.android2d.iap;

import android.content.DialogInterface;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class d implements DialogInterface.OnClickListener {
    d() {
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        b.bkP = false;
    }
}
