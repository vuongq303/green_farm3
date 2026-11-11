package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class bt extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.e> bUO;

    public bt(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.e> fVar) {
        this.bUO = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void H(DataHolder dataHolder) {
        this.bUO.setResult(new cw(dataHolder));
    }
}
