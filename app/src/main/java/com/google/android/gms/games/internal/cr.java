package com.google.android.gms.games.internal;

/* JADX INFO: loaded from: classes.dex */
final class cr extends a {
    private final com.google.android.gms.common.api.a.v<com.google.android.gms.games.video.e> bUc;

    cr(com.google.android.gms.common.api.a.v<com.google.android.gms.games.video.e> vVar) {
        this.bUc = (com.google.android.gms.common.api.a.v) com.google.android.gms.common.internal.bf.d(vVar, "Callback must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void on(int i) {
        this.bUc.a(new cs(i));
    }
}
