package com.google.android.gms.games.internal;

import com.google.android.gms.games.request.GameRequest;

/* JADX INFO: loaded from: classes.dex */
final class bi implements com.google.android.gms.common.api.a.x<com.google.android.gms.games.request.b> {
    private final GameRequest bUH;

    bi(GameRequest gameRequest) {
        this.bUH = gameRequest;
    }

    @Override // com.google.android.gms.common.api.a.x
    public final void YU() {
    }

    @Override // com.google.android.gms.common.api.a.x
    public final /* synthetic */ void z(com.google.android.gms.games.request.b bVar) {
        bVar.d(this.bUH);
    }
}
