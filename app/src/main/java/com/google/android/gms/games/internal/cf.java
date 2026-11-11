package com.google.android.gms.games.internal;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
final class cf extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.j> bUX;

    public cf(com.google.android.gms.common.api.a.f<com.google.android.gms.games.multiplayer.turnbased.j> fVar) {
        this.bUX = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void e(int i, Bundle bundle) {
        bundle.setClassLoader(getClass().getClassLoader());
        this.bUX.setResult(new aa(com.google.android.gms.games.n.og(i), bundle));
    }
}
