package com.gameloft.android.wrapper;

import android.app.Activity;
import android.content.DialogInterface;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class bd implements DialogInterface.OnCancelListener {
    bd() {
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        ((Activity) au.getContext()).finish();
        System.exit(0);
    }
}
