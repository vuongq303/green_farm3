package com.google.android.gms.internal.ads;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzbfm extends zzbfc<zzbfm> {
    public Integer zzamf = null;
    private Integer zzecg = null;
    public String url = null;
    public String zzech = null;
    private String zzeci = null;
    public zzbfn zzecj = null;
    public zzbfu[] zzeck = zzbfu.zzagu();
    public String zzecl = null;
    public zzbft zzecm = null;
    private Boolean zzecn = null;
    private String[] zzeco = zzbfl.zzecd;
    private String zzecp = null;
    private Boolean zzecq = null;
    private Boolean zzecr = null;
    private byte[] zzecs = null;
    public zzbfv zzect = null;
    public String[] zzecu = zzbfl.zzecd;
    public String[] zzecv = zzbfl.zzecd;

    public zzbfm() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzaa, reason: merged with bridge method [inline-methods] */
    public final zzbfm zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.url = zzbezVar.readString();
                    break;
                case 18:
                    this.zzech = zzbezVar.readString();
                    break;
                case 26:
                    this.zzeci = zzbezVar.readString();
                    break;
                case 34:
                    int iZzb = zzbfl.zzb(zzbezVar, 34);
                    int length = this.zzeck == null ? 0 : this.zzeck.length;
                    zzbfu[] zzbfuVarArr = new zzbfu[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzeck, 0, zzbfuVarArr, 0, length);
                    }
                    while (length < zzbfuVarArr.length - 1) {
                        zzbfuVarArr[length] = new zzbfu();
                        zzbezVar.zza(zzbfuVarArr[length]);
                        zzbezVar.zzabk();
                        length++;
                    }
                    zzbfuVarArr[length] = new zzbfu();
                    zzbezVar.zza(zzbfuVarArr[length]);
                    this.zzeck = zzbfuVarArr;
                    break;
                case 40:
                    this.zzecn = Boolean.valueOf(zzbezVar.zzabq());
                    break;
                case 50:
                    int iZzb2 = zzbfl.zzb(zzbezVar, 50);
                    int length2 = this.zzeco == null ? 0 : this.zzeco.length;
                    String[] strArr = new String[iZzb2 + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzeco, 0, strArr, 0, length2);
                    }
                    while (length2 < strArr.length - 1) {
                        strArr[length2] = zzbezVar.readString();
                        zzbezVar.zzabk();
                        length2++;
                    }
                    strArr[length2] = zzbezVar.readString();
                    this.zzeco = strArr;
                    break;
                case 58:
                    this.zzecp = zzbezVar.readString();
                    break;
                case 64:
                    this.zzecq = Boolean.valueOf(zzbezVar.zzabq());
                    break;
                case 72:
                    this.zzecr = Boolean.valueOf(zzbezVar.zzabq());
                    break;
                case 80:
                    int position = zzbezVar.getPosition();
                    try {
                        int iZzabn = zzbezVar.zzabn();
                        if (iZzabn < 0 || iZzabn > 9) {
                            throw new IllegalArgumentException(new StringBuilder(42).append(iZzabn).append(" is not a valid enum ReportType").toString());
                        }
                        this.zzamf = Integer.valueOf(iZzabn);
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 88:
                    int position2 = zzbezVar.getPosition();
                    try {
                        int iZzabn2 = zzbezVar.zzabn();
                        if (iZzabn2 < 0 || iZzabn2 > 4) {
                            throw new IllegalArgumentException(new StringBuilder(39).append(iZzabn2).append(" is not a valid enum Verdict").toString());
                        }
                        this.zzecg = Integer.valueOf(iZzabn2);
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 98:
                    if (this.zzecj == null) {
                        this.zzecj = new zzbfn();
                    }
                    zzbezVar.zza(this.zzecj);
                    break;
                case 106:
                    this.zzecl = zzbezVar.readString();
                    break;
                case 114:
                    if (this.zzecm == null) {
                        this.zzecm = new zzbft();
                    }
                    zzbezVar.zza(this.zzecm);
                    break;
                case 122:
                    this.zzecs = zzbezVar.readBytes();
                    break;
                case 138:
                    if (this.zzect == null) {
                        this.zzect = new zzbfv();
                    }
                    zzbezVar.zza(this.zzect);
                    break;
                case 162:
                    int iZzb3 = zzbfl.zzb(zzbezVar, 162);
                    int length3 = this.zzecu == null ? 0 : this.zzecu.length;
                    String[] strArr2 = new String[iZzb3 + length3];
                    if (length3 != 0) {
                        System.arraycopy(this.zzecu, 0, strArr2, 0, length3);
                    }
                    while (length3 < strArr2.length - 1) {
                        strArr2[length3] = zzbezVar.readString();
                        zzbezVar.zzabk();
                        length3++;
                    }
                    strArr2[length3] = zzbezVar.readString();
                    this.zzecu = strArr2;
                    break;
                case 170:
                    int iZzb4 = zzbfl.zzb(zzbezVar, 170);
                    int length4 = this.zzecv == null ? 0 : this.zzecv.length;
                    String[] strArr3 = new String[iZzb4 + length4];
                    if (length4 != 0) {
                        System.arraycopy(this.zzecv, 0, strArr3, 0, length4);
                    }
                    while (length4 < strArr3.length - 1) {
                        strArr3[length4] = zzbezVar.readString();
                        zzbezVar.zzabk();
                        length4++;
                    }
                    strArr3[length4] = zzbezVar.readString();
                    this.zzecv = strArr3;
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
        if (this.url != null) {
            zzbfaVar.zzf(1, this.url);
        }
        if (this.zzech != null) {
            zzbfaVar.zzf(2, this.zzech);
        }
        if (this.zzeci != null) {
            zzbfaVar.zzf(3, this.zzeci);
        }
        if (this.zzeck != null && this.zzeck.length > 0) {
            for (int i = 0; i < this.zzeck.length; i++) {
                zzbfu zzbfuVar = this.zzeck[i];
                if (zzbfuVar != null) {
                    zzbfaVar.zza(4, zzbfuVar);
                }
            }
        }
        if (this.zzecn != null) {
            zzbfaVar.zzf(5, this.zzecn.booleanValue());
        }
        if (this.zzeco != null && this.zzeco.length > 0) {
            for (int i2 = 0; i2 < this.zzeco.length; i2++) {
                String str = this.zzeco[i2];
                if (str != null) {
                    zzbfaVar.zzf(6, str);
                }
            }
        }
        if (this.zzecp != null) {
            zzbfaVar.zzf(7, this.zzecp);
        }
        if (this.zzecq != null) {
            zzbfaVar.zzf(8, this.zzecq.booleanValue());
        }
        if (this.zzecr != null) {
            zzbfaVar.zzf(9, this.zzecr.booleanValue());
        }
        if (this.zzamf != null) {
            zzbfaVar.zzm(10, this.zzamf.intValue());
        }
        if (this.zzecg != null) {
            zzbfaVar.zzm(11, this.zzecg.intValue());
        }
        if (this.zzecj != null) {
            zzbfaVar.zza(12, this.zzecj);
        }
        if (this.zzecl != null) {
            zzbfaVar.zzf(13, this.zzecl);
        }
        if (this.zzecm != null) {
            zzbfaVar.zza(14, this.zzecm);
        }
        if (this.zzecs != null) {
            zzbfaVar.zza(15, this.zzecs);
        }
        if (this.zzect != null) {
            zzbfaVar.zza(17, this.zzect);
        }
        if (this.zzecu != null && this.zzecu.length > 0) {
            for (int i3 = 0; i3 < this.zzecu.length; i3++) {
                String str2 = this.zzecu[i3];
                if (str2 != null) {
                    zzbfaVar.zzf(20, str2);
                }
            }
        }
        if (this.zzecv != null && this.zzecv.length > 0) {
            for (int i4 = 0; i4 < this.zzecv.length; i4++) {
                String str3 = this.zzecv[i4];
                if (str3 != null) {
                    zzbfaVar.zzf(21, str3);
                }
            }
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.url != null) {
            iZzr += zzbfa.zzg(1, this.url);
        }
        if (this.zzech != null) {
            iZzr += zzbfa.zzg(2, this.zzech);
        }
        if (this.zzeci != null) {
            iZzr += zzbfa.zzg(3, this.zzeci);
        }
        if (this.zzeck != null && this.zzeck.length > 0) {
            int iZzb = iZzr;
            for (int i = 0; i < this.zzeck.length; i++) {
                zzbfu zzbfuVar = this.zzeck[i];
                if (zzbfuVar != null) {
                    iZzb += zzbfa.zzb(4, zzbfuVar);
                }
            }
            iZzr = iZzb;
        }
        if (this.zzecn != null) {
            this.zzecn.booleanValue();
            iZzr += zzbfa.zzcd(5) + 1;
        }
        if (this.zzeco != null && this.zzeco.length > 0) {
            int iZzeo = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < this.zzeco.length; i3++) {
                String str = this.zzeco[i3];
                if (str != null) {
                    i2++;
                    iZzeo += zzbfa.zzeo(str);
                }
            }
            iZzr = iZzr + iZzeo + (i2 * 1);
        }
        if (this.zzecp != null) {
            iZzr += zzbfa.zzg(7, this.zzecp);
        }
        if (this.zzecq != null) {
            this.zzecq.booleanValue();
            iZzr += zzbfa.zzcd(8) + 1;
        }
        if (this.zzecr != null) {
            this.zzecr.booleanValue();
            iZzr += zzbfa.zzcd(9) + 1;
        }
        if (this.zzamf != null) {
            iZzr += zzbfa.zzq(10, this.zzamf.intValue());
        }
        if (this.zzecg != null) {
            iZzr += zzbfa.zzq(11, this.zzecg.intValue());
        }
        if (this.zzecj != null) {
            iZzr += zzbfa.zzb(12, this.zzecj);
        }
        if (this.zzecl != null) {
            iZzr += zzbfa.zzg(13, this.zzecl);
        }
        if (this.zzecm != null) {
            iZzr += zzbfa.zzb(14, this.zzecm);
        }
        if (this.zzecs != null) {
            iZzr += zzbfa.zzb(15, this.zzecs);
        }
        if (this.zzect != null) {
            iZzr += zzbfa.zzb(17, this.zzect);
        }
        if (this.zzecu != null && this.zzecu.length > 0) {
            int iZzeo2 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < this.zzecu.length; i5++) {
                String str2 = this.zzecu[i5];
                if (str2 != null) {
                    i4++;
                    iZzeo2 += zzbfa.zzeo(str2);
                }
            }
            iZzr = iZzr + iZzeo2 + (i4 * 2);
        }
        if (this.zzecv == null || this.zzecv.length <= 0) {
            return iZzr;
        }
        int iZzeo3 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < this.zzecv.length; i7++) {
            String str3 = this.zzecv[i7];
            if (str3 != null) {
                i6++;
                iZzeo3 += zzbfa.zzeo(str3);
            }
        }
        return iZzr + iZzeo3 + (i6 * 2);
    }
}
