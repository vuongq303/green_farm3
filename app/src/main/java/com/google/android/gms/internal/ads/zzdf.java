package com.google.android.gms.internal.ads;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzdf extends zzbh<Integer, Long> {
    public Long zzfp;
    public Long zzfr;
    public Long zzfv;
    public Long zzfw;
    public Long zzsp;
    public Long zzsq;
    public Long zzsr;
    public Long zzss;
    public Long zzst;
    public Long zzsu;
    public Long zzsv;

    public zzdf() {
    }

    public zzdf(String str) {
        zzj(str);
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final void zzj(String str) {
        HashMap mapZzk = zzk(str);
        if (mapZzk != null) {
            this.zzsp = (Long) mapZzk.get(0);
            this.zzsq = (Long) mapZzk.get(1);
            this.zzsr = (Long) mapZzk.get(2);
            this.zzfr = (Long) mapZzk.get(3);
            this.zzfp = (Long) mapZzk.get(4);
            this.zzss = (Long) mapZzk.get(5);
            this.zzst = (Long) mapZzk.get(6);
            this.zzsu = (Long) mapZzk.get(7);
            this.zzfw = (Long) mapZzk.get(8);
            this.zzfv = (Long) mapZzk.get(9);
            this.zzsv = (Long) mapZzk.get(10);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final HashMap<Integer, Long> zzu() {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(0, this.zzsp);
        map.put(1, this.zzsq);
        map.put(2, this.zzsr);
        map.put(3, this.zzfr);
        map.put(4, this.zzfp);
        map.put(5, this.zzss);
        map.put(6, this.zzst);
        map.put(7, this.zzsu);
        map.put(8, this.zzfw);
        map.put(9, this.zzfv);
        map.put(10, this.zzsv);
        return map;
    }
}
