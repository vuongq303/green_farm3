package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzyl extends zzyj {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzbtt;
    private int zzbtu;
    private int zzbtv;
    private int zzbtw;

    private zzyl(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzbtw = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i + i2;
        this.pos = i;
        this.zzbtv = this.pos;
        this.zzbtt = z;
    }

    private final void zzts() {
        this.limit += this.zzbtu;
        int i = this.limit - this.zzbtv;
        if (i <= this.zzbtw) {
            this.zzbtu = 0;
        } else {
            this.zzbtu = i - this.zzbtw;
            this.limit -= this.zzbtu;
        }
    }

    public final int zzah(int i) {
        if (i < 0) {
            throw zzyy.zzuf();
        }
        int iZztr = zztr() + i;
        int i2 = this.zzbtw;
        if (iZztr > i2) {
            throw zzyy.zzue();
        }
        this.zzbtw = iZztr;
        zzts();
        return i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzyj
    public final int zztr() {
        return this.pos - this.zzbtv;
    }
}
