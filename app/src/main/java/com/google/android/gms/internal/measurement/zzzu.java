package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzzu<T> implements zzaaa<T> {
    private final zzzq zzbvv;
    private final zzaao<?, ?> zzbvw;
    private final boolean zzbvx;
    private final zzyo<?> zzbvy;

    private zzzu(zzaao<?, ?> zzaaoVar, zzyo<?> zzyoVar, zzzq zzzqVar) {
        this.zzbvw = zzaaoVar;
        this.zzbvx = zzyoVar.zza(zzzqVar);
        this.zzbvy = zzyoVar;
        this.zzbvv = zzzqVar;
    }

    static <T> zzzu<T> zza(zzaao<?, ?> zzaaoVar, zzyo<?> zzyoVar, zzzq zzzqVar) {
        return new zzzu<>(zzaaoVar, zzyoVar, zzzqVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzaaa
    public final boolean equals(T t, T t2) {
        if (!this.zzbvw.zzw(t).equals(this.zzbvw.zzw(t2))) {
            return false;
        }
        if (this.zzbvx) {
            return this.zzbvy.zzu(t).equals(this.zzbvy.zzu(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzaaa
    public final int hashCode(T t) {
        int iHashCode = this.zzbvw.zzw(t).hashCode();
        return this.zzbvx ? (iHashCode * 53) + this.zzbvy.zzu(t).hashCode() : iHashCode;
    }
}
