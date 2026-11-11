package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzdl extends zzdu {
    private final /* synthetic */ int zzkm;
    private final /* synthetic */ int[] zzkn;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdl(zzdb zzdbVar, w wVar, int i, int[] iArr) {
        super(wVar, null);
        this.zzkm = i;
        this.zzkn = iArr;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzkm, this.zzkn);
    }
}
