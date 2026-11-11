package com.gameloft.android.ANMP.GloftGF2F;

import android.content.DialogInterface;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class au implements DialogInterface.OnCancelListener {
    final /* synthetic */ at EC;

    au(at atVar) {
        this.EC = atVar;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        String unused = InGameBrowserActivity.Et = "LoadPageFailed";
        this.EC.EB.Ex.exit();
    }
}
