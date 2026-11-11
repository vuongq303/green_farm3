package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzm extends zzr {
    private final /* synthetic */ String val$id;
    private final /* synthetic */ int zzjh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzm(zzf zzfVar, String str, w wVar, String str2, int i) {
        super(str, wVar);
        this.val$id = str2;
        this.zzjh = i;
    }

    @Override // com.google.android.gms.common.api.a.e
    public final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.val$id, this.zzjh);
    }
}
