package com.c.a.a.a.f.a.a;

import android.text.TextUtils;
import android.webkit.WebView;
import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: compiled from: AvidBridgeManager.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private final com.c.a.a.a.f.a.c ckb;
    private boolean ckc;
    private boolean cke;
    private b ckf;
    private final ArrayList<c> ckg = new ArrayList<>();
    private com.c.a.a.a.i.c ckd = new com.c.a.a.a.i.c(null);

    public a(com.c.a.a.a.f.a.c cVar) {
        this.ckb = cVar;
    }

    public boolean isActive() {
        return this.ckc;
    }

    public void a(b bVar) {
        this.ckf = bVar;
    }

    public void ajp() {
        ajq();
    }

    public void setWebView(WebView webView) {
        if (this.ckd.get() != webView) {
            this.ckd.set(webView);
            this.ckc = false;
            if (com.c.a.a.a.a.ait()) {
                ajq();
            }
        }
    }

    public void destroy() {
        setWebView(null);
    }

    public void iP(String str) {
        this.ckd.ja(str);
    }

    public void iQ(String str) {
        iP(com.c.a.a.a.g.a.iS(str));
    }

    public void iR(String str) {
        iP(com.c.a.a.a.g.a.iT(str));
    }

    private void ajq() {
        if (!this.ckd.isEmpty()) {
            this.ckc = true;
            this.ckd.iZ(com.c.a.a.a.a.aiu());
            ajs();
            ajr();
            aju();
            ajt();
        }
    }

    private void ajr() {
        if (isActive() && this.cke) {
            iP(com.c.a.a.a.g.a.ajw());
        }
    }

    private void g(String str, JSONObject jSONObject) {
        String string = jSONObject != null ? jSONObject.toString() : null;
        if (!TextUtils.isEmpty(string)) {
            iP(com.c.a.a.a.g.a.aD(str, string));
        } else {
            iP(com.c.a.a.a.g.a.iU(str));
        }
    }

    private void ajs() {
        iP(com.c.a.a.a.g.a.iV(this.ckb.ajn().toString()));
    }

    private void ajt() {
        if (this.ckf != null) {
            this.ckf.ajh();
        }
    }

    private void aju() {
        for (c cVar : this.ckg) {
            g(cVar.getType(), cVar.getData());
        }
        this.ckg.clear();
    }
}
