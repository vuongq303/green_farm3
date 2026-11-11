package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class be extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.f> bUF;

    public be(com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.f> fVar) {
        this.bUF = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void M(DataHolder dataHolder) {
        this.bUF.setResult(new ae(dataHolder));
    }
}
