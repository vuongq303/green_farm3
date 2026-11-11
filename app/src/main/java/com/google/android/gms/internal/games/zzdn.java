package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.multiplayer.turnbased.f;

/* JADX INFO: loaded from: classes.dex */
final class zzdn implements f {
    private final /* synthetic */ Status zzbc;
    private final /* synthetic */ zzdm zzko;

    zzdn(zzdm zzdmVar, Status status) {
        this.zzko = zzdmVar;
        this.zzbc = status;
    }

    public final String getMatchId() {
        return this.zzko.zzji;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
