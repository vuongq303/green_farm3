package com.google.android.gms.internal.ads;

import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.gameloft.adsmanager.JavaUtils;
import com.gameloft.glads.GLURLConnection;
import com.google.android.gms.ads.AdRequest;

/* JADX INFO: loaded from: classes.dex */
public final class zzba extends zzbfc<zzba> {
    public Integer zzel;
    public Integer zzem;
    public Integer zzes;
    private Integer zzfe;
    private Integer zzff;
    private Integer zzfg;
    public Integer zzfj;
    public String zzdc = null;
    public String zzcw = null;
    public Long zzdd = null;
    private Long zzde = null;
    public Long zzdf = null;
    public Long zzdg = null;
    private Long zzdh = null;
    private Long zzdi = null;
    private Long zzdj = null;
    private Long zzdk = null;
    private Long zzdl = null;
    public Long zzdm = null;
    private String zzdn = null;
    public Long zzdo = null;
    public Long zzdp = null;
    public Long zzdq = null;
    public Long zzdr = null;
    private Long zzds = null;
    private Long zzdt = null;
    public Long zzdu = null;
    public Long zzdv = null;
    public Long zzdw = null;
    public String zzcx = null;
    public String zzdx = null;
    public Long zzdy = null;
    public Long zzdz = null;
    public Long zzea = null;
    public String zzcz = null;
    public Long zzeb = null;
    public Long zzec = null;
    public Long zzed = null;
    private zzbd zzee = null;
    public Long zzef = null;
    public Long zzeg = null;
    public Long zzeh = null;
    public Long zzei = null;
    public Long zzej = null;
    public Long zzek = null;
    public String zzda = null;
    public String zzdb = null;
    public Long zzen = null;
    public Long zzeo = null;
    public Long zzep = null;
    private Long zzeq = null;
    private Long zzer = null;
    public zzbb zzet = null;
    public zzbb[] zzeu = zzbb.zzs();
    public zzbc zzev = null;
    private Long zzew = null;
    public Long zzex = null;
    public Long zzey = null;
    public Long zzez = null;
    public Long zzfa = null;
    public Long zzfb = null;
    public String zzfc = null;
    private Long zzfd = null;
    private Long zzfh = null;
    public String zzfi = null;
    public Boolean zzfk = null;
    private String zzfl = null;
    public Long zzfm = null;
    public zzbf zzfn = null;

    public zzba() {
        this.zzebt = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzbfi
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final zzba zza(zzbez zzbezVar) throws zzbfh {
        while (true) {
            int iZzabk = zzbezVar.zzabk();
            switch (iZzabk) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.zzdc = zzbezVar.readString();
                    break;
                case 18:
                    this.zzcw = zzbezVar.readString();
                    break;
                case 24:
                    this.zzdd = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 32:
                    this.zzde = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 40:
                    this.zzdf = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 48:
                    this.zzdg = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 56:
                    this.zzdh = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 64:
                    this.zzdi = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 72:
                    this.zzdj = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 80:
                    this.zzdk = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 88:
                    this.zzdl = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 96:
                    this.zzdm = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 106:
                    this.zzdn = zzbezVar.readString();
                    break;
                case 112:
                    this.zzdo = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 120:
                    this.zzdp = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 128:
                    this.zzdq = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 136:
                    this.zzdr = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 144:
                    this.zzds = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 152:
                    this.zzdt = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 160:
                    this.zzdu = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 168:
                    this.zzfh = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 176:
                    this.zzdv = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 184:
                    this.zzdw = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 194:
                    this.zzfi = zzbezVar.readString();
                    break;
                case GLURLConnection.HTTP_OK /* 200 */:
                    this.zzfm = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 208:
                    int position = zzbezVar.getPosition();
                    try {
                        int iZzacc = zzbezVar.zzacc();
                        if (iZzacc < 0 || iZzacc > 6) {
                            throw new IllegalArgumentException(new StringBuilder(44).append(iZzacc).append(" is not a valid enum DeviceIdType").toString());
                        }
                        this.zzfj = Integer.valueOf(iZzacc);
                    } catch (IllegalArgumentException e) {
                        zzbezVar.zzdc(position);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 218:
                    this.zzcx = zzbezVar.readString();
                    break;
                case 224:
                    this.zzfk = Boolean.valueOf(zzbezVar.zzabq());
                    break;
                case 234:
                    this.zzdx = zzbezVar.readString();
                    break;
                case 242:
                    this.zzfl = zzbezVar.readString();
                    break;
                case 248:
                    this.zzdy = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 256:
                    this.zzdz = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 264:
                    this.zzea = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 274:
                    this.zzcz = zzbezVar.readString();
                    break;
                case 280:
                    this.zzeb = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 288:
                    this.zzec = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 296:
                    this.zzed = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 306:
                    if (this.zzee == null) {
                        this.zzee = new zzbd();
                    }
                    zzbezVar.zza(this.zzee);
                    break;
                case 312:
                    this.zzef = Long.valueOf(zzbezVar.zzacd());
                    break;
                case JavaUtils.Constants.BANNER_WIDTH_DP /* 320 */:
                    this.zzeg = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 328:
                    this.zzeh = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 336:
                    this.zzei = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 346:
                    int iZzb = zzbfl.zzb(zzbezVar, 346);
                    int length = this.zzeu == null ? 0 : this.zzeu.length;
                    zzbb[] zzbbVarArr = new zzbb[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzeu, 0, zzbbVarArr, 0, length);
                    }
                    while (length < zzbbVarArr.length - 1) {
                        zzbbVarArr[length] = new zzbb();
                        zzbezVar.zza(zzbbVarArr[length]);
                        zzbezVar.zzabk();
                        length++;
                    }
                    zzbbVarArr[length] = new zzbb();
                    zzbezVar.zza(zzbbVarArr[length]);
                    this.zzeu = zzbbVarArr;
                    break;
                case 352:
                    this.zzej = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 360:
                    this.zzek = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 370:
                    this.zzda = zzbezVar.readString();
                    break;
                case 378:
                    this.zzdb = zzbezVar.readString();
                    break;
                case 384:
                    int position2 = zzbezVar.getPosition();
                    try {
                        this.zzel = Integer.valueOf(zzaz.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e2) {
                        zzbezVar.zzdc(position2);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 392:
                    int position3 = zzbezVar.getPosition();
                    try {
                        this.zzem = Integer.valueOf(zzaz.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e3) {
                        zzbezVar.zzdc(position3);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 402:
                    if (this.zzet == null) {
                        this.zzet = new zzbb();
                    }
                    zzbezVar.zza(this.zzet);
                    break;
                case 408:
                    this.zzen = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 416:
                    this.zzeo = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 424:
                    this.zzep = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 432:
                    this.zzeq = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 440:
                    this.zzer = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 448:
                    int position4 = zzbezVar.getPosition();
                    try {
                        this.zzes = Integer.valueOf(zzaz.zzd(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e4) {
                        zzbezVar.zzdc(position4);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case FacebookRequestErrorClassification.ESC_APP_NOT_INSTALLED /* 458 */:
                    if (this.zzev == null) {
                        this.zzev = new zzbc();
                    }
                    zzbezVar.zza(this.zzev);
                    break;
                case 464:
                    this.zzew = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 472:
                    this.zzex = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 480:
                    this.zzey = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 488:
                    this.zzez = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 496:
                    this.zzfa = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 504:
                    this.zzfb = Long.valueOf(zzbezVar.zzacd());
                    break;
                case AdRequest.MAX_CONTENT_URL_LENGTH /* 512 */:
                    this.zzfd = Long.valueOf(zzbezVar.zzacd());
                    break;
                case 520:
                    int position5 = zzbezVar.getPosition();
                    try {
                        this.zzfe = Integer.valueOf(zzaz.zzf(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e5) {
                        zzbezVar.zzdc(position5);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 528:
                    int position6 = zzbezVar.getPosition();
                    try {
                        this.zzff = Integer.valueOf(zzaz.zze(zzbezVar.zzacc()));
                    } catch (IllegalArgumentException e6) {
                        zzbezVar.zzdc(position6);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 538:
                    this.zzfc = zzbezVar.readString();
                    break;
                case 544:
                    int position7 = zzbezVar.getPosition();
                    try {
                        int iZzacc2 = zzbezVar.zzacc();
                        if (iZzacc2 < 0 || iZzacc2 > 3) {
                            throw new IllegalArgumentException(new StringBuilder(45).append(iZzacc2).append(" is not a valid enum DebuggerState").toString());
                        }
                        this.zzfg = Integer.valueOf(iZzacc2);
                    } catch (IllegalArgumentException e7) {
                        zzbezVar.zzdc(position7);
                        zza(zzbezVar, iZzabk);
                    }
                    break;
                case 1610:
                    if (this.zzfn == null) {
                        this.zzfn = new zzbf();
                    }
                    zzbezVar.zza(this.zzfn);
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
        if (this.zzdc != null) {
            zzbfaVar.zzf(1, this.zzdc);
        }
        if (this.zzcw != null) {
            zzbfaVar.zzf(2, this.zzcw);
        }
        if (this.zzdd != null) {
            zzbfaVar.zzi(3, this.zzdd.longValue());
        }
        if (this.zzde != null) {
            zzbfaVar.zzi(4, this.zzde.longValue());
        }
        if (this.zzdf != null) {
            zzbfaVar.zzi(5, this.zzdf.longValue());
        }
        if (this.zzdg != null) {
            zzbfaVar.zzi(6, this.zzdg.longValue());
        }
        if (this.zzdh != null) {
            zzbfaVar.zzi(7, this.zzdh.longValue());
        }
        if (this.zzdi != null) {
            zzbfaVar.zzi(8, this.zzdi.longValue());
        }
        if (this.zzdj != null) {
            zzbfaVar.zzi(9, this.zzdj.longValue());
        }
        if (this.zzdk != null) {
            zzbfaVar.zzi(10, this.zzdk.longValue());
        }
        if (this.zzdl != null) {
            zzbfaVar.zzi(11, this.zzdl.longValue());
        }
        if (this.zzdm != null) {
            zzbfaVar.zzi(12, this.zzdm.longValue());
        }
        if (this.zzdn != null) {
            zzbfaVar.zzf(13, this.zzdn);
        }
        if (this.zzdo != null) {
            zzbfaVar.zzi(14, this.zzdo.longValue());
        }
        if (this.zzdp != null) {
            zzbfaVar.zzi(15, this.zzdp.longValue());
        }
        if (this.zzdq != null) {
            zzbfaVar.zzi(16, this.zzdq.longValue());
        }
        if (this.zzdr != null) {
            zzbfaVar.zzi(17, this.zzdr.longValue());
        }
        if (this.zzds != null) {
            zzbfaVar.zzi(18, this.zzds.longValue());
        }
        if (this.zzdt != null) {
            zzbfaVar.zzi(19, this.zzdt.longValue());
        }
        if (this.zzdu != null) {
            zzbfaVar.zzi(20, this.zzdu.longValue());
        }
        if (this.zzfh != null) {
            zzbfaVar.zzi(21, this.zzfh.longValue());
        }
        if (this.zzdv != null) {
            zzbfaVar.zzi(22, this.zzdv.longValue());
        }
        if (this.zzdw != null) {
            zzbfaVar.zzi(23, this.zzdw.longValue());
        }
        if (this.zzfi != null) {
            zzbfaVar.zzf(24, this.zzfi);
        }
        if (this.zzfm != null) {
            zzbfaVar.zzi(25, this.zzfm.longValue());
        }
        if (this.zzfj != null) {
            zzbfaVar.zzm(26, this.zzfj.intValue());
        }
        if (this.zzcx != null) {
            zzbfaVar.zzf(27, this.zzcx);
        }
        if (this.zzfk != null) {
            zzbfaVar.zzf(28, this.zzfk.booleanValue());
        }
        if (this.zzdx != null) {
            zzbfaVar.zzf(29, this.zzdx);
        }
        if (this.zzfl != null) {
            zzbfaVar.zzf(30, this.zzfl);
        }
        if (this.zzdy != null) {
            zzbfaVar.zzi(31, this.zzdy.longValue());
        }
        if (this.zzdz != null) {
            zzbfaVar.zzi(32, this.zzdz.longValue());
        }
        if (this.zzea != null) {
            zzbfaVar.zzi(33, this.zzea.longValue());
        }
        if (this.zzcz != null) {
            zzbfaVar.zzf(34, this.zzcz);
        }
        if (this.zzeb != null) {
            zzbfaVar.zzi(35, this.zzeb.longValue());
        }
        if (this.zzec != null) {
            zzbfaVar.zzi(36, this.zzec.longValue());
        }
        if (this.zzed != null) {
            zzbfaVar.zzi(37, this.zzed.longValue());
        }
        if (this.zzee != null) {
            zzbfaVar.zza(38, this.zzee);
        }
        if (this.zzef != null) {
            zzbfaVar.zzi(39, this.zzef.longValue());
        }
        if (this.zzeg != null) {
            zzbfaVar.zzi(40, this.zzeg.longValue());
        }
        if (this.zzeh != null) {
            zzbfaVar.zzi(41, this.zzeh.longValue());
        }
        if (this.zzei != null) {
            zzbfaVar.zzi(42, this.zzei.longValue());
        }
        if (this.zzeu != null && this.zzeu.length > 0) {
            for (int i = 0; i < this.zzeu.length; i++) {
                zzbb zzbbVar = this.zzeu[i];
                if (zzbbVar != null) {
                    zzbfaVar.zza(43, zzbbVar);
                }
            }
        }
        if (this.zzej != null) {
            zzbfaVar.zzi(44, this.zzej.longValue());
        }
        if (this.zzek != null) {
            zzbfaVar.zzi(45, this.zzek.longValue());
        }
        if (this.zzda != null) {
            zzbfaVar.zzf(46, this.zzda);
        }
        if (this.zzdb != null) {
            zzbfaVar.zzf(47, this.zzdb);
        }
        if (this.zzel != null) {
            zzbfaVar.zzm(48, this.zzel.intValue());
        }
        if (this.zzem != null) {
            zzbfaVar.zzm(49, this.zzem.intValue());
        }
        if (this.zzet != null) {
            zzbfaVar.zza(50, this.zzet);
        }
        if (this.zzen != null) {
            zzbfaVar.zzi(51, this.zzen.longValue());
        }
        if (this.zzeo != null) {
            zzbfaVar.zzi(52, this.zzeo.longValue());
        }
        if (this.zzep != null) {
            zzbfaVar.zzi(53, this.zzep.longValue());
        }
        if (this.zzeq != null) {
            zzbfaVar.zzi(54, this.zzeq.longValue());
        }
        if (this.zzer != null) {
            zzbfaVar.zzi(55, this.zzer.longValue());
        }
        if (this.zzes != null) {
            zzbfaVar.zzm(56, this.zzes.intValue());
        }
        if (this.zzev != null) {
            zzbfaVar.zza(57, this.zzev);
        }
        if (this.zzew != null) {
            zzbfaVar.zzi(58, this.zzew.longValue());
        }
        if (this.zzex != null) {
            zzbfaVar.zzi(59, this.zzex.longValue());
        }
        if (this.zzey != null) {
            zzbfaVar.zzi(60, this.zzey.longValue());
        }
        if (this.zzez != null) {
            zzbfaVar.zzi(61, this.zzez.longValue());
        }
        if (this.zzfa != null) {
            zzbfaVar.zzi(62, this.zzfa.longValue());
        }
        if (this.zzfb != null) {
            zzbfaVar.zzi(63, this.zzfb.longValue());
        }
        if (this.zzfd != null) {
            zzbfaVar.zzi(64, this.zzfd.longValue());
        }
        if (this.zzfe != null) {
            zzbfaVar.zzm(65, this.zzfe.intValue());
        }
        if (this.zzff != null) {
            zzbfaVar.zzm(66, this.zzff.intValue());
        }
        if (this.zzfc != null) {
            zzbfaVar.zzf(67, this.zzfc);
        }
        if (this.zzfg != null) {
            zzbfaVar.zzm(68, this.zzfg.intValue());
        }
        if (this.zzfn != null) {
            zzbfaVar.zza(201, this.zzfn);
        }
        super.zza(zzbfaVar);
    }

    @Override // com.google.android.gms.internal.ads.zzbfc, com.google.android.gms.internal.ads.zzbfi
    protected final int zzr() {
        int iZzr = super.zzr();
        if (this.zzdc != null) {
            iZzr += zzbfa.zzg(1, this.zzdc);
        }
        if (this.zzcw != null) {
            iZzr += zzbfa.zzg(2, this.zzcw);
        }
        if (this.zzdd != null) {
            iZzr += zzbfa.zzd(3, this.zzdd.longValue());
        }
        if (this.zzde != null) {
            iZzr += zzbfa.zzd(4, this.zzde.longValue());
        }
        if (this.zzdf != null) {
            iZzr += zzbfa.zzd(5, this.zzdf.longValue());
        }
        if (this.zzdg != null) {
            iZzr += zzbfa.zzd(6, this.zzdg.longValue());
        }
        if (this.zzdh != null) {
            iZzr += zzbfa.zzd(7, this.zzdh.longValue());
        }
        if (this.zzdi != null) {
            iZzr += zzbfa.zzd(8, this.zzdi.longValue());
        }
        if (this.zzdj != null) {
            iZzr += zzbfa.zzd(9, this.zzdj.longValue());
        }
        if (this.zzdk != null) {
            iZzr += zzbfa.zzd(10, this.zzdk.longValue());
        }
        if (this.zzdl != null) {
            iZzr += zzbfa.zzd(11, this.zzdl.longValue());
        }
        if (this.zzdm != null) {
            iZzr += zzbfa.zzd(12, this.zzdm.longValue());
        }
        if (this.zzdn != null) {
            iZzr += zzbfa.zzg(13, this.zzdn);
        }
        if (this.zzdo != null) {
            iZzr += zzbfa.zzd(14, this.zzdo.longValue());
        }
        if (this.zzdp != null) {
            iZzr += zzbfa.zzd(15, this.zzdp.longValue());
        }
        if (this.zzdq != null) {
            iZzr += zzbfa.zzd(16, this.zzdq.longValue());
        }
        if (this.zzdr != null) {
            iZzr += zzbfa.zzd(17, this.zzdr.longValue());
        }
        if (this.zzds != null) {
            iZzr += zzbfa.zzd(18, this.zzds.longValue());
        }
        if (this.zzdt != null) {
            iZzr += zzbfa.zzd(19, this.zzdt.longValue());
        }
        if (this.zzdu != null) {
            iZzr += zzbfa.zzd(20, this.zzdu.longValue());
        }
        if (this.zzfh != null) {
            iZzr += zzbfa.zzd(21, this.zzfh.longValue());
        }
        if (this.zzdv != null) {
            iZzr += zzbfa.zzd(22, this.zzdv.longValue());
        }
        if (this.zzdw != null) {
            iZzr += zzbfa.zzd(23, this.zzdw.longValue());
        }
        if (this.zzfi != null) {
            iZzr += zzbfa.zzg(24, this.zzfi);
        }
        if (this.zzfm != null) {
            iZzr += zzbfa.zzd(25, this.zzfm.longValue());
        }
        if (this.zzfj != null) {
            iZzr += zzbfa.zzq(26, this.zzfj.intValue());
        }
        if (this.zzcx != null) {
            iZzr += zzbfa.zzg(27, this.zzcx);
        }
        if (this.zzfk != null) {
            this.zzfk.booleanValue();
            iZzr += zzbfa.zzcd(28) + 1;
        }
        if (this.zzdx != null) {
            iZzr += zzbfa.zzg(29, this.zzdx);
        }
        if (this.zzfl != null) {
            iZzr += zzbfa.zzg(30, this.zzfl);
        }
        if (this.zzdy != null) {
            iZzr += zzbfa.zzd(31, this.zzdy.longValue());
        }
        if (this.zzdz != null) {
            iZzr += zzbfa.zzd(32, this.zzdz.longValue());
        }
        if (this.zzea != null) {
            iZzr += zzbfa.zzd(33, this.zzea.longValue());
        }
        if (this.zzcz != null) {
            iZzr += zzbfa.zzg(34, this.zzcz);
        }
        if (this.zzeb != null) {
            iZzr += zzbfa.zzd(35, this.zzeb.longValue());
        }
        if (this.zzec != null) {
            iZzr += zzbfa.zzd(36, this.zzec.longValue());
        }
        if (this.zzed != null) {
            iZzr += zzbfa.zzd(37, this.zzed.longValue());
        }
        if (this.zzee != null) {
            iZzr += zzbfa.zzb(38, this.zzee);
        }
        if (this.zzef != null) {
            iZzr += zzbfa.zzd(39, this.zzef.longValue());
        }
        if (this.zzeg != null) {
            iZzr += zzbfa.zzd(40, this.zzeg.longValue());
        }
        if (this.zzeh != null) {
            iZzr += zzbfa.zzd(41, this.zzeh.longValue());
        }
        if (this.zzei != null) {
            iZzr += zzbfa.zzd(42, this.zzei.longValue());
        }
        if (this.zzeu != null && this.zzeu.length > 0) {
            int iZzb = iZzr;
            for (int i = 0; i < this.zzeu.length; i++) {
                zzbb zzbbVar = this.zzeu[i];
                if (zzbbVar != null) {
                    iZzb += zzbfa.zzb(43, zzbbVar);
                }
            }
            iZzr = iZzb;
        }
        if (this.zzej != null) {
            iZzr += zzbfa.zzd(44, this.zzej.longValue());
        }
        if (this.zzek != null) {
            iZzr += zzbfa.zzd(45, this.zzek.longValue());
        }
        if (this.zzda != null) {
            iZzr += zzbfa.zzg(46, this.zzda);
        }
        if (this.zzdb != null) {
            iZzr += zzbfa.zzg(47, this.zzdb);
        }
        if (this.zzel != null) {
            iZzr += zzbfa.zzq(48, this.zzel.intValue());
        }
        if (this.zzem != null) {
            iZzr += zzbfa.zzq(49, this.zzem.intValue());
        }
        if (this.zzet != null) {
            iZzr += zzbfa.zzb(50, this.zzet);
        }
        if (this.zzen != null) {
            iZzr += zzbfa.zzd(51, this.zzen.longValue());
        }
        if (this.zzeo != null) {
            iZzr += zzbfa.zzd(52, this.zzeo.longValue());
        }
        if (this.zzep != null) {
            iZzr += zzbfa.zzd(53, this.zzep.longValue());
        }
        if (this.zzeq != null) {
            iZzr += zzbfa.zzd(54, this.zzeq.longValue());
        }
        if (this.zzer != null) {
            iZzr += zzbfa.zzd(55, this.zzer.longValue());
        }
        if (this.zzes != null) {
            iZzr += zzbfa.zzq(56, this.zzes.intValue());
        }
        if (this.zzev != null) {
            iZzr += zzbfa.zzb(57, this.zzev);
        }
        if (this.zzew != null) {
            iZzr += zzbfa.zzd(58, this.zzew.longValue());
        }
        if (this.zzex != null) {
            iZzr += zzbfa.zzd(59, this.zzex.longValue());
        }
        if (this.zzey != null) {
            iZzr += zzbfa.zzd(60, this.zzey.longValue());
        }
        if (this.zzez != null) {
            iZzr += zzbfa.zzd(61, this.zzez.longValue());
        }
        if (this.zzfa != null) {
            iZzr += zzbfa.zzd(62, this.zzfa.longValue());
        }
        if (this.zzfb != null) {
            iZzr += zzbfa.zzd(63, this.zzfb.longValue());
        }
        if (this.zzfd != null) {
            iZzr += zzbfa.zzd(64, this.zzfd.longValue());
        }
        if (this.zzfe != null) {
            iZzr += zzbfa.zzq(65, this.zzfe.intValue());
        }
        if (this.zzff != null) {
            iZzr += zzbfa.zzq(66, this.zzff.intValue());
        }
        if (this.zzfc != null) {
            iZzr += zzbfa.zzg(67, this.zzfc);
        }
        if (this.zzfg != null) {
            iZzr += zzbfa.zzq(68, this.zzfg.intValue());
        }
        return this.zzfn != null ? iZzr + zzbfa.zzb(201, this.zzfn) : iZzr;
    }
}
