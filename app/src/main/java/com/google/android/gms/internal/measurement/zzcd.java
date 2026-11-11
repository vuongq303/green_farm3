package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.a.a;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzcd<V> {
    private final V zzzw;
    private final a<V> zzzx;

    private zzcd(a<V> aVar, V v) {
        bf.checkNotNull(aVar);
        this.zzzx = aVar;
        this.zzzw = v;
    }

    static zzcd<Float> zza(String str, float f, float f2) {
        return new zzcd<>(a.a(str, Float.valueOf(0.5f)), Float.valueOf(0.5f));
    }

    static zzcd<Integer> zza(String str, int i, int i2) {
        return new zzcd<>(a.a(str, Integer.valueOf(i2)), Integer.valueOf(i));
    }

    static zzcd<Long> zza(String str, long j, long j2) {
        return new zzcd<>(a.a(str, Long.valueOf(j2)), Long.valueOf(j));
    }

    static zzcd<String> zza(String str, String str2, String str3) {
        return new zzcd<>(a.av(str, str3), str2);
    }

    static zzcd<Boolean> zza(String str, boolean z, boolean z2) {
        return new zzcd<>(a.r(str, z2), Boolean.valueOf(z));
    }

    public final V get() {
        return this.zzzw;
    }
}
