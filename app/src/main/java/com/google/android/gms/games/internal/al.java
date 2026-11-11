package com.google.android.gms.games.internal;

import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;

/* JADX INFO: loaded from: classes.dex */
final class al implements com.google.android.gms.common.api.a.x<com.google.android.gms.games.multiplayer.turnbased.b> {
    private final TurnBasedMatch bUv;

    al(TurnBasedMatch turnBasedMatch) {
        this.bUv = turnBasedMatch;
    }

    @Override // com.google.android.gms.common.api.a.x
    public final void YU() {
    }

    @Override // com.google.android.gms.common.api.a.x
    public final /* synthetic */ void z(com.google.android.gms.games.multiplayer.turnbased.b bVar) {
        bVar.a(this.bUv);
    }
}
