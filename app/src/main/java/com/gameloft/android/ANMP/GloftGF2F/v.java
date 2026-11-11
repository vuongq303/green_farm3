package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class v implements Runnable {
    final /* synthetic */ IGPFreemiumActivity Dj;

    v(IGPFreemiumActivity iGPFreemiumActivity) {
        this.Dj = iGPFreemiumActivity;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.Dj.Di != null) {
                this.Dj.Di.dismiss();
            }
            this.Dj.Di = null;
            com.gameloft.android.wrapper.au.Qi();
        } catch (Exception e) {
        }
    }
}
