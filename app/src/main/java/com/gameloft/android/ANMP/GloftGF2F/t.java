package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class t implements Runnable {
    final /* synthetic */ IGPFreemiumActivity Dj;

    t(IGPFreemiumActivity iGPFreemiumActivity) {
        this.Dj = iGPFreemiumActivity;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.Dj.Df.removeView(this.Dj.Dg);
            this.Dj.Dg.destroy();
            this.Dj.Dg.destroyDrawingCache();
            this.Dj.Dg = null;
        } catch (Exception e) {
        }
    }
}
