package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.a.a.k;
import com.google.android.gms.plus.c;

/* JADX INFO: loaded from: classes.dex */
final class zzq implements c {
    private final /* synthetic */ Status zzan;

    zzq(zzp zzpVar, Status status) {
        this.zzan = status;
    }

    public final String getNextPageToken() {
        return null;
    }

    public final k getPersonBuffer() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzan;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
