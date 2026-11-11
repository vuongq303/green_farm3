package com.google.android.gms.internal.ads;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzalp {
    private final String[] zzcsu;
    private final double[] zzcsv;
    private final double[] zzcsw;
    private final int[] zzcsx;
    private int zzcsy;

    private zzalp(zzals zzalsVar) {
        int size = zzalsVar.zzctd.size();
        this.zzcsu = (String[]) zzalsVar.zzctc.toArray(new String[size]);
        this.zzcsv = zzo(zzalsVar.zzctd);
        this.zzcsw = zzo(zzalsVar.zzcte);
        this.zzcsx = new int[size];
        this.zzcsy = 0;
    }

    private static double[] zzo(List<Double> list) {
        double[] dArr = new double[list.size()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= dArr.length) {
                return dArr;
            }
            dArr[i2] = list.get(i2).doubleValue();
            i = i2 + 1;
        }
    }

    public final void zza(double d) {
        this.zzcsy++;
        for (int i = 0; i < this.zzcsw.length; i++) {
            if (this.zzcsw[i] <= d && d < this.zzcsv[i]) {
                int[] iArr = this.zzcsx;
                iArr[i] = iArr[i] + 1;
            }
            if (d < this.zzcsw[i]) {
                return;
            }
        }
    }

    public final List<zzalr> zzry() {
        ArrayList arrayList = new ArrayList(this.zzcsu.length);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.zzcsu.length) {
                return arrayList;
            }
            arrayList.add(new zzalr(this.zzcsu[i2], this.zzcsw[i2], this.zzcsv[i2], ((double) this.zzcsx[i2]) / ((double) this.zzcsy), this.zzcsx[i2]));
            i = i2 + 1;
        }
    }
}
