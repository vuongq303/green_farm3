package com.gameloft.android2d.iap;

import android.app.AlertDialog;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class z extends Thread {
    final /* synthetic */ String val$message;

    z(String str) {
        this.val$message = str;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(com.gameloft.android2d.iap.utils.v.getContext()).create();
        alertDialogCreate.setCancelable(false);
        alertDialogCreate.setMessage(this.val$message);
        alertDialogCreate.setButton("OK", new aa(this));
        alertDialogCreate.setOnKeyListener(new ab(this));
        alertDialogCreate.show();
    }
}
