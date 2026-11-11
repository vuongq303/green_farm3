package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzbfq extends zzbfc<zzbfq> {
    private byte[] zzede = null;
    private byte[] zzedf = null;
    private byte[] zzedg = null;

    public zzbfq() {
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
                    this.zzede = zzbezVar.readBytes();
                    break;
                case 18:
                    this.zzedf = zzbezVar.readBytes();
                    break;
                case 26:
                    this.zzedg = zzbezVar.readBytes();
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
        if (this.zzede != null) {
            zzbfaVar.zza(1, this.zzede);
        }
        if (this.zzedf != null) {
            zzbfaVar.zza(2, this.zzedf);
        }
        if (this.zzedg != null) {
            zzbfaVar.zza(3, this.zzedg);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzede != null) {
            iZzr += zzbfa.zzb(1, this.zzede);
        }
        if (this.zzedf != null) {
            iZzr += zzbfa.zzb(2, this.zzedf);
        }
        return this.zzedg != null ? iZzr + zzbfa.zzb(3, this.zzedg) : iZzr;
    }
}
