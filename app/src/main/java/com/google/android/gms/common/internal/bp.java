package com.google.android.gms.common.internal;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
final class bp implements f {
    private final /* synthetic */ com.google.android.gms.common.api.y bOE;

    bp(com.google.android.gms.common.api.y yVar) {
        this.bOE = yVar;
    }

    @Override // com.google.android.gms.common.internal.f
    public final void onConnected(Bundle bundle) {
        this.bOE.onConnected(bundle);
    }

    @Override // com.google.android.gms.common.internal.f
    public final void onConnectionSuspended(int i) {
        this.bOE.onConnectionSuspended(i);
    }
}
