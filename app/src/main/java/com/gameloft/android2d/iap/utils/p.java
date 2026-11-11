package com.gameloft.android2d.iap.utils;

import android.os.Bundle;

/* JADX INFO: compiled from: IABRequestHandler.java */
/* JADX INFO: loaded from: classes.dex */
class p extends j {
    final /* synthetic */ n bnI;
    m bnJ;

    public p(n nVar, m mVar) {
        this.bnI = nVar;
        this.bnJ = null;
        this.bnJ = mVar;
    }

    @Override // com.gameloft.android2d.iap.utils.j
    protected Integer e(Bundle bundle) {
        Bundle bundleR = this.bnI.r(bundle);
        if (this.bnJ != null) {
            this.bnJ.p(bundleR);
        }
        return new Integer(0);
    }
}
