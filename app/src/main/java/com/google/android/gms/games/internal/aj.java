package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;

/* JADX INFO: loaded from: classes.dex */
final class aj extends a {
    private final com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.turnbased.b> bUc;

    aj(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.turnbased.b> vVar) {
        this.bUc = vVar;
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void it(String str) {
        this.bUc.a(new ai(str));
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void r(DataHolder dataHolder) {
        com.google.android.gms.games.multiplayer.turnbased.c cVar = new com.google.android.gms.games.multiplayer.turnbased.c(dataHolder);
        try {
            TurnBasedMatch turnBasedMatchFreeze = cVar.getCount() > 0 ? cVar.get(0).freeze() : null;
            cVar.release();
            if (turnBasedMatchFreeze != null) {
                this.bUc.a(new al(turnBasedMatchFreeze));
            }
        } catch (Throwable th) {
            cVar.release();
            throw th;
        }
    }
}
