package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzya {
    private static final Class<?> zzbth = zzfk("libcore.io.Memory");
    private static final boolean zzbti;

    static {
        zzbti = zzfk("org.robolectric.Robolectric") != null;
    }

    private static <T> Class<T> zzfk(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable th) {
            return null;
        }
    }

    static boolean zztn() {
        return (zzbth == null || zzbti) ? false : true;
    }

    static Class<?> zzto() {
        return zzbth;
    }
}
