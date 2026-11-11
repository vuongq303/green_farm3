package com.google.android.gms.ads.internal;

import android.os.Build;
import com.google.android.gms.ads.internal.overlay.zzl;
import com.google.android.gms.ads.internal.overlay.zzu;
import com.google.android.gms.ads.internal.overlay.zzv;
import com.google.android.gms.internal.ads.zzaan;
import com.google.android.gms.internal.ads.zzabl;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzadi;
import com.google.android.gms.internal.ads.zzagc;
import com.google.android.gms.internal.ads.zzaiy;
import com.google.android.gms.internal.ads.zzajm;
import com.google.android.gms.internal.ads.zzajv;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzakq;
import com.google.android.gms.internal.ads.zzakv;
import com.google.android.gms.internal.ads.zzakw;
import com.google.android.gms.internal.ads.zzakx;
import com.google.android.gms.internal.ads.zzaky;
import com.google.android.gms.internal.ads.zzakz;
import com.google.android.gms.internal.ads.zzala;
import com.google.android.gms.internal.ads.zzalb;
import com.google.android.gms.internal.ads.zzalk;
import com.google.android.gms.internal.ads.zzamg;
import com.google.android.gms.internal.ads.zzamh;
import com.google.android.gms.internal.ads.zzamq;
import com.google.android.gms.internal.ads.zzaok;
import com.google.android.gms.internal.ads.zzaor;
import com.google.android.gms.internal.ads.zzaqg;
import com.google.android.gms.internal.ads.zzarc;
import com.google.android.gms.internal.ads.zzgg;
import com.google.android.gms.internal.ads.zzhc;
import com.google.android.gms.internal.ads.zzhd;
import com.google.android.gms.internal.ads.zzhr;
import com.google.android.gms.internal.ads.zznp;
import com.google.android.gms.internal.ads.zztw;
import com.google.android.gms.internal.ads.zzuq;
import com.google.android.gms.internal.ads.zzwg;
import com.google.android.gms.internal.ads.zzxg;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzbv {
    private static zzbv bCn;
    private static final Object sLock = new Object();
    private final zzad bCA;
    private final zznp bCB;
    private final zzalk bCC;
    private final zzagc bCD;
    private final zzaok bCE;
    private final zztw bCF;
    private final zzwg bCG;
    private final zzamg bCH;
    private final zzu bCI;
    private final zzv bCJ;
    private final zzxg bCK;
    private final zzamh bCL;
    private final zzbb bCM;
    private final zzaan bCN;
    private final zzhr bCO;
    private final zzaiy bCP;
    private final zzaqg bCQ;
    private final zzaor bCR;
    private final zzuq bCS;
    private final zzalb bCT;
    private final zzamq bCU;
    private final com.google.android.gms.ads.internal.overlay.zza bCo = new com.google.android.gms.ads.internal.overlay.zza();
    private final zzadi bCp = new zzadi();
    private final zzl bCq = new zzl();
    private final zzabl bCr = new zzabl();
    private final zzakk bCs = new zzakk();
    private final zzarc bCt = new zzarc();
    private final zzakq bCu;
    private final zzgg bCv;
    private final zzajm bCw;
    private final zzhc bCx;
    private final zzhd bCy;
    private final com.google.android.gms.common.d.e bCz;
    private final zzajv zzacn;

    static {
        zzbv zzbvVar = new zzbv();
        synchronized (sLock) {
            bCn = zzbvVar;
        }
    }

    protected zzbv() {
        int i = Build.VERSION.SDK_INT;
        this.bCu = i >= 21 ? new zzala() : i >= 19 ? new zzakz() : i >= 18 ? new zzakx() : i >= 17 ? new zzakw() : i >= 16 ? new zzaky() : new zzakv();
        this.bCv = new zzgg();
        this.bCw = new zzajm();
        this.zzacn = new zzajv();
        this.bCx = new zzhc();
        this.bCy = new zzhd();
        this.bCz = com.google.android.gms.common.d.h.abr();
        this.bCA = new zzad();
        this.bCB = new zznp();
        this.bCC = new zzalk();
        this.bCD = new zzagc();
        this.bCS = new zzuq();
        this.bCE = new zzaok();
        this.bCF = new zztw();
        this.bCG = new zzwg();
        this.bCH = new zzamg();
        this.bCI = new zzu();
        this.bCJ = new zzv();
        this.bCK = new zzxg();
        this.bCL = new zzamh();
        this.bCM = new zzbb();
        this.bCN = new zzaan();
        this.bCO = new zzhr();
        this.bCP = new zzaiy();
        this.bCQ = new zzaqg();
        this.bCR = new zzaor();
        this.bCT = new zzalb();
        this.bCU = new zzamq();
    }

    private static zzbv Xh() {
        zzbv zzbvVar;
        synchronized (sLock) {
            zzbvVar = bCn;
        }
        return zzbvVar;
    }

    public static zzadi zzeg() {
        return Xh().bCp;
    }

    public static com.google.android.gms.ads.internal.overlay.zza zzeh() {
        return Xh().bCo;
    }

    public static zzl zzei() {
        return Xh().bCq;
    }

    public static zzabl zzej() {
        return Xh().bCr;
    }

    public static zzakk zzek() {
        return Xh().bCs;
    }

    public static zzarc zzel() {
        return Xh().bCt;
    }

    public static zzakq zzem() {
        return Xh().bCu;
    }

    public static zzgg zzen() {
        return Xh().bCv;
    }

    public static zzajm zzeo() {
        return Xh().bCw;
    }

    public static zzajv zzep() {
        return Xh().zzacn;
    }

    public static zzhd zzeq() {
        return Xh().bCy;
    }

    public static com.google.android.gms.common.d.e zzer() {
        return Xh().bCz;
    }

    public static zzad zzes() {
        return Xh().bCA;
    }

    public static zznp zzet() {
        return Xh().bCB;
    }

    public static zzalk zzeu() {
        return Xh().bCC;
    }

    public static zzagc zzev() {
        return Xh().bCD;
    }

    public static zzaok zzew() {
        return Xh().bCE;
    }

    public static zztw zzex() {
        return Xh().bCF;
    }

    public static zzwg zzey() {
        return Xh().bCG;
    }

    public static zzamg zzez() {
        return Xh().bCH;
    }

    public static zzaan zzfa() {
        return Xh().bCN;
    }

    public static zzu zzfb() {
        return Xh().bCI;
    }

    public static zzv zzfc() {
        return Xh().bCJ;
    }

    public static zzxg zzfd() {
        return Xh().bCK;
    }

    public static zzamh zzfe() {
        return Xh().bCL;
    }

    public static zzaqg zzff() {
        return Xh().bCQ;
    }

    public static zzaor zzfg() {
        return Xh().bCR;
    }

    public static zzaiy zzfh() {
        return Xh().bCP;
    }

    public static zzuq zzfi() {
        return Xh().bCS;
    }

    public static zzalb zzfj() {
        return Xh().bCT;
    }

    public static zzamq zzfk() {
        return Xh().bCU;
    }
}
