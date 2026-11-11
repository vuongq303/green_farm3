package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzbft extends zzbfc<zzbft> {
    public Integer zzamf = null;
    public String mimeType = null;
    public byte[] zzedl = null;

    public zzbft() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzab, reason: merged with bridge method [inline-methods] */
    public final zzbft zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    int position = zzbezVar.getPosition();
                    try {
                        int iZzabn = zzbezVar.zzabn();
                        if (iZzabn < 0 || iZzabn > 1) {
                            throw new IllegalArgumentException(new StringBuilder(36).append(iZzabn).append(" is not a valid enum Type").toString());
                        }
                        this.zzamf = Integer.valueOf(iZzabn);
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 18:
                    this.mimeType = zzbezVar.readString();
                    break;
                case 26:
                    this.zzedl = zzbezVar.readBytes();
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
        if (this.mimeType != null) {
            zzbfaVar.zzf(2, this.mimeType);
        }
        if (this.zzedl != null) {
            zzbfaVar.zza(3, this.zzedl);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzamf != null) {
            iZzr += zzbfa.zzq(1, this.zzamf.intValue());
        }
        if (this.mimeType != null) {
            iZzr += zzbfa.zzg(2, this.mimeType);
        }
        return this.zzedl != null ? iZzr + zzbfa.zzb(3, this.zzedl) : iZzr;
    }
}
