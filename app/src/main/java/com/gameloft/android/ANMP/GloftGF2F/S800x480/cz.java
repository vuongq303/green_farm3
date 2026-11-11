package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class cz {
    int bbZ = 0;
    int bca = 0;
    Vector bcb = new Vector();
    Vector bcc = new Vector();

    int MS() {
        return this.bbZ;
    }

    void a(dc dcVar, int i) {
        this.bbZ++;
        this.bcb.addElement(dcVar);
        this.bcc.addElement(new Integer(i));
    }

    Object mq(int i) {
        return this.bcb.elementAt(i);
    }
}
