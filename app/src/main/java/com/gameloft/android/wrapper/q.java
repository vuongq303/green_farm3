package com.gameloft.android.wrapper;

import android.content.DialogInterface;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class q implements DialogInterface.OnClickListener {
    final /* synthetic */ p bfF;

    q(p pVar) {
        this.bfF = pVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.bfF.bfC.requestPermissions(this.bfF.bfB, 0);
    }
}
