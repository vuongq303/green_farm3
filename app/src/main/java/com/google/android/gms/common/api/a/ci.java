package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class ci implements Runnable {
    private final /* synthetic */ ch bLB;
    private final /* synthetic */ t bLq;
    private final /* synthetic */ String bLr;

    ci(ch chVar, t tVar, String str) {
        this.bLB = chVar;
        this.bLq = tVar;
        this.bLr = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.bLB.zzlc > 0) {
            this.bLq.onCreate(this.bLB.bLp != null ? this.bLB.bLp.getBundle(this.bLr) : null);
        }
        if (this.bLB.zzlc >= 2) {
            this.bLq.onStart();
        }
        if (this.bLB.zzlc >= 3) {
            this.bLq.onResume();
        }
        if (this.bLB.zzlc >= 4) {
            this.bLq.onStop();
        }
        if (this.bLB.zzlc >= 5) {
            this.bLq.onDestroy();
        }
    }
}
