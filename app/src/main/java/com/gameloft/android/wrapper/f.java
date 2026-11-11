package com.gameloft.android.wrapper;

import android.net.Uri;

/* JADX INFO: compiled from: DataSharing.java */
/* JADX INFO: loaded from: classes.dex */
final class f extends Thread {
    final /* synthetic */ String bfs;
    final /* synthetic */ String val$key;

    f(String str, String str2) {
        this.val$key = str;
        this.bfs = str2;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        synchronized (e.bfo) {
            String str = "Saving " + this.val$key + " to local provider by calling get";
            e.a(Uri.parse("content://" + e.bfp.getPackageName() + ".KeyProvider/key/"), this.val$key, this.bfs);
        }
    }
}
