package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzbd extends zzbfc<zzbd> {
    private Long zzgl = null;
    private Integer zzgm = null;
    private Boolean zzgn = null;
    private int[] zzgo = zzbfl.zzeby;
    private Long zzgp = null;

    public zzbd() {
        this.zzebt = -1;
    }

    @Override // com.google.android.gms.internal.ads.zzbfi
    public final /* synthetic */ zzbfi zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    this.zzgl = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 16:
                    this.zzgm = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 24:
                    this.zzgn = Boolean.valueOf(zzbezVar.zzabq());
                    break;
                case 32:
                    int iZzb = zzbfl.zzb(zzbezVar, 32);
                    int length = this.zzgo == null ? 0 : this.zzgo.length;
                    int[] iArr = new int[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzgo, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = zzbezVar.zzacc();
                        zzbezVar.zzabk();
                        length++;
                    }
                    iArr[length] = zzbezVar.zzacc();
                    this.zzgo = iArr;
                    break;
                case 34:
                    int iZzbr = zzbezVar.zzbr(zzbezVar.zzacc());
                    int position = zzbezVar.getPosition();
                    int i = 0;
                    while (zzbezVar.zzagn() > 0) {
                        zzbezVar.zzacc();
                        i++;
                    }
                    zzbezVar.zzdc(position);
                    int length2 = this.zzgo == null ? 0 : this.zzgo.length;
                    int[] iArr2 = new int[i + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzgo, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = zzbezVar.zzacc();
                        length2++;
                    }
                    this.zzgo = iArr2;
                    zzbezVar.zzbs(iZzbr);
                    break;
                case 40:
                    this.zzgp = Long.valueOf(zzbezVar.zzacd());
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
        if (this.zzgl != null) {
            zzbfaVar.zzi(1, this.zzgl.longValue());
        }
        if (this.zzgm != null) {
            zzbfaVar.zzm(2, this.zzgm.intValue());
        }
        if (this.zzgn != null) {
            zzbfaVar.zzf(3, this.zzgn.booleanValue());
        }
        if (this.zzgo != null && this.zzgo.length > 0) {
            for (int i = 0; i < this.zzgo.length; i++) {
                zzbfaVar.zzm(4, this.zzgo[i]);
            }
        }
        if (this.zzgp != null) {
            zzbfaVar.zza(5, this.zzgp.longValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int i;
        int iZzce = 0;
        int iZzr = super.zzr();
        if (this.zzgl != null) {
            iZzr += zzbfa.zzd(1, this.zzgl.longValue());
        }
        if (this.zzgm != null) {
            iZzr += zzbfa.zzq(2, this.zzgm.intValue());
        }
        if (this.zzgn != null) {
            this.zzgn.booleanValue();
            iZzr += zzbfa.zzcd(3) + 1;
        }
        if (this.zzgo != null && this.zzgo.length > 0) {
            int i2 = 0;
            while (true) {
                i = iZzce;
                if (i2 >= this.zzgo.length) {
                    break;
                }
                iZzce = zzbfa.zzce(this.zzgo[i2]) + i;
                i2++;
            }
            iZzr = iZzr + i + (this.zzgo.length * 1);
        }
        return this.zzgp != null ? iZzr + zzbfa.zze(5, this.zzgp.longValue()) : iZzr;
    }
}
