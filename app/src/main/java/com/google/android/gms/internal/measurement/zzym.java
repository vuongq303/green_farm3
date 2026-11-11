package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzym {
    private static final Class<?> zzbtx = zztt();

    private static final zzyn zzfm(String str) {
        return (zzyn) zzbtx.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }

    private static Class<?> zztt() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzyn zztu() {
        if (zzbtx != null) {
            try {
                return zzfm("getEmptyRegistry");
            } catch (Exception e) {
            }
        }
        return zzyn.zzbua;
    }
}
