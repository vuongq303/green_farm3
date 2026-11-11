package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class av extends bj {
    private final /* synthetic */ com.google.android.gms.common.internal.i bKC;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    av(at atVar, bh bhVar, com.google.android.gms.common.internal.i iVar) {
        super(bhVar);
        this.bKC = iVar;
    }

    @Override // com.google.android.gms.common.api.a.bj
    @GuardedBy("mLock")
    public final void zzaq() {
        this.bKC.d(new ConnectionResult(16, null));
    }
}
