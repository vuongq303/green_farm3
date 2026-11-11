package com.google.android.gms.common.api.a;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class dl implements bu {
    private final Lock bKh;

    @GuardedBy("mLock")
    private ConnectionResult bKi;
    private final Map<com.google.android.gms.common.api.a<?>, Boolean> bKu;
    private final bc bMI;
    private final Condition bMJ;
    private final boolean bMK;
    private final boolean bML;

    @GuardedBy("mLock")
    private boolean bMM;

    @GuardedBy("mLock")
    private Map<cv<?>, ConnectionResult> bMN;

    @GuardedBy("mLock")
    private Map<cv<?>, ConnectionResult> bMO;

    @GuardedBy("mLock")
    private Cdo bMP;
    private final Looper zzcn;
    private final l zzcq;
    private final com.google.android.gms.common.internal.r zzgf;
    private final com.google.android.gms.common.g zzgk;
    private final Map<com.google.android.gms.common.api.d<?>, dk<?>> bMG = new HashMap();
    private final Map<com.google.android.gms.common.api.d<?>, dk<?>> bMH = new HashMap();
    private final Queue<e<?, ?>> bKI = new LinkedList();

    public dl(Context context, Lock lock, Looper looper, com.google.android.gms.common.g gVar, Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> map, com.google.android.gms.common.internal.r rVar, Map<com.google.android.gms.common.api.a<?>, Boolean> map2, com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bVar, ArrayList<de> arrayList, bc bcVar, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        this.bKh = lock;
        this.zzcn = looper;
        this.bMJ = lock.newCondition();
        this.zzgk = gVar;
        this.bMI = bcVar;
        this.bKu = map2;
        this.zzgf = rVar;
        this.bMK = z;
        HashMap map3 = new HashMap();
        for (com.google.android.gms.common.api.a<?> aVar : map2.keySet()) {
            map3.put(aVar.getClientKey(), aVar);
        }
        HashMap map4 = new HashMap();
        ArrayList<de> arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            de deVar = arrayList2.get(i);
            i++;
            de deVar2 = deVar;
            map4.put(deVar2.mApi, deVar2);
        }
        boolean z5 = false;
        boolean z6 = true;
        boolean z7 = false;
        for (Map.Entry<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> entry : map.entrySet()) {
            com.google.android.gms.common.api.a aVar2 = (com.google.android.gms.common.api.a) map3.get(entry.getKey());
            com.google.android.gms.common.api.l value = entry.getValue();
            if (value.requiresGooglePlayServices()) {
                z2 = true;
                if (this.bKu.get(aVar2).booleanValue()) {
                    z3 = z6;
                    z4 = z7;
                } else {
                    z3 = z6;
                    z4 = true;
                }
            } else {
                z2 = z5;
                z3 = false;
                z4 = z7;
            }
            dk<?> dkVar = new dk<>(context, aVar2, looper, value, (de) map4.get(aVar2), rVar, bVar);
            this.bMG.put(entry.getKey(), dkVar);
            if (value.requiresSignIn()) {
                this.bMH.put(entry.getKey(), dkVar);
            }
            z5 = z2;
            z6 = z3;
            z7 = z4;
        }
        this.bML = (!z5 || z6 || z7) ? false : true;
        this.zzcq = l.YV();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void ZR() {
        if (this.zzgf == null) {
            this.bMI.bKP = Collections.emptySet();
            return;
        }
        HashSet hashSet = new HashSet(this.zzgf.aaf());
        Map<com.google.android.gms.common.api.a<?>, com.google.android.gms.common.internal.t> mapAah = this.zzgf.aah();
        for (com.google.android.gms.common.api.a<?> aVar : mapAah.keySet()) {
            ConnectionResult connectionResultD = d(aVar);
            if (connectionResultD != null && connectionResultD.Sp()) {
                hashSet.addAll(mapAah.get(aVar).mScopes);
            }
        }
        this.bMI.bKP = hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void ZS() {
        while (!this.bKI.isEmpty()) {
            b(this.bKI.remove());
        }
        this.bMI.s(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final ConnectionResult ZT() {
        int i = 0;
        ConnectionResult connectionResult = null;
        int i2 = 0;
        ConnectionResult connectionResult2 = null;
        for (dk<?> dkVar : this.bMG.values()) {
            com.google.android.gms.common.api.a<?> api = dkVar.getApi();
            ConnectionResult connectionResult3 = this.bMN.get(dkVar.zzm());
            if (!connectionResult3.Sp() && (!this.bKu.get(api).booleanValue() || connectionResult3.Yy() || this.zzgk.ns(connectionResult3.getErrorCode()))) {
                if (connectionResult3.getErrorCode() == 4 && this.bMK) {
                    int priority = api.YI().getPriority();
                    if (connectionResult == null || i > priority) {
                        i = priority;
                        connectionResult = connectionResult3;
                    }
                } else {
                    int priority2 = api.YI().getPriority();
                    if (connectionResult2 != null && i2 <= priority2) {
                        priority2 = i2;
                        connectionResult3 = connectionResult2;
                    }
                    i2 = priority2;
                    connectionResult2 = connectionResult3;
                }
            }
        }
        return (connectionResult2 == null || connectionResult == null || i2 <= i) ? connectionResult2 : connectionResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean a(dk<?> dkVar, ConnectionResult connectionResult) {
        return !connectionResult.Sp() && !connectionResult.Yy() && this.bKu.get(dkVar.getApi()).booleanValue() && dkVar.Zb().requiresGooglePlayServices() && this.zzgk.ns(connectionResult.getErrorCode());
    }

    static /* synthetic */ boolean a(dl dlVar, boolean z) {
        dlVar.bMM = false;
        return false;
    }

    private final ConnectionResult b(com.google.android.gms.common.api.d<?> dVar) {
        this.bKh.lock();
        try {
            dk<?> dkVar = this.bMG.get(dVar);
            if (this.bMN == null || dkVar == null) {
                return null;
            }
            return this.bMN.get(dkVar.zzm());
        } finally {
            this.bKh.unlock();
        }
    }

    private final <T extends e<? extends com.google.android.gms.common.api.ae, ? extends com.google.android.gms.common.api.c>> boolean d(T t) {
        com.google.android.gms.common.api.d<?> clientKey = t.getClientKey();
        ConnectionResult connectionResultB = b(clientKey);
        if (connectionResultB == null || connectionResultB.getErrorCode() != 4) {
            return false;
        }
        t.setFailedResult(new Status(4, null, this.zzcq.a(this.bMG.get(clientKey).zzm(), System.identityHashCode(this.bMI))));
        return true;
    }

    private final boolean zzaf() {
        this.bKh.lock();
        try {
            if (!this.bMM || !this.bMK) {
                return false;
            }
            Iterator<com.google.android.gms.common.api.d<?>> it = this.bMH.keySet().iterator();
            while (it.hasNext()) {
                ConnectionResult connectionResultB = b(it.next());
                if (connectionResultB == null || !connectionResultB.Sp()) {
                    return false;
                }
            }
            return true;
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final void ZD() {
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t) {
        if (this.bMK && d(t)) {
            return t;
        }
        if (isConnected()) {
            this.bMI.bKU.a(t);
            return (T) this.bMG.get(t.getClientKey()).doRead(t);
        }
        this.bKI.add(t);
        return t;
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final boolean a(ab abVar) {
        Lock lock;
        this.bKh.lock();
        try {
            if (!this.bMM || zzaf()) {
                return false;
            }
            this.zzcq.YX();
            this.bMP = new Cdo(this, abVar);
            this.zzcq.a(this.bMH.values()).a(new com.google.android.gms.common.d.a.a(this.zzcn), this.bMP);
            return true;
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t) {
        com.google.android.gms.common.api.d<A> clientKey = t.getClientKey();
        if (this.bMK && d(t)) {
            return t;
        }
        this.bMI.bKU.a(t);
        return (T) this.bMG.get(clientKey).doWrite(t);
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final void connect() {
        this.bKh.lock();
        try {
            if (this.bMM) {
                return;
            }
            this.bMM = true;
            this.bMN = null;
            this.bMO = null;
            this.bMP = null;
            this.bKi = null;
            this.zzcq.YX();
            this.zzcq.a(this.bMG.values()).a(new com.google.android.gms.common.d.a.a(this.zzcn), new dn(this));
        } finally {
            this.bKh.unlock();
        }
    }

    public final ConnectionResult d(com.google.android.gms.common.api.a<?> aVar) {
        return b(aVar.getClientKey());
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final void disconnect() {
        this.bKh.lock();
        try {
            this.bMM = false;
            this.bMN = null;
            this.bMO = null;
            if (this.bMP != null) {
                this.bMP.cancel();
                this.bMP = null;
            }
            this.bKi = null;
            while (!this.bKI.isEmpty()) {
                e<?, ?> eVarRemove = this.bKI.remove();
                eVarRemove.zza((cq) null);
                eVarRemove.cancel();
            }
            this.bMJ.signalAll();
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final boolean isConnected() {
        this.bKh.lock();
        try {
            return this.bMN != null && this.bKi == null;
        } finally {
            this.bKh.unlock();
        }
    }
}
