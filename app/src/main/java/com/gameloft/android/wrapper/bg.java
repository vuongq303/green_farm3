package com.gameloft.android.wrapper;

import android.content.DialogInterface;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class bg implements DialogInterface.OnClickListener {
    bg() {
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        System.exit(0);
    }
}
