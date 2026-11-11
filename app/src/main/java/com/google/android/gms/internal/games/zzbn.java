package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.p;
import com.google.android.gms.games.s;

/* JADX INFO: loaded from: classes.dex */
final class zzbn implements s {
    private final /* synthetic */ Status zzbc;

    zzbn(zzbm zzbmVar, Status status) {
        this.zzbc = status;
    }

    public final p getPlayers() {
        return new p(DataHolder.nG(14));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
