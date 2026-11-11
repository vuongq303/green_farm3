package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class bq implements com.google.android.gms.common.internal.o {
    final /* synthetic */ m bLi;

    bq(m mVar) {
        this.bLi = mVar;
    }

    @Override // com.google.android.gms.common.internal.o
    public final void ZF() {
        this.bLi.bJC.handler.post(new br(this));
    }
}
