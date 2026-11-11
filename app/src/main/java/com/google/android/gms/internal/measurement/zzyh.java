package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
class zzyh extends zzyg {
    protected final byte[] zzbto;

    zzyh(byte[] bArr) {
        this.zzbto = bArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzyb
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof zzyb) && size() == ((zzyb) obj).size()) {
            if (size() == 0) {
                return true;
            }
            if (!(obj instanceof zzyh)) {
                return obj.equals(this);
            }
            int iZztp = zztp();
            int iZztp2 = ((zzyh) obj).zztp();
            if (iZztp == 0 || iZztp2 == 0 || iZztp == iZztp2) {
                return zza((zzyh) obj, 0, size());
            }
            return false;
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzyb
    public int size() {
        return this.zzbto.length;
    }

    @Override // com.google.android.gms.internal.measurement.zzyb
    protected final int zza(int i, int i2, int i3) {
        return zzyw.zza(i, this.zzbto, zztq(), i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzyg
    final boolean zza(zzyb zzybVar, int i, int i2) {
        if (i2 > zzybVar.size()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(size()).toString());
        }
        if (i2 > zzybVar.size()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: 0, ").append(i2).append(", ").append(zzybVar.size()).toString());
        }
        if (!(zzybVar instanceof zzyh)) {
            return zzybVar.zzb(0, i2).equals(zzb(0, i2));
        }
        zzyh zzyhVar = (zzyh) zzybVar;
        byte[] bArr = this.zzbto;
        byte[] bArr2 = zzyhVar.zzbto;
        int iZztq = zztq() + i2;
        int iZztq2 = zztq();
        int iZztq3 = zzyhVar.zztq();
        while (iZztq2 < iZztq) {
            if (bArr[iZztq2] != bArr2[iZztq3]) {
                return false;
            }
            iZztq2++;
            iZztq3++;
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzyb
    public byte zzag(int i) {
        return this.zzbto[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzyb
    public final zzyb zzb(int i, int i2) {
        int iZzb = zzb(0, i2, size());
        return iZzb == 0 ? zzyb.zzbtj : new zzye(this.zzbto, zztq(), iZzb);
    }

    protected int zztq() {
        return 0;
    }
}
