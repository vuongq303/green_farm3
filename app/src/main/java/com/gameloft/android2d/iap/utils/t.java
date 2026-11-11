package com.gameloft.android2d.iap.utils;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/* JADX INFO: compiled from: MyWebView.java */
/* JADX INFO: loaded from: classes.dex */
class t extends WebViewClient {
    final /* synthetic */ MyWebView bon;
    ProgressDialog boo = null;
    MyWebView bop;

    public t(MyWebView myWebView, MyWebView myWebView2) {
        this.bon = myWebView;
        this.bop = null;
        this.bop = myWebView2;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (!MyWebView.bof.Rw().equals(v.mJ(com.b.d.cgL)) || str == null || !str.startsWith("sms:")) {
            return false;
        }
        int iIndexOf = str.indexOf("sms:");
        int iIndexOf2 = str.indexOf("?body=");
        this.bon.bol = str.substring(iIndexOf + 4, iIndexOf2);
        this.bon.bom = str.substring(iIndexOf2 + 6);
        try {
            this.bon.bom = URLDecoder.decode(this.bon.bom, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        if (Build.VERSION.SDK_INT > 22) {
            if (this.bop.checkSelfPermission("android.permission.SEND_SMS") != 0) {
                this.bop.requestPermissions(new String[]{"android.permission.SEND_SMS"}, 0);
                return true;
            }
            this.bon.Tf();
            this.bon.Te();
            return true;
        }
        this.bon.Tf();
        this.bon.Te();
        return true;
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(WebView webView, String str) {
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        this.bon.bok = true;
        if (this.boo == null) {
            this.boo = new ProgressDialog(this.bon);
        }
        this.boo.setProgressStyle(0);
        this.boo.setMessage(v.mI(com.b.d.cfS));
        try {
            this.boo.show();
        } catch (Exception e) {
        }
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        this.bon.bok = false;
        try {
            if (this.boo == null) {
                this.boo = new ProgressDialog(this.bon);
            } else {
                this.boo.dismiss();
                this.boo = null;
            }
            this.bop.Ta();
        } catch (Exception e) {
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        super.onReceivedError(webView, i, str, str2);
        if (this.boo != null) {
            this.boo.dismiss();
            this.boo = null;
        }
        com.gameloft.android2d.iap.b.setResult(3);
        com.gameloft.android2d.iap.b.mB(-1);
        this.bon.Te();
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        switch (sslError.getPrimaryError()) {
            case 4:
                com.gameloft.android2d.iap.b.setResult(3);
                com.gameloft.android2d.iap.b.mB(-25);
                this.bon.Te();
                break;
            default:
                com.gameloft.android2d.iap.b.setResult(3);
                com.gameloft.android2d.iap.b.mB(-26);
                this.bon.Te();
                break;
        }
    }
}
