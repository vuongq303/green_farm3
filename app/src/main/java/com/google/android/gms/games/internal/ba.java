package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class ba extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.d> bUC;

    public ba(com.google.android.gms.common.api.a.f<com.google.android.gms.games.quest.d> fVar) {
        this.bUC = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void J(DataHolder dataHolder) {
        this.bUC.setResult(new cj(dataHolder));
    }
}
