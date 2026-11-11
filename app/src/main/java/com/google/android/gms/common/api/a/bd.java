package com.google.android.gms.common.api.a;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
final class bd implements com.google.android.gms.common.internal.aa {
    private final /* synthetic */ bc bKW;

    bd(bc bcVar) {
        this.bKW = bcVar;
    }

    @Override // com.google.android.gms.common.internal.aa
    public final Bundle getConnectionHint() {
        return null;
    }

    @Override // com.google.android.gms.common.internal.aa
    public final boolean isConnected() {
        return this.bKW.isConnected();
    }
}
