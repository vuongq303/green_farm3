package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzin extends zzbfc<zzin> {
    private Integer zzany = null;
    private zzis zzanz = null;

    public zzin() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzp, reason: merged with bridge method [inline-methods] */
    public final zzin zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzany = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 18:
                    if (this.zzanz == null) {
                        this.zzanz = new zzis();
                    }
                    zzbezVar.zza(this.zzanz);
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
        if (this.zzany != null) {
            zzbfaVar.zzm(1, this.zzany.intValue());
        }
        if (this.zzanz != null) {
            zzbfaVar.zza(2, this.zzanz);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzany != null) {
            iZzr += zzbfa.zzq(1, this.zzany.intValue());
        }
        return this.zzanz != null ? iZzr + zzbfa.zzb(2, this.zzanz) : iZzr;
    }
}
