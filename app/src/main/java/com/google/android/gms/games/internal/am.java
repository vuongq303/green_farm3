package com.google.android.gms.games.internal;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;

/* JADX INFO: loaded from: classes.dex */
final class am implements com.google.android.gms.common.api.a.x<com.google.android.gms.games.multiplayer.realtime.b> {
    private final RealTimeMessage bUw;

    am(RealTimeMessage realTimeMessage) {
        this.bUw = realTimeMessage;
    }

    @Override // com.google.android.gms.common.api.a.x
    public final void YU() {
    }

    @Override // com.google.android.gms.common.api.a.x
    public final /* synthetic */ void z(com.google.android.gms.games.multiplayer.realtime.b bVar) {
        bVar.a(this.bUw);
    }
}
