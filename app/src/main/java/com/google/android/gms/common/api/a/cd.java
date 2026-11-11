package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
final class cd implements Runnable {
    private final /* synthetic */ cc bLz;

    cd(cc ccVar) {
        this.bLz = ccVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bLz.bLy.a(new ConnectionResult(4));
    }
}
