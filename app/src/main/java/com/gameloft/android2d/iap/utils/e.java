package com.gameloft.android2d.iap.utils;

/* JADX INFO: compiled from: Device.java */
/* JADX INFO: loaded from: classes.dex */
final class e extends Thread {
    e() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        String unused = d.userAgent = System.getProperty("http.agent").trim();
    }
}
