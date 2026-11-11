package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.multiplayer.turnbased.k;

/* JADX INFO: loaded from: classes.dex */
abstract class zzdw extends g<k> {
    private zzdw(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzdw(w wVar, zzdc zzdcVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzdx(this, status);
    }
}
