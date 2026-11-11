package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzyq {
    private static final zzyo<?> zzbuc = new zzyp();
    private static final zzyo<?> zzbud = zztx();

    private static zzyo<?> zztx() {
        try {
            return (zzyo) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static zzyo<?> zzty() {
        return zzbuc;
    }

    static zzyo<?> zztz() {
        if (zzbud == null) {
            throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
        }
        return zzbud;
    }
}
