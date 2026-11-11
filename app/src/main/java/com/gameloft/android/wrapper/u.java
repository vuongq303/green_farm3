package com.gameloft.android.wrapper;

import android.content.DialogInterface;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class u implements DialogInterface.OnClickListener {
    final /* synthetic */ s bfG;

    u(s sVar) {
        this.bfG = sVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.bfG.bfC.setResult(-1);
        this.bfG.bfC.finish();
    }
}
