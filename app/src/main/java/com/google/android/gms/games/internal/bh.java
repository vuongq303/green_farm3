package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.request.GameRequest;

/* JADX INFO: loaded from: classes.dex */
final class bh extends a {
    private final com.google.android.gms.common.api.a.v<com.google.android.gms.games.request.b> bUc;

    bh(com.google.android.gms.common.api.a.v<com.google.android.gms.games.request.b> vVar) {
        this.bUc = vVar;
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void is(String str) {
        this.bUc.a(new bj(str));
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void m(DataHolder dataHolder) {
        com.google.android.gms.games.request.a aVar = new com.google.android.gms.games.request.a(dataHolder);
        try {
            GameRequest gameRequestFreeze = aVar.getCount() > 0 ? aVar.get(0).freeze() : null;
            aVar.release();
            if (gameRequestFreeze != null) {
                this.bUc.a(new bi(gameRequestFreeze));
            }
        } catch (Throwable th) {
            aVar.release();
            throw th;
        }
    }
}
