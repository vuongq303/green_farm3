package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.support.v4.app.bk;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class c implements bk<Void> {
    private final /* synthetic */ SignInHubActivity bHg;

    private c(SignInHubActivity signInHubActivity) {
        this.bHg = signInHubActivity;
    }

    @Override // android.support.v4.app.bk
    public final void a(android.support.v4.content.f<Void> fVar) {
    }

    @Override // android.support.v4.app.bk
    public final /* synthetic */ void a(android.support.v4.content.f<Void> fVar, Void r5) {
        this.bHg.setResult(this.bHg.bHe, this.bHg.bHf);
        this.bHg.finish();
    }

    @Override // android.support.v4.app.bk
    public final android.support.v4.content.f<Void> b(int i, Bundle bundle) {
        return new e(this.bHg, w.YM());
    }
}
