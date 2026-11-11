package com.google.a.f.a;

/* JADX INFO: compiled from: Version.java */
/* JADX INFO: loaded from: classes.dex */
public final class e {
    private final int cdm;
    private final d[] cdn;

    e(int i, d... dVarArr) {
        this.cdm = i;
        this.cdn = dVarArr;
    }

    public int ahs() {
        return this.cdm;
    }

    public int aht() {
        d[] dVarArr = this.cdn;
        int length = dVarArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int count = dVarArr[i].getCount() + i2;
            i++;
            i2 = count;
        }
        return i2;
    }

    public int ahu() {
        return this.cdm * aht();
    }

    public d[] ahv() {
        return this.cdn;
    }
}
