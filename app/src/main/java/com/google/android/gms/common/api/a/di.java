package com.google.android.gms.common.api.a;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
final class di implements bv {
    private final /* synthetic */ dg bMD;

    private di(dg dgVar) {
        this.bMD = dgVar;
    }

    /* synthetic */ di(dg dgVar, dh dhVar) {
        this(dgVar);
    }

    @Override // com.google.android.gms.common.api.a.bv
    public final void N(int i, boolean z) {
        this.bMD.bKh.lock();
        try {
            if (this.bMD.bMB || this.bMD.bMA == null || !this.bMD.bMA.Sp()) {
                this.bMD.bMB = false;
                this.bMD.zza(i, z);
            } else {
                this.bMD.bMB = true;
                this.bMD.bMu.onConnectionSuspended(i);
            }
        } finally {
            this.bMD.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bv
    public final void g(ConnectionResult connectionResult) {
        this.bMD.bKh.lock();
        try {
            this.bMD.bMz = connectionResult;
            this.bMD.ZO();
        } finally {
            this.bMD.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bv
    public final void s(Bundle bundle) {
        this.bMD.bKh.lock();
        try {
            this.bMD.u(bundle);
            this.bMD.bMz = ConnectionResult.bHX;
            this.bMD.ZO();
        } finally {
            this.bMD.bKh.unlock();
        }
    }
}
