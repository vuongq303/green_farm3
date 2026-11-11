package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
abstract class ba implements Runnable {
    private final /* synthetic */ aq bKw;

    private ba(aq aqVar) {
        this.bKw = aqVar;
    }

    /* synthetic */ ba(aq aqVar, ar arVar) {
        this(aqVar);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.bKw.bKh.lock();
        try {
            if (Thread.interrupted()) {
                return;
            }
            zzaq();
            return;
        } catch (RuntimeException e) {
            this.bKw.bKe.c(e);
            return;
        } finally {
            this.bKw.bKh.unlock();
        }
        this.bKw.bKh.unlock();
    }

    protected abstract void zzaq();
}
