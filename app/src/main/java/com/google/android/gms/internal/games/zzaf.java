package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.m;

/* JADX INFO: loaded from: classes.dex */
abstract class zzaf extends g<m> {
    private zzaf(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzaf(w wVar, zzae zzaeVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzag(this, status);
    }
}
