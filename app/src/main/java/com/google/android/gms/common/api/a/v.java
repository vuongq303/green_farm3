package com.google.android.gms.common.api.a;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public final class v<L> {
    private final y bJP;
    private volatile L bJQ;
    private final w<L> bJR;

    v(Looper looper, L l, String str) {
        this.bJP = new y(this, looper);
        this.bJQ = (L) com.google.android.gms.common.internal.bf.d(l, "Listener must not be null");
        this.bJR = new w<>(l, com.google.android.gms.common.internal.bf.id(str));
    }

    public final w<L> Zo() {
        return this.bJR;
    }

    public final void a(x<? super L> xVar) {
        com.google.android.gms.common.internal.bf.d(xVar, "Notifier must not be null");
        this.bJP.sendMessage(this.bJP.obtainMessage(1, xVar));
    }

    final void b(x<? super L> xVar) {
        L l = this.bJQ;
        if (l == null) {
            xVar.YU();
            return;
        }
        try {
            xVar.z(l);
        } catch (RuntimeException e) {
            xVar.YU();
            throw e;
        }
    }

    public final void clear() {
        this.bJQ = null;
    }
}
