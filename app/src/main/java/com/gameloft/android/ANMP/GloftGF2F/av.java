package com.gameloft.android.ANMP.GloftGF2F;

import android.content.DialogInterface;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class av implements DialogInterface.OnClickListener {
    final /* synthetic */ at EC;

    av(at atVar) {
        this.EC = atVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        String unused = InGameBrowserActivity.Et = "LoadPageFailed";
        this.EC.EB.Ex.exit();
    }
}
