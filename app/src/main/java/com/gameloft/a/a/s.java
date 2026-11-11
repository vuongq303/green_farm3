package com.gameloft.a.a;

import java.util.Vector;
import org.json.JSONObject;

/* JADX INFO: compiled from: ItemPriceArray.java */
/* JADX INFO: loaded from: classes.dex */
public class s {
    private Vector aiv = new Vector();

    public int a(JSONObject[] jSONObjectArr) {
        this.aiv.removeAllElements();
        for (JSONObject jSONObject : jSONObjectArr) {
            r rVar = new r();
            rVar.g(jSONObject);
            this.aiv.addElement(rVar);
        }
        return 0;
    }

    public int hP() {
        return this.aiv.size();
    }
}
