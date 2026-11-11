package com.google.android.gms.common.api.a;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class bi implements bu, df {
    private final com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bIJ;
    final Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> bKO;
    private final Condition bKY;
    private final bk bKZ;
    private final Lock bKh;
    private final Map<com.google.android.gms.common.api.a<?>, Boolean> bKu;
    private volatile bh bLb;
    final bc bLd;
    final bv bLe;
    private final Context mContext;
    private final com.google.android.gms.common.internal.r zzgf;
    private final com.google.android.gms.common.g zzgk;
    int zzje;
    final Map<com.google.android.gms.common.api.d<?>, ConnectionResult> bLa = new HashMap();
    private ConnectionResult bLc = null;

    public bi(Context context, bc bcVar, Lock lock, Looper looper, com.google.android.gms.common.g gVar, Map<com.google.android.gms.common.api.d<?>, com.google.android.gms.common.api.l> map, com.google.android.gms.common.internal.r rVar, Map<com.google.android.gms.common.api.a<?>, Boolean> map2, com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bVar, ArrayList<de> arrayList, bv bvVar) {
        this.mContext = context;
        this.bKh = lock;
        this.zzgk = gVar;
        this.bKO = map;
        this.zzgf = rVar;
        this.bKu = map2;
        this.bIJ = bVar;
        this.bLd = bcVar;
        this.bLe = bvVar;
        ArrayList<de> arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            de deVar = arrayList2.get(i);
            i++;
            deVar.a(this);
        }
        this.bKZ = new bk(this, looper);
        this.bKY = lock.newCondition();
        this.bLb = new bb(this);
    }

    final void ZC() {
        this.bKh.lock();
        try {
            this.bLb = new aq(this, this.zzgf, this.bKu, this.zzgk, this.bIJ, this.bKh, this.mContext);
            this.bLb.begin();
            this.bKY.signalAll();
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final void ZD() {
        if (isConnected()) {
            ((an) this.bLb).Zv();
        }
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t) {
        t.zzx();
        return (T) this.bLb.a(t);
    }

    @Override // com.google.android.gms.common.api.a.df
    public final void a(ConnectionResult connectionResult, com.google.android.gms.common.api.a<?> aVar, boolean z) {
        this.bKh.lock();
        try {
            this.bLb.a(connectionResult, aVar, z);
        } finally {
            this.bKh.unlock();
        }
    }

    final void a(bj bjVar) {
        this.bKZ.sendMessage(this.bKZ.obtainMessage(1, bjVar));
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final boolean a(ab abVar) {
        return false;
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t) {
        t.zzx();
        return (T) this.bLb.b(t);
    }

    final void c(RuntimeException runtimeException) {
        this.bKZ.sendMessage(this.bKZ.obtainMessage(2, runtimeException));
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final void connect() {
        this.bLb.connect();
    }

    @Override // com.google.android.gms.common.api.a.bu
    @GuardedBy("mLock")
    public final void disconnect() {
        if (this.bLb.disconnect()) {
            this.bLa.clear();
        }
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String strConcat = String.valueOf(str).concat("  ");
        printWriter.append((CharSequence) str).append("mState=").println(this.bLb);
        for (com.google.android.gms.common.api.a<?> aVar : this.bKu.keySet()) {
            printWriter.append((CharSequence) str).append((CharSequence) aVar.getName()).println(":");
            this.bKO.get(aVar.getClientKey()).dump(strConcat, fileDescriptor, printWriter, strArr);
        }
    }

    final void h(ConnectionResult connectionResult) {
        this.bKh.lock();
        try {
            this.bLc = connectionResult;
            this.bLb = new bb(this);
            this.bLb.begin();
            this.bKY.signalAll();
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.a.bu
    public final boolean isConnected() {
        return this.bLb instanceof an;
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnected(Bundle bundle) {
        this.bKh.lock();
        try {
            this.bLb.onConnected(bundle);
        } finally {
            this.bKh.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnectionSuspended(int i) {
        this.bKh.lock();
        try {
            this.bLb.onConnectionSuspended(i);
        } finally {
            this.bKh.unlock();
        }
    }

    final void zzbd() {
        this.bKh.lock();
        try {
            this.bLd.ZA();
            this.bLb = new an(this);
            this.bLb.begin();
            this.bKY.signalAll();
        } finally {
            this.bKh.unlock();
        }
    }
}
