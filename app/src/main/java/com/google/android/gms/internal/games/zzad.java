package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.c;
import com.google.android.gms.games.l;
import com.google.android.gms.games.m;

/* JADX INFO: loaded from: classes.dex */
public final class zzad implements l {
    public final Game getCurrentGame(w wVar) {
        return c.a(wVar).adl();
    }

    public final aa<m> loadGame(w wVar) {
        return wVar.a(new zzae(this, wVar));
    }
}
