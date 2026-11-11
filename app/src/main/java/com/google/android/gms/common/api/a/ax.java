package com.google.android.gms.common.api.a;

import com.google.android.gms.signin.internal.SignInResponse;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class ax extends com.google.android.gms.signin.internal.b {
    private final WeakReference<aq> bKx;

    ax(aq aqVar) {
        this.bKx = new WeakReference<>(aqVar);
    }

    @Override // com.google.android.gms.signin.internal.b, com.google.android.gms.signin.internal.d
    public final void b(SignInResponse signInResponse) {
        aq aqVar = this.bKx.get();
        if (aqVar == null) {
            return;
        }
        aqVar.bKe.a(new ay(this, aqVar, aqVar, signInResponse));
    }
}
