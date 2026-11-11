package com.google.android.gms.common.api.a;

import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class as implements com.google.android.gms.common.internal.i {
    private final WeakReference<aq> bKx;
    private final boolean bKy;
    private final com.google.android.gms.common.api.a<?> mApi;

    public as(aq aqVar, com.google.android.gms.common.api.a<?> aVar, boolean z) {
        this.bKx = new WeakReference<>(aqVar);
        this.mApi = aVar;
        this.bKy = z;
    }

    @Override // com.google.android.gms.common.internal.i
    public final void d(ConnectionResult connectionResult) {
        aq aqVar = this.bKx.get();
        if (aqVar == null) {
            return;
        }
        com.google.android.gms.common.internal.bf.a(Looper.myLooper() == aqVar.bKe.bLd.getLooper(), "onReportServiceBinding must be called on the GoogleApiClient handler thread");
        aqVar.bKh.lock();
        try {
            if (aqVar.nz(0)) {
                if (!connectionResult.Sp()) {
                    aqVar.b(connectionResult, this.mApi, this.bKy);
                }
                if (aqVar.Zw()) {
                    aqVar.Zx();
                }
            }
        } finally {
            aqVar.bKh.unlock();
        }
    }
}
