package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzdj extends zzdq {
    private final /* synthetic */ String zzew;
    private final /* synthetic */ String zzkk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdj(zzdb zzdbVar, w wVar, String str, String str2) {
        super(wVar, null);
        this.zzew = str;
        this.zzkk = str2;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzew, this.zzkk);
    }
}
