package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
final class bp implements Runnable {
    private final /* synthetic */ m bLi;
    private final /* synthetic */ ConnectionResult bLj;

    bp(m mVar, ConnectionResult connectionResult) {
        this.bLi = mVar;
        this.bLj = connectionResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bLi.onConnectionFailed(this.bLj);
    }
}
