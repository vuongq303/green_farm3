package com.google.a.b.a;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: ReedSolomonEncoder.java */
/* JADX INFO: loaded from: classes.dex */
public final class c {
    private final a cbh;
    private final List<b> cbj = new ArrayList();

    public c(a aVar) {
        this.cbh = aVar;
        this.cbj.add(new b(aVar, new int[]{1}));
    }

    private b oI(int i) {
        if (i >= this.cbj.size()) {
            b bVar = this.cbj.get(this.cbj.size() - 1);
            b bVarB = bVar;
            for (int size = this.cbj.size(); size <= i; size++) {
                bVarB = bVarB.b(new b(this.cbh, new int[]{1, this.cbh.oE((size - 1) + this.cbh.agF())}));
                this.cbj.add(bVarB);
            }
        }
        return this.cbj.get(i);
    }

    public void g(int[] iArr, int i) {
        if (i == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int length = iArr.length - i;
        if (length <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        b bVarOI = oI(i);
        int[] iArr2 = new int[length];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        int[] iArrAgG = new b(this.cbh, iArr2).cA(i, 1).c(bVarOI)[1].agG();
        int length2 = i - iArrAgG.length;
        for (int i2 = 0; i2 < length2; i2++) {
            iArr[length + i2] = 0;
        }
        System.arraycopy(iArrAgG, 0, iArr, length + length2, iArrAgG.length);
    }
}
