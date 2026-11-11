package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class bx implements Runnable {
    private final /* synthetic */ t bLq;
    private final /* synthetic */ String bLr;
    private final /* synthetic */ bw bLs;

    bx(bw bwVar, t tVar, String str) {
        this.bLs = bwVar;
        this.bLq = tVar;
        this.bLr = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.bLs.zzlc > 0) {
            this.bLq.onCreate(this.bLs.bLp != null ? this.bLs.bLp.getBundle(this.bLr) : null);
        }
        if (this.bLs.zzlc >= 2) {
            this.bLq.onStart();
        }
        if (this.bLs.zzlc >= 3) {
            this.bLq.onResume();
        }
        if (this.bLs.zzlc >= 4) {
            this.bLq.onStop();
        }
        if (this.bLs.zzlc >= 5) {
            this.bLq.onDestroy();
        }
    }
}
