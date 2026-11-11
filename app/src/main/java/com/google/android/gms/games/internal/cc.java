package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class cc extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.i> bUV;

    public cc(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.i> fVar) {
        this.bUV = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void n(DataHolder dataHolder) {
        this.bUV.setResult(new z(dataHolder));
    }
}
