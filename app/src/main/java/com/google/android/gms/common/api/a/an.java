package com.google.android.gms.common.api.a;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class an implements bh {
    private final bi bKe;
    private boolean bKf = false;

    public an(bi biVar) {
        this.bKe = biVar;
    }

    final void Zv() {
        if (this.bKf) {
            this.bKf = false;
            this.bKe.bLd.bKU.release();
            disconnect();
        }
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t) {
        return (T) b(t);
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void a(ConnectionResult connectionResult, com.google.android.gms.common.api.a<?> aVar, boolean z) {
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t) {
        try {
            this.bKe.bLd.bKU.a(t);
            bc bcVar = this.bKe.bLd;
            com.google.android.gms.common.api.l lVar = bcVar.bKO.get(t.getClientKey());
            com.google.android.gms.common.internal.bf.d(lVar, "Appropriate Api was not requested.");
            if (lVar.isConnected() || !this.bKe.bLa.containsKey(t.getClientKey())) {
                boolean z = lVar instanceof com.google.android.gms.common.internal.bi;
                A aAaF = lVar;
                if (z) {
                    aAaF = ((com.google.android.gms.common.internal.bi) lVar).aaF();
                }
                t.run(aAaF);
            } else {
                t.setFailedResult(new Status(17));
            }
        } catch (DeadObjectException e) {
            this.bKe.a(new ao(this, this));
        }
        return t;
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void begin() {
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void connect() {
        if (this.bKf) {
            this.bKf = false;
            this.bKe.a(new ap(this, this));
        }
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final boolean disconnect() {
        if (this.bKf) {
            return false;
        }
        if (!this.bKe.bLd.ZB()) {
            this.bKe.h(null);
            return true;
        }
        this.bKf = true;
        Iterator<ck> it = this.bKe.bLd.bKT.iterator();
        while (it.hasNext()) {
            it.next().zzcc();
        }
        return false;
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void onConnected(Bundle bundle) {
    }

    @Override // com.google.android.gms.common.api.a.bh
    public final void onConnectionSuspended(int i) {
        this.bKe.h(null);
        this.bKe.bLe.N(i, this.bKf);
    }
}
