package com.google.android.gms.common.api.a;

import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import java.lang.ref.WeakReference;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class ck<R extends com.google.android.gms.common.api.ae> extends com.google.android.gms.common.api.ai<R> implements com.google.android.gms.common.api.af<R> {
    private final cm bLH;
    private final WeakReference<com.google.android.gms.common.api.w> zzfc;
    private com.google.android.gms.common.api.ah<? super R, ? extends com.google.android.gms.common.api.ae> bLC = null;
    private ck<? extends com.google.android.gms.common.api.ae> bLD = null;
    private volatile com.google.android.gms.common.api.ag<? super R> bLE = null;
    private com.google.android.gms.common.api.aa<R> bLF = null;
    private final Object zzfa = new Object();
    private Status bLG = null;
    private boolean bLI = false;

    public ck(WeakReference<com.google.android.gms.common.api.w> weakReference) {
        com.google.android.gms.common.internal.bf.d(weakReference, "GoogleApiClient reference must not be null");
        this.zzfc = weakReference;
        com.google.android.gms.common.api.w wVar = this.zzfc.get();
        this.bLH = new cm(this, wVar != null ? wVar.getLooper() : Looper.getMainLooper());
    }

    @GuardedBy("mSyncToken")
    private final boolean ZH() {
        return (this.bLE == null || this.zzfc.get() == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void d(Status status) {
        synchronized (this.zzfa) {
            this.bLG = status;
            zze(this.bLG);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(com.google.android.gms.common.api.ae aeVar) {
        if (aeVar instanceof com.google.android.gms.common.api.ac) {
            try {
                ((com.google.android.gms.common.api.ac) aeVar).release();
            } catch (RuntimeException e) {
                String strValueOf = String.valueOf(aeVar);
                Log.w("TransformedResultImpl", new StringBuilder(String.valueOf(strValueOf).length() + 18).append("Unable to release ").append(strValueOf).toString(), e);
            }
        }
    }

    @GuardedBy("mSyncToken")
    private final void zzcb() {
        if (this.bLC == null && this.bLE == null) {
            return;
        }
        com.google.android.gms.common.api.w wVar = this.zzfc.get();
        if (!this.bLI && this.bLC != null && wVar != null) {
            wVar.a(this);
            this.bLI = true;
        }
        if (this.bLG != null) {
            zze(this.bLG);
        } else if (this.bLF != null) {
            this.bLF.setResultCallback(this);
        }
    }

    private final void zze(Status status) {
        synchronized (this.zzfa) {
            if (this.bLC != null) {
                Status statusB = this.bLC.b(status);
                com.google.android.gms.common.internal.bf.d(statusB, "onFailure must not return null");
                this.bLD.d(statusB);
            } else if (ZH()) {
                this.bLE.onFailure(status);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(com.google.android.gms.common.api.aa<?> aaVar) {
        synchronized (this.zzfa) {
            this.bLF = aaVar;
            zzcb();
        }
    }

    @Override // com.google.android.gms.common.api.af
    public final void a(R r) {
        synchronized (this.zzfa) {
            if (!r.getStatus().Sp()) {
                d(r.getStatus());
                zzb(r);
            } else if (this.bLC != null) {
                ca.ZE().submit(new cl(this, r));
            } else if (ZH()) {
                this.bLE.b(r);
            }
        }
    }

    @Override // com.google.android.gms.common.api.ai
    public final <S extends com.google.android.gms.common.api.ae> com.google.android.gms.common.api.ai<S> then(com.google.android.gms.common.api.ah<? super R, ? extends S> ahVar) {
        ck<? extends com.google.android.gms.common.api.ae> ckVar;
        synchronized (this.zzfa) {
            com.google.android.gms.common.internal.bf.a(this.bLC == null, "Cannot call then() twice.");
            com.google.android.gms.common.internal.bf.a(this.bLE == null, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.bLC = ahVar;
            ckVar = new ck<>(this.zzfc);
            this.bLD = ckVar;
            zzcb();
        }
        return ckVar;
    }

    final void zzcc() {
        this.bLE = null;
    }
}
