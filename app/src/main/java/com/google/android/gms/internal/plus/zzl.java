package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.plus.internal.f;

/* JADX INFO: loaded from: classes.dex */
final class zzl extends zzp {
    private final /* synthetic */ String zzak;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzl(zzj zzjVar, w wVar, String str) {
        super(wVar, null);
        this.zzak = str;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        setCancelToken(((f) cVar).a(this, 0, this.zzak));
    }
}
