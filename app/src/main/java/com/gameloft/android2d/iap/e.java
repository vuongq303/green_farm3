package com.gameloft.android2d.iap;

import android.content.DialogInterface;
import android.view.KeyEvent;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class e implements DialogInterface.OnKeyListener {
    e() {
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getAction() == 1) {
            dialogInterface.dismiss();
            b.bkP = false;
            MIDlet.coD = 2;
        }
        return true;
    }
}
