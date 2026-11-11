package com.google.android.gms.internal.auth;

import com.google.android.gms.auth.a.f;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.api.a.e;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class zzl extends e<ae, zzu> {
    private final /* synthetic */ boolean zzag;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzl(zzk zzkVar, a aVar, w wVar, boolean z) {
        super((a<?>) aVar, wVar);
        this.zzag = z;
    }

    @Override // com.google.android.gms.common.api.a.g
    protected final ae createFailedResult(Status status) {
        return new zzs(status);
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((f) ((zzu) cVar).getService()).zze(this.zzag);
        setResult(new zzs(Status.bIO));
    }
}
