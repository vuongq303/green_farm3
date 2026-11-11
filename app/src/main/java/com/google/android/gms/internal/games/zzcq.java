package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.snapshot.f;

/* JADX INFO: loaded from: classes.dex */
abstract class zzcq extends g<f> {
    private zzcq(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzcq(w wVar, zzcj zzcjVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzcr(this, status);
    }
}
