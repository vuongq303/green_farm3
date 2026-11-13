package com.quanhoang.greenfarm3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: compiled from: SplashScreenActivity.java */
/* JADX INFO: loaded from: classes.dex */
class bq extends WebViewClient {
    final /* synthetic */ SplashScreenActivity bcJ;
    boolean bcO;

    private bq(SplashScreenActivity splashScreenActivity) {
        this.bcJ = splashScreenActivity;
        this.bcO = false;
    }

    /* synthetic */ bq(SplashScreenActivity splashScreenActivity, bj bjVar) {
        this(splashScreenActivity);
    }

    public void bd(String str) {
        if (str != null && str.length() > 0) {
            try {
                com.gameloft.android.wrapper.au.getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            } catch (Exception e) {
            }
        }
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (str.startsWith("play:")) {
            this.bcJ.dO(str.replace("play:", "").split("[?]")[0]);
            this.bcJ.Nc();
            return true;
        }
        if (str.startsWith("link:")) {
            bd(str.replace("link:", ""));
            this.bcJ.Nc();
            return true;
        }
        if (str.startsWith("exit:")) {
            this.bcJ.Nc();
            return true;
        }
        if (str.startsWith("goto:")) {
            try {
                i.o(str.replace("goto:", ""));
            } catch (Exception e) {
            }
            this.bcJ.Nc();
            return true;
        }
        if (str.startsWith("browser:")) {
            InGameBrowserActivity.P(str.replace("browser:", ""));
            this.bcJ.Nc();
            return true;
        }
        if (str.contains("201205igp.gameloft.com/redir/ads/splashscreen_click") || str.contains(com.gameloft.android.wrapper.au.OV() + "/redir/ads/splashscreen_click")) {
            webView.loadUrl(str);
            return true;
        }
        if (str.startsWith("market://")) {
            this.bcJ.dM(str);
            this.bcJ.Nc();
            return true;
        }
        if (str.startsWith("amzn://")) {
            this.bcJ.dN(str);
            this.bcJ.Nc();
            return true;
        }
        if ((str.contains("201205igp.gameloft.com/redir/?from") || str.contains(com.gameloft.android.wrapper.au.OV() + "/redir/?from")) && !str.contains("ctg=PLAY")) {
            new Thread(new br(this, str)).start();
            this.bcJ.Nc();
            return true;
        }
        if (str.startsWith("vnd.youtube:")) {
            this.bcJ.dL(str);
            this.bcJ.Nc();
            return true;
        }
        webView.loadUrl(str);
        return true;
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        if (this.bcO) {
            this.bcJ.Nc();
        }
        this.bcO = false;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this.bcO = true;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }
}
