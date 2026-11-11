package com.google.android.gms.common.api.a;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
abstract class ce<T> extends be {
    protected final com.google.android.gms.f.g<T> bLA;

    public ce(int i, com.google.android.gms.f.g<T> gVar) {
        super(i);
        this.bLA = gVar;
    }

    @Override // com.google.android.gms.common.api.a.be
    public void a(ah ahVar, boolean z) {
    }

    @Override // com.google.android.gms.common.api.a.be
    public void b(RuntimeException runtimeException) {
        this.bLA.h(runtimeException);
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void d(m<?> mVar) throws DeadObjectException {
        try {
            e(mVar);
        } catch (DeadObjectException e) {
            zza(be.a(e));
            throw e;
        } catch (RemoteException e2) {
            zza(be.a(e2));
        } catch (RuntimeException e3) {
            b(e3);
        }
    }

    protected abstract void e(m<?> mVar);

    @Override // com.google.android.gms.common.api.a.be
    public void zza(Status status) {
        this.bLA.h(new com.google.android.gms.common.api.q(status));
    }
}
