package com.a.a.a.a.g;

import android.webkit.WebView;
import com.a.a.a.a.b.h;
import com.a.a.a.a.b.i;
import com.a.a.a.a.c.f;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    private com.a.a.a.a.f.b ceI;
    private com.a.a.a.a.b.a ceJ;
    private b ceK;
    private double e;

    public a() {
        h();
        this.ceI = new com.a.a.a.a.f.b(null);
    }

    public void a() {
    }

    public void a(com.a.a.a.a.b.a aVar) {
        this.ceJ = aVar;
    }

    public void a(com.a.a.a.a.b.c cVar) {
        f.aia().a(getWebView(), cVar.ahF());
    }

    public void a(i iVar, com.a.a.a.a.b.d dVar) {
        String strAhR = iVar.ahR();
        JSONObject jSONObject = new JSONObject();
        com.a.a.a.a.e.b.b(jSONObject, "environment", "app");
        com.a.a.a.a.e.b.b(jSONObject, "adSessionType", dVar.ahK());
        com.a.a.a.a.e.b.b(jSONObject, "deviceInfo", com.a.a.a.a.e.a.aih());
        JSONArray jSONArray = new JSONArray();
        jSONArray.put("clid");
        jSONArray.put("vlid");
        com.a.a.a.a.e.b.b(jSONObject, "supports", jSONArray);
        JSONObject jSONObject2 = new JSONObject();
        com.a.a.a.a.e.b.b(jSONObject2, "partnerName", dVar.ahG().getName());
        com.a.a.a.a.e.b.b(jSONObject2, "partnerVersion", dVar.ahG().getVersion());
        com.a.a.a.a.e.b.b(jSONObject, "omidNativeInfo", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        com.a.a.a.a.e.b.b(jSONObject3, "libraryVersion", "1.1.3-Gameloft");
        com.a.a.a.a.e.b.b(jSONObject3, "appId", com.a.a.a.a.c.e.ahY().ahZ().getApplicationContext().getPackageName());
        com.a.a.a.a.e.b.b(jSONObject, "app", jSONObject3);
        if (dVar.ahI() != null) {
            com.a.a.a.a.e.b.b(jSONObject, "customReferenceData", dVar.ahI());
        }
        JSONObject jSONObject4 = new JSONObject();
        for (h hVar : dVar.ahH()) {
            com.a.a.a.a.e.b.b(jSONObject4, hVar.ahL(), hVar.ahN());
        }
        f.aia().a(getWebView(), strAhR, jSONObject, jSONObject4);
    }

    public void a(String str, double d) {
        if (d > this.e) {
            this.ceK = b.AD_STATE_VISIBLE;
            f.aia().c(getWebView(), str);
        }
    }

    public com.a.a.a.a.b.a aij() {
        return this.ceJ;
    }

    public void b() {
        this.ceI.clear();
    }

    void b(WebView webView) {
        this.ceI = new com.a.a.a.a.f.b(webView);
    }

    public void b(String str, double d) {
        if (d <= this.e || this.ceK == b.AD_STATE_HIDDEN) {
            return;
        }
        this.ceK = b.AD_STATE_HIDDEN;
        f.aia().c(getWebView(), str);
    }

    public void ch(boolean z) {
        if (e()) {
            f.aia().d(getWebView(), z ? "foregrounded" : "backgrounded");
        }
    }

    public boolean e() {
        return this.ceI.get() != null;
    }

    public void f() {
        f.aia().b(getWebView());
    }

    public void g() {
        f.aia().c(getWebView());
    }

    public WebView getWebView() {
        return (WebView) this.ceI.get();
    }

    public void h() {
        this.e = com.a.a.a.a.e.e.aii();
        this.ceK = b.AD_STATE_IDLE;
    }

    public void k(float f) {
        f.aia().a(getWebView(), f);
    }
}
