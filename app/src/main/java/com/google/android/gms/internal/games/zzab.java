package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzab extends g<ae> {
    private zzab(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzab(w wVar, zzw zzwVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public ae createFailedResult(Status status) {
        return new zzac(this, status);
    }
}
