package com.gameloft.a.c;

import java.util.Vector;

/* JADX INFO: compiled from: PopUpsServer.java */
/* JADX INFO: loaded from: classes.dex */
public class e {
    boolean bxB = false;
    boolean byn = true;
    boolean byo = false;
    Vector<f> byq = new Vector<>();
    Vector<String> byp = new Vector<>();
    Thread byr = null;

    public boolean hy(String str) {
        if (!this.bxB || hz(str)) {
            return false;
        }
        if (a.WB().WC()) {
            this.byp.add(str);
            return false;
        }
        f fVar = new f(this, str);
        if (fVar == null) {
            return false;
        }
        this.byq.add(fVar);
        this.byo = true;
        return true;
    }

    boolean hz(String str) {
        String strHA = com.gameloft.a.c.a.a.hA(str);
        for (int i = 0; i < this.byq.size(); i++) {
            f fVarElementAt = this.byq.elementAt(i);
            if (!fVarElementAt.byt && strHA.compareTo(fVarElementAt.byu) == 0 && str.compareTo(fVarElementAt.byv) == 0) {
                return true;
            }
        }
        return false;
    }
}
