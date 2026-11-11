package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzaac {
    private static final Class<?> zzbwe = zzuz();
    private static final zzaao<?, ?> zzbwf = zzw(false);
    private static final zzaao<?, ?> zzbwg = zzw(true);
    private static final zzaao<?, ?> zzbwh = new zzaaq();

    public static void zzh(Class<?> cls) {
        if (!zzyv.class.isAssignableFrom(cls) && zzbwe != null && !zzbwe.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static zzaao<?, ?> zzuw() {
        return zzbwf;
    }

    public static zzaao<?, ?> zzux() {
        return zzbwg;
    }

    public static zzaao<?, ?> zzuy() {
        return zzbwh;
    }

    private static Class<?> zzuz() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zzva() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            return null;
        }
    }

    private static zzaao<?, ?> zzw(boolean z) {
        try {
            Class<?> clsZzva = zzva();
            if (clsZzva == null) {
                return null;
            }
            return (zzaao) clsZzva.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable th) {
            return null;
        }
    }
}
