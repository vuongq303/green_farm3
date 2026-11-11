package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzat extends zzba {
    private final /* synthetic */ String zzbq;
    private final /* synthetic */ long zzbt;
    private final /* synthetic */ String zzbu;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzat(zzam zzamVar, w wVar, String str, long j, String str2) {
        super(wVar);
        this.zzbq = str;
        this.zzbt = j;
        this.zzbu = str2;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzbq, this.zzbt, this.zzbu);
    }
}
