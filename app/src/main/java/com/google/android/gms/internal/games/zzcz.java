package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.stats.c;

/* JADX INFO: loaded from: classes.dex */
abstract class zzcz extends g<c> {
    private zzcz(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzcz(w wVar, zzcy zzcyVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzda(this, status);
    }
}
