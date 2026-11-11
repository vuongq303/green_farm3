package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;

/* JADX INFO: loaded from: classes.dex */
final class zzac implements ae {
    private final /* synthetic */ Status zzbc;

    zzac(zzab zzabVar, Status status) {
        this.zzbc = status;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
