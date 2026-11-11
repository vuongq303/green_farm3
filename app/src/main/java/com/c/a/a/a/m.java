package com.c.a.a.a;

/* JADX INFO: compiled from: AvidTreeWalker.java */
/* JADX INFO: loaded from: classes.dex */
final class m implements Runnable {
    m() {
    }

    @Override // java.lang.Runnable
    public void run() {
        if (l.cjb != null) {
            l.cjb.sendEmptyMessage(0);
            l.cjb.postDelayed(l.cjj, 200L);
        }
    }
}
