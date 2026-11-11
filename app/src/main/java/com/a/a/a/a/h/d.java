package com.a.a.a.a.h;

/* JADX INFO: loaded from: classes.dex */
final class d implements Runnable {
    d() {
    }

    @Override // java.lang.Runnable
    public void run() {
        if (a.c != null) {
            a.c.post(a.ceZ);
            a.c.postDelayed(a.cfa, 200L);
        }
    }
}
