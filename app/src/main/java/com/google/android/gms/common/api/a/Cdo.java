package com.google.android.gms.common.api.a;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.google.android.gms.common.api.a.do, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
final class Cdo implements com.google.android.gms.f.b<Map<cv<?>, String>> {
    private final /* synthetic */ dl bMQ;
    private ab bMR;

    Cdo(dl dlVar, ab abVar) {
        this.bMQ = dlVar;
        this.bMR = abVar;
    }

    @Override // com.google.android.gms.f.b
    public final void a(com.google.android.gms.f.f<Map<cv<?>, String>> fVar) {
        this.bMQ.bKh.lock();
        try {
            if (!this.bMQ.bMM) {
                this.bMR.onComplete();
                this.bMQ.bKh.unlock();
                return;
            }
            if (fVar.agq()) {
                this.bMQ.bMO = new android.support.v4.e.a(this.bMQ.bMH.size());
                Iterator it = this.bMQ.bMH.values().iterator();
                while (it.hasNext()) {
                    this.bMQ.bMO.put(((dk) it.next()).zzm(), ConnectionResult.bHX);
                }
            } else if (fVar.getException() instanceof com.google.android.gms.common.api.r) {
                com.google.android.gms.common.api.r rVar = (com.google.android.gms.common.api.r) fVar.getException();
                if (this.bMQ.bML) {
                    this.bMQ.bMO = new android.support.v4.e.a(this.bMQ.bMH.size());
                    for (dk dkVar : this.bMQ.bMH.values()) {
                        Object objZzm = dkVar.zzm();
                        ConnectionResult connectionResultA = rVar.a(dkVar);
                        if (this.bMQ.a((dk<?>) dkVar, connectionResultA)) {
                            this.bMQ.bMO.put(objZzm, new ConnectionResult(16));
                        } else {
                            this.bMQ.bMO.put(objZzm, connectionResultA);
                        }
                    }
                } else {
                    this.bMQ.bMO = rVar.YK();
                }
            } else {
                Log.e("ConnectionlessGAC", "Unexpected availability exception", fVar.getException());
                this.bMQ.bMO = Collections.emptyMap();
            }
            if (this.bMQ.isConnected()) {
                this.bMQ.bMN.putAll(this.bMQ.bMO);
                if (this.bMQ.ZT() == null) {
                    this.bMQ.ZR();
                    this.bMQ.ZS();
                    this.bMQ.bMJ.signalAll();
                }
            }
            this.bMR.onComplete();
            this.bMQ.bKh.unlock();
        } catch (Throwable th) {
            this.bMQ.bKh.unlock();
            throw th;
        }
    }

    final void cancel() {
        this.bMR.onComplete();
    }
}
