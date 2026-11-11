package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzis extends zzbfc<zzis> {
    private static volatile zzis[] zzaoq;
    private Integer zzaor = null;
    private Integer zzaos = null;

    public zzis() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    public static zzis[] zzht() {
        if (zzaoq == null) {
            synchronized (zzbfg.zzebs) {
                if (zzaoq == null) {
                    zzaoq = new zzis[0];
                }
            }
        }
        return zzaoq;
    }

    @Override // com.google.android.gms.internal.ads.zzbfi
    public final /* synthetic */ zzbfi zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    this.zzaor = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 16:
                    this.zzaos = Integer.valueOf(zzbezVar.zzacc());
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
        if (this.zzaor != null) {
            zzbfaVar.zzm(1, this.zzaor.intValue());
        }
        if (this.zzaos != null) {
            zzbfaVar.zzm(2, this.zzaos.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzaor != null) {
            iZzr += zzbfa.zzq(1, this.zzaor.intValue());
        }
        return this.zzaos != null ? iZzr + zzbfa.zzq(2, this.zzaos.intValue()) : iZzr;
    }
}
