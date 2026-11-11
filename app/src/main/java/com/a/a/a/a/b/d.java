package com.a.a.a.a.b;

import android.webkit.WebView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    private final g cdM;
    private final List<h> cdN = new ArrayList();
    private final String cdO;
    private final String cdP;
    private final e cdQ;
    private final WebView webView;

    private d(g gVar, WebView webView, String str, List<h> list, String str2) {
        this.cdM = gVar;
        this.webView = webView;
        this.cdO = str;
        if (list != null) {
            this.cdN.addAll(list);
            this.cdQ = e.NATIVE;
        } else {
            this.cdQ = e.HTML;
        }
        this.cdP = str2;
    }

    public static d a(g gVar, WebView webView, String str) {
        com.a.a.a.a.e.f.c(gVar, "Partner is null");
        com.a.a.a.a.e.f.c(webView, "WebView is null");
        if (str != null) {
            com.a.a.a.a.e.f.e(str, 256, "CustomReferenceData is greater than 256 characters");
        }
        return new d(gVar, webView, null, null, str);
    }

    public g ahG() {
        return this.cdM;
    }

    public List<h> ahH() {
        return Collections.unmodifiableList(this.cdN);
    }

    public String ahI() {
        return this.cdP;
    }

    public String ahJ() {
        return this.cdO;
    }

    public e ahK() {
        return this.cdQ;
    }

    public WebView getWebView() {
        return this.webView;
    }
}
