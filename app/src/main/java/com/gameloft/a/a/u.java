package com.gameloft.a.a;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: PromotionOI.java */
/* JADX INFO: loaded from: classes.dex */
public class u {
    private String Kq;
    private String ajO;

    public int g(JSONObject jSONObject) {
        if (!jSONObject.has("promotion")) {
            return -2147479542;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("promotion");
            if (!jSONObject2.has(FirebaseAnalytics.Param.END_DATE)) {
                return -2147483646;
            }
            this.ajO = jSONObject2.getString(FirebaseAnalytics.Param.END_DATE);
            if (!jSONObject2.has("description")) {
                return -2147483646;
            }
            this.Kq = jSONObject2.getString("description");
            return 0;
        } catch (JSONException e) {
            return -2147483646;
        }
    }

    public void hM() {
        this.ajO = "";
        this.Kq = "";
    }

    public String nE() {
        return this.ajO;
    }
}
