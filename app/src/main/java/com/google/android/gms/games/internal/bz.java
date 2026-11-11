package com.google.android.gms.games.internal;

/* JADX INFO: loaded from: classes.dex */
final class bz extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.f> bUS;

    public bz(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.f> fVar) {
        this.bUS = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void zzc(int i, String str) {
        this.bUS.setResult(new cm(com.google.android.gms.games.n.og(i), str));
    }
}
