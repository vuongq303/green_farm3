package com.google.android.gms.internal.measurement;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzzb<K> implements Map.Entry<K, Object> {
    private Map.Entry<K, zzyz> zzbvg;

    private zzzb(Map.Entry<K, zzyz> entry) {
        this.zzbvg = entry;
    }

    @Override // java.util.Map.Entry
    public final K getKey() {
        return this.zzbvg.getKey();
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (this.zzbvg.getValue() == null) {
            return null;
        }
        return zzyz.zzug();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (obj instanceof zzzq) {
            return this.zzbvg.getValue().zzc((zzzq) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
