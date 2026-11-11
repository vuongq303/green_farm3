package com.gameloft.android2d.iap;

import android.view.View;
import org.json.JSONException;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class p implements View.OnClickListener {
    final /* synthetic */ n bkZ;

    p(n nVar) {
        this.bkZ = nVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws JSONException {
        b.bjp.bjJ.cancel();
        b.setResult(10);
        b.mB(-100);
        ae.KB();
    }
}
