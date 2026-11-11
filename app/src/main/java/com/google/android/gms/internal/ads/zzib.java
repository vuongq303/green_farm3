package com.google.android.gms.internal.ads;

import com.gameloft.adsmanager.JavaUtils;

/* JADX INFO: loaded from: classes.dex */
public final class zzib extends zzbfc<zzib> {
    public Integer zzalt = null;
    private Integer zzalu = null;
    private zzid zzalv = null;
    public zzie zzalw = null;
    private zzic[] zzalx = zzic.zzhr();
    private zzif zzaly = null;
    private zzio zzalz = null;
    private zzin zzama = null;
    private zzik zzamb = null;
    private zzil zzamc = null;
    private zziu[] zzamd = zziu.zzhu();

    public zzib() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final zzib zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 56:
                    int position = zzbezVar.getPosition();
                    try {
                        int iZzacc = zzbezVar.zzacc();
                        if (iZzacc < 0 || iZzacc > 9) {
                            throw new IllegalArgumentException(new StringBuilder(43).append(iZzacc).append(" is not a valid enum AdInitiater").toString());
                        }
                        this.zzalt = Integer.valueOf(iZzacc);
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 64:
                    int position2 = zzbezVar.getPosition();
                    try {
                        this.zzalu = Integer.valueOf(zzia.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 74:
                    if (this.zzalv == null) {
                        this.zzalv = new zzid();
                    }
                    zzbezVar.zza(this.zzalv);
                    break;
                case 82:
                    if (this.zzalw == null) {
                        this.zzalw = new zzie();
                    }
                    zzbezVar.zza(this.zzalw);
                    break;
                case JavaUtils.Constants.TABLET_HEIGHT_DP /* 90 */:
                    int iZzb = zzbfl.zzb(zzbezVar, 90);
                    int length = this.zzalx == null ? 0 : this.zzalx.length;
                    zzic[] zzicVarArr = new zzic[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzalx, 0, zzicVarArr, 0, length);
                    }
                    while (length < zzicVarArr.length - 1) {
                        zzicVarArr[length] = new zzic();
                        zzbezVar.zza(zzicVarArr[length]);
                        zzbezVar.zzabk();
                        length++;
                    }
                    zzicVarArr[length] = new zzic();
                    zzbezVar.zza(zzicVarArr[length]);
                    this.zzalx = zzicVarArr;
                    break;
                case 98:
                    if (this.zzaly == null) {
                        this.zzaly = new zzif();
                    }
                    zzbezVar.zza(this.zzaly);
                    break;
                case 106:
                    if (this.zzalz == null) {
                        this.zzalz = new zzio();
                    }
                    zzbezVar.zza(this.zzalz);
                    break;
                case 114:
                    if (this.zzama == null) {
                        this.zzama = new zzin();
                    }
                    zzbezVar.zza(this.zzama);
                    break;
                case 122:
                    if (this.zzamb == null) {
                        this.zzamb = new zzik();
                    }
                    zzbezVar.zza(this.zzamb);
                    break;
                case 130:
                    if (this.zzamc == null) {
                        this.zzamc = new zzil();
                    }
                    zzbezVar.zza(this.zzamc);
                    break;
                case 138:
                    int iZzb2 = zzbfl.zzb(zzbezVar, 138);
                    int length2 = this.zzamd == null ? 0 : this.zzamd.length;
                    zziu[] zziuVarArr = new zziu[iZzb2 + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzamd, 0, zziuVarArr, 0, length2);
                    }
                    while (length2 < zziuVarArr.length - 1) {
                        zziuVarArr[length2] = new zziu();
                        zzbezVar.zza(zziuVarArr[length2]);
                        zzbezVar.zzabk();
                        length2++;
                    }
                    zziuVarArr[length2] = new zziu();
                    zzbezVar.zza(zziuVarArr[length2]);
                    this.zzamd = zziuVarArr;
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
        if (this.zzalt != null) {
            zzbfaVar.zzm(7, this.zzalt.intValue());
        }
        if (this.zzalu != null) {
            zzbfaVar.zzm(8, this.zzalu.intValue());
        }
        if (this.zzalv != null) {
            zzbfaVar.zza(9, this.zzalv);
        }
        if (this.zzalw != null) {
            zzbfaVar.zza(10, this.zzalw);
        }
        if (this.zzalx != null && this.zzalx.length > 0) {
            for (int i = 0; i < this.zzalx.length; i++) {
                zzic zzicVar = this.zzalx[i];
                if (zzicVar != null) {
                    zzbfaVar.zza(11, zzicVar);
                }
            }
        }
        if (this.zzaly != null) {
            zzbfaVar.zza(12, this.zzaly);
        }
        if (this.zzalz != null) {
            zzbfaVar.zza(13, this.zzalz);
        }
        if (this.zzama != null) {
            zzbfaVar.zza(14, this.zzama);
        }
        if (this.zzamb != null) {
            zzbfaVar.zza(15, this.zzamb);
        }
        if (this.zzamc != null) {
            zzbfaVar.zza(16, this.zzamc);
        }
        if (this.zzamd != null && this.zzamd.length > 0) {
            for (int i2 = 0; i2 < this.zzamd.length; i2++) {
                zziu zziuVar = this.zzamd[i2];
                if (zziuVar != null) {
                    zzbfaVar.zza(17, zziuVar);
                }
            }
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzalt != null) {
            iZzr += zzbfa.zzq(7, this.zzalt.intValue());
        }
        if (this.zzalu != null) {
            iZzr += zzbfa.zzq(8, this.zzalu.intValue());
        }
        if (this.zzalv != null) {
            iZzr += zzbfa.zzb(9, this.zzalv);
        }
        if (this.zzalw != null) {
            iZzr += zzbfa.zzb(10, this.zzalw);
        }
        if (this.zzalx != null && this.zzalx.length > 0) {
            int iZzb = iZzr;
            for (int i = 0; i < this.zzalx.length; i++) {
                zzic zzicVar = this.zzalx[i];
                if (zzicVar != null) {
                    iZzb += zzbfa.zzb(11, zzicVar);
                }
            }
            iZzr = iZzb;
        }
        if (this.zzaly != null) {
            iZzr += zzbfa.zzb(12, this.zzaly);
        }
        if (this.zzalz != null) {
            iZzr += zzbfa.zzb(13, this.zzalz);
        }
        if (this.zzama != null) {
            iZzr += zzbfa.zzb(14, this.zzama);
        }
        if (this.zzamb != null) {
            iZzr += zzbfa.zzb(15, this.zzamb);
        }
        if (this.zzamc != null) {
            iZzr += zzbfa.zzb(16, this.zzamc);
        }
        if (this.zzamd != null && this.zzamd.length > 0) {
            for (int i2 = 0; i2 < this.zzamd.length; i2++) {
                zziu zziuVar = this.zzamd[i2];
                if (zziuVar != null) {
                    iZzr += zzbfa.zzb(17, zziuVar);
                }
            }
        }
        return iZzr;
    }
}
