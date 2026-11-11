package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzbfu extends zzbfc<zzbfu> {
    private static volatile zzbfu[] zzedm;
    public Integer zzedn = null;
    public String url = null;
    public zzbfp zzedo = null;
    private zzbfr zzedp = null;
    private Integer zzedq = null;
    private int[] zzedr = zzbfl.zzeby;
    private String zzeds = null;
    public Integer zzedt = null;
    public String[] zzedu = zzbfl.zzecd;

    public zzbfu() {
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzac, reason: merged with bridge method [inline-methods] */
    public final zzbfu zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case 8:
                    this.zzedn = Integer.valueOf(zzbezVar.zzabn());
                    break;
                case 18:
                    this.url = zzbezVar.readString();
                    break;
                case 26:
                    if (this.zzedo == null) {
                        this.zzedo = new zzbfp();
                    }
                    zzbezVar.zza(this.zzedo);
                    break;
                case 34:
                    if (this.zzedp == null) {
                        this.zzedp = new zzbfr();
                    }
                    zzbezVar.zza(this.zzedp);
                    break;
                case 40:
                    this.zzedq = Integer.valueOf(zzbezVar.zzabn());
                    break;
                case 48:
                    int iZzb = zzbfl.zzb(zzbezVar, 48);
                    int length = this.zzedr == null ? 0 : this.zzedr.length;
                    int[] iArr = new int[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzedr, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = zzbezVar.zzabn();
                        zzbezVar.zzabk();
                        length++;
                    }
                    iArr[length] = zzbezVar.zzabn();
                    this.zzedr = iArr;
                    break;
                case 50:
                    int iZzbr = zzbezVar.zzbr(zzbezVar.zzacc());
                    int position = zzbezVar.getPosition();
                    int i = 0;
                    while (zzbezVar.zzagn() > 0) {
                        zzbezVar.zzabn();
                        i++;
                    }
                    zzbezVar.zzdc(position);
                    int length2 = this.zzedr == null ? 0 : this.zzedr.length;
                    int[] iArr2 = new int[i + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzedr, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = zzbezVar.zzabn();
                        length2++;
                    }
                    this.zzedr = iArr2;
                    zzbezVar.zzbs(iZzbr);
                    break;
                case 58:
                    this.zzeds = zzbezVar.readString();
                    break;
                case 64:
                    int position2 = zzbezVar.getPosition();
                    try {
                        int iZzabn = zzbezVar.zzabn();
                        if (iZzabn < 0 || iZzabn > 3) {
                            throw new IllegalArgumentException(new StringBuilder(46).append(iZzabn).append(" is not a valid enum AdResourceType").toString());
                        }
                        this.zzedt = Integer.valueOf(iZzabn);
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 74:
                    int iZzb2 = zzbfl.zzb(zzbezVar, 74);
                    int length3 = this.zzedu == null ? 0 : this.zzedu.length;
                    String[] strArr = new String[iZzb2 + length3];
                    if (length3 != 0) {
                        System.arraycopy(this.zzedu, 0, strArr, 0, length3);
                    }
                    while (length3 < strArr.length - 1) {
                        strArr[length3] = zzbezVar.readString();
                        zzbezVar.zzabk();
                        length3++;
                    }
                    strArr[length3] = zzbezVar.readString();
                    this.zzedu = strArr;
                    break;
                default:
                    if (!super.zza(zzbezVar, iZzabk)) {
                    }
                    break;
            }
        }
        return this;
    }

    public static zzbfu[] zzagu() {
        if (zzedm == null) {
            synchronized (zzbfg.zzebs) {
                if (zzedm == null) {
                    zzedm = new zzbfu[0];
                }
            }
        }
        return zzedm;
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    public final void zza(zzbfa zzbfaVar) throws zzbfb {
        zzbfaVar.zzm(1, this.zzedn.intValue());
        if (this.url != null) {
            zzbfaVar.zzf(2, this.url);
        }
        if (this.zzedo != null) {
            zzbfaVar.zza(3, this.zzedo);
        }
        if (this.zzedp != null) {
            zzbfaVar.zza(4, this.zzedp);
        }
        if (this.zzedq != null) {
            zzbfaVar.zzm(5, this.zzedq.intValue());
        }
        if (this.zzedr != null && this.zzedr.length > 0) {
            for (int i = 0; i < this.zzedr.length; i++) {
                zzbfaVar.zzm(6, this.zzedr[i]);
            }
        }
        if (this.zzeds != null) {
            zzbfaVar.zzf(7, this.zzeds);
        }
        if (this.zzedt != null) {
            zzbfaVar.zzm(8, this.zzedt.intValue());
        }
        if (this.zzedu != null && this.zzedu.length > 0) {
            for (int i2 = 0; i2 < this.zzedu.length; i2++) {
                String str = this.zzedu[i2];
                if (str != null) {
                    zzbfaVar.zzf(9, str);
                }
            }
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr() + zzbfa.zzq(1, this.zzedn.intValue());
        if (this.url != null) {
            iZzr += zzbfa.zzg(2, this.url);
        }
        if (this.zzedo != null) {
            iZzr += zzbfa.zzb(3, this.zzedo);
        }
        if (this.zzedp != null) {
            iZzr += zzbfa.zzb(4, this.zzedp);
        }
        if (this.zzedq != null) {
            iZzr += zzbfa.zzq(5, this.zzedq.intValue());
        }
        if (this.zzedr != null && this.zzedr.length > 0) {
            int iZzce = 0;
            for (int i = 0; i < this.zzedr.length; i++) {
                iZzce += zzbfa.zzce(this.zzedr[i]);
            }
            iZzr = iZzr + iZzce + (this.zzedr.length * 1);
        }
        if (this.zzeds != null) {
            iZzr += zzbfa.zzg(7, this.zzeds);
        }
        if (this.zzedt != null) {
            iZzr += zzbfa.zzq(8, this.zzedt.intValue());
        }
        if (this.zzedu == null || this.zzedu.length <= 0) {
            return iZzr;
        }
        int iZzeo = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzedu.length; i3++) {
            String str = this.zzedu[i3];
            if (str != null) {
                i2++;
                iZzeo += zzbfa.zzeo(str);
            }
        }
        return iZzr + iZzeo + (i2 * 1);
    }
}
