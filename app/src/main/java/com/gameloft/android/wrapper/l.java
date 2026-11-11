package com.gameloft.android.wrapper;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class l implements DialogInterface.OnKeyListener {
    final /* synthetic */ i bfD;
    final /* synthetic */ String bfE;

    l(i iVar, String str) {
        this.bfD = iVar;
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
