package com.google.android.gms.games;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class a extends com.google.android.gms.common.data.a<Game> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.c
    /* JADX INFO: renamed from: of, reason: merged with bridge method [inline-methods] */
    public final Game get(int i) {
        return new GameRef(this.mDataHolder, i);
    }
}
