package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.plus.f;

/* JADX INFO: loaded from: classes.dex */
abstract class zzg extends f<Status> {
    private zzg(w wVar) {
        super(wVar);
    }

    /* synthetic */ zzg(w wVar, zzf zzfVar) {
        this(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return status;
    }
}
