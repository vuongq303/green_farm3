package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.c;
import com.google.android.gms.games.event.b;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
public final class zzv implements b {
    public final void increment(w wVar, String str, int i) {
        i iVarB = c.b(wVar, false);
        if (iVarB == null) {
            return;
        }
        if (iVarB.isConnected()) {
            iVarB.zza(str, i);
        } else {
            wVar.b(new zzy(this, wVar, str, i));
        }
    }

    public final aa<com.google.android.gms.games.event.c> load(w wVar, boolean z) {
        return wVar.a(new zzx(this, wVar, z));
    }

    public final aa<com.google.android.gms.games.event.c> loadByIds(w wVar, boolean z, String... strArr) {
        return wVar.a(new zzw(this, wVar, z, strArr));
    }
}
