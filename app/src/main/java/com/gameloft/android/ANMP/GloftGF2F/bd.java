package com.gameloft.android.ANMP.GloftGF2F;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Message.java */
/* JADX INFO: loaded from: classes.dex */
public class bd {
    public JSONObject EJ;

    public bd(int i) {
        this.EJ = new JSONObject();
        try {
            this.EJ.put("ggid", i);
        } catch (JSONException e) {
            o.d(e);
        }
    }

    public bd(String str) {
        try {
            this.EJ = new JSONObject(str);
        } catch (JSONException e) {
            o.d(e);
        }
    }

    public int gZ() {
        try {
            return this.EJ.getJSONArray("events").length();
        } catch (JSONException e) {
            return 0;
        }
    }

    public int ha() {
        try {
            if (this.EJ.has("ggid")) {
                return this.EJ.getInt("ggid");
            }
            return 0;
        } catch (JSONException e) {
            return 0;
        }
    }

    boolean b(bw bwVar) {
        if (bwVar == null) {
            return false;
        }
        try {
            if (!this.EJ.has("events")) {
                this.EJ.put("events", new JSONArray());
            }
            this.EJ.accumulate("events", bwVar.bdd);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public String toString() {
        return this.EJ.toString();
    }
}
