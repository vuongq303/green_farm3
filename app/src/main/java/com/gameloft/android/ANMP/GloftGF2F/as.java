package com.gameloft.android.ANMP.GloftGF2F;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class as extends WebViewClient {
    final /* synthetic */ InGameBrowserActivity Ex;

    as(InGameBrowserActivity inGameBrowserActivity) {
        this.Ex = inGameBrowserActivity;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        if (!this.Ex.Eb) {
            if (!InGameBrowserActivity.Eq || !str.startsWith(InGameBrowserActivity.Es)) {
                this.Ex.gH();
            } else {
                InGameBrowserActivity.T(str.replace(InGameBrowserActivity.Es, ""));
                this.Ex.exit();
                return;
            }
        }
        this.Ex.Eb = false;
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        if (!this.Ex.Eb) {
            this.Ex.gI();
            try {
                if (!TextUtils.isEmpty(InGameBrowserActivity.Ev) && !str.startsWith(InGameBrowserActivity.Es)) {
                    webView.loadUrl("javascript:" + InGameBrowserActivity.Ev);
                }
            } catch (Exception e) {
            }
        }
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (InGameBrowserActivity.Eq) {
            if (!str.startsWith(InGameBrowserActivity.Es)) {
                return false;
            }
            InGameBrowserActivity.T(str.replace(InGameBrowserActivity.Es, ""));
            this.Ex.exit();
            return true;
        }
        if (str.startsWith("exit:checkreward:")) {
            String unused = InGameBrowserActivity.DQ = str.replace("exit:checkreward:", "");
            this.Ex.exit();
            return true;
        }
        if (str.startsWith("checkreward:")) {
            String unused2 = InGameBrowserActivity.DQ = str.replace("checkreward:", "");
            return true;
        }
        if (str.startsWith("play:")) {
            try {
                Intent launchIntentForPackage = this.Ex.getPackageManager().getLaunchIntentForPackage(str.replace("play:", "").split("[?]")[0]);
                launchIntentForPackage.addCategory("android.intent.category.LAUNCHER");
                launchIntentForPackage.setSelector(null);
                this.Ex.startActivity(launchIntentForPackage);
            } catch (Exception e) {
            }
            return true;
        }
        if (str.startsWith("mailto:") || str.startsWith("instagram://")) {
            try {
                Intent uri = Intent.parseUri(str, 1);
                uri.setSelector(null);
                this.Ex.startActivity(uri);
            } catch (Exception e2) {
            }
            return true;
        }
        if (str.startsWith("goto:")) {
            return true;
        }
        if (str.equals("exit:") || str.equals("unavailable:")) {
            this.Ex.exit();
            return true;
        }
        if (str.startsWith("market://")) {
            this.Ex.J(str);
            return true;
        }
        if (str.startsWith("amzn://")) {
            this.Ex.I(str);
            return true;
        }
        if (str.contains("www.amazon.com")) {
            this.Ex.I(str.replaceAll("http://", "amzn://").replaceAll("https://", "amzn://"));
            return true;
        }
        if (str.startsWith("skt:")) {
            this.Ex.K(str.replaceAll("skt:", ""));
            return true;
        }
        if (str.startsWith("link:")) {
            String strReplaceAll = str.replaceAll("link:", "");
            if (strReplaceAll.contains("www.amazon.com")) {
                this.Ex.I(strReplaceAll.replaceAll("http://", "amzn://").replaceAll("https://", "amzn://"));
                return true;
            }
            InGameBrowserActivity.H(strReplaceAll);
            return true;
        }
        if (str.contains("/redir/")) {
            if (!str.contains("type=SKTMARKET")) {
                return false;
            }
            webView.loadUrl(str.replaceAll("&pp=1", ""));
            return true;
        }
        if (!str.contains("wapshop.gameloft.com/support/") && !str.contains("wapshop.gameloft.com/bannedaccount/")) {
            return false;
        }
        String strReplace = str.replace("/support/", "/webview/");
        if (strReplace.indexOf("hitId") != -1) {
            strReplace = strReplace.concat("&lang=" + InGameBrowserActivity.DZ[InGameBrowserActivity.DG]);
        }
        this.Ex.Eb = true;
        webView.loadUrl(strReplace);
        return true;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this.Ex.runOnUiThread(new at(this, webView));
    }
}
