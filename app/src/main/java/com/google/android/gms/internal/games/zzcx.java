package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.stats.b;
import com.google.android.gms.games.stats.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzcx implements b {
    public final aa<c> loadPlayerStats(w wVar, boolean z) {
        return wVar.a(new zzcy(this, wVar, z));
    }
}
