package com.gameloft.android2d.iap.a.a;

import org.json.JSONObject;

/* JADX INFO: compiled from: Purchase.java */
/* JADX INFO: loaded from: classes.dex */
public class af {
    String bmD;
    String bmE;
    String bmF;
    String bmG;
    long bmH;
    int bmI;
    String bmJ;
    String bmK;
    String bmL;
    String bmM;

    public af(String str, String str2, String str3) {
        this.bmD = str;
        this.bmL = str2;
        JSONObject jSONObject = new JSONObject(this.bmL);
        this.bmE = jSONObject.optString("orderId");
        this.bmF = jSONObject.optString("packageName");
        this.bmG = jSONObject.optString("productId");
        this.bmH = jSONObject.optLong("purchaseTime");
        this.bmI = jSONObject.optInt("purchaseState");
        this.bmJ = jSONObject.optString("developerPayload");
        this.bmK = jSONObject.optString("token", jSONObject.optString("purchaseToken"));
        this.bmM = str3;
    }

    public String Ss() {
        return this.bmD;
    }

    public String St() {
        return this.bmE;
    }

    public String Su() {
        return this.bmG;
    }

    public long Sv() {
        return this.bmH;
    }

    public String Sw() {
        return this.bmJ;
    }

    public String getToken() {
        return this.bmK;
    }

    public String Sx() {
        return this.bmL;
    }

    public String toString() {
        return "PurchaseInfo(type:" + this.bmD + "):" + this.bmL;
    }
}
