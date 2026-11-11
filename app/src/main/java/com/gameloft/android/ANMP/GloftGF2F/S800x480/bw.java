package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.Vector;

/* JADX INFO: compiled from: StoreOfflineItemArray.java */
/* JADX INFO: loaded from: classes.dex */
public class bw {
    private Vector Km = new Vector();

    public int a(bc bcVar) {
        this.Km.removeAllElements();
        if (bcVar.cd("prices") == null) {
            return -2147483646;
        }
        bc[] bcVarArrCj = null;
        try {
            bcVarArrCj = bcVar.cj("prices");
        } catch (Exception e) {
        }
        for (bc bcVar2 : bcVarArrCj) {
            bv bvVar = new bv();
            bvVar.a(bcVar2);
            this.Km.addElement(bvVar);
        }
        return 0;
    }

    public int oi() {
        return this.Km.size();
    }

    public bv eO(int i) {
        return (bv) this.Km.elementAt(i);
    }
}
