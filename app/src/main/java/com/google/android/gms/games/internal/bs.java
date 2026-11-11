package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class bs extends a {
    private final com.google.android.gms.common.api.a.f<Status> bUf;

    public bs(com.google.android.gms.common.api.a.f<Status> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void ZF() {
        this.bUf.setResult(com.google.android.gms.games.n.og(0));
    }
}
