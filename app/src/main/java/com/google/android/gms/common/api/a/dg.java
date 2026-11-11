package com.google.android.gms.common.api.a;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class dg implements bu {
    private final Lock bKh;
    private final bc bLd;
    private final bi bMt;
    private final bi bMu;
    private final Map<com.google.android.gms.common.api.d<?>, bi> bMv;
    private final com.google.android.gms.common.api.l bMx;
    private Bundle bMy;
    private final Context mContext;
    private final Looper zzcn;
    private final Set<ab> bMw = Collections.newSetFromMap(new WeakHashMap());
    private ConnectionResult bMz = null;
    private ConnectionResult bMA = null;
    private boolean bMB = false;

    @GuardedBy("mLock")
    private int bMC = 0;

    private dg(Context context, bc bcVar, Lock lock, Looper looper, com.google.android.gms.common.g gVar, Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> map, Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> map2, com.google.android.gms.common.internal.r rVar, com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bVar, com.google.android.gms.common.api.l lVar, ArrayList<de> arrayList, ArrayList<de> arrayList2, Map<com.google.android.gms.common.api.a<?>, Boolean> map3, Map<com.google.android.gms.common.api.a<?>, Boolean> map4) {
        this.mContext = context;
        this.bLd = bcVar;
        this.bKh = lock;
        this.zzcn = looper;
        this.bMx = lVar;
        this.bMt = new bi(context, this.bLd, lock, looper, gVar, map2, null, map4, null, arrayList2, new di(this, null));
        this.bMu = new bi(context, this.bLd, lock, looper, gVar, map, rVar, map3, bVar, arrayList, new dj(this, null));
        android.support.v4.e.a aVar = new android.support.v4.e.a();
        Iterator<com.google.android.gms.common.api.d<?>> it = map2.keySet().iterator();
        while (it.hasNext()) {
            aVar.put(it.next(), this.bMt);
        }
        Iterator<com.google.android.gms.common.api.d<?>> it2 = map.keySet().iterator();
        while (it2.hasNext()) {
            aVar.put(it2.next(), this.bMu);
        }
        this.bMv = Collections.unmodifiableMap(aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void ZO() {
        if (!j(this.bMz)) {
            if (this.bMz != null && j(this.bMA)) {
                this.bMu.disconnect();
                i(this.bMz);
                return;
            } else {
                if (this.bMz == null || this.bMA == null) {
                    return;
                }
                ConnectionResult connectionResult = this.bMz;
                if (this.bMu.zzje < this.bMt.zzje) {
                    connectionResult = this.bMA;
                }
                i(connectionResult);
                return;
            }
        }
        if (j(this.bMA) || ZP()) {
            switch (this.bMC) {
                case 2:
                    this.bLd.s(this.bMy);
                case 1:
                    zzab();
                    break;
                default:
                    Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                    break;
            }
            this.bMC = 0;
            return;
        }
        if (this.bMA != null) {
            if (this.bMC == 1) {
                zzab();
            } else {
                i(this.bMA);
                this.bMt.disconnect();
            }
        }
    }

    @GuardedBy("mLock")
    private final boolean ZP() {
        return this.bMA != null && this.bMA.getErrorCode() == 4;
    }

    private final PendingIntent ZQ() {
        if (this.bMx == null) {
            return null;
        }
        return PendingIntent.getActivity(this.mContext, System.identityHashCode(this.bLd), this.bMx.getSignInIntent(), 134217728);
    }

    public static dg a(Context context, bc bcVar, Lock lock, Looper looper, com.google.android.gms.common.g gVar, Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> map, com.google.android.gms.common.internal.r rVar, Map<com.google.android.gms.common.api.a<?>, Boolean> map2, com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bVar, ArrayList<de> arrayList) {
        com.google.android.gms.common.api.l lVar = null;
        android.support.v4.e.a aVar = new android.support.v4.e.a();
        android.support.v4.e.a aVar2 = new android.support.v4.e.a();
        for (Map.Entry<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> entry : map.entrySet()) {
            com.google.android.gms.common.api.l value = entry.getValue();
            if (value.providesSignIn()) {
                lVar = value;
            }
            if (value.requiresSignIn()) {
                aVar.put(entry.getKey(), value);
            } else {
                aVar2.put(entry.getKey(), value);
            }
        }
        com.google.android.gms.common.internal.bf.a(!aVar.isEmpty(), "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        android.support.v4.e.a aVar3 = new android.support.v4.e.a();
        android.support.v4.e.a aVar4 = new android.support.v4.e.a();
        for (com.google.android.gms.common.api.a<?> aVar5 : map2.keySet()) {
            com.google.android.gms.common.api.d<?> clientKey = aVar5.getClientKey();
            if (aVar.containsKey(clientKey)) {
                aVar3.put(aVar5, map2.get(aVar5));
            } else {
                if (!aVar2.containsKey(clientKey)) {
                    throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
                }
                aVar4.put(aVar5, map2.get(aVar5));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList<de> arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            de deVar = arrayList4.get(i);
            i++;
            de deVar2 = deVar;
            if (aVar3.containsKey(deVar2.mApi)) {
                arrayList2.add(deVar2);
            } else {
                if (!aVar4.containsKey(deVar2.mApi)) {
                    throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
                }
                arrayList3.add(deVar2);
            }
        }
        return new dg(context, bcVar, lock, looper, gVar, aVar, aVar2, rVar, bVar, lVar, arrayList2, arrayList3, aVar3, aVar4);
    }

    private final boolean c(e<? extends com.google.android.gms.common.api.ae, ? extends com.google.android.gms.common.api.c> eVar) {
        Object clientKey = eVar.getClientKey();
        com.google.android.gms.common.internal.bf.b(this.bMv.containsKey(clientKey), "GoogleApiClient is not configured to use the API required for this call.");
        return this.bMv.get(clientKey).equals(this.bMu);
    }

    @GuardedBy("mLock")
    private final void i(ConnectionResult connectionResult) {
        switch (this.bMC) {
            case 2:
                this.bLd.g(connectionResult);
            case 1:
                zzab();
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        this.bMC = 0;
    }

    private static boolean j(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.Sp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void u(Bundle bundle) {
        if (this.bMy == null) {
            this.bMy = bundle;
        } else if (bundle != null) {
            this.bMy.putAll(bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zza(int i, boolean z) {
        this.bLd.N(i, z);
        this.bMA = null;
        this.bMz = null;
    }

    @GuardedBy("mLock")
    private final void zzab() {
        Iterator<ab> it = this.bMw.iterator();
        while (it.hasNext()) {
            it.next().onComplete();
        }
        this.bMw.clear();
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final void ZD() {
        this.bMt.ZD();
        this.bMu.ZD();
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t) {
        if (!c((e<? extends com.google.android.gms.common.api.ae, ? extends com.google.android.gms.common.api.c>) t)) {
            return (T) this.bMt.a(t);
        }
        if (!ZP()) {
            return (T) this.bMu.a(t);
        }
        t.setFailedResult(new Status(4, null, ZQ()));
        return t;
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final boolean a(ab abVar) {
        this.bKh.lock();
        try {
            if ((!isConnecting() && !isConnected()) || this.bMu.isConnected()) {
                return false;
            }
            this.bMw.add(abVar);
            if (this.bMC == 0) {
                this.bMC = 1;
            }
            this.bMA = null;
            this.bMu.connect();
            return true;
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t) {
        if (!c((e<? extends com.google.android.gms.common.api.ae, ? extends com.google.android.gms.common.api.c>) t)) {
            return (T) this.bMt.b(t);
        }
        if (!ZP()) {
            return (T) this.bMu.b(t);
        }
        t.setFailedResult(new Status(4, null, ZQ()));
        return t;
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final void connect() {
        this.bMC = 2;
        this.bMB = false;
        this.bMA = null;
        this.bMz = null;
        this.bMt.connect();
        this.bMu.connect();
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final void disconnect() {
        this.bMA = null;
        this.bMz = null;
        this.bMC = 0;
        this.bMt.disconnect();
        this.bMu.disconnect();
        zzab();
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append((CharSequence) str).append("authClient").println(":");
        this.bMu.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append((CharSequence) str).append("anonClient").println(":");
        this.bMt.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final boolean isConnected() {
        boolean z = true;
        this.bKh.lock();
        try {
            if (!this.bMt.isConnected() || (!this.bMu.isConnected() && !ZP() && this.bMC != 1)) {
                z = false;
            }
            return z;
        } finally {
            this.bKh.unlock();
        }
    }

    public final boolean isConnecting() {
        this.bKh.lock();
        try {
            return this.bMC == 2;
        } finally {
            this.bKh.unlock();
        }
    }
}
