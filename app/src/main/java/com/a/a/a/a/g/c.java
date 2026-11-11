package com.a.a.a.a.g;

import android.webkit.WebView;

/* JADX INFO: loaded from: classes.dex */
public class c extends a {
    public c(WebView webView) {
        if (webView != null && !webView.getSettings().getJavaScriptEnabled()) {
            webView.getSettings().setJavaScriptEnabled(true);
        }
        b(webView);
    }
}
