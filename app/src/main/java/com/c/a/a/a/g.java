package com.c.a.a.a;

import android.os.Handler;

/* JADX INFO: compiled from: AvidLoader.java */
/* JADX INFO: loaded from: classes.dex */
public class g {
    final /* synthetic */ c ciT;
    private Handler handler = new Handler();

    public g(c cVar) {
        this.ciT = cVar;
    }

    public void aiC() {
        this.handler.postDelayed(this.ciT.ciS, 2000L);
    }

    public void bM() {
        this.handler.removeCallbacks(this.ciT.ciS);
    }
}
