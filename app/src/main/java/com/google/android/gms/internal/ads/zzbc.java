package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzbc extends zzbfc<zzbc> {
    private Long zzeq = null;
    private Long zzer = null;
    public Long zzgi = null;
    public Long zzgj = null;
    public Long zzgk = null;

    public zzbc() {
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
                    this.zzeq = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 16:
                    this.zzer = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 24:
                    this.zzgi = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 32:
                    this.zzgj = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 40:
                    this.zzgk = Long.valueOf(zzbezVar.zzacd());
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
        if (this.zzeq != null) {
            zzbfaVar.zzi(1, this.zzeq.longValue());
        }
        if (this.zzer != null) {
            zzbfaVar.zzi(2, this.zzer.longValue());
        }
        if (this.zzgi != null) {
            zzbfaVar.zzi(3, this.zzgi.longValue());
        }
        if (this.zzgj != null) {
            zzbfaVar.zzi(4, this.zzgj.longValue());
        }
        if (this.zzgk != null) {
            zzbfaVar.zzi(5, this.zzgk.longValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzeq != null) {
            iZzr += zzbfa.zzd(1, this.zzeq.longValue());
        }
        if (this.zzer != null) {
            iZzr += zzbfa.zzd(2, this.zzer.longValue());
        }
        if (this.zzgi != null) {
            iZzr += zzbfa.zzd(3, this.zzgi.longValue());
        }
        if (this.zzgj != null) {
            iZzr += zzbfa.zzd(4, this.zzgj.longValue());
        }
        return this.zzgk != null ? iZzr + zzbfa.zzd(5, this.zzgk.longValue()) : iZzr;
    }
}
