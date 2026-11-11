package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
final class s implements Runnable {
    s() {
    }

    @Override // java.lang.Runnable
    public void run() {
        while (com.gameloft.android.ANMP.GloftGF2F.h.fV() == null) {
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
            }
        }
        q.FP = com.gameloft.android.ANMP.GloftGF2F.h.fV().replace("/" + com.gameloft.android.ANMP.GloftGF2F.h.getClientId(), "");
        q.FQ = com.gameloft.android.ANMP.GloftGF2F.h.getClientId();
        q.hm();
    }
}
