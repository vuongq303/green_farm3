package com.google.android.gms.internal.games;

import android.content.Intent;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.multiplayer.c;
import com.google.android.gms.games.multiplayer.d;
import com.google.android.gms.games.multiplayer.f;

/* JADX INFO: loaded from: classes.dex */
public final class zzai implements c {
    public final Intent getInvitationInboxIntent(w wVar) {
        return com.google.android.gms.games.c.a(wVar).adq();
    }

    public final aa<d> loadInvitations(w wVar) {
        return loadInvitations(wVar, 0);
    }

    public final aa<d> loadInvitations(w wVar, int i) {
        return wVar.a(new zzaj(this, wVar, i));
    }

    public final void registerInvitationListener(w wVar, f fVar) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.b(wVar.y(fVar));
        }
    }

    public final void unregisterInvitationListener(w wVar) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.zzab();
        }
    }
}
