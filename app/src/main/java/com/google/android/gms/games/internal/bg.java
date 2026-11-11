package com.google.android.gms.games.internal;

/* JADX INFO: loaded from: classes.dex */
final class bg extends a {
    private final com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.realtime.d> bUG;

    public bg(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.realtime.d> vVar) {
        this.bUG = vVar;
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void b(int i, int i2, String str) {
        if (this.bUG != null) {
            this.bUG.a(new bf(i, i2, str));
        }
    }
}
