package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzbs extends zzbx {
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ String[] zzjw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbs(zzbo zzboVar, w wVar, boolean z, String[] strArr) {
        super(wVar, null);
        this.zzjg = z;
        this.zzjw = strArr;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).b(this, this.zzjg, this.zzjw);
    }
}
