package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class da {
    int bbZ = 0;
    Vector bcb = new Vector();
    Vector bcd = new Vector();
    Vector bce = new Vector();

    int MS() {
        return this.bbZ;
    }

    void a(dc dcVar, int i, int i2) {
        this.bbZ++;
        this.bcb.addElement(dcVar);
        this.bcd.addElement(new Integer(i));
        this.bce.addElement(new Integer(i2));
    }
}
