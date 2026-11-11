package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzyj {
    private static volatile boolean zzbts;
    int zzbtp;
    private int zzbtq;
    private boolean zzbtr;

    static {
        zzbts = false;
        zzbts = true;
    }

    private zzyj() {
        this.zzbtp = 100;
        this.zzbtq = Integer.MAX_VALUE;
        this.zzbtr = false;
    }

    static zzyj zza(byte[] bArr, int i, int i2, boolean z) {
        zzyl zzylVar = new zzyl(bArr, i, i2);
        try {
            zzylVar.zzah(i2);
            return zzylVar;
        } catch (zzyy e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract int zztr();
}
