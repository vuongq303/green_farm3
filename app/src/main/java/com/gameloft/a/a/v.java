package com.gameloft.a.a;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: StoreOfflineItem.java */
/* JADX INFO: loaded from: classes.dex */
public class v {
    private String Ki;
    private int akr;
    private int akt;
    private boolean bwG;
    private Vector aku = new Vector();
    private b bwF = new b();
    private int aks = n.nb(-1);
    private String ajO = null;

    public int g(JSONObject jSONObject) {
        try {
            this.Ki = jSONObject.getString("item");
            try {
                this.akr = n.nb(jSONObject.getInt(FirebaseAnalytics.Param.QUANTITY));
                try {
                    if (jSONObject.get("replaced_quantity") != JSONObject.NULL) {
                        this.aks = n.nb(jSONObject.getInt("replaced_quantity"));
                    }
                    try {
                        JSONArray jSONArray = jSONObject.getJSONArray("category");
                        this.akt = jSONArray.length();
                        for (int i = 0; i < this.akt; i++) {
                            this.aku.addElement(jSONArray.getString(i));
                        }
                    } catch (JSONException e) {
                    }
                    if (jSONObject.has("billing_methods")) {
                        try {
                            JSONArray jSONArray2 = jSONObject.getJSONArray("billing_methods");
                            JSONObject[] jSONObjectArr = new JSONObject[jSONArray2.length()];
                            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                jSONObjectArr[i2] = jSONArray2.getJSONObject(i2);
                            }
                            this.bwF.a(jSONObjectArr);
                        } catch (JSONException e2) {
                        }
                        if (this.bwF.hP() < 1) {
                            return -2147483646;
                        }
                    }
                    if (jSONObject.has("promotion")) {
                        try {
                            this.ajO = jSONObject.getJSONObject("promotion").getString(FirebaseAnalytics.Param.END_DATE);
                        } catch (JSONException e3) {
                            return -2147483646;
                        }
                    }
                    if (jSONObject.has("hidden")) {
                        try {
                            this.bwG = jSONObject.getBoolean("hidden");
                        } catch (JSONException e4) {
                            return -2147483646;
                        }
                    }
                    return 0;
                } catch (Exception e5) {
                    return -2147483646;
                }
            } catch (JSONException e6) {
                return -2147483646;
            }
        } catch (JSONException e7) {
            return -2147483646;
        }
    }
}
