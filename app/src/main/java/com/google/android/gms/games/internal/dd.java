package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class dd extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.m> bUf;

    dd(com.google.android.gms.common.api.a.f<com.google.android.gms.games.m> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void g(DataHolder dataHolder) {
        this.bUf.setResult(new x(dataHolder));
    }
}
