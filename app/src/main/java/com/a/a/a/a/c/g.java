package com.a.a.a.a.c;

import android.webkit.WebView;

/* JADX INFO: loaded from: classes.dex */
class g implements Runnable {
    final /* synthetic */ WebView cet;
    final /* synthetic */ String ceu;
    final /* synthetic */ f cev;

    g(f fVar, WebView webView, String str) {
        this.cev = fVar;
        this.cet = webView;
        this.ceu = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.cet.loadUrl(this.ceu);
    }
}
