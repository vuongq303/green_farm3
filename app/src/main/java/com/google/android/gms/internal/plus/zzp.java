package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.plus.c;
import com.google.android.gms.plus.f;

/* JADX INFO: loaded from: classes.dex */
abstract class zzp extends f<c> {
    private zzp(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzp(w wVar, zzk zzkVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzq(this, status);
    }
}
