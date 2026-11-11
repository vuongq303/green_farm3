package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzcb extends zzcg {
    private final /* synthetic */ String[] zzjx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcb(zzca zzcaVar, w wVar, String[] strArr) {
        super(wVar, null);
        this.zzjx = strArr;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzjx);
    }
}
