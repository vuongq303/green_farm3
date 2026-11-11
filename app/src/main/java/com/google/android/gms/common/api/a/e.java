package com.google.android.gms.common.api.a;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.c;

/* JADX INFO: loaded from: classes.dex */
public abstract class e<R extends com.google.android.gms.common.api.ae, A extends com.google.android.gms.common.api.c> extends g<R> implements f<R> {
    private final com.google.android.gms.common.api.a<?> mApi;
    private final com.google.android.gms.common.api.d<A> mClientKey;

    protected e(h<R> hVar) {
        super(hVar);
        this.mClientKey = null;
        this.mApi = null;
    }

    protected e(com.google.android.gms.common.api.a<?> aVar, com.google.android.gms.common.api.w wVar) {
        super((com.google.android.gms.common.api.w) com.google.android.gms.common.internal.bf.d(wVar, "GoogleApiClient must not be null"));
        com.google.android.gms.common.internal.bf.d(aVar, "Api must not be null");
        this.mClientKey = (com.google.android.gms.common.api.d<A>) aVar.getClientKey();
        this.mApi = aVar;
    }

    @Deprecated
    protected e(com.google.android.gms.common.api.d<A> dVar, com.google.android.gms.common.api.w wVar) {
        super((com.google.android.gms.common.api.w) com.google.android.gms.common.internal.bf.d(wVar, "GoogleApiClient must not be null"));
        this.mClientKey = (com.google.android.gms.common.api.d) com.google.android.gms.common.internal.bf.checkNotNull(dVar);
        this.mApi = null;
    }

    private void setFailedResult(RemoteException remoteException) {
        setFailedResult(new Status(8, remoteException.getLocalizedMessage(), null));
    }

    protected abstract void doExecute(A a2);

    public final com.google.android.gms.common.api.a<?> getApi() {
        return this.mApi;
    }

    public final com.google.android.gms.common.api.d<A> getClientKey() {
        return this.mClientKey;
    }

    protected void onSetFailedResult(R r) {
    }

    public final void run(A a2) throws DeadObjectException {
        if (a2 instanceof com.google.android.gms.common.internal.bi) {
            a2 = ((com.google.android.gms.common.internal.bi) a2).aaF();
        }
        try {
            doExecute(a2);
        } catch (DeadObjectException e) {
            setFailedResult(e);
            throw e;
        } catch (RemoteException e2) {
            setFailedResult(e2);
        }
    }

    @Override // com.google.android.gms.common.api.a.f
    public final void setFailedResult(Status status) {
        com.google.android.gms.common.internal.bf.b(!status.Sp(), "Failed result must not be success");
        R rCreateFailedResult = createFailedResult(status);
        setResult((com.google.android.gms.common.api.ae) rCreateFailedResult);
        onSetFailedResult(rCreateFailedResult);
    }

    @Override // com.google.android.gms.common.api.a.f
    public /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((com.google.android.gms.common.api.ae) obj);
    }
}
