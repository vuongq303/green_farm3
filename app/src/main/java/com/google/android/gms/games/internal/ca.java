package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class ca extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.g> bUT;

    public ca(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.g> fVar) {
        this.bUT = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void o(DataHolder dataHolder) {
        this.bUT.setResult(new k(dataHolder));
    }
}
