package com.google.android.gms.games.internal;

/* JADX INFO: loaded from: classes.dex */
final class bu extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.f> bUf;

    public bu(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.f> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void ac(int i, String str) {
        this.bUf.setResult(new cy(i, str));
    }
}
