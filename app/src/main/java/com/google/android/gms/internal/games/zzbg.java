package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzbg extends zzbm {
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ String zzjr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbg(zzbe zzbeVar, w wVar, String str, boolean z) {
        super(wVar);
        this.zzjr = str;
        this.zzjg = z;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzjr, this.zzjg);
    }
}
