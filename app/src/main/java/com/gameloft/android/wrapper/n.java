package com.gameloft.android.wrapper;

import android.content.DialogInterface;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class n implements DialogInterface.OnClickListener {
    final /* synthetic */ i bfD;

    n(i iVar) {
        this.bfD = iVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        GamePermission.bfx = true;
        this.bfD.bfC.setResult(1);
        this.bfD.bfC.finish();
    }
}
