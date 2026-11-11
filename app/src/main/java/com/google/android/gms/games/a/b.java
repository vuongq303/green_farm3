package com.google.android.gms.games.a;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class b extends com.google.android.gms.common.data.j<a> {
    public b(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.j
    protected final String ZX() {
        return "external_leaderboard_id";
    }

    @Override // com.google.android.gms.common.data.j
    protected final /* synthetic */ a cq(int i, int i2) {
        return new d(this.mDataHolder, i, i2);
    }
}
