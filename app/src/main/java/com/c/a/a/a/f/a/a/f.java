package com.c.a.a.a.f.a.a;

import android.webkit.WebView;

/* JADX INFO: compiled from: AvidWebViewManager.java */
/* JADX INFO: loaded from: classes.dex */
public class f implements e {
    private final a cjo;
    private final com.c.a.a.a.f.a.c ckb;
    private final com.c.a.a.a.i.c ckd = new com.c.a.a.a.i.c(null);
    private d ckj;

    public f(com.c.a.a.a.f.a.c cVar, a aVar) {
        this.ckb = cVar;
        this.cjo = aVar;
    }

    public void setWebView(WebView webView) {
        if (this.ckd.get() != webView) {
            this.cjo.setWebView(null);
            ajv();
            this.ckd.set(webView);
            if (webView != null) {
                this.ckj = new d(this.ckb);
                this.ckj.a(this);
                webView.addJavascriptInterface(this.ckj, "avid");
            }
        }
    }

    public void destroy() {
        setWebView(null);
    }

    private void ajv() {
        if (this.ckj != null) {
            this.ckj.a(null);
            this.ckj = null;
        }
    }
}
