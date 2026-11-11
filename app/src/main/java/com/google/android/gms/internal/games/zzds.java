package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.multiplayer.turnbased.i;

/* JADX INFO: loaded from: classes.dex */
abstract class zzds extends g<i> {
    private zzds(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzds(w wVar, zzdc zzdcVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzdt(this, status);
    }
}
