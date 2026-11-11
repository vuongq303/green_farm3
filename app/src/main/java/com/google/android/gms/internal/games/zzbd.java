package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.w;
import com.google.android.gms.games.c;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.o;

/* JADX INFO: loaded from: classes.dex */
public final class zzbd implements o {
    public final void clear(w wVar, int i) {
        i iVarA = c.a(wVar, false);
        if (iVarA != null) {
            iVarA.zzl(i);
        }
    }

    public final void clearAll(w wVar) {
        clear(wVar, 63);
    }
}
