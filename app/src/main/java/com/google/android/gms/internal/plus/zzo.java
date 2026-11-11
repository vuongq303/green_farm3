package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.plus.internal.f;

/* JADX INFO: loaded from: classes.dex */
final class zzo extends zzp {
    private final /* synthetic */ String[] zzam;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzo(zzj zzjVar, w wVar, String[] strArr) {
        super(wVar, null);
        this.zzam = strArr;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((f) cVar).a(this, this.zzam);
    }
}
