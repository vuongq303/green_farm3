package com.gameloft.android2d.iap;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.Toast;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class m implements DialogInterface.OnKeyListener {
    m() {
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4) {
            Toast.makeText(com.gameloft.android2d.iap.utils.v.getContext(), com.b.d.cfL, 0).show();
            return true;
        }
        return true;
    }
}
