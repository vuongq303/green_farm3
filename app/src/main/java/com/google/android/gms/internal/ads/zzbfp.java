package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzbfp extends zzbfc<zzbfp> {
    private zzbfq zzecz = null;
    public zzbfo[] zzeda = zzbfo.zzagt();
    private byte[] zzedb = null;
    private byte[] zzedc = null;
    private Integer zzedd = null;

    public zzbfp() {
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
                    if (this.zzecz == null) {
                        this.zzecz = new zzbfq();
                    }
                    zzbezVar.zza(this.zzecz);
                    break;
                case 18:
                    int iZzb = zzbfl.zzb(zzbezVar, 18);
                    int length = this.zzeda == null ? 0 : this.zzeda.length;
                    zzbfo[] zzbfoVarArr = new zzbfo[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzeda, 0, zzbfoVarArr, 0, length);
                    }
                    while (length < zzbfoVarArr.length - 1) {
                        zzbfoVarArr[length] = new zzbfo();
                        zzbezVar.zza(zzbfoVarArr[length]);
                        zzbezVar.zzabk();
                        length++;
                    }
                    zzbfoVarArr[length] = new zzbfo();
                    zzbezVar.zza(zzbfoVarArr[length]);
                    this.zzeda = zzbfoVarArr;
                    break;
                case 26:
                    this.zzedb = zzbezVar.readBytes();
                    break;
                case 34:
                    this.zzedc = zzbezVar.readBytes();
                    break;
                case 40:
                    this.zzedd = Integer.valueOf(zzbezVar.zzabn());
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
        if (this.zzecz != null) {
            zzbfaVar.zza(1, this.zzecz);
        }
        if (this.zzeda != null && this.zzeda.length > 0) {
            for (int i = 0; i < this.zzeda.length; i++) {
                zzbfo zzbfoVar = this.zzeda[i];
                if (zzbfoVar != null) {
                    zzbfaVar.zza(2, zzbfoVar);
                }
            }
        }
        if (this.zzedb != null) {
            zzbfaVar.zza(3, this.zzedb);
        }
        if (this.zzedc != null) {
            zzbfaVar.zza(4, this.zzedc);
        }
        if (this.zzedd != null) {
            zzbfaVar.zzm(5, this.zzedd.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzecz != null) {
            iZzr += zzbfa.zzb(1, this.zzecz);
        }
        if (this.zzeda != null && this.zzeda.length > 0) {
            int iZzb = iZzr;
            for (int i = 0; i < this.zzeda.length; i++) {
                zzbfo zzbfoVar = this.zzeda[i];
                if (zzbfoVar != null) {
                    iZzb += zzbfa.zzb(2, zzbfoVar);
                }
            }
            iZzr = iZzb;
        }
        if (this.zzedb != null) {
            iZzr += zzbfa.zzb(3, this.zzedb);
        }
        if (this.zzedc != null) {
            iZzr += zzbfa.zzb(4, this.zzedc);
        }
        return this.zzedd != null ? iZzr + zzbfa.zzq(5, this.zzedd.intValue()) : iZzr;
    }
}
