package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zziw extends zzbfc<zziw> {
    private Integer zzapp = null;

    public zziw() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzt, reason: merged with bridge method [inline-methods] */
    public final zziw zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    int position = zzbezVar.getPosition();
                    try {
                        int iZzacc = zzbezVar.zzacc();
                        if (iZzacc < 0 || iZzacc > 3) {
                            throw new IllegalArgumentException(new StringBuilder(46).append(iZzacc).append(" is not a valid enum VideoErrorCode").toString());
                        }
                        this.zzapp = Integer.valueOf(iZzacc);
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
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
        if (this.zzapp != null) {
            zzbfaVar.zzm(1, this.zzapp.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        return this.zzapp != null ? iZzr + zzbfa.zzq(1, this.zzapp.intValue()) : iZzr;
    }
}
