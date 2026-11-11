package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzap extends zzaw {
    private final /* synthetic */ String zzbq;
    private final /* synthetic */ int zzjm;
    private final /* synthetic */ int zzjn;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzap(zzam zzamVar, w wVar, String str, int i, int i2) {
        super(wVar, null);
        this.zzbq = str;
        this.zzjm = i;
        this.zzjn = i2;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, (String) null, this.zzbq, this.zzjm, this.zzjn);
    }
}
