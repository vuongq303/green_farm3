package com.google.android.gms.internal.ads;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzcx extends zzbh<Integer, Object> {
    public Long zzro;
    public Boolean zzrp;
    public Boolean zzrq;

    public zzcx() {
    }

    public zzcx(String str) {
        zzj(str);
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final void zzj(String str) {
        HashMap mapZzk = zzk(str);
        if (mapZzk != null) {
            this.zzro = (Long) mapZzk.get(0);
            this.zzrp = (Boolean) mapZzk.get(1);
            this.zzrq = (Boolean) mapZzk.get(2);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final HashMap<Integer, Object> zzu() {
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(0, this.zzro);
        map.put(1, this.zzrp);
        map.put(2, this.zzrq);
        return map;
    }
}
