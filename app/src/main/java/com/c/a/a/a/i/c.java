package com.c.a.a.a.i;

import android.webkit.WebView;

/* JADX INFO: compiled from: AvidWebView.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends b<WebView> {
    public c(WebView webView) {
        super(webView);
    }

    public void iZ(String str) {
        ja(com.c.a.a.a.g.a.iX(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void ja(String str) {
        WebView webView = (WebView) get();
        if (webView != null) {
            webView.loadUrl(str);
        }
    }
}
