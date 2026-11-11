package com.google.android.gms.games.multiplayer.realtime;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class l extends com.google.android.gms.common.data.j<Room> {
    public l(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.j
    protected final String ZX() {
        return "external_match_id";
    }

    @Override // com.google.android.gms.common.data.j
    protected final /* synthetic */ Room cq(int i, int i2) {
        return new zzf(this.mDataHolder, i, i2);
    }
}
