package com.a.a.a.a.b;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private final f cdJ;
    private final f cdK;
    private final boolean cdL;

    private c(f fVar, f fVar2, boolean z) {
        this.cdJ = fVar;
        if (fVar2 == null) {
            this.cdK = f.NONE;
        } else {
            this.cdK = fVar2;
        }
        this.cdL = z;
    }

    public static c a(f fVar, f fVar2, boolean z) {
        com.a.a.a.a.e.f.c(fVar, "Impression owner is null");
        com.a.a.a.a.e.f.a(fVar);
        return new c(fVar, fVar2, z);
    }

    public boolean ahE() {
        return f.NATIVE == this.cdJ;
    }

    public JSONObject ahF() {
        JSONObject jSONObject = new JSONObject();
        com.a.a.a.a.e.b.b(jSONObject, "impressionOwner", this.cdJ);
        com.a.a.a.a.e.b.b(jSONObject, "videoEventsOwner", this.cdK);
        com.a.a.a.a.e.b.b(jSONObject, "isolateVerificationScripts", Boolean.valueOf(this.cdL));
        return jSONObject;
    }
}
