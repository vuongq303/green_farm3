package com.google.android.gms.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;

/* JADX INFO: loaded from: classes.dex */
abstract class i extends g<f> {
    private i(com.google.android.gms.common.api.w wVar) {
        super(wVar);
    }

    /* synthetic */ i(com.google.android.gms.common.api.w wVar, x xVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new ab(this, status);
    }
}
