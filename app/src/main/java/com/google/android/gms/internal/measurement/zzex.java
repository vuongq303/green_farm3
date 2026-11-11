package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzex<V> {
    private final V zzaid;
    private final String zznt;
    private final V zzzw;

    private zzex(String str, V v, V v2) {
        this.zzzw = v;
        this.zzaid = v2;
        this.zznt = str;
    }

    static zzex<Double> zza(String str, double d, double d2) {
        zzex<Double> zzexVar = new zzex<>(str, Double.valueOf(-3.0d), Double.valueOf(-3.0d));
        zzew.zzage.add(zzexVar);
        return zzexVar;
    }

    static zzex<Long> zzb(String str, long j, long j2) {
        zzex<Long> zzexVar = new zzex<>(str, Long.valueOf(j), Long.valueOf(j2));
        zzew.zzagb.add(zzexVar);
        return zzexVar;
    }

    static zzex<Boolean> zzb(String str, boolean z, boolean z2) {
        zzex<Boolean> zzexVar = new zzex<>(str, Boolean.valueOf(z), Boolean.valueOf(z2));
        zzew.zzagc.add(zzexVar);
        return zzexVar;
    }

    static zzex<Integer> zzc(String str, int i, int i2) {
        zzex<Integer> zzexVar = new zzex<>(str, Integer.valueOf(i), Integer.valueOf(i2));
        zzew.zzaga.add(zzexVar);
        return zzexVar;
    }

    static zzex<String> zzd(String str, String str2, String str3) {
        zzex<String> zzexVar = new zzex<>(str, str2, str3);
        zzew.zzagd.add(zzexVar);
        return zzexVar;
    }

    public final V get() {
        return this.zzzw;
    }

    public final V get(V v) {
        return v != null ? v : this.zzzw;
    }

    public final String getKey() {
        return this.zznt;
    }
}
