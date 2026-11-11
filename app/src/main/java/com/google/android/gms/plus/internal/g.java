package com.google.android.gms.plus.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class g implements com.google.android.gms.plus.c {
    private final com.google.android.gms.plus.a.a.k bXW;
    private final Status mStatus;
    private final String zzt;

    public g(Status status, DataHolder dataHolder, String str) {
        this.mStatus = status;
        this.zzt = str;
        this.bXW = dataHolder != null ? new com.google.android.gms.plus.a.a.k(dataHolder) : null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.mStatus;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
        if (this.bXW != null) {
            this.bXW.release();
        }
    }
}
