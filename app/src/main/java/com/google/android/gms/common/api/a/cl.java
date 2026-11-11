package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class cl implements Runnable {
    private final /* synthetic */ com.google.android.gms.common.api.ae bLJ;
    private final /* synthetic */ ck bLK;

    cl(ck ckVar, com.google.android.gms.common.api.ae aeVar) {
        this.bLK = ckVar;
        this.bLJ = aeVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            g.zzez.set(true);
            this.bLK.bLH.sendMessage(this.bLK.bLH.obtainMessage(0, this.bLK.bLC.c(this.bLJ)));
        } catch (RuntimeException e) {
            this.bLK.bLH.sendMessage(this.bLK.bLH.obtainMessage(1, e));
        } finally {
            g.zzez.set(false);
            ck ckVar = this.bLK;
            ck.zzb(this.bLJ);
            com.google.android.gms.common.api.w wVar = (com.google.android.gms.common.api.w) this.bLK.zzfc.get();
            if (wVar != null) {
                wVar.b(this.bLK);
            }
        }
    }
}
