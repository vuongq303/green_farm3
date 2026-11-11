package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.Invitation;

/* JADX INFO: loaded from: classes.dex */
final class l extends a {
    private final com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.f> bUc;

    l(com.google.android.gms.common.api.a.v<com.google.android.gms.games.multiplayer.f> vVar) {
        this.bUc = vVar;
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void ir(String str) {
        this.bUc.a(new n(str));
    }

    @Override // com.google.android.gms.games.internal.a, com.google.android.gms.games.internal.dj
    public final void l(DataHolder dataHolder) {
        com.google.android.gms.games.multiplayer.a aVar = new com.google.android.gms.games.multiplayer.a(dataHolder);
        try {
            Invitation invitationFreeze = aVar.getCount() > 0 ? aVar.get(0).freeze() : null;
            aVar.release();
            if (invitationFreeze != null) {
                this.bUc.a(new m(invitationFreeze));
            }
        } catch (Throwable th) {
            aVar.release();
            throw th;
        }
    }
}
