package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.achievement.c;
import com.google.android.gms.games.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzp extends g<c> {
    private zzp(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzp(w wVar, zzg zzgVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzq(this, status);
    }
}
