package com.google.android.gms.common.api.a;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
final class az implements com.google.android.gms.common.api.y, com.google.android.gms.common.api.z {
    private final /* synthetic */ aq bKw;

    private az(aq aqVar) {
        this.bKw = aqVar;
    }

    /* synthetic */ az(aq aqVar, ar arVar) {
        this(aqVar);
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnected(Bundle bundle) {
        this.bKw.bKo.a(new ax(this.bKw));
    }

    @Override // com.google.android.gms.common.api.z
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.bKw.bKh.lock();
        try {
            if (this.bKw.e(connectionResult)) {
                this.bKw.zzau();
                this.bKw.Zx();
            } else {
                this.bKw.f(connectionResult);
            }
        } finally {
            this.bKw.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnectionSuspended(int i) {
    }
}
