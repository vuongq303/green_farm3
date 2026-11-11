package com.google.android.gms.common.api.a;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public class k implements com.google.android.gms.common.api.ac, com.google.android.gms.common.api.ae {
    protected final DataHolder mDataHolder;
    protected final Status mStatus;

    protected k(DataHolder dataHolder, Status status) {
        this.mStatus = status;
        this.mDataHolder = dataHolder;
    }

    @Override // com.google.android.gms.common.api.ae
    public Status getStatus() {
        return this.mStatus;
    }

    @Override // com.google.android.gms.common.api.ac
    public void release() {
        if (this.mDataHolder != null) {
            this.mDataHolder.close();
        }
    }
}
