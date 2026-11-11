package com.c.a.a.a.h;

import com.c.a.a.a.h.a.d;
import com.c.a.a.a.h.a.e;
import com.c.a.a.a.h.a.f;
import com.c.a.a.a.h.a.g;
import com.c.a.a.a.h.a.h;
import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: compiled from: AvidStatePublisher.java */
/* JADX INFO: loaded from: classes.dex */
public class b implements d {
    private final com.c.a.a.a.e.a ckm;
    private JSONObject ckt;
    private final e cku;

    public b(com.c.a.a.a.e.a aVar, e eVar) {
        this.ckm = aVar;
        this.cku = eVar;
    }

    public void c(JSONObject jSONObject, HashSet<String> hashSet, double d) {
        this.cku.b(new h(this, this.ckm, hashSet, jSONObject, d));
    }

    public void d(JSONObject jSONObject, HashSet<String> hashSet, double d) {
        this.cku.b(new g(this, this.ckm, hashSet, jSONObject, d));
    }

    public void ajC() {
        this.cku.b(new f(this));
    }

    @Override // com.c.a.a.a.h.a.d
    public JSONObject ajD() {
        return this.ckt;
    }

    @Override // com.c.a.a.a.h.a.d
    public void Q(JSONObject jSONObject) {
        this.ckt = jSONObject;
    }
}
