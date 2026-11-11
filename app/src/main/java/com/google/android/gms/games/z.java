package com.google.android.gms.games;

/* JADX INFO: loaded from: classes.dex */
final class z extends i {
    private final /* synthetic */ String zzaq;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    z(com.google.android.gms.common.api.w wVar, String str) {
        super(wVar, null);
        this.zzaq = str;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(com.google.android.gms.common.api.c cVar) {
        ((com.google.android.gms.games.internal.i) cVar).a(this.zzaq, this);
    }
}
