package com.c.a.a.a.f.a;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: InternalAvidAdSessionContext.java */
/* JADX INFO: loaded from: classes.dex */
public class c {
    private com.c.a.a.a.f.e cjP;
    private String cjQ;
    private String cjR;
    private String cjy;

    public c(Context context, String str, String str2, String str3, com.c.a.a.a.f.e eVar) {
        com.c.a.a.a.b.aiv().init(context);
        this.cjy = str;
        this.cjP = eVar;
        this.cjQ = str2;
        this.cjR = str3;
    }

    public String aiZ() {
        return this.cjy;
    }

    public JSONObject ajn() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("avidAdSessionId", this.cjy);
            jSONObject.put("bundleIdentifier", com.c.a.a.a.b.aiv().aiw());
            jSONObject.put("partner", com.c.a.a.a.b.aiv().aiy());
            jSONObject.put("partnerVersion", this.cjP.ajb());
            jSONObject.put("avidLibraryVersion", com.c.a.a.a.b.aiv().aix());
            jSONObject.put("avidAdSessionType", this.cjQ);
            jSONObject.put("mediaType", this.cjR);
            jSONObject.put("isDeferred", this.cjP.ajc());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
