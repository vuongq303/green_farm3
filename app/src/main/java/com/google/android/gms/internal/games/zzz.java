package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.event.c;
import com.google.android.gms.games.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzz extends g<c> {
    private zzz(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzz(w wVar, zzw zzwVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzaa(this, status);
    }
}
