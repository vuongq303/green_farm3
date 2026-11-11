package com.google.android.gms.internal.auth;

import android.accounts.Account;
import com.google.android.gms.auth.a.f;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.api.a.e;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class zzo extends e<ae, zzu> {
    private final /* synthetic */ Account zzr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzo(zzk zzkVar, a aVar, w wVar, Account account) {
        super((a<?>) aVar, wVar);
        this.zzr = account;
    }

    @Override // com.google.android.gms.common.api.a.g
    protected final ae createFailedResult(Status status) {
        return new zzt(status);
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((f) ((zzu) cVar).getService()).a(new zzp(this), this.zzr);
    }
}
