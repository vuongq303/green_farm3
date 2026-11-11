package com.gameloft.android.wrapper;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class w implements DialogInterface.OnKeyListener {
    final /* synthetic */ String bfE;
    final /* synthetic */ s bfG;

    w(s sVar, String str) {
        this.bfG = sVar;
        this.bfE = str;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4) {
            au.eC(this.bfE);
            return false;
        }
        return false;
    }
}
