package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
final class p implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        n.iF();
        n.iG();
        try {
            Thread.sleep(5000L);
        } catch (Exception e) {
        }
        if (n.Lr) {
            n.setResult(2);
        } else {
            n.setResult(10);
            n.Lz = true;
        }
    }
}
