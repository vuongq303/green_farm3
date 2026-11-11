package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.stats.PlayerStats;
import com.google.android.gms.games.stats.c;

/* JADX INFO: loaded from: classes.dex */
final class zzda implements c {
    private final /* synthetic */ Status zzbc;

    zzda(zzcz zzczVar, Status status) {
        this.zzbc = status;
    }

    public final PlayerStats getPlayerStats() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
    }
}
