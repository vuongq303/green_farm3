package com.gameloft.android.wrapper;

import android.net.Uri;

/* JADX INFO: compiled from: DataSharing.java */
/* JADX INFO: loaded from: classes.dex */
final class g extends Thread {
    final /* synthetic */ String bft;
    final /* synthetic */ String val$key;

    g(String str, String str2) {
        this.val$key = str;
        this.bft = str2;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        synchronized (e.bfo) {
            String str = "Start  Save " + this.val$key + " with value " + this.bft;
            int size = e.bfo.size();
            for (int i = 0; i < size; i++) {
                e.a(Uri.parse("content://" + ((String) e.bfo.get(i)) + "/key/"), this.val$key, this.bft);
            }
        }
    }
}
