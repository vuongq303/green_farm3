package com.gameloft.a.a;

import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CrmFatigueGroup.java */
/* JADX INFO: loaded from: classes.dex */
public class i {
    private String bsz;
    private long bsx = 0;
    private boolean bsy = false;
    private Vector bsA = new Vector();
    private Vector KE = new Vector();

    public i(JSONObject jSONObject) {
        try {
            n(jSONObject);
        } catch (JSONException e) {
        }
    }

    public int n(JSONObject jSONObject) throws JSONException {
        if (!jSONObject.has("name")) {
            return -34;
        }
        this.bsz = jSONObject.getString("name");
        JSONArray jSONArray = jSONObject.getJSONArray("limit");
        if (jSONArray.length() > 0) {
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                if (!jSONObject2.has("max") || !jSONObject2.has("interval")) {
                    return -34;
                }
                int i2 = jSONObject2.getInt("max");
                int i3 = jSONObject2.getInt("interval");
                j jVar = new j();
                jVar.bsC = i3;
                jVar.bsB = i2;
                if (jVar.bsC > this.bsx) {
                    this.bsx = jVar.bsC;
                }
                this.bsA.add(jVar);
            }
        }
        this.bsy = true;
        return 0;
    }

    public boolean K(long j) {
        M(j);
        for (int i = 0; i < this.bsA.size(); i++) {
            j jVar = (j) this.bsA.elementAt(i);
            if (jVar.bsB <= this.KE.size()) {
                int size = (this.KE.size() - 1) - (jVar.bsB - 1);
                if (size >= 0 && size < this.KE.size() && j - ((long) Integer.parseInt(this.KE.elementAt(size).toString())) < jVar.bsC) {
                    return false;
                }
            }
        }
        return true;
    }

    public void L(long j) {
        this.KE.add(new Long(j));
    }

    public void M(long j) {
        int i = 0;
        while (i < this.KE.size()) {
            if (b(Long.parseLong(this.KE.elementAt(i).toString()), j)) {
                this.KE.removeElementAt(i);
                i--;
            }
            i++;
        }
    }

    public String od() {
        return this.bsz;
    }

    public JSONObject VL() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("GroupName", od());
        } catch (JSONException e) {
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.KE.size(); i++) {
            jSONArray.put(Long.parseLong(this.KE.elementAt(i).toString()));
        }
        try {
            jSONObject.put("ImpressionArray", jSONArray);
        } catch (JSONException e2) {
        }
        return jSONObject;
    }

    public int m(JSONObject jSONObject) throws JSONException {
        if (!jSONObject.has("GroupName") || !jSONObject.getString("GroupName").equals(this.bsz)) {
            return -34;
        }
        JSONArray jSONArray = jSONObject.getJSONArray("ImpressionArray");
        int length = jSONArray.length();
        this.KE.clear();
        for (int i = 0; i < length; i++) {
            this.KE.add(Long.valueOf(jSONArray.getLong(i)));
        }
        return 0;
    }

    public boolean IsValid() {
        return this.bsy;
    }

    private boolean b(long j, long j2) {
        return j2 - j > this.bsx;
    }
}
