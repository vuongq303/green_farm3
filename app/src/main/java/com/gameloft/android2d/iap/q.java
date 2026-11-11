package com.gameloft.android2d.iap;

import android.content.DialogInterface;
import android.view.KeyEvent;
import org.json.JSONException;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class q implements DialogInterface.OnKeyListener {
    final /* synthetic */ n bkZ;

    q(n nVar) {
        this.bkZ = nVar;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) throws JSONException {
        if (i == 4 && keyEvent.getAction() == 1) {
            b.bjp.bjJ.cancel();
            b.setResult(10);
            b.mB(-100);
            ae.KB();
        }
        return true;
    }
}
