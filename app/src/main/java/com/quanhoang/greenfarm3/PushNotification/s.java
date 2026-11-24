package com.quanhoang.greenfarm3.PushNotification;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
final class s implements Runnable {
    s() {
    }

    @Override // java.lang.Runnable
    public void run() {
        while (com.quanhoang.greenfarm3.h.fV() == null) {
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
            }
        }
        q.FP = com.quanhoang.greenfarm3.h.fV().replace("/" + com.quanhoang.greenfarm3.h.getClientId(), "");
        q.FQ = com.quanhoang.greenfarm3.h.getClientId();
        q.hm();
    }
}
