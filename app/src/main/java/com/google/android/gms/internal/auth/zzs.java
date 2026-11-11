package com.google.android.gms.internal.auth;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;

/* JADX INFO: loaded from: classes.dex */
final class zzs implements ae {
    private final Status mStatus;

    public zzs(Status status) {
        this.mStatus = status;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.mStatus;
    }
}
