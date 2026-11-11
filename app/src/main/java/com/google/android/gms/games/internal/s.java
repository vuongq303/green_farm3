package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class s extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.k> bUf;

    s(com.google.android.gms.common.api.a.f<com.google.android.gms.games.a.k> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void c(DataHolder dataHolder) {
        this.bUf.setResult(new q(dataHolder));
    }
}
