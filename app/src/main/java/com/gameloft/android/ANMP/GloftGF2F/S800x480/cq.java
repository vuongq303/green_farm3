package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class cq implements Runnable {
    final /* synthetic */ ci bbN;

    public cq(ci ciVar) {
        this.bbN = ciVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        while (ci.bbF != null) {
            try {
                if (ci.bbG) {
                    ci.bbG = false;
                    // Serialize only after completed game-thread actions.
                    ProgressSaveController.requestSave();
                }
                Thread.sleep(50L);
            } catch (Exception e) {
            }
        }
    }
}
