package com.gameloft.a.a;

import org.json.JSONObject;

/* JADX INFO: compiled from: UserProfile.java */
/* JADX INFO: loaded from: classes.dex */
class y extends Thread {
    Object[] adx;
    final /* synthetic */ x bwI;

    y(x xVar, Object[] objArr) {
        this.bwI = xVar;
        this.adx = objArr;
    }

    private void mJ() {
        boolean unused = x.adu = false;
        boolean unused2 = x.adw = true;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        switch (x.adv) {
            case 0:
                int unused = x.adh = this.bwI.cF((String) this.adx[1]);
                break;
            case 1:
                int unused2 = x.adh = this.bwI.oo();
                break;
            case 2:
                int unused3 = x.adh = this.bwI.oq();
                break;
            case 3:
                int unused4 = x.adh = this.bwI.or();
                break;
            case 4:
                int unused5 = x.adh = this.bwI.f((JSONObject) this.adx[1], (String) this.adx[2]);
                break;
            case 5:
                int unused6 = x.adh = this.bwI.g((JSONObject) this.adx[1], (String) this.adx[2]);
                break;
            case 6:
                int unused7 = x.adh = this.bwI.h((JSONObject) this.adx[1], (String) this.adx[2]);
                break;
        }
        mJ();
    }
}
