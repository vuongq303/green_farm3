package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzim extends zzbfc<zzim> {
    private Integer zzamf = null;
    private Integer zzanx = null;

    public zzim() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzo, reason: merged with bridge method [inline-methods] */
    public final zzim zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    int position = zzbezVar.getPosition();
                    try {
                        int iZzacc = zzbezVar.zzacc();
                        if (iZzacc < 0 || iZzacc > 2) {
                            throw new IllegalArgumentException(new StringBuilder(43).append(iZzacc).append(" is not a valid enum NetworkType").toString());
                        }
                        this.zzamf = Integer.valueOf(iZzacc);
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 16:
                    int position2 = zzbezVar.getPosition();
                    try {
                        int iZzacc2 = zzbezVar.zzacc();
                        if ((iZzacc2 < 0 || iZzacc2 > 2) && (iZzacc2 < 4 || iZzacc2 > 4)) {
                            throw new IllegalArgumentException(new StringBuilder(51).append(iZzacc2).append(" is not a valid enum CellularNetworkType").toString());
                        }
                        this.zzanx = Integer.valueOf(iZzacc2);
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
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
        if (this.zzamf != null) {
            zzbfaVar.zzm(1, this.zzamf.intValue());
        }
        if (this.zzanx != null) {
            zzbfaVar.zzm(2, this.zzanx.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzamf != null) {
            iZzr += zzbfa.zzq(1, this.zzamf.intValue());
        }
        return this.zzanx != null ? iZzr + zzbfa.zzq(2, this.zzanx.intValue()) : iZzr;
    }
}
