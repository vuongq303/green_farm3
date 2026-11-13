package com.quanhoang.greenfarm3;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
final class ah implements Runnable {
    ah() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            InGameBrowserActivity.Dt.exit();
        } catch (Exception e) {
        }
    }
}
