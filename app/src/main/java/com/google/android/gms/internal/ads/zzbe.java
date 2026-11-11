package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzbe extends zzbfc<zzbe> {
    public byte[] data = null;
    public byte[] zzgq = null;
    public byte[] zzgr = null;
    public byte[] zzgs = null;

    public zzbe() {
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
                    this.data = zzbezVar.readBytes();
                    break;
                case 18:
                    this.zzgq = zzbezVar.readBytes();
                    break;
                case 26:
                    this.zzgr = zzbezVar.readBytes();
                    break;
                case 34:
                    this.zzgs = zzbezVar.readBytes();
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
        if (this.data != null) {
            zzbfaVar.zza(1, this.data);
        }
        if (this.zzgq != null) {
            zzbfaVar.zza(2, this.zzgq);
        }
        if (this.zzgr != null) {
            zzbfaVar.zza(3, this.zzgr);
        }
        if (this.zzgs != null) {
            zzbfaVar.zza(4, this.zzgs);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.data != null) {
            iZzr += zzbfa.zzb(1, this.data);
        }
        if (this.zzgq != null) {
            iZzr += zzbfa.zzb(2, this.zzgq);
        }
        if (this.zzgr != null) {
            iZzr += zzbfa.zzb(3, this.zzgr);
        }
        return this.zzgs != null ? iZzr + zzbfa.zzb(4, this.zzgs) : iZzr;
    }
}
