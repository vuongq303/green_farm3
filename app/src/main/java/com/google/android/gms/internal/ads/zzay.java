package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzay extends zzbfc<zzay> {
    public String zzcx;
    private String zzcy;
    private String zzcz;
    private String zzda;
    private String zzdb;

    @Override // com.google.android.gms.internal.ads.zzbfi
    public final /* synthetic */ zzbfi zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.zzcx = zzbezVar.readString();
                    break;
                case 18:
                    this.zzcy = zzbezVar.readString();
                    break;
                case 26:
                    this.zzcz = zzbezVar.readString();
                    break;
                case 34:
                    this.zzda = zzbezVar.readString();
                    break;
                case 42:
                    this.zzdb = zzbezVar.readString();
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
        if (this.zzcx != null) {
            zzbfaVar.zzf(1, this.zzcx);
        }
        if (this.zzcy != null) {
            zzbfaVar.zzf(2, this.zzcy);
        }
        if (this.zzcz != null) {
            zzbfaVar.zzf(3, this.zzcz);
        }
        if (this.zzda != null) {
            zzbfaVar.zzf(4, this.zzda);
        }
        if (this.zzdb != null) {
            zzbfaVar.zzf(5, this.zzdb);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzcx != null) {
            iZzr += zzbfa.zzg(1, this.zzcx);
        }
        if (this.zzcy != null) {
            iZzr += zzbfa.zzg(2, this.zzcy);
        }
        if (this.zzcz != null) {
            iZzr += zzbfa.zzg(3, this.zzcz);
        }
        if (this.zzda != null) {
            iZzr += zzbfa.zzg(4, this.zzda);
        }
        return this.zzdb != null ? iZzr + zzbfa.zzg(5, this.zzdb) : iZzr;
    }
}
