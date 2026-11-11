package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
final class bs implements Runnable {
    private final /* synthetic */ ConnectionResult bLj;
    private final /* synthetic */ o bLl;

    bs(o oVar, ConnectionResult connectionResult) {
        this.bLl = oVar;
        this.bLj = connectionResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (!this.bLj.Sp()) {
            ((m) this.bLl.bJC.bJn.get(this.bLl.bJu)).onConnectionFailed(this.bLj);
            return;
        }
        o.a(this.bLl, true);
        if (this.bLl.bJs.requiresSignIn()) {
            this.bLl.zzbu();
        } else {
            this.bLl.bJs.getRemoteService(null, Collections.emptySet());
        }
    }
}
