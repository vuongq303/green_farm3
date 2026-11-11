package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzig extends zzbfc<zzig> {
    public String zzamu = null;
    private zzis zzamv = null;
    private Integer zzamw = null;
    public zzit zzamx = null;
    private Integer zzamy = null;
    private Integer zzamz = null;
    private Integer zzana = null;
    private Integer zzanb = null;

    public zzig() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzi, reason: merged with bridge method [inline-methods] */
    public final zzig zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.zzamu = zzbezVar.readString();
                    break;
                case 18:
                    if (this.zzamv == null) {
                        this.zzamv = new zzis();
                    }
                    zzbezVar.zza(this.zzamv);
                    break;
                case 24:
                    this.zzamw = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 34:
                    if (this.zzamx == null) {
                        this.zzamx = new zzit();
                    }
                    zzbezVar.zza(this.zzamx);
                    break;
                case 40:
                    this.zzamy = Integer.valueOf(zzbezVar.zzacc());
                    break;
                case 48:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzamz = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 56:
                    int position2 = zzbezVar.getPosition();
                    try {
                        this.zzana = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 64:
                    int position3 = zzbezVar.getPosition();
                    try {
                        this.zzanb = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e3) {
                        zzbezVar.zzdc(position3);
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
        if (this.zzamu != null) {
            zzbfaVar.zzf(1, this.zzamu);
        }
        if (this.zzamv != null) {
            zzbfaVar.zza(2, this.zzamv);
        }
        if (this.zzamw != null) {
            zzbfaVar.zzm(3, this.zzamw.intValue());
        }
        if (this.zzamx != null) {
            zzbfaVar.zza(4, this.zzamx);
        }
        if (this.zzamy != null) {
            zzbfaVar.zzm(5, this.zzamy.intValue());
        }
        if (this.zzamz != null) {
            zzbfaVar.zzm(6, this.zzamz.intValue());
        }
        if (this.zzana != null) {
            zzbfaVar.zzm(7, this.zzana.intValue());
        }
        if (this.zzanb != null) {
            zzbfaVar.zzm(8, this.zzanb.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzamu != null) {
            iZzr += zzbfa.zzg(1, this.zzamu);
        }
        if (this.zzamv != null) {
            iZzr += zzbfa.zzb(2, this.zzamv);
        }
        if (this.zzamw != null) {
            iZzr += zzbfa.zzq(3, this.zzamw.intValue());
        }
        if (this.zzamx != null) {
            iZzr += zzbfa.zzb(4, this.zzamx);
        }
        if (this.zzamy != null) {
            iZzr += zzbfa.zzq(5, this.zzamy.intValue());
        }
        if (this.zzamz != null) {
            iZzr += zzbfa.zzq(6, this.zzamz.intValue());
        }
        if (this.zzana != null) {
            iZzr += zzbfa.zzq(7, this.zzana.intValue());
        }
        return this.zzanb != null ? iZzr + zzbfa.zzq(8, this.zzanb.intValue()) : iZzr;
    }
}
