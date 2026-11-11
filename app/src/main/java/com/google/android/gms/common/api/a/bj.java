package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
abstract class bj {
    private final bh bLf;

    protected bj(bh bhVar) {
        this.bLf = bhVar;
    }

    public final void c(bi biVar) {
        biVar.bKh.lock();
        try {
            if (biVar.bLb != this.bLf) {
                return;
            }
            zzaq();
        } finally {
            biVar.bKh.unlock();
        }
    }

    protected abstract void zzaq();
}
