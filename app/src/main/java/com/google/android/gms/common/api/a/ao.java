package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class ao extends bj {
    private final /* synthetic */ an bKg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ao(an anVar, bh bhVar) {
        super(bhVar);
        this.bKg = anVar;
    }

    @Override // com.google.android.gms.common.api.a.bj
    public final void zzaq() {
        this.bKg.onConnectionSuspended(1);
    }
}
