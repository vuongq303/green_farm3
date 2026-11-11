package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzy extends zzab {
    private final /* synthetic */ String zzk;
    private final /* synthetic */ int zzl;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzy(zzv zzvVar, w wVar, String str, int i) {
        super(wVar, null);
        this.zzk = str;
        this.zzl = i;
    }

    @Override // com.google.android.gms.common.api.a.e
    public final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).zza(this.zzk, this.zzl);
    }
}
