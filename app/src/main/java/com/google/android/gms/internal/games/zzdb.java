package com.google.android.gms.internal.games;

import android.content.Intent;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.c;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.turnbased.b;
import com.google.android.gms.games.multiplayer.turnbased.d;
import com.google.android.gms.games.multiplayer.turnbased.e;
import com.google.android.gms.games.multiplayer.turnbased.f;
import com.google.android.gms.games.multiplayer.turnbased.g;
import com.google.android.gms.games.multiplayer.turnbased.h;
import com.google.android.gms.games.multiplayer.turnbased.j;
import com.google.android.gms.games.multiplayer.turnbased.k;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzdb implements e {
    public final aa<g> acceptInvitation(w wVar, String str) {
        return wVar.b(new zzdf(this, wVar, str));
    }

    public final aa<f> cancelMatch(w wVar, String str) {
        return wVar.b(new zzdk(this, str, wVar, str));
    }

    public final aa<g> createMatch(w wVar, d dVar) {
        return wVar.b(new zzdc(this, wVar, dVar));
    }

    public final void declineInvitation(w wVar, String str) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            iVarA.S(str, 1);
        }
    }

    public final void dismissInvitation(w wVar, String str) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            iVarA.Q(str, 1);
        }
    }

    public final void dismissMatch(w wVar, String str) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            iVarA.zzc(str);
        }
    }

    public final aa<k> finishMatch(w wVar, String str) {
        return finishMatch(wVar, str, (byte[]) null, (ParticipantResult[]) null);
    }

    public final aa<k> finishMatch(w wVar, String str, byte[] bArr, List<ParticipantResult> list) {
        return finishMatch(wVar, str, bArr, list == null ? null : (ParticipantResult[]) list.toArray(new ParticipantResult[list.size()]));
    }

    public final aa<k> finishMatch(w wVar, String str, byte[] bArr, ParticipantResult... participantResultArr) {
        return wVar.b(new zzdh(this, wVar, str, bArr, participantResultArr));
    }

    public final Intent getInboxIntent(w wVar) {
        return c.a(wVar).adp();
    }

    public final int getMaxMatchDataSize(w wVar) {
        return c.a(wVar).adx();
    }

    public final Intent getSelectOpponentsIntent(w wVar, int i, int i2) {
        return c.a(wVar).p(i, i2, true);
    }

    public final Intent getSelectOpponentsIntent(w wVar, int i, int i2, boolean z) {
        return c.a(wVar).p(i, i2, z);
    }

    public final aa<h> leaveMatch(w wVar, String str) {
        return wVar.b(new zzdi(this, wVar, str));
    }

    public final aa<h> leaveMatchDuringTurn(w wVar, String str, String str2) {
        return wVar.b(new zzdj(this, wVar, str, str2));
    }

    public final aa<com.google.android.gms.games.multiplayer.turnbased.i> loadMatch(w wVar, String str) {
        return wVar.a(new zzdd(this, wVar, str));
    }

    public final aa<j> loadMatchesByStatus(w wVar, int i, int[] iArr) {
        return wVar.a(new zzdl(this, wVar, i, iArr));
    }

    public final aa<j> loadMatchesByStatus(w wVar, int[] iArr) {
        return loadMatchesByStatus(wVar, 0, iArr);
    }

    public final void registerMatchUpdateListener(w wVar, b bVar) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            iVarA.d(wVar.y(bVar));
        }
    }

    public final aa<g> rematch(w wVar, String str) {
        return wVar.b(new zzde(this, wVar, str));
    }

    public final aa<k> takeTurn(w wVar, String str, byte[] bArr, String str2) {
        return takeTurn(wVar, str, bArr, str2, (ParticipantResult[]) null);
    }

    public final aa<k> takeTurn(w wVar, String str, byte[] bArr, String str2, List<ParticipantResult> list) {
        return takeTurn(wVar, str, bArr, str2, list == null ? null : (ParticipantResult[]) list.toArray(new ParticipantResult[list.size()]));
    }

    public final aa<k> takeTurn(w wVar, String str, byte[] bArr, String str2, ParticipantResult... participantResultArr) {
        return wVar.b(new zzdg(this, wVar, str, bArr, str2, participantResultArr));
    }

    public final void unregisterMatchUpdateListener(w wVar) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            iVarA.adr();
        }
    }
}
