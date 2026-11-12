package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.ArrayList;

/* JADX INFO: compiled from: ProfilerNode.java */
/* JADX INFO: loaded from: classes.dex */
public class bm {
    private int ajL;
    private bm ajM;
    private ArrayList<bm> ajN = new ArrayList<>();
    private String eventName;

    public bm(String str, int i) {
        this.eventName = str;
        this.ajL = i;
    }

    public void a(bm bmVar) {
        bmVar.ajM = this;
        if (this.ajN.size() >= w.Zq) {
            this.ajN.remove(0);
        }
        this.ajN.add(bmVar);
    }

    public bm nD() {
        return this.ajM;
    }

    public String getName() {
        return this.eventName;
    }
}
