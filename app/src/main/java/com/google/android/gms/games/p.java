package com.google.android.gms.games;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class p extends com.google.android.gms.common.data.a<Player> {
    public p(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.c
    /* JADX INFO: renamed from: oi, reason: merged with bridge method [inline-methods] */
    public final Player get(int i) {
        return new PlayerRef(this.mDataHolder, i);
    }
}
