package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class ce extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.k> bUW;

    public ce(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.k> fVar) {
        this.bUW = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void p(DataHolder dataHolder) {
        this.bUW.setResult(new ch(dataHolder));
    }
}
