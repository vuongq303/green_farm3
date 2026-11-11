package com.gameloft.android2d.iap;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class s extends Thread {
    s() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (true) {
            if (b.bjp != null && b.bjp.bjJ != null) {
                b.bjp.bjJ.show();
                return;
            }
        }
    }
}
