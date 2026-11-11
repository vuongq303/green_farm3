package com.google.android.gms.internal.ads;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzcm extends zzbh<Integer, Long> {
    public long zzri;
    public long zzrj;

    public zzcm() {
        this.zzri = -1L;
        this.zzrj = -1L;
    }

    public zzcm(String str) {
        this();
        zzj(str);
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final void zzj(String str) {
        HashMap mapZzk = zzk(str);
        if (mapZzk != null) {
            this.zzri = ((Long) mapZzk.get(0)).longValue();
            this.zzrj = ((Long) mapZzk.get(1)).longValue();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbh
    protected final HashMap<Integer, Long> zzu() {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(0, Long.valueOf(this.zzri));
        map.put(1, Long.valueOf(this.zzrj));
        return map;
    }
}
