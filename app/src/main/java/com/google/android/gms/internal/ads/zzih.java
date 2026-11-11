package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzih extends zzbfc<zzih> {
    private Integer zzanc = null;
    private zzit zzand = null;
    private String zzane = null;
    private String zzanf = null;

    public zzih() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzj, reason: merged with bridge method [inline-methods] */
    public final zzih zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 40:
                    int position = zzbezVar.getPosition();
                    try {
                        int iZzacc = zzbezVar.zzacc();
                        if (iZzacc < 0 || iZzacc > 2) {
                            throw new IllegalArgumentException(new StringBuilder(40).append(iZzacc).append(" is not a valid enum Platform").toString());
                        }
                        this.zzanc = Integer.valueOf(iZzacc);
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 50:
                    if (this.zzand == null) {
                        this.zzand = new zzit();
                    }
                    zzbezVar.zza(this.zzand);
                    break;
                case 58:
                    this.zzane = zzbezVar.readString();
                    break;
                case 66:
                    this.zzanf = zzbezVar.readString();
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
        if (this.zzanc != null) {
            zzbfaVar.zzm(5, this.zzanc.intValue());
        }
        if (this.zzand != null) {
            zzbfaVar.zza(6, this.zzand);
        }
        if (this.zzane != null) {
            zzbfaVar.zzf(7, this.zzane);
        }
        if (this.zzanf != null) {
            zzbfaVar.zzf(8, this.zzanf);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzanc != null) {
            iZzr += zzbfa.zzq(5, this.zzanc.intValue());
        }
        if (this.zzand != null) {
            iZzr += zzbfa.zzb(6, this.zzand);
        }
        if (this.zzane != null) {
            iZzr += zzbfa.zzg(7, this.zzane);
        }
        return this.zzanf != null ? iZzr + zzbfa.zzg(8, this.zzanf) : iZzr;
    }
}
