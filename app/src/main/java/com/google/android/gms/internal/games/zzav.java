package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.a.b;
import com.google.android.gms.games.a.k;

/* JADX INFO: loaded from: classes.dex */
final class zzav implements k {
    private final /* synthetic */ Status zzbc;

    zzav(zzau zzauVar, Status status) {
        this.zzbc = status;
    }

    public final b getLeaderboards() {
        return new b(DataHolder.nG(14));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
