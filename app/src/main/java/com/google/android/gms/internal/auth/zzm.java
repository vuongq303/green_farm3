package com.google.android.gms.internal.auth;

import com.google.android.gms.auth.a.c;
import com.google.android.gms.auth.a.f;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.api.a.e;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class zzm extends e<c, zzu> {
    private final /* synthetic */ String zzt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzm(zzk zzkVar, a aVar, w wVar, String str) {
        super((a<?>) aVar, wVar);
        this.zzt = str;
    }

    @Override // com.google.android.gms.common.api.a.g
    protected final /* synthetic */ ae createFailedResult(Status status) {
        return new zzr(status, null);
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(com.google.android.gms.common.api.c cVar) {
        ((f) ((zzu) cVar).getService()).a(new zzn(this), this.zzt);
    }
}
