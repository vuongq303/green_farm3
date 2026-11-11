package com.gameloft.android.wrapper;

import android.app.Activity;
import android.content.DialogInterface;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class bc implements DialogInterface.OnClickListener {
    bc() {
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        ((Activity) au.getContext()).finish();
        System.exit(0);
    }
}
