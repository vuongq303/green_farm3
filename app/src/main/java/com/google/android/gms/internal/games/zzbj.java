package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.a.f;
import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.s;

/* JADX INFO: loaded from: classes.dex */
final class zzbj extends zzbm {
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ int zzjs;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbj(zzbe zzbeVar, w wVar, int i, boolean z) {
        super(wVar);
        this.zzjs = i;
        this.zzjg = z;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a((f<s>) this, "played_with", this.zzjs, false, this.zzjg);
    }
}
