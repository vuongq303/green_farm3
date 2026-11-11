package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzif extends zzbfc<zzif> {
    private Integer zzamo = null;
    private zzis zzamp = null;
    private zzis zzamq = null;
    private zzis zzamr = null;
    private zzis[] zzams = zzis.zzht();
    private Integer zzamt = null;

    public zzif() {
        this.zzebk = null;
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
                    this.zzamo = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 18:
                    if (this.zzamp == null) {
                        this.zzamp = new zzis();
                    }
                    zzbezVar.zza(this.zzamp);
                    break;
                case 26:
                    if (this.zzamq == null) {
                        this.zzamq = new zzis();
                    }
                    zzbezVar.zza(this.zzamq);
                    break;
                case 34:
                    if (this.zzamr == null) {
                        this.zzamr = new zzis();
                    }
                    zzbezVar.zza(this.zzamr);
                    break;
                case 42:
                    int iZzb = zzbfl.zzb(zzbezVar, 42);
                    int length = this.zzams == null ? 0 : this.zzams.length;
                    zzis[] zzisVarArr = new zzis[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzams, 0, zzisVarArr, 0, length);
                    }
                    while (length < zzisVarArr.length - 1) {
                        zzisVarArr[length] = new zzis();
                        zzbezVar.zza(zzisVarArr[length]);
                        zzbezVar.zzabk();
                        length++;
                    }
                    zzisVarArr[length] = new zzis();
                    zzbezVar.zza(zzisVarArr[length]);
                    this.zzams = zzisVarArr;
                    break;
                case 48:
                    this.zzamt = Integer.valueOf(zzbezVar.zzacc());
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
        if (this.zzamo != null) {
            zzbfaVar.zzm(1, this.zzamo.intValue());
        }
        if (this.zzamp != null) {
            zzbfaVar.zza(2, this.zzamp);
        }
        if (this.zzamq != null) {
            zzbfaVar.zza(3, this.zzamq);
        }
        if (this.zzamr != null) {
            zzbfaVar.zza(4, this.zzamr);
        }
        if (this.zzams != null && this.zzams.length > 0) {
            for (int i = 0; i < this.zzams.length; i++) {
                zzis zzisVar = this.zzams[i];
                if (zzisVar != null) {
                    zzbfaVar.zza(5, zzisVar);
                }
            }
        }
        if (this.zzamt != null) {
            zzbfaVar.zzm(6, this.zzamt.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzamo != null) {
            iZzr += zzbfa.zzq(1, this.zzamo.intValue());
        }
        if (this.zzamp != null) {
            iZzr += zzbfa.zzb(2, this.zzamp);
        }
        if (this.zzamq != null) {
            iZzr += zzbfa.zzb(3, this.zzamq);
        }
        if (this.zzamr != null) {
            iZzr += zzbfa.zzb(4, this.zzamr);
        }
        if (this.zzams != null && this.zzams.length > 0) {
            int iZzb = iZzr;
            for (int i = 0; i < this.zzams.length; i++) {
                zzis zzisVar = this.zzams[i];
                if (zzisVar != null) {
                    iZzb += zzbfa.zzb(5, zzisVar);
                }
            }
            iZzr = iZzb;
        }
        return this.zzamt != null ? iZzr + zzbfa.zzq(6, this.zzamt.intValue()) : iZzr;
    }
}
