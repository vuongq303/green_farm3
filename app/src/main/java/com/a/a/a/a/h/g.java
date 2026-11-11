package com.a.a.a.a.h;

import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class g implements com.a.a.a.a.h.a.d {
    private JSONObject cfo;
    private final com.a.a.a.a.h.a.e cfp;

    public g(com.a.a.a.a.h.a.e eVar) {
        this.cfp = eVar;
    }

    @Override // com.a.a.a.a.h.a.d
    public void O(JSONObject jSONObject) {
        this.cfo = jSONObject;
    }

    public void a() {
        this.cfp.b(new com.a.a.a.a.h.a.f(this));
    }

    public void a(JSONObject jSONObject, HashSet<String> hashSet, double d) {
        this.cfp.b(new com.a.a.a.a.h.a.h(this, hashSet, jSONObject, d));
    }

    @Override // com.a.a.a.a.h.a.d
    public JSONObject ais() {
        return this.cfo;
    }

    public void b(JSONObject jSONObject, HashSet<String> hashSet, double d) {
        this.cfp.b(new com.a.a.a.a.h.a.g(this, hashSet, jSONObject, d));
    }
}
