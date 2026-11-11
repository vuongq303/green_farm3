package com.c.a.a.a.h.a;

import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: compiled from: AbstractAvidPublishAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a extends b {
    protected final JSONObject ckA;
    protected final double ckB;
    protected final com.c.a.a.a.e.a ckm;
    protected final HashSet<String> ckz;

    public a(d dVar, com.c.a.a.a.e.a aVar, HashSet<String> hashSet, JSONObject jSONObject, double d) {
        super(dVar);
        this.ckm = aVar;
        this.ckz = new HashSet<>(hashSet);
        this.ckA = jSONObject;
        this.ckB = d;
    }
}
