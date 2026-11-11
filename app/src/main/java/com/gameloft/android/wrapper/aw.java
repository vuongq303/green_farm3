package com.gameloft.android.wrapper;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class aw implements DialogInterface.OnKeyListener {
    aw() {
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4) {
            System.exit(0);
            return true;
        }
        return true;
    }
}
