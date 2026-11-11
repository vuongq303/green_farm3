package com.google.android.gms.common.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
public final class n extends p {
    private final /* synthetic */ e bNy;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public n(e eVar, int i, Bundle bundle) {
        super(eVar, i, bundle);
        this.bNy = eVar;
    }

    @Override // com.google.android.gms.common.internal.p
    protected final boolean aac() {
        this.bNy.mConnectionProgressReportCallbacks.d(ConnectionResult.bHX);
        return true;
    }

    @Override // com.google.android.gms.common.internal.p
    protected final void k(ConnectionResult connectionResult) {
        this.bNy.mConnectionProgressReportCallbacks.d(connectionResult);
        this.bNy.onConnectionFailed(connectionResult);
    }
}
