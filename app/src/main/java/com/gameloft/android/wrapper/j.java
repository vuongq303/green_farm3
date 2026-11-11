package com.gameloft.android.wrapper;

import android.content.DialogInterface;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class j implements DialogInterface.OnClickListener {
    final /* synthetic */ i bfD;

    j(i iVar) {
        this.bfD = iVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.bfD.bfC.setResult(-2);
        this.bfD.bfC.finish();
    }
}
