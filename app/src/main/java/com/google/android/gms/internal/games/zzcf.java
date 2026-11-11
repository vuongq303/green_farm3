package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.request.a;
import com.google.android.gms.games.request.d;

/* JADX INFO: loaded from: classes.dex */
final class zzcf implements d {
    private final /* synthetic */ Status zzbc;

    zzcf(zzce zzceVar, Status status) {
        this.zzbc = status;
    }

    public final a getRequests(int i) {
        return new a(DataHolder.nG(this.zzbc.getStatusCode()));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
