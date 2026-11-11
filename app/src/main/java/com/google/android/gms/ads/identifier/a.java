package com.google.android.gms.ads.identifier;

import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class a extends Thread {
    private WeakReference<AdvertisingIdClient> bzJ;
    private long bzK;
    CountDownLatch bzL = new CountDownLatch(1);
    boolean bzM = false;

    public a(AdvertisingIdClient advertisingIdClient, long j) {
        this.bzJ = new WeakReference<>(advertisingIdClient);
        this.bzK = j;
        start();
    }

    private final void disconnect() {
        AdvertisingIdClient advertisingIdClient = this.bzJ.get();
        if (advertisingIdClient != null) {
            advertisingIdClient.finish();
            this.bzM = true;
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        try {
            if (this.bzL.await(this.bzK, TimeUnit.MILLISECONDS)) {
                return;
            }
            disconnect();
        } catch (InterruptedException e) {
            disconnect();
        }
    }
}
