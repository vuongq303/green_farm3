package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzbaq {
    private static volatile boolean zzdqc;
    int zzdpx;
    int zzdpy;
    private int zzdpz;
    zzbat zzdqa;
    private boolean zzdqb;

    static {
        zzdqc = false;
        zzdqc = true;
    }

    private zzbaq() {
        this.zzdpy = 100;
        this.zzdpz = Integer.MAX_VALUE;
        this.zzdqb = false;
    }

    static zzbaq zza(byte[] bArr, int i, int i2, boolean z) {
        zzbas zzbasVar = new zzbas(bArr, i, i2, z);
        try {
            zzbasVar.zzbr(i2);
            return zzbasVar;
        } catch (zzbbu e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zzbu(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public static long zzl(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public abstract double readDouble();

    public abstract float readFloat();

    public abstract String readString();

    public abstract int zzabk();

    public abstract long zzabl();

    public abstract long zzabm();

    public abstract int zzabn();

    public abstract long zzabo();

    public abstract int zzabp();

    public abstract boolean zzabq();

    public abstract String zzabr();

    public abstract zzbah zzabs();

    public abstract int zzabt();

    public abstract int zzabu();

    public abstract int zzabv();

    public abstract long zzabw();

    public abstract int zzabx();

    public abstract long zzaby();

    abstract long zzabz();

    public abstract boolean zzaca();

    public abstract int zzacb();

    public abstract void zzbp(int i);

    public abstract boolean zzbq(int i);

    public abstract int zzbr(int i);

    public abstract void zzbs(int i);

    public abstract void zzbt(int i);
}
