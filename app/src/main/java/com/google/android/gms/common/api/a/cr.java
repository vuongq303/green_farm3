package com.google.android.gms.common.api.a;

import android.os.DeadObjectException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.e;

/* JADX INFO: loaded from: classes.dex */
public final class cr<A extends e<? extends com.google.android.gms.common.api.ae, com.google.android.gms.common.api.c>> extends be {
    private final A bLT;

    public cr(int i, A a2) {
        super(i);
        this.bLT = a2;
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void a(ah ahVar, boolean z) {
        ahVar.a(this.bLT, z);
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void b(RuntimeException runtimeException) {
        String simpleName = runtimeException.getClass().getSimpleName();
        String localizedMessage = runtimeException.getLocalizedMessage();
        this.bLT.setFailedResult(new Status(10, new StringBuilder(String.valueOf(simpleName).length() + 2 + String.valueOf(localizedMessage).length()).append(simpleName).append(": ").append(localizedMessage).toString()));
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void d(m<?> mVar) throws DeadObjectException {
        try {
            this.bLT.run(mVar.Zb());
        } catch (RuntimeException e) {
            b(e);
        }
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void zza(Status status) {
        this.bLT.setFailedResult(status);
    }
}
