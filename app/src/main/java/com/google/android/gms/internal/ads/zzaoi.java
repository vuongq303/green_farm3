package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaoi<T> extends zzaoj<T> {
    private final T zzcwe;

    private zzaoi(T t) {
        this.zzcwe = t;
    }

    public static <T> zzaoi<T> zzj(T t) {
        return new zzaoi<>(t);
    }

    public final void zzsn() {
        set(this.zzcwe);
    }
}
