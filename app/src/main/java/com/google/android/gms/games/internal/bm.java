package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class bm extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.request.e> bUK;

    public bm(com.google.android.gms.common.api.a.f<com.google.android.gms.games.request.e> fVar) {
        this.bUK = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void D(DataHolder dataHolder) {
        this.bUK.setResult(new ci(dataHolder));
    }
}
