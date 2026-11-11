package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzcs extends g<com.google.android.gms.games.snapshot.g> {
    private zzcs(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzcs(w wVar, zzcj zzcjVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzct(this, status);
    }
}
