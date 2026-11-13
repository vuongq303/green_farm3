package com.quanhoang.greenfarm3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class y extends WebViewClient {
    final /* synthetic */ IGPFreemiumActivity Dj;
    Handler Dl = new Handler();
    Runnable Dm = new z(this);

    y(IGPFreemiumActivity iGPFreemiumActivity) {
        this.Dj = iGPFreemiumActivity;
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        IGPFreemiumActivity.CH = true;
        this.Dj.gH();
        this.Dl.postDelayed(this.Dm, 15000L);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        this.Dl.removeCallbacks(this.Dm);
        this.Dj.gI();
        IGPFreemiumActivity.CH = false;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (str.startsWith("play:")) {
            try {
                Intent launchIntentForPackage = com.gameloft.android.wrapper.au.getActivity().getPackageManager().getLaunchIntentForPackage(str.replace("play:", "").split("[?]")[0]);
                launchIntentForPackage.addCategory("android.intent.category.LAUNCHER");
                com.gameloft.android.wrapper.au.getActivity().startActivity(launchIntentForPackage);
                return true;
            } catch (Exception e) {
                if (this.Dj.CY == null) {
                    return true;
                }
                webView.loadUrl(this.Dj.CY);
                this.Dj.CY = null;
                return true;
            }
        }
        if (str.equals("exit:") || str.equals("unavailable:")) {
            if (IGPFreemiumActivity.CH) {
                return true;
            }
            this.Dj.gG();
            return true;
        }
        if (str.startsWith("market://")) {
            this.Dj.J(str);
            return true;
        }
        if (str.startsWith("amzn://")) {
            this.Dj.I(str);
            return true;
        }
        if (str.contains("www.amazon.com")) {
            this.Dj.I(str.replaceAll("http://", "amzn://").replaceAll("https://", "amzn://"));
            return true;
        }
        if (str.startsWith("skt:")) {
            this.Dj.K(str.replaceAll("skt:", ""));
            return true;
        }
        if (str.startsWith("link:")) {
            String strReplaceAll = str.replaceAll("link:", "");
            if (strReplaceAll.contains("www.amazon.com")) {
                this.Dj.I(strReplaceAll.replaceAll("http://", "amzn://").replaceAll("https://", "amzn://"));
                return true;
            }
            this.Dj.H(strReplaceAll);
            return true;
        }
        if (!str.contains("201205igp.gameloft.com/redir/")) {
            this.Dj.H(str);
            return true;
        }
        if (str.contains("ctg=PLAY")) {
            this.Dj.CY = str.replace("t=bundle", "t=game").replace("ctg=PLAY", "ctg=FRINSTALL&old_ctg=PLAY");
        }
        if (!str.contains("type=SKTMARKET") || !str.contains("&pp=1")) {
            return false;
        }
        if (str.contains("ctg=PLAY")) {
            this.Dj.CY = this.Dj.CY.replaceAll("&pp=1", "");
        }
        webView.loadUrl(str.replaceAll("&pp=1", ""));
        return true;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this.Dl.removeCallbacks(this.Dm);
        com.gameloft.android.wrapper.au.getActivity().runOnUiThread(new ad(this, webView));
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }
}
