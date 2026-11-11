package com.google.android.gms.common.api.a;

/* JADX INFO: Add missing generic type declarations: [TResult] */
/* JADX INFO: loaded from: classes.dex */
final class aj<TResult> implements com.google.android.gms.f.b<TResult> {
    private final /* synthetic */ ah bJZ;
    private final /* synthetic */ com.google.android.gms.f.g bKa;

    aj(ah ahVar, com.google.android.gms.f.g gVar) {
        this.bJZ = ahVar;
        this.bKa = gVar;
    }

    @Override // com.google.android.gms.f.b
    public final void a(com.google.android.gms.f.f<TResult> fVar) {
        this.bJZ.bJX.remove(this.bKa);
    }
}
