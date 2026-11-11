package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.a.n;
import com.google.android.gms.games.g;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzba extends g<n> {
    protected zzba(w wVar) {
        super(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    public /* synthetic */ ae createFailedResult(Status status) {
        return new zzbb(this, status);
    }
}
