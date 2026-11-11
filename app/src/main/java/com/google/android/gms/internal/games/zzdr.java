package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.h;

/* JADX INFO: loaded from: classes.dex */
final class zzdr implements h {
    private final /* synthetic */ Status zzbc;

    zzdr(zzdq zzdqVar, Status status) {
        this.zzbc = status;
    }

    public final TurnBasedMatch getMatch() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
