package com.quanhoang.greenfarm3.S800x480;

import android.os.SystemClock;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.gameloft.glads.GLURLConnection;
import com.google.android.gms.ads.AdRequest;

/* JADX INFO: loaded from: classes.dex */
class FarmEntity {
    static FarmEntity[] als;
    static int[] ape;
    static int[] apf;
    static int[][] apg;
    static int aph;
    int Bj;
    private long tutorialEatingEpoch;
    private long tutorialGrowthEpoch;
    int HL;
    int Kx;
    int alC;
    int alD;
    int alE;
    int alF;
    int alG;
    int alH;
    float alI;
    float alJ;
    float alK;
    float alL;
    int alM;
    int alv;
    int alw;
    int alx;
    int aly;
    int alz;
    byte amb;
    long amd;
    int ame;
    int amf;
    int amg;
    long amq;
    boolean anE;
    boolean anF;
    String ans;
    int anu;
    int anv;
    int aoA;
    int aoD;
    int aoG;
    int aoH;
    int aoI;
    int aoJ;
    int aoK;
    int aoL;
    FarmEntity aoM;
    int[] aoN;
    aa aoO;
    int aoP;
    int aoQ;
    aa aoR;
    aa aoS;
    boolean aoT;
    int aoU;
    int aoV;
    int aoW;
    FarmEntity aoi;
    FarmEntity aoj;
    int aol;
    int aom;
    int aon;
    int aoo;
    int aop;
    int aoq;
    int aor;
    int aos;
    int aot;
    int aou;
    int aov;
    int aow;
    int aox;
    int aoy;
    int aoz;
    int apa;
    int apb;
    boolean apc;
    boolean apd;
    int apj;
    public static boolean alt = false;
    public static long alu = 300000;
    static boolean alN = false;
    static final int alU = FarmGameEngine.ih(86400);
    public static boolean amc = false;
    static int anY = 0;
    static int anZ = 10;
    static int aoa = 0;
    static int aob = 10;
    static int aoc = 0;
    static int aod = 0;
    static int aoe = 0;
    static int aof = 10;
    static boolean aog = false;
    static int api = 0;
    boolean alA = false;
    boolean alB = false;
    int alO = 0;
    int alP = 0;
    boolean alQ = false;
    short alR = 0;
    short alS = 0;
    char alT = 0;
    long alV = 0;
    boolean alW = false;
    int alX = 0;
    int alY = 0;
    int alZ = 0;
    boolean ama = false;
    int amh = 0;
    int ami = 0;
    int amj = 0;
    int amk = 0;
    byte aml = 0;
    boolean amm = false;
    boolean amn = false;
    boolean amo = false;
    byte amp = 0;
    int amr = FarmGameEngine.ih(0);
    boolean ams = false;
    int amt = 2;
    boolean amu = false;
    long amv = 0;
    final int amw = 1;
    int amx = 0;
    int amy = 0;
    int amz = 0;
    int amA = 0;
    long amB = 0;
    long amC = 0;
    int amD = Integer.MAX_VALUE;
    int amE = FarmGameEngine.ih(Integer.MAX_VALUE);
    int amF = 0;
    int amG = 0;
    int amH = 0;
    int amI = FarmGameEngine.ih(0);
    long amJ = 0;
    int amK = 0;
    boolean amL = false;
    boolean amM = false;
    boolean amN = false;
    boolean amO = false;
    boolean amP = false;
    boolean amQ = false;
    int amR = 0;
    short amS = 0;
    boolean amT = false;
    boolean amU = false;
    short amV = 0;
    short amW = 0;
    int amX = 0;
    int amY = 0;
    int amZ = 0;
    int ana = 0;
    boolean anb = false;
    short anc = 0;
    boolean and = false;
    long ane = 0;
    int anf = 0;
    int ang = 0;
    int anh = 0;
    boolean ani = true;
    boolean anj = false;
    boolean ank = false;
    boolean anl = true;
    boolean anm = true;
    boolean ann = false;
    int ano = 0;
    int anp = 0;
    boolean anq = false;
    boolean anr = true;
    int ant = -1;
    boolean anw = false;
    long anx = 1;
    long any = 1;
    long anz = 0;
    long anA = 0;
    long anB = 0;
    long anC = 0;
    int anD = 0;
    int anG = 0;
    int anH = 0;
    int anI = 0;
    int anJ = 0;
    int anK = 0;
    int anL = 0;
    int anM = 0;
    boolean anN = false;
    boolean anO = false;
    int anP = 0;
    int anQ = 0;
    int anR = 0;
    int anS = 0;
    int anT = 0;
    int anU = 0;
    int anV = 0;
    int anW = -1;
    boolean anX = true;
    int aoh = 0;
    int aok = -1;
    short aoB = 0;
    int aoC = -1;
    int aoE = 0;
    int aoF = 0;
    int aoX = 0;
    byte aoY = 0;
    z aoZ = null;
    boolean apk = false;

    static void Init() {
        als = new FarmEntity[6];
        ape = new int[4];
        apf = new int[7];
        apg = new int[500][];
        aph = 0;
        for (int i = 0; i < 500; i++) {
            apg[i] = new int[4];
        }
    }

    static int a(int i, int i2, int i3, int i4, int i5, int i6, FarmEntity[] chVarArr, int i7) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        int i8 = 0;
        for (FarmEntity chVar = als[i]; chVar != null && i8 < i7; chVar = chVar.aoi) {
            iArr[0] = chVar.aol;
            iArr[1] = chVar.aom;
            FarmGameEngine.f(iArr, iArr2);
            if ((i2 == -1 || i2 == chVar.Kx) && chVar.Kx != -1 && iArr2[0] >= i3 && iArr2[0] < i4 && iArr2[1] >= i5 && iArr2[1] < i6) {
                chVarArr[i8] = chVar;
                i8++;
            }
        }
        return i8;
    }

    static int a(int i, int i2, int i3, int i4, FarmEntity[] chVarArr, int i5) {
        int i6 = 0;
        for (FarmEntity chVar = als[i]; chVar != null && i6 < i5; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && chVar.aoC >= i3 && chVar.aoC <= i4 && !chVar.fz(32)) {
                chVarArr[i6] = chVar;
                i6++;
            }
        }
        return i6;
    }

    static int a(int i, int i2, int i3, FarmEntity[] chVarArr, int i4) {
        int i5 = 0;
        for (FarmEntity chVar = als[i]; chVar != null && i5 < i4; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && ((i3 == -1 || chVar.aoC == i3) && chVar.ams)) {
                chVarArr[i5] = chVar;
                i5++;
            }
        }
        return i5;
    }

    static int a(int i, int i2, FarmEntity[] chVarArr, int i3) {
        return b(i, i2, -1, chVarArr, i3);
    }

    static int a(int i, int i2, FarmEntity[] chVarArr, int i3, FarmEntity chVar) {
        int i4 = 0;
        for (FarmEntity chVar2 = als[i]; chVar2 != null && i4 < i3; chVar2 = chVar2.aoi) {
            if (chVar2.Kx == i2 && chVar2.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (FarmGameEngine.awl || FarmGameEngine.c(chVar2, chVar))) {
                chVarArr[i4] = chVar2;
                i4++;
            }
        }
        return i4;
    }

    static int a(FarmEntity[] chVarArr, int i) {
        return a(chVarArr, i, -1);
    }

    static int a(FarmEntity[] chVarArr, int i, int i2) {
        int i3 = 0;
        if (als != null) {
            for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
                if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (chVar.qj() || chVar.qi())) {
                    chVarArr[i3] = chVar;
                    i3++;
                }
            }
        }
        return i3;
    }

    static void a(FarmEntity chVar, int i) {
        chVar.aok = i;
        chVar.aov = chVar.oW();
        FarmEntity chVar2 = null;
        FarmEntity chVar3 = als[i];
        while (chVar3 != null && chVar.aov > chVar3.oW()) {
            chVar2 = chVar3;
            chVar3 = chVar3.aoi;
        }
        FarmEntity chVar4 = chVar2;
        while (chVar3 != null && chVar.aov == chVar3.oW() && chVar.aoG > chVar3.aoG) {
            chVar4 = chVar3;
            chVar3 = chVar3.aoi;
        }
        chVar.aoj = chVar4;
        chVar.aoi = chVar3;
        if (chVar4 == null) {
            als[i] = chVar;
        } else {
            chVar4.aoi = chVar;
        }
        if (chVar3 != null) {
            chVar3.aoj = chVar;
        }
    }

    static boolean a(FarmEntity chVar) {
        return chVar.Kx == 50 && (chVar.aoC == 9 || chVar.aoC == 14);
    }

    static boolean a(FarmEntity[] chVarArr, FarmEntity chVar) {
        for (int i = 0; i < chVarArr.length && chVarArr[i] != null; i++) {
            if (chVarArr[i].Kx == chVar.Kx && chVarArr[i].aoC == chVar.aoC && chVarArr[i].aoU == chVar.aoU) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:82:0x0113  */
    static FarmEntity[] a(int i, int i2, FarmEntity chVar, int i3) {
        int i4;
        FarmEntity[] chVarArr;
        boolean z;
        FarmEntity[] chVarArr2;
        int i5;
        FarmEntity chVar2 = als[i];
        FarmEntity[] chVarArr3 = new FarmEntity[i3];
        int i6 = 0;
        int i7 = chVar.aoy;
        int i8 = chVar.aoz;
        for (FarmEntity chVar3 = chVar2; chVar3 != null && i6 < i3; chVar3 = chVar3.aoi) {
            if (!(i2 == -1 || chVar3.Kx == i2) || !chVar3.fz(AdRequest.MAX_CONTENT_URL_LENGTH) || !chVar3.fz(128) || chVar3.fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) || chVar3.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) || chVar3.anw || a(chVar3)) {
                i4 = i6;
            } else if (i2 == -1) {
                chVar.aol = chVar3.aol;
                chVar.aom = chVar3.aom;
                int[] iArrA = FarmGameEngine.a(new FarmEntity[]{chVar});
                if (iArrA != null) {
                    FarmEntity chVar4 = als[i];
                    chVarArr3[0] = chVar3;
                    int i9 = 1;
                    FarmEntity chVar5 = chVar4;
                    while (true) {
                        if (chVar5 == null || i9 >= i3) {
                            z = true;
                            chVarArr2 = chVarArr3;
                            break;
                        }
                        if (!chVar5.fz(AdRequest.MAX_CONTENT_URL_LENGTH) || chVar5.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) || chVar5.fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) || chVar5.anw || !a(chVarArr3, chVar5) || a(chVar5) || !FarmGameEngine.c(chVar5, chVar)) {
                            i5 = i9;
                        } else {
                            if (!chVar5.fz(128)) {
                                z = false;
                                chVarArr2 = new FarmEntity[i3];
                                break;
                            }
                            i5 = i9 + 1;
                            chVarArr3[i9] = chVar5;
                        }
                        chVar5 = chVar5.aoi;
                        i9 = i5;
                    }
                    if (z) {
                        int[] iArrA2 = FarmGameEngine.a(chVarArr2);
                        boolean z2 = false;
                        for (int i10 = 0; i10 < iArrA.length && !z2; i10++) {
                            if (FarmGameEngine.aAP[iArrA[i10]] == 1) {
                                boolean z3 = false;
                                for (int i11 = 0; i11 < iArrA2.length && !z3; i11++) {
                                    if (iArrA[i10] == iArrA2[i11]) {
                                        z3 = true;
                                    }
                                }
                                z2 = !z3;
                            }
                        }
                        String str = "collide --------------------------------------------------- " + z2;
                        if (!z2) {
                            return chVarArr2;
                        }
                    }
                    chVarArr = chVarArr2;
                } else {
                    chVarArr = chVarArr3;
                }
                i4 = i6;
                chVarArr3 = chVarArr;
            } else if (chVar3.aoy < i7 || chVar3.aoz < i8) {
                i4 = i6;
            } else {
                FarmEntity chVarFm = FarmGameEngine.Fm();
                if (chVarFm != null ? FarmGameEngine.c(chVarFm, chVar3) : false) {
                    i4 = i6;
                } else {
                    chVarArr3[i6] = chVar3;
                    i4 = i6 + 1;
                }
            }
            i6 = i4;
        }
        return chVarArr3;
    }

    static int aA(int i, int i2) {
        int i3 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && !chVar.fz(32) && chVar.fz(2048)) {
                i3++;
            }
        }
        return i3;
    }

    static FarmEntity aB(int i, int i2) {
        if (als != null) {
            FarmEntity chVar = als[i];
            FarmEntity chVar2 = chVar;
            for (FarmEntity chVar3 = chVar; chVar3 != null && 0 == 0; chVar3 = chVar3.aoi) {
                if ((i2 == -1 || chVar3.Kx == i2) && chVar3.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && chVar3.qj()) {
                    return chVar3;
                }
                chVar2 = chVar3;
            }
            for (FarmEntity chVar4 = chVar2; chVar4 != null && 0 == 0; chVar4 = chVar4.aoj) {
                if ((i2 == -1 || chVar4.Kx == i2) && chVar4.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && chVar4.qi()) {
                    return chVar4;
                }
            }
        }
        return null;
    }

    static FarmEntity aC(int i, int i2) {
        FarmEntity chVar = als[i];
        while (chVar != null) {
            if (chVar.aoD == i2 && (FarmGameEngine.aCu != 19 || chVar.aoC != 14 || FarmGameEngine.currentEngineState() != 36)) {
                return chVar;
            }
            chVar = chVar.aoi;
        }
        return null;
    }

    static int aD(int i, int i2) {
        int i3 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 54) {
                String str = "actor animal. sel:" + chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) + ",wt:" + ((int) chVar.aoB);
            }
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH)) {
                i3 += chVar.aoB;
            }
        }
        return i3;
    }

    public static void as(boolean z) {
        for (FarmEntity chVar = als[1]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 50) {
                switch (chVar.aoC) {
                    case 29:
                        if (z) {
                            chVar.fx(8);
                            amc = true;
                        } else {
                            chVar.fy(8);
                            amc = false;
                        }
                        break;
                }
            }
        }
    }

    static void au(boolean z) {
        for (FarmEntity chVar = als[1]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx != 54 || ((chVar.anp != 0 && z) || chVar.anj || chVar.ank || chVar.anw)) {
                if (chVar.Kx == 69 && FarmGameEngine.u(chVar)) {
                    if (z) {
                        chVar.px();
                    } else {
                        chVar.py();
                    }
                }
            } else if (FarmGameEngine.u(chVar)) {
                if (z) {
                    chVar.pE();
                } else {
                    chVar.pS();
                }
            }
        }
    }

    static int av(int i, int i2) {
        int i3 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2) {
                i3++;
            }
        }
        return i3;
    }

    static int aw(int i, int i2) {
        int i3 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH)) {
                if (!(chVar.Kx == 50 && chVar.aoC == 27)) {
                    i3++;
                }
            }
        }
        return i3;
    }

    static int ax(int i, int i2) {
        int i3 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 54 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && !chVar.anj && !chVar.ank) {
                if (i2 == 0 && chVar.fz(32)) {
                    i3++;
                } else if (i2 == 1 && !chVar.fz(32)) {
                    i3++;
                } else if (i2 == 2 && chVar.fz(2048)) {
                    i3++;
                }
            }
        }
        return i3;
    }

    static int ay(int i, int i2) {
        int i3 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.aoU == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH)) {
                i3++;
            }
        }
        return i3;
    }

    static int az(int i, int i2) {
        int i3 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH)) {
                i3++;
            }
        }
        return i3;
    }

    static int b(int i, int i2, int i3, int i4, FarmEntity[] chVarArr, int i5) {
        int i6 = 0;
        for (FarmEntity chVar = als[i]; chVar != null && i6 < i5; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && chVar.aoU >= i3 && chVar.aoU <= i4 && !chVar.fz(32) && !chVar.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                chVarArr[i6] = chVar;
                i6++;
            }
        }
        return i6;
    }

    static int b(int i, int i2, int i3, FarmEntity[] chVarArr, int i4) {
        int i5 = 0;
        for (FarmEntity chVar = als[i]; chVar != null && i5 < i4; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && (i3 == -1 || chVar.aoC == i3)) {
                chVarArr[i5] = chVar;
                i5++;
            }
        }
        return i5;
    }

    static FarmEntity b(int i, int i2, int[] iArr) {
        FarmEntity chVar = new FarmEntity();
        chVar.d(i, iArr);
        int i3 = api;
        api = i3 + 1;
        chVar.aoW = i3;
        a(chVar, i2);
        return chVar;
    }

    static int c(int i, int i2, int i3, FarmEntity[] chVarArr, int i4) {
        int i5 = 0;
        for (FarmEntity chVar = als[i]; chVar != null && i5 < i4; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (i3 == -1 || chVar.aoC == i3)) {
                chVarArr[i5] = chVar;
                i5++;
            }
        }
        return i5;
    }

    static void c(boolean z, boolean z2) {
        FarmGameEngine.a(151144, new String[]{"INT:" + Integer.toString(151147), "INT:" + Integer.toString(z2 ? 151148 : 151149), "INT:" + Integer.toString(FarmGameEngine.FC())});
    }

    static int d(int i, int i2, int i3, FarmEntity[] chVarArr, int i4) {
        int i5 = 0;
        for (FarmEntity chVar = als[i]; chVar != null && i5 < i4; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && (i3 == -1 || chVar.aoU == i3)) {
                chVarArr[i5] = chVar;
                i5++;
            }
        }
        return i5;
    }

    static int e(int i, int i2, int i3, FarmEntity[] chVarArr, int i4) {
        int i5;
        int i6 = 0;
        FarmEntity chVar = als[i];
        while (chVar != null && i6 < i4) {
            if (chVar.Kx == i2 && chVar.fz(4096) && chVar.aoU == i3) {
                i5 = i6 + 1;
                chVarArr[i6] = chVar;
            } else {
                i5 = i6;
            }
            chVar = chVar.aoi;
            i6 = i5;
        }
        return i6;
    }

    static FarmEntity e(int i, int[] iArr) {
        return b(i, 1, iArr);
    }

    static void eW(int i) {
        FarmEntity chVar = als[i];
        while (chVar != null) {
            FarmEntity chVar2 = chVar.aoi;
            chVar.mg();
            chVar = chVar2;
        }
    }

    static void eY(int i) {
        while (als[i] != null) {
            als[i].oN();
        }
    }

    static void eZ(int i) {
        FarmEntity chVar = als[i];
        FarmEntity chVar2 = null;
        while (chVar != null) {
            FarmEntity chVar3 = chVar.aoi;
            int iOW = chVar.oW();
            if (iOW != chVar.aov) {
                chVar.aov = iOW;
                chVar.oN();
                chVar.aoi = chVar2;
                chVar2 = chVar;
            }
            chVar = chVar3;
        }
        while (chVar2 != null) {
            FarmEntity chVar4 = chVar2.aoi;
            a(chVar2, i);
            chVar2 = chVar4;
        }
    }

    static int fA(int i) {
        if ((i >= 1000 && i < 2000) || (i >= 10000 && i < 11000)) {
            return 50;
        }
        if (i >= 2000 && i < 3000) {
            return 54;
        }
        if (i >= 3000 && i < 4000) {
            return 49;
        }
        if (i >= 7000 && i < 8000) {
            return 20;
        }
        if (i >= 25000 && i < 26000) {
            return 48;
        }
        if (i < 4000 || i >= 5000) {
            return (i < 9000 || i >= 10000) ? -1 : 56;
        }
        return 55;
    }

    static void fa(int i) {
        FarmEntity chVar = als[i];
        while (chVar != null) {
            FarmEntity chVar2 = chVar.aoi;
            chVar.aov = 0;
            chVar = chVar2;
        }
    }

    static int fb(int i) {
        int i2 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 49 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (chVar.Bj == 3 || chVar.Bj == 4 || chVar.Bj == 5)) {
                i2++;
            }
        }
        return i2;
    }

    static FarmEntity fc(int i) {
        return aB(i, -1);
    }

    static void fd(int i) {
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            int[] iArr = new int[2];
            FarmGameEngine.f(new int[]{chVar.aol, chVar.aom}, iArr);
            if (!FarmGameEngine.hB(FarmGameEngine.bE(iArr[0], iArr[1]))) {
                chVar.fy(AdRequest.MAX_CONTENT_URL_LENGTH);
            } else if (chVar.Kx != 50 || chVar.aoC < 0 || chVar.aoC > 8) {
                chVar.fx(AdRequest.MAX_CONTENT_URL_LENGTH);
            } else {
                chVar.fy(AdRequest.MAX_CONTENT_URL_LENGTH);
            }
            if (chVar.Kx == 50 && (chVar.aoC == 27 || chVar.aoC == 29)) {
                chVar.fx(AdRequest.MAX_CONTENT_URL_LENGTH);
            }
        }
    }

    static int fe(int i) {
        int i2;
        int i3 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 47 && chVar.anG == 59 && (i2 = chVar.anM & 65535) != 110 && i2 != 5 && (i2 < 111 || i2 > 116)) {
                i3 += (chVar.anM & (-65536)) >> 16;
            }
        }
        return i3;
    }

    static int ff(int i) {
        int i2 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 47 && chVar.anG == 37) {
                i2++;
            }
        }
        String str = "ActorLists_GetQtyEXPReward : " + i2;
        return i2;
    }

    static void fr(int i) {
        FarmGameEngine.a(151144, new String[]{"INT:" + Integer.toString(151145), "INT:" + Integer.toString(i), "INT:" + Integer.toString(FarmGameEngine.FC())});
    }

    static FarmEntity ft(int i) {
        int iL = GameRuntime.L(25, 29);
        String str = "Fireworks sndIndex : " + iL;
        FarmGameEngine.gX(iL);
        if (i == -1) {
            anY = -1;
        } else {
            anY = 0;
            anZ = i;
        }
        if (FarmGameEngine.aZf[85] == null) {
            FarmGameEngine.s(0, 85, 15, 24);
            GameRuntime.bk("/9");
            FarmGameEngine.fP(0);
            GameRuntime.ke();
        }
        if (av(1, 21) <= 0) {
            FarmEntity chVarE = e(21, new int[]{((FarmGameEngine.aZj.aol + (GameRuntime.kH() >> 1)) * 100) / FarmGameEngine.aYP, ((FarmGameEngine.aZj.aom + (GameRuntime.kI() >> 1)) * 100) / FarmGameEngine.aYP, 255});
            chVarE.aoO = new aa(FarmGameEngine.aZf[85], chVarE.aol, chVarE.aom);
            chVarE.aoO.Y(0, 1);
            return chVarE;
        }
        FarmEntity[] chVarArr = new FarmEntity[1];
        a(1, 21, chVarArr, 1);
        chVarArr[0].aoO = new aa(FarmGameEngine.aZf[85], chVarArr[0].aol, chVarArr[0].aom);
        chVarArr[0].aoO.Y(0, 1);
        return chVarArr[0];
    }

    static FarmEntity fu(int i) {
        FarmEntity[] chVarArr = new FarmEntity[1];
        b(1, 50, 18, chVarArr, 1);
        if (chVarArr[0] != null) {
            aoc = chVarArr[0].aol;
            aod = chVarArr[0].aom;
        }
        FarmGameEngine.gX(30);
        if (i == -1) {
            aoa = -1;
        } else {
            aoa = 0;
            aob = i;
        }
        if (av(1, 23) <= 0) {
            FarmEntity chVarE = e(23, new int[]{(aoc * 100) / FarmGameEngine.aYP, (aod * 100) / FarmGameEngine.aYP, 255});
            chVarE.aoO = new aa(FarmGameEngine.aZf[13], chVarE.aol, chVarE.aom);
            chVarE.aoO.Y(1, 1);
            return chVarE;
        }
        FarmEntity[] chVarArr2 = new FarmEntity[1];
        a(1, 23, chVarArr2, 1);
        chVarArr2[0].aoO = new aa(FarmGameEngine.aZf[13], chVarArr2[0].aol, chVarArr2[0].aom);
        chVarArr2[0].aoO.Y(1, 1);
        return chVarArr2[0];
    }

    static FarmEntity fv(int i) {
        if (i == -1) {
            aoe = -1;
        } else {
            aoe = 0;
            aof = i;
        }
        if (FarmGameEngine.aZf[85] == null) {
            FarmGameEngine.s(0, 85, 15, 24);
            GameRuntime.bk("/9");
            FarmGameEngine.fP(0);
            GameRuntime.ke();
        }
        FarmGameEngine.q(11, false);
        aog = true;
        if (av(1, 24) <= 0) {
            FarmEntity chVarE = e(24, new int[]{((FarmGameEngine.aZj.aol + (GameRuntime.kH() >> 1)) * 100) / FarmGameEngine.aYP, ((FarmGameEngine.aZj.aom + (GameRuntime.kI() >> 1)) * 100) / FarmGameEngine.aYP, 255});
            chVarE.aoO = new aa(FarmGameEngine.aZf[85], chVarE.aol, chVarE.aom);
            chVarE.aoO.Y(0, 1);
            return chVarE;
        }
        FarmEntity[] chVarArr = new FarmEntity[1];
        a(1, 24, chVarArr, 1);
        chVarArr[0].aoO = new aa(FarmGameEngine.aZf[85], chVarArr[0].aol, chVarArr[0].aom);
        chVarArr[0].aoO.Y(0, 1);
        return chVarArr[0];
    }

    static void o(int i, int i2, int i3, int i4) {
        if (aph >= 500) {
            oK();
        }
        apg[aph][0] = i;
        apg[aph][1] = i2;
        apg[aph][2] = i3;
        apg[aph][3] = i4;
        aph++;
    }

    static void oI() {
        for (int i = 0; i < 6; i++) {
            eW(i);
        }
    }

    static void oJ() {
        for (int i = 0; i < 6; i++) {
            eX(i);
        }
        FarmGameEngine.aZz = false;
        FarmGameEngine.aZA = false;
        oK();
    }

    static void oK() {
        aph = 0;
    }

    static void oL() {
        for (int i = 0; i < 6; i++) {
            eY(i);
        }
    }

    static void oM() {
        FarmGameEngine.bbs = false;
        FarmEntity chVar = als[3];
        while (chVar != null) {
            FarmEntity chVar2 = chVar.aoi;
            if (chVar.Kx == 47 && chVar.anX) {
                chVar.anX = false;
                if (chVar != null && chVar.anG == 37) {
                    String str = "extra info:" + chVar.anM;
                    FarmGameEngine.hX(chVar.anM);
                } else if (chVar.anG == 36) {
                    FarmGameEngine.hS(chVar.anM);
                } else if (chVar.anG == 6) {
                    FarmGameEngine.hT(chVar.anM);
                } else if (chVar.anG == 59) {
                    int i = chVar.anM & 65535;
                    int i2 = (chVar.anM & (-65536)) >> 16;
                    String str2 = "item index:" + i + ",qty:" + i2;
                    if (i == 110) {
                        FarmGameEngine.hV(i2);
                    } else if (i == 5) {
                        FarmGameEngine.hU(i2);
                    } else if ((i < 111 || i > 116) && ((i >= 1 && i <= 26) || i == 119)) {
                        String str3 = "add crop, tree,:" + i + ",qty:" + i2;
                        FarmGameEngine.O(0, i, i2);
                    }
                } else if (chVar.anG == 67) {
                    FarmGameEngine.hg(chVar.anM);
                    FarmGameEngine.bW(1, 121);
                }
                chVar.oN();
                FarmGameEngine.bbs = true;
            }
            chVar = chVar2;
        }
    }

    static FarmEntity oP() {
        FarmEntity chVar;
        FarmEntity chVar2;
        FarmEntity chVar3;
        FarmEntity chVar4;
        FarmEntity chVar5;
        FarmEntity chVar6;
        FarmEntity chVar7;
        FarmEntity chVar8;
        if (als != null) {
            chVar = null;
            chVar2 = null;
            chVar3 = null;
            chVar4 = null;
            chVar5 = null;
            chVar6 = null;
            chVar7 = null;
            chVar8 = null;
            for (FarmEntity chVar9 = als[1]; chVar9 != null; chVar9 = chVar9.aoi) {
                if (chVar9.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (chVar9.qj() || chVar9.qi())) {
                    if (chVar9.Kx == 54) {
                        if (chVar9.ano != 0) {
                            chVar5 = chVar9;
                        } else if (chVar9.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                            chVar8 = chVar9;
                        } else if (chVar9.anw) {
                            chVar7 = chVar9;
                        }
                        if (chVar9.fz(GLURLConnection.BUFFER_SIZE) || chVar9.fz(4) || chVar9.fz(16) || chVar9.fz(256)) {
                            chVar4 = chVar9;
                            chVar6 = chVar9;
                        } else {
                            chVar4 = chVar9;
                        }
                    } else if (chVar9.Kx == 49 || chVar9.Kx == 52 || chVar9.Kx == 69) {
                        if (chVar2 == null) {
                            boolean z = chVar9.Kx == 49 && chVar9.Bj == 5;
                            boolean z2 = (chVar9.Kx == 52 || chVar9.Kx == 69) && chVar9.Bj == 3;
                            if (chVar9.ano != 0) {
                                chVar5 = chVar9;
                            } else if (z || z2) {
                                chVar2 = chVar9;
                            } else {
                                chVar3 = chVar9;
                            }
                        }
                    } else if (chVar9.ano != 0) {
                        chVar = chVar9;
                        chVar5 = chVar9;
                    } else {
                        chVar = chVar9;
                    }
                }
            }
        } else {
            chVar = null;
            chVar2 = null;
            chVar3 = null;
            chVar4 = null;
            chVar5 = null;
            chVar6 = null;
            chVar7 = null;
            chVar8 = null;
        }
        String str = "actor pick. move state;" + FarmGameEngine.avD + ",obj:" + FarmGameEngine.avE;
        if (FarmGameEngine.avD != -1 && FarmGameEngine.avE != null) {
            if (FarmGameEngine.avE == chVar2) {
                return chVar2;
            }
            if (FarmGameEngine.avE == chVar4) {
                return chVar4;
            }
            if (FarmGameEngine.avE == chVar3) {
                return chVar3;
            }
            if (FarmGameEngine.avE == chVar) {
                return chVar;
            }
        }
        if (chVar5 != null) {
            return chVar5;
        }
        if (chVar8 != null) {
            return chVar8;
        }
        if (chVar7 != null) {
            return chVar7;
        }
        if (chVar6 != null) {
            return chVar6;
        }
        if (chVar2 != null) {
            return chVar2;
        }
        if (chVar4 != null) {
            return chVar4;
        }
        return chVar3 != null ? chVar3 : chVar;
    }

    static int p(int i, int i2, int i3, int i4) {
        int i5 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && !chVar.fz(32) && !chVar.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) && chVar.aoC >= i3 && chVar.aoC <= i4) {
                i5++;
            }
        }
        return i5;
    }

    static void pj() {
        int i = FarmGameEngine.aZB == 0 ? FarmGameEngine.aZD : FarmGameEngine.aNG;
        for (FarmEntity chVar = als[1]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 50) {
                switch (chVar.aoC) {
                    case 0:
                        String str = "collection. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 2) {
                            chVar.fy(8);
                        } else {
                            chVar.fx(8);
                        }
                        break;
                    case 1:
                        String str2 = "gallery. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 4) {
                            chVar.aoG = 128;
                            chVar.aoH = 1;
                        } else {
                            chVar.aoG = 130;
                            chVar.aoH = 0;
                        }
                        break;
                    case 2:
                        String str3 = "kitchen. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 1) {
                            chVar.fy(8);
                        } else {
                            chVar.fx(8);
                        }
                        break;
                    case 3:
                        String str4 = "trophy. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 3) {
                            chVar.fy(8);
                        } else {
                            chVar.fx(8);
                        }
                        break;
                    case 4:
                        String str5 = "lib. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 5) {
                            chVar.fy(8);
                        } else {
                            chVar.fx(8);
                        }
                        break;
                    case 5:
                        String str6 = "green. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 6) {
                            chVar.fy(8);
                        } else {
                            chVar.fx(8);
                        }
                        break;
                    case 6:
                        String str7 = "pantry. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 7) {
                            chVar.fy(8);
                        } else {
                            chVar.fx(8);
                        }
                        break;
                    case 7:
                        String str8 = "storage. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 8) {
                            chVar.aoG = 128;
                            chVar.aoH = 2;
                        } else {
                            chVar.aoG = 136;
                            chVar.aoH = 0;
                        }
                        break;
                    case 8:
                        String str9 = "attic. pos;" + chVar.aol + "," + chVar.aom;
                        chVar.qr();
                        if (i < 9) {
                            chVar.fy(8);
                        } else {
                            chVar.fx(8);
                        }
                        break;
                    case 29:
                        chVar.qr();
                        chVar.aoG = 185;
                        chVar.aoH = 0;
                        as(true);
                        break;
                }
            }
        }
    }

    public static boolean pk() {
        return amc;
    }

    static int q(int i, int i2, int i3, int i4) {
        int i5 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && !chVar.fz(32) && !chVar.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) && ((chVar.aoC >= i3 && chVar.aoC <= i4) || chVar.aoC == i4 - 2)) {
                i5++;
            }
        }
        return i5;
    }

    static int x(int i, int i2, int i3) {
        int i4 = 0;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(4096) && chVar.fz(i3)) {
                i4++;
            }
        }
        return i4;
    }

    int A(int i, int i2, int i3) {
        return FarmGameEngine.gf(i2);
    }

    void a(int i, int i2, int i3, int i4, boolean z, boolean z2, int i5) {
        this.anX = false;
        b(i, i2, i3, i4, z, z2, i5);
    }

    void a(int i, int i2, int i3, int i4, boolean z, boolean z2, int i5, int i6) {
        this.anW = i6;
        b(i, i2, i3, i4, z, z2, i5);
    }

    boolean a(a aVar, int i) {
        int iKH;
        int iKI;
        if (aVar == null || i <= -1) {
            return false;
        }
        int iBA = aVar.bA(i) + this.aot;
        int iBB = aVar.bB(i) + this.aou;
        int iBy = iBA + aVar.by(i);
        int iBz = iBB + aVar.bz(i);
        if ((FarmGameEngine.kH() * 100.0f) / FarmGameEngine.aYP > 0.0f) {
            iKH = (int) (((FarmGameEngine.kH() * 100.0f) / FarmGameEngine.aYP) + 0.5f);
        } else {
            iKH = (((float) FarmGameEngine.kH()) * 100.0f) / ((float) FarmGameEngine.aYP) < 0.0f ? (int) (((FarmGameEngine.kH() * 100.0f) / FarmGameEngine.aYP) - 0.5f) : 0;
        }
        int i2 = 0 + iKH;
        if ((FarmGameEngine.kI() * 100.0f) / FarmGameEngine.aYP > 0.0f) {
            iKI = (int) (((FarmGameEngine.kI() * 100.0f) / FarmGameEngine.aYP) + 0.5f);
        } else {
            iKI = (((float) FarmGameEngine.kI()) * 100.0f) / ((float) FarmGameEngine.aYP) < 0.0f ? (int) (((FarmGameEngine.kI() * 100.0f) / FarmGameEngine.aYP) - 0.5f) : 0;
        }
        return iBy > 0 && iBA < i2 && iBz > 0 && iBB < iKI + 0;
    }

    boolean a(FarmEntity chVar, FarmEntity chVar2, int[] iArr) {
        int i = chVar.aol < chVar2.aol ? 72 : -72;
        int i2 = chVar.aom < chVar2.aol ? 36 : -36;
        iArr[0] = chVar2.aol;
        iArr[1] = chVar2.aom;
        for (int i3 = 1; i3 >= -1; i3--) {
            int i4 = i * i3;
            for (int i5 = 1; i5 >= -1; i5--) {
                if (i3 != 0 || i5 != 0) {
                    int i6 = i2 * i5;
                    chVar2.aol = chVar.aol + i4;
                    chVar2.aom = chVar.aom + i6;
                    boolean z = !FarmGameEngine.t(chVar2);
                    chVar2.aol = iArr[0];
                    chVar2.aom = iArr[1];
                    if (z) {
                        iArr[0] = chVar.aol + i4;
                        iArr[1] = chVar.aom + i6;
                        return true;
                    }
                }
            }
        }
        if (chVar.Kx != 54 || chVar2.anj || chVar2.ank) {
            return false;
        }
        iArr[0] = chVar.aol;
        iArr[1] = chVar.aom;
        return true;
    }

    boolean aF(int i, int i2) {
        int i3 = i & 32767;
        return a((32768 & i) != 0 ? FarmGameEngine.atf[i3] : FarmGameEngine.aZf[i3], i2);
    }

    void aG(int i, int i2) {
        int i3;
        boolean z = true;
        if (this.aoE > this.aol) {
            this.apc = false;
            if (this.aoF > this.aom) {
                this.apd = false;
                i3 = 0;
            } else {
                this.apd = true;
                i3 = 1;
            }
        } else {
            this.apc = true;
            if (this.aoF > this.aom) {
                this.apd = false;
                i3 = 0;
            } else {
                this.apd = true;
                i3 = 1;
            }
        }
        if ((this.apc || this.apd) && (!this.apc || !this.apd)) {
            z = false;
        }
        this.aoO.Y(i3, -1);
        if (z) {
            this.aoO.dU(2);
        } else {
            this.aoO.dU(0);
        }
    }

    boolean aH(int i, int i2) {
        return this.aol - i <= 90 && this.aol - i >= 45 && this.aom - i2 <= 120 && this.aom - i2 >= 90;
    }

    void aI(int i, int i2) {
        int[] iArr = new int[2];
        FarmGameEngine.c(new int[]{i, i2}, iArr);
        aJ(iArr[0], iArr[1]);
    }

    void aJ(int i, int i2) {
        this.aoE = i;
        this.aoF = i2;
        fg(1);
        aQ(this.aoE, this.aoF);
    }

    void aL(int i, int i2) {
        this.aol = i;
        this.aom = i2;
        aa.u(0, this.aol, this.aom);
    }

    void aM(int i, int i2) {
        this.aoE = i;
        this.aoF = i2;
        this.aoZ = null;
        this.apa = 0;
        this.apb = 0;
        String str = "Animal set target :" + this.aoW;
        fp(1);
    }

    void aN(int i, int i2) {
        this.aoE = i;
        this.aoF = i2;
        if (this.aoE != 0 || this.aoF != 0) {
            fp(1);
            aQ(this.aoE, this.aoF);
            return;
        }
        this.aom = 0;
        this.aol = 0;
        fp(0);
        fy(8);
        FarmGameEngine.axt = 0;
        FarmGameEngine.bbp = 0;
    }

    void aO(int i, int i2) {
        this.aoE = i;
        this.aoF = i2;
        String str = "[actor vehicle] target X:" + this.aoE + " /Y:" + this.aoF;
        fw(1);
    }

    void at(boolean z) {
        int i;
        int[] iArr = {0, 0, 14};
        b(47, 3, iArr).a(this.aol, this.aom, 37, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, false, this.amk, 0);
        if (FarmGameEngine.baf != null && FarmGameEngine.aZB == 0) {
            int i2 = z ? 116010 : 115424;
            if (this.aoC == 22 || this.aoC == 23) {
                i2 = 150886;
            }
            bb bbVar = new bb();
            bbVar.aiw = 0;
            bbVar.aix = 115443;
            bbVar.aiB = 116136;
            bbVar.aiC = i2;
            bbVar.aiE = this.amk;
            bbVar.aiF = FarmGameEngine.FC();
            FarmGameEngine.a(bbVar);
        }
        if (FarmGameEngine.aZB == 0 || ((FarmGameEngine.awV && FarmGameEngine.aYS != -1) || (FarmGameEngine.hF(5) < FarmGameEngine.Fw() && GameRuntime.L(0, 100) < FarmGameEngine.aZw[20]))) {
            FarmEntity chVarB = b(47, 3, iArr);
            int i3 = this.aoL;
            if (this.aoL == -1) {
                return;
            }
            if (z) {
                i = this.aoC == 23 ? 4 : 3;
            } else {
                i = 1;
            }
            if (fz(1024)) {
                i++;
            }
            if (z && FarmGameEngine.currentTutorialStepIndex == -1) {
                FarmGameEngine.N(this.aoU, i, 14);
            }
            if (FarmGameEngine.currentTutorialStepIndex == -1) {
                if (this.aoC == 23 || this.aoC == 22) {
                    String str = "m_resourceGenerated  " + this.aoL;
                    FarmGameEngine.aa(this.aoL + 4000, i, 14);
                } else {
                    FarmGameEngine.aa(this.aoU, i, 14);
                }
            }
            chVarB.a(this.aol, this.aom, 59, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, i3 | (i << 16), 0);
        }
        if (FarmGameEngine.FC() < FarmGameEngine.aLg[0] || GameRuntime.L(0, 100) >= FarmGameEngine.aZw[10]) {
            return;
        }
        b(47, 3, iArr).a(this.aol - 60, this.aom, 60, 2000, false, false, 1, 0);
    }

    void b(int i, int i2, int i3, int i4, boolean z, boolean z2, int i5) {
        this.anG = i3;
        this.anJ = i;
        this.anK = i2;
        this.aot = this.anJ;
        this.aou = this.anK;
        this.anI = i4;
        this.anL = 0;
        this.anH = 0;
        this.anN = z;
        this.anO = z2;
        this.anM = i5;
        if (i3 == 59) {
            int i6 = this.anM & 65535;
            String str = "itemIndex : " + i6;
            this.aoG = FarmGameEngine.aZM[i6] | Short.MIN_VALUE;
            this.aoH = FarmGameEngine.aZN[i6];
            String str2 = "sprite id :" + this.aoG + ",frame:" + this.aoH;
        } else if (i3 == 60) {
            int iIe = FarmGameEngine.Ie();
            int i7 = (iIe / 4) + 109;
            this.aoG = i7 | 32768;
            this.aoH = iIe % 4;
            String str3 = "Reward_Init sprite id :" + this.aoG + ",frame:" + this.aoH;
            FarmGameEngine.s(1, i7, 1, 24);
            GameRuntime.bk("/11");
            FarmGameEngine.fP(1);
            GameRuntime.ke();
        } else {
            this.aoG = 32769;
            this.aoH = this.anG;
        }
        this.anT = 0;
        this.anU = 0;
        this.anS = 0;
        this.anR = 4;
        this.anV = 1;
    }

    void b(FarmEntity chVar) {
        this.amD = j.bY(0).KW[chVar.aoC][25] * 60;
        this.amD = (this.amD * FarmGameEngine.md(this.aoU)) / 100;
        this.amD = (this.amD * 30) / 100;
        String str = "[Animal] prepare next care. dbid:" + chVar.aoC + ",time:" + this.amE;
    }

    void c(FarmEntity chVar) {
        this.aoG = chVar.aoG;
        this.aoH = chVar.aoH;
        this.ant = chVar.ant;
        this.aoC = chVar.aoC;
        this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aol, this.aom);
        if (chVar.fz(32)) {
            fx(32);
        }
    }

    void d(int i, int[] iArr) {
        ip();
        this.Kx = i;
        fx(8);
        switch (this.Kx) {
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                break;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoG = iArr[3];
                this.aoH = iArr[4];
                this.aoo = iArr[5];
                break;
            case 13:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.Bj = 0;
                pa();
                aa.u(0, this.aol, this.aom);
                fy(8);
                break;
            case 20:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoC = iArr[3];
                this.aoG = iArr[4];
                this.aoI = iArr[5];
                this.aoH = iArr[6];
                this.aoy = iArr[7];
                this.aoz = iArr[8];
                this.aoA = FarmGameEngine.bF(this.aoy, this.aoz);
                if (FarmGameEngine.aZf[this.aoG].hu() > 0) {
                    this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aol, this.aom);
                    this.aoO.Y(0, -1);
                    fx(33554432);
                } else {
                    fx(16777216);
                }
                fx(515);
                int i2 = iArr[9];
                int i3 = iArr[10];
                if (i2 == 1) {
                    fx(64);
                }
                if (i3 == 1) {
                    fx(128);
                }
                break;
            case 21:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                fx(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
                break;
            case 22:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoO = new aa(FarmGameEngine.aZf[144], this.aol, this.aom);
                this.aoO.Y(0, -1);
                fx(514);
                this.aoG = 144;
                this.aoH = 0;
                break;
            case 23:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                fx(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
                break;
            case 24:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                fx(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
                break;
            case 32:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aoE = this.aol;
                this.aoF = this.aom;
                this.aon = iArr[2];
                this.aoG = iArr[3];
                this.aoH = iArr[4];
                this.aoI = iArr[5];
                if (iArr[6] != -1) {
                    this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aol, this.aom);
                    this.aoO.Y(iArr[6], -1);
                }
                this.aoJ = this.aoG;
                this.aoK = iArr[7];
                this.aos = iArr[8];
                break;
            case 33:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoG = iArr[3];
                this.aoH = iArr[4];
                this.aow = iArr[5];
                this.aox = iArr[6];
                if (iArr.length > 7) {
                    this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aol, this.aom);
                    this.aoO.Y(iArr[7], iArr[8]);
                } else {
                    fx(16777216);
                }
                fx(AdRequest.MAX_CONTENT_URL_LENGTH);
                this.aoI = 0;
                break;
            case 34:
                this.aon = iArr[0];
                this.aoG = iArr[1];
                this.aoO = new aa(FarmGameEngine.aZf[this.aoG], 0, 0);
                this.aoO.Y(iArr[2], -1);
                this.Bj = iArr[3];
                switch (iArr[3]) {
                    case 0:
                        fs(iArr[4]);
                        break;
                    case 1:
                        pV();
                        this.aos = iArr[4];
                        break;
                }
                break;
            case 42:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoG = iArr[3];
                this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aol, this.aom);
                this.aoE = this.aol;
                this.aoF = this.aom;
                this.aos = 150;
                fx(33554432);
                fg(0);
                break;
            case 47:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoy = 2;
                this.aoz = 2;
                fx(AdRequest.MAX_CONTENT_URL_LENGTH);
                this.anX = true;
                break;
            case 48:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoG = iArr[3];
                this.aoH = iArr[4];
                this.aoI = iArr[5];
                fx(16777730);
                this.aoy = 4;
                this.aoz = 4;
                this.aoA = 16;
                break;
            case 49:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoy = 4;
                this.aoz = 4;
                this.aoA = 16;
                fx(64);
                fx(16777731);
                fj(1);
                break;
            case 50:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoC = iArr[3];
                this.aoG = iArr[4];
                this.aoH = iArr[5];
                this.aoy = iArr[6];
                this.aoz = iArr[7];
                this.aoA = FarmGameEngine.bF(this.aoy, this.aoz);
                fx(16777730);
                int i4 = iArr[8];
                int i5 = iArr[9];
                int i6 = iArr[10];
                if (i4 == 1) {
                    fx(64);
                }
                if (i5 == 1) {
                    fx(128);
                }
                if (i6 == 1) {
                    fx(1);
                }
                if (this.aoC == 18) {
                    this.aoJ = 135;
                    this.aoK = 0;
                    FarmGameEngine.aZW = this.aol;
                    FarmGameEngine.aZX = this.aom;
                }
                break;
            case 51:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoG = iArr[4];
                a aVar = FarmGameEngine.aZf[this.aoG];
                this.aoH = 0;
                this.aoO = new aa(aVar, this.aol, this.aom);
                this.aoE = this.aol;
                this.aoF = this.aom;
                this.aos = iArr[6];
                fw(0);
                fx(33554432);
                break;
            case 52:
            case 69:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoy = 4;
                this.aoz = 4;
                this.aoA = 16;
                this.aoG = iArr[3];
                if (this.Kx == 69) {
                    this.aoy = 3;
                    this.aoz = 3;
                    this.aoA = 9;
                }
                fx(16777763);
                int i7 = iArr[4];
                int i8 = iArr[5];
                if (i7 == 1) {
                    fx(64);
                }
                if (i8 == 1) {
                    fx(128);
                }
                fk(2);
                break;
            case 54:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoy = iArr[3];
                this.aoz = iArr[4];
                this.aoA = 16;
                String str = "[Actor SET] type ANIMAL sprite id=" + iArr[5];
                a aVar2 = FarmGameEngine.aZf[iArr[5]];
                this.aoI = iArr[6];
                String str2 = "is kid animal:" + iArr[7];
                if (iArr[7] == 1) {
                    fx(32);
                }
                this.aoL = iArr[8];
                this.aoG = iArr[5];
                this.aoH = iArr[9];
                String str3 = "m_resourceGenerated " + this.aoL;
                this.aoO = new aa(aVar2, this.aol, this.aom);
                fp(0);
                fx(33554945);
                int i9 = iArr[10];
                int i10 = iArr[11];
                if (i9 == 1) {
                    fx(64);
                }
                if (i10 == 1) {
                    fx(128);
                }
                if (iArr[12] == 1) {
                    fx(Utility.DEFAULT_STREAM_BUFFER_SIZE);
                    String str4 = "[actor] Thief pos:" + this.aol + "," + this.aom;
                    String str5 = "[actor] Thief pos:" + this.aol + "," + this.aom;
                }
                if (iArr[13] == 6) {
                    fx(32768);
                }
                this.aos = 50;
                break;
            default:
                this.Kx = -1;
                break;
        }
        this.aoE = this.aol;
        this.aoF = this.aom;
        if (fz(16777216)) {
            qr();
        }
    }

    void d(FarmEntity chVar) {
        if (this != null) {
            aN(chVar.aol, chVar.aom);
        }
    }

    void e(FarmEntity chVar) {
        this.aoM = chVar;
        chVar.aoM = this;
    }

    void fg(int i) {
        int i2;
        int i3;
        int i4;
        int i5 = -1;
        boolean z = true;
        switch (i) {
            case 0:
                i2 = 2;
                break;
            case 1:
                if (this.aoE > this.aol) {
                    this.apc = false;
                    if (this.aoF > this.aom) {
                        this.apd = false;
                        i4 = 0;
                    } else {
                        this.apd = true;
                        i4 = 1;
                    }
                } else {
                    this.apc = true;
                    if (this.aoF > this.aom) {
                        this.apd = false;
                        i4 = 0;
                    } else {
                        this.apd = true;
                        i4 = 1;
                    }
                }
                FarmGameEngine.gX(53);
                i2 = i4;
                break;
            case 2:
                switch (FarmGameEngine.aCd[FarmGameEngine.aCf]) {
                    case 0:
                        i5 = 46;
                        i2 = 4;
                        break;
                    case 1:
                        i5 = 39;
                        i2 = 4;
                        break;
                    case 2:
                        if (FarmGameEngine.aCe[FarmGameEngine.aCf] != null && (FarmGameEngine.aCe[FarmGameEngine.aCf].aoC == 0 || FarmGameEngine.aCe[FarmGameEngine.aCf].aoC == 51 || FarmGameEngine.aCe[FarmGameEngine.aCf].aoC == 52)) {
                            i5 = 22;
                            i2 = 3;
                        } else {
                            // Original cond_c1 and cond_c5 both select (3, 40).
                            i5 = 40;
                            i2 = 3;
                        }
                        break;
                    case 3:
                        i5 = 35;
                        i2 = 6;
                        break;
                    case 4:
                        i2 = 4;
                        if (FarmGameEngine.axm) {
                            FarmGameEngine.axm = false;
                            i3 = -1;
                        } else if (FarmGameEngine.axn) {
                            FarmGameEngine.axn = false;
                            i3 = -1;
                        } else {
                            i3 = 35;
                        }
                        this.apd = false;
                        this.apc = false;
                        i5 = i3;
                        break;
                    case 5:
                        i2 = 3;
                        break;
                    case 6:
                        i5 = 22;
                        i2 = 3;
                        break;
                    default:
                        i5 = 53;
                        i2 = 2;
                        break;
                }
                FarmGameEngine.gX(i5);
                i5 = 1;
                break;
            case 3:
                i2 = 7;
                i5 = 3;
                break;
            default:
                i2 = 0;
                break;
        }
        this.Bj = i;
        if ((this.apc || this.apd) && (!this.apc || !this.apd)) {
            z = false;
        }
        this.aoO.Y(i2, i5);
        if (z) {
            this.aoO.dU(2);
        } else {
            this.aoO.dU(0);
        }
    }

    void fh(int i) {
        String str = "CameraResetZoomLevel." + i;
        if (FarmGameEngine.aYP != i) {
            FarmGameEngine.aYP = i;
            int iKH = GameRuntime.kH() >> 1;
            int iKI = GameRuntime.kI() >> 1;
            int i2 = (((FarmGameEngine.aZj.aol - ((FarmGameEngine.aYP * iKH) / 100)) + iKH) * 100) / FarmGameEngine.aYP;
            int i3 = (((FarmGameEngine.aZj.aom - ((FarmGameEngine.aYP * iKI) / 100)) + iKI) * 100) / FarmGameEngine.aYP;
            FarmGameEngine.aZj.aol = (((i2 + iKH) * FarmGameEngine.aYP) / 100) - iKH;
            FarmGameEngine.aZj.aom = (((i3 + iKI) * FarmGameEngine.aYP) / 100) - iKI;
        }
    }

    void fi(int i) {
        String str = "Crop_PrepareSeed seedType : " + i;
        if (this.amn) {
            return;
        }
        this.amn = true;
        this.aoC = (char) i;
        this.aoL = FarmGameEngine.avT;
        this.amk = FarmGameEngine.awd;
        this.amf = FarmGameEngine.avV;
        this.amg = FarmGameEngine.avW;
        String str2 = "Crop_PrepareSeed m_cropCostCash : " + this.amg;
        this.aoC = FarmGameEngine.avS;
        this.aoU = FarmGameEngine.avZ;
        this.aoV = FarmGameEngine.awa;
        this.aoG = FarmGameEngine.awb;
        this.aml = FarmGameEngine.awe;
        this.ame = FarmGameEngine.ih((FarmGameEngine.avU * FarmGameEngine.lZ(this.aoU)) / 100);
    }

    void fj(int i) {
        qr();
        this.Bj = i;
        switch (this.Bj) {
            case 1:
                this.aoG = -1;
                this.aoH = -1;
                this.aoC = 0;
                this.amm = false;
                this.amn = false;
                this.amo = false;
                this.aon = 11;
                break;
            case 3:
                this.aoH = 0;
                this.amd = FarmGameEngine.MD();
                this.aon = 11;
                qq();
                break;
            case 4:
                this.aoH = 1;
                this.aon = 12;
                break;
            case 5:
                this.aoH = 2;
                this.aon = 12;
                break;
            case 8:
                this.aon = 11;
                break;
        }
        this.aoJ = 6;
        this.aoK = 0;
    }

    void fk(int i) {
        qr();
        String str = "Set tree state " + i;
        this.Bj = i;
        qq();
        switch (this.Bj) {
            case 2:
                this.amq = FarmGameEngine.MD();
                this.aoH = 0;
                break;
            case 3:
                fy(32);
                this.aoH = 1;
                if (this.aoC == 22 || this.aoC == 23) {
                    this.aoR = new aa(FarmGameEngine.aZf[187], this.aot, this.aou);
                    this.aoR.Y(1, -1);
                    this.aoQ = 0;
                }
                break;
            case 4:
                if (!this.amu) {
                    this.amu = true;
                    this.amr = FarmGameEngine.ih((int) (((long) FarmGameEngine.ii(this.amr)) - ((FarmGameEngine.MD() - this.amq) / 1000)));
                }
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 22, -63, -117, 0, 1}));
                break;
            case 5:
                this.aoH = 1;
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 12, -63, -117, 0, 1}));
                break;
            case 6:
                FarmGameEngine.gX(31);
                FarmGameEngine.axn = true;
                break;
        }
    }

    void fl(int i) {
        this.anx = FarmGameEngine.w(i);
        this.any = this.anx;
    }

    void fm(int i) {
        this.anz = FarmGameEngine.w(i);
        this.anA = this.anz;
    }

    void fn(int i) {
        this.anB = FarmGameEngine.w(i);
        this.anC = this.anB;
    }

    void fo(int i) {
        this.ano = i;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0097  */
    void fq(int i) {
        int i2 = 16;
        String str = "[actor ANIMAL] request feed bubble. type:" + i;
        if (FarmGameEngine.aZB == 0 || i != 4) {
            if (FarmGameEngine.aZB <= 0 || FarmGameEngine.aZB > 10 || !this.anw) {
                pT();
                fx(i);
                if (i == 4) {
                    if (this.anw) {
                        i2 = 19;
                    } else if (this.anj) {
                        i2 = 15;
                    } else if (!this.ank) {
                        i2 = 14;
                    }
                } else if (i == 16) {
                    i2 = 13;
                } else if (i == 256) {
                    i2 = 12;
                } else if (i == 16384) {
                    if (this.anp == 1) {
                        i2 = 15;
                    } else if (this.anp != 2) {
                        i2 = 0;
                    }
                } else if (i == 65536) {
                    i2 = 20;
                } else if (i == 131072) {
                    i2 = 21;
                } else {
                    i2 = 0;
                }
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, i2, -63, -117, 0, 1}));
            }
        }
    }

    void fs(int i) {
        FarmEntity[] chVarArr = new FarmEntity[100];
        int iA = a(1, i, chVarArr, 100);
        if (iA > 0) {
            int iL = GameRuntime.L(0, iA);
            this.aol = chVarArr[iL].aol;
            this.aom = chVarArr[iL].aom;
            this.aoE = this.aol;
            this.aoF = this.aom;
            String str = "Boid find his home at " + this.aol + " " + this.aom;
        }
    }

    void fx(int i) {
        if ((i & 8) != 0) {
            qr();
        }
        this.HL |= i;
    }

    void fy(int i) {
        if ((i & 8) != 0) {
            qr();
        }
        this.HL &= i ^ (-1);
    }

    boolean fz(int i) {
        return (this.HL & i) == i;
    }

    void ip() {
        this.aoi = null;
        this.aoj = null;
        this.aoN = null;
        this.aoO = null;
        this.aoR = null;
        this.aok = -1;
        this.Kx = 0;
        this.aol = 0;
        this.aom = 0;
        this.aon = 0;
        this.aop = 0;
        this.aoq = 0;
        this.aor = 0;
        this.aot = 0;
        this.aou = 0;
        this.aov = 0;
        this.aoo = -1;
        this.aoP = 0;
        this.aoQ = 0;
        this.aoD = -1;
        this.HL = 0;
        this.aoG = -1;
        this.aoH = -1;
        this.aoI = 0;
        this.aoJ = -1;
        this.aoT = false;
        this.aoy = 1;
        this.aoz = 1;
        this.aoY = (byte) 0;
        this.aow = 0;
        this.aox = 0;
    }

    void oN() {
        if (fz(16777216)) {
            qr();
        }
        if (this.aoj != null) {
            this.aoj.aoi = this.aoi;
        } else if (this.aok >= 0) {
            als[this.aok] = this.aoi;
        }
        if (this.aoi != null) {
            this.aoi.aoj = this.aoj;
        }
        this.aok = -1;
        this.aoi = null;
        this.aoj = null;
    }

    boolean oO() {
        return this.aok == -1;
    }

    void oQ() {
        if (!oT() && this.aoJ > -1 && fz(8)) {
            aE(this.aoJ, this.aoK);
        }
    }

    boolean oT() {
        if (FarmGameEngine.aYP == 100) {
            return (this.aot < -144 || this.aot > FarmGameEngine.kH() + 144 || this.aou < -288 || this.aou > FarmGameEngine.kI() + 288) && this.aoo == -1;
        }
        return (this.aot < -36 || this.aot > (FarmGameEngine.kH() << 1) + 36 || this.aou < -36 || this.aou > (FarmGameEngine.kI() << 1) + 36) && this.aoo == -1;
    }

    void oV() {
        int i = 0;
        if (this.aoo == 2) {
            this.aot = 0;
            this.aou = ((((this.aom + this.aox) - 215) - FarmGameEngine.aZj.aom) * FarmGameEngine.aYP) / 100;
            return;
        }
        if (this.aoo != 1) {
            this.aot = (this.aol + this.aow) - FarmGameEngine.aZj.aol;
            this.aou = (this.aom + this.aox) - FarmGameEngine.aZj.aom;
            return;
        }
        float f = (648 - FarmGameEngine.aZj.aom) / 648.0f;
        this.aot = this.aol - (FarmGameEngine.aZj.aol >> 4);
        if (this.aom - (f * 40.0f) > 0.0f) {
            i = (int) ((this.aom - (f * 40.0f)) + 0.5f);
        } else if (this.aom - (f * 40.0f) < 0.0f) {
            i = (int) ((this.aom - (f * 40.0f)) - 0.5f);
        }
        this.aou = i - FarmGameEngine.aZj.aom;
    }

    int oW() {
        return (this.Kx == 50 && (this.aoC == 18 || this.aoC == 19)) ? ((this.aon << 20) + this.aom) - 1 : (this.aon << 20) + this.aom;
    }

    void pA() {
        qr();
        FarmGameEngine.o(this);
        j jVarBY = j.bY(11);
        int i = jVarBY.KW[this.aoC][3];
        int i2 = jVarBY.KW[this.aoC][4];
        int i3 = jVarBY.KW[this.aoC][5];
        int i4 = jVarBY.KW[this.aoC][6];
        int[] iArr = {0, 0, 14};
        if (i > 0) {
            b(47, 3, iArr).b(this.aol, this.aom, 37, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, false, i);
        }
        if (i2 > 0) {
            b(47, 3, iArr).b(this.aol, this.aom, 36, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, i2);
        }
        int iL = 0;
        int iFB = 0;
        if (i4 > 0) {
            iL = GameRuntime.L(i3, i4 + 1);
            if (iL > 0) {
                b(47, 3, iArr).b(this.aol, this.aom, 6, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, iL);
                iFB = FarmGameEngine.FB() + iL;
                String str = "cash_balance ==========" + iFB;
            }
        } else {
            iFB = FarmGameEngine.FB();
        }
        String str2 = "Player_GetCash ==========" + FarmGameEngine.FB();
        String str3 = "cash_balance ==========" + iFB;
        if (FarmGameEngine.baf != null && FarmGameEngine.aZB == 0) {
            FarmGameEngine.a(115443, new String[]{"INT:" + Integer.toString(iFB), "INT:" + Integer.toString(iL), "INT:" + Integer.toString(i2), "INT:" + Integer.toString(119043), "INT:" + Integer.toString(115433), "N/A", "N/A", "INT:" + Integer.toString(i), "INT:" + Integer.toString(FarmGameEngine.FC())});
        }
        oN();
    }

    int pB() {
        long jX = FarmGameEngine.x(this.anx);
        long jX2 = FarmGameEngine.x(this.any);
        return jX != jX2 ? (int) jX2 : (int) jX;
    }

    int pC() {
        long jX = FarmGameEngine.x(this.anz);
        long jX2 = FarmGameEngine.x(this.anA);
        return jX != jX2 ? (int) jX2 : (int) jX;
    }

    int pD() {
        long jX = FarmGameEngine.x(this.anB);
        long jX2 = FarmGameEngine.x(this.anC);
        return jX != jX2 ? (int) jX2 : (int) jX;
    }

    void pE() {
        if (fz(32)) {
            if (FarmGameEngine.ii(this.amI) > 0) {
                this.amI = FarmGameEngine.ih((int) (((long) FarmGameEngine.ii(this.amI)) - ((FarmGameEngine.MD() / 1000) - this.amJ)));
            }
        } else if (FarmGameEngine.ii(this.amE) != 0) {
            long jMD = (FarmGameEngine.MD() - this.amB) / 1000;
            this.amE = FarmGameEngine.ih((int) (((long) FarmGameEngine.ii(this.amE)) - jMD));
            String str = jMD + " ----------------elapsedTime------m_nextAnimalCare--------- " + this.amE;
        }
        fp(9);
    }

    void pF() {
        FarmEntity chVar;
        if (this.anj || this.ank) {
            FarmEntity chVarFl = FarmGameEngine.Fl();
            if (chVarFl == null) {
                FarmEntity[] chVarArr = new FarmEntity[1];
                b(1, 69, 22, chVarArr, 1);
                if (chVarArr[0] == null) {
                    FarmEntity[] chVarArr2 = new FarmEntity[1];
                    b(1, 69, 23, chVarArr2, 1);
                    chVar = chVarArr2[0];
                } else {
                    chVar = chVarArr[0];
                }
            } else {
                chVar = chVarFl;
            }
        } else {
            chVar = null;
        }
        if (chVar == null) {
            this.ani = true;
            return;
        }
        if (FarmGameEngine.axt == 0) {
            FarmGameEngine.axt = this.anj ? 1 : 2;
            this.ani = true;
            if (chVar.Kx != 69) {
                chVar.anq = true;
            }
            fx(8);
            FarmGameEngine.bbp = 0;
            if (chVar.Kx != 69) {
                b(chVar);
            }
            if (this.anj) {
                if (this.anl) {
                    FarmGameEngine.gX(69);
                }
                if (!FarmGameEngine.ha(1)) {
                    FarmGameEngine.q(62, false);
                }
                FarmGameEngine.f(true, true);
                if (FarmGameEngine.auj) {
                    FarmGameEngine.aNa = FarmGameEngine.MD();
                }
                if (!FarmGameEngine.auj || !FarmGameEngine.lN(299)) {
                    int[] iArr = new int[2];
                    int[] iArr2 = {this.aol, this.aom};
                    boolean zA = FarmGameEngine.a(this, iArr);
                    this.aol = iArr2[0];
                    this.aom = iArr2[1];
                    if (zA) {
                        if (FarmGameEngine.awV && iArr[0] == 1782 && iArr[1] == 711) {
                            iArr[0] = iArr[0] - 10;
                            iArr[1] = iArr[1] - 10;
                        }
                        aN(iArr[0], iArr[1]);
                    } else if (a(chVar, this, iArr)) {
                        aN(iArr[0], iArr[1]);
                    } else {
                        z(chVar.aol, chVar.aom, 36);
                    }
                } else if (FarmGameEngine.aMY != null) {
                    aN(FarmGameEngine.aMY[0], FarmGameEngine.aMY[1]);
                } else {
                    z(chVar.aol, chVar.aom, 36);
                }
                this.anl = false;
            } else {
                if (this.anm) {
                    FarmGameEngine.gX(71);
                }
                if (!FarmGameEngine.ha(1)) {
                    FarmGameEngine.q(62, false);
                }
                FarmGameEngine.f(true, false);
                if (FarmGameEngine.auj) {
                    FarmGameEngine.aNa = FarmGameEngine.MD();
                }
                int[] iArr3 = new int[2];
                int[] iArr4 = {this.aol, this.aom};
                boolean zA2 = FarmGameEngine.a(this, iArr3);
                this.aol = iArr4[0];
                this.aom = iArr4[1];
                if (zA2 || a(chVar, this, iArr3)) {
                    aN(iArr3[0], iArr3[1]);
                } else {
                    z(chVar.aol, chVar.aom, 36);
                }
                this.anm = false;
            }
            au(true);
        }
    }

    void pJ() {
        FarmGameEngine.bad = true;
        int iL = GameRuntime.L(-4, 4);
        int iL2 = GameRuntime.L(-4, 4);
        int[] iArr = new int[2];
        int[] iArr2 = {this.aol, this.aom};
        FarmGameEngine.f(iArr2, iArr);
        int i = 0;
        do {
            iArr[0] = iArr[0] + iL;
            iArr[1] = iArr[1] + iL2;
            if (this.anw && FarmGameEngine.axE) {
                if (((FarmGameEngine.a(this, iArr[0], iArr[1]) || FarmGameEngine.C(iArr[0], iArr[1], this.aoy, this.aoz)) ? false : true) || i >= 50) {
                    FarmGameEngine.axE = false;
                } else {
                    i++;
                    iArr[0] = iArr[0] - iL;
                    iArr[1] = iArr[1] - iL2;
                    iL = GameRuntime.L(-4, 4);
                    iL2 = GameRuntime.L(-4, 4);
                }
            }
            if (!FarmGameEngine.axE) {
                break;
            }
        } while (this.anw);
        if (FarmGameEngine.currentTutorialStepIndex != -1) {
            fp(0);
            return;
        }
        if (!((FarmGameEngine.C(iArr[0], iArr[1], this.aoy, this.aoz) || FarmGameEngine.a(this, iArr[0], iArr[1]) || ((FarmGameEngine.r(this) != 0 || FarmGameEngine.bz(iArr[0], iArr[1])) && FarmGameEngine.r(this) <= 0)) ? false : true)) {
            String str = "no move, collide test fail: " + this.aoW;
            fp(0);
        } else {
            String str2 = "Target collide test OK: " + this.aoW;
            FarmGameEngine.g(iArr, iArr2);
            aQ(iArr2[0], iArr2[1]);
        }
    }

    boolean pK() {
        if ((!this.anw || FarmGameEngine.aZB == 0) && fz(8) && FarmGameEngine.awX != 1) {
            String str = "step animal :" + this.Bj;
            if (this.Bj != 3 && this.Bj != 7 && this.Bj != 13) {
                if (fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) && FarmGameEngine.axt != 0) {
                    String strDt = FarmGameEngine.dt(12327);
                    if (strDt == null) {
                        strDt = "";
                    }
                    FarmGameEngine.m(strDt, 7, 0);
                    return false;
                }
                if (this.Bj == 15) {
                    FarmGameEngine.he(FarmGameEngine.awk);
                    return true;
                }
                String str2 = "[actor Animal] Animal_Click. flag. need care." + fz(4) + ",flag ready produce:" + fz(16) + ",need clean:" + fz(256);
                if (!this.anw && !this.anj && !this.ank) {
                    FarmGameEngine.gX(this.aoY);
                }
                if (this.anw && fz(32)) {
                    FarmGameEngine.gX(70);
                }
                if (FarmGameEngine.axY == this) {
                    FarmGameEngine.Et();
                }
                if (!fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                    if (fz(131072)) {
                        fp(15);
                        FarmGameEngine.c(true, 2, 2);
                        FarmGameEngine.aqY = this;
                        FarmGameEngine.aV(true);
                        return true;
                    }
                    if (fz(4)) {
                        String str3 = "Animal need care flag are active. feed:" + FarmGameEngine.hZ(this.amS) + ",cat:" + ((int) this.amS) + ",consume:" + this.amK;
                        if (this.amK == 0 && this.anw) {
                            this.amK = 1;
                        }
                        if (FarmGameEngine.aMj[this.amS] < 0) {
                            FarmGameEngine.aMj[this.amS] = 0;
                        }
                        if (FarmGameEngine.aCg < 64 && !FarmGameEngine.b(4, this)) {
                            if ((FarmGameEngine.aCi & 16) == 16) {
                                if (FarmGameEngine.hZ(this.amS) - FarmGameEngine.aMj[this.amS] >= this.amK || (FarmGameEngine.aYS == 1045 && this.amS == 1)) {
                                    int[] iArr = FarmGameEngine.aMj;
                                    short s = this.amS;
                                    iArr[s] = iArr[s] + this.amK;
                                    FarmGameEngine.a(4, this);
                                } else if (!FarmGameEngine.awj) {
                                    FarmGameEngine.S(401, 7, 0);
                                }
                            }
                        }
                        return true;
                    }
                    if (fz(16)) {
                        if (FarmGameEngine.hF(5) + fe(3) < FarmGameEngine.Fw() || FarmGameEngine.aZB != 0) {
                            FarmGameEngine.a(7, this);
                            return true;
                        }
                        FarmGameEngine.H(481, 487, 7, 0);
                        return false;
                    }
                    if (fz(256)) {
                        FarmGameEngine.a(4, this);
                        FarmGameEngine.axm = true;
                        this.anh = 0;
                        return true;
                    }
                    if (this.anw && FarmGameEngine.aZB == 0 && ((this.Bj == 0 || this.Bj == 1) && !fz(32))) {
                        fp(15);
                        FarmGameEngine.c(true, 2, 2);
                        FarmGameEngine.aqY = this;
                        FarmGameEngine.aV(true);
                        return true;
                    }
                    fp(0);
                    if (!this.anw) {
                        FarmGameEngine.a(true, this);
                    }
                    if (!this.anw && !fz(32) && FarmGameEngine.ii(this.amE) == 0 && this.amB != 0 && FarmGameEngine.aZB == 0) {
                        fq(16);
                        FarmGameEngine.a(false, (FarmEntity) null);
                    }
                    return false;
                }
                if (this.aol != this.aoE || this.aom != this.aoF) {
                    return false;
                }
                FarmEntity chVarFm = FarmGameEngine.Fm();
                if (FarmGameEngine.aYS == 1022 || FarmGameEngine.aYS == 1049 || FarmGameEngine.aYS == 1056 || FarmGameEngine.aYS == 1063) {
                    FarmGameEngine.axW = true;
                } else if (FarmGameEngine.auj && FarmGameEngine.aZB <= 10 && FarmGameEngine.ii(FarmGameEngine.azx[FarmGameEngine.aZB - 1][5]) == 0) {
                    if ((chVarFm == null || chVarFm.Bj != 8) && (FarmGameEngine.aYS == -1 || FarmGameEngine.aYS == 0)) {
                        FarmGameEngine.S(12332, 7, 0);
                        return false;
                    }
                }
                if (chVarFm == null && FarmGameEngine.lN(GLURLConnection.HTTP_FOUND)) {
                    int iFv = FarmGameEngine.Fv();
                    if (iFv != -1) {
                        FarmGameEngine.hG(iFv);
                    }
                    chVarFm = FarmGameEngine.Fm();
                    if (chVarFm != null) {
                        FarmGameEngine.avE = chVarFm;
                        FarmGameEngine.DP();
                        FarmGameEngine.aD(false);
                        FarmGameEngine.avD = -1;
                        chVarFm.amQ = true;
                    }
                }
                if (chVarFm == null) {
                    FarmGameEngine.F(883, 885, 15, 0);
                } else {
                    if (chVarFm.Bj == 8) {
                        return false;
                    }
                    if (FarmGameEngine.b(chVarFm, this) || FarmGameEngine.auj || FarmGameEngine.lN(GLURLConnection.HTTP_FOUND)) {
                        if (FarmGameEngine.auj && FarmGameEngine.baH) {
                            FarmGameEngine.baH = false;
                            FarmGameEngine.jQ(-1);
                        }
                        chVarFm.ann = true;
                        FarmGameEngine.aCn = FarmGameEngine.MD();
                        chVarFm.d(this);
                    } else {
                        String str4 = FarmGameEngine.aMw;
                        String strDt2 = FarmGameEngine.dt(522);
                        if (strDt2 == null) {
                            strDt2 = "";
                        }
                        if (!str4.equals(strDt2)) {
                            String str5 = FarmGameEngine.aMw;
                            String strDt3 = FarmGameEngine.dt(476);
                            if (strDt3 == null) {
                                strDt3 = "";
                            }
                            if (!str5.equals(strDt3)) {
                                FarmGameEngine.aty = this;
                                String strDt4 = FarmGameEngine.dt(1001);
                                if (strDt4 == null) {
                                    strDt4 = "";
                                }
                                String strDt5 = FarmGameEngine.dt(1002);
                                if (strDt5 == null) {
                                    strDt5 = "";
                                }
                                FarmGameEngine.d(strDt4, strDt5, 15, 0);
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    boolean pL() {
        return (FarmGameEngine.awX == 1 || this.Bj == 3 || this.Bj == 7 || this.Bj == 13 || this.Bj == 15 || this.Bj == 8 || fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) || fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) || fz(GLURLConnection.BUFFER_SIZE) || fz(16) || fz(256)) ? false : true;
    }

    void pM() {
        this.amE = FarmGameEngine.ih(j.bY(0).KW[this.aoC][25] * 60);
        this.amE = FarmGameEngine.ih((FarmGameEngine.ii(this.amE) * FarmGameEngine.md(this.aoU)) / 100);
        String str = "[Animal] prepare next care. dbid:" + this.aoC + ",time:" + this.amE;
        this.amB = FarmGameEngine.MD();
        String str2 = "last anim care:" + ((int) this.amB);
    }

    void pN() {
        this.amI = FarmGameEngine.ih(j.bY(0).KW[this.aoC][14] * 60);
        this.amJ = FarmGameEngine.MD() / 1000;
    }

    void pO() {
        if (!fz(4)) {
            if (fz(256)) {
                fp(7);
                this.anb = true;
                FarmGameEngine.aa(this.aoU, 1, 20);
                fy(256);
                pT();
                this.aoR = new aa(FarmGameEngine.aZf[13], this.aot, this.aou);
                this.aoR.Y(0, 2);
                this.aoP = this.amZ;
                this.aoQ = this.ana;
                FarmGameEngine.gX(31);
                return;
            }
            return;
        }
        if (this.Bj == 3 || FarmGameEngine.aZB != 0) {
            if (FarmGameEngine.aZB == 0) {
                FarmGameEngine.gX(55);
                return;
            }
            fp(3);
            fy(4);
            pT();
            this.amB = FarmGameEngine.MD();
            this.amL = false;
            this.amO = false;
            this.amN = false;
            this.amM = false;
            this.amP = false;
            return;
        }
        if (FarmGameEngine.aYS == 1045) {
            FarmGameEngine.axW = true;
        }
        FarmGameEngine.bM(this.amK, this.amS);
        if (FarmGameEngine.aMj[this.amS] > 0) {
            int[] iArr = FarmGameEngine.aMj;
            short s = this.amS;
            iArr[s] = iArr[s] - this.amK;
        }
        fp(3);
        if (!(fz(32) && this.anw)) {
            this.amG++;
            String str = "[cActor_Animal] m_dataBaseID : " + this.aoC;
            String str2 = "m_animalTapCount : " + this.amG;
            String str3 = "m_animalTapCountMax : " + this.amF;
            if (this.amG >= this.amF && !fz(2048)) {
                fx(2048);
                FarmGameEngine.e(479, 480, 7, 0, this.aoG + Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.aoH, this.aoI);
                FarmGameEngine.lQ(0);
            }
            String str4 = "[ANIMAL] TAKE CARE. tapcount:" + this.amG + ",max:" + this.amF + ",uid:" + this.aoU;
        }
        FarmGameEngine.aa(this.aoU, 1, 19);
        fy(4);
        pT();
        this.amB = FarmGameEngine.MD();
        this.amL = false;
        this.amO = false;
        this.amN = false;
        this.amM = false;
        this.amP = false;
    }

    void pP() {
        fp(5);
        fy(16);
        pT();
        this.amB = FarmGameEngine.MD();
    }

    /* JADX WARN: Code duplicated, block: B:50:0x033b  */
    void pQ() {
        boolean z;
        boolean z2;
        int[] iArr = {0, 0, 14};
        b(47, 3, iArr).b(this.aol, this.aom, 37, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, false, 1);
        if (FarmGameEngine.baf != null && FarmGameEngine.aZB == 0) {
            int i = 115423;
            if (FarmGameEngine.gg(this.aoC)) {
                i = 119024;
            } else if (FarmGameEngine.gh(this.aoC)) {
                i = 150869;
            }
            FarmGameEngine.a(115443, new String[]{"INT:" + Integer.toString(FarmGameEngine.FB()), "INT:" + Integer.toString(0), "INT:" + Integer.toString(0), "INT:" + Integer.toString(116136), "INT:" + Integer.toString(i), "N/A", "N/A", "INT:" + Integer.toString(1), "INT:" + Integer.toString(FarmGameEngine.FC())});
        }
        j jVarBY = j.bY(0);
        int i2 = jVarBY.KW[this.aoC][18];
        int i3 = jVarBY.KW[this.aoC][19];
        int i4 = jVarBY.KW[this.aoC][20];
        int i5 = jVarBY.KW[this.aoC][21];
        int i6 = jVarBY.KW[this.aoC][22];
        int i7 = jVarBY.KW[this.aoC][23];
        this.amH = jVarBY.KW[this.aoC][8];
        if (this.amP) {
            if (i2 > 0) {
                i2++;
            }
            if (i5 > 0) {
                i5++;
            }
            this.amP = false;
        }
        int i8 = i5;
        if (FarmGameEngine.aZB == 0 || (FarmGameEngine.hF(5) < FarmGameEngine.Fw() && GameRuntime.L(0, 100) < FarmGameEngine.aZw[20])) {
            int iL = GameRuntime.L(0, 100);
            int i9 = 0;
            if (iL < i4) {
                this.aoL = i3;
                int i10 = i2 << 16;
                FarmEntity chVarB = b(47, 3, iArr);
                i9 = FarmGameEngine.aZB != 0 ? 65536 : i10;
                chVarB.b(this.aol, this.aom, 59, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, this.aoL | i9);
                z = true;
            } else {
                z = false;
            }
            if (iL >= i7) {
                z2 = z;
            } else if (FarmGameEngine.aZB == 0) {
                this.aoL = i6;
                b(47, 3, iArr).b(this.aol + 50, this.aom + 10, 59, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, (i8 << 16) | this.aoL);
                z2 = true;
            } else if (i9 == 0) {
                this.aoL = i6;
                b(47, 3, iArr).b(this.aol + 50, this.aom + 10, 59, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, 65536 | this.aoL);
                z2 = true;
            } else {
                z2 = z;
            }
            if (fz(2048) && this.amR > 0) {
                b(47, 3, iArr).b(this.aol + 65, this.aom, 36, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, this.amR);
                if (FarmGameEngine.baf != null && FarmGameEngine.aZB == 0) {
                    FarmGameEngine.a(115443, new String[]{"INT:" + Integer.toString(FarmGameEngine.FB()), "INT:" + Integer.toString(0), "INT:" + Integer.toString(this.amR), "INT:" + Integer.toString(116136), "INT:" + Integer.toString(115423), "N/A", "N/A", "INT:" + Integer.toString(1), "INT:" + Integer.toString(FarmGameEngine.FC())});
                }
            }
        } else {
            z2 = false;
        }
        if (z2) {
            FarmGameEngine.c(this.aoU, 1, 41, fz(2048));
        }
    }

    void pR() {
        if (fz(32)) {
            int i = this.aoU;
            FarmGameEngine.a(false, (FarmEntity) null);
            fy(32);
            this.aoC++;
            j jVarBY = j.bY(0);
            this.amK = jVarBY.KW[this.aoC][24];
            this.amF = jVarBY.KW[this.aoC][15];
            this.amR = jVarBY.KW[this.aoC][17];
            this.amG = 0;
            this.aoy = jVarBY.KW[this.aoC][27];
            this.aoz = jVarBY.KW[this.aoC][28];
            this.amH = jVarBY.KW[this.aoC][8];
            a aVar = FarmGameEngine.aZf[jVarBY.KW[this.aoC][31]];
            this.aoI = jVarBY.KW[this.aoC][32];
            this.aoH = jVarBY.KW[this.aoC][33];
            this.amV = (short) jVarBY.KW[this.aoC][29];
            this.amW = (short) jVarBY.KW[this.aoC][30];
            if (this.aoO != null && aVar != null) {
                String str = "animal evol. db:" + this.aoC + ".sprite" + aVar;
                this.aoO.a(aVar);
                this.aoO.Y(0, -1);
            }
            startGrowthEffect();
            if (FarmGameEngine.aZB == 0) {
                if (this.anw) {
                    FarmGameEngine.axj = i;
                } else {
                    FarmGameEngine.aa(i, 1, 21);
                }
            }
            this.aoU = jVarBY.KW[this.aoC][1];
            this.aoV = jVarBY.KW[this.aoC][2];
            if (FarmGameEngine.aZB == 0) {
                FarmGameEngine.lQ(this.aoU);
                FarmGameEngine.aX(true);
            }
            if (!this.anw) {
                pM();
                fp(2);
            }
            if (jVarBY.KW[this.aoC][19] != 5 && jVarBY.KW[this.aoC][19] != 110) {
                this.anf = jVarBY.KW[this.aoC][18];
            }
            if (jVarBY.KW[this.aoC][22] != 5 && jVarBY.KW[this.aoC][22] != 110) {
                this.ang = jVarBY.KW[this.aoC][21];
            }
            String str2 = "m_animalIncomeResourceQty1 : " + this.anf;
            String str3 = "m_animalIncomeResourceQty2 : " + this.ang;
            FarmGameEngine.gX(43);
            int iV = aVar.v(this.amV, 0);
            this.amX = aVar.bw(iV);
            this.amY = aVar.bx(iV);
            String str4 = "Food pile offset:" + this.amX + ",y:" + this.amY + ",marker:" + iV;
            int iV2 = aVar.v(this.amW, 0);
            this.amZ = aVar.bw(iV2);
            this.ana = aVar.bx(iV2);
            String str5 = "Clear fx offset:" + this.amZ + ",y:" + this.ana + ",marker:" + iV2;
            this.amT = false;
        }
    }

    private void startGrowthEffect() {
        this.aoR = new aa(FarmGameEngine.aZf[13], this.aot, this.aou - 50);
        this.aoR.Y(1, 1);
        tutorialGrowthEpoch = TutorialFlowController.captureCallback(FarmGameEngine.currentTutorialStepIndex, FarmGameEngine.aYS, FarmGameEngine.awV);
    }

    void restoreTutorialGrowthEffect() {
        if (FarmGameEngine.currentTutorialStepIndex != 18 || this.Kx != 54 || this.aoC != 17
                || this.anw || fz(32) || this.aoR != null) return;
        startGrowthEffect();
    }

    void pS() {
        if (fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
            this.anp = 0;
            this.amP = false;
            this.anq = false;
            fy(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
            pT();
            this.amB = FarmGameEngine.MD();
            this.amJ = FarmGameEngine.MD() / 1000;
            fp(0);
            this.anb = true;
            fx(64);
            fx(128);
        }
    }

    void pT() {
        qq();
    }

    void pU() {
        String str = "animal place. need care :" + fz(4) + ",link:" + this.aoM;
        if (fz(4) && this.aoM != null) {
            qq();
            this.aoM = null;
        }
        String str2 = " ready produce.:" + fz(16);
        if (fz(16) && this.aoM != null) {
            qq();
            this.aoM = null;
        }
        String str3 = " need clean:" + fz(256);
        if (!fz(256) || this.aoM == null) {
            return;
        }
        qq();
        this.aoM = null;
    }

    void pW() {
        if (this.Bj == 1) {
            qm();
            if (this.aol == this.aoE && this.aom == this.aoF) {
                pV();
                this.anE = false;
            }
        }
        int[] iArr = new int[2];
        FarmGameEngine.d(new int[]{this.aol, this.aom}, iArr);
        if (iArr[0] > 5 && iArr[0] < GameRuntime.kH() - 5 && iArr[1] > 5 && iArr[1] < GameRuntime.kI() - 5 && !this.anE) {
            this.anF = true;
        }
        if (!this.anE && qs() && this.aoC == 2 && this.anF) {
            FarmGameEngine.gX(21);
            this.anE = true;
            this.anF = false;
        }
    }

    void pX() {
        if (fz(8)) {
            FarmGameEngine.axd = true;
            if (!this.anN) {
                this.anT += GameRuntime.Qy;
                this.anU += GameRuntime.Qy;
                int i = this.anT * 2;
                int i2 = this.anO ? 1 : -1;
                if (this.anV < 3) {
                    this.anP = (i2 * (this.anR * this.anU)) / 100;
                    this.anQ = (((i * 4) * i) / 10000) + ((i * (-40)) / 100);
                    for (int i3 = 1; i3 < this.anV; i3++) {
                        this.anQ = (this.anQ * 40) / 100;
                    }
                    if (this.anQ > 0) {
                        this.anV++;
                        this.anQ = (this.anQ * (-40)) / 100;
                        this.anT = 0;
                    }
                } else {
                    this.anN = true;
                    long jMD = FarmGameEngine.MD();
                    if (jMD - FarmGameEngine.avC >= 700) {
                        FarmGameEngine.gX(37);
                        FarmGameEngine.avC = jMD;
                    }
                    if (this.anG == 60) {
                        FarmGameEngine.h(true, (((this.aoG & (-32769)) - 109) * 4) + this.aoH);
                    }
                }
                this.aol = this.anJ + this.anP;
                this.aom = this.anK + this.anQ;
                return;
            }
            this.anH += GameRuntime.Qy * 4;
            if (this.anG == 57) {
                this.aoE = FarmGameEngine.aZj.aol + FarmGameEngine.atc[1][30][2] + (FarmGameEngine.atf[1].by(33) >> 1);
                this.aoF = FarmGameEngine.aZj.aom + FarmGameEngine.atc[1][30][3] + (FarmGameEngine.atf[1].by(33) >> 1);
            } else if (this.anG >= 59) {
                if ((this.anM & 65535) == 5) {
                    this.aoE = FarmGameEngine.aZj.aol + FarmGameEngine.atc[1][32][2];
                    this.aoF = FarmGameEngine.aZj.aom + FarmGameEngine.atc[1][32][3];
                } else {
                    this.aoE = FarmGameEngine.aZj.aol + FarmGameEngine.atc[1][78][2];
                    this.aoF = FarmGameEngine.aZj.aom + FarmGameEngine.atc[1][78][3];
                }
            } else if (this.anG == 37) {
                this.aoE = FarmGameEngine.aZj.aol + FarmGameEngine.atc[1][25][2] + (FarmGameEngine.atf[1].by(4) >> 1);
                this.aoF = FarmGameEngine.aZj.aom + FarmGameEngine.atc[1][25][3] + (FarmGameEngine.atf[1].by(4) >> 1);
            } else {
                this.aoE = FarmGameEngine.aZj.aol;
                this.aoF = FarmGameEngine.aZj.aom;
            }
            if (this.anG == 60) {
                this.aoE = FarmGameEngine.aZj.aol + FarmGameEngine.atc[1][this.aoH + FacebookRequestErrorClassification.EC_INVALID_SESSION][2];
                this.aoF = FarmGameEngine.aZj.aom + FarmGameEngine.atc[1][this.aoH + FacebookRequestErrorClassification.EC_INVALID_SESSION][3];
            }
            if (this.anG == 67 || this.anG == 67) {
                this.aoE = FarmGameEngine.aZj.aol + FarmGameEngine.atc[1][120][2];
                this.aoF = FarmGameEngine.aZj.aom + FarmGameEngine.atc[1][120][3];
            }
            if (this.anI > 0) {
                this.aol = this.anJ + (((((this.aoE * 100) / FarmGameEngine.aYP) - this.anJ) * this.anH) / this.anI);
                this.aom = this.anK + ((((((this.aoF * 100) / FarmGameEngine.aYP) - this.anK) * (this.anH * this.anH)) / this.anI) / this.anI);
            }
            if (this.anH > this.anI) {
                fx(8);
                FarmGameEngine.aCk = false;
                if (this.anX) {
                    oN();
                    FarmGameEngine.axd = false;
                    this.anX = false;
                    if (this.anG == 37) {
                        FarmGameEngine.hX(this.anM);
                    } else if (this.anG == 36) {
                        FarmGameEngine.hS(this.anM);
                    } else if (this.anG == 6) {
                        FarmGameEngine.hT(this.anM);
                    } else if (this.anG == 59) {
                        int i4 = this.anM & 65535;
                        int i5 = (this.anM & (-65536)) >> 16;
                        if (i4 == 110) {
                            FarmGameEngine.hV(i5);
                            short s = FarmGameEngine.aZL[i4];
                            FarmGameEngine.N(s, i5, 34);
                            FarmGameEngine.aa(s, i5, 34);
                            FarmGameEngine.aa(s, i5, 22);
                        } else if (i4 == 5) {
                            FarmGameEngine.hU(i5);
                        } else if (i4 < 111 || i4 > 116) {
                            FarmGameEngine.O(0, i4, i5);
                            if (i5 > 0) {
                                FarmGameEngine.hI(-i5);
                            }
                            FarmGameEngine.Fy();
                        } else {
                            FarmGameEngine.bN(i5, i4 - 111);
                        }
                        String str = "Reward item. add item.:" + i4;
                        if (i4 >= 0 && i4 < 6) {
                            FarmGameEngine.aa(FarmGameEngine.aZL[i4], i5, 22);
                        }
                    } else if (this.anG != 60 && this.anG == 67) {
                        FarmGameEngine.hg(this.anM);
                        FarmGameEngine.bW(1, 121);
                    }
                    if (FarmGameEngine.aYS == 1069) {
                        FarmGameEngine.axW = true;
                    }
                }
            }
        }
    }

    void pY() {
        aE(this.aoG, this.aoH);
    }

    void pe() {
        float f = 0.0f;
        if ((FarmGameEngine.aAy - FarmGameEngine.aAw < 0 ? -(FarmGameEngine.aAy - FarmGameEngine.aAw) : FarmGameEngine.aAy - FarmGameEngine.aAw) <= 0) {
            if ((FarmGameEngine.aAz - FarmGameEngine.aAx < 0 ? -(FarmGameEngine.aAz - FarmGameEngine.aAx) : FarmGameEngine.aAz - FarmGameEngine.aAx) <= 0) {
                return;
            }
        }
        int i = FarmGameEngine.aAy - FarmGameEngine.aAw;
        int i2 = FarmGameEngine.aAz - FarmGameEngine.aAx;
        float fSqrt = (float) Math.sqrt((i * i) + (i2 * i2));
        this.alK = i / fSqrt;
        this.alL = i2 / fSqrt;
        String str = "velo slid:" + fSqrt + ",x:" + this.alK;
        if (fSqrt > 200.0f) {
            return;
        }
        float f2 = (i * i) + (i2 * i2);
        float fSqrt2 = (float) Math.sqrt((FarmGameEngine.kH() * FarmGameEngine.kH()) + (FarmGameEngine.kI() * FarmGameEngine.kI()));
        if ((1000 - FarmGameEngine.aAA) / 1000.0f >= 0.0f) {
            f = ((float) (1000 - FarmGameEngine.aAA)) / 1000.0f > 1.0f ? 1.0f : (1000 - FarmGameEngine.aAA) / 1000.0f;
        }
        this.alI = f * ((100.0f * fSqrt) / fSqrt2);
        this.alJ = (1.0f - (fSqrt / fSqrt2)) * 0.5f;
        String str2 = "velo inertia:" + this.alI + ",drop:" + this.alJ;
    }

    void pg() {
    }

    void ph() {
        FarmGameEngine.awX = 1;
        fu(4);
    }

    void pi() {
        FarmGameEngine.awX = 2;
        fv(-1);
    }

    boolean pm() {
        if (FarmGameEngine.currentTutorialStepIndex == 1 || FarmGameEngine.currentTutorialStepIndex == 2 || FarmGameEngine.currentTutorialStepIndex == 3 || (FarmGameEngine.currentTutorialStepIndex >= 32 && FarmGameEngine.currentTutorialStepIndex <= 36)) {
            return false;
        }
        String str = "[Actor CROP] Crop_Pressed. cropState:" + this.Bj;
        if (this.Bj == 5) {
            FarmGameEngine.hc(1);
        }
        return pn();
    }

    boolean pn() {
        String str = "Crop_Over m_cropType :" + FarmGameEngine.avS;
        if (FarmGameEngine.currentTutorialStepIndex == 4 || FarmGameEngine.currentTutorialStepIndex == 7 || FarmGameEngine.currentTutorialStepIndex == 45) {
            FarmGameEngine.axW = true;
        }
        if (this.Bj != 1 || FarmGameEngine.avP != 0) {
            if (this.Bj != 5 || FarmGameEngine.avP != 1) {
                return false;
            }
            if (((FarmGameEngine.aCi & 2) == 2) && !FarmGameEngine.aAY) {
                if ((FarmGameEngine.aZB != 0 || FarmGameEngine.hF(5) + fe(3) + 1 <= FarmGameEngine.Fw()) && (FarmGameEngine.aZB != 0 || FarmGameEngine.hF(5) + FarmGameEngine.aAX + 1 <= FarmGameEngine.Fw())) {
                    FarmGameEngine.a(1, this);
                    if (FarmGameEngine.currentTutorialStepIndex != 5) {
                        return true;
                    }
                    FarmGameEngine.ayp = false;
                    return true;
                }
                FarmGameEngine.aAY = true;
                FarmGameEngine.c(false, 1, FarmGameEngine.avR);
                FarmGameEngine.awH = false;
                FarmGameEngine.avP = -1;
                FarmGameEngine.H(481, 487, 7, 0);
                return false;
            }
            return false;
        }
        if (FarmGameEngine.awc > FarmGameEngine.FC()) {
            return true;
        }
        if (FarmGameEngine.currentTutorialStepIndex == 46) {
            String str2 = "[crop] over. plant tomato. set croptype tomato.:" + FarmGameEngine.avS + ", tomat:1";
            FarmGameEngine.avS = 1;
        }
        fi(FarmGameEngine.avS);
        if ((FarmGameEngine.avV > 0 && FarmGameEngine.hO(FarmGameEngine.avV)) || (FarmGameEngine.avW > 0 && FarmGameEngine.hP(FarmGameEngine.avW))) {
            FarmGameEngine.a(0, this);
            if (FarmGameEngine.currentTutorialStepIndex != 5 && FarmGameEngine.currentTutorialStepIndex != 8) {
                return true;
            }
            FarmGameEngine.ayp = false;
            return true;
        }
        FarmGameEngine.aLV = -1;
        if (FarmGameEngine.avV > 0) {
            FarmGameEngine.aSX = 4;
            FarmGameEngine.aSW = this;
            FarmGameEngine.b(FarmGameEngine.avV - FarmGameEngine.FA(), (short) 19);
            if (FarmGameEngine.avV > FarmGameEngine.FA()) {
                FarmGameEngine.bal = FarmGameEngine.avV - FarmGameEngine.FA();
                FarmGameEngine.bak = FarmGameEngine.ge(FarmGameEngine.avV - FarmGameEngine.FA());
            }
        } else if (FarmGameEngine.avW > 0) {
            String str3 = "not enough cash : " + FarmGameEngine.avW;
            FarmGameEngine.aSX = 4;
            FarmGameEngine.aSW = this;
            FarmGameEngine.a(FarmGameEngine.avW - FarmGameEngine.FB(), (short) 20);
        }
        if (FarmGameEngine.aLV != -1) {
            FarmGameEngine.fE(35);
            FarmGameEngine.bax = true;
        }
        FarmGameEngine.avP = -1;
        this.amn = false;
        return true;
    }

    boolean po() {
        String str = "[Crop] clicked/" + this.Bj + "," + FarmGameEngine.avP;
        if (FarmGameEngine.currentTutorialStepIndex == 38) {
            return false;
        }
        if (this.Bj == 1 && FarmGameEngine.avP != 0) {
            if ((FarmGameEngine.aCi & 1) == 1) {
                FarmGameEngine.hc(0);
                return true;
            }
        }
        if (FarmGameEngine.currentTutorialStepIndex >= 14 && FarmGameEngine.currentTutorialStepIndex <= 35) {
            String str2 = "ignore in tutorial guide step." + FarmGameEngine.awW;
            return false;
        }
        if ((this.Bj != 3 && this.Bj != 4) || FarmGameEngine.avP != -1) {
            return false;
        }
        String str3 = "[Crop] . cut tuto.:" + FarmGameEngine.currentTutorialStepIndex + ",endtuto:-1";
        if (FarmGameEngine.currentTutorialStepIndex != -1) {
            return false;
        }
        long jMD = FarmGameEngine.MD();
        if (jMD - FarmGameEngine.avC >= 700) {
            FarmGameEngine.gX(46);
            FarmGameEngine.avC = jMD;
        }
        FarmGameEngine.a(true, this);
        return false;
    }

    void pp() {
        String str = "Crop_PlantSeed m_dataBaseID : " + this.aoC + " m_cropCostCash : " + this.amg;
        String str2 = "m_cropType :" + FarmGameEngine.avS + " m_name : " + this.aoV;
        if ((this.amf <= 0 || !FarmGameEngine.hN(this.amf)) && (this.amg <= 0 || !FarmGameEngine.hQ(this.amg))) {
            FarmGameEngine.aLV = -1;
            if (this.amf > 0) {
                FarmGameEngine.aSX = 5;
                FarmGameEngine.aSW = this;
                FarmGameEngine.b(FarmGameEngine.avV - FarmGameEngine.FA(), (short) 19);
                if (FarmGameEngine.avV > FarmGameEngine.FA()) {
                    FarmGameEngine.bal = FarmGameEngine.avV - FarmGameEngine.FA();
                    FarmGameEngine.bak = FarmGameEngine.ge(FarmGameEngine.avV - FarmGameEngine.FA());
                }
            } else if (this.amg > 0) {
                String str3 = "not enough cash : " + this.amg;
                FarmGameEngine.aSX = 5;
                FarmGameEngine.aSW = this;
                FarmGameEngine.a(this.amg - FarmGameEngine.FB(), (short) 20);
            }
            if (FarmGameEngine.aLV != -1) {
                FarmGameEngine.fE(35);
                FarmGameEngine.bax = true;
            }
            FarmGameEngine.avP = -1;
            this.amn = false;
            if (this.amf > 0 && this.amf > FarmGameEngine.FA()) {
                FarmGameEngine.bal = this.amf - FarmGameEngine.FA();
                return;
            } else {
                if (this.amg <= 0 || this.amg <= FarmGameEngine.FB()) {
                    return;
                }
                FarmGameEngine.bak = this.amg - FarmGameEngine.FB();
                return;
            }
        }
        String str4 = "namvt +++++++++++++++++ Promo Coin : " + FarmGameEngine.avY + " - Promo Cash : " + FarmGameEngine.avX;
        j jVarBY = j.bY(2);
        bb bbVar = new bb();
        bbVar.aiw = 1;
        bbVar.aiG = FarmGameEngine.FB();
        bbVar.aiH = this.amf > 0 ? 111552 : 111551;
        bbVar.aiD = jVarBY.KW[this.aoC][30];
        bbVar.aiI = FarmGameEngine.baj;
        bbVar.aiJ = FarmGameEngine.bai;
        bbVar.aiK = this.amf > 0 ? this.amf : this.amg;
        bbVar.aiL = FarmGameEngine.avX > 0 ? FarmGameEngine.avX : FarmGameEngine.avY;
        bbVar.aiM = FarmGameEngine.ban;
        bbVar.aiF = FarmGameEngine.FC();
        bbVar.aiN = this.aoC;
        FarmGameEngine.a(bbVar);
        fj(3);
        String str5 = "[crop] actor . plant seed. :" + FarmGameEngine.currentTutorialStepIndex + ",db id:" + this.aoC + ",uid:" + this.aoU;
        if (FarmGameEngine.currentTutorialStepIndex == 46) {
            FarmGameEngine.ayf++;
        } else if (FarmGameEngine.currentTutorialStepIndex == 8) {
            FarmGameEngine.ayf++;
        } else if (FarmGameEngine.currentTutorialStepIndex == -1) {
            FarmGameEngine.aa(this.aoU, 1, 12);
        }
    }

    void pq() {
        fj(1);
        if (FarmGameEngine.currentTutorialStepIndex == 38 || FarmGameEngine.currentTutorialStepIndex == 5) {
            FarmGameEngine.ayf++;
        }
        at(false);
        this.aoU = FarmGameEngine.aZE[0];
        this.aoV = FarmGameEngine.aZF[0];
        fy(1024);
    }

    int pr() {
        return this.Bj;
    }

    void ps() {
        if (this.Bj == 2) {
            long jMD = (FarmGameEngine.MD() - this.amq) / 1000;
            if (jMD >= FarmGameEngine.ii(this.amr)) {
                fk(3);
                if (FarmGameEngine.aYS == 1067 || FarmGameEngine.aYS == 1066) {
                    FarmGameEngine.axW = true;
                    if (FarmGameEngine.aYS == 1066) {
                        FarmGameEngine.axN = 1068;
                    }
                }
            }
            if (FarmGameEngine.aZo == this && FarmGameEngine.ii(this.amr) != 0) {
                FarmGameEngine.ba((int) jMD, FarmGameEngine.ii(this.amr));
                this.aoX = A(this.amp, (int) (((long) FarmGameEngine.ii(this.amr)) - jMD), FarmGameEngine.ii(this.amr));
            }
        } else if (FarmGameEngine.aZo == this) {
            FarmGameEngine.a(false, (FarmEntity) null);
        }
        if (this.aoC == 22 || this.aoC == 23) {
            if (this.Bj == 6 && (this.aoR == null || (this.aoR != null && this.aoR.lQ()))) {
                fk(2);
                if (FarmGameEngine.aYS == 1073) {
                    FarmGameEngine.axW = true;
                }
            }
            if (this.aoR != null) {
                this.aoR.dV(GameRuntime.Qy);
            }
        }
    }

    boolean pt() {
        if (this.amu) {
            return false;
        }
        if (this.Bj == 3) {
            FarmGameEngine.hc(1);
            if (this.aoC == 22 || this.aoC == 23) {
                FarmGameEngine.awH = false;
            }
        }
        return pu();
    }

    boolean pv() {
        if (this.amu) {
            String strDt = FarmGameEngine.dt(12413);
            if (strDt == null) {
                strDt = "";
            }
            FarmGameEngine.m(strDt, 7, 0);
            return false;
        }
        String str = "tree clicked. sate :" + this.Bj;
        if (this.Bj == 2) {
            if (FarmGameEngine.currentTutorialStepIndex != 18) {
                String str2 = "[tree] show progess bar.:paint actino:" + FarmGameEngine.avP;
                if (FarmGameEngine.avP == -1) {
                    if (FarmGameEngine.aYS == 1066) {
                        FarmGameEngine.gX(79);
                        FarmGameEngine.axW = true;
                    } else if (this.aoC == 22 || this.aoC == 23) {
                        FarmGameEngine.gX(79);
                    } else {
                        FarmGameEngine.gX(46);
                    }
                    FarmGameEngine.a(true, this);
                    return true;
                }
            }
        } else if (this.Bj == 3 && FarmGameEngine.avP != 1) {
            FarmGameEngine.hc(1);
            if (this.aoC != 22 && this.aoC != 23) {
                return true;
            }
            FarmGameEngine.awH = false;
            return true;
        }
        return false;
    }

    void pw() {
        if (this.amu) {
            return;
        }
        if (this.aoC == 22 || this.aoC == 23) {
            fk(5);
        } else {
            fk(2);
        }
        this.amr = FarmGameEngine.ih((this.apj * FarmGameEngine.ma(this.aoU)) / 100);
        at(true);
        fy(1024);
    }

    void px() {
        this.amt = this.Bj;
        fk(4);
        fy(64);
        fy(128);
    }

    void py() {
        if (this.Bj == 4) {
            if (this.amt == 4) {
                fk(2);
            } else {
                fk(this.amt);
            }
        }
        fx(64);
        fx(128);
        this.amu = false;
    }

    void pz() {
        fk(6);
        this.aoR = new aa(FarmGameEngine.aZf[13], this.aot, this.aou);
        this.aoR.Y(0, 2);
        this.aoQ = -50;
    }

    void qe() {
        switch (this.Bj) {
            case 0:
                if (this.aoO.lQ()) {
                    fw(0);
                }
                break;
            case 1:
                qm();
                if (this.aol == this.aoE && this.aom == this.aoF) {
                    fw(0);
                    break;
                }
                break;
        }
    }

    void qf() {
        if (this.Bj == 0) {
            return;
        }
        if (this.aoO.lQ()) {
            this.aoO.Y(0, -1);
        }
        oV();
    }

    void qg() {
        FarmGameEngine.o(this);
        oN();
    }

    void qh() {
        FarmGameEngine.n(this);
        this.Bj = 1;
        this.aoO.Y(0, -1);
        this.aoH = 0;
    }

    boolean qk() {
        return (this.aoE == this.aol && this.aoF == this.aom) ? false : true;
    }

    void ql() {
        if (this.Kx == 49) {
            this.ame = FarmGameEngine.ih(0);
            fj(5);
            return;
        }
        if (this.Kx == 54) {
            if (fz(32)) {
                this.amI = FarmGameEngine.ih(0);
                pR();
            } else {
                this.amE = FarmGameEngine.ih(0);
            }
            FarmGameEngine.aZq = 100;
            FarmGameEngine.aZr = "0:0:0";
            return;
        }
        if (this.Kx == 52 || this.Kx == 69) {
            if (FarmGameEngine.aYS == 1067) {
                FarmGameEngine.axW = true;
            }
            fk(3);
        } else if (this.Kx == 50) {
            this.alV = 0L;
        }
    }

    void qm() {
        if (this.anj) {
        }
        int[] iArr = {this.aoE, this.aoF};
        int[] iArr2 = new int[2];
        int[] iArr3 = new int[2];
        if (this.aoZ != null && this.apa >= 2) {
            int[] iArr4 = new int[3];
            int[] iArr5 = new int[3];
            for (int i = 0; i < 3; i++) {
                iArr2[0] = this.aoZ.dO(i);
                iArr2[1] = this.aoZ.dP(i);
                FarmGameEngine.g(iArr2, iArr3);
                iArr4[i] = iArr3[0];
                iArr5[i] = iArr3[1];
            }
            if (this.Kx == 42 || ((this.Kx == 54 && this.anw && this.ann) || (this.Kx == 54 && (this.anj || this.ank)))) {
                this.apb -= av.adS;
            } else if (this.Kx == 54) {
                this.apb -= 5;
            }
            if (this.apb <= 0) {
                this.apa = 0;
            } else {
                GameRuntime.a(iArr4[0], iArr5[0], iArr4[1], iArr5[1], iArr4[2], iArr5[2], this.apb);
                iArr[0] = GameRuntime.jX();
                iArr[1] = GameRuntime.jY();
                if (this.Kx == 42) {
                    aG(iArr[0], iArr[1]);
                }
            }
        }
        int i2 = iArr[0] - this.aol;
        int i3 = iArr[1] - this.aom;
        int iCR = GameRuntime.cR((i2 * i2) + (i3 * i3));
        if (iCR == 0) {
            this.aol = iArr[0];
            this.aom = iArr[1];
            if (this.apb <= 0) {
                this.aol = this.aoE;
                this.aom = this.aoF;
                return;
            }
            return;
        }
        int i4 = ((this.aos * GameRuntime.Qy) << 8) / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
        int i5 = ((i2 * i4) / iCR) >> 8;
        int i6 = ((i4 * i3) / iCR) >> 8;
        int i7 = i5 < 0 ? -i5 : i5;
        if (i2 < 0) {
            i2 = -i2;
        }
        if (i7 >= i2 || i5 == 0) {
            this.aol = iArr[0];
        } else {
            this.aol += i5;
        }
        int i8 = i6 < 0 ? -i6 : i6;
        if (i3 < 0) {
            i3 = -i3;
        }
        if (i8 >= i3 || i6 == 0) {
            this.aom = iArr[1];
        } else {
            this.aom += i6;
        }
    }

    int qn() {
        switch (fA(this.aoU)) {
            case 20:
                return j.bY(4).KW[this.aoC][18];
            case 49:
                return j.bY(2).KW[this.aoC][27];
            case 50:
                return j.bY(3).KW[this.aoC][31];
            case 54:
                j jVarBY = j.bY(0);
                return fz(2048) ? jVarBY.KW[this.aoC][12] : jVarBY.KW[this.aoC][11];
            default:
                return 0;
        }
    }

    void qo() {
        int iQn;
        int i;
        String str = "actor sell. type:" + this.Kx;
        if (this.Kx == 49) {
            String str2 = "crop state:" + this.Bj + ",coin:" + this.amf;
            if (this.Bj == 3 || this.Bj == 4) {
                iQn = qn();
                fj(1);
                this.aoU = FarmGameEngine.aZE[0];
                this.aoV = FarmGameEngine.aZF[0];
                fy(1024);
            } else {
                if (fz(2)) {
                    FarmGameEngine.o(this);
                }
                iQn = qn();
                oN();
            }
        } else {
            if (fz(2)) {
                FarmGameEngine.o(this);
            }
            iQn = qn();
            oN();
        }
        qq();
        FarmGameEngine.a(false, iQn, this.aol, this.aom);
        if (FarmGameEngine.baf != null) {
            int i2 = 119043;
            switch (this.Kx) {
                case 20:
                    if (FarmGameEngine.gj(this.aoC)) {
                        i = 150885;
                    } else if (this.aoC != 68) {
                        i = 115430;
                        i2 = 116467;
                    } else {
                        i = 155472;
                    }
                    break;
                case 49:
                    i = 115424;
                    break;
                case 50:
                    if (FarmGameEngine.gi(this.aoC)) {
                        i = 150870;
                    } else {
                        i = 115421;
                        i2 = 116460;
                    }
                    FarmGameEngine.l(this);
                    break;
                case 52:
                    i = 116010;
                    break;
                case 54:
                    if (!FarmGameEngine.gg(this.aoC)) {
                        i = !FarmGameEngine.gh(this.aoC) ? 115423 : 150869;
                    } else {
                        i = 119024;
                    }
                    break;
                case 69:
                    i = 150886;
                    break;
                default:
                    i = 0;
                    break;
            }
            FarmGameEngine.a(115443, new String[]{"INT:" + Integer.toString(FarmGameEngine.FB()), "INT:" + Integer.toString(0), "INT:" + Integer.toString(iQn), "INT:" + Integer.toString(i2), "INT:" + Integer.toString(i), "N/A", "N/A", "INT:" + Integer.toString(0), "INT:" + Integer.toString(FarmGameEngine.FC())});
        }
    }

    int qp() {
        switch (this.Kx) {
            case 20:
                return 2;
            case 50:
                return 1;
            case 52:
            case 69:
                return 4;
            case 54:
                return 3;
            default:
                return -1;
        }
    }

    void qq() {
        if (this.aoM != null) {
            this.aoM.Kx = -1;
        }
    }

    void qr() {
        aP(this.aoJ, this.aoK);
        aP(this.aoG, this.aoH);
    }

    /* JADX WARN: Code duplicated, block: B:41:0x00a9  */
    boolean qs() {
        int i;
        int i2;
        int i3 = 8;
        int[] iArr = new int[2];
        FarmGameEngine.d(new int[]{this.aol, this.aom}, iArr);
        if (this.Kx == 50) {
            j jVarBY = j.bY(3);
            if ((this.aoC >= 0 && this.aoC <= 8) || this.aoC == 19) {
                return true;
            }
            if (this.aoC != -1) {
                int i4 = jVarBY.KW[this.aoC][22];
                int i5 = jVarBY.KW[this.aoC][23];
                if (this.aoC == 17) {
                    i5 = 4;
                } else {
                    i3 = i4;
                }
                i2 = i3 * 36;
                i = i5 * 36;
            } else {
                i = 0;
                i2 = 0;
            }
        } else {
            i = 0;
            i2 = 0;
        }
        if (this.aoC == 17 && this.Kx == 50) {
            return iArr[0] > (-i2) / 2 && iArr[0] < (i2 / 2) + GameRuntime.kH() && iArr[1] >= (-i) / 2 && iArr[1] < (i / 2) + GameRuntime.kI();
        }
        return iArr[0] > (-100) - i2 && iArr[0] < i2 + (GameRuntime.kH() + 100) && iArr[1] >= (-100) - i && iArr[1] < i + (GameRuntime.kI() + 100);
    }

    void y(int i, int i2, int i3) {
        this.aoE = i;
        this.aoF = i2;
        this.Bj = 1;
        this.alM = i3;
    }

    void z(int i, int i2, int i3) {
        int[] iArr = new int[2];
        if (!FarmGameEngine.a(this, iArr, i + i3, i2 + i3)) {
            aN(i, i2);
            return;
        }
        this.aoE = iArr[0];
        this.aoF = iArr[1];
        fp(1);
        aQ(this.aoE, this.aoF);
    }

    void mg() {
        if (this.aoO != null) {
            this.aoO.dV(GameRuntime.Qy);
        }
        if (fz(8) && this.Kx != 11) {
            this.apk = qs();
        }
        switch (this.Kx) {
            case -1:
                oN();
                break;
            case 21:
                qb();
                break;
            case 22:
                qf();
                break;
            case 23:
                qc();
                break;
            case 24:
                qd();
                break;
            case 32:
                if (this.aol != this.aoE || this.aom != this.aoF) {
                    qm();
                }
                break;
            case 33:
                this.aol = this.aoM.aol;
                this.aom = this.aoM.aom;
                if (this.aoO != null && this.aoO.lQ()) {
                    this.aoO = null;
                }
                break;
            case 34:
                pW();
                break;
            case 42:
                oX();
                break;
            case 47:
                pX();
                break;
            case 49:
                pl();
                break;
            case 50:
                pf();
                break;
            case 51:
                qe();
                break;
            case 52:
            case 69:
                ps();
                break;
            case 54:
                pG();
                break;
        }
        EntityPoseState.spriteUpdated(this);
        if (this.Kx == 10 || this.Kx == 13) {
            return;
        }
        oV();
        if (this.aoO != null) {
            aa aaVar = this.aoO;
            int i = this.aot;
            int i2 = this.aou;
            aaVar.aaL = i;
            aaVar.aaM = i2;
        }
    }

    void oU() {
        if (this.aoR != null) {
            aa aaVar = this.aoR;
            int i = this.aot + this.aoP;
            int i2 = this.aou + this.aoQ;
            aaVar.aaL = i;
            aaVar.aaM = i2;
            aa aaVar2 = this.aoR;
            int i3 = FarmGameEngine.aYP;
            if (w.Zo) {
                aaVar2.aaW = i3;
            } else {
                GameRuntime.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            GameRuntime.l(18, 5, 1);
            this.aoR.lR();
            GameRuntime.l(18, 5, 0);
        }
        if (this.aoS != null) {
            aa aaVar3 = this.aoS;
            int i4 = this.aot + this.aoP;
            int i5 = this.aou + this.aoQ;
            aaVar3.aaL = i4;
            aaVar3.aaM = i5;
            aa aaVar4 = this.aoS;
            int i6 = FarmGameEngine.aYP;
            if (w.Zo) {
                aaVar4.aaW = i6;
            } else {
                GameRuntime.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            GameRuntime.l(18, 5, 1);
            this.aoS.lR();
            GameRuntime.l(18, 5, 0);
        }
    }

    void pl() {
        if (this.aoR != null) {
            this.aoR.dV(GameRuntime.Qy);
        }
        if (this.Bj == 3) {
            long jMD = (FarmGameEngine.MD() - this.amd) / 1000;
            if (jMD >= FarmGameEngine.ii(this.ame) / 2 && FarmGameEngine.currentTutorialStepIndex != 8 && FarmGameEngine.currentTutorialStepIndex != 46) {
                fj(4);
            }
            if (FarmGameEngine.aZo == this && FarmGameEngine.ii(this.ame) != 0) {
                FarmGameEngine.ba((int) jMD, FarmGameEngine.ii(this.ame));
                this.aoX = A(this.aml, (int) (((long) FarmGameEngine.ii(this.ame)) - jMD), FarmGameEngine.ii(this.ame));
            }
        } else if (this.Bj == 4) {
            long jMD2 = (FarmGameEngine.MD() - this.amd) / 1000;
            if (jMD2 >= FarmGameEngine.ii(this.ame) && ((FarmGameEngine.currentTutorialStepIndex < 47 || FarmGameEngine.currentTutorialStepIndex > 58) && FarmGameEngine.currentTutorialStepIndex != 8 && FarmGameEngine.currentTutorialStepIndex != 46)) {
                fj(5);
            }
            if (FarmGameEngine.aZo == this && FarmGameEngine.ii(this.ame) != 0) {
                FarmGameEngine.ba((int) jMD2, FarmGameEngine.ii(this.ame));
                this.aoX = A(this.aml, (int) (((long) FarmGameEngine.ii(this.ame)) - jMD2), FarmGameEngine.ii(this.ame));
            }
        } else if (FarmGameEngine.aZo == this) {
            FarmGameEngine.a(false, (FarmEntity) null);
        }
        if ((this.aoR == null || this.aoR.aaO != FarmGameEngine.aZf[16]) && this.Bj == 5) {
            this.aoR = new aa(FarmGameEngine.aZf[16], this.aot, this.aou);
            this.aoR.Y(0, -1);
            this.aoR.aaY = 0;
        } else if (this.aoR != null && this.Bj != 5 && this.aoR.aaO == FarmGameEngine.aZf[16]) {
            this.aoR = null;
        }
        if (this.aoR == null || this.Bj != 5) {
            return;
        }
        int i = fz(1024) ? 1 : 0;
        if (this.aoR.aaO.hI() != i) {
            this.aoR.aaO.bQ(i);
        }
    }

    void qb() {
        if (this.aoO == null || !this.aoO.lQ() || (anY != -1 && anY >= anZ)) {
            if (this.aoO == null || !this.aoO.lQ() || anY < anZ) {
                return;
            }
            this.aoO = null;
            this.Kx = -1;
            if (a(1, 21, new FarmEntity[1], 1) == 0) {
            }
            return;
        }
        int iL = GameRuntime.L(25, 29);
        String str = "Fireworks sndIndex : " + iL;
        FarmGameEngine.gX(iL);
        this.aoO.Y(0, 1);
        int iBy = this.aoO.aaO.by(0);
        int iBz = this.aoO.aaO.bz(0);
        this.aol = ((GameRuntime.L(iBy, GameRuntime.kH() - iBy) + FarmGameEngine.aZj.aol) * 100) / FarmGameEngine.aYP;
        this.aom = ((FarmGameEngine.aZj.aom + GameRuntime.L(iBz, GameRuntime.kI() - iBz)) * 100) / FarmGameEngine.aYP;
        if (FarmGameEngine.apC[FarmGameEngine.apB] == 14) {
            this.aot = ((this.aol * FarmGameEngine.aYP) / 100) - FarmGameEngine.aZj.aol;
            this.aou = ((this.aom * FarmGameEngine.aYP) / 100) - FarmGameEngine.aZj.aom;
        }
        this.aoO.aaY = GameRuntime.L(0, this.aoO.aaO.hw());
        if (anY > -1) {
            anY++;
        }
    }

    void qc() {
        if (this.aoO != null && this.aoO.lQ() && (aoa == -1 || aoa < aob)) {
            this.aoO.Y(1, 1);
            int iBy = this.aoO.aaO.by(0);
            int iBz = this.aoO.aaO.bz(0);
            this.aol = GameRuntime.L(-iBy, iBy) + aoc;
            this.aom = aod + GameRuntime.L(-iBz, iBz);
            if (aoa > -1) {
                aoa++;
                return;
            }
            return;
        }
        if (this.aoO == null || !this.aoO.lQ() || aoa < aob) {
            return;
        }
        this.aoO = null;
        this.Kx = -1;
        a(1, 23, new FarmEntity[1], 1);
        pj();
        FarmEntity[] chVarArr = new FarmEntity[1];
        a(1, 42, chVarArr, 1);
        if (chVarArr != null) {
            chVarArr[0].fg(3);
            FarmGameEngine.gX(33);
        }
        ft(3);
    }

    void qd() {
        if (!FarmGameEngine.ha(1) && aog) {
            this.aoO = null;
            FarmGameEngine.awX = 0;
            FarmGameEngine.DJ();
            aog = false;
            FarmGameEngine.fE(42);
        }
        if (this.aoO == null || !this.aoO.lQ() || (aoe != -1 && aoe >= aof)) {
            if (this.aoO == null || !this.aoO.lQ() || aoe >= aof) {
            }
            return;
        }
        this.aoO.Y(0, 1);
        int iBy = this.aoO.aaO.by(0);
        int iBz = this.aoO.aaO.bz(0);
        this.aol = ((GameRuntime.L(iBy, GameRuntime.kH() - iBy) + FarmGameEngine.aZj.aol) * 100) / FarmGameEngine.aYP;
        this.aom = ((FarmGameEngine.aZj.aom + GameRuntime.L(iBz, GameRuntime.kI() - iBz)) * 100) / FarmGameEngine.aYP;
        if (FarmGameEngine.apC[FarmGameEngine.apB] == 14) {
            this.aot = ((this.aol * FarmGameEngine.aYP) / 100) - FarmGameEngine.aZj.aol;
            this.aou = ((this.aom * FarmGameEngine.aYP) / 100) - FarmGameEngine.aZj.aom;
        }
        this.aoO.aaY = GameRuntime.L(0, this.aoO.aaO.hw());
        if (aoe > -1) {
            aoe++;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:4:0x000d  */
    void fp(int i) {
        if (i == 3) {
            tutorialEatingEpoch = TutorialFlowController.captureCallback(FarmGameEngine.currentTutorialStepIndex, FarmGameEngine.aYS, FarmGameEngine.awV);
        }
        boolean z;
        int i2;
        int i3;
        boolean z2;
        int i4;
        boolean z3;
        int i5;
        int iL = -1;
        fx(1);
        switch (i) {
            case 0:
                int iL2 = GameRuntime.L(1, 100);
                if (fz(32)) {
                    i5 = iL2 < 70 ? 5 : 7;
                } else {
                    i5 = iL2 < 70 ? 0 : 2;
                    if (this.anw) {
                        if (!fz(131072) || FarmGameEngine.axE) {
                            String str = " time_dog_idle = " + FarmGameEngine.axf;
                            String str2 = " cGame.time_dog_idle_start = " + FarmGameEngine.axe;
                            if (iL2 < 40) {
                                if (iL2 < 20) {
                                    FarmGameEngine.axE = true;
                                }
                                i5 = 0;
                            } else if (iL2 < 60) {
                                i5 = 2;
                            } else {
                                i5 = iL2 < 80 ? 22 : 23;
                            }
                        } else {
                            i5 = 24;
                        }
                    }
                }
                int iL3 = (!fz(131072) || (FarmGameEngine.axE && this.anw)) ? GameRuntime.L(1, 5) : -1;
                if (i5 == 23 || i5 == 22) {
                    z = false;
                    iL = GameRuntime.L(1, 3);
                    i2 = i5;
                } else {
                    z = false;
                    iL = iL3;
                    i2 = i5;
                }
                break;
            case 1:
                if (!this.anw) {
                    int i6 = fz(32) ? 5 : 0;
                    if (this.aoE > this.aol) {
                        if (this.aoF > this.aom) {
                            i4 = 3;
                            z3 = true;
                        } else {
                            i4 = 4;
                            z3 = false;
                        }
                    } else if (this.aoF > this.aom) {
                        i4 = 3;
                        z3 = false;
                    } else {
                        i4 = 4;
                        z3 = true;
                    }
                    int i7 = i6 + i4;
                    z = z3;
                    i2 = i7;
                } else {
                    int i8 = fz(32) ? 2 : 0;
                    if (this.aoE > this.aol) {
                        if (this.aoF > this.aom) {
                            i3 = 12;
                            z2 = true;
                        } else {
                            i3 = 13;
                            z2 = false;
                        }
                    } else if (this.aoF > this.aom) {
                        i3 = 12;
                        z2 = false;
                    } else {
                        i3 = 13;
                        z2 = true;
                    }
                    int i9 = i8 + i3;
                    z = z2;
                    i2 = i9;
                }
                break;
            case 2:
                if (!fz(32)) {
                    z = false;
                    i2 = 0;
                } else {
                    z = false;
                    i2 = GameRuntime.L(1, 100) >= 70 ? 7 : 5;
                }
                break;
            case 3:
                fy(1);
                int i10 = fz(32) ? 6 : 1;
                FarmGameEngine.axi = true;
                z = false;
                iL = 3;
                i2 = i10;
                break;
            case 4:
                if (!fz(32)) {
                    z = false;
                    i2 = 0;
                } else {
                    z = false;
                    i2 = 5;
                }
                break;
            case 5:
            case 6:
            case R.styleable.CardView_contentPaddingTop /* 11 */:
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
            default:
                z = false;
                i2 = 0;
                break;
            case 7:
                fy(1);
                z = false;
                iL = 5;
                i2 = fz(32) ? 5 : 0;
                break;
            case 8:
                z = false;
                iL = 1;
                i2 = this.anw ? 21 : 15;
                break;
            case 9:
                if (!fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                    int iL4 = GameRuntime.L(1, 100);
                    int i11 = fz(32) ? iL4 < 70 ? 5 : 7 : iL4 < 70 ? 0 : 2;
                    z = false;
                    iL = GameRuntime.L(1, 5);
                    i2 = i11;
                } else {
                    int i12 = GameRuntime.L(1, 100) < 70 ? 0 : 2;
                    z = false;
                    iL = GameRuntime.L(1, 5);
                    i2 = i12;
                }
                break;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                z = false;
                i2 = 0;
                break;
            case 13:
                fy(1);
                if (fz(131072)) {
                    fy(131072);
                    pT();
                }
                int i13 = (this.anD + 17) - 64;
                FarmGameEngine.axh = true;
                FarmGameEngine.aa(FarmGameEngine.aZL[118], 1, 44);
                z = false;
                iL = 2;
                i2 = i13;
                break;
            case 14:
                if (this.anj) {
                    this.anl = true;
                } else if (this.ank) {
                    this.anm = true;
                }
                if (this.anp == 1) {
                    this.aoS = new aa(FarmGameEngine.aZf[171], this.aot, this.aou);
                    this.aoS.Y(12, 3);
                } else if (this.anp == 2) {
                    this.aoS = new aa(FarmGameEngine.aZf[172], this.aot, this.aou);
                    this.aoS.Y(12, 3);
                }
                this.aoP = this.amZ;
                this.aoQ = this.ana;
                z = false;
                iL = 1;
                i2 = 0;
                break;
            case 15:
                fy(1);
                FarmGameEngine.axh = true;
                z = false;
                iL = 3;
                i2 = 10;
                break;
        }
        boolean z4 = this.aoO.aaP == i2;
        this.Bj = i;
        aa aaVar = this.aoO;
        if (z4) {
            aaVar.Y(-1, 1);
        }
        aaVar.Y(i2, iL);
        if (z) {
            this.aoO.dU(2);
        } else {
            this.aoO.dU(0);
        }
    }

    void oX() {
        switch (this.Bj) {
            case 0:
                if (FarmGameEngine.currentTutorialStepIndex == -1) {
                    if (alt) {
                        e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 23, -63, -117, 0, 1}));
                        alt = false;
                    }
                    if (!b.Kf) {
                        qq();
                    }
                    if (!b.Kc || b.Kg) {
                        if (b.JU || (FarmGameEngine.bbd != 0 && FarmGameEngine.MD() - FarmGameEngine.bbd < alu)) {
                            b.Kd = false;
                            qq();
                        } else if (FarmGameEngine.qO()) {
                            b.Kc = true;
                            b.JL = true;
                            b.Kd = true;
                            if (b.Kg) {
                                b.Kg = false;
                                b.Ke = false;
                            }
                        } else {
                            b.JL = false;
                            b.Kd = false;
                        }
                    }
                    if (b.Kc && !b.Ke) {
                        b.JL = true;
                        b.Ke = true;
                        b.JP = 1;
                    }
                    if (b.Kd) {
                        switch (b.JP) {
                            case 1:
                                FarmGameEngine.dE("enter_section_video_bubble");
                                b.JP = 0;
                                qq();
                                break;
                            case 2:
                                qq();
                                break;
                            case 3:
                                alt = true;
                                b.Kf = true;
                                b.Kd = false;
                                break;
                            case 4:
                                qq();
                                break;
                            default:
                                qq();
                                break;
                        }
                    }
                }
                if (this.aoO.lQ()) {
                    fg(0);
                }
                break;
            case 1:
                qm();
                if (this.aol == this.aoE && this.aom == this.aoF) {
                    fg(0);
                    break;
                }
                break;
            case 2:
                int i = this.aoO.aaP;
                if (this.aoO.lQ() || i == 2) {
                    fg(0);
                    FarmGameEngine.aCh = 3;
                }
                break;
            case 3:
                if (this.aoO.lQ()) {
                    FarmGameEngine.aCl = false;
                    fg(0);
                    FarmGameEngine.n(300, 301, 15, 0, 1);
                    if (FarmGameEngine.qN()) {
                        String str = "character jump. manor restored.:" + FarmGameEngine.aZD;
                        if (FarmGameEngine.aZD == 3 && FarmGameEngine.aTK) {
                            FarmGameEngine.aLV = 14;
                        }
                    }
                    FarmGameEngine.awX = 0;
                }
                break;
        }
    }

    private void finishEatingTutorial() {
        if (!FarmGameEngine.awO && FarmGameEngine.currentEngineState() == 19 && FarmGameEngine.currentTutorialStepIndex == 32
                && TutorialFlowController.callbackCurrent(tutorialEatingEpoch, FarmGameEngine.currentTutorialStepIndex, FarmGameEngine.aYS, FarmGameEngine.awV)) {
            FarmGameEngine.awO = true;
            FarmGameEngine.axW = false;
            FarmGameEngine.axV = false;
            FarmGameEngine.currentTutorialStepIndex = 33;
        }
    }

    void pH() {
        FarmEntity chVarFn;
        String strDt;
        FarmEntity chVarHA = null;
        switch (this.Bj) {
            case 0:
                if (this.anw && FarmGameEngine.axh && qs() && FarmGameEngine.aZB == 0) {
                    if (this.aoO.aaQ == 6 && this.aoO.aaP == 23) {
                        FarmGameEngine.gX(64);
                        FarmGameEngine.axh = false;
                    } else if (this.aoO.aaQ == 1 && (this.aoO.aaP == 2 || this.aoO.aaP == 22)) {
                        FarmGameEngine.gX(75);
                        FarmGameEngine.axh = false;
                    }
                }
                if (this.anw && this.aoO.aaQ == 0) {
                    FarmGameEngine.axh = true;
                }
                if (!FarmGameEngine.axE || !this.anw) {
                    if (this.aoO.lQ()) {
                        if (FarmGameEngine.avD != 0 && FarmGameEngine.avD != 1 && FarmGameEngine.avD != 3 && ((!FarmGameEngine.ss() || FarmGameEngine.aZo != this) && ((qs() || fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) && !FarmGameEngine.bad && this.anp == 0))) {
                            if (GameRuntime.L(0, 100) >= (!this.anw ? 20 : 0)) {
                                if (!fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                                    pJ();
                                } else if (!this.ani) {
                                    pF();
                                }
                            }
                        }
                        fp(0);
                        break;
                    } else if (fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) && !this.ani) {
                        pF();
                        break;
                    }
                } else {
                    pJ();
                    break;
                }
                break;
            case 1:
                int i = this.aol;
                int i2 = this.aom;
                qm();
                if (!this.amT) {
                    int[] iArr = new int[2];
                    FarmGameEngine.f(new int[]{this.aol, this.aom}, iArr);
                    if (FarmGameEngine.ss() && FarmGameEngine.aZo == this) {
                        this.aol = i;
                        this.aom = i2;
                        fp(0);
                    }
                    if (!FarmGameEngine.bD(iArr[0], iArr[1]) && !this.anw && !this.anj && !this.ank) {
                        this.aol = i;
                        this.aom = i2;
                        fp(0);
                        break;
                    } else if (this.aol == this.aoE && this.aom == this.aoF && this.aoE != 0 && this.aoF != 0) {
                        if (((this.anj && !this.anl) || (this.ank && !this.anm)) && FarmGameEngine.axt != 0) {
                            fp(9);
                        } else if (this.anw && this.ann) {
                            this.ann = false;
                            if (FarmGameEngine.axt == 1) {
                                chVarHA = FarmGameEngine.hA(73);
                            } else if (FarmGameEngine.axt == 2) {
                                chVarHA = FarmGameEngine.hA(74);
                            }
                            if (chVarHA != null) {
                                c(true, chVarHA.anj);
                            }
                            fp(8);
                            if (!FarmGameEngine.avp) {
                                FarmGameEngine.DJ();
                                FarmGameEngine.g(3, 68, false);
                                FarmGameEngine.avp = true;
                            }
                            int i3 = FarmGameEngine.axt == 2 ? 4 : 3;
                            this.aoS = new aa(FarmGameEngine.aZf[13], this.aot, this.aou);
                            this.aoS.Y(i3, 5);
                            this.aoP = this.amZ;
                            this.aoQ = this.ana;
                        } else {
                            fp(0);
                            if (this.anj || this.ank) {
                                fy(8);
                                FarmGameEngine.axt = 0;
                                FarmGameEngine.bbp = 0;
                            }
                        }
                        break;
                    }
                }
                break;
            case 2:
                if (!fz(GLURLConnection.BUFFER_SIZE)) {
                    if (!fz(4)) {
                        fq(4);
                    }
                    if (this.aoO.lQ() || (this.anw && fz(32))) {
                        fp(0);
                    }
                }
                break;
            case 3:
                if (this.aoO.aaQ == 2 && FarmGameEngine.axi) {
                    FarmGameEngine.gX(78);
                    FarmGameEngine.axi = false;
                }
                if (this.aoO.lQ() && !fz(GLURLConnection.BUFFER_SIZE)) {
                    String str = "ANIMAL EATING animation over. step:" + FarmGameEngine.awW + ",guide cow:" + FarmGameEngine.awO;
                    finishEatingTutorial();
                    if (this.anw) {
                        if (fz(32)) {
                            pR();
                            if (FarmGameEngine.aYS == 1019) {
                                FarmGameEngine.axW = true;
                            }
                        }
                        FarmGameEngine.c(this, true);
                    }
                    fp(0);
                    if (this.anw && pC() <= 5) {
                        fq(131072);
                        break;
                    }
                }
                break;
            case 5:
                if (!(this.anw && !FarmGameEngine.lM(FarmGameEngine.aZQ[298])) && !fz(GLURLConnection.BUFFER_SIZE)) {
                    pQ();
                    pM();
                    fp(2);
                    break;
                }
                break;
            case 7:
                if ((this.aoO.lQ() && !fz(GLURLConnection.BUFFER_SIZE)) || (this.anw && this.aoR != null && this.aoR.lQ())) {
                    fp(0);
                    this.amP = true;
                    if (this.anw) {
                        FarmGameEngine.c(this, false);
                    }
                    break;
                }
                break;
            case 8:
                if (this.anw) {
                    if (this.aoO.lQ()) {
                        fp(8);
                    }
                    if (this.aoS == null || this.aoS.lQ()) {
                        fp(0);
                        FarmEntity chVarHA2 = FarmGameEngine.axt == 1 ? FarmGameEngine.hA(73) : FarmGameEngine.axt == 2 ? FarmGameEngine.hA(74) : null;
                        if (chVarHA2 != null) {
                            boolean zA = FarmGameEngine.auj ? true : FarmGameEngine.a(this, chVarHA2);
                            au(false);
                            FarmGameEngine.f(false, false);
                            FarmGameEngine.DJ();
                            FarmGameEngine.avp = false;
                            if (!FarmGameEngine.auj) {
                                FarmGameEngine.gX(76);
                            }
                            if (FarmGameEngine.auj) {
                                FarmGameEngine.azz[FarmGameEngine.aNI][6] = FarmGameEngine.MD() / 1000;
                                FarmGameEngine.aa((FarmGameEngine.aZJ[0] + FarmGameEngine.aZB) - 1, 1, 24);
                            }
                            chVarHA2.fp(14);
                            if (!FarmGameEngine.awV && FarmGameEngine.aYS != -1) {
                                String[] strArr = new String[2];
                                strArr[0] = FarmGameEngine.aPn[FarmGameEngine.aPp];
                                if (chVarHA2.anj) {
                                    strDt = FarmGameEngine.dt(992);
                                    if (strDt == null) {
                                        strDt = "";
                                    }
                                } else {
                                    strDt = FarmGameEngine.dt(993);
                                    if (strDt == null) {
                                        strDt = "";
                                    }
                                }
                                strArr[1] = strDt;
                                FarmGameEngine.m(FarmGameEngine.a(959, (Object[]) strArr), 7, 0);
                            }
                            int iA = FarmGameEngine.a(this, chVarHA2, -1, (FarmEntity) null, zA);
                            if (FarmGameEngine.lN(GLURLConnection.HTTP_FOUND) && (chVarFn = FarmGameEngine.Fn()) != null) {
                                chVarFn.pT();
                                if (chVarFn.fz(2)) {
                                    FarmGameEngine.o(chVarFn);
                                }
                                FarmGameEngine.w(chVarFn);
                                FarmGameEngine.avF = null;
                            }
                            if (FarmGameEngine.auj) {
                                int i4 = 52183;
                                int i5 = 52009;
                                if (FarmGameEngine.aND.equals("")) {
                                    i4 = 52185;
                                    i5 = 0;
                                }
                                FarmGameEngine.a(51811, new String[]{"INT:0", "INT:" + Integer.toString(150855), "INT:" + Integer.toString(i4), "INT:0", "INT:0", "INT:0", "INT:" + Integer.toString(i5), "INT:" + Integer.toString(iA), "INT:" + Integer.toString(FarmGameEngine.FC())});
                            } else {
                                FarmEntity chVarFo = FarmGameEngine.Fo();
                                if (!zA && chVarFo != null) {
                                    int[] iArr2 = new int[2];
                                    if (a(chVarFo, this, iArr2)) {
                                        this.ann = true;
                                        aN(iArr2[0], iArr2[1]);
                                        this.ann = false;
                                    }
                                }
                            }
                            if (FarmGameEngine.aYS == 1023 || FarmGameEngine.aYS == 1050 || FarmGameEngine.aYS == 1057 || FarmGameEngine.aYS == 1064) {
                                FarmGameEngine.axW = true;
                            }
                            FarmGameEngine.aa(2999, 1, 43);
                        }
                        break;
                    } else {
                        if (FarmGameEngine.axt == 1) {
                            chVarHA = FarmGameEngine.hA(73);
                        } else if (FarmGameEngine.axt == 2) {
                            chVarHA = FarmGameEngine.hA(74);
                        }
                        if (chVarHA != null && chVarHA.aoO.aaP != 15) {
                            chVarHA.aoO.Y(15, -1);
                            break;
                        }
                    }
                } else {
                    if (!FarmGameEngine.auj) {
                        fp(14);
                    }
                    break;
                }
                break;
            case 9:
                if (!fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                    if (!fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
                        FarmGameEngine.a(false, (FarmEntity) null);
                        fq(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
                        fy(64);
                        fy(128);
                    }
                    if (this.aoO.lQ()) {
                        fp(9);
                    }
                    break;
                } else {
                    if (this.aoO.lQ()) {
                        fp(9);
                    }
                    if (FarmGameEngine.aYS == 1021) {
                        FarmGameEngine.axS++;
                    }
                    if (FarmGameEngine.aYS == 1021 && (!FarmGameEngine.axd || FarmGameEngine.axS == 25)) {
                        FarmGameEngine.axS = 30;
                        FarmGameEngine.axd = false;
                        FarmGameEngine.axW = true;
                        FarmGameEngine.aMw = "";
                        String strDt2 = FarmGameEngine.dt(957);
                        if (strDt2 == null) {
                            strDt2 = "";
                        }
                        FarmGameEngine.m(strDt2, 7, 0);
                    }
                    if (FarmGameEngine.auj && FarmGameEngine.aNb) {
                        long jMD = (FarmGameEngine.MD() / 1000) - (FarmGameEngine.aNa / 1000);
                        String str2 = "elapsedSecTime :: show arrow ::" + jMD;
                        if (jMD > 30) {
                            FarmGameEngine.b(this, 0, -70);
                            FarmGameEngine.aZj.y(this.aol, this.aom, 0);
                            FarmGameEngine.aNb = false;
                            FarmGameEngine.aNa = FarmGameEngine.MD();
                        }
                        break;
                    }
                }
                break;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                if (!fz(GLURLConnection.BUFFER_SIZE)) {
                    this.anr = true;
                    fq(GLURLConnection.BUFFER_SIZE);
                    pM();
                }
                break;
            case 13:
                if (this.aoO.lQ()) {
                    FarmGameEngine.a(this, (FarmEntity) null, this.anD, (FarmEntity) null, false);
                    fp(0);
                }
                switch (this.anD) {
                    case 64:
                        if (this.aoO.aaQ == 9 && FarmGameEngine.axh) {
                            FarmGameEngine.axh = false;
                            FarmGameEngine.gX(67);
                        }
                        if (this.aoO.aaQ != 9) {
                            FarmGameEngine.axh = true;
                        }
                        break;
                    case 65:
                        if (this.aoO.aaQ == 9 && FarmGameEngine.axh) {
                            FarmGameEngine.gX(67);
                            FarmGameEngine.axh = false;
                        }
                        if (this.aoO.aaQ != 9) {
                            FarmGameEngine.axh = true;
                        }
                        break;
                    case 66:
                        if (this.aoO.aaQ == 9 && FarmGameEngine.axh) {
                            FarmGameEngine.gX(72);
                            FarmGameEngine.axh = false;
                        }
                        if (this.aoO.aaQ != 9) {
                            FarmGameEngine.axh = true;
                        }
                        break;
                    case 67:
                        if (this.aoO.aaQ == 9 && FarmGameEngine.axh) {
                            FarmGameEngine.gX(72);
                            FarmGameEngine.axh = false;
                        }
                        if (this.aoO.aaQ != 9) {
                            FarmGameEngine.axh = true;
                        }
                        break;
                }
                break;
            case 14:
                FarmGameEngine.f(false, false);
                aN(0, 0);
                break;
            case 15:
                if (!FarmGameEngine.atx) {
                    fp(0);
                }
                if (this.aoO.lQ() && this.aoO.aaP == 10) {
                    boolean z = this.aoO.aaP == 2;
                    aa aaVar = this.aoO;
                    if (z) {
                        aaVar.Y(-1, 1);
                    }
                    aaVar.Y(2, 2);
                }
                if (this.aoO.lQ() && this.aoO.aaP == 2) {
                    boolean z2 = this.aoO.aaP == 0;
                    aa aaVar2 = this.aoO;
                    if (z2) {
                        aaVar2.Y(-1, 1);
                    }
                    aaVar2.Y(0, -1);
                }
                if (this.anw && !fz(32)) {
                    if (this.aoO.aaQ == 1 && this.aoO.aaP == 10 && FarmGameEngine.axh) {
                        FarmGameEngine.axh = false;
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (w.Vl) {
                            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To;
                        }
                        if (Math.abs(jCurrentTimeMillis) / 2 == 0) {
                            FarmGameEngine.gX(77);
                        } else {
                            FarmGameEngine.gX(64);
                        }
                    }
                    if (this.aoO.aaP == 2 && this.aoO.aaQ == 1 && FarmGameEngine.axh) {
                        FarmGameEngine.axh = false;
                        FarmGameEngine.gX(75);
                    }
                    if (this.aoO.aaQ != 1 && this.aoO.aaQ != 2) {
                        if (this.aoO.aaP == 10 || this.aoO.aaP == 2) {
                            FarmGameEngine.axh = true;
                        }
                        break;
                    }
                }
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    void pI() {
        int i;
        boolean z;
        int i2;
        if (fz(8)) {
            int i3 = this.aoO.aaP;
            if ((i3 == 1 || i3 == 6) && FarmGameEngine.atf[1] != null) {
                if (this.aoC == 68) {
                    i = 10;
                } else {
                    i = this.aoC == 75 ? 16 : 0;
                }
                int i4 = this.aot + this.amX + i;
                int i5 = (this.aou + this.amY) - i;
                if (FarmGameEngine.aYP == 100) {
                    FarmGameEngine.atf[1].a(GameRuntime.Qj, this.anw ? 75 : 34, (float) i4, (float) i5, 0);
                } else {
                    GameRuntime.l(18, 5, 1);
                    FarmGameEngine.atf[1].b(GameRuntime.Qj, this.anw ? 75 : 34, i4, i5, 0, FarmGameEngine.aYP);
                    GameRuntime.l(18, 5, 0);
                }
            }
            if (this.anw) {
                switch (this.aoC) {
                    case 68:
                        z = i3 == 17;
                        this.anv = 2;
                        break;
                    case 69:
                        this.anv = 53;
                        z = false;
                        break;
                    case 70:
                        z = i3 == 17;
                        this.anv = 2;
                        break;
                    case 71:
                        this.anv = 53;
                        z = false;
                        break;
                    case 72:
                        z = i3 == 17;
                        this.anv = 2;
                        break;
                    case 73:
                    case 74:
                    default:
                        z = false;
                        break;
                    case 75:
                        z = i3 == 17;
                        this.anv = 2;
                        break;
                }
                if (z) {
                    int i6 = j.bY(4).KW[this.anD][3];
                    a aVar = i6 < 1000 ? FarmGameEngine.atf[i6] : FarmGameEngine.aZf[i6 - 1000];
                    if (aVar != null) {
                        switch (this.anD) {
                            case 64:
                                i2 = 3;
                                break;
                            case 65:
                                i2 = 3;
                                break;
                            case 66:
                                i2 = 6;
                                break;
                            case 67:
                                i2 = 7;
                                break;
                            default:
                                i2 = 0;
                                break;
                        }
                        int i7 = this.amX + this.aot;
                        int i8 = this.amY + this.aou;
                        if (FarmGameEngine.aYP == 100) {
                            aVar.a(GameRuntime.Qj, i2, (float) i7, (float) i8, 0);
                        } else {
                            GameRuntime.l(18, 5, 1);
                            aVar.b(GameRuntime.Qj, i2, i7, i8, 0, FarmGameEngine.aYP);
                            GameRuntime.l(18, 5, 0);
                        }
                    }
                }
                if (this.anw && FarmGameEngine.axv) {
                    String strDt = FarmGameEngine.dt(12330);
                    if (strDt == null) {
                        strDt = "";
                    }
                    FarmGameEngine.a(strDt, FarmGameEngine.axx, FarmGameEngine.axy - (FarmGameEngine.axw * 4), 0, 0, 0, 0, 2, 1, false, false);
                    if (FarmGameEngine.axw > 10) {
                        FarmGameEngine.axv = false;
                        if (FarmGameEngine.aYS == 1019) {
                            FarmGameEngine.axW = true;
                        }
                    } else {
                        FarmGameEngine.axw++;
                    }
                }
            }
            if (fz(2048)) {
                int i9 = this.aot + 5;
                int i10 = this.aou - 80;
                if (FarmGameEngine.aYP == 100) {
                    FarmGameEngine.atf[1].a(GameRuntime.Qj, 64, (float) i9, (float) i10, 0);
                    return;
                }
                GameRuntime.l(18, 5, 1);
                FarmGameEngine.atf[1].b(GameRuntime.Qj, 64, i9, i10, 0, FarmGameEngine.aYP);
                GameRuntime.l(18, 5, 0);
            }
        }
    }

    void fw(int i) {
        boolean z;
        int i2;
        switch (i) {
            case 0:
                z = false;
                i2 = 1;
                break;
            case 1:
                if (this.aoE <= this.aol) {
                    z = true;
                    i2 = 0;
                } else {
                    z = false;
                    i2 = 0;
                }
                break;
            default:
                z = false;
                i2 = 0;
                break;
        }
        this.Bj = i;
        aa aaVar = this.aoO;
        GameRuntime.c(aaVar.aaO != null, "GLLibPlayer.GetNbanim().sprite is not set");
        if (aaVar.aaO.hu() > i2) {
            this.aoO.Y(i2, -1);
            if (z) {
                this.aoO.dU(2);
            } else {
                this.aoO.dU(0);
            }
        }
    }

    void qa() {
        if (this.aoO != null) {
            aa aaVar = this.aoO;
            int i = FarmGameEngine.aYP;
            if (w.Zo) {
                aaVar.aaW = i;
            } else {
                GameRuntime.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            aa aaVar2 = this.aoO;
            int i2 = this.aot;
            int i3 = this.aou;
            aaVar2.aaL = i2;
            aaVar2.aaM = i3;
            this.aoO.lR();
        }
    }

    void aP(int i, int i2) {
        int i3;
        int i4;
        if (i <= -1 || i2 <= -1) {
            return;
        }
        a aVar = FarmGameEngine.aZf[0];
        if (w.WY) {
            i3 = aVar.GE[0] & 65535;
        } else {
            i3 = aVar.GG[0] & 255;
        }
        a aVar2 = FarmGameEngine.aZf[0];
        if (w.WY) {
            i4 = aVar2.GF[0] & 65535;
        } else {
            i4 = aVar2.GH[0] & 255;
        }
        int i5 = i & 32767;
        a aVar3 = (32768 & i) != 0 ? FarmGameEngine.atf[i5] : FarmGameEngine.aZf[i5];
        if (aVar3.bG(i2) > 0) {
            aVar3.a(ape, i2, this.aol, this.aom, 0, false, false);
            ape[0] = (ape[0] / i3) * i3;
            ape[1] = (ape[1] / i4) * i4;
            ape[2] = (((ape[2] + i3) - 1) / i3) * i3;
            ape[3] = (((ape[3] + i4) - 1) / i4) * i4;
            o(ape[0], ape[1], ape[2], ape[3]);
            return;
        }
        String str = "Actor_RegisterClipRect> sprite " + i + " frame " + i2 + " doesn't have any rect for clipping";
    }

    static void eX(int i) {
        boolean z = false;
        for (FarmEntity chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 10) {
                z = true;
            }
            chVar.oR();
            chVar.oQ();
            chVar.oS();
            chVar.oU();
        }
        if (z) {
            aa.a(GameRuntime.Qj, 0, 0, 0);
        }
    }

    void pd() {
        int i;
        if (this.Bj == 0) {
            if (this.alI > 1.0f) {
                this.aol -= (int) (this.alK * this.alI);
                this.aom -= (int) (this.alL * this.alI);
                this.alI -= (this.alI * this.alJ) * (GameRuntime.Qy / 100.0f);
            }
            int i2 = this.alE;
            int i3 = this.alG;
            int iKH = this.alF - ((FarmGameEngine.kH() * 100) / FarmGameEngine.aYP);
            int iKI = this.alH - ((FarmGameEngine.kI() * 100) / FarmGameEngine.aYP);
            if (this.aol < i2) {
                this.aol = i2;
            } else if (this.aol > iKH) {
                this.aol = iKH;
            }
            if (this.aom < i3) {
                this.aom = i3;
            } else if (this.aom > iKI) {
                this.aom = iKI;
            }
        } else if (this.Bj == 1) {
            this.alM -= GameRuntime.Qy;
            int[] iArr = new int[2];
            FarmGameEngine.c(new int[]{FarmGameEngine.kH() >> 1, FarmGameEngine.kI() >> 1}, iArr);
            int i4 = this.aoE - iArr[0];
            int i5 = this.aoF - iArr[1];
            if (this.alM > 0) {
                i = (GameRuntime.Qy * 100) / this.alM;
                if (i > 100) {
                    i = 100;
                }
            } else {
                this.alM = 0;
                this.Bj = 0;
                if (FarmGameEngine.aYS == 1005) {
                    FarmGameEngine.axW = true;
                }
                if (FarmGameEngine.aum) {
                    String strDt = FarmGameEngine.dt(12393);
                    if (strDt == null) {
                        strDt = "";
                    }
                    FarmGameEngine.aMR = strDt;
                    if (FarmGameEngine.ayK[0]) {
                        String str = FarmGameEngine.aMR;
                        String strDt2 = FarmGameEngine.dt(FarmGameEngine.EI() + 320);
                        if (strDt2 == null) {
                            strDt2 = "";
                        }
                        FarmGameEngine.aMR = FarmGameEngine.l(str, "Acre", strDt2);
                    } else {
                        String str2 = FarmGameEngine.aMR;
                        String strDt3 = FarmGameEngine.dt(320);
                        if (strDt3 == null) {
                            strDt3 = "";
                        }
                        FarmGameEngine.aMR = FarmGameEngine.l(str2, "Acre", strDt3);
                    }
                    FarmGameEngine.b(FarmGameEngine.azd[FarmGameEngine.EI() + 1], 0, -80);
                    FarmGameEngine.m(FarmGameEngine.aMR, 7, 0);
                    FarmGameEngine.aum = false;
                }
                i = 100;
            }
            this.aol = ((i4 * i) / 100) + this.aol;
            this.aom = ((i * i5) / 100) + this.aom;
            int i6 = this.alE;
            int i7 = this.alG;
            int iKH2 = this.alF - ((FarmGameEngine.kH() * 100) / FarmGameEngine.aYP);
            int iKI2 = this.alH - ((FarmGameEngine.kI() * 100) / FarmGameEngine.aYP);
            if (this.aol < i6) {
                this.aol = i6;
            } else if (this.aol > iKH2) {
                this.aol = iKH2;
            }
            if (this.aom < i7) {
                this.aom = i7;
            } else if (this.aom > iKI2) {
                this.aom = iKI2;
            }
        }
        aa.u(0, this.aol, this.aom);
        aa.abW = FarmGameEngine.aYP;
    }

    void oR() {
        int i;
        if (this.Kx != 10 || FarmGameEngine.aZj.aom >= 648) {
            return;
        }
        float f = (648 - FarmGameEngine.aZj.aom) / 648.0f;
        if (648.0f - (f * 215.0f) > 0.0f) {
            i = (int) ((648.0f - (f * 215.0f)) + 0.5f);
        } else {
            i = 648.0f - (f * 215.0f) < 0.0f ? (int) ((648.0f - (f * 215.0f)) - 0.5f) : 0;
        }
        int i2 = i - FarmGameEngine.aZj.aom;
        if (FarmGameEngine.Mt()) {
            FarmGameEngine.dk(-15287308);
        } else {
            FarmGameEngine.dk(-15682062);
        }
        GameRuntime.b(GameRuntime.Qj, 0.0f, 0.0f, aa.abT[0][5], i2, true);
    }

    void pV() {
        if ((GameRuntime.L(0, 100) & 1) == 0) {
            this.aol = GameRuntime.L(aa.abT[0][5] >> 1, aa.abT[0][5]);
            this.aom = aa.abT[0][6];
        } else {
            this.aol = aa.abT[0][5];
            this.aom = GameRuntime.L(aa.abT[0][6] >> 1, aa.abT[0][6]);
        }
        this.aoE = 0;
        this.aoF = 0;
    }

    void pa() {
        this.alE = 0;
        this.alG = 0;
        this.alF = aa.abT[0][5];
        this.alH = aa.abT[0][6];
    }

    void aK(int i, int i2) {
        this.aoE = i;
        this.aoF = i2;
        if (Math.abs(this.aoE - this.aol) > 180 || Math.abs(this.aoF - this.aom) > 180) {
            this.aol += ((this.aoE - this.aol) * 80) / 100;
            this.aom += ((this.aoF - this.aom) * 80) / 100;
        }
        fg(1);
    }

    void aQ(int i, int i2) {
        if (this.anj) {
        }
        int[] iArr = {i, i2};
        if (this.aoZ == null) {
            for (int i3 = 0; i3 < 9216; i3++) {
                FarmGameEngine.aAQ[i3] = (byte) (FarmGameEngine.aAP[i3] & 1);
            }
            this.aoZ = new z();
            this.aoZ.a(96, 96, FarmGameEngine.aAQ, 14, 10, 10, 8);
        }
        int[] iArr2 = new int[2];
        FarmGameEngine.f(iArr, iArr2);
        iArr2[0] = iArr2[0] < 0 ? 0 : iArr2[0] > 95 ? 95 : iArr2[0];
        iArr2[1] = iArr2[1] < 0 ? 0 : iArr2[1] > 95 ? 95 : iArr2[1];
        String str = "targetGridPos x : " + iArr2[0] + " y : " + iArr2[1];
        int[] iArr3 = new int[2];
        FarmGameEngine.f(new int[]{this.aol, this.aom}, iArr3);
        iArr3[0] = iArr3[0] < 0 ? 0 : iArr3[0] > 95 ? 95 : iArr3[0];
        iArr3[1] = iArr3[1] < 0 ? 0 : iArr3[1] > 95 ? 95 : iArr3[1];
        String str2 = "characterGridPos x : " + iArr3[0] + " y : " + iArr3[1];
        this.aoZ.c(iArr3[0], iArr3[1], 0, iArr2[0], iArr2[1]);
        int iLK = this.aoZ.lK();
        this.apb = GameRuntime.Rk;
        this.apa = 0;
        if (this.Kx != 42 && (this.Kx != 54 || ((!this.anw || !this.ann) && !this.anj && !this.ank))) {
            if (this.Kx == 54) {
                if (iLK >= 2) {
                    this.apa = iLK - 1;
                    String str3 = "[PathFinding] m_pathCurNode : " + this.apa;
                } else {
                    this.apa = 0;
                }
                if (this.apa == 0) {
                    fp(0);
                    return;
                }
                this.aoE = i;
                this.aoF = i2;
                fp(1);
                return;
            }
            return;
        }
        if (iLK > 0) {
            this.apa = iLK - 1;
        }
        if (this.apa > 2) {
            this.apa = 2;
            int[] iArr4 = new int[2];
            FarmGameEngine.g(new int[]{this.aoZ.dO(this.apa), this.aoZ.dP(this.apa)}, iArr4);
            this.aol = iArr4[0];
            this.aom = iArr4[1];
            return;
        }
        if (this.Kx == 54 && ((this.anw && this.ann) || this.anj || this.ank)) {
            if (Math.abs(this.aoE - this.aol) > 40 || Math.abs(this.aoF - this.aom) > 40) {
                this.aol += ((this.aoE - this.aol) * 90) / 100;
                this.aom += ((this.aoF - this.aom) * 90) / 100;
            }
        } else if (Math.abs(this.aoE - this.aol) > 180 || Math.abs(this.aoF - this.aom) > 180) {
            this.aol += ((this.aoE - this.aol) * 80) / 100;
            this.aom += ((this.aoF - this.aom) * 80) / 100;
        }
        this.apb = 0;
    }

    void pG() {
        if (this.aoO == null) {
            return;
        }
        if (this.aoR != null) {
            int i = this.aoR.aaP;
            if (i != 0) {
                aa aaVar = this.aoR;
                int i2 = this.aot;
                int i3 = this.aou - 50;
                aaVar.aaL = i2;
                aaVar.aaM = i3;
            } else if (this.amW == 0) {
                j jVarBY = j.bY(0);
                this.amW = (short) jVarBY.KW[this.aoC][30];
                a aVar = FarmGameEngine.aZf[jVarBY.KW[this.aoC][31]];
                int iV = aVar.v(this.amW, 0);
                this.amZ = aVar.bw(iV);
                this.ana = aVar.bx(iV);
                this.aoP = this.amZ;
                this.aoQ = this.ana;
            }
            aa aaVar2 = this.aoR;
            int i4 = FarmGameEngine.aYP;
            if (w.Zo) {
                aaVar2.aaW = i4;
            } else {
                GameRuntime.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            if (this.aoR.lQ()) {
                if (i == 1) {
                    if (FarmGameEngine.currentTutorialStepIndex == 18 && TutorialFlowController.callbackCurrent(tutorialGrowthEpoch, FarmGameEngine.currentTutorialStepIndex, FarmGameEngine.aYS, FarmGameEngine.awV)) {
                        FarmGameEngine.axW = true;
                    }
                    if (this.anw && FarmGameEngine.axj != -1) {
                        FarmGameEngine.aa(FarmGameEngine.axj, 1, 21);
                        FarmGameEngine.axj = -1;
                    }
                }
                this.aoR = null;
            } else {
                this.aoR.dV(GameRuntime.Qy);
            }
        }
        if ((this.aoR == null || this.aoR.aaO != FarmGameEngine.aZf[16]) && this.amP) {
            this.aoR = new aa(FarmGameEngine.aZf[16], this.aot, this.aou);
            this.aoR.Y(0, -1);
            this.aoR.aaY = 1;
        } else if (this.aoR != null && !this.amP && this.aoR.aaO == FarmGameEngine.aZf[16]) {
            this.aoR = null;
        }
        if (this.aoS != null) {
            int i5 = this.aoS.aaP;
            aa aaVar3 = this.aoS;
            int i6 = this.aot;
            int i7 = this.aou - 50;
            aaVar3.aaL = i6;
            aaVar3.aaM = i7;
            aa aaVar4 = this.aoS;
            int i8 = FarmGameEngine.aYP;
            if (w.Zo) {
                aaVar4.aaW = i8;
            } else {
                GameRuntime.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            if (this.aoS.lQ()) {
                if (i5 == 1) {
                    if (FarmGameEngine.currentTutorialStepIndex == 18 && TutorialFlowController.callbackCurrent(tutorialGrowthEpoch, FarmGameEngine.currentTutorialStepIndex, FarmGameEngine.aYS, FarmGameEngine.awV)) {
                        FarmGameEngine.axW = true;
                    }
                    if (this.anw && FarmGameEngine.axj != -1) {
                        FarmGameEngine.aa(FarmGameEngine.axj, 1, 21);
                        FarmGameEngine.axj = -1;
                    }
                }
                this.aoS = null;
            } else {
                this.aoS.dV(GameRuntime.Qy);
            }
        }
        if (fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
            if (FarmGameEngine.FC() >= 10 && FarmGameEngine.aZB == 0 && !FarmGameEngine.auj && FarmGameEngine.lM(FarmGameEngine.aZQ[298]) && !FarmGameEngine.lN(GLURLConnection.HTTP_FOUND)) {
                String str = "time when active game :: " + (FarmGameEngine.bbp / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
                String str2 = "m_maxTimeThieveAppearPerDay :: " + FarmGameEngine.awo;
                String str3 = "m_timeAppearPerDay :: " + FarmGameEngine.awp;
                if (FarmGameEngine.axt == 1) {
                    if (this.anj && (FarmGameEngine.MD() / 1000) - this.amC >= 86400) {
                        FarmGameEngine.asQ = true;
                        FarmGameEngine.awp = 1;
                        this.amC = FarmGameEngine.MD() / 1000;
                    }
                } else if (FarmGameEngine.axt == 2) {
                    if (this.ank && (FarmGameEngine.MD() / 1000) - this.amC >= 86400) {
                        FarmGameEngine.asQ = true;
                        FarmGameEngine.awp = 1;
                        this.amC = FarmGameEngine.MD() / 1000;
                    }
                } else if (FarmGameEngine.axt == 0 && FarmGameEngine.avD == -1 && (FarmGameEngine.bbp / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS >= FarmGameEngine.awn || ((FarmGameEngine.axk || FarmGameEngine.axl) && FarmGameEngine.bbp / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS > 10))) {
                    if (FarmGameEngine.awm == -1) {
                        int iL = 0;
                        if (FarmGameEngine.aMg != null && FarmGameEngine.aMg.pB() >= 3) {
                            iL = GameRuntime.L(0, 10);
                        }
                        if (iL <= 5) {
                            FarmGameEngine.awm = 0;
                        } else {
                            FarmGameEngine.awm = 1;
                        }
                    }
                    if (FarmGameEngine.axk) {
                        FarmGameEngine.awm = 0;
                    } else if (FarmGameEngine.axl) {
                        FarmGameEngine.awm = 1;
                    }
                    if (this.anj && FarmGameEngine.awm == 0) {
                        if (this.anl) {
                            boolean z = true;
                            long jMD = (FarmGameEngine.MD() / 1000) - this.amC;
                            if (FarmGameEngine.awp < FarmGameEngine.awo) {
                                FarmGameEngine.awp++;
                                z = false;
                                if (jMD >= 86400) {
                                    FarmGameEngine.asQ = true;
                                    FarmGameEngine.awp = 1;
                                    this.amC = FarmGameEngine.MD() / 1000;
                                }
                            }
                            if (FarmGameEngine.awp >= FarmGameEngine.awo && jMD >= 86400) {
                                FarmGameEngine.asQ = true;
                                FarmGameEngine.awp = 0;
                                this.amC = FarmGameEngine.MD() / 1000;
                            }
                            if (FarmGameEngine.axk && FarmGameEngine.axt == 0) {
                                this.ani = false;
                                FarmGameEngine.axk = false;
                            } else if (FarmGameEngine.axt == 0 && !z) {
                                this.ani = false;
                                fr(151148);
                            }
                            FarmGameEngine.awm = -1;
                        }
                    } else if (this.ank && FarmGameEngine.awm == 1 && this.anm) {
                        boolean z2 = true;
                        long jMD2 = (FarmGameEngine.MD() / 1000) - this.amC;
                        if (FarmGameEngine.awp < FarmGameEngine.awo) {
                            FarmGameEngine.awp++;
                            z2 = false;
                            if (jMD2 >= 86400) {
                                FarmGameEngine.asQ = true;
                                FarmGameEngine.awp = 1;
                                this.amC = FarmGameEngine.MD() / 1000;
                            }
                        }
                        if (FarmGameEngine.awp >= FarmGameEngine.awo && jMD2 >= 86400) {
                            FarmGameEngine.asQ = true;
                            FarmGameEngine.awp = 0;
                            this.amC = FarmGameEngine.MD() / 1000;
                        }
                        if (FarmGameEngine.axl && FarmGameEngine.axt == 0) {
                            this.ani = false;
                            FarmGameEngine.axl = false;
                        } else if (FarmGameEngine.axt == 0 && !z2) {
                            this.ani = false;
                            fr(151149);
                        }
                        FarmGameEngine.awm = -1;
                    }
                }
                if (FarmGameEngine.asQ) {
                    FarmGameEngine.asQ = false;
                    FarmEntity chVarHA = FarmGameEngine.hA(73);
                    FarmEntity chVarHA2 = FarmGameEngine.hA(74);
                    if (chVarHA.amC > chVarHA2.amC) {
                        chVarHA2.amC = chVarHA.amC;
                    } else {
                        chVarHA.amC = chVarHA2.amC;
                    }
                }
                if (FarmGameEngine.bbp / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS > FarmGameEngine.awn) {
                    FarmGameEngine.bbp = FarmGameEngine.awn * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
                }
            } else if ((FarmGameEngine.aYS == 1004 || FarmGameEngine.aYS == 1046) && this.anj) {
                FarmGameEngine.axW = true;
                this.ani = false;
            } else if (FarmGameEngine.auj) {
                if (this.anj && FarmGameEngine.auk == 0 && !FarmGameEngine.aul && this.aol == 0 && this.aom == 0) {
                    FarmGameEngine.aul = true;
                    this.ani = false;
                    if (FarmGameEngine.aYS == 1055) {
                        FarmGameEngine.axW = true;
                    }
                    fr(151148);
                } else if (this.ank && FarmGameEngine.auk == 1 && !FarmGameEngine.aul && this.aol == 0 && this.aom == 0) {
                    FarmGameEngine.aul = true;
                    this.ani = false;
                    fr(151149);
                }
            } else if (((FarmGameEngine.lM(FarmGameEngine.aZQ[298]) && !FarmGameEngine.lN(GLURLConnection.HTTP_FOUND)) || FarmGameEngine.FC() < 10) && FarmGameEngine.aZB <= 10 && (this.aol != 0 || this.aom != 0)) {
                aN(0, 0);
            }
        }
        if (!fz(AdRequest.MAX_CONTENT_URL_LENGTH) && !fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
            if (this.aoO.lQ()) {
                fp(0);
                return;
            }
            return;
        }
        if (qs()) {
            if (!this.amT && FarmGameEngine.avE != null && FarmGameEngine.avE != this && this.aol > FarmGameEngine.aZW + 150 && this.aol < FarmGameEngine.aZW + 530 && this.aom > FarmGameEngine.aZX + 340 && this.aom < FarmGameEngine.aZX + 570) {
                this.amT = true;
                aM(FarmGameEngine.aZW + 150, FarmGameEngine.aZX + 446);
            }
            if (this.amT && this.aol == this.aoE && this.aom == this.aoF) {
                this.amT = false;
            }
            if (this.amU && this.aol == this.aoE && this.aom == this.aoF) {
                this.amU = false;
            }
        }
        pH();
        if (!fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) && !fz(131072) && this.Bj != 13 && this.Bj != 15 && this.Bj != 8 && (!this.anw || !FarmGameEngine.axK)) {
            if (!fz(32) || fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
                if (!fz(4) && !fz(16) && !fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
                    long jMD3 = FarmGameEngine.MD();
                    if (FarmGameEngine.currentTutorialStepIndex == 33) {
                        this.amB = jMD3;
                        String str4 = "set last animal care delay:" + (jMD3 / 1000);
                    }
                    long j = (jMD3 - this.amB) / 1000;
                    if (!fz(256) && (FarmGameEngine.lN(20) || FarmGameEngine.lM(FarmGameEngine.aZQ[20]))) {
                        if (j > (FarmGameEngine.ii(this.amE) * 3) / 4 && this.amB > 0 && !this.amM) {
                            this.amM = true;
                            if (!this.amL) {
                                int iL2 = GameRuntime.L(0, 100);
                                String str5 = "Check animal request clean.!!!!!" + iL2;
                                if (iL2 < 35) {
                                    fq(256);
                                    this.amL = true;
                                }
                            }
                        } else if (j > (FarmGameEngine.ii(this.amE) >> 1) && this.amB > 0 && !this.amN) {
                            this.amN = true;
                            if (!this.amL) {
                                int iL3 = GameRuntime.L(0, 100);
                                String str6 = "Check animal request clean2.!!!!!" + iL3;
                                if (iL3 < 35) {
                                    fq(256);
                                    this.amL = true;
                                }
                            }
                        } else if (j > (FarmGameEngine.ii(this.amE) >> 2) && this.amB > 0 && !this.amO) {
                            this.amO = true;
                            if (!this.amL) {
                                int iL4 = GameRuntime.L(0, 100);
                                String str7 = "Check animal request clean3.!!!!!" + iL4;
                                if (iL4 < 35) {
                                    fq(256);
                                    this.amL = true;
                                }
                            }
                        }
                    }
                    if (!this.anw && j >= FarmGameEngine.ii(this.amE) && this.amB != 0) {
                        if (FarmGameEngine.currentTutorialStepIndex == 35) {
                            FarmGameEngine.axW = true;
                        }
                        if (FarmGameEngine.currentTutorialStepIndex != 34) {
                            String str8 = j + " -------------------------------ANIMAL_READY_PRODUCE------------------------- " + this.amE;
                            if (j != 0 || FarmGameEngine.ii(this.amE) >= 0) {
                                fq(16);
                                FarmGameEngine.a(false, (FarmEntity) null);
                            } else {
                                pM();
                            }
                        }
                    }
                    if (this.anw && j >= FarmGameEngine.ii(this.amE) && this.amB != 0 && FarmGameEngine.currentTutorialStepIndex != 34) {
                        pM();
                        fp(2);
                    }
                    if (FarmGameEngine.aZo == this && FarmGameEngine.ii(this.amE) != 0) {
                        FarmGameEngine.ba((int) j, FarmGameEngine.ii(this.amE));
                        this.aoX = A(this.amH, (int) (((long) FarmGameEngine.ii(this.amE)) - j), FarmGameEngine.ii(this.amE));
                    }
                }
            } else if (!this.anw) {
                long jMD4 = FarmGameEngine.MD();
                long j2 = (jMD4 / 1000) - this.amJ;
                if (this.and && this.ane > 0) {
                    this.amI = FarmGameEngine.ih((int) (((jMD4 - this.ane) / 1000) + ((long) (j.bY(0).KW[this.aoC][14] * 60))));
                }
                if (FarmGameEngine.ii(this.amI) > 0 && FarmGameEngine.aZo == this) {
                    FarmGameEngine.ba((int) j2, FarmGameEngine.ii(this.amI));
                    this.aoX = A(this.amH, (int) (((long) FarmGameEngine.ii(this.amI)) - j2), FarmGameEngine.ii(this.amI));
                }
                if (j2 >= FarmGameEngine.ii(this.amI)) {
                    pR();
                }
            } else if (FarmGameEngine.avD == -1 && !fz(4) && this.Bj == 0 && FarmGameEngine.aZB == 0) {
                pM();
                fp(2);
            }
            if (fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) && FarmGameEngine.axt != 0 && !this.anw && (this.aoM == null || this.aoM.Kx == -1)) {
                fq(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
                return;
            }
            if (fz(4) && (this.aoM == null || this.aoM.Kx == -1)) {
                fq(4);
                return;
            }
            if (fz(16) && (this.aoM == null || this.aoM.Kx == -1)) {
                fq(16);
                return;
            }
            if (fz(256) && (this.aoM == null || this.aoM.Kx == -1)) {
                fq(256);
                return;
            }
            if (fz(GLURLConnection.BUFFER_SIZE) && this.anr && (this.aoM == null || this.aoM.Kx == -1)) {
                fq(GLURLConnection.BUFFER_SIZE);
                return;
            } else if ((this.anp == 1 || this.anp == 2) && this.anr && (this.aoM == null || this.aoM.Kx == -1)) {
                fq(GLURLConnection.BUFFER_SIZE);
                return;
            }
        } else if (fz(131072) && (this.aoM == null || this.aoM.Kx == -1)) {
            fq(131072);
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To;
        }
        if (jCurrentTimeMillis - this.amv <= 7000 || FarmGameEngine.currentEngineState() != 18) {
            return;
        }
        if (FarmGameEngine.avE == null || FarmGameEngine.avE != this) {
            if (!this.anw && GameRuntime.L(0, 100) < 10) {
                FarmGameEngine.gY(this.aoY);
            }
            this.amv = FarmGameEngine.MD();
        }
    }

    void pf() {
        if (this.aoC == 18) {
            return;
        }
        if ((this.aoC == 12 || this.aoC == 11) && FarmGameEngine.aZB == 0 && fz(AdRequest.MAX_CONTENT_URL_LENGTH)) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To;
            }
            long j = (jCurrentTimeMillis / 1000) - this.alV;
            if (j < FarmGameEngine.ii(alU) && FarmGameEngine.aZo == this) {
                FarmGameEngine.ba((int) j, FarmGameEngine.ii(alU));
                this.aoX = FarmGameEngine.gf((int) (((long) FarmGameEngine.ii(alU)) - j));
            }
            if (this.alW || j < FarmGameEngine.ii(alU)) {
                return;
            }
            this.alW = true;
            fx(16);
            e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
            if (FarmGameEngine.ss()) {
                FarmGameEngine.a(false, (FarmEntity) null);
                return;
            }
            return;
        }
        if (this.aoC == 15 && FarmGameEngine.aTK) {
            aa aaVar = this.aoO;
            if (FarmGameEngine.EN() || FarmGameEngine.EM()) {
                if (this.alZ >= 0) {
                    this.alZ -= GameRuntime.Qy;
                    if (this.alZ < 0) {
                        this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aot, this.aou);
                        this.aoO.Y(0, 1);
                    }
                } else if (this.aoO != null) {
                    this.aoO.dV(GameRuntime.Qy);
                    if (this.aoO.lQ()) {
                        this.aoO = null;
                        this.alZ = GameRuntime.L(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, 4000);
                    }
                }
            } else if (FarmGameEngine.aNU > 0 || FarmGameEngine.aNY > 0) {
                if (this.alZ >= 0) {
                    this.alZ -= GameRuntime.Qy;
                    if (this.alZ < 0) {
                        this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aot, this.aou);
                        this.aoO.Y(0, 1);
                    }
                } else if (this.aoO != null) {
                    this.aoO.dV(GameRuntime.Qy);
                    if (this.aoO.lQ()) {
                        this.aoO = null;
                        this.alZ = GameRuntime.L(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, 4000);
                    }
                }
            } else if (this.aoO != null) {
                this.aoO = null;
            }
            if (aaVar != this.aoO) {
                qr();
                return;
            }
            return;
        }
        if (this.aoC == 9) {
            aa aaVar2 = this.aoO;
            if (FarmGameEngine.aZB != 0 || !FarmGameEngine.EQ()) {
                if (this.aoO != null) {
                    this.aoO = null;
                    qr();
                }
                if (this.ama) {
                    qq();
                    this.ama = false;
                    return;
                }
                return;
            }
            if (!this.ama && FarmGameEngine.lM(FarmGameEngine.aZQ[0]) && FarmGameEngine.lM(FarmGameEngine.aZQ[1])) {
                this.ama = true;
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
            }
            if (this.alZ >= 0) {
                this.alZ -= GameRuntime.Qy;
                if (this.alZ < 0) {
                    this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aot, this.aou);
                    this.aoO.Y(0, 1);
                    return;
                }
                return;
            }
            if (this.aoO != null) {
                this.aoO.dV(GameRuntime.Qy);
                if (this.aoO.lQ()) {
                    this.alZ = GameRuntime.L(10000, 30000);
                    this.aoO = null;
                    return;
                }
                return;
            }
            return;
        }
        if (this.aoC == 2) {
            if (!FarmGameEngine.GT()) {
                if (this.aoR != null) {
                    this.aoR = null;
                    return;
                }
                return;
            }
            if (this.aoR == null) {
                a aVar = FarmGameEngine.aZf[this.aoG];
                int iV = aVar.v(1, 0);
                this.aoP = aVar.bw(iV);
                this.aoQ = aVar.bx(iV);
                this.aoR = new aa(FarmGameEngine.aZf[147], this.aot, this.aou);
            }
            if (this.alZ >= 0) {
                this.alZ -= GameRuntime.Qy;
                if (this.alZ < 0) {
                    this.aoR.Y(0, 1);
                    return;
                }
                return;
            }
            this.aoR.dV(GameRuntime.Qy);
            if (this.aoR.lQ()) {
                this.alZ = GameRuntime.L(3000, 4000);
                return;
            }
            return;
        }
        if (this.aoC == 1) {
            if (FarmGameEngine.aZB == 0) {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To;
                }
                long j2 = jCurrentTimeMillis2 / 1000;
                if (!FarmGameEngine.IL() || !FarmGameEngine.aTK) {
                    if (this.aoR != null) {
                        this.aoR = null;
                        return;
                    }
                    return;
                } else {
                    if (this.aoR != null) {
                        this.aoR.dV(GameRuntime.Qy);
                        return;
                    }
                    a aVar2 = FarmGameEngine.aZf[this.aoG];
                    int iV2 = aVar2.v(FarmGameEngine.aZD < 4 ? 4 : 1, 0);
                    this.aoP = aVar2.bw(iV2);
                    this.aoQ = aVar2.bx(iV2);
                    this.aoR = new aa(FarmGameEngine.aZf[148], this.aot, this.aou);
                    this.aoR.Y(0, -1);
                    return;
                }
            }
            return;
        }
        if (this.aoC == 28) {
            if (!this.alW && FarmGameEngine.Hi()) {
                this.alW = true;
                fx(16);
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
            }
            long jCurrentTimeMillis3 = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis3 = (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To;
            }
            long j3 = jCurrentTimeMillis3 / 1000;
            boolean z = false;
            for (int i = 0; !z && i < FarmGameEngine.aGm; i++) {
                z = FarmGameEngine.aGo[i] && FarmGameEngine.aGs[i] > 0 && j3 < FarmGameEngine.aGs[i] + ((long) FarmGameEngine.ii(FarmGameEngine.aGr[i]));
            }
            if (z) {
                if (this.aoO != null) {
                    this.aoO.dV(GameRuntime.Qy);
                    return;
                } else {
                    this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aot, this.aou);
                    this.aoO.Y(1, -1);
                    return;
                }
            }
            if (FarmGameEngine.bL(0, 119) >= FarmGameEngine.aGa) {
                this.aoH = 0;
            } else {
                this.aoH = 7;
            }
            if (this.aoO != null) {
                this.aoO = null;
                return;
            }
            return;
        }
        if (this.aoC == 14) {
            if (!this.alW && FarmGameEngine.Hh()) {
                this.alW = true;
                fx(16);
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
            }
            long jCurrentTimeMillis4 = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis4 = (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To;
            }
            long j4 = jCurrentTimeMillis4 / 1000;
            boolean z2 = false;
            for (int i2 = 0; !z2 && i2 < FarmGameEngine.GY(); i2++) {
                z2 = FarmGameEngine.aHa[i2] > 0 && j4 < FarmGameEngine.aHa[i2] + ((long) FarmGameEngine.ii(FarmGameEngine.aGZ[i2]));
            }
            if (!z2) {
                if (this.aoO != null) {
                    this.aoO = null;
                    return;
                }
                return;
            } else if (this.aoO != null) {
                this.aoO.dV(GameRuntime.Qy);
                return;
            } else {
                this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aot, this.aou);
                this.aoO.Y(1, -1);
                return;
            }
        }
        if (this.aoC == 17) {
            if (this.alZ >= 0) {
                this.alZ -= GameRuntime.Qy;
                if (this.alZ < 0) {
                    this.aoO = new aa(FarmGameEngine.aZf[this.aoG], this.aot, this.aou);
                    this.aoO.Y(0, 1);
                    if (qs()) {
                        FarmGameEngine.gX(29);
                    }
                }
            } else if (this.aoO != null) {
                this.aoO.dV(GameRuntime.Qy);
                if (this.aoO.lQ()) {
                    this.aoO = null;
                    this.alZ = GameRuntime.L(10000, 30000);
                }
            }
            if (FarmGameEngine.aZB == 0) {
                long jCurrentTimeMillis5 = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis5 = (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To;
                }
                FarmGameEngine.aUl = jCurrentTimeMillis5;
                if (fz(AdRequest.MAX_CONTENT_URL_LENGTH) && ((this.aoM == null || this.aoM.Kx == -1) && FarmGameEngine.aUl > FarmGameEngine.aUm)) {
                    e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
                } else {
                    if (!fz(AdRequest.MAX_CONTENT_URL_LENGTH) || this.aoM == null || this.aoM.Kx == -1 || FarmGameEngine.aUl > FarmGameEngine.aUm) {
                        return;
                    }
                    qq();
                }
            }
        }
    }

    void pZ() {
        int i;
        int i2;
        int i3;
        if (this.anG == 37 || this.anG == 36 || this.anG == 6 || this.anG == 67 || this.anG == 68 || this.anG == 69 || this.anG == 66 || this.anG == 67) {
            i = this.anM;
        } else {
            i = this.anG == 59 ? (this.anM & (-65536)) >> 16 : 0;
        }
        if (i > 0 || this.anG == 67 || this.anG == 67 || this.anG == 68 || this.anG == 69) {
            GameRuntime.l(18, 5, 1);
            if ((((this.aol + this.aow) - FarmGameEngine.aZj.aol) * FarmGameEngine.aYP) / 100.0f > 0.0f) {
                i2 = (int) (((((this.aol + this.aow) - FarmGameEngine.aZj.aol) * FarmGameEngine.aYP) / 100.0f) + 0.5f);
            } else {
                i2 = ((float) (((this.aol + this.aow) - FarmGameEngine.aZj.aol) * FarmGameEngine.aYP)) / 100.0f < 0.0f ? (int) (((((this.aol + this.aow) - FarmGameEngine.aZj.aol) * FarmGameEngine.aYP) / 100.0f) - 0.5f) : 0;
            }
            if ((((this.aom + this.aox) - FarmGameEngine.aZj.aom) * FarmGameEngine.aYP) / 100.0f > 0.0f) {
                i3 = (int) (((((this.aom + this.aox) - FarmGameEngine.aZj.aom) * FarmGameEngine.aYP) / 100.0f) + 0.5f);
            } else {
                i3 = ((float) (((this.aom + this.aox) - FarmGameEngine.aZj.aom) * FarmGameEngine.aYP)) / 100.0f < 0.0f ? (int) (((((this.aom + this.aox) - FarmGameEngine.aZj.aom) * FarmGameEngine.aYP) / 100.0f) - 0.5f) : 0;
            }
            FarmGameEngine.a((i > 0 ? "+" : "") + FarmGameEngine.a(i, FarmGameEngine.aYU, " "), i2 + 10, i3 + 10, 0, 0, 0, 0, 2, 1, false, false);
            GameRuntime.l(18, 5, 0);
        }
    }

    void aE(int i, int i2) {
        if (oT()) {
            return;
        }
        int i3 = i & 32767;
        a aVar = (32768 & i) != 0 ? FarmGameEngine.atf[i3] : FarmGameEngine.aZf[i3];
        int iHw = this.aoI;
        if (aF(i, i2)) {
            if (aVar == null || i2 < 0) {
                String str = "!!!!![Actor] draw exec. sprite null.:" + i + ",frm:" + i2;
                return;
            }
            if (fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
                iHw += aVar.hw() >> 1;
            }
            aVar.bQ(iHw);
            if (FarmGameEngine.aYP == 100 || this.aoo == 2) {
                aVar.a(GameRuntime.Qj, i2, (float) this.aot, (float) this.aou, 0);
                return;
            }
            GameRuntime.l(18, 5, 1);
            aVar.b(GameRuntime.Qj, i2, this.aot, this.aou, 0, FarmGameEngine.aYP);
            GameRuntime.l(18, 5, 0);
        }
    }

    void oS() {
        int i;
        int i2;
        int i3;
        boolean z;
        int i4;
        if (oT()) {
            return;
        }
        if (fz(8) && (this.Kx == 11 || this.apk)) {
            int iHw = this.aoI;
            if (this.aoO != null && a(this.aoO.aaO, this.aoO.lN())) {
                if (fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
                    iHw += this.aoO.aaO.hw() >> 1;
                }
                this.aoO.aaO.bQ(iHw);
                aa aaVar = this.aoO;
                int i5 = this.aot;
                int i6 = this.aou;
                aaVar.aaL = i5;
                aaVar.aaM = i6;
                aa aaVar2 = this.aoO;
                int i7 = FarmGameEngine.aYP;
                if (w.Zo) {
                    aaVar2.aaW = i7;
                } else {
                    GameRuntime.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
                }
                GameRuntime.l(18, 5, 1);
                this.aoO.lR();
                GameRuntime.l(18, 5, 0);
            } else if (this.aoG > -1) {
                aE(this.aoG, this.aoH);
            }
            if (this.Kx == 50) {
                pg();
            }
            if (this.Kx == 47) {
                pZ();
            }
        }
        switch (this.Kx) {
            case 54:
                pI();
                break;
        }
        if (!this.aoT || FarmGameEngine.avE != this) {
            return;
        }
        if (FarmGameEngine.t(this) && aF(14, this.aoA)) {
            int i8 = this.aot;
            int i9 = this.aou;
            if (FarmGameEngine.j(this)) {
                i9 += 108;
            }
            r(14, this.aoA, i8, i9);
        }
        if (!FarmGameEngine.j(FarmGameEngine.avE)) {
            return;
        }
        if (FarmGameEngine.avE.aoC == 20) {
            i = 161;
            i2 = 162;
            i3 = 164;
            z = false;
        } else {
            i = 165;
            i2 = 166;
            i3 = 163;
            z = true;
        }
        int[] iArr = {FarmGameEngine.avE.aol, FarmGameEngine.avE.aom};
        int[] iArr2 = {i + 1, i};
        FarmGameEngine.f(iArr, iArr2);
        int i10 = iArr2[0];
        int i11 = iArr2[1];
        int i12 = 0;
        while (true) {
            int i13 = i12;
            if (i13 >= 6) {
                iArr2[0] = i10;
                iArr2[1] = i11 + 1;
                for (int i14 = 0; i14 < 6; i14++) {
                    FarmGameEngine.g(iArr2, iArr);
                    r(i2, 0, iArr[0] - FarmGameEngine.aZj.aol, iArr[1] - FarmGameEngine.aZj.aom);
                    iArr2[1] = iArr2[1] + 2;
                }
                iArr2[0] = i10 + 1;
                iArr2[1] = i11 + 12;
                for (int i15 = 0; i15 < 6; i15++) {
                    FarmGameEngine.g(iArr2, iArr);
                    r(i, 0, iArr[0] - FarmGameEngine.aZj.aol, iArr[1] - FarmGameEngine.aZj.aom);
                    iArr2[0] = iArr2[0] + 2;
                }
                iArr2[0] = i10 + 12;
                iArr2[1] = i11 + 1;
                for (int i16 = 0; i16 < 6; i16++) {
                    FarmGameEngine.g(iArr2, iArr);
                    r(i2, 0, iArr[0] - FarmGameEngine.aZj.aol, iArr[1] - FarmGameEngine.aZj.aom);
                    iArr2[1] = iArr2[1] + 2;
                }
                if (z) {
                    iArr2[0] = i10 + 3;
                    iArr2[1] = i11;
                    for (int i17 = 0; i17 < 6; i17 += 2) {
                        FarmGameEngine.g(iArr2, iArr);
                        r(168, 2, iArr[0] - FarmGameEngine.aZj.aol, iArr[1] - FarmGameEngine.aZj.aom);
                        iArr2[0] = iArr2[0] + 4;
                    }
                    iArr2[0] = i10;
                    iArr2[1] = i11 + 3;
                    for (int i18 = 0; i18 < 6; i18 += 2) {
                        FarmGameEngine.g(iArr2, iArr);
                        r(168, 3, iArr[0] - FarmGameEngine.aZj.aol, iArr[1] - FarmGameEngine.aZj.aom);
                        iArr2[1] = iArr2[1] + 4;
                    }
                    iArr2[0] = i10 + 3;
                    iArr2[1] = i11 + 12;
                    for (int i19 = 0; i19 < 6; i19 += 2) {
                        FarmGameEngine.g(iArr2, iArr);
                        r(168, 2, iArr[0] - FarmGameEngine.aZj.aol, iArr[1] - FarmGameEngine.aZj.aom);
                        iArr2[0] = iArr2[0] + 4;
                    }
                    iArr2[0] = i10 + 12;
                    iArr2[1] = i11 + 3;
                    for (int i20 = 0; i20 < 6; i20 += 2) {
                        FarmGameEngine.g(iArr2, iArr);
                        r(168, 3, iArr[0] - FarmGameEngine.aZj.aol, iArr[1] - FarmGameEngine.aZj.aom);
                        iArr2[1] = iArr2[1] + 4;
                    }
                    int[] iArr3 = {0, 0, 4, 0, 8, 0, 12, 0, 12, 4, 0, 4, 0, 8, 12, 8, 12, 12, 8, 12, 4, 12, 0, 12};
                    for (int i21 = 0; i21 < 12; i21++) {
                        iArr2[0] = iArr3[i21 * 2] + i10;
                        iArr2[1] = iArr3[(i21 * 2) + 1] + i11;
                        FarmGameEngine.g(iArr2, iArr);
                        r(168, (i21 % 2) + 0, iArr[0] - FarmGameEngine.aZj.aol, (iArr[1] - FarmGameEngine.aZj.aom) + 13);
                    }
                    return;
                }
                return;
            }
            FarmGameEngine.g(iArr2, iArr);
            if (i13 == 0) {
                iArr[1] = iArr[1] - 3;
                i4 = i3;
            } else {
                i4 = i;
            }
            r(i4, 0, iArr[0] - FarmGameEngine.aZj.aol, iArr[1] - FarmGameEngine.aZj.aom);
            iArr2[0] = iArr2[0] + 2;
            i12 = i13 + 1;
        }
    }

    void r(int i, int i2, int i3, int i4) {
        if (FarmGameEngine.aYP != 100) {
            GameRuntime.l(18, 5, 1);
            FarmGameEngine.aZf[i].b(GameRuntime.Qj, i2, i3, i4, 0, FarmGameEngine.aYP);
            GameRuntime.l(18, 5, 0);
            return;
        }
        FarmGameEngine.aZf[i].a(GameRuntime.Qj, i2, (float) i3, (float) i4, 0);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0048  */
    /* JADX WARN: Code duplicated, block: B:23:0x005a  */
    /* JADX WARN: Code duplicated, block: B:25:0x0060  */
    /* JADX WARN: Code duplicated, block: B:27:0x0076  */
    /* JADX WARN: Code duplicated, block: B:29:0x007e  */
    /* JADX WARN: Code duplicated, block: B:32:0x0093  */
    /* JADX WARN: Code duplicated, block: B:38:0x00a3  */
    boolean pu() {
        String strDt;
        String strDt2;
        if (this.amu) {
            return false;
        }
        if (this.Bj != 3 || FarmGameEngine.avP != 1) {
            if (this.Bj != 5 || GameRuntime.dy(0) == 3) {
                return false;
            }
            FarmGameEngine.a(4, this);
            return true;
        }
        if (FarmGameEngine.aAY) {
            return false;
        }
        String str = "m_countToStore : " + FarmGameEngine.aAX;
        int i = this.aoC != 23 ? 3 : 4;
        if (FarmGameEngine.aZB == 0) {
            if (fe(3) + FarmGameEngine.hF(5) + i <= FarmGameEngine.Fw()) {
                if (FarmGameEngine.aZB == 0 && i + FarmGameEngine.hF(5) + FarmGameEngine.aAX > FarmGameEngine.Fw()) {
                    if (FarmGameEngine.aYS != 1069) {
                        FarmGameEngine.aAY = true;
                        FarmGameEngine.c(false, 1, FarmGameEngine.avR);
                        FarmGameEngine.awH = false;
                        FarmGameEngine.avP = -1;
                        if (FarmGameEngine.hF(5) < FarmGameEngine.Fw()) {
                            strDt = FarmGameEngine.dt(486);
                            if (strDt == null) {
                                strDt = "";
                            }
                            String strL = FarmGameEngine.l(strDt, "%slots", "3");
                            strDt2 = FarmGameEngine.dt(485);
                            if (strDt2 == null) {
                                strDt2 = "";
                            }
                            FarmGameEngine.g(strDt2, strL, 7, 0);
                        } else {
                            FarmGameEngine.H(481, 487, 7, 0);
                        }
                        return false;
                    }
                }
            } else if (FarmGameEngine.aYS != 1069) {
                FarmGameEngine.aAY = true;
                FarmGameEngine.c(false, 1, FarmGameEngine.avR);
                FarmGameEngine.awH = false;
                FarmGameEngine.avP = -1;
                if (FarmGameEngine.hF(5) < FarmGameEngine.Fw()) {
                    strDt = FarmGameEngine.dt(486);
                    if (strDt == null) {
                        strDt = "";
                    }
                    String strL2 = FarmGameEngine.l(strDt, "%slots", "3");
                    strDt2 = FarmGameEngine.dt(485);
                    if (strDt2 == null) {
                        strDt2 = "";
                    }
                    FarmGameEngine.g(strDt2, strL2, 7, 0);
                } else {
                    FarmGameEngine.H(481, 487, 7, 0);
                }
                return false;
            }
        } else if (FarmGameEngine.aZB == 0) {
            if (FarmGameEngine.aYS != 1069) {
                FarmGameEngine.aAY = true;
                FarmGameEngine.c(false, 1, FarmGameEngine.avR);
                FarmGameEngine.awH = false;
                FarmGameEngine.avP = -1;
                if (FarmGameEngine.hF(5) < FarmGameEngine.Fw()) {
                    strDt = FarmGameEngine.dt(486);
                    if (strDt == null) {
                        strDt = "";
                    }
                    String strL3 = FarmGameEngine.l(strDt, "%slots", "3");
                    strDt2 = FarmGameEngine.dt(485);
                    if (strDt2 == null) {
                        strDt2 = "";
                    }
                    FarmGameEngine.g(strDt2, strL3, 7, 0);
                } else {
                    FarmGameEngine.H(481, 487, 7, 0);
                }
                return false;
            }
        }
        FarmGameEngine.a(1, this);
        return true;
    }

    void pb() {
        int i = 50;
        if (this.Bj == 0) {
            if (GameRuntime.dB(0)) {
                this.alv = this.aol;
                this.alw = this.aom;
                this.alx = GameRuntime.dG(0);
                this.aly = GameRuntime.dH(0);
                this.alA = true;
            } else if (this.alB || !GameRuntime.dC(0) || !this.alA || FarmGameEngine.awH) {
                if (GameRuntime.dA(0)) {
                    if (this.alA && FarmGameEngine.Fb() > 50) {
                        pe();
                    }
                    this.alA = false;
                }
            } else {
                this.aol = this.alv + (((this.alx - GameRuntime.dG(0)) * 100) / FarmGameEngine.aYP);
                this.aom = this.alw + (((this.aly - GameRuntime.dH(0)) * 100) / FarmGameEngine.aYP);
                if (FarmGameEngine.avP == 0 && !FarmGameEngine.awH) {
                    FarmGameEngine.avP = -1;
                    FarmGameEngine.c(false, 0, FarmGameEngine.avR);
                }
            }
            if (!GameRuntime.dF(0) || !GameRuntime.dF(1)) {
                if (this.alB) {
                    this.alB = false;
                    this.alA = false;
                    return;
                }
                return;
            }
            int iKH = FarmGameEngine.kH() >> 1;
            int iKI = FarmGameEngine.kI() >> 1;
            if (!this.alB) {
                this.alB = true;
                this.alz = FarmGameEngine.aYP;
                this.alC = Math.abs(GameRuntime.dG(0) - GameRuntime.dG(1));
                this.alD = Math.abs(GameRuntime.dH(0) - GameRuntime.dH(1));
                this.alv = this.aol + ((iKH * 100) / FarmGameEngine.aYP);
                this.alw = this.aom + ((iKI * 100) / FarmGameEngine.aYP);
            }
            int iAbs = Math.abs(GameRuntime.dG(0) - GameRuntime.dG(1));
            int iAbs2 = Math.abs(GameRuntime.dH(0) - GameRuntime.dH(1));
            int i2 = FarmGameEngine.aYP;
            if (this.alz + ((int) (((double) (iAbs - this.alC)) * 0.1d)) + ((int) (((double) (iAbs2 - this.alD)) * 0.1d)) >= 50) {
                i = (this.alz + ((int) (((double) (iAbs - this.alC)) * 0.1d))) + ((int) (((double) (iAbs2 - this.alD)) * 0.1d)) > 100 ? 100 : this.alz + ((int) (((double) (iAbs - this.alC)) * 0.1d)) + ((int) (((double) (iAbs2 - this.alD)) * 0.1d));
            }
            FarmGameEngine.aYP = i;
            if (FarmGameEngine.getDeviceName().equals("LG-P700") && FarmGameEngine.aYP <= 65) {
                FarmGameEngine.aYP = 65;
            }
            if (i2 != FarmGameEngine.aYP) {
                this.aol = this.alv - ((iKH * 100) / FarmGameEngine.aYP);
                this.aom = this.alw - ((iKI * 100) / FarmGameEngine.aYP);
            }
        }
    }

    void pc() {
        if (this.Bj == 0 && GameRuntime.dC(0)) {
            if (GameRuntime.dG(0) < 40) {
                this.aol -= GameRuntime.Qy * 1;
            } else if (GameRuntime.dG(0) > GameRuntime.kH() - 40) {
                this.aol += GameRuntime.Qy * 1;
            }
            if (GameRuntime.dH(0) < 40) {
                this.aom -= GameRuntime.Qy * 1;
            } else if (GameRuntime.dH(0) > GameRuntime.kI() - 40) {
                this.aom += GameRuntime.Qy * 1;
            }
        }
    }

    boolean oZ() {
        boolean z;
        if (this.Bj == 3 || FarmGameEngine.aCg != 0 || FarmGameEngine.Fb() >= 50) {
            return false;
        }
        int[] iArr = new int[2];
        int[] iArr2 = {GameRuntime.dG(0), GameRuntime.dH(0)};
        FarmGameEngine.c(iArr2, iArr);
        FarmGameEngine.f(iArr, iArr2);
        if (!GameRuntime.dA(0) || FarmGameEngine.avD != -1) {
            if (FarmGameEngine.aZB != 0 || FarmGameEngine.awX != 0 || !GameRuntime.dF(0) || FarmGameEngine.hB(FarmGameEngine.bE(iArr2[0], iArr2[1])) || FarmGameEngine.bE(iArr2[0], iArr2[1]) <= 0 || FarmGameEngine.avD != -1) {
                return false;
            }
            int iBE = FarmGameEngine.bE(iArr2[0], iArr2[1]);
            if (FarmGameEngine.bE(iArr2[0], iArr2[1]) <= 0 || FarmGameEngine.currentTutorialStepIndex != -1 || FarmGameEngine.ayM != null) {
                return false;
            }
            String str = "pressed expandable area... quest 1:" + FarmGameEngine.lM(FarmGameEngine.aZQ[0]) + ",q2:" + FarmGameEngine.lM(FarmGameEngine.aZQ[1]);
            FarmGameEngine.ayJ = iBE;
            FarmGameEngine.aze = true;
            return false;
        }
        if (FarmGameEngine.bD(iArr2[0], iArr2[1])) {
            aI(GameRuntime.dG(0), GameRuntime.dH(0));
            return true;
        }
        if (FarmGameEngine.aZB != 0 || FarmGameEngine.awX != 0 || FarmGameEngine.bE(iArr2[0], iArr2[1]) <= 0 || FarmGameEngine.hh(FarmGameEngine.aYS)) {
            return false;
        }
        int iBE2 = FarmGameEngine.bE(iArr2[0], iArr2[1]);
        String str2 = "clickGridPos[0] : " + iArr2[0] + " clickGridPos[1] : " + iArr2[1];
        String str3 = "Grid_GetZoneNumber : " + iBE2;
        if (iBE2 < 1 || FarmGameEngine.ayK[iBE2]) {
            return false;
        }
        FarmEntity[] chVarArr = new FarmEntity[10];
        a(3, 47, chVarArr, 10);
        int i = 0;
        while (true) {
            if (i >= chVarArr.length) {
                z = false;
                break;
            }
            if (chVarArr[i] != null && chVarArr[i].anG == 37) {
                String str4 = " --------------------------------------------------------------------- true";
                z = true;
                break;
            }
            i++;
        }
        if (FarmGameEngine.currentTutorialStepIndex != -1 || FarmGameEngine.ayM != null || z) {
            return false;
        }
        int iLz = FarmGameEngine.Lz();
        if (iLz == -1 || FarmGameEngine.aTH) {
            FarmGameEngine.ayJ = iBE2;
            FarmGameEngine.fE(13);
            return false;
        }
        FarmGameEngine.aVF = iLz;
        FarmGameEngine.fE(27);
        FarmGameEngine.awC = false;
        FarmGameEngine.awD = (char) 0;
        FarmGameEngine.e(1, 54, false);
        FarmGameEngine.f(1, 54, false);
        return false;
    }

    boolean oY() {
        int[] iArr = new int[2];
        FarmGameEngine.c(new int[]{GameRuntime.dG(0), GameRuntime.dH(0)}, iArr);
        if (aH(iArr[0], iArr[1])) {
            String str = "#HOAN clickWorldPos m_x-x TRUE:" + (this.aol - iArr[0]) + " m_y-y:" + (this.aom - iArr[1]);
            return true;
        }
        String str2 = "#HOAN clickWorldPos m_x-x FALSE:" + (this.aol - iArr[0]) + " m_y-y:" + (this.aom - iArr[1]);
        return false;
    }

    boolean qi() {
        int[] iArr = new int[2];
        int[] iArr2 = {this.aol, this.aom};
        FarmGameEngine.f(iArr2, iArr);
        iArr[0] = iArr[0] - (this.aoy >> 1);
        iArr[1] = iArr[1] - (this.aoz >> 1);
        int[] iArr3 = {GameRuntime.dG(0), GameRuntime.dH(0)};
        FarmGameEngine.c(iArr3, iArr2);
        FarmGameEngine.f(iArr2, iArr3);
        return iArr3[0] >= iArr[0] && iArr3[0] < iArr[0] + this.aoy && iArr3[1] >= iArr[1] && iArr3[1] < iArr[1] + this.aoz;
    }

    boolean qj() {
        if (this.aoG != -1 && this.aoH != -1 && (this.aoG & (-65536)) == 0) {
            if (((this.aoG & 32768) != 0 ? FarmGameEngine.atf[this.aoG & 32767].bG(this.aoH) : FarmGameEngine.aZf[this.aoG].bG(this.aoH)) > 0) {
                int[] iArr = new int[4];
                int[] iArr2 = new int[2];
                FarmGameEngine.c(new int[]{GameRuntime.dG(0), GameRuntime.dH(0)}, iArr2);
                if ((this.aoG & 32768) != 0) {
                    FarmGameEngine.atf[this.aoG & 32767].a(this.aoH, 0, iArr, 0);
                } else {
                    FarmGameEngine.aZf[this.aoG].a(this.aoH, 0, iArr, 0);
                }
                int i = this.aol + iArr[0];
                int i2 = this.aom + iArr[1];
                return iArr2[0] + 20 >= i && iArr2[0] + (-20) <= iArr[2] + i && iArr2[1] + 20 >= i2 && iArr2[1] + (-20) <= iArr[3] + i2;
            }
        }
        return false;
    }
}
