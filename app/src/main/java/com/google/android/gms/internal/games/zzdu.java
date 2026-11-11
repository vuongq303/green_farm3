package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.multiplayer.turnbased.j;

/* JADX INFO: loaded from: classes.dex */
abstract class zzdu extends g<j> {
    private zzdu(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzdu(w wVar, zzdc zzdcVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzdv(this, status);
    }
}
