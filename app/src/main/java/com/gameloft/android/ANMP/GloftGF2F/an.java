package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class an extends Thread {
    final /* synthetic */ InGameBrowserActivity Ex;

    an(InGameBrowserActivity inGameBrowserActivity) {
        this.Ex = inGameBrowserActivity;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (InGameBrowserActivity.CG) {
            if (Math.abs(this.Ex.Eh) >= 0.3d || Math.abs(this.Ex.Ei) >= 0.3d || Math.abs(this.Ex.Ej) >= 0.3d || Math.abs(this.Ex.Ek) >= 0.3d) {
                this.Ex.runOnUiThread(new ao(this));
            }
            try {
                Thread.sleep(30L);
            } catch (Exception e) {
            }
        }
    }
}
