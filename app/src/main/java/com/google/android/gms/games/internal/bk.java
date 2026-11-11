package com.google.android.gms.games.internal;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
final class bk extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.request.d> bUJ;

    public bk(com.google.android.gms.common.api.a.f<com.google.android.gms.games.request.d> fVar) {
        this.bUJ = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void f(int i, Bundle bundle) {
        bundle.setClassLoader(getClass().getClassLoader());
        this.bUJ.setResult(new af(com.google.android.gms.games.n.og(i), bundle));
    }
}
