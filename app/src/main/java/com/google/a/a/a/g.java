package com.google.a.a.a;

import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: compiled from: State.java */
/* JADX INFO: loaded from: classes.dex */
final class g {
    static final g caf = new g(h.caj, 0, 0, 0);
    private final h cag;
    private final int cah;
    private final int cai;
    private final int mode;

    private g(h hVar, int i, int i2, int i3) {
        this.cag = hVar;
        this.mode = i;
        this.cah = i2;
        this.cai = i3;
    }

    int getMode() {
        return this.mode;
    }

    int agx() {
        return this.cah;
    }

    int agy() {
        return this.cai;
    }

    g cr(int i, int i2) {
        h hVarCt;
        int i3 = this.cai;
        h hVar = this.cag;
        if (i != this.mode) {
            int i4 = d.bZZ[this.mode][i];
            i3 += i4 >> 16;
            hVarCt = hVar.ct(65535 & i4, i4 >> 16);
        } else {
            hVarCt = hVar;
        }
        int i5 = i == 2 ? 4 : 5;
        return new g(hVarCt.ct(i2, i5), i, 0, i5 + i3);
    }

    g cs(int i, int i2) {
        h hVar = this.cag;
        int i3 = this.mode == 2 ? 4 : 5;
        return new g(hVar.ct(d.cab[this.mode][i], i3).ct(i2, 5), this.mode, 0, i3 + this.cai + 5);
    }

    g oB(int i) {
        int i2;
        h hVarCt = this.cag;
        int i3 = this.mode;
        int i4 = this.cai;
        if (this.mode == 4 || this.mode == 2) {
            int i5 = d.bZZ[i3][0];
            hVarCt = hVarCt.ct(65535 & i5, i5 >> 16);
            i4 += i5 >> 16;
            i3 = 0;
        }
        if (this.cah == 0 || this.cah == 31) {
            i2 = 18;
        } else {
            i2 = this.cah == 62 ? 9 : 8;
        }
        g gVar = new g(hVarCt, i3, this.cah + 1, i4 + i2);
        return gVar.cah == 2078 ? gVar.oC(i + 1) : gVar;
    }

    g oC(int i) {
        return this.cah == 0 ? this : new g(this.cag.cu(i - this.cah, this.cah), this.mode, 0, this.cai);
    }

    boolean a(g gVar) {
        int i = this.cai + (d.bZZ[this.mode][gVar.mode] >> 16);
        if (gVar.cah > 0 && (this.cah == 0 || this.cah > gVar.cah)) {
            i += 10;
        }
        return i <= gVar.cai;
    }

    com.google.a.b.a t(byte[] bArr) {
        LinkedList linkedList = new LinkedList();
        for (h hVarAgz = oC(bArr.length).cag; hVarAgz != null; hVarAgz = hVarAgz.agz()) {
            linkedList.addFirst(hVarAgz);
        }
        com.google.a.b.a aVar = new com.google.a.b.a();
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            ((h) it.next()).a(aVar, bArr);
        }
        return aVar;
    }

    public String toString() {
        return String.format("%s bits=%d bytes=%d", d.bZY[this.mode], Integer.valueOf(this.cai), Integer.valueOf(this.cah));
    }
}
