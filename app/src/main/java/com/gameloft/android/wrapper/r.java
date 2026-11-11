package com.gameloft.android.wrapper;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class r implements DialogInterface.OnKeyListener {
    final /* synthetic */ String bfE;
    final /* synthetic */ p bfF;

    r(p pVar, String str) {
        this.bfF = pVar;
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
