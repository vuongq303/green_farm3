package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzbp extends zzbt {
    private final /* synthetic */ String zzjt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbp(zzbo zzboVar, w wVar, String str) {
        super(wVar, null);
        this.zzjt = str;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).h(this, this.zzjt);
    }
}
