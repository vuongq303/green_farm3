package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzbr extends zzbx {
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ int zzjl;
    private final /* synthetic */ int[] zzjv;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbr(zzbo zzboVar, w wVar, int[] iArr, int i, boolean z) {
        super(wVar, null);
        this.zzjv = iArr;
        this.zzjl = i;
        this.zzjg = z;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzjv, this.zzjl, this.zzjg);
    }
}
