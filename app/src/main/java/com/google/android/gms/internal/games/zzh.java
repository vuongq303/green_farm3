package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.a.f;
import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.achievement.d;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
final class zzh extends zzr {
    private final /* synthetic */ String val$id;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzh(zzf zzfVar, String str, w wVar, String str2) {
        super(str, wVar);
        this.val$id = str2;
    }

    @Override // com.google.android.gms.common.api.a.e
    public final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a((f<d>) null, this.val$id);
    }
}
