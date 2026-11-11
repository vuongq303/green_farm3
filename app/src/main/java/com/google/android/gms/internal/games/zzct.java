package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.snapshot.a;
import com.google.android.gms.games.snapshot.g;

/* JADX INFO: loaded from: classes.dex */
final class zzct implements g {
    private final /* synthetic */ Status zzbc;

    zzct(zzcs zzcsVar, Status status) {
        this.zzbc = status;
    }

    public final a getSnapshots() {
        return new a(DataHolder.nG(14));
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
