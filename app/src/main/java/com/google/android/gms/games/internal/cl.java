package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class cl extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.c> bUf;

    cl(com.google.android.gms.common.api.a.f<com.google.android.gms.games.achievement.c> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void a(DataHolder dataHolder) {
        this.bUf.setResult(new v(dataHolder));
    }
}
