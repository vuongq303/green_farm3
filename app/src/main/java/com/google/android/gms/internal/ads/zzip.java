package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzip extends zzbfc<zzip> {
    private Integer zzaol = null;
    private Integer zzaom = null;

    public zzip() {
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
                    this.zzaol = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 16:
                    this.zzaom = Integer.valueOf(zzbezVar.zzacc());
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
        if (this.zzaol != null) {
            zzbfaVar.zzm(1, this.zzaol.intValue());
        }
        if (this.zzaom != null) {
            zzbfaVar.zzm(2, this.zzaom.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzaol != null) {
            iZzr += zzbfa.zzq(1, this.zzaol.intValue());
        }
        return this.zzaom != null ? iZzr + zzbfa.zzq(2, this.zzaom.intValue()) : iZzr;
    }
}
