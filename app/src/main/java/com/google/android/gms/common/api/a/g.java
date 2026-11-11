package com.google.android.gms.common.api.a;

import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class g<R extends com.google.android.gms.common.api.ae> extends com.google.android.gms.common.api.aa<R> {
    static final ThreadLocal<Boolean> zzez = new dd();
    private i mResultGuardian;
    private Status mStatus;
    private R zzdm;
    private final Object zzfa;
    private final h<R> zzfb;
    private final WeakReference<com.google.android.gms.common.api.w> zzfc;
    private final CountDownLatch zzfd;
    private final ArrayList<com.google.android.gms.common.api.ab> zzfe;
    private com.google.android.gms.common.api.af<? super R> zzff;
    private final AtomicReference<cq> zzfg;
    private volatile boolean zzfh;
    private boolean zzfi;
    private boolean zzfj;
    private com.google.android.gms.common.internal.ak zzfk;
    private volatile ck<R> zzfl;
    private boolean zzfm;

    @Deprecated
    g() {
        this.zzfa = new Object();
        this.zzfd = new CountDownLatch(1);
        this.zzfe = new ArrayList<>();
        this.zzfg = new AtomicReference<>();
        this.zzfm = false;
        this.zzfb = new h<>(Looper.getMainLooper());
        this.zzfc = new WeakReference<>(null);
    }

    @Deprecated
    protected g(Looper looper) {
        this.zzfa = new Object();
        this.zzfd = new CountDownLatch(1);
        this.zzfe = new ArrayList<>();
        this.zzfg = new AtomicReference<>();
        this.zzfm = false;
        this.zzfb = new h<>(looper);
        this.zzfc = new WeakReference<>(null);
    }

    protected g(h<R> hVar) {
        this.zzfa = new Object();
        this.zzfd = new CountDownLatch(1);
        this.zzfe = new ArrayList<>();
        this.zzfg = new AtomicReference<>();
        this.zzfm = false;
        this.zzfb = (h) com.google.android.gms.common.internal.bf.d(hVar, "CallbackHandler must not be null");
        this.zzfc = new WeakReference<>(null);
    }

    protected g(com.google.android.gms.common.api.w wVar) {
        this.zzfa = new Object();
        this.zzfd = new CountDownLatch(1);
        this.zzfe = new ArrayList<>();
        this.zzfg = new AtomicReference<>();
        this.zzfm = false;
        this.zzfb = new h<>(wVar != null ? wVar.getLooper() : Looper.getMainLooper());
        this.zzfc = new WeakReference<>(wVar);
    }

    private final R get() {
        R r;
        synchronized (this.zzfa) {
            com.google.android.gms.common.internal.bf.a(this.zzfh ? false : true, "Result has already been consumed.");
            com.google.android.gms.common.internal.bf.a(isReady(), "Result is not ready.");
            r = this.zzdm;
            this.zzdm = null;
            this.zzff = null;
            this.zzfh = true;
        }
        cq andSet = this.zzfg.getAndSet(null);
        if (andSet != null) {
            andSet.b(this);
        }
        return r;
    }

    private final void zza(R r) {
        dd ddVar = null;
        this.zzdm = r;
        this.zzfk = null;
        this.zzfd.countDown();
        this.mStatus = this.zzdm.getStatus();
        if (this.zzfi) {
            this.zzff = null;
        } else if (this.zzff != null) {
            this.zzfb.removeMessages(2);
            this.zzfb.a(this.zzff, get());
        } else if (this.zzdm instanceof com.google.android.gms.common.api.ac) {
            this.mResultGuardian = new i(this, ddVar);
        }
        ArrayList<com.google.android.gms.common.api.ab> arrayList = this.zzfe;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            com.google.android.gms.common.api.ab abVar = arrayList.get(i);
            i++;
            abVar.a(this.mStatus);
        }
        this.zzfe.clear();
    }

    public static void zzb(com.google.android.gms.common.api.ae aeVar) {
        if (aeVar instanceof com.google.android.gms.common.api.ac) {
            try {
                ((com.google.android.gms.common.api.ac) aeVar).release();
            } catch (RuntimeException e) {
                String strValueOf = String.valueOf(aeVar);
                Log.w("BasePendingResult", new StringBuilder(String.valueOf(strValueOf).length() + 18).append("Unable to release ").append(strValueOf).toString(), e);
            }
        }
    }

    @Override // com.google.android.gms.common.api.aa
    public final void addStatusListener(com.google.android.gms.common.api.ab abVar) {
        com.google.android.gms.common.internal.bf.b(abVar != null, "Callback cannot be null.");
        synchronized (this.zzfa) {
            if (isReady()) {
                abVar.a(this.mStatus);
            } else {
                this.zzfe.add(abVar);
            }
        }
    }

    @Override // com.google.android.gms.common.api.aa
    public final R await() {
        com.google.android.gms.common.internal.bf.hW("await must not be called on the UI thread");
        com.google.android.gms.common.internal.bf.a(!this.zzfh, "Result has already been consumed");
        com.google.android.gms.common.internal.bf.a(this.zzfl == null, "Cannot await if then() has been called.");
        try {
            this.zzfd.await();
        } catch (InterruptedException e) {
            zzb(Status.bIP);
        }
        com.google.android.gms.common.internal.bf.a(isReady(), "Result is not ready.");
        return (R) get();
    }

    @Override // com.google.android.gms.common.api.aa
    public final R await(long j, TimeUnit timeUnit) {
        if (j > 0) {
            com.google.android.gms.common.internal.bf.hW("await must not be called on the UI thread when time is greater than zero.");
        }
        com.google.android.gms.common.internal.bf.a(!this.zzfh, "Result has already been consumed.");
        com.google.android.gms.common.internal.bf.a(this.zzfl == null, "Cannot await if then() has been called.");
        try {
            if (!this.zzfd.await(j, timeUnit)) {
                zzb(Status.bIR);
            }
        } catch (InterruptedException e) {
            zzb(Status.bIP);
        }
        com.google.android.gms.common.internal.bf.a(isReady(), "Result is not ready.");
        return (R) get();
    }

    @Override // com.google.android.gms.common.api.aa
    public void cancel() {
        synchronized (this.zzfa) {
            if (this.zzfi || this.zzfh) {
                return;
            }
            if (this.zzfk != null) {
                try {
                    this.zzfk.cancel();
                } catch (RemoteException e) {
                }
            }
            zzb(this.zzdm);
            this.zzfi = true;
            zza(createFailedResult(Status.bIS));
        }
    }

    protected abstract R createFailedResult(Status status);

    @Override // com.google.android.gms.common.api.aa
    public boolean isCanceled() {
        boolean z;
        synchronized (this.zzfa) {
            z = this.zzfi;
        }
        return z;
    }

    public final boolean isReady() {
        return this.zzfd.getCount() == 0;
    }

    protected final void setCancelToken(com.google.android.gms.common.internal.ak akVar) {
        synchronized (this.zzfa) {
            this.zzfk = akVar;
        }
    }

    public final void setResult(R r) {
        synchronized (this.zzfa) {
            if (this.zzfj || this.zzfi) {
                zzb(r);
                return;
            }
            if (isReady()) {
            }
            com.google.android.gms.common.internal.bf.a(!isReady(), "Results have already been set");
            com.google.android.gms.common.internal.bf.a(this.zzfh ? false : true, "Result has already been consumed");
            zza(r);
        }
    }

    @Override // com.google.android.gms.common.api.aa
    public final void setResultCallback(com.google.android.gms.common.api.af<? super R> afVar) {
        synchronized (this.zzfa) {
            try {
                if (afVar == null) {
                    this.zzff = null;
                    return;
                }
                com.google.android.gms.common.internal.bf.a(!this.zzfh, "Result has already been consumed.");
                com.google.android.gms.common.internal.bf.a(this.zzfl == null, "Cannot set callbacks if then() has been called.");
                if (isCanceled()) {
                    return;
                }
                if (isReady()) {
                    this.zzfb.a(afVar, get());
                } else {
                    this.zzff = afVar;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.google.android.gms.common.api.aa
    public final void setResultCallback(com.google.android.gms.common.api.af<? super R> afVar, long j, TimeUnit timeUnit) {
        synchronized (this.zzfa) {
            try {
                if (afVar == null) {
                    this.zzff = null;
                    return;
                }
                com.google.android.gms.common.internal.bf.a(!this.zzfh, "Result has already been consumed.");
                com.google.android.gms.common.internal.bf.a(this.zzfl == null, "Cannot set callbacks if then() has been called.");
                if (isCanceled()) {
                    return;
                }
                if (isReady()) {
                    this.zzfb.a(afVar, get());
                } else {
                    this.zzff = afVar;
                    h<R> hVar = this.zzfb;
                    hVar.sendMessageDelayed(hVar.obtainMessage(2, this), timeUnit.toMillis(j));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.google.android.gms.common.api.aa
    public <S extends com.google.android.gms.common.api.ae> com.google.android.gms.common.api.ai<S> then(com.google.android.gms.common.api.ah<? super R, ? extends S> ahVar) {
        com.google.android.gms.common.api.ai<S> aiVarThen;
        com.google.android.gms.common.internal.bf.a(!this.zzfh, "Result has already been consumed.");
        synchronized (this.zzfa) {
            com.google.android.gms.common.internal.bf.a(this.zzfl == null, "Cannot call then() twice.");
            com.google.android.gms.common.internal.bf.a(this.zzff == null, "Cannot call then() if callbacks are set.");
            com.google.android.gms.common.internal.bf.a(this.zzfi ? false : true, "Cannot call then() if result was canceled.");
            this.zzfm = true;
            this.zzfl = new ck<>(this.zzfc);
            aiVarThen = this.zzfl.then(ahVar);
            if (isReady()) {
                this.zzfb.a(this.zzfl, get());
            } else {
                this.zzff = this.zzfl;
            }
        }
        return aiVarThen;
    }

    public final void zza(cq cqVar) {
        this.zzfg.set(cqVar);
    }

    public final void zzb(Status status) {
        synchronized (this.zzfa) {
            if (!isReady()) {
                setResult(createFailedResult(status));
                this.zzfj = true;
            }
        }
    }

    @Override // com.google.android.gms.common.api.aa
    public final Integer zzo() {
        return null;
    }

    public final boolean zzw() {
        boolean zIsCanceled;
        synchronized (this.zzfa) {
            if (this.zzfc.get() == null || !this.zzfm) {
                cancel();
            }
            zIsCanceled = isCanceled();
        }
        return zIsCanceled;
    }

    public final void zzx() {
        this.zzfm = this.zzfm || zzez.get().booleanValue();
    }
}
