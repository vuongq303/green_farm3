package com.google.firebase.components;

/* JADX INFO: loaded from: classes.dex */
public final class zzk {
    public static void checkState(boolean z, String str) {
        if (!z) {
            throw new IllegalStateException(str);
        }
    }

    public static <T> T zza(T t, String str) {
        if (t == null) {
            throw new NullPointerException(str);
        }
        return t;
    }
}
