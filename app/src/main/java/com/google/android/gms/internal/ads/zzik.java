package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzik extends zzbfc<zzik> {
    private Integer zzanu = null;
    private int[] zzans = zzbfl.zzeby;

    public zzik() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzm, reason: merged with bridge method [inline-methods] */
    public final zzik zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzanu = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 16:
                    int iZzb = zzbfl.zzb(zzbezVar, 16);
                    int length = this.zzans == null ? 0 : this.zzans.length;
                    int[] iArr = new int[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzans, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = zzbezVar.zzacc();
                        zzbezVar.zzabk();
                        length++;
                    }
                    iArr[length] = zzbezVar.zzacc();
                    this.zzans = iArr;
                    break;
                case 18:
                    int iZzbr = zzbezVar.zzbr(zzbezVar.zzacc());
                    int position2 = zzbezVar.getPosition();
                    int i = 0;
                    while (zzbezVar.zzagn() > 0) {
                        zzbezVar.zzacc();
                        i++;
                    }
                    zzbezVar.zzdc(position2);
                    int length2 = this.zzans == null ? 0 : this.zzans.length;
                    int[] iArr2 = new int[i + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzans, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = zzbezVar.zzacc();
                        length2++;
                    }
                    this.zzans = iArr2;
                    zzbezVar.zzbs(iZzbr);
                    break;
                default:
                    if (!super.zza(zzbezVar, iZzabk)) {
                    }
                    break;
            }
        }
        return this;
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    public final void zza(zzbfa zzbfaVar) throws zzbfb {
        if (this.zzanu != null) {
            zzbfaVar.zzm(1, this.zzanu.intValue());
        }
        if (this.zzans != null && this.zzans.length > 0) {
            for (int i = 0; i < this.zzans.length; i++) {
                zzbfaVar.zzm(2, this.zzans[i]);
            }
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzce = 0;
        int iZzr = super.zzr();
        if (this.zzanu != null) {
            iZzr += zzbfa.zzq(1, this.zzanu.intValue());
        }
        if (this.zzans == null || this.zzans.length <= 0) {
            return iZzr;
        }
        int i = 0;
        while (true) {
            int i2 = iZzce;
            if (i >= this.zzans.length) {
                return iZzr + i2 + (this.zzans.length * 1);
            }
            iZzce = zzbfa.zzce(this.zzans[i]) + i2;
            i++;
        }
    }
}
