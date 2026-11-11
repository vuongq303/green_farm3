package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class aq implements Runnable {
    final /* synthetic */ InGameBrowserActivity Ex;
    final /* synthetic */ String val$message;

    aq(InGameBrowserActivity inGameBrowserActivity, String str) {
        this.Ex = inGameBrowserActivity;
        this.val$message = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.Ex.gI();
            this.Ex.Eo = new ax(this.Ex, this.Ex);
            this.Ex.Eo.setProgressStyle(0);
            this.Ex.Eo.setMessage(this.val$message);
            this.Ex.Eo.setCanceledOnTouchOutside(false);
            this.Ex.Eo.show();
        } catch (Exception e) {
        }
    }
}
