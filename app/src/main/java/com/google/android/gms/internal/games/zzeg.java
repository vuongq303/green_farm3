package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.g;
import com.google.android.gms.games.video.f;

/* JADX INFO: loaded from: classes.dex */
abstract class zzeg extends g<f> {
    private zzeg(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzeg(w wVar, zzdz zzdzVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzeh(this, status);
    }
}
