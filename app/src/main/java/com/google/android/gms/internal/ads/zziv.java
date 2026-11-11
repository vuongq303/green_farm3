package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zziv extends zzbfc<zziv> {
    private Integer zzanu = null;
    private zziw zzapn = null;
    private zzis zzapo = null;

    public zziv() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzs, reason: merged with bridge method [inline-methods] */
    public final zziv zza(zzbez zzbezVar) throws zzbfh {
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
                case 26:
                    if (this.zzapo == null) {
                        this.zzapo = new zzis();
                    }
                    zzbezVar.zza(this.zzapo);
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
        if (this.zzapo != null) {
            zzbfaVar.zza(3, this.zzapo);
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
        return this.zzapo != null ? iZzr + zzbfa.zzb(3, this.zzapo) : iZzr;
    }
}
