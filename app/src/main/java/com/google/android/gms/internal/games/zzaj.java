package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzaj extends zzak {
    private final /* synthetic */ int zzjl;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaj(zzai zzaiVar, w wVar, int i) {
        super(wVar, null);
        this.zzjl = i;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzjl);
    }
}
