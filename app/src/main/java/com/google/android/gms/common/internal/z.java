package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class z implements Handler.Callback {
    private final aa bOa;
    private final Handler mHandler;
    private final ArrayList<com.google.android.gms.common.api.y> bOb = new ArrayList<>();
    private final ArrayList<com.google.android.gms.common.api.y> bOc = new ArrayList<>();
    private final ArrayList<com.google.android.gms.common.api.z> bOd = new ArrayList<>();
    private volatile boolean bOe = false;
    private final AtomicInteger bOf = new AtomicInteger(0);
    private boolean bOg = false;
    private final Object mLock = new Object();

    public z(Looper looper, aa aaVar) {
        this.bOa = aaVar;
        this.mHandler = new Handler(looper, this);
    }

    public final void a(com.google.android.gms.common.api.y yVar) {
        bf.checkNotNull(yVar);
        synchronized (this.mLock) {
            if (this.bOb.contains(yVar)) {
                String strValueOf = String.valueOf(yVar);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(strValueOf).length() + 62).append("registerConnectionCallbacks(): listener ").append(strValueOf).append(" is already registered").toString());
            } else {
                this.bOb.add(yVar);
            }
        }
        if (this.bOa.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, yVar));
        }
    }

    public final void a(com.google.android.gms.common.api.z zVar) {
        bf.checkNotNull(zVar);
        synchronized (this.mLock) {
            if (this.bOd.contains(zVar)) {
                String strValueOf = String.valueOf(zVar);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(strValueOf).length() + 67).append("registerConnectionFailedListener(): listener ").append(strValueOf).append(" is already registered").toString());
            } else {
                this.bOd.add(zVar);
            }
        }
    }

    public final void aat() {
        this.bOe = false;
        this.bOf.incrementAndGet();
    }

    public final void aau() {
        this.bOe = true;
    }

    public final void b(com.google.android.gms.common.api.z zVar) {
        bf.checkNotNull(zVar);
        synchronized (this.mLock) {
            if (!this.bOd.remove(zVar)) {
                String strValueOf = String.valueOf(zVar);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(strValueOf).length() + 57).append("unregisterConnectionFailedListener(): listener ").append(strValueOf).append(" not found").toString());
            }
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (message.what != 1) {
            Log.wtf("GmsClientEvents", new StringBuilder(45).append("Don't know how to handle message: ").append(message.what).toString(), new Exception());
            return false;
        }
        com.google.android.gms.common.api.y yVar = (com.google.android.gms.common.api.y) message.obj;
        synchronized (this.mLock) {
            if (this.bOe && this.bOa.isConnected() && this.bOb.contains(yVar)) {
                yVar.onConnected(this.bOa.getConnectionHint());
            }
        }
        return true;
    }

    public final void l(ConnectionResult connectionResult) {
        int i = 0;
        bf.a(Looper.myLooper() == this.mHandler.getLooper(), "onConnectionFailure must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.mLock) {
            ArrayList arrayList = new ArrayList(this.bOd);
            int i2 = this.bOf.get();
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                com.google.android.gms.common.api.z zVar = (com.google.android.gms.common.api.z) obj;
                if (!this.bOe || this.bOf.get() != i2) {
                    return;
                }
                if (this.bOd.contains(zVar)) {
                    zVar.onConnectionFailed(connectionResult);
                }
            }
        }
    }

    public final void nN(int i) {
        int i2 = 0;
        bf.a(Looper.myLooper() == this.mHandler.getLooper(), "onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.mLock) {
            this.bOg = true;
            ArrayList arrayList = new ArrayList(this.bOb);
            int i3 = this.bOf.get();
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                com.google.android.gms.common.api.y yVar = (com.google.android.gms.common.api.y) obj;
                if (!this.bOe || this.bOf.get() != i3) {
                    break;
                } else if (this.bOb.contains(yVar)) {
                    yVar.onConnectionSuspended(i);
                }
            }
            this.bOc.clear();
            this.bOg = false;
        }
    }

    public final void w(Bundle bundle) {
        int i = 0;
        bf.a(Looper.myLooper() == this.mHandler.getLooper(), "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.mLock) {
            bf.bZ(!this.bOg);
            this.mHandler.removeMessages(1);
            this.bOg = true;
            bf.bZ(this.bOc.size() == 0);
            ArrayList arrayList = new ArrayList(this.bOb);
            int i2 = this.bOf.get();
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                com.google.android.gms.common.api.y yVar = (com.google.android.gms.common.api.y) obj;
                if (!this.bOe || !this.bOa.isConnected() || this.bOf.get() != i2) {
                    break;
                } else if (!this.bOc.contains(yVar)) {
                    yVar.onConnected(bundle);
                }
            }
            this.bOc.clear();
            this.bOg = false;
        }
    }
}
