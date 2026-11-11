package com.gameloft.glads;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: loaded from: classes.dex */
public class GLWebViewClient extends WebViewClient {
    AndroidWebView parentWebView;

    GLWebViewClient(AndroidWebView androidWebView) {
        this.parentWebView = androidWebView;
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        this.parentWebView.OnFinishLoad();
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        super.onReceivedError(webView, i, str, str2);
        this.parentWebView.OnFailedLoad(str + " - " + str2);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (str.startsWith("mwvparams:")) {
            this.parentWebView.modalWebviewParams = str.replace("mwvparams:", "");
            return true;
        }
        if (this.parentWebView.ShouldOpenURL(str)) {
            return super.shouldOverrideUrlLoading(webView, str);
        }
        return true;
    }
}
