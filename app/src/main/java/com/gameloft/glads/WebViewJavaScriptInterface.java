package com.gameloft.glads;

import android.webkit.JavascriptInterface;

/* JADX INFO: loaded from: classes.dex */
public class WebViewJavaScriptInterface {
    AndroidWebView view;

    public WebViewJavaScriptInterface(AndroidWebView androidWebView) {
        this.view = androidWebView;
    }

    @JavascriptInterface
    public void OnJavaScriptEvaluated(String str, int i) {
        this.view.OnJavaScriptEvaluated(str, i);
    }
}
