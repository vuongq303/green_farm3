package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.snapshot.h;

/* JADX INFO: loaded from: classes.dex */
abstract class zzcu extends g<h> {
    private zzcu(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzcu(w wVar, zzcj zzcjVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzcv(this, status);
    }
}
