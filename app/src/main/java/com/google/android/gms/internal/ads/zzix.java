package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzix extends zzbfc<zzix> {
    private zzit zzapq = null;
    private Integer zzanu = null;
    private zziw zzapn = null;
    private zzis zzapo = null;

    public zzix() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzu, reason: merged with bridge method [inline-methods] */
    public final zzix zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    if (this.zzapq == null) {
                        this.zzapq = new zzit();
                    }
                    zzbezVar.zza(this.zzapq);
                    break;
                case 16:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzanu = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 26:
                    if (this.zzapn == null) {
                        this.zzapn = new zziw();
                    }
                    zzbezVar.zza(this.zzapn);
                    break;
                case 34:
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
        if (this.zzapq != null) {
            zzbfaVar.zza(1, this.zzapq);
        }
        if (this.zzanu != null) {
            zzbfaVar.zzm(2, this.zzanu.intValue());
        }
        if (this.zzapn != null) {
            zzbfaVar.zza(3, this.zzapn);
        }
        if (this.zzapo != null) {
            zzbfaVar.zza(4, this.zzapo);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzapq != null) {
            iZzr += zzbfa.zzb(1, this.zzapq);
        }
        if (this.zzanu != null) {
            iZzr += zzbfa.zzq(2, this.zzanu.intValue());
        }
        if (this.zzapn != null) {
            iZzr += zzbfa.zzb(3, this.zzapn);
        }
        return this.zzapo != null ? iZzr + zzbfa.zzb(4, this.zzapo) : iZzr;
    }
}
