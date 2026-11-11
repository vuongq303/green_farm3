package com.google.android.gms.games.internal;

/* JADX INFO: loaded from: classes.dex */
final class ck extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.d> bUf;

    ck(com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.d> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void zzb(int i, String str) {
        this.bUf.setResult(new cg(i, str));
    }
}
