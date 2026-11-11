package com.a.a.a.a.h.a;

import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class a extends b {
    protected final double c;
    protected final HashSet<String> cfg;
    protected final JSONObject cfh;

    public a(d dVar, HashSet<String> hashSet, JSONObject jSONObject, double d) {
        super(dVar);
        this.cfg = new HashSet<>(hashSet);
        this.cfh = jSONObject;
        this.c = d;
    }
}
