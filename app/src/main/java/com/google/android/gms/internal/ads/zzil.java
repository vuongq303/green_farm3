package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzil extends zzbfc<zzil> {
    private zzij zzanv = null;
    private zzir[] zzanw = zzir.zzhs();
    private Integer zzanu = null;
    private zzis zzant = null;

    public zzil() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzn, reason: merged with bridge method [inline-methods] */
    public final zzil zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    if (this.zzanv == null) {
                        this.zzanv = new zzij();
                    }
                    zzbezVar.zza(this.zzanv);
                    break;
                case 18:
                    int iZzb = zzbfl.zzb(zzbezVar, 18);
                    int length = this.zzanw == null ? 0 : this.zzanw.length;
                    zzir[] zzirVarArr = new zzir[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzanw, 0, zzirVarArr, 0, length);
                    }
                    while (length < zzirVarArr.length - 1) {
                        zzirVarArr[length] = new zzir();
                        zzbezVar.zza(zzirVarArr[length]);
                        zzbezVar.zzabk();
                        length++;
                    }
                    zzirVarArr[length] = new zzir();
                    zzbezVar.zza(zzirVarArr[length]);
                    this.zzanw = zzirVarArr;
                    break;
                case 24:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzanu = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
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
        if (this.zzanv != null) {
            zzbfaVar.zza(1, this.zzanv);
        }
        if (this.zzanw != null && this.zzanw.length > 0) {
            for (int i = 0; i < this.zzanw.length; i++) {
                zzir zzirVar = this.zzanw[i];
                if (zzirVar != null) {
                    zzbfaVar.zza(2, zzirVar);
                }
            }
        }
        if (this.zzanu != null) {
            zzbfaVar.zzm(3, this.zzanu.intValue());
        }
        if (this.zzant != null) {
            zzbfaVar.zza(4, this.zzant);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzanv != null) {
            iZzr += zzbfa.zzb(1, this.zzanv);
        }
        if (this.zzanw != null && this.zzanw.length > 0) {
            int iZzb = iZzr;
            for (int i = 0; i < this.zzanw.length; i++) {
                zzir zzirVar = this.zzanw[i];
                if (zzirVar != null) {
                    iZzb += zzbfa.zzb(2, zzirVar);
                }
            }
            iZzr = iZzb;
        }
        if (this.zzanu != null) {
            iZzr += zzbfa.zzq(3, this.zzanu.intValue());
        }
        return this.zzant != null ? iZzr + zzbfa.zzb(4, this.zzant) : iZzr;
    }
}
