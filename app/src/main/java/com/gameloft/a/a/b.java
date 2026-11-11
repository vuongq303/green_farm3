package com.gameloft.a.a;

import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: BillingMethodArray.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private Vector Km = new Vector();

    public int a(JSONObject[] jSONObjectArr) throws JSONException {
        this.Km.removeAllElements();
        for (JSONObject jSONObject : jSONObjectArr) {
            a aVar = new a();
            aVar.g(jSONObject);
            this.Km.addElement(aVar);
        }
        return 0;
    }

    public int hP() {
        return this.Km.size();
    }
}
