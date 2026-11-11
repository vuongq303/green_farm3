package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.Vector;

/* JADX INFO: compiled from: BillingMethodArray.java */
/* JADX INFO: loaded from: classes.dex */
public class d {
    private Vector Km = new Vector();

    public int a(bc[] bcVarArr) {
        this.Km.removeAllElements();
        for (bc bcVar : bcVarArr) {
            c cVar = new c();
            cVar.a(bcVar);
            this.Km.addElement(cVar);
        }
        return 0;
    }

    public int hP() {
        return this.Km.size();
    }

    public c bX(int i) {
        if (i < this.Km.size()) {
            return (c) this.Km.elementAt(i);
        }
        return null;
    }
}
