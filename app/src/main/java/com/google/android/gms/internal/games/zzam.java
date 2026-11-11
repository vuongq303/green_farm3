package com.google.android.gms.internal.games;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.a.f;
import com.google.android.gms.games.a.j;
import com.google.android.gms.games.a.k;
import com.google.android.gms.games.a.l;
import com.google.android.gms.games.a.m;
import com.google.android.gms.games.a.n;
import com.google.android.gms.games.c;
import com.google.android.gms.games.internal.di;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
public final class zzam implements j {
    public final Intent getAllLeaderboardsIntent(w wVar) {
        return c.a(wVar).adn();
    }

    public final Intent getLeaderboardIntent(w wVar, String str) {
        return getLeaderboardIntent(wVar, str, -1);
    }

    public final Intent getLeaderboardIntent(w wVar, String str, int i) {
        return getLeaderboardIntent(wVar, str, i, -1);
    }

    public final Intent getLeaderboardIntent(w wVar, String str, int i, int i2) {
        return c.a(wVar).y(str, i, i2);
    }

    public final aa<l> loadCurrentPlayerLeaderboardScore(w wVar, String str, int i, int i2) {
        return wVar.a(new zzap(this, wVar, str, i, i2));
    }

    public final aa<k> loadLeaderboardMetadata(w wVar, String str, boolean z) {
        return wVar.a(new zzao(this, wVar, str, z));
    }

    public final aa<k> loadLeaderboardMetadata(w wVar, boolean z) {
        return wVar.a(new zzan(this, wVar, z));
    }

    public final aa<m> loadMoreScores(w wVar, f fVar, int i, int i2) {
        return wVar.a(new zzas(this, wVar, fVar, i, i2));
    }

    public final aa<m> loadPlayerCenteredScores(w wVar, String str, int i, int i2, int i3) {
        return loadPlayerCenteredScores(wVar, str, i, i2, i3, false);
    }

    public final aa<m> loadPlayerCenteredScores(w wVar, String str, int i, int i2, int i3, boolean z) {
        return wVar.a(new zzar(this, wVar, str, i, i2, i3, z));
    }

    public final aa<m> loadTopScores(w wVar, String str, int i, int i2, int i3) {
        return loadTopScores(wVar, str, i, i2, i3, false);
    }

    public final aa<m> loadTopScores(w wVar, String str, int i, int i2, int i3, boolean z) {
        return wVar.a(new zzaq(this, wVar, str, i, i2, i3, z));
    }

    public final void submitScore(w wVar, String str, long j) {
        submitScore(wVar, str, j, null);
    }

    public final void submitScore(w wVar, String str, long j, String str2) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            try {
                iVarA.a((com.google.android.gms.common.api.a.f<n>) null, str, j, str2);
            } catch (RemoteException e) {
                di.ax("LeaderboardsImpl", "service died");
            }
        }
    }

    public final aa<n> submitScoreImmediate(w wVar, String str, long j) {
        return submitScoreImmediate(wVar, str, j, null);
    }

    public final aa<n> submitScoreImmediate(w wVar, String str, long j, String str2) {
        return wVar.b(new zzat(this, wVar, str, j, str2));
    }
}
