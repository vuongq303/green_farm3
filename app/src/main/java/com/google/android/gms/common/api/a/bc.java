package com.google.android.gms.common.api.a;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class bc extends com.google.android.gms.common.api.w implements bv {
    private final int bIG;
    private final com.google.android.gms.common.e bII;
    private final com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bIJ;
    private final com.google.android.gms.common.internal.z bKG;
    private volatile boolean bKJ;
    private final bf bKM;
    private p bKN;
    final Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> bKO;
    private final ArrayList<de> bKR;
    private Integer bKS;
    final cn bKU;
    private final Lock bKh;
    private final Map<com.google.android.gms.common.api.a<?>, Boolean> bKu;
    private final Context mContext;
    private final Looper zzcn;
    private final com.google.android.gms.common.internal.r zzgf;
    private bu bKH = null;
    final Queue<e<?, ?>> bKI = new LinkedList();
    private long bKK = 120000;
    private long bKL = 5000;
    Set<Scope> bKP = new HashSet();
    private final z bKQ = new z();
    Set<ck> bKT = null;
    private final com.google.android.gms.common.internal.aa bKV = new bd(this);
    private boolean bIM = false;

    public bc(Context context, Lock lock, Looper looper, com.google.android.gms.common.internal.r rVar, com.google.android.gms.common.e eVar, com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bVar, Map<com.google.android.gms.common.api.a<?>, Boolean> map, List<com.google.android.gms.common.api.y> list, List<com.google.android.gms.common.api.z> list2, Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> map2, int i, int i2, ArrayList<de> arrayList, boolean z) {
        this.bKS = null;
        this.mContext = context;
        this.bKh = lock;
        this.bKG = new com.google.android.gms.common.internal.z(looper, this.bKV);
        this.zzcn = looper;
        this.bKM = new bf(this, looper);
        this.bII = eVar;
        this.bIG = i;
        if (this.bIG >= 0) {
            this.bKS = Integer.valueOf(i2);
        }
        this.bKu = map;
        this.bKO = map2;
        this.bKR = arrayList;
        this.bKU = new cn(this.bKO);
        Iterator<com.google.android.gms.common.api.y> it = list.iterator();
        while (it.hasNext()) {
            this.bKG.a(it.next());
        }
        Iterator<com.google.android.gms.common.api.z> it2 = list2.iterator();
        while (it2.hasNext()) {
            this.bKG.a(it2.next());
        }
        this.zzgf = rVar;
        this.bIJ = bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void Ze() {
        this.bKh.lock();
        try {
            if (ZA()) {
                zzax();
            }
        } finally {
            this.bKh.unlock();
        }
    }

    public static int a(Iterable<com.google.android.gms.common.api.l> iterable, boolean z) {
        boolean z2 = false;
        boolean z3 = false;
        for (com.google.android.gms.common.api.l lVar : iterable) {
            if (lVar.requiresSignIn()) {
                z3 = true;
            }
            z2 = lVar.providesSignIn() ? true : z2;
        }
        if (z3) {
            return (z2 && z) ? 2 : 1;
        }
        return 3;
    }

    private static String nB(int i) {
        switch (i) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resume() {
        this.bKh.lock();
        try {
            if (this.bKJ) {
                zzax();
            }
        } finally {
            this.bKh.unlock();
        }
    }

    @GuardedBy("mLock")
    private final void zzax() {
        this.bKG.aau();
        this.bKH.connect();
    }

    private final void zzg(int i) {
        if (this.bKS == null) {
            this.bKS = Integer.valueOf(i);
        } else if (this.bKS.intValue() != i) {
            String strNB = nB(i);
            String strNB2 = nB(this.bKS.intValue());
            throw new IllegalStateException(new StringBuilder(String.valueOf(strNB).length() + 51 + String.valueOf(strNB2).length()).append("Cannot use sign-in mode: ").append(strNB).append(". Mode was already set to ").append(strNB2).toString());
        }
        if (this.bKH != null) {
            return;
        }
        boolean z = false;
        boolean z2 = false;
        for (com.google.android.gms.common.api.l lVar : this.bKO.values()) {
            if (lVar.requiresSignIn()) {
                z2 = true;
            }
            z = lVar.providesSignIn() ? true : z;
        }
        switch (this.bKS.intValue()) {
            case 1:
                if (!z2) {
                    throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                }
                if (z) {
                    throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
                break;
            case 2:
                if (z2) {
                    if (this.bIM) {
                        this.bKH = new dl(this.mContext, this.bKh, this.zzcn, this.bII, this.bKO, this.zzgf, this.bKu, this.bIJ, this.bKR, this, true);
                        return;
                    } else {
                        this.bKH = dg.a(this.mContext, this, this.bKh, this.zzcn, this.bII, this.bKO, this.zzgf, this.bKu, this.bIJ, this.bKR);
                        return;
                    }
                }
                break;
        }
        if (!this.bIM || z) {
            this.bKH = new bi(this.mContext, this, this.bKh, this.zzcn, this.bII, this.bKO, this.zzgf, this.bKu, this.bIJ, this.bKR, this);
        } else {
            this.bKH = new dl(this.mContext, this.bKh, this.zzcn, this.bII, this.bKO, this.zzgf, this.bKu, this.bIJ, this.bKR, this, false);
        }
    }

    @Override // com.google.android.gms.common.api.a.bv
    @GuardedBy("mLock")
    public final void N(int i, boolean z) {
        if (i == 1 && !z && !this.bKJ) {
            this.bKJ = true;
            if (this.bKN == null) {
                this.bKN = this.bII.a(this.mContext.getApplicationContext(), new bg(this));
            }
            this.bKM.sendMessageDelayed(this.bKM.obtainMessage(1), this.bKK);
            this.bKM.sendMessageDelayed(this.bKM.obtainMessage(2), this.bKL);
        }
        this.bKU.zzce();
        this.bKG.nN(i);
        this.bKG.aat();
        if (i == 2) {
            zzax();
        }
    }

    @GuardedBy("mLock")
    final boolean ZA() {
        if (!this.bKJ) {
            return false;
        }
        this.bKJ = false;
        this.bKM.removeMessages(2);
        this.bKM.removeMessages(1);
        if (this.bKN != null) {
            this.bKN.unregister();
            this.bKN = null;
        }
        return true;
    }

    final boolean ZB() {
        boolean z = false;
        this.bKh.lock();
        try {
            if (this.bKT != null) {
                z = this.bKT.isEmpty() ? false : true;
            }
            return z;
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.w
    public final <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t) {
        com.google.android.gms.common.internal.bf.b(t.getClientKey() != null, "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean zContainsKey = this.bKO.containsKey(t.getClientKey());
        String name = t.getApi() != null ? t.getApi().getName() : "the API";
        com.google.android.gms.common.internal.bf.b(zContainsKey, new StringBuilder(String.valueOf(name).length() + 65).append("GoogleApiClient is not configured to use ").append(name).append(" required for this call.").toString());
        this.bKh.lock();
        try {
            if (this.bKH == null) {
                this.bKI.add(t);
            } else {
                t = (T) this.bKH.a(t);
            }
            return t;
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.w
    public final <C extends com.google.android.gms.common.api.l> C a(com.google.android.gms.common.api.d<C> dVar) {
        C c = (C) this.bKO.get(dVar);
        com.google.android.gms.common.internal.bf.d(c, "Appropriate Api was not requested.");
        return c;
    }

    @Override // com.google.android.gms.common.api.w
    public final void a(ck ckVar) {
        this.bKh.lock();
        try {
            if (this.bKT == null) {
                this.bKT = new HashSet();
            }
            this.bKT.add(ckVar);
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.w
    public final void a(com.google.android.gms.common.api.z zVar) {
        this.bKG.a(zVar);
    }

    @Override // com.google.android.gms.common.api.w
    public final boolean a(ab abVar) {
        return this.bKH != null && this.bKH.a(abVar);
    }

    @Override // com.google.android.gms.common.api.w
    public final boolean a(com.google.android.gms.common.api.a<?> aVar) {
        return this.bKO.containsKey(aVar.getClientKey());
    }

    @Override // com.google.android.gms.common.api.w
    public final <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t) {
        com.google.android.gms.common.internal.bf.b(t.getClientKey() != null, "This task can not be executed (it's probably a Batch or malformed)");
        boolean zContainsKey = this.bKO.containsKey(t.getClientKey());
        String name = t.getApi() != null ? t.getApi().getName() : "the API";
        com.google.android.gms.common.internal.bf.b(zContainsKey, new StringBuilder(String.valueOf(name).length() + 65).append("GoogleApiClient is not configured to use ").append(name).append(" required for this call.").toString());
        this.bKh.lock();
        try {
            if (this.bKH == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (this.bKJ) {
                this.bKI.add(t);
                while (!this.bKI.isEmpty()) {
                    e<?, ?> eVarRemove = this.bKI.remove();
                    this.bKU.a(eVarRemove);
                    eVarRemove.setFailedResult(Status.bIQ);
                }
                this.bKh.unlock();
            } else {
                t = (T) this.bKH.b(t);
                this.bKh.unlock();
            }
            return t;
        } catch (Throwable th) {
            this.bKh.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.w
    public final void b(ck ckVar) {
        this.bKh.lock();
        try {
            if (this.bKT == null) {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (!this.bKT.remove(ckVar)) {
                Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (!ZB()) {
                this.bKH.ZD();
            }
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.w
    public final void b(com.google.android.gms.common.api.z zVar) {
        this.bKG.b(zVar);
    }

    @Override // com.google.android.gms.common.api.w
    public final boolean b(com.google.android.gms.common.api.a<?> aVar) {
        if (!isConnected()) {
            return false;
        }
        com.google.android.gms.common.api.l lVar = this.bKO.get(aVar.getClientKey());
        return lVar != null && lVar.isConnected();
    }

    @Override // com.google.android.gms.common.api.w
    public final void connect() {
        this.bKh.lock();
        try {
            if (this.bIG >= 0) {
                com.google.android.gms.common.internal.bf.a(this.bKS != null, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.bKS == null) {
                this.bKS = Integer.valueOf(a(this.bKO.values(), false));
            } else if (this.bKS.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            ny(this.bKS.intValue());
            this.bKh.unlock();
        } catch (Throwable th) {
            this.bKh.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.w
    public final void disconnect() {
        this.bKh.lock();
        try {
            this.bKU.release();
            if (this.bKH != null) {
                this.bKH.disconnect();
            }
            this.bKQ.release();
            for (e<?, ?> eVar : this.bKI) {
                eVar.zza((cq) null);
                eVar.cancel();
            }
            this.bKI.clear();
            if (this.bKH == null) {
                return;
            }
            ZA();
            this.bKG.aat();
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.w
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append((CharSequence) str).append("mContext=").println(this.mContext);
        printWriter.append((CharSequence) str).append("mResuming=").print(this.bKJ);
        printWriter.append(" mWorkQueue.size()=").print(this.bKI.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.bKU.bLN.size());
        if (this.bKH != null) {
            this.bKH.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // com.google.android.gms.common.api.a.bv
    @GuardedBy("mLock")
    public final void g(ConnectionResult connectionResult) {
        if (!this.bII.isPlayServicesPossiblyUpdating(this.mContext, connectionResult.getErrorCode())) {
            ZA();
        }
        if (this.bKJ) {
            return;
        }
        this.bKG.l(connectionResult);
        this.bKG.aat();
    }

    @Override // com.google.android.gms.common.api.w
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.google.android.gms.common.api.w
    public final Looper getLooper() {
        return this.zzcn;
    }

    @Override // com.google.android.gms.common.api.w
    public final boolean isConnected() {
        return this.bKH != null && this.bKH.isConnected();
    }

    @Override // com.google.android.gms.common.api.w
    public final void ny(int i) {
        boolean z = true;
        this.bKh.lock();
        if (i != 3 && i != 1 && i != 2) {
            z = false;
        }
        try {
            com.google.android.gms.common.internal.bf.b(z, new StringBuilder(33).append("Illegal sign-in mode: ").append(i).toString());
            zzg(i);
            zzax();
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bv
    @GuardedBy("mLock")
    public final void s(Bundle bundle) {
        while (!this.bKI.isEmpty()) {
            b(this.bKI.remove());
        }
        this.bKG.w(bundle);
    }

    @Override // com.google.android.gms.common.api.w
    public final <L> v<L> y(L l) {
        this.bKh.lock();
        try {
            return this.bKQ.a(l, this.zzcn, "NO_TYPE");
        } finally {
            this.bKh.unlock();
        }
    }

    final String zzbb() {
        StringWriter stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }
}
