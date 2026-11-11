package com.google.android.gms.internal.games;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.multiplayer.turnbased.a;
import com.google.android.gms.games.multiplayer.turnbased.j;

/* JADX INFO: loaded from: classes.dex */
final class zzdv implements j {
    private final /* synthetic */ Status zzbc;

    zzdv(zzdu zzduVar, Status status) {
        this.zzbc = status;
    }

    public final a getMatches() {
        return new a(new Bundle());
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
