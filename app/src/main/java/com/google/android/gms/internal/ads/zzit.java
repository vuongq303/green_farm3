package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzit extends zzbfc<zzit> {
    public Integer zzaot = null;
    public Integer zzaou = null;
    public Integer zzaov = null;

    public zzit() {
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
                    this.zzaot = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 16:
                    this.zzaou = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 24:
                    this.zzaov = Integer.valueOf(zzbezVar.zzacc());
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
        if (this.zzaot != null) {
            zzbfaVar.zzm(1, this.zzaot.intValue());
        }
        if (this.zzaou != null) {
            zzbfaVar.zzm(2, this.zzaou.intValue());
        }
        if (this.zzaov != null) {
            zzbfaVar.zzm(3, this.zzaov.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzaot != null) {
            iZzr += zzbfa.zzq(1, this.zzaot.intValue());
        }
        if (this.zzaou != null) {
            iZzr += zzbfa.zzq(2, this.zzaou.intValue());
        }
        return this.zzaov != null ? iZzr + zzbfa.zzq(3, this.zzaov.intValue()) : iZzr;
    }
}
