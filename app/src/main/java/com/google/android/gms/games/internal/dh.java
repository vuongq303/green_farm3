package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class dh implements com.google.android.gms.common.api.a.f<Status> {
    private final /* synthetic */ com.google.android.gms.common.internal.o bVj;

    dh(i iVar, com.google.android.gms.common.internal.o oVar) {
        this.bVj = oVar;
    }

    @Override // com.google.android.gms.common.api.a.f
    public final void setFailedResult(Status status) {
        this.bVj.ZF();
    }

    @Override // com.google.android.gms.common.api.a.f
    public final /* synthetic */ void setResult(Status status) {
        this.bVj.ZF();
    }
}
