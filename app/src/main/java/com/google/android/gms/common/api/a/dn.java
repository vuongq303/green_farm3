package com.google.android.gms.common.api.a;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class dn implements com.google.android.gms.f.b<Map<cv<?>, String>> {
    private final /* synthetic */ dl bMQ;

    private dn(dl dlVar) {
        this.bMQ = dlVar;
    }

    @Override // com.google.android.gms.f.b
    public final void a(com.google.android.gms.f.f<Map<cv<?>, String>> fVar) {
        this.bMQ.bKh.lock();
        try {
            if (this.bMQ.bMM) {
                if (fVar.agq()) {
                    this.bMQ.bMN = new android.support.v4.e.a(this.bMQ.bMG.size());
                    Iterator it = this.bMQ.bMG.values().iterator();
                    while (it.hasNext()) {
                        this.bMQ.bMN.put(((dk) it.next()).zzm(), ConnectionResult.bHX);
                    }
                } else if (fVar.getException() instanceof com.google.android.gms.common.api.r) {
                    com.google.android.gms.common.api.r rVar = (com.google.android.gms.common.api.r) fVar.getException();
                    if (this.bMQ.bML) {
                        this.bMQ.bMN = new android.support.v4.e.a(this.bMQ.bMG.size());
                        for (dk dkVar : this.bMQ.bMG.values()) {
                            Object objZzm = dkVar.zzm();
                            ConnectionResult connectionResultA = rVar.a(dkVar);
                            if (this.bMQ.a((dk<?>) dkVar, connectionResultA)) {
                                this.bMQ.bMN.put(objZzm, new ConnectionResult(16));
                            } else {
                                this.bMQ.bMN.put(objZzm, connectionResultA);
                            }
                        }
                    } else {
                        this.bMQ.bMN = rVar.YK();
                    }
                    this.bMQ.bKi = this.bMQ.ZT();
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", fVar.getException());
                    this.bMQ.bMN = Collections.emptyMap();
                    this.bMQ.bKi = new ConnectionResult(8);
                }
                if (this.bMQ.bMO != null) {
                    this.bMQ.bMN.putAll(this.bMQ.bMO);
                    this.bMQ.bKi = this.bMQ.ZT();
                }
                if (this.bMQ.bKi == null) {
                    this.bMQ.ZR();
                    this.bMQ.ZS();
                } else {
                    dl.a(this.bMQ, false);
                    this.bMQ.bMI.g(this.bMQ.bKi);
                }
                this.bMQ.bMJ.signalAll();
            }
        } finally {
            this.bMQ.bKh.unlock();
        }
    }
}
