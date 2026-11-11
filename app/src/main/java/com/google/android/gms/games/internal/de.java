package com.google.android.gms.games.internal;

/* JADX INFO: loaded from: classes.dex */
final class de extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.f> bUf;

    public de(com.google.android.gms.common.api.a.f<com.google.android.gms.games.f> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void zza(int i, String str) {
        this.bUf.setResult(new df(com.google.android.gms.games.n.og(i), str));
    }
}
