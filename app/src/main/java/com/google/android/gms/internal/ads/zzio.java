package com.google.android.gms.internal.ads;

import com.gameloft.adsmanager.JavaUtils;

/* JADX INFO: loaded from: classes.dex */
public final class zzio extends zzbfc<zzio> {
    private Integer zzaoa = null;
    private Integer zzaob = null;
    private Integer zzaoc = null;
    private Integer zzaod = null;
    private Integer zzaoe = null;
    private Integer zzaof = null;
    private Integer zzaog = null;
    private Integer zzaoh = null;
    private Integer zzaoi = null;
    private Integer zzaoj = null;
    private zzip zzaok = null;

    public zzio() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzq, reason: merged with bridge method [inline-methods] */
    public final zzio zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzaoa = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 16:
                    int position2 = zzbezVar.getPosition();
                    try {
                        this.zzaob = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 24:
                    this.zzaoc = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 32:
                    this.zzaod = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 40:
                    this.zzaoe = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 48:
                    this.zzaof = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 56:
                    this.zzaog = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 64:
                    this.zzaoh = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 72:
                    this.zzaoi = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 80:
                    this.zzaoj = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case JavaUtils.Constants.TABLET_HEIGHT_DP /* 90 */:
                    if (this.zzaok == null) {
                        this.zzaok = new zzip();
                    }
                    zzbezVar.zza(this.zzaok);
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
        if (this.zzaoa != null) {
            zzbfaVar.zzm(1, this.zzaoa.intValue());
        }
        if (this.zzaob != null) {
            zzbfaVar.zzm(2, this.zzaob.intValue());
        }
        if (this.zzaoc != null) {
            zzbfaVar.zzm(3, this.zzaoc.intValue());
        }
        if (this.zzaod != null) {
            zzbfaVar.zzm(4, this.zzaod.intValue());
        }
        if (this.zzaoe != null) {
            zzbfaVar.zzm(5, this.zzaoe.intValue());
        }
        if (this.zzaof != null) {
            zzbfaVar.zzm(6, this.zzaof.intValue());
        }
        if (this.zzaog != null) {
            zzbfaVar.zzm(7, this.zzaog.intValue());
        }
        if (this.zzaoh != null) {
            zzbfaVar.zzm(8, this.zzaoh.intValue());
        }
        if (this.zzaoi != null) {
            zzbfaVar.zzm(9, this.zzaoi.intValue());
        }
        if (this.zzaoj != null) {
            zzbfaVar.zzm(10, this.zzaoj.intValue());
        }
        if (this.zzaok != null) {
            zzbfaVar.zza(11, this.zzaok);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzaoa != null) {
            iZzr += zzbfa.zzq(1, this.zzaoa.intValue());
        }
        if (this.zzaob != null) {
            iZzr += zzbfa.zzq(2, this.zzaob.intValue());
        }
        if (this.zzaoc != null) {
            iZzr += zzbfa.zzq(3, this.zzaoc.intValue());
        }
        if (this.zzaod != null) {
            iZzr += zzbfa.zzq(4, this.zzaod.intValue());
        }
        if (this.zzaoe != null) {
            iZzr += zzbfa.zzq(5, this.zzaoe.intValue());
        }
        if (this.zzaof != null) {
            iZzr += zzbfa.zzq(6, this.zzaof.intValue());
        }
        if (this.zzaog != null) {
            iZzr += zzbfa.zzq(7, this.zzaog.intValue());
        }
        if (this.zzaoh != null) {
            iZzr += zzbfa.zzq(8, this.zzaoh.intValue());
        }
        if (this.zzaoi != null) {
            iZzr += zzbfa.zzq(9, this.zzaoi.intValue());
        }
        if (this.zzaoj != null) {
            iZzr += zzbfa.zzq(10, this.zzaoj.intValue());
        }
        return this.zzaok != null ? iZzr + zzbfa.zzb(11, this.zzaok) : iZzr;
    }
}
