package com.google.android.gms.internal.ads;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzals {
    private final List<String> zzctc = new ArrayList();
    private final List<Double> zzctd = new ArrayList();
    private final List<Double> zzcte = new ArrayList();

    public final zzals zza(String str, double d, double d2) {
        int i;
        int i2 = 0;
        while (true) {
            i = i2;
            if (i >= this.zzctc.size()) {
                break;
            }
            double dDoubleValue = this.zzcte.get(i).doubleValue();
            double dDoubleValue2 = this.zzctd.get(i).doubleValue();
            if (d < dDoubleValue || (dDoubleValue == d && d2 < dDoubleValue2)) {
                break;
            }
            i2 = i + 1;
        }
        this.zzctc.add(i, str);
        this.zzcte.add(i, Double.valueOf(d));
        this.zzctd.add(i, Double.valueOf(d2));
        return this;
    }

    public final zzalp zzrz() {
        return new zzalp(this);
    }
}
