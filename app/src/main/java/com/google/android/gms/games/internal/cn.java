package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class cn extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.c> bUf;

    cn(com.google.android.gms.common.api.a.f<com.google.android.gms.games.video.c> fVar) {
        this.bUf = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void zza(int i, boolean z) {
        this.bUf.setResult(new co(new Status(i), z));
    }
}
