package com.google.android.gms.common.data;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class j<T> extends a<T> {
    private boolean bNq;
    private ArrayList<Integer> bNr;

    protected j(DataHolder dataHolder) {
        super(dataHolder);
        this.bNq = false;
    }

    private final void ZY() {
        synchronized (this) {
            if (!this.bNq) {
                int count = this.mDataHolder.getCount();
                this.bNr = new ArrayList<>();
                if (count > 0) {
                    this.bNr.add(0);
                    String strZX = ZX();
                    String strR = this.mDataHolder.r(strZX, 0, this.mDataHolder.nF(0));
                    int i = 1;
                    while (i < count) {
                        int iNF = this.mDataHolder.nF(i);
                        String strR2 = this.mDataHolder.r(strZX, i, iNF);
                        if (strR2 == null) {
                            throw new NullPointerException(new StringBuilder(String.valueOf(strZX).length() + 78).append("Missing value for markerColumn: ").append(strZX).append(", at row: ").append(i).append(", for window: ").append(iNF).toString());
                        }
                        if (strR2.equals(strR)) {
                            strR2 = strR;
                        } else {
                            this.bNr.add(Integer.valueOf(i));
                        }
                        i++;
                        strR = strR2;
                    }
                }
                this.bNq = true;
            }
        }
    }

    protected abstract String ZX();

    protected String ZZ() {
        return null;
    }

    protected abstract T cq(int i, int i2);

    @Override // com.google.android.gms.common.data.c
    public final T get(int i) {
        ZY();
        return cq(nI(i), nJ(i));
    }

    @Override // com.google.android.gms.common.data.a, com.google.android.gms.common.data.c
    public int getCount() {
        ZY();
        return this.bNr.size();
    }

    final int nI(int i) {
        if (i < 0 || i >= this.bNr.size()) {
            throw new IllegalArgumentException(new StringBuilder(53).append("Position ").append(i).append(" is out of bounds for this buffer").toString());
        }
        return this.bNr.get(i).intValue();
    }

    protected int nJ(int i) {
        if (i < 0 || i == this.bNr.size()) {
            return 0;
        }
        int count = i == this.bNr.size() + (-1) ? this.mDataHolder.getCount() - this.bNr.get(i).intValue() : this.bNr.get(i + 1).intValue() - this.bNr.get(i).intValue();
        if (count != 1) {
            return count;
        }
        int iNI = nI(i);
        int iNF = this.mDataHolder.nF(iNI);
        String strZZ = ZZ();
        if (strZZ == null || this.mDataHolder.r(strZZ, iNI, iNF) != null) {
            return count;
        }
        return 0;
    }
}
