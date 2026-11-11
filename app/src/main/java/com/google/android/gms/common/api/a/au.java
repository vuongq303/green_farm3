package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class au extends bj {
    private final /* synthetic */ ConnectionResult bKA;
    private final /* synthetic */ at bKB;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    au(at atVar, bh bhVar, ConnectionResult connectionResult) {
        super(bhVar);
        this.bKB = atVar;
        this.bKA = connectionResult;
    }

    @Override // com.google.android.gms.common.api.a.bj
    @GuardedBy("mLock")
    public final void zzaq() {
        this.bKB.bKw.f(this.bKA);
    }
}
