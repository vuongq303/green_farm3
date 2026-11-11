package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzzx {
    private static final zzzv zzbvz = zzuu();
    private static final zzzv zzbwa = new zzzw();

    static zzzv zzus() {
        return zzbvz;
    }

    static zzzv zzut() {
        return zzbwa;
    }

    private static zzzv zzuu() {
        try {
            return (zzzv) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
