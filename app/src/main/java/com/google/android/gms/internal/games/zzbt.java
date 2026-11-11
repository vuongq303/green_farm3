package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.quest.d;

/* JADX INFO: loaded from: classes.dex */
abstract class zzbt extends g<d> {
    private zzbt(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzbt(w wVar, zzbp zzbpVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzbu(this, status);
    }
}
