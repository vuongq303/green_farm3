package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzdo extends g<com.google.android.gms.games.multiplayer.turnbased.g> {
    private zzdo(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzdo(w wVar, zzdc zzdcVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzdp(this, status);
    }
}
