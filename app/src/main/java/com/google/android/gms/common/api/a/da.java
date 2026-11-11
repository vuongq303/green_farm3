package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
final class da {
    private final int bMm;
    private final ConnectionResult bMn;

    da(ConnectionResult connectionResult, int i) {
        com.google.android.gms.common.internal.bf.checkNotNull(connectionResult);
        this.bMn = connectionResult;
        this.bMm = i;
    }

    final int ZL() {
        return this.bMm;
    }

    final ConnectionResult ZM() {
        return this.bMn;
    }
}
