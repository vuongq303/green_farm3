package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.multiplayer.d;

/* JADX INFO: loaded from: classes.dex */
abstract class zzak extends g<d> {
    private zzak(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzak(w wVar, zzaj zzajVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzal(this, status);
    }
}
