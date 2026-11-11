package com.gameloft.a.a;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ItemPrice.java */
/* JADX INFO: loaded from: classes.dex */
public class r {
    private String ait = "";
    private int aiK = n.nb(0);

    public int g(JSONObject jSONObject) {
        try {
            Object obj = jSONObject.get("currency");
            if (obj == null) {
                return -2147483646;
            }
            this.ait = (String) obj;
            Object obj2 = jSONObject.get("price");
            if (obj2 == null) {
                return -2147483646;
            }
            try {
                this.aiK = n.nb(Integer.parseInt(String.valueOf(obj2)));
                return Wb() < 0 ? -2147483646 : 0;
            } catch (NumberFormatException e) {
                return -2147483646;
            }
        } catch (JSONException e2) {
            return -2147483646;
        }
    }

    public int Wb() {
        return n.nc(this.aiK);
    }
}
