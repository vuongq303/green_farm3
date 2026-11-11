package com.google.android.gms.common.api.a;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public final class ct<ResultT> extends be {
    private final com.google.android.gms.f.g<ResultT> bLA;
    private final ad<com.google.android.gms.common.api.c, ResultT> bLW;
    private final ac bLX;

    public ct(int i, ad<com.google.android.gms.common.api.c, ResultT> adVar, com.google.android.gms.f.g<ResultT> gVar, ac acVar) {
        super(i);
        this.bLA = gVar;
        this.bLW = adVar;
        this.bLX = acVar;
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void a(ah ahVar, boolean z) {
        ahVar.a(this.bLA, z);
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void b(RuntimeException runtimeException) {
        this.bLA.h(runtimeException);
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void d(m<?> mVar) throws DeadObjectException {
        try {
            this.bLW.doExecute(mVar.Zb(), this.bLA);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zza(be.a(e2));
        } catch (RuntimeException e3) {
            b(e3);
        }
    }

    public final Feature[] getRequiredFeatures() {
        return this.bLW.zzca();
    }

    public final boolean shouldAutoResolveMissingFeatures() {
        return this.bLW.shouldAutoResolveMissingFeatures();
    }

    @Override // com.google.android.gms.common.api.a.be
    public final void zza(Status status) {
        this.bLA.h(this.bLX.getException(status));
    }
}
