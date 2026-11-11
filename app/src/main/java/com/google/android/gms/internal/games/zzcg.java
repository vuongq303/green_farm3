package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.request.e;

/* JADX INFO: loaded from: classes.dex */
abstract class zzcg extends g<e> {
    private zzcg(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzcg(w wVar, zzcb zzcbVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzch(this, status);
    }
}
