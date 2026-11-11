package com.gameloft.android2d.iap.utils;

import android.webkit.WebView;
import java.util.HashMap;

/* JADX INFO: compiled from: MyWebView.java */
/* JADX INFO: loaded from: classes.dex */
class s implements Runnable {
    final /* synthetic */ MyWebView bon;

    s(MyWebView myWebView) {
        this.bon = myWebView;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.bon.bog = (WebView) this.bon.findViewById(com.b.b.cfD);
        String url = this.bon.bog.getUrl();
        if (!MyWebView.bof.Rw().equals(v.mJ(com.b.d.cgL))) {
            if (this.bon.bog.findAll(v.mJ(com.b.d.ciC)) == 0) {
                if (url == null) {
                    return;
                }
                if (url.indexOf(v.mJ(com.b.d.ciE) + "&id=") <= 0 && url.indexOf("&error=") <= 0 && url.indexOf("?error=") <= 0) {
                    return;
                }
            }
            if (!this.bon.boj) {
                this.bon.boj = true;
                if (url.indexOf(v.mJ(com.b.d.ciE) + "&id=") > 0) {
                    u.fV(this.bon.Tb());
                    u.D(this.bon.Tc(), 39);
                    MyWebView.bof.RU();
                } else if (url.indexOf(v.mJ(com.b.d.ciA)) > 0 || url.indexOf(v.mJ(com.b.d.ciB)) > 0 || url.indexOf(v.mJ(com.b.d.ciF)) > 0) {
                    com.gameloft.android2d.iap.b.a(url, (HashMap<String, String>) MyWebView.boi);
                } else {
                    com.gameloft.android2d.iap.b.setResult(3);
                    com.gameloft.android2d.iap.b.mB(-5);
                }
                this.bon.Te();
                return;
            }
            return;
        }
        if (url != null) {
            if ((url.indexOf(v.mJ(com.b.d.ciD)) > 0 || url.indexOf("&error=") > 0) && !this.bon.boj) {
                this.bon.boj = true;
                if (url.indexOf(v.mJ(com.b.d.ciD)) > 0) {
                    u.fV((String) MyWebView.boi.get(v.mJ(com.b.d.cio)));
                    MyWebView.bof.RU();
                } else {
                    com.gameloft.android2d.iap.b.setResult(3);
                    com.gameloft.android2d.iap.b.mB(v.fZ(this.bon.Td()));
                }
                this.bon.Te();
            }
        }
    }
}
