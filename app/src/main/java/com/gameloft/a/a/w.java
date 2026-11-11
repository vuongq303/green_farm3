package com.gameloft.a.a;

import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: StoreOfflineItemArray.java */
/* JADX INFO: loaded from: classes.dex */
public class w {
    private Vector Km = new Vector();

    public int g(JSONObject jSONObject) {
        this.Km.removeAllElements();
        if (!jSONObject.has("prices")) {
            return -2147483646;
        }
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("prices");
            for (int i = 0; i < jSONArray.length(); i++) {
                v vVar = new v();
                vVar.g(jSONArray.getJSONObject(i));
                this.Km.addElement(vVar);
            }
            return 0;
        } catch (JSONException e) {
            return -2147483646;
        }
    }
}
