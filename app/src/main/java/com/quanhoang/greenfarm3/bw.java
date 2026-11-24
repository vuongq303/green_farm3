package com.quanhoang.greenfarm3;

import org.json.JSONObject;

/* JADX INFO: compiled from: TrackingEvent.java */
/* JADX INFO: loaded from: classes.dex */
public class bw {
    public JSONObject bdd = new JSONObject();
    public int id;
    public int type;

    public String toString() {
        return this.bdd.toString();
    }

    public bw(int i, int i2) {
        this.id = i;
        this.type = i2;
    }

    public bw() {
    }
}
