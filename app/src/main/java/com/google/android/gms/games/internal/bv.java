package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;

/* JADX INFO: loaded from: classes.dex */
final class bv extends a {
    private final com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.h> bUP;

    public bv(com.google.android.gms.common.api.a.f<com.google.android.gms.games.snapshot.h> fVar) {
        this.bUP = (com.google.android.gms.common.api.a.f) com.google.android.gms.common.internal.bf.d(fVar, "Holder must not be null");
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void a(DataHolder dataHolder, Contents contents) {
        this.bUP.setResult(new an(dataHolder, contents));
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void a(DataHolder dataHolder, String str, Contents contents, Contents contents2, Contents contents3) {
        this.bUP.setResult(new an(dataHolder, str, contents, contents2, contents3));
    }
}
