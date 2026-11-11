package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzye extends zzyh {
    private final int zzbtm;
    private final int zzbtn;

    zzye(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzbtm = i;
        this.zzbtn = i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzyh, com.google.android.gms.internal.measurement.zzyb
    public final int size() {
        return this.zzbtn;
    }

    @Override // com.google.android.gms.internal.measurement.zzyh, com.google.android.gms.internal.measurement.zzyb
    public final byte zzag(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzbto[this.zzbtm + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(22).append("Index < 0: ").append(i).toString());
        }
        throw new ArrayIndexOutOfBoundsException(new StringBuilder(40).append("Index > length: ").append(i).append(", ").append(size).toString());
    }

    @Override // com.google.android.gms.internal.measurement.zzyh
    protected final int zztq() {
        return this.zzbtm;
    }
}
