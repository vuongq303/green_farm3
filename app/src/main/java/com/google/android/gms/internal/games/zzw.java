package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzw extends zzz {
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ String[] zzjk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzw(zzv zzvVar, w wVar, boolean z, String[] strArr) {
        super(wVar, null);
        this.zzjg = z;
        this.zzjk = strArr;
    }

    @Override // com.google.android.gms.common.api.a.e
    public final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzjg, this.zzjk);
    }
}
