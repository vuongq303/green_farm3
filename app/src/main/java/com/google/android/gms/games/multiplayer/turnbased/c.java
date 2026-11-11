package com.google.android.gms.games.multiplayer.turnbased;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class c extends com.google.android.gms.common.data.j<TurnBasedMatch> {
    public c(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.j
    protected final String ZX() {
        return "external_match_id";
    }

    @Override // com.google.android.gms.common.data.j
    protected final /* synthetic */ TurnBasedMatch cq(int i, int i2) {
        return new zzd(this.mDataHolder, i, i2);
    }
}
