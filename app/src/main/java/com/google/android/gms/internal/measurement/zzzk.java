package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzzk implements zzzp {
    private zzzp[] zzbvr;

    zzzk(zzzp... zzzpVarArr) {
        this.zzbvr = zzzpVarArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzzp
    public final boolean zzd(Class<?> cls) {
        for (zzzp zzzpVar : this.zzbvr) {
            if (zzzpVar.zzd(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzzp
    public final zzzo zze(Class<?> cls) {
        for (zzzp zzzpVar : this.zzbvr) {
            if (zzzpVar.zzd(cls)) {
                return zzzpVar.zze(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
