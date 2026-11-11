package com.gameloft.android2d.iap.a.a;

import org.json.JSONObject;

/* JADX INFO: compiled from: SkuDetails.java */
/* JADX INFO: loaded from: classes.dex */
public class ah {
    String bmD;
    String bmG;
    String bmP;
    String bmQ;
    String bmR;
    String bmS;
    String bmT;

    public ah(String str, String str2) {
        this.bmD = str;
        this.bmT = str2;
        JSONObject jSONObject = new JSONObject(this.bmT);
        this.bmG = jSONObject.optString("productId");
        this.bmP = jSONObject.optString("type");
        this.bmQ = jSONObject.optString("price");
        this.bmR = jSONObject.optString("title");
        this.bmS = jSONObject.optString("description");
    }

    public String Su() {
        return this.bmG;
    }

    public String toString() {
        return "SkuDetails:" + this.bmT;
    }
}
