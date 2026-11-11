package com.gameloft.android2d.iap.a.a;

import android.os.Bundle;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class h extends com.gameloft.android2d.iap.utils.m {
    final /* synthetic */ a blU;

    h(a aVar) {
        this.blU = aVar;
    }

    @Override // com.gameloft.android2d.iap.utils.m
    public void p(Bundle bundle) {
        if (bundle.getInt("a5") == 1) {
            bundle.getString("a4");
        } else {
            bundle.getInt("a6");
            new Thread(new i(this)).start();
        }
    }
}
