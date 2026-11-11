package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.Vector;

/* JADX INFO: compiled from: ItemPriceArray.java */
/* JADX INFO: loaded from: classes.dex */
public class ba {
    private Vector aiv = new Vector();

    public int a(bc[] bcVarArr) {
        this.aiv.removeAllElements();
        for (bc bcVar : bcVarArr) {
            az azVar = new az();
            azVar.a(bcVar);
            this.aiv.addElement(azVar);
        }
        return 0;
    }

    public int hP() {
        return this.aiv.size();
    }

    public String eG(int i) {
        if (i < this.aiv.size()) {
            return ((az) this.aiv.elementAt(i)).nn();
        }
        return null;
    }

    public double eH(int i) {
        if (i < this.aiv.size()) {
            return ((az) this.aiv.elementAt(i)).nm();
        }
        return 0.0d;
    }
}
