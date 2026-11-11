package com.google.android.gms.internal.games;

import android.content.Intent;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.c;
import com.google.android.gms.games.r;
import com.google.android.gms.games.s;

/* JADX INFO: loaded from: classes.dex */
public final class zzbe implements r {
    public final Intent getCompareProfileIntent(w wVar, Player player) {
        return c.a(wVar).b(new PlayerEntity(player));
    }

    @Override // com.google.android.gms.games.r
    public final Player getCurrentPlayer(w wVar) {
        return c.a(wVar).adj();
    }

    public final String getCurrentPlayerId(w wVar) {
        return c.a(wVar).cc(true);
    }

    public final Intent getPlayerSearchIntent(w wVar) {
        return c.a(wVar).adv();
    }

    public final aa<s> loadConnectedPlayers(w wVar, boolean z) {
        return wVar.a(new zzbl(this, wVar, z));
    }

    public final aa<s> loadInvitablePlayers(w wVar, int i, boolean z) {
        return wVar.a(new zzbh(this, wVar, i, z));
    }

    public final aa<s> loadMoreInvitablePlayers(w wVar, int i) {
        return wVar.a(new zzbi(this, wVar, i));
    }

    public final aa<s> loadMoreRecentlyPlayedWithPlayers(w wVar, int i) {
        return wVar.a(new zzbk(this, wVar, i));
    }

    public final aa<s> loadPlayer(w wVar, String str) {
        return wVar.a(new zzbf(this, wVar, str));
    }

    public final aa<s> loadPlayer(w wVar, String str, boolean z) {
        return wVar.a(new zzbg(this, wVar, str, z));
    }

    public final aa<s> loadRecentlyPlayedWithPlayers(w wVar, int i, boolean z) {
        return wVar.a(new zzbj(this, wVar, i, z));
    }
}
