package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.a.f;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzas extends zzay {
    private final /* synthetic */ int zzjo;
    private final /* synthetic */ f zzjp;
    private final /* synthetic */ int zzjq;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzas(zzam zzamVar, w wVar, f fVar, int i, int i2) {
        super(wVar, null);
        this.zzjp = fVar;
        this.zzjo = i;
        this.zzjq = i2;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzjp, this.zzjo, this.zzjq);
    }
}
