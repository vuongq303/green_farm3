package com.gameloft.android2d.iap;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class l implements DialogInterface.OnClickListener {
    l() {
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + com.gameloft.android2d.iap.utils.v.getContext().getPackageName()));
        com.gameloft.android2d.iap.utils.v.getContext().startActivity(intent);
        b.setResult(10);
        b.mB(0);
    }
}
