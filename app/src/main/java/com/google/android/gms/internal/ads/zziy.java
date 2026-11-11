package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zziy extends zzbfc<zziy> {
    private Integer zzanu = null;
    private zziw zzapn = null;
    private Integer zzape = null;
    private Integer zzapf = null;
    private Integer zzapr = null;

    public zziy() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzv, reason: merged with bridge method [inline-methods] */
    public final zziy zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzanu = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 18:
                    if (this.zzapn == null) {
                        this.zzapn = new zziw();
                    }
                    zzbezVar.zza(this.zzapn);
                    break;
                case 24:
                    this.zzape = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 32:
                    this.zzapf = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 40:
                    this.zzapr = Integer.valueOf(zzbezVar.zzacc());
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
        if (this.zzanu != null) {
            zzbfaVar.zzm(1, this.zzanu.intValue());
        }
        if (this.zzapn != null) {
            zzbfaVar.zza(2, this.zzapn);
        }
        if (this.zzape != null) {
            zzbfaVar.zzm(3, this.zzape.intValue());
        }
        if (this.zzapf != null) {
            zzbfaVar.zzm(4, this.zzapf.intValue());
        }
        if (this.zzapr != null) {
            zzbfaVar.zzm(5, this.zzapr.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzanu != null) {
            iZzr += zzbfa.zzq(1, this.zzanu.intValue());
        }
        if (this.zzapn != null) {
            iZzr += zzbfa.zzb(2, this.zzapn);
        }
        if (this.zzape != null) {
            iZzr += zzbfa.zzq(3, this.zzape.intValue());
        }
        if (this.zzapf != null) {
            iZzr += zzbfa.zzq(4, this.zzapf.intValue());
        }
        return this.zzapr != null ? iZzr + zzbfa.zzq(5, this.zzapr.intValue()) : iZzr;
    }
}
