package com.google.android.gms.games;

import android.content.Context;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
class h extends com.google.android.gms.common.api.b<com.google.android.gms.games.internal.i, d> {
    private h() {
    }

    /* synthetic */ h(x xVar) {
        this();
    }

    @Override // com.google.android.gms.common.api.b
    public /* synthetic */ com.google.android.gms.common.api.l a(Context context, Looper looper, com.google.android.gms.common.internal.r rVar, d dVar, com.google.android.gms.common.api.y yVar, com.google.android.gms.common.api.z zVar) {
        d dVar2 = dVar;
        return new com.google.android.gms.games.internal.i(context, looper, rVar, dVar2 == null ? new e(null).aco() : dVar2, yVar, zVar);
    }

    @Override // com.google.android.gms.common.api.k
    public int getPriority() {
        return 1;
    }
}
