package com.google.android.gms.games.internal;

import com.google.android.gms.games.multiplayer.Invitation;

/* JADX INFO: loaded from: classes.dex */
final class m implements com.google.android.gms.common.api.a.x<com.google.android.gms.games.multiplayer.f> {
    private final Invitation bUd;

    m(Invitation invitation) {
        this.bUd = invitation;
    }

    @Override // com.google.android.gms.common.api.a.x
    public final void YU() {
    }

    @Override // com.google.android.gms.common.api.a.x
    public final /* synthetic */ void z(com.google.android.gms.games.multiplayer.f fVar) {
        fVar.c(this.bUd);
    }
}
