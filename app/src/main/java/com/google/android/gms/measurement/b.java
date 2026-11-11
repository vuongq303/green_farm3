package com.google.android.gms.measurement;

import android.os.Bundle;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.measurement.zzjv;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public Bundle bXA;
    public long bXB;
    public boolean bXC;
    public long bXD;
    public String bXq;
    public String bXr;
    public String bXs;
    public long bXt;
    public String bXu;
    public Bundle bXv;
    public String bXw;
    public Bundle bXx;
    public long bXy;
    public String bXz;
    public String mName;
    public Object mValue;

    public b() {
    }

    public b(b bVar) {
        bf.checkNotNull(bVar);
        this.bXq = bVar.bXq;
        this.bXr = bVar.bXr;
        this.bXB = bVar.bXB;
        this.mName = bVar.mName;
        if (bVar.mValue != null) {
            this.mValue = zzjv.zzf(bVar.mValue);
            if (this.mValue == null) {
                this.mValue = bVar.mValue;
            }
        }
        this.mValue = bVar.mValue;
        this.bXC = bVar.bXC;
        this.bXs = bVar.bXs;
        this.bXt = bVar.bXt;
        this.bXu = bVar.bXu;
        if (bVar.bXv != null) {
            this.bXv = new Bundle(bVar.bXv);
        }
        this.bXw = bVar.bXw;
        if (bVar.bXx != null) {
            this.bXx = new Bundle(bVar.bXx);
        }
        this.bXD = bVar.bXD;
        this.bXy = bVar.bXy;
        this.bXz = bVar.bXz;
        if (bVar.bXA != null) {
            this.bXA = new Bundle(bVar.bXA);
        }
    }
}
