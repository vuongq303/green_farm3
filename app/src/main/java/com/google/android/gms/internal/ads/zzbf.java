package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzbf extends zzbfc<zzbf> {
    public Long zzgl = null;
    private String zzgt = null;
    private byte[] zzgu = null;

    public zzbf() {
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
                case 26:
                    this.zzgt = zzbezVar.readString();
                    break;
                case 34:
                    this.zzgu = zzbezVar.readBytes();
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
        if (this.zzgt != null) {
            zzbfaVar.zzf(3, this.zzgt);
        }
        if (this.zzgu != null) {
            zzbfaVar.zza(4, this.zzgu);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzgl != null) {
            iZzr += zzbfa.zzd(1, this.zzgl.longValue());
        }
        if (this.zzgt != null) {
            iZzr += zzbfa.zzg(3, this.zzgt);
        }
        return this.zzgu != null ? iZzr + zzbfa.zzb(4, this.zzgu) : iZzr;
    }
}
