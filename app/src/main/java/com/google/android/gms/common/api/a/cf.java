package com.google.android.gms.common.api.a;

import com.google.android.gms.signin.internal.SignInResponse;

/* JADX INFO: loaded from: classes.dex */
final class cf implements Runnable {
    private final /* synthetic */ SignInResponse bKF;
    private final /* synthetic */ cc bLz;

    cf(cc ccVar, SignInResponse signInResponse) {
        this.bLz = ccVar;
        this.bKF = signInResponse;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bLz.c(this.bKF);
    }
}
