package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzyu implements zzzp {
    private static final zzyu zzbuj = new zzyu();

    private zzyu() {
    }

    public static zzyu zzud() {
        return zzbuj;
    }

    @Override // com.google.android.gms.internal.measurement.zzzp
    public final boolean zzd(Class<?> cls) {
        return zzyv.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.measurement.zzzp
    public final zzzo zze(Class<?> cls) {
        if (!zzyv.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzzo) zzyv.zzf(cls.asSubclass(zzyv.class)).zza(3, (Object) null, (Object) null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
