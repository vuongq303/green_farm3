package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.a.n;
import com.google.android.gms.games.a.o;

/* JADX INFO: loaded from: classes.dex */
final class zzbb implements n {
    private final /* synthetic */ Status zzbc;

    zzbb(zzba zzbaVar, Status status) {
        this.zzbc = status;
    }

    public final o getScoreData() {
        return new o(DataHolder.nG(14));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
