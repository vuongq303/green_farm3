package com.c.a.a.a;

import android.os.AsyncTask;
import android.os.Build;

/* JADX INFO: compiled from: AvidLoader.java */
/* JADX INFO: loaded from: classes.dex */
public class f {
    final /* synthetic */ c ciT;

    public f(c cVar) {
        this.ciT = cVar;
    }

    public void a(p pVar) {
        if (Build.VERSION.SDK_INT >= 11) {
            this.ciT.ciP.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "https://mobile-static.adsafeprotected.com/avid-v2.js");
        } else {
            this.ciT.ciP.execute("https://mobile-static.adsafeprotected.com/avid-v2.js");
        }
    }
}
