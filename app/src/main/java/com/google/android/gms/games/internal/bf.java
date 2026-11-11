package com.google.android.gms.games.internal;

/* JADX INFO: loaded from: classes.dex */
final class bf implements com.google.android.gms.common.api.a.x<com.google.android.gms.games.multiplayer.realtime.d> {
    private final int statusCode;
    private final int token;
    private final String zzhq;

    bf(int i, int i2, String str) {
        this.statusCode = i;
        this.token = i2;
        this.zzhq = str;
    }

    @Override // com.google.android.gms.common.api.a.x
    public final void YU() {
    }

    @Override // com.google.android.gms.common.api.a.x
    public final /* synthetic */ void z(com.google.android.gms.games.multiplayer.realtime.d dVar) {
        com.google.android.gms.games.multiplayer.realtime.d dVar2 = dVar;
        if (dVar2 != null) {
            dVar2.c(this.statusCode, this.token, this.zzhq);
        }
    }
}
