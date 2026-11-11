package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.a.m;
import com.google.android.gms.games.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzay extends g<m> {
    private zzay(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzay(w wVar, zzan zzanVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzaz(this, status);
    }
}
