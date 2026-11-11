package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class bw extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.g> bUQ;

    public bw(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.g> fVar) {
        this.bUQ = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void G(DataHolder dataHolder) {
        this.bUQ.setResult(new ah(dataHolder));
    }
}
