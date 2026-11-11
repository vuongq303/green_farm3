package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzbfv extends zzbfc<zzbfv> {
    public String zzedv = null;
    public Long zzedw = null;
    public Boolean zzedx = null;

    public zzbfv() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    @Override // com.google.android.gms.internal.ads.zzbfi
    public final /* synthetic */ zzbfi zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.zzedv = zzbezVar.readString();
                    break;
                case 16:
                    this.zzedw = Long.valueOf(zzbezVar.zzabm());
                    break;
                case 24:
                    this.zzedx = Boolean.valueOf(zzbezVar.zzabq());
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
        if (this.zzedv != null) {
            zzbfaVar.zzf(1, this.zzedv);
        }
        if (this.zzedw != null) {
            zzbfaVar.zzi(2, this.zzedw.longValue());
        }
        if (this.zzedx != null) {
            zzbfaVar.zzf(3, this.zzedx.booleanValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzedv != null) {
            iZzr += zzbfa.zzg(1, this.zzedv);
        }
        if (this.zzedw != null) {
            iZzr += zzbfa.zzd(2, this.zzedw.longValue());
        }
        if (this.zzedx == null) {
            return iZzr;
        }
        this.zzedx.booleanValue();
        return iZzr + zzbfa.zzcd(3) + 1;
    }
}
