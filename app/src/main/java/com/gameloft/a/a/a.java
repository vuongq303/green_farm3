package com.gameloft.a.a;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: BillingMethod.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private String Ki = "";
    private String Kj = "";
    private s bsg = new s();
    private s bsh = new s();

    public int g(JSONObject jSONObject) throws JSONException {
        Object obj = jSONObject.get("type");
        if (obj != null) {
            this.Kj = (String) obj;
        } else {
            hM();
        }
        Object obj2 = jSONObject.get("name");
        if (obj2 != null) {
            this.Ki = (String) obj2;
        } else {
            hM();
        }
        Object obj3 = jSONObject.get("price");
        if (obj3 != null) {
            if (obj3 instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) obj3;
                JSONObject[] jSONObjectArr = new JSONObject[jSONArray.length()];
                for (int i = 0; i < jSONObjectArr.length; i++) {
                    jSONObjectArr[i] = jSONArray.getJSONObject(i);
                }
                this.bsg.a(jSONObjectArr);
            }
        } else {
            hM();
        }
        if (this.bsg.hP() < 1) {
            return -2147483646;
        }
        Object obj4 = jSONObject.get("replaced_price");
        if (obj4 == null || !(obj4 instanceof JSONArray)) {
            return 0;
        }
        JSONArray jSONArray2 = (JSONArray) obj4;
        JSONObject[] jSONObjectArr2 = new JSONObject[jSONArray2.length()];
        for (int i2 = 0; i2 < jSONObjectArr2.length; i2++) {
            jSONObjectArr2[i2] = jSONArray2.getJSONObject(i2);
        }
        this.bsh.a(jSONObjectArr2);
        return 0;
    }

    public void hM() {
        this.Ki = new String();
        this.Kj = new String();
        this.bsg = new s();
        this.bsh = new s();
    }
}
