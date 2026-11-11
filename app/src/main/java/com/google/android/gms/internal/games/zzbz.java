package com.google.android.gms.internal.games;

import android.content.Intent;
import com.google.android.gms.common.api.a.v;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.b;
import com.google.android.gms.games.multiplayer.realtime.c;
import com.google.android.gms.games.multiplayer.realtime.d;
import com.google.android.gms.games.multiplayer.realtime.e;
import com.google.android.gms.games.multiplayer.realtime.h;
import com.google.android.gms.games.multiplayer.realtime.j;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzbz implements c {
    private static v<j> zza(w wVar, e eVar) {
        return eVar.aeG() != null ? wVar.y(eVar.aeG()) : wVar.y(eVar.aeF());
    }

    private static <L> v<L> zza(w wVar, L l) {
        if (l == null) {
            return null;
        }
        return wVar.y(l);
    }

    private static v<h> zzb(w wVar, e eVar) {
        return eVar.aeI() != null ? zza(wVar, eVar.aeI()) : zza(wVar, eVar.aeH());
    }

    private static v<b> zzc(w wVar, e eVar) {
        return eVar.aeK() != null ? wVar.y(eVar.aeK()) : wVar.y(eVar.aeJ());
    }

    public final void create(w wVar, e eVar) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA == null) {
            return;
        }
        iVarA.b(zza(wVar, eVar), zzb(wVar, eVar), zzc(wVar, eVar), eVar);
    }

    public final void declineInvitation(w wVar, String str) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.S(str, 0);
        }
    }

    public final void dismissInvitation(w wVar, String str) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.Q(str, 0);
        }
    }

    public final Intent getSelectOpponentsIntent(w wVar, int i, int i2) {
        return com.google.android.gms.games.c.a(wVar).r(i, i2, true);
    }

    public final Intent getSelectOpponentsIntent(w wVar, int i, int i2, boolean z) {
        return com.google.android.gms.games.c.a(wVar).r(i, i2, z);
    }

    public final Intent getWaitingRoomIntent(w wVar, Room room, int i) {
        return com.google.android.gms.games.c.a(wVar).b(room, i);
    }

    public final void join(w wVar, e eVar) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA == null) {
            return;
        }
        iVarA.d(zza(wVar, eVar), zzb(wVar, eVar), zzc(wVar, eVar), eVar);
    }

    public final void leave(w wVar, j jVar, String str) {
        i iVarA = com.google.android.gms.games.c.a(wVar, false);
        if (iVarA != null) {
            iVarA.a(wVar.y(jVar), str);
        }
    }

    public final int sendReliableMessage(w wVar, d dVar, byte[] bArr, String str, String str2) {
        return com.google.android.gms.games.c.a(wVar).b(zza(wVar, dVar), bArr, str, str2);
    }

    public final int sendUnreliableMessage(w wVar, byte[] bArr, String str, String str2) {
        return com.google.android.gms.games.c.a(wVar).a(bArr, str, new String[]{str2});
    }

    public final int sendUnreliableMessage(w wVar, byte[] bArr, String str, List<String> list) {
        return com.google.android.gms.games.c.a(wVar).a(bArr, str, (String[]) list.toArray(new String[list.size()]));
    }

    public final int sendUnreliableMessageToOthers(w wVar, byte[] bArr, String str) {
        return com.google.android.gms.games.c.a(wVar).f(bArr, str);
    }
}
