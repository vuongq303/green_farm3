package com.quanhoang.greenfarm3;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class u implements Runnable {
    final /* synthetic */ IGPFreemiumActivity Dj;
    final /* synthetic */ String val$message;

    u(IGPFreemiumActivity iGPFreemiumActivity, String str) {
        this.Dj = iGPFreemiumActivity;
        this.val$message = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.Dj.gI();
            this.Dj.Di = new ag(this.Dj, IGPFreemiumActivity.Dh.getContext());
            this.Dj.Di.setProgressStyle(0);
            this.Dj.Di.setMessage(this.val$message);
            this.Dj.Di.setCanceledOnTouchOutside(false);
            this.Dj.Di.show();
        } catch (Exception e) {
        }
    }
}
