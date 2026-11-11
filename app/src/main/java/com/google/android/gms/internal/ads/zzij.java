package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzij extends zzbfc<zzij> {
    private String zzanq = null;
    private Integer zzanr = null;
    private int[] zzans = zzbfl.zzeby;
    private zzis zzant = null;

    public zzij() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzl, reason: merged with bridge method [inline-methods] */
    public final zzij zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.zzanq = zzbezVar.readString();
                    break;
                case 16:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzanr = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 24:
                    int iZzb = zzbfl.zzb(zzbezVar, 24);
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
                case 26:
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
                case 34:
                    if (this.zzant == null) {
                        this.zzant = new zzis();
                    }
                    zzbezVar.zza(this.zzant);
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
        if (this.zzanq != null) {
            zzbfaVar.zzf(1, this.zzanq);
        }
        if (this.zzanr != null) {
            zzbfaVar.zzm(2, this.zzanr.intValue());
        }
        if (this.zzans != null && this.zzans.length > 0) {
            for (int i = 0; i < this.zzans.length; i++) {
                zzbfaVar.zzm(3, this.zzans[i]);
            }
        }
        if (this.zzant != null) {
            zzbfaVar.zza(4, this.zzant);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int i;
        int iZzce = 0;
        int iZzr = super.zzr();
        if (this.zzanq != null) {
            iZzr += zzbfa.zzg(1, this.zzanq);
        }
        if (this.zzanr != null) {
            iZzr += zzbfa.zzq(2, this.zzanr.intValue());
        }
        if (this.zzans != null && this.zzans.length > 0) {
            int i2 = 0;
            while (true) {
                i = iZzce;
                if (i2 >= this.zzans.length) {
                    break;
                }
                iZzce = zzbfa.zzce(this.zzans[i2]) + i;
                i2++;
            }
            iZzr = iZzr + i + (this.zzans.length * 1);
        }
        return this.zzant != null ? iZzr + zzbfa.zzb(4, this.zzant) : iZzr;
    }
}
