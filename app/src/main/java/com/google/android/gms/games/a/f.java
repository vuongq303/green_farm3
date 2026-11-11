package com.google.android.gms.games.a;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class f extends com.google.android.gms.common.data.a<e> {
    private final q bVp;

    public f(DataHolder dataHolder) {
        super(dataHolder);
        this.bVp = new q(dataHolder.ZW());
    }

    public final q aeb() {
        return this.bVp;
    }

    @Override // com.google.android.gms.common.data.c
    /* JADX INFO: renamed from: oo, reason: merged with bridge method [inline-methods] */
    public final e get(int i) {
        return new h(this.mDataHolder, i);
    }
}
