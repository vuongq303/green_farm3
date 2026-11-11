package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzcd extends zzce {
    private final /* synthetic */ int zzjl;
    private final /* synthetic */ int zzjy;
    private final /* synthetic */ int zzjz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcd(zzca zzcaVar, w wVar, int i, int i2, int i3) {
        super(wVar, null);
        this.zzjy = i;
        this.zzjz = i2;
        this.zzjl = i3;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzjy, this.zzjz, this.zzjl);
    }
}
