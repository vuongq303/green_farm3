package com.quanhoang.greenfarm3;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class al extends Thread {
    final /* synthetic */ InGameBrowserActivity Ex;

    al(InGameBrowserActivity inGameBrowserActivity) {
        this.Ex = inGameBrowserActivity;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (InGameBrowserActivity.CG) {
            this.Ex.Ee = this.Ex.aX(16777232);
            this.Ex.runOnUiThread(new am(this));
            try {
                Thread.sleep(10000L);
            } catch (Exception e) {
            }
        }
    }
}
