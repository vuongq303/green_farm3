package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzbg extends zzbfc<zzbg> {
    public Integer zzfe;
    private Integer zzff;
    public byte[][] zzgv = zzbfl.zzece;
    public byte[] zzgq = null;

    public zzbg() {
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzbg zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    int iZzb = zzbfl.zzb(zzbezVar, 10);
                    int length = this.zzgv == null ? 0 : this.zzgv.length;
                    byte[][] bArr = new byte[iZzb + length][];
                    if (length != 0) {
                        System.arraycopy(this.zzgv, 0, bArr, 0, length);
                    }
                    while (length < bArr.length - 1) {
                        bArr[length] = zzbezVar.readBytes();
                        zzbezVar.zzabk();
                        length++;
                    }
                    bArr[length] = zzbezVar.readBytes();
                    this.zzgv = bArr;
                    break;
                case 18:
                    this.zzgq = zzbezVar.readBytes();
                    break;
                case 24:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzff = Integer.valueOf(zzaz.zze(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 32:
                    int position2 = zzbezVar.getPosition();
                    try {
                        this.zzfe = Integer.valueOf(zzaz.zzf(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
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
        if (this.zzgv != null && this.zzgv.length > 0) {
            for (int i = 0; i < this.zzgv.length; i++) {
                byte[] bArr = this.zzgv[i];
                if (bArr != null) {
                    zzbfaVar.zza(1, bArr);
                }
            }
        }
        if (this.zzgq != null) {
            zzbfaVar.zza(2, this.zzgq);
        }
        if (this.zzff != null) {
            zzbfaVar.zzm(3, this.zzff.intValue());
        }
        if (this.zzfe != null) {
            zzbfaVar.zzm(4, this.zzfe.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzq;
        int iZzv;
        int iZzr = super.zzr();
        if (this.zzgv == null || this.zzgv.length <= 0) {
            iZzq = iZzr;
        } else {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (i < this.zzgv.length) {
                byte[] bArr = this.zzgv[i];
                if (bArr != null) {
                    i3++;
                    iZzv = zzbfa.zzv(bArr) + i2;
                } else {
                    iZzv = i2;
                }
                i++;
                i2 = iZzv;
            }
            iZzq = iZzr + i2 + (i3 * 1);
        }
        if (this.zzgq != null) {
            iZzq += zzbfa.zzb(2, this.zzgq);
        }
        if (this.zzff != null) {
            iZzq += zzbfa.zzq(3, this.zzff.intValue());
        }
        return this.zzfe != null ? iZzq + zzbfa.zzq(4, this.zzfe.intValue()) : iZzq;
    }
}
