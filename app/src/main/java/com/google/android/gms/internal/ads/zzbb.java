package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzbb extends zzbfc<zzbb> {
    private static volatile zzbb[] zzfo;
    public Integer zzft;
    public Integer zzfx;
    public Long zzdo = null;
    public Long zzdp = null;
    public Long zzfp = null;
    public Long zzfq = null;
    public Long zzfr = null;
    public Long zzfs = null;
    public Long zzfu = null;
    public Long zzfv = null;
    public Long zzfw = null;
    public Long zzfy = null;
    public Long zzfz = null;
    public Long zzga = null;
    public Long zzgb = null;
    public Long zzgc = null;
    public Long zzgd = null;
    public Long zzge = null;
    public Long zzgf = null;
    private Long zzgg = null;
    private Long zzgh = null;

    public zzbb() {
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public final zzbb zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    this.zzdo = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 16:
                    this.zzdp = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 24:
                    this.zzfp = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 32:
                    this.zzfq = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 40:
                    this.zzfr = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 48:
                    this.zzfs = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 56:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzft = Integer.valueOf(zzaz.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 64:
                    this.zzfu = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 72:
                    this.zzfv = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 80:
                    this.zzfw = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 88:
                    int position2 = zzbezVar.getPosition();
                    try {
                        this.zzfx = Integer.valueOf(zzaz.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 96:
                    this.zzfy = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 104:
                    this.zzfz = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 112:
                    this.zzga = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 120:
                    this.zzgb = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 128:
                    this.zzgc = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 136:
                    this.zzgd = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 144:
                    this.zzge = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 152:
                    this.zzgf = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 160:
                    this.zzgg = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 168:
                    this.zzgh = Long.valueOf(zzbezVar.zzacd());
                    break;
                default:
                    if (!super.zza(zzbezVar, iZzabk)) {
                    }
                    break;
            }
        }
        return this;
    }

    public static zzbb[] zzs() {
        if (zzfo == null) {
            synchronized (zzbfg.zzebs) {
                if (zzfo == null) {
                    zzfo = new zzbb[0];
                }
            }
        }
        return zzfo;
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    public final void zza(zzbfa zzbfaVar) throws zzbfb {
        if (this.zzdo != null) {
            zzbfaVar.zzi(1, this.zzdo.longValue());
        }
        if (this.zzdp != null) {
            zzbfaVar.zzi(2, this.zzdp.longValue());
        }
        if (this.zzfp != null) {
            zzbfaVar.zzi(3, this.zzfp.longValue());
        }
        if (this.zzfq != null) {
            zzbfaVar.zzi(4, this.zzfq.longValue());
        }
        if (this.zzfr != null) {
            zzbfaVar.zzi(5, this.zzfr.longValue());
        }
        if (this.zzfs != null) {
            zzbfaVar.zzi(6, this.zzfs.longValue());
        }
        if (this.zzft != null) {
            zzbfaVar.zzm(7, this.zzft.intValue());
        }
        if (this.zzfu != null) {
            zzbfaVar.zzi(8, this.zzfu.longValue());
        }
        if (this.zzfv != null) {
            zzbfaVar.zzi(9, this.zzfv.longValue());
        }
        if (this.zzfw != null) {
            zzbfaVar.zzi(10, this.zzfw.longValue());
        }
        if (this.zzfx != null) {
            zzbfaVar.zzm(11, this.zzfx.intValue());
        }
        if (this.zzfy != null) {
            zzbfaVar.zzi(12, this.zzfy.longValue());
        }
        if (this.zzfz != null) {
            zzbfaVar.zzi(13, this.zzfz.longValue());
        }
        if (this.zzga != null) {
            zzbfaVar.zzi(14, this.zzga.longValue());
        }
        if (this.zzgb != null) {
            zzbfaVar.zzi(15, this.zzgb.longValue());
        }
        if (this.zzgc != null) {
            zzbfaVar.zzi(16, this.zzgc.longValue());
        }
        if (this.zzgd != null) {
            zzbfaVar.zzi(17, this.zzgd.longValue());
        }
        if (this.zzge != null) {
            zzbfaVar.zzi(18, this.zzge.longValue());
        }
        if (this.zzgf != null) {
            zzbfaVar.zzi(19, this.zzgf.longValue());
        }
        if (this.zzgg != null) {
            zzbfaVar.zzi(20, this.zzgg.longValue());
        }
        if (this.zzgh != null) {
            zzbfaVar.zzi(21, this.zzgh.longValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzdo != null) {
            iZzr += zzbfa.zzd(1, this.zzdo.longValue());
        }
        if (this.zzdp != null) {
            iZzr += zzbfa.zzd(2, this.zzdp.longValue());
        }
        if (this.zzfp != null) {
            iZzr += zzbfa.zzd(3, this.zzfp.longValue());
        }
        if (this.zzfq != null) {
            iZzr += zzbfa.zzd(4, this.zzfq.longValue());
        }
        if (this.zzfr != null) {
            iZzr += zzbfa.zzd(5, this.zzfr.longValue());
        }
        if (this.zzfs != null) {
            iZzr += zzbfa.zzd(6, this.zzfs.longValue());
        }
        if (this.zzft != null) {
            iZzr += zzbfa.zzq(7, this.zzft.intValue());
        }
        if (this.zzfu != null) {
            iZzr += zzbfa.zzd(8, this.zzfu.longValue());
        }
        if (this.zzfv != null) {
            iZzr += zzbfa.zzd(9, this.zzfv.longValue());
        }
        if (this.zzfw != null) {
            iZzr += zzbfa.zzd(10, this.zzfw.longValue());
        }
        if (this.zzfx != null) {
            iZzr += zzbfa.zzq(11, this.zzfx.intValue());
        }
        if (this.zzfy != null) {
            iZzr += zzbfa.zzd(12, this.zzfy.longValue());
        }
        if (this.zzfz != null) {
            iZzr += zzbfa.zzd(13, this.zzfz.longValue());
        }
        if (this.zzga != null) {
            iZzr += zzbfa.zzd(14, this.zzga.longValue());
        }
        if (this.zzgb != null) {
            iZzr += zzbfa.zzd(15, this.zzgb.longValue());
        }
        if (this.zzgc != null) {
            iZzr += zzbfa.zzd(16, this.zzgc.longValue());
        }
        if (this.zzgd != null) {
            iZzr += zzbfa.zzd(17, this.zzgd.longValue());
        }
        if (this.zzge != null) {
            iZzr += zzbfa.zzd(18, this.zzge.longValue());
        }
        if (this.zzgf != null) {
            iZzr += zzbfa.zzd(19, this.zzgf.longValue());
        }
        if (this.zzgg != null) {
            iZzr += zzbfa.zzd(20, this.zzgg.longValue());
        }
        return this.zzgh != null ? iZzr + zzbfa.zzd(21, this.zzgh.longValue()) : iZzr;
    }
}
