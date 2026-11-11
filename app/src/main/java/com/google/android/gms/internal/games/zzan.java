package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzan extends zzau {
    private final /* synthetic */ boolean zzjg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzan(zzam zzamVar, w wVar, boolean z) {
        super(wVar, null);
        this.zzjg = z;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).b(this, this.zzjg);
    }
}
