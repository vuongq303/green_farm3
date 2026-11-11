package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zziq extends zzbfc<zziq> {
    private Integer zzaon = null;
    private Integer zzaoo = null;

    public zziq() {
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
                    this.zzaon = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 16:
                    this.zzaoo = Integer.valueOf(zzbezVar.zzacc());
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
        if (this.zzaon != null) {
            zzbfaVar.zzm(1, this.zzaon.intValue());
        }
        if (this.zzaoo != null) {
            zzbfaVar.zzm(2, this.zzaoo.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzaon != null) {
            iZzr += zzbfa.zzq(1, this.zzaon.intValue());
        }
        return this.zzaoo != null ? iZzr + zzbfa.zzq(2, this.zzaoo.intValue()) : iZzr;
    }
}
