package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.plus.internal.f;

/* JADX INFO: loaded from: classes.dex */
final class zzk extends zzp {
    private final /* synthetic */ int zzaj;
    private final /* synthetic */ String zzak;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzk(zzj zzjVar, w wVar, int i, String str) {
        super(wVar, null);
        this.zzaj = i;
        this.zzak = str;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        setCancelToken(((f) cVar).a(this, this.zzaj, this.zzak));
    }
}
