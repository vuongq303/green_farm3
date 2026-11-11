package com.google.android.gms.games.internal;

/* JADX INFO: loaded from: classes.dex */
final class u implements com.google.android.gms.common.api.a.x<com.google.android.gms.games.multiplayer.realtime.j> {
    private final int statusCode;
    private final String zzgt;

    u(int i, String str) {
        this.statusCode = i;
        this.zzgt = str;
    }

    @Override // com.google.android.gms.common.api.a.x
    public final void YU() {
    }

    @Override // com.google.android.gms.common.api.a.x
    public final /* synthetic */ void z(com.google.android.gms.games.multiplayer.realtime.j jVar) {
        jVar.ab(this.statusCode, this.zzgt);
    }
}
