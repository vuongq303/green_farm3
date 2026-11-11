package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.snapshot.e;

/* JADX INFO: loaded from: classes.dex */
abstract class zzco extends g<e> {
    private zzco(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzco(w wVar, zzcj zzcjVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzcp(this, status);
    }
}
