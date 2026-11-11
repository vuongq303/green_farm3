package com.google.android.gms.internal.ads;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzbj extends zzbh<Integer, Object> {
    public String zzcx;
    public String zzcz;
    public String zzda;
    public String zzdb;
    public long zzhx;

    public zzbj() {
        this.zzcx = "E";
        this.zzhx = -1L;
        this.zzcz = "E";
        this.zzda = "E";
        this.zzdb = "E";
    }

    public zzbj(String str) {
        this();
        zzj(str);
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final void zzj(String str) {
        HashMap mapZzk = zzk(str);
        if (mapZzk != null) {
            this.zzcx = mapZzk.get(0) == null ? "E" : (String) mapZzk.get(0);
            this.zzhx = mapZzk.get(1) == null ? -1L : ((Long) mapZzk.get(1)).longValue();
            this.zzcz = mapZzk.get(2) == null ? "E" : (String) mapZzk.get(2);
            this.zzda = mapZzk.get(3) == null ? "E" : (String) mapZzk.get(3);
            this.zzdb = mapZzk.get(4) == null ? "E" : (String) mapZzk.get(4);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final HashMap<Integer, Object> zzu() {
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(0, this.zzcx);
        map.put(4, this.zzdb);
        map.put(3, this.zzda);
        map.put(2, this.zzcz);
        map.put(1, Long.valueOf(this.zzhx));
        return map;
    }
}
