package com.google.android.gms.games.stats;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class a extends com.google.android.gms.common.data.a<PlayerStats> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.c
    public final /* synthetic */ Object get(int i) {
        return new zzc(this.mDataHolder, i);
    }
}
