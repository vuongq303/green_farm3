package com.gameloft.android.wrapper;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class be implements DialogInterface.OnKeyListener {
    be() {
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4) {
            ((Activity) au.getContext()).finish();
            System.exit(0);
        }
        return false;
    }
}
