package com.gameloft.android2d.iap;

import android.content.DialogInterface;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class j implements DialogInterface.OnClickListener {
    j() {
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        b.setResult(3);
        b.mB(-16);
    }
}
