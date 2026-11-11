package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class ar implements Runnable {
    final /* synthetic */ InGameBrowserActivity Ex;

    ar(InGameBrowserActivity inGameBrowserActivity) {
        this.Ex = inGameBrowserActivity;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.Ex.Eo != null) {
                this.Ex.Eo.dismiss();
            }
            this.Ex.Eo = null;
            com.gameloft.android.wrapper.au.Qi();
        } catch (Exception e) {
        }
    }
}
