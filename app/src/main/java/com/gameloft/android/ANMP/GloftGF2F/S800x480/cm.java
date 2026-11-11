package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
final class cm implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        try {
            ci.JM();
        } catch (Exception e) {
        }
        try {
            ci.Jd();
        } catch (Exception e2) {
        }
        if (ci.arm == 1) {
            com.gameloft.android2d.socialnetwork.l.mU(1);
            bt.nV();
        } else {
            com.gameloft.android2d.socialnetwork.l.mU(2);
            bt.nV();
        }
        ci.ark = true;
    }
}
