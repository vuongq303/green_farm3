package com.google.android.gms.internal.ads;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzdh extends zzbh<Integer, Long> {
    public Long zzgj;
    public Long zzgk;
    public Long zzsx;

    public zzdh() {
    }

    public zzdh(String str) {
        zzj(str);
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final void zzj(String str) {
        HashMap mapZzk = zzk(str);
        if (mapZzk != null) {
            this.zzsx = (Long) mapZzk.get(0);
            this.zzgj = (Long) mapZzk.get(1);
            this.zzgk = (Long) mapZzk.get(2);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final HashMap<Integer, Long> zzu() {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(0, this.zzsx);
        map.put(1, this.zzgj);
        map.put(2, this.zzgk);
        return map;
    }
}
