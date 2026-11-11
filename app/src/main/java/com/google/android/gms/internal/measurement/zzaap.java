package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzaap {
    private static final zzaap zzbwu = new zzaap(0, new int[0], new Object[0], false);
    private int count;
    private int zzbul;
    private int[] zzbwv;
    private Object[] zzbww;
    private boolean zzbwx;

    private zzaap() {
        this(0, new int[8], new Object[8], true);
    }

    private zzaap(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzbul = -1;
        this.count = 0;
        this.zzbwv = iArr;
        this.zzbww = objArr;
        this.zzbwx = z;
    }

    public static zzaap zzvi() {
        return zzbwu;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && (obj instanceof zzaap);
    }

    public final int hashCode() {
        return 506991;
    }
}
