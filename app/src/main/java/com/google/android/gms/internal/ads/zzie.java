package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzie extends zzbfc<zzie> {
    private String zzamj = null;
    private zzic[] zzamk = zzic.zzhr();
    private Integer zzaml = null;
    private Integer zzamm = null;
    private Integer zzamn = null;

    public zzie() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzh, reason: merged with bridge method [inline-methods] */
    public final zzie zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.zzamj = zzbezVar.readString();
                    break;
                case 18:
                    int iZzb = zzbfl.zzb(zzbezVar, 18);
                    int length = this.zzamk == null ? 0 : this.zzamk.length;
                    zzic[] zzicVarArr = new zzic[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzamk, 0, zzicVarArr, 0, length);
                    }
                    while (length < zzicVarArr.length - 1) {
                        zzicVarArr[length] = new zzic();
                        zzbezVar.zza(zzicVarArr[length]);
                        zzbezVar.zzabk();
                        length++;
                    }
                    zzicVarArr[length] = new zzic();
                    zzbezVar.zza(zzicVarArr[length]);
                    this.zzamk = zzicVarArr;
                    break;
                case 24:
                    int position = zzbezVar.getPosition();
                    try {
                        this.zzaml = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 32:
                    int position2 = zzbezVar.getPosition();
                    try {
                        this.zzamm = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 40:
                    int position3 = zzbezVar.getPosition();
                    try {
                        this.zzamn = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
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
        if (this.zzamj != null) {
            zzbfaVar.zzf(1, this.zzamj);
        }
        if (this.zzamk != null && this.zzamk.length > 0) {
            for (int i = 0; i < this.zzamk.length; i++) {
                zzic zzicVar = this.zzamk[i];
                if (zzicVar != null) {
                    zzbfaVar.zza(2, zzicVar);
                }
            }
        }
        if (this.zzaml != null) {
            zzbfaVar.zzm(3, this.zzaml.intValue());
        }
        if (this.zzamm != null) {
            zzbfaVar.zzm(4, this.zzamm.intValue());
        }
        if (this.zzamn != null) {
            zzbfaVar.zzm(5, this.zzamn.intValue());
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzamj != null) {
            iZzr += zzbfa.zzg(1, this.zzamj);
        }
        if (this.zzamk != null && this.zzamk.length > 0) {
            int iZzb = iZzr;
            for (int i = 0; i < this.zzamk.length; i++) {
                zzic zzicVar = this.zzamk[i];
                if (zzicVar != null) {
                    iZzb += zzbfa.zzb(2, zzicVar);
                }
            }
            iZzr = iZzb;
        }
        if (this.zzaml != null) {
            iZzr += zzbfa.zzq(3, this.zzaml.intValue());
        }
        if (this.zzamm != null) {
            iZzr += zzbfa.zzq(4, this.zzamm.intValue());
        }
        return this.zzamn != null ? iZzr + zzbfa.zzq(5, this.zzamn.intValue()) : iZzr;
    }
}
