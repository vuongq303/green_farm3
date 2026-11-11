package com.a.a.a.a.g;

import android.webkit.WebView;

/* JADX INFO: loaded from: classes.dex */
class e implements Runnable {
    private WebView ceQ;
    final /* synthetic */ d ceR;

    e(d dVar) {
        this.ceR = dVar;
        this.ceQ = this.ceR.cet;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.ceQ.destroy();
    }
}
