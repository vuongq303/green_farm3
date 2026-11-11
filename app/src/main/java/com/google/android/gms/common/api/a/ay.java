package com.google.android.gms.common.api.a;

import com.google.android.gms.signin.internal.SignInResponse;

/* JADX INFO: loaded from: classes.dex */
final class ay extends bj {
    private final /* synthetic */ aq bKE;
    private final /* synthetic */ SignInResponse bKF;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ay(ax axVar, bh bhVar, aq aqVar, SignInResponse signInResponse) {
        super(bhVar);
        this.bKE = aqVar;
        this.bKF = signInResponse;
    }

    @Override // com.google.android.gms.common.api.a.bj
    public final void zzaq() {
        this.bKE.a(this.bKF);
    }
}
