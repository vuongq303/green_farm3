package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.os.SystemClock;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.gameloft.glads.GLURLConnection;
import com.google.android.gms.ads.AdRequest;

/* JADX INFO: loaded from: classes.dex */
class ch {
    static ch[] als;
    static int[] ape;
    static int[] apf;
    static int[][] apg;
    static int aph;
    int Bj;
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
    ch aoM;
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
    ch aoi;
    ch aoj;
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
    static final int alU = ci.ih(86400);
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
    int amr = ci.ih(0);
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
    int amE = ci.ih(Integer.MAX_VALUE);
    int amF = 0;
    int amG = 0;
    int amH = 0;
    int amI = ci.ih(0);
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
        als = new ch[6];
        ape = new int[4];
        apf = new int[7];
        apg = new int[500][];
        aph = 0;
        for (int i = 0; i < 500; i++) {
            apg[i] = new int[4];
        }
    }

    static int a(int i, int i2, int i3, int i4, int i5, int i6, ch[] chVarArr, int i7) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        int i8 = 0;
        for (ch chVar = als[i]; chVar != null && i8 < i7; chVar = chVar.aoi) {
            iArr[0] = chVar.aol;
            iArr[1] = chVar.aom;
            ci.f(iArr, iArr2);
            if ((i2 == -1 || i2 == chVar.Kx) && chVar.Kx != -1 && iArr2[0] >= i3 && iArr2[0] < i4 && iArr2[1] >= i5 && iArr2[1] < i6) {
                chVarArr[i8] = chVar;
                i8++;
            }
        }
        return i8;
    }

    static int a(int i, int i2, int i3, int i4, ch[] chVarArr, int i5) {
        int i6 = 0;
        for (ch chVar = als[i]; chVar != null && i6 < i5; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && chVar.aoC >= i3 && chVar.aoC <= i4 && !chVar.fz(32)) {
                chVarArr[i6] = chVar;
                i6++;
            }
        }
        return i6;
    }

    static int a(int i, int i2, int i3, ch[] chVarArr, int i4) {
        int i5 = 0;
        for (ch chVar = als[i]; chVar != null && i5 < i4; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && ((i3 == -1 || chVar.aoC == i3) && chVar.ams)) {
                chVarArr[i5] = chVar;
                i5++;
            }
        }
        return i5;
    }

    static int a(int i, int i2, ch[] chVarArr, int i3) {
        return b(i, i2, -1, chVarArr, i3);
    }

    static int a(int i, int i2, ch[] chVarArr, int i3, ch chVar) {
        int i4 = 0;
        for (ch chVar2 = als[i]; chVar2 != null && i4 < i3; chVar2 = chVar2.aoi) {
            if (chVar2.Kx == i2 && chVar2.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (ci.awl || ci.c(chVar2, chVar))) {
                chVarArr[i4] = chVar2;
                i4++;
            }
        }
        return i4;
    }

    static int a(ch[] chVarArr, int i) {
        return a(chVarArr, i, -1);
    }

    static int a(ch[] chVarArr, int i, int i2) {
        int i3 = 0;
        if (als != null) {
            for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
                if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (chVar.qj() || chVar.qi())) {
                    chVarArr[i3] = chVar;
                    i3++;
                }
            }
        }
        return i3;
    }

    static void a(ch chVar, int i) {
        chVar.aok = i;
        chVar.aov = chVar.oW();
        ch chVar2 = null;
        ch chVar3 = als[i];
        while (chVar3 != null && chVar.aov > chVar3.oW()) {
            chVar2 = chVar3;
            chVar3 = chVar3.aoi;
        }
        ch chVar4 = chVar2;
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

    static boolean a(ch chVar) {
        return chVar.Kx == 50 && (chVar.aoC == 9 || chVar.aoC == 14);
    }

    static boolean a(ch[] chVarArr, ch chVar) {
        for (int i = 0; i < chVarArr.length && chVarArr[i] != null; i++) {
            if (chVarArr[i].Kx == chVar.Kx && chVarArr[i].aoC == chVar.aoC && chVarArr[i].aoU == chVar.aoU) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:82:0x0113  */
    static ch[] a(int i, int i2, ch chVar, int i3) {
        int i4;
        ch[] chVarArr;
        boolean z;
        ch[] chVarArr2;
        int i5;
        ch chVar2 = als[i];
        ch[] chVarArr3 = new ch[i3];
        int i6 = 0;
        int i7 = chVar.aoy;
        int i8 = chVar.aoz;
        for (ch chVar3 = chVar2; chVar3 != null && i6 < i3; chVar3 = chVar3.aoi) {
            if (!(i2 == -1 || chVar3.Kx == i2) || !chVar3.fz(AdRequest.MAX_CONTENT_URL_LENGTH) || !chVar3.fz(128) || chVar3.fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) || chVar3.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) || chVar3.anw || a(chVar3)) {
                i4 = i6;
            } else if (i2 == -1) {
                chVar.aol = chVar3.aol;
                chVar.aom = chVar3.aom;
                int[] iArrA = ci.a(new ch[]{chVar});
                if (iArrA != null) {
                    ch chVar4 = als[i];
                    chVarArr3[0] = chVar3;
                    int i9 = 1;
                    ch chVar5 = chVar4;
                    while (true) {
                        if (chVar5 == null || i9 >= i3) {
                            z = true;
                            chVarArr2 = chVarArr3;
                            break;
                        }
                        if (!chVar5.fz(AdRequest.MAX_CONTENT_URL_LENGTH) || chVar5.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) || chVar5.fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) || chVar5.anw || !a(chVarArr3, chVar5) || a(chVar5) || !ci.c(chVar5, chVar)) {
                            i5 = i9;
                        } else {
                            if (!chVar5.fz(128)) {
                                z = false;
                                chVarArr2 = new ch[i3];
                                break;
                            }
                            i5 = i9 + 1;
                            chVarArr3[i9] = chVar5;
                        }
                        chVar5 = chVar5.aoi;
                        i9 = i5;
                    }
                    if (z) {
                        int[] iArrA2 = ci.a(chVarArr2);
                        boolean z2 = false;
                        for (int i10 = 0; i10 < iArrA.length && !z2; i10++) {
                            if (ci.aAP[iArrA[i10]] == 1) {
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
                ch chVarFm = ci.Fm();
                if (chVarFm != null ? ci.c(chVarFm, chVar3) : false) {
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
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && !chVar.fz(32) && chVar.fz(2048)) {
                i3++;
            }
        }
        return i3;
    }

    static ch aB(int i, int i2) {
        if (als != null) {
            ch chVar = als[i];
            ch chVar2 = chVar;
            for (ch chVar3 = chVar; chVar3 != null && 0 == 0; chVar3 = chVar3.aoi) {
                if ((i2 == -1 || chVar3.Kx == i2) && chVar3.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && chVar3.qj()) {
                    return chVar3;
                }
                chVar2 = chVar3;
            }
            for (ch chVar4 = chVar2; chVar4 != null && 0 == 0; chVar4 = chVar4.aoj) {
                if ((i2 == -1 || chVar4.Kx == i2) && chVar4.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && chVar4.qi()) {
                    return chVar4;
                }
            }
        }
        return null;
    }

    static ch aC(int i, int i2) {
        ch chVar = als[i];
        while (chVar != null) {
            if (chVar.aoD == i2 && (ci.aCu != 19 || chVar.aoC != 14 || ci.qD() != 36)) {
                return chVar;
            }
            chVar = chVar.aoi;
        }
        return null;
    }

    static int aD(int i, int i2) {
        int i3 = 0;
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
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
        for (ch chVar = als[1]; chVar != null; chVar = chVar.aoi) {
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
        for (ch chVar = als[1]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx != 54 || ((chVar.anp != 0 && z) || chVar.anj || chVar.ank || chVar.anw)) {
                if (chVar.Kx == 69 && ci.u(chVar)) {
                    if (z) {
                        chVar.px();
                    } else {
                        chVar.py();
                    }
                }
            } else if (ci.u(chVar)) {
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
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2) {
                i3++;
            }
        }
        return i3;
    }

    static int aw(int i, int i2) {
        int i3 = 0;
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
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
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
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
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.aoU == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH)) {
                i3++;
            }
        }
        return i3;
    }

    static int az(int i, int i2) {
        int i3 = 0;
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH)) {
                i3++;
            }
        }
        return i3;
    }

    static int b(int i, int i2, int i3, int i4, ch[] chVarArr, int i5) {
        int i6 = 0;
        for (ch chVar = als[i]; chVar != null && i6 < i5; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && chVar.aoU >= i3 && chVar.aoU <= i4 && !chVar.fz(32) && !chVar.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                chVarArr[i6] = chVar;
                i6++;
            }
        }
        return i6;
    }

    static int b(int i, int i2, int i3, ch[] chVarArr, int i4) {
        int i5 = 0;
        for (ch chVar = als[i]; chVar != null && i5 < i4; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && (i3 == -1 || chVar.aoC == i3)) {
                chVarArr[i5] = chVar;
                i5++;
            }
        }
        return i5;
    }

    static ch b(int i, int i2, int[] iArr) {
        ch chVar = new ch();
        chVar.d(i, iArr);
        int i3 = api;
        api = i3 + 1;
        chVar.aoW = i3;
        a(chVar, i2);
        return chVar;
    }

    static int c(int i, int i2, int i3, ch[] chVarArr, int i4) {
        int i5 = 0;
        for (ch chVar = als[i]; chVar != null && i5 < i4; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (i3 == -1 || chVar.aoC == i3)) {
                chVarArr[i5] = chVar;
                i5++;
            }
        }
        return i5;
    }

    static void c(boolean z, boolean z2) {
        ci.a(151144, new String[]{"INT:" + Integer.toString(151147), "INT:" + Integer.toString(z2 ? 151148 : 151149), "INT:" + Integer.toString(ci.FC())});
    }

    static int d(int i, int i2, int i3, ch[] chVarArr, int i4) {
        int i5 = 0;
        for (ch chVar = als[i]; chVar != null && i5 < i4; chVar = chVar.aoi) {
            if ((i2 == -1 || chVar.Kx == i2) && (i3 == -1 || chVar.aoU == i3)) {
                chVarArr[i5] = chVar;
                i5++;
            }
        }
        return i5;
    }

    static int e(int i, int i2, int i3, ch[] chVarArr, int i4) {
        int i5;
        int i6 = 0;
        ch chVar = als[i];
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

    static ch e(int i, int[] iArr) {
        return b(i, 1, iArr);
    }

    static void eW(int i) {
        ch chVar = als[i];
        while (chVar != null) {
            ch chVar2 = chVar.aoi;
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
        ch chVar = als[i];
        ch chVar2 = null;
        while (chVar != null) {
            ch chVar3 = chVar.aoi;
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
            ch chVar4 = chVar2.aoi;
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
        ch chVar = als[i];
        while (chVar != null) {
            ch chVar2 = chVar.aoi;
            chVar.aov = 0;
            chVar = chVar2;
        }
    }

    static int fb(int i) {
        int i2 = 0;
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 49 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && (chVar.Bj == 3 || chVar.Bj == 4 || chVar.Bj == 5)) {
                i2++;
            }
        }
        return i2;
    }

    static ch fc(int i) {
        return aB(i, -1);
    }

    static void fd(int i) {
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            int[] iArr = new int[2];
            ci.f(new int[]{chVar.aol, chVar.aom}, iArr);
            if (!ci.hB(ci.bE(iArr[0], iArr[1]))) {
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
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 47 && chVar.anG == 59 && (i2 = chVar.anM & 65535) != 110 && i2 != 5 && (i2 < 111 || i2 > 116)) {
                i3 += (chVar.anM & (-65536)) >> 16;
            }
        }
        return i3;
    }

    static int ff(int i) {
        int i2 = 0;
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 47 && chVar.anG == 37) {
                i2++;
            }
        }
        String str = "ActorLists_GetQtyEXPReward : " + i2;
        return i2;
    }

    static void fr(int i) {
        ci.a(151144, new String[]{"INT:" + Integer.toString(151145), "INT:" + Integer.toString(i), "INT:" + Integer.toString(ci.FC())});
    }

    static ch ft(int i) {
        int iL = GLLib.L(25, 29);
        String str = "Fireworks sndIndex : " + iL;
        ci.gX(iL);
        if (i == -1) {
            anY = -1;
        } else {
            anY = 0;
            anZ = i;
        }
        if (ci.aZf[85] == null) {
            ci.s(0, 85, 15, 24);
            GLLib.bk("/9");
            ci.fP(0);
            GLLib.ke();
        }
        if (av(1, 21) <= 0) {
            ch chVarE = e(21, new int[]{((ci.aZj.aol + (GLLib.kH() >> 1)) * 100) / ci.aYP, ((ci.aZj.aom + (GLLib.kI() >> 1)) * 100) / ci.aYP, 255});
            chVarE.aoO = new aa(ci.aZf[85], chVarE.aol, chVarE.aom);
            chVarE.aoO.Y(0, 1);
            return chVarE;
        }
        ch[] chVarArr = new ch[1];
        a(1, 21, chVarArr, 1);
        chVarArr[0].aoO = new aa(ci.aZf[85], chVarArr[0].aol, chVarArr[0].aom);
        chVarArr[0].aoO.Y(0, 1);
        return chVarArr[0];
    }

    static ch fu(int i) {
        ch[] chVarArr = new ch[1];
        b(1, 50, 18, chVarArr, 1);
        if (chVarArr[0] != null) {
            aoc = chVarArr[0].aol;
            aod = chVarArr[0].aom;
        }
        ci.gX(30);
        if (i == -1) {
            aoa = -1;
        } else {
            aoa = 0;
            aob = i;
        }
        if (av(1, 23) <= 0) {
            ch chVarE = e(23, new int[]{(aoc * 100) / ci.aYP, (aod * 100) / ci.aYP, 255});
            chVarE.aoO = new aa(ci.aZf[13], chVarE.aol, chVarE.aom);
            chVarE.aoO.Y(1, 1);
            return chVarE;
        }
        ch[] chVarArr2 = new ch[1];
        a(1, 23, chVarArr2, 1);
        chVarArr2[0].aoO = new aa(ci.aZf[13], chVarArr2[0].aol, chVarArr2[0].aom);
        chVarArr2[0].aoO.Y(1, 1);
        return chVarArr2[0];
    }

    static ch fv(int i) {
        if (i == -1) {
            aoe = -1;
        } else {
            aoe = 0;
            aof = i;
        }
        if (ci.aZf[85] == null) {
            ci.s(0, 85, 15, 24);
            GLLib.bk("/9");
            ci.fP(0);
            GLLib.ke();
        }
        ci.q(11, false);
        aog = true;
        if (av(1, 24) <= 0) {
            ch chVarE = e(24, new int[]{((ci.aZj.aol + (GLLib.kH() >> 1)) * 100) / ci.aYP, ((ci.aZj.aom + (GLLib.kI() >> 1)) * 100) / ci.aYP, 255});
            chVarE.aoO = new aa(ci.aZf[85], chVarE.aol, chVarE.aom);
            chVarE.aoO.Y(0, 1);
            return chVarE;
        }
        ch[] chVarArr = new ch[1];
        a(1, 24, chVarArr, 1);
        chVarArr[0].aoO = new aa(ci.aZf[85], chVarArr[0].aol, chVarArr[0].aom);
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
        ci.aZz = false;
        ci.aZA = false;
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
        ci.bbs = false;
        ch chVar = als[3];
        while (chVar != null) {
            ch chVar2 = chVar.aoi;
            if (chVar.Kx == 47 && chVar.anX) {
                chVar.anX = false;
                if (chVar != null && chVar.anG == 37) {
                    String str = "extra info:" + chVar.anM;
                    ci.hX(chVar.anM);
                } else if (chVar.anG == 36) {
                    ci.hS(chVar.anM);
                } else if (chVar.anG == 6) {
                    ci.hT(chVar.anM);
                } else if (chVar.anG == 59) {
                    int i = chVar.anM & 65535;
                    int i2 = (chVar.anM & (-65536)) >> 16;
                    String str2 = "item index:" + i + ",qty:" + i2;
                    if (i == 110) {
                        ci.hV(i2);
                    } else if (i == 5) {
                        ci.hU(i2);
                    } else if ((i < 111 || i > 116) && ((i >= 1 && i <= 26) || i == 119)) {
                        String str3 = "add crop, tree,:" + i + ",qty:" + i2;
                        ci.O(0, i, i2);
                    }
                } else if (chVar.anG == 67) {
                    ci.hg(chVar.anM);
                    ci.bW(1, 121);
                }
                chVar.oN();
                ci.bbs = true;
            }
            chVar = chVar2;
        }
    }

    static ch oP() {
        ch chVar;
        ch chVar2;
        ch chVar3;
        ch chVar4;
        ch chVar5;
        ch chVar6;
        ch chVar7;
        ch chVar8;
        if (als != null) {
            chVar = null;
            chVar2 = null;
            chVar3 = null;
            chVar4 = null;
            chVar5 = null;
            chVar6 = null;
            chVar7 = null;
            chVar8 = null;
            for (ch chVar9 = als[1]; chVar9 != null; chVar9 = chVar9.aoi) {
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
        String str = "actor pick. move state;" + ci.avD + ",obj:" + ci.avE;
        if (ci.avD != -1 && ci.avE != null) {
            if (ci.avE == chVar2) {
                return chVar2;
            }
            if (ci.avE == chVar4) {
                return chVar4;
            }
            if (ci.avE == chVar3) {
                return chVar3;
            }
            if (ci.avE == chVar) {
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
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && !chVar.fz(32) && !chVar.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) && chVar.aoC >= i3 && chVar.aoC <= i4) {
                i5++;
            }
        }
        return i5;
    }

    static void pj() {
        int i = ci.aZB == 0 ? ci.aZD : ci.aNG;
        for (ch chVar = als[1]; chVar != null; chVar = chVar.aoi) {
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
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(AdRequest.MAX_CONTENT_URL_LENGTH) && !chVar.fz(32) && !chVar.fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) && ((chVar.aoC >= i3 && chVar.aoC <= i4) || chVar.aoC == i4 - 2)) {
                i5++;
            }
        }
        return i5;
    }

    static int x(int i, int i2, int i3) {
        int i4 = 0;
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == i2 && chVar.fz(4096) && chVar.fz(i3)) {
                i4++;
            }
        }
        return i4;
    }

    int A(int i, int i2, int i3) {
        return ci.gf(i2);
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
        if ((ci.kH() * 100.0f) / ci.aYP > 0.0f) {
            iKH = (int) (((ci.kH() * 100.0f) / ci.aYP) + 0.5f);
        } else {
            iKH = (((float) ci.kH()) * 100.0f) / ((float) ci.aYP) < 0.0f ? (int) (((ci.kH() * 100.0f) / ci.aYP) - 0.5f) : 0;
        }
        int i2 = 0 + iKH;
        if ((ci.kI() * 100.0f) / ci.aYP > 0.0f) {
            iKI = (int) (((ci.kI() * 100.0f) / ci.aYP) + 0.5f);
        } else {
            iKI = (((float) ci.kI()) * 100.0f) / ((float) ci.aYP) < 0.0f ? (int) (((ci.kI() * 100.0f) / ci.aYP) - 0.5f) : 0;
        }
        return iBy > 0 && iBA < i2 && iBz > 0 && iBB < iKI + 0;
    }

    boolean a(ch chVar, ch chVar2, int[] iArr) {
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
                    boolean z = !ci.t(chVar2);
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
        return a((32768 & i) != 0 ? ci.atf[i3] : ci.aZf[i3], i2);
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
        ci.c(new int[]{i, i2}, iArr);
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
        ci.axt = 0;
        ci.bbp = 0;
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
        if (ci.baf != null && ci.aZB == 0) {
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
            bbVar.aiF = ci.FC();
            ci.a(bbVar);
        }
        if (ci.aZB == 0 || ((ci.awV && ci.aYS != -1) || (ci.hF(5) < ci.Fw() && GLLib.L(0, 100) < ci.aZw[20]))) {
            ch chVarB = b(47, 3, iArr);
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
            if (z && ci.aYR == -1) {
                ci.N(this.aoU, i, 14);
            }
            if (ci.aYR == -1) {
                if (this.aoC == 23 || this.aoC == 22) {
                    String str = "m_resourceGenerated  " + this.aoL;
                    ci.aa(this.aoL + 4000, i, 14);
                } else {
                    ci.aa(this.aoU, i, 14);
                }
            }
            chVarB.a(this.aol, this.aom, 59, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, i3 | (i << 16), 0);
        }
        if (ci.FC() < ci.aLg[0] || GLLib.L(0, 100) >= ci.aZw[10]) {
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
            this.aoG = ci.aZM[i6] | Short.MIN_VALUE;
            this.aoH = ci.aZN[i6];
            String str2 = "sprite id :" + this.aoG + ",frame:" + this.aoH;
        } else if (i3 == 60) {
            int iIe = ci.Ie();
            int i7 = (iIe / 4) + 109;
            this.aoG = i7 | 32768;
            this.aoH = iIe % 4;
            String str3 = "Reward_Init sprite id :" + this.aoG + ",frame:" + this.aoH;
            ci.s(1, i7, 1, 24);
            GLLib.bk("/11");
            ci.fP(1);
            GLLib.ke();
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

    void b(ch chVar) {
        this.amD = j.bY(0).KW[chVar.aoC][25] * 60;
        this.amD = (this.amD * ci.md(this.aoU)) / 100;
        this.amD = (this.amD * 30) / 100;
        String str = "[Animal] prepare next care. dbid:" + chVar.aoC + ",time:" + this.amE;
    }

    void c(ch chVar) {
        this.aoG = chVar.aoG;
        this.aoH = chVar.aoH;
        this.ant = chVar.ant;
        this.aoC = chVar.aoC;
        this.aoO = new aa(ci.aZf[this.aoG], this.aol, this.aom);
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
                this.aoA = ci.bF(this.aoy, this.aoz);
                if (ci.aZf[this.aoG].hu() > 0) {
                    this.aoO = new aa(ci.aZf[this.aoG], this.aol, this.aom);
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
                this.aoO = new aa(ci.aZf[144], this.aol, this.aom);
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
                    this.aoO = new aa(ci.aZf[this.aoG], this.aol, this.aom);
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
                    this.aoO = new aa(ci.aZf[this.aoG], this.aol, this.aom);
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
                this.aoO = new aa(ci.aZf[this.aoG], 0, 0);
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
                this.aoO = new aa(ci.aZf[this.aoG], this.aol, this.aom);
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
                this.aoA = ci.bF(this.aoy, this.aoz);
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
                    ci.aZW = this.aol;
                    ci.aZX = this.aom;
                }
                break;
            case 51:
                this.aol = iArr[0];
                this.aom = iArr[1];
                this.aon = iArr[2];
                this.aoG = iArr[4];
                a aVar = ci.aZf[this.aoG];
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
                a aVar2 = ci.aZf[iArr[5]];
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

    void d(ch chVar) {
        if (this != null) {
            aN(chVar.aol, chVar.aom);
        }
    }

    void e(ch chVar) {
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
                ci.gX(53);
                i2 = i4;
                break;
            case 2:
                switch (ci.aCd[ci.aCf]) {
                    case 0:
                        i5 = 46;
                        i2 = 4;
                        break;
                    case 1:
                        i5 = 39;
                        i2 = 4;
                        break;
                    case 2:
                        if (ci.aCe[ci.aCf] != null && (ci.aCe[ci.aCf].aoC == 0 || ci.aCe[ci.aCf].aoC == 51 || ci.aCe[ci.aCf].aoC == 52)) {
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
                        if (ci.axm) {
                            ci.axm = false;
                            i3 = -1;
                        } else if (ci.axn) {
                            ci.axn = false;
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
                ci.gX(i5);
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
        if (ci.aYP != i) {
            ci.aYP = i;
            int iKH = GLLib.kH() >> 1;
            int iKI = GLLib.kI() >> 1;
            int i2 = (((ci.aZj.aol - ((ci.aYP * iKH) / 100)) + iKH) * 100) / ci.aYP;
            int i3 = (((ci.aZj.aom - ((ci.aYP * iKI) / 100)) + iKI) * 100) / ci.aYP;
            ci.aZj.aol = (((i2 + iKH) * ci.aYP) / 100) - iKH;
            ci.aZj.aom = (((i3 + iKI) * ci.aYP) / 100) - iKI;
        }
    }

    void fi(int i) {
        String str = "Crop_PrepareSeed seedType : " + i;
        if (this.amn) {
            return;
        }
        this.amn = true;
        this.aoC = (char) i;
        this.aoL = ci.avT;
        this.amk = ci.awd;
        this.amf = ci.avV;
        this.amg = ci.avW;
        String str2 = "Crop_PrepareSeed m_cropCostCash : " + this.amg;
        this.aoC = ci.avS;
        this.aoU = ci.avZ;
        this.aoV = ci.awa;
        this.aoG = ci.awb;
        this.aml = ci.awe;
        this.ame = ci.ih((ci.avU * ci.lZ(this.aoU)) / 100);
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
                this.amd = ci.MD();
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
                this.amq = ci.MD();
                this.aoH = 0;
                break;
            case 3:
                fy(32);
                this.aoH = 1;
                if (this.aoC == 22 || this.aoC == 23) {
                    this.aoR = new aa(ci.aZf[187], this.aot, this.aou);
                    this.aoR.Y(1, -1);
                    this.aoQ = 0;
                }
                break;
            case 4:
                if (!this.amu) {
                    this.amu = true;
                    this.amr = ci.ih((int) (((long) ci.ii(this.amr)) - ((ci.MD() - this.amq) / 1000)));
                }
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 22, -63, -117, 0, 1}));
                break;
            case 5:
                this.aoH = 1;
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 12, -63, -117, 0, 1}));
                break;
            case 6:
                ci.gX(31);
                ci.axn = true;
                break;
        }
    }

    void fl(int i) {
        this.anx = ci.w(i);
        this.any = this.anx;
    }

    void fm(int i) {
        this.anz = ci.w(i);
        this.anA = this.anz;
    }

    void fn(int i) {
        this.anB = ci.w(i);
        this.anC = this.anB;
    }

    void fo(int i) {
        this.ano = i;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0097  */
    void fq(int i) {
        int i2 = 16;
        String str = "[actor ANIMAL] request feed bubble. type:" + i;
        if (ci.aZB == 0 || i != 4) {
            if (ci.aZB <= 0 || ci.aZB > 10 || !this.anw) {
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
        ch[] chVarArr = new ch[100];
        int iA = a(1, i, chVarArr, 100);
        if (iA > 0) {
            int iL = GLLib.L(0, iA);
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
        if (ci.aYP == 100) {
            return (this.aot < -144 || this.aot > ci.kH() + 144 || this.aou < -288 || this.aou > ci.kI() + 288) && this.aoo == -1;
        }
        return (this.aot < -36 || this.aot > (ci.kH() << 1) + 36 || this.aou < -36 || this.aou > (ci.kI() << 1) + 36) && this.aoo == -1;
    }

    void oV() {
        int i = 0;
        if (this.aoo == 2) {
            this.aot = 0;
            this.aou = ((((this.aom + this.aox) - 215) - ci.aZj.aom) * ci.aYP) / 100;
            return;
        }
        if (this.aoo != 1) {
            this.aot = (this.aol + this.aow) - ci.aZj.aol;
            this.aou = (this.aom + this.aox) - ci.aZj.aom;
            return;
        }
        float f = (648 - ci.aZj.aom) / 648.0f;
        this.aot = this.aol - (ci.aZj.aol >> 4);
        if (this.aom - (f * 40.0f) > 0.0f) {
            i = (int) ((this.aom - (f * 40.0f)) + 0.5f);
        } else if (this.aom - (f * 40.0f) < 0.0f) {
            i = (int) ((this.aom - (f * 40.0f)) - 0.5f);
        }
        this.aou = i - ci.aZj.aom;
    }

    int oW() {
        return (this.Kx == 50 && (this.aoC == 18 || this.aoC == 19)) ? ((this.aon << 20) + this.aom) - 1 : (this.aon << 20) + this.aom;
    }

    void pA() {
        qr();
        ci.o(this);
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
            iL = GLLib.L(i3, i4 + 1);
            if (iL > 0) {
                b(47, 3, iArr).b(this.aol, this.aom, 6, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, iL);
                iFB = ci.FB() + iL;
                String str = "cash_balance ==========" + iFB;
            }
        } else {
            iFB = ci.FB();
        }
        String str2 = "Player_GetCash ==========" + ci.FB();
        String str3 = "cash_balance ==========" + iFB;
        if (ci.baf != null && ci.aZB == 0) {
            ci.a(115443, new String[]{"INT:" + Integer.toString(iFB), "INT:" + Integer.toString(iL), "INT:" + Integer.toString(i2), "INT:" + Integer.toString(119043), "INT:" + Integer.toString(115433), "N/A", "N/A", "INT:" + Integer.toString(i), "INT:" + Integer.toString(ci.FC())});
        }
        oN();
    }

    int pB() {
        long jX = ci.x(this.anx);
        long jX2 = ci.x(this.any);
        return jX != jX2 ? (int) jX2 : (int) jX;
    }

    int pC() {
        long jX = ci.x(this.anz);
        long jX2 = ci.x(this.anA);
        return jX != jX2 ? (int) jX2 : (int) jX;
    }

    int pD() {
        long jX = ci.x(this.anB);
        long jX2 = ci.x(this.anC);
        return jX != jX2 ? (int) jX2 : (int) jX;
    }

    void pE() {
        if (fz(32)) {
            if (ci.ii(this.amI) > 0) {
                this.amI = ci.ih((int) (((long) ci.ii(this.amI)) - ((ci.MD() / 1000) - this.amJ)));
            }
        } else if (ci.ii(this.amE) != 0) {
            long jMD = (ci.MD() - this.amB) / 1000;
            this.amE = ci.ih((int) (((long) ci.ii(this.amE)) - jMD));
            String str = jMD + " ----------------elapsedTime------m_nextAnimalCare--------- " + this.amE;
        }
        fp(9);
    }

    void pF() {
        ch chVar;
        if (this.anj || this.ank) {
            ch chVarFl = ci.Fl();
            if (chVarFl == null) {
                ch[] chVarArr = new ch[1];
                b(1, 69, 22, chVarArr, 1);
                if (chVarArr[0] == null) {
                    ch[] chVarArr2 = new ch[1];
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
        if (ci.axt == 0) {
            ci.axt = this.anj ? 1 : 2;
            this.ani = true;
            if (chVar.Kx != 69) {
                chVar.anq = true;
            }
            fx(8);
            ci.bbp = 0;
            if (chVar.Kx != 69) {
                b(chVar);
            }
            if (this.anj) {
                if (this.anl) {
                    ci.gX(69);
                }
                if (!ci.ha(1)) {
                    ci.q(62, false);
                }
                ci.f(true, true);
                if (ci.auj) {
                    ci.aNa = ci.MD();
                }
                if (!ci.auj || !ci.lN(299)) {
                    int[] iArr = new int[2];
                    int[] iArr2 = {this.aol, this.aom};
                    boolean zA = ci.a(this, iArr);
                    this.aol = iArr2[0];
                    this.aom = iArr2[1];
                    if (zA) {
                        if (ci.awV && iArr[0] == 1782 && iArr[1] == 711) {
                            iArr[0] = iArr[0] - 10;
                            iArr[1] = iArr[1] - 10;
                        }
                        aN(iArr[0], iArr[1]);
                    } else if (a(chVar, this, iArr)) {
                        aN(iArr[0], iArr[1]);
                    } else {
                        z(chVar.aol, chVar.aom, 36);
                    }
                } else if (ci.aMY != null) {
                    aN(ci.aMY[0], ci.aMY[1]);
                } else {
                    z(chVar.aol, chVar.aom, 36);
                }
                this.anl = false;
            } else {
                if (this.anm) {
                    ci.gX(71);
                }
                if (!ci.ha(1)) {
                    ci.q(62, false);
                }
                ci.f(true, false);
                if (ci.auj) {
                    ci.aNa = ci.MD();
                }
                int[] iArr3 = new int[2];
                int[] iArr4 = {this.aol, this.aom};
                boolean zA2 = ci.a(this, iArr3);
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
        ci.bad = true;
        int iL = GLLib.L(-4, 4);
        int iL2 = GLLib.L(-4, 4);
        int[] iArr = new int[2];
        int[] iArr2 = {this.aol, this.aom};
        ci.f(iArr2, iArr);
        int i = 0;
        do {
            iArr[0] = iArr[0] + iL;
            iArr[1] = iArr[1] + iL2;
            if (this.anw && ci.axE) {
                if (((ci.a(this, iArr[0], iArr[1]) || ci.C(iArr[0], iArr[1], this.aoy, this.aoz)) ? false : true) || i >= 50) {
                    ci.axE = false;
                } else {
                    i++;
                    iArr[0] = iArr[0] - iL;
                    iArr[1] = iArr[1] - iL2;
                    iL = GLLib.L(-4, 4);
                    iL2 = GLLib.L(-4, 4);
                }
            }
            if (!ci.axE) {
                break;
            }
        } while (this.anw);
        if (ci.aYR != -1) {
            fp(0);
            return;
        }
        if (!((ci.C(iArr[0], iArr[1], this.aoy, this.aoz) || ci.a(this, iArr[0], iArr[1]) || ((ci.r(this) != 0 || ci.bz(iArr[0], iArr[1])) && ci.r(this) <= 0)) ? false : true)) {
            String str = "no move, collide test fail: " + this.aoW;
            fp(0);
        } else {
            String str2 = "Target collide test OK: " + this.aoW;
            ci.g(iArr, iArr2);
            aQ(iArr2[0], iArr2[1]);
        }
    }

    boolean pK() {
        if ((!this.anw || ci.aZB == 0) && fz(8) && ci.awX != 1) {
            String str = "step animal :" + this.Bj;
            if (this.Bj != 3 && this.Bj != 7 && this.Bj != 13) {
                if (fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) && ci.axt != 0) {
                    String strDt = ci.dt(12327);
                    if (strDt == null) {
                        strDt = "";
                    }
                    ci.m(strDt, 7, 0);
                    return false;
                }
                if (this.Bj == 15) {
                    ci.he(ci.awk);
                    return true;
                }
                String str2 = "[actor Animal] Animal_Click. flag. need care." + fz(4) + ",flag ready produce:" + fz(16) + ",need clean:" + fz(256);
                if (!this.anw && !this.anj && !this.ank) {
                    ci.gX(this.aoY);
                }
                if (this.anw && fz(32)) {
                    ci.gX(70);
                }
                if (ci.axY == this) {
                    ci.Et();
                }
                if (!fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                    if (fz(131072)) {
                        fp(15);
                        ci.c(true, 2, 2);
                        ci.aqY = this;
                        ci.aV(true);
                        return true;
                    }
                    if (fz(4)) {
                        String str3 = "Animal need care flag are active. feed:" + ci.hZ(this.amS) + ",cat:" + ((int) this.amS) + ",consume:" + this.amK;
                        if (this.amK == 0 && this.anw) {
                            this.amK = 1;
                        }
                        if (ci.aMj[this.amS] < 0) {
                            ci.aMj[this.amS] = 0;
                        }
                        if (ci.aCg < 64 && !ci.b(4, this)) {
                            if ((ci.aCi & 16) == 16) {
                                if (ci.hZ(this.amS) - ci.aMj[this.amS] >= this.amK || (ci.aYS == 1045 && this.amS == 1)) {
                                    int[] iArr = ci.aMj;
                                    short s = this.amS;
                                    iArr[s] = iArr[s] + this.amK;
                                    ci.a(4, this);
                                } else if (!ci.awj) {
                                    ci.S(401, 7, 0);
                                }
                            }
                        }
                        return true;
                    }
                    if (fz(16)) {
                        if (ci.hF(5) + fe(3) < ci.Fw() || ci.aZB != 0) {
                            ci.a(7, this);
                            return true;
                        }
                        ci.H(481, 487, 7, 0);
                        return false;
                    }
                    if (fz(256)) {
                        ci.a(4, this);
                        ci.axm = true;
                        this.anh = 0;
                        return true;
                    }
                    if (this.anw && ci.aZB == 0 && ((this.Bj == 0 || this.Bj == 1) && !fz(32))) {
                        fp(15);
                        ci.c(true, 2, 2);
                        ci.aqY = this;
                        ci.aV(true);
                        return true;
                    }
                    fp(0);
                    if (!this.anw) {
                        ci.a(true, this);
                    }
                    if (!this.anw && !fz(32) && ci.ii(this.amE) == 0 && this.amB != 0 && ci.aZB == 0) {
                        fq(16);
                        ci.a(false, (ch) null);
                    }
                    return false;
                }
                if (this.aol != this.aoE || this.aom != this.aoF) {
                    return false;
                }
                ch chVarFm = ci.Fm();
                if (ci.aYS == 1022 || ci.aYS == 1049 || ci.aYS == 1056 || ci.aYS == 1063) {
                    ci.axW = true;
                } else if (ci.auj && ci.aZB <= 10 && ci.ii(ci.azx[ci.aZB - 1][5]) == 0) {
                    if ((chVarFm == null || chVarFm.Bj != 8) && (ci.aYS == -1 || ci.aYS == 0)) {
                        ci.S(12332, 7, 0);
                        return false;
                    }
                }
                if (chVarFm == null && ci.lN(GLURLConnection.HTTP_FOUND)) {
                    int iFv = ci.Fv();
                    if (iFv != -1) {
                        ci.hG(iFv);
                    }
                    chVarFm = ci.Fm();
                    if (chVarFm != null) {
                        ci.avE = chVarFm;
                        ci.DP();
                        ci.aD(false);
                        ci.avD = -1;
                        chVarFm.amQ = true;
                    }
                }
                if (chVarFm == null) {
                    ci.F(883, 885, 15, 0);
                } else {
                    if (chVarFm.Bj == 8) {
                        return false;
                    }
                    if (ci.b(chVarFm, this) || ci.auj || ci.lN(GLURLConnection.HTTP_FOUND)) {
                        if (ci.auj && ci.baH) {
                            ci.baH = false;
                            ci.jQ(-1);
                        }
                        chVarFm.ann = true;
                        ci.aCn = ci.MD();
                        chVarFm.d(this);
                    } else {
                        String str4 = ci.aMw;
                        String strDt2 = ci.dt(522);
                        if (strDt2 == null) {
                            strDt2 = "";
                        }
                        if (!str4.equals(strDt2)) {
                            String str5 = ci.aMw;
                            String strDt3 = ci.dt(476);
                            if (strDt3 == null) {
                                strDt3 = "";
                            }
                            if (!str5.equals(strDt3)) {
                                ci.aty = this;
                                String strDt4 = ci.dt(1001);
                                if (strDt4 == null) {
                                    strDt4 = "";
                                }
                                String strDt5 = ci.dt(1002);
                                if (strDt5 == null) {
                                    strDt5 = "";
                                }
                                ci.d(strDt4, strDt5, 15, 0);
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
        return (ci.awX == 1 || this.Bj == 3 || this.Bj == 7 || this.Bj == 13 || this.Bj == 15 || this.Bj == 8 || fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) || fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) || fz(GLURLConnection.BUFFER_SIZE) || fz(16) || fz(256)) ? false : true;
    }

    void pM() {
        this.amE = ci.ih(j.bY(0).KW[this.aoC][25] * 60);
        this.amE = ci.ih((ci.ii(this.amE) * ci.md(this.aoU)) / 100);
        String str = "[Animal] prepare next care. dbid:" + this.aoC + ",time:" + this.amE;
        this.amB = ci.MD();
        String str2 = "last anim care:" + ((int) this.amB);
    }

    void pN() {
        this.amI = ci.ih(j.bY(0).KW[this.aoC][14] * 60);
        this.amJ = ci.MD() / 1000;
    }

    void pO() {
        if (!fz(4)) {
            if (fz(256)) {
                fp(7);
                this.anb = true;
                ci.aa(this.aoU, 1, 20);
                fy(256);
                pT();
                this.aoR = new aa(ci.aZf[13], this.aot, this.aou);
                this.aoR.Y(0, 2);
                this.aoP = this.amZ;
                this.aoQ = this.ana;
                ci.gX(31);
                return;
            }
            return;
        }
        if (this.Bj == 3 || ci.aZB != 0) {
            if (ci.aZB == 0) {
                ci.gX(55);
                return;
            }
            fp(3);
            fy(4);
            pT();
            this.amB = ci.MD();
            this.amL = false;
            this.amO = false;
            this.amN = false;
            this.amM = false;
            this.amP = false;
            return;
        }
        if (ci.aYS == 1045) {
            ci.axW = true;
        }
        ci.bM(this.amK, this.amS);
        if (ci.aMj[this.amS] > 0) {
            int[] iArr = ci.aMj;
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
                ci.e(479, 480, 7, 0, this.aoG + Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.aoH, this.aoI);
                ci.lQ(0);
            }
            String str4 = "[ANIMAL] TAKE CARE. tapcount:" + this.amG + ",max:" + this.amF + ",uid:" + this.aoU;
        }
        ci.aa(this.aoU, 1, 19);
        fy(4);
        pT();
        this.amB = ci.MD();
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
        this.amB = ci.MD();
    }

    /* JADX WARN: Code duplicated, block: B:50:0x033b  */
    void pQ() {
        boolean z;
        boolean z2;
        int[] iArr = {0, 0, 14};
        b(47, 3, iArr).b(this.aol, this.aom, 37, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, false, 1);
        if (ci.baf != null && ci.aZB == 0) {
            int i = 115423;
            if (ci.gg(this.aoC)) {
                i = 119024;
            } else if (ci.gh(this.aoC)) {
                i = 150869;
            }
            ci.a(115443, new String[]{"INT:" + Integer.toString(ci.FB()), "INT:" + Integer.toString(0), "INT:" + Integer.toString(0), "INT:" + Integer.toString(116136), "INT:" + Integer.toString(i), "N/A", "N/A", "INT:" + Integer.toString(1), "INT:" + Integer.toString(ci.FC())});
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
        if (ci.aZB == 0 || (ci.hF(5) < ci.Fw() && GLLib.L(0, 100) < ci.aZw[20])) {
            int iL = GLLib.L(0, 100);
            int i9 = 0;
            if (iL < i4) {
                this.aoL = i3;
                int i10 = i2 << 16;
                ch chVarB = b(47, 3, iArr);
                i9 = ci.aZB != 0 ? 65536 : i10;
                chVarB.b(this.aol, this.aom, 59, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, false, true, this.aoL | i9);
                z = true;
            } else {
                z = false;
            }
            if (iL >= i7) {
                z2 = z;
            } else if (ci.aZB == 0) {
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
                if (ci.baf != null && ci.aZB == 0) {
                    ci.a(115443, new String[]{"INT:" + Integer.toString(ci.FB()), "INT:" + Integer.toString(0), "INT:" + Integer.toString(this.amR), "INT:" + Integer.toString(116136), "INT:" + Integer.toString(115423), "N/A", "N/A", "INT:" + Integer.toString(1), "INT:" + Integer.toString(ci.FC())});
                }
            }
        } else {
            z2 = false;
        }
        if (z2) {
            ci.c(this.aoU, 1, 41, fz(2048));
        }
    }

    void pR() {
        if (fz(32)) {
            int i = this.aoU;
            ci.a(false, (ch) null);
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
            a aVar = ci.aZf[jVarBY.KW[this.aoC][31]];
            this.aoI = jVarBY.KW[this.aoC][32];
            this.aoH = jVarBY.KW[this.aoC][33];
            this.amV = (short) jVarBY.KW[this.aoC][29];
            this.amW = (short) jVarBY.KW[this.aoC][30];
            if (this.aoO != null && aVar != null) {
                String str = "animal evol. db:" + this.aoC + ".sprite" + aVar;
                this.aoO.a(aVar);
                this.aoO.Y(0, -1);
            }
            this.aoR = new aa(ci.aZf[13], this.aot, this.aou - 50);
            this.aoR.Y(1, 1);
            if (ci.aZB == 0) {
                if (this.anw) {
                    ci.axj = i;
                } else {
                    ci.aa(i, 1, 21);
                }
            }
            this.aoU = jVarBY.KW[this.aoC][1];
            this.aoV = jVarBY.KW[this.aoC][2];
            if (ci.aZB == 0) {
                ci.lQ(this.aoU);
                ci.aX(true);
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
            ci.gX(43);
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

    void pS() {
        if (fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
            this.anp = 0;
            this.amP = false;
            this.anq = false;
            fy(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
            pT();
            this.amB = ci.MD();
            this.amJ = ci.MD() / 1000;
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
        ci.d(new int[]{this.aol, this.aom}, iArr);
        if (iArr[0] > 5 && iArr[0] < GLLib.kH() - 5 && iArr[1] > 5 && iArr[1] < GLLib.kI() - 5 && !this.anE) {
            this.anF = true;
        }
        if (!this.anE && qs() && this.aoC == 2 && this.anF) {
            ci.gX(21);
            this.anE = true;
            this.anF = false;
        }
    }

    void pX() {
        if (fz(8)) {
            ci.axd = true;
            if (!this.anN) {
                this.anT += GLLib.Qy;
                this.anU += GLLib.Qy;
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
                    long jMD = ci.MD();
                    if (jMD - ci.avC >= 700) {
                        ci.gX(37);
                        ci.avC = jMD;
                    }
                    if (this.anG == 60) {
                        ci.h(true, (((this.aoG & (-32769)) - 109) * 4) + this.aoH);
                    }
                }
                this.aol = this.anJ + this.anP;
                this.aom = this.anK + this.anQ;
                return;
            }
            this.anH += GLLib.Qy * 4;
            if (this.anG == 57) {
                this.aoE = ci.aZj.aol + ci.atc[1][30][2] + (ci.atf[1].by(33) >> 1);
                this.aoF = ci.aZj.aom + ci.atc[1][30][3] + (ci.atf[1].by(33) >> 1);
            } else if (this.anG >= 59) {
                if ((this.anM & 65535) == 5) {
                    this.aoE = ci.aZj.aol + ci.atc[1][32][2];
                    this.aoF = ci.aZj.aom + ci.atc[1][32][3];
                } else {
                    this.aoE = ci.aZj.aol + ci.atc[1][78][2];
                    this.aoF = ci.aZj.aom + ci.atc[1][78][3];
                }
            } else if (this.anG == 37) {
                this.aoE = ci.aZj.aol + ci.atc[1][25][2] + (ci.atf[1].by(4) >> 1);
                this.aoF = ci.aZj.aom + ci.atc[1][25][3] + (ci.atf[1].by(4) >> 1);
            } else {
                this.aoE = ci.aZj.aol;
                this.aoF = ci.aZj.aom;
            }
            if (this.anG == 60) {
                this.aoE = ci.aZj.aol + ci.atc[1][this.aoH + FacebookRequestErrorClassification.EC_INVALID_SESSION][2];
                this.aoF = ci.aZj.aom + ci.atc[1][this.aoH + FacebookRequestErrorClassification.EC_INVALID_SESSION][3];
            }
            if (this.anG == 67 || this.anG == 67) {
                this.aoE = ci.aZj.aol + ci.atc[1][120][2];
                this.aoF = ci.aZj.aom + ci.atc[1][120][3];
            }
            if (this.anI > 0) {
                this.aol = this.anJ + (((((this.aoE * 100) / ci.aYP) - this.anJ) * this.anH) / this.anI);
                this.aom = this.anK + ((((((this.aoF * 100) / ci.aYP) - this.anK) * (this.anH * this.anH)) / this.anI) / this.anI);
            }
            if (this.anH > this.anI) {
                fx(8);
                ci.aCk = false;
                if (this.anX) {
                    oN();
                    ci.axd = false;
                    this.anX = false;
                    if (this.anG == 37) {
                        ci.hX(this.anM);
                    } else if (this.anG == 36) {
                        ci.hS(this.anM);
                    } else if (this.anG == 6) {
                        ci.hT(this.anM);
                    } else if (this.anG == 59) {
                        int i4 = this.anM & 65535;
                        int i5 = (this.anM & (-65536)) >> 16;
                        if (i4 == 110) {
                            ci.hV(i5);
                            short s = ci.aZL[i4];
                            ci.N(s, i5, 34);
                            ci.aa(s, i5, 34);
                            ci.aa(s, i5, 22);
                        } else if (i4 == 5) {
                            ci.hU(i5);
                        } else if (i4 < 111 || i4 > 116) {
                            ci.O(0, i4, i5);
                            if (i5 > 0) {
                                ci.hI(-i5);
                            }
                            ci.Fy();
                        } else {
                            ci.bN(i5, i4 - 111);
                        }
                        String str = "Reward item. add item.:" + i4;
                        if (i4 >= 0 && i4 < 6) {
                            ci.aa(ci.aZL[i4], i5, 22);
                        }
                    } else if (this.anG != 60 && this.anG == 67) {
                        ci.hg(this.anM);
                        ci.bW(1, 121);
                    }
                    if (ci.aYS == 1069) {
                        ci.axW = true;
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
        if ((ci.aAy - ci.aAw < 0 ? -(ci.aAy - ci.aAw) : ci.aAy - ci.aAw) <= 0) {
            if ((ci.aAz - ci.aAx < 0 ? -(ci.aAz - ci.aAx) : ci.aAz - ci.aAx) <= 0) {
                return;
            }
        }
        int i = ci.aAy - ci.aAw;
        int i2 = ci.aAz - ci.aAx;
        float fSqrt = (float) Math.sqrt((i * i) + (i2 * i2));
        this.alK = i / fSqrt;
        this.alL = i2 / fSqrt;
        String str = "velo slid:" + fSqrt + ",x:" + this.alK;
        if (fSqrt > 200.0f) {
            return;
        }
        float f2 = (i * i) + (i2 * i2);
        float fSqrt2 = (float) Math.sqrt((ci.kH() * ci.kH()) + (ci.kI() * ci.kI()));
        if ((1000 - ci.aAA) / 1000.0f >= 0.0f) {
            f = ((float) (1000 - ci.aAA)) / 1000.0f > 1.0f ? 1.0f : (1000 - ci.aAA) / 1000.0f;
        }
        this.alI = f * ((100.0f * fSqrt) / fSqrt2);
        this.alJ = (1.0f - (fSqrt / fSqrt2)) * 0.5f;
        String str2 = "velo inertia:" + this.alI + ",drop:" + this.alJ;
    }

    void pg() {
    }

    void ph() {
        ci.awX = 1;
        fu(4);
    }

    void pi() {
        ci.awX = 2;
        fv(-1);
    }

    boolean pm() {
        if (ci.aYR == 1 || ci.aYR == 2 || ci.aYR == 3 || (ci.aYR >= 32 && ci.aYR <= 36)) {
            return false;
        }
        String str = "[Actor CROP] Crop_Pressed. cropState:" + this.Bj;
        if (this.Bj == 5) {
            ci.hc(1);
        }
        return pn();
    }

    boolean pn() {
        String str = "Crop_Over m_cropType :" + ci.avS;
        if (ci.aYR == 4 || ci.aYR == 7 || ci.aYR == 45) {
            ci.axW = true;
        }
        if (this.Bj != 1 || ci.avP != 0) {
            if (this.Bj != 5 || ci.avP != 1) {
                return false;
            }
            if (((ci.aCi & 2) == 2) && !ci.aAY) {
                if ((ci.aZB != 0 || ci.hF(5) + fe(3) + 1 <= ci.Fw()) && (ci.aZB != 0 || ci.hF(5) + ci.aAX + 1 <= ci.Fw())) {
                    ci.a(1, this);
                    if (ci.aYR != 5) {
                        return true;
                    }
                    ci.ayp = false;
                    return true;
                }
                ci.aAY = true;
                ci.c(false, 1, ci.avR);
                ci.awH = false;
                ci.avP = -1;
                ci.H(481, 487, 7, 0);
                return false;
            }
            return false;
        }
        if (ci.awc > ci.FC()) {
            return true;
        }
        if (ci.aYR == 46) {
            String str2 = "[crop] over. plant tomato. set croptype tomato.:" + ci.avS + ", tomat:1";
            ci.avS = 1;
        }
        fi(ci.avS);
        if ((ci.avV > 0 && ci.hO(ci.avV)) || (ci.avW > 0 && ci.hP(ci.avW))) {
            ci.a(0, this);
            if (ci.aYR != 5 && ci.aYR != 8) {
                return true;
            }
            ci.ayp = false;
            return true;
        }
        ci.aLV = -1;
        if (ci.avV > 0) {
            ci.aSX = 4;
            ci.aSW = this;
            ci.b(ci.avV - ci.FA(), (short) 19);
            if (ci.avV > ci.FA()) {
                ci.bal = ci.avV - ci.FA();
                ci.bak = ci.ge(ci.avV - ci.FA());
            }
        } else if (ci.avW > 0) {
            String str3 = "not enough cash : " + ci.avW;
            ci.aSX = 4;
            ci.aSW = this;
            ci.a(ci.avW - ci.FB(), (short) 20);
        }
        if (ci.aLV != -1) {
            ci.fE(35);
            ci.bax = true;
        }
        ci.avP = -1;
        this.amn = false;
        return true;
    }

    boolean po() {
        String str = "[Crop] clicked/" + this.Bj + "," + ci.avP;
        if (ci.aYR == 38) {
            return false;
        }
        if (this.Bj == 1 && ci.avP != 0) {
            if ((ci.aCi & 1) == 1) {
                ci.hc(0);
                return true;
            }
        }
        if (ci.aYR >= 14 && ci.aYR <= 35) {
            String str2 = "ignore in tutorial guide step." + ci.awW;
            return false;
        }
        if ((this.Bj != 3 && this.Bj != 4) || ci.avP != -1) {
            return false;
        }
        String str3 = "[Crop] . cut tuto.:" + ci.aYR + ",endtuto:-1";
        if (ci.aYR != -1) {
            return false;
        }
        long jMD = ci.MD();
        if (jMD - ci.avC >= 700) {
            ci.gX(46);
            ci.avC = jMD;
        }
        ci.a(true, this);
        return false;
    }

    void pp() {
        String str = "Crop_PlantSeed m_dataBaseID : " + this.aoC + " m_cropCostCash : " + this.amg;
        String str2 = "m_cropType :" + ci.avS + " m_name : " + this.aoV;
        if ((this.amf <= 0 || !ci.hN(this.amf)) && (this.amg <= 0 || !ci.hQ(this.amg))) {
            ci.aLV = -1;
            if (this.amf > 0) {
                ci.aSX = 5;
                ci.aSW = this;
                ci.b(ci.avV - ci.FA(), (short) 19);
                if (ci.avV > ci.FA()) {
                    ci.bal = ci.avV - ci.FA();
                    ci.bak = ci.ge(ci.avV - ci.FA());
                }
            } else if (this.amg > 0) {
                String str3 = "not enough cash : " + this.amg;
                ci.aSX = 5;
                ci.aSW = this;
                ci.a(this.amg - ci.FB(), (short) 20);
            }
            if (ci.aLV != -1) {
                ci.fE(35);
                ci.bax = true;
            }
            ci.avP = -1;
            this.amn = false;
            if (this.amf > 0 && this.amf > ci.FA()) {
                ci.bal = this.amf - ci.FA();
                return;
            } else {
                if (this.amg <= 0 || this.amg <= ci.FB()) {
                    return;
                }
                ci.bak = this.amg - ci.FB();
                return;
            }
        }
        String str4 = "namvt +++++++++++++++++ Promo Coin : " + ci.avY + " - Promo Cash : " + ci.avX;
        j jVarBY = j.bY(2);
        bb bbVar = new bb();
        bbVar.aiw = 1;
        bbVar.aiG = ci.FB();
        bbVar.aiH = this.amf > 0 ? 111552 : 111551;
        bbVar.aiD = jVarBY.KW[this.aoC][30];
        bbVar.aiI = ci.baj;
        bbVar.aiJ = ci.bai;
        bbVar.aiK = this.amf > 0 ? this.amf : this.amg;
        bbVar.aiL = ci.avX > 0 ? ci.avX : ci.avY;
        bbVar.aiM = ci.ban;
        bbVar.aiF = ci.FC();
        bbVar.aiN = this.aoC;
        ci.a(bbVar);
        fj(3);
        String str5 = "[crop] actor . plant seed. :" + ci.aYR + ",db id:" + this.aoC + ",uid:" + this.aoU;
        if (ci.aYR == 46) {
            ci.ayf++;
        } else if (ci.aYR == 8) {
            ci.ayf++;
        } else if (ci.aYR == -1) {
            ci.aa(this.aoU, 1, 12);
        }
    }

    void pq() {
        fj(1);
        if (ci.aYR == 38 || ci.aYR == 5) {
            ci.ayf++;
        }
        at(false);
        this.aoU = ci.aZE[0];
        this.aoV = ci.aZF[0];
        fy(1024);
    }

    int pr() {
        return this.Bj;
    }

    void ps() {
        if (this.Bj == 2) {
            long jMD = (ci.MD() - this.amq) / 1000;
            if (jMD >= ci.ii(this.amr)) {
                fk(3);
                if (ci.aYS == 1067 || ci.aYS == 1066) {
                    ci.axW = true;
                    if (ci.aYS == 1066) {
                        ci.axN = 1068;
                    }
                }
            }
            if (ci.aZo == this && ci.ii(this.amr) != 0) {
                ci.ba((int) jMD, ci.ii(this.amr));
                this.aoX = A(this.amp, (int) (((long) ci.ii(this.amr)) - jMD), ci.ii(this.amr));
            }
        } else if (ci.aZo == this) {
            ci.a(false, (ch) null);
        }
        if (this.aoC == 22 || this.aoC == 23) {
            if (this.Bj == 6 && (this.aoR == null || (this.aoR != null && this.aoR.lQ()))) {
                fk(2);
                if (ci.aYS == 1073) {
                    ci.axW = true;
                }
            }
            if (this.aoR != null) {
                this.aoR.dV(GLLib.Qy);
            }
        }
    }

    boolean pt() {
        if (this.amu) {
            return false;
        }
        if (this.Bj == 3) {
            ci.hc(1);
            if (this.aoC == 22 || this.aoC == 23) {
                ci.awH = false;
            }
        }
        return pu();
    }

    boolean pv() {
        if (this.amu) {
            String strDt = ci.dt(12413);
            if (strDt == null) {
                strDt = "";
            }
            ci.m(strDt, 7, 0);
            return false;
        }
        String str = "tree clicked. sate :" + this.Bj;
        if (this.Bj == 2) {
            if (ci.aYR != 18) {
                String str2 = "[tree] show progess bar.:paint actino:" + ci.avP;
                if (ci.avP == -1) {
                    if (ci.aYS == 1066) {
                        ci.gX(79);
                        ci.axW = true;
                    } else if (this.aoC == 22 || this.aoC == 23) {
                        ci.gX(79);
                    } else {
                        ci.gX(46);
                    }
                    ci.a(true, this);
                    return true;
                }
            }
        } else if (this.Bj == 3 && ci.avP != 1) {
            ci.hc(1);
            if (this.aoC != 22 && this.aoC != 23) {
                return true;
            }
            ci.awH = false;
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
        this.amr = ci.ih((this.apj * ci.ma(this.aoU)) / 100);
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
        this.aoR = new aa(ci.aZf[13], this.aot, this.aou);
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
        ci.o(this);
        oN();
    }

    void qh() {
        ci.n(this);
        this.Bj = 1;
        this.aoO.Y(0, -1);
        this.aoH = 0;
    }

    boolean qk() {
        return (this.aoE == this.aol && this.aoF == this.aom) ? false : true;
    }

    void ql() {
        if (this.Kx == 49) {
            this.ame = ci.ih(0);
            fj(5);
            return;
        }
        if (this.Kx == 54) {
            if (fz(32)) {
                this.amI = ci.ih(0);
                pR();
            } else {
                this.amE = ci.ih(0);
            }
            ci.aZq = 100;
            ci.aZr = "0:0:0";
            return;
        }
        if (this.Kx == 52 || this.Kx == 69) {
            if (ci.aYS == 1067) {
                ci.axW = true;
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
                ci.g(iArr2, iArr3);
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
                GLLib.a(iArr4[0], iArr5[0], iArr4[1], iArr5[1], iArr4[2], iArr5[2], this.apb);
                iArr[0] = GLLib.jX();
                iArr[1] = GLLib.jY();
                if (this.Kx == 42) {
                    aG(iArr[0], iArr[1]);
                }
            }
        }
        int i2 = iArr[0] - this.aol;
        int i3 = iArr[1] - this.aom;
        int iCR = GLLib.cR((i2 * i2) + (i3 * i3));
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
        int i4 = ((this.aos * GLLib.Qy) << 8) / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
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
                this.aoU = ci.aZE[0];
                this.aoV = ci.aZF[0];
                fy(1024);
            } else {
                if (fz(2)) {
                    ci.o(this);
                }
                iQn = qn();
                oN();
            }
        } else {
            if (fz(2)) {
                ci.o(this);
            }
            iQn = qn();
            oN();
        }
        qq();
        ci.a(false, iQn, this.aol, this.aom);
        if (ci.baf != null) {
            int i2 = 119043;
            switch (this.Kx) {
                case 20:
                    if (ci.gj(this.aoC)) {
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
                    if (ci.gi(this.aoC)) {
                        i = 150870;
                    } else {
                        i = 115421;
                        i2 = 116460;
                    }
                    ci.l(this);
                    break;
                case 52:
                    i = 116010;
                    break;
                case 54:
                    if (!ci.gg(this.aoC)) {
                        i = !ci.gh(this.aoC) ? 115423 : 150869;
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
            ci.a(115443, new String[]{"INT:" + Integer.toString(ci.FB()), "INT:" + Integer.toString(0), "INT:" + Integer.toString(iQn), "INT:" + Integer.toString(i2), "INT:" + Integer.toString(i), "N/A", "N/A", "INT:" + Integer.toString(0), "INT:" + Integer.toString(ci.FC())});
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
        ci.d(new int[]{this.aol, this.aom}, iArr);
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
            return iArr[0] > (-i2) / 2 && iArr[0] < (i2 / 2) + GLLib.kH() && iArr[1] >= (-i) / 2 && iArr[1] < (i / 2) + GLLib.kI();
        }
        return iArr[0] > (-100) - i2 && iArr[0] < i2 + (GLLib.kH() + 100) && iArr[1] >= (-100) - i && iArr[1] < i + (GLLib.kI() + 100);
    }

    void y(int i, int i2, int i3) {
        this.aoE = i;
        this.aoF = i2;
        this.Bj = 1;
        this.alM = i3;
    }

    void z(int i, int i2, int i3) {
        int[] iArr = new int[2];
        if (!ci.a(this, iArr, i + i3, i2 + i3)) {
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
            this.aoO.dV(GLLib.Qy);
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
            int i3 = ci.aYP;
            if (w.Zo) {
                aaVar2.aaW = i3;
            } else {
                GLLib.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            GLLib.l(18, 5, 1);
            this.aoR.lR();
            GLLib.l(18, 5, 0);
        }
        if (this.aoS != null) {
            aa aaVar3 = this.aoS;
            int i4 = this.aot + this.aoP;
            int i5 = this.aou + this.aoQ;
            aaVar3.aaL = i4;
            aaVar3.aaM = i5;
            aa aaVar4 = this.aoS;
            int i6 = ci.aYP;
            if (w.Zo) {
                aaVar4.aaW = i6;
            } else {
                GLLib.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            GLLib.l(18, 5, 1);
            this.aoS.lR();
            GLLib.l(18, 5, 0);
        }
    }

    void pl() {
        if (this.aoR != null) {
            this.aoR.dV(GLLib.Qy);
        }
        if (this.Bj == 3) {
            long jMD = (ci.MD() - this.amd) / 1000;
            if (jMD >= ci.ii(this.ame) / 2 && ci.aYR != 8 && ci.aYR != 46) {
                fj(4);
            }
            if (ci.aZo == this && ci.ii(this.ame) != 0) {
                ci.ba((int) jMD, ci.ii(this.ame));
                this.aoX = A(this.aml, (int) (((long) ci.ii(this.ame)) - jMD), ci.ii(this.ame));
            }
        } else if (this.Bj == 4) {
            long jMD2 = (ci.MD() - this.amd) / 1000;
            if (jMD2 >= ci.ii(this.ame) && ((ci.aYR < 47 || ci.aYR > 58) && ci.aYR != 8 && ci.aYR != 46)) {
                fj(5);
            }
            if (ci.aZo == this && ci.ii(this.ame) != 0) {
                ci.ba((int) jMD2, ci.ii(this.ame));
                this.aoX = A(this.aml, (int) (((long) ci.ii(this.ame)) - jMD2), ci.ii(this.ame));
            }
        } else if (ci.aZo == this) {
            ci.a(false, (ch) null);
        }
        if ((this.aoR == null || this.aoR.aaO != ci.aZf[16]) && this.Bj == 5) {
            this.aoR = new aa(ci.aZf[16], this.aot, this.aou);
            this.aoR.Y(0, -1);
            this.aoR.aaY = 0;
        } else if (this.aoR != null && this.Bj != 5 && this.aoR.aaO == ci.aZf[16]) {
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
            if (a(1, 21, new ch[1], 1) == 0) {
            }
            return;
        }
        int iL = GLLib.L(25, 29);
        String str = "Fireworks sndIndex : " + iL;
        ci.gX(iL);
        this.aoO.Y(0, 1);
        int iBy = this.aoO.aaO.by(0);
        int iBz = this.aoO.aaO.bz(0);
        this.aol = ((GLLib.L(iBy, GLLib.kH() - iBy) + ci.aZj.aol) * 100) / ci.aYP;
        this.aom = ((ci.aZj.aom + GLLib.L(iBz, GLLib.kI() - iBz)) * 100) / ci.aYP;
        if (ci.apC[ci.apB] == 14) {
            this.aot = ((this.aol * ci.aYP) / 100) - ci.aZj.aol;
            this.aou = ((this.aom * ci.aYP) / 100) - ci.aZj.aom;
        }
        this.aoO.aaY = GLLib.L(0, this.aoO.aaO.hw());
        if (anY > -1) {
            anY++;
        }
    }

    void qc() {
        if (this.aoO != null && this.aoO.lQ() && (aoa == -1 || aoa < aob)) {
            this.aoO.Y(1, 1);
            int iBy = this.aoO.aaO.by(0);
            int iBz = this.aoO.aaO.bz(0);
            this.aol = GLLib.L(-iBy, iBy) + aoc;
            this.aom = aod + GLLib.L(-iBz, iBz);
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
        a(1, 23, new ch[1], 1);
        pj();
        ch[] chVarArr = new ch[1];
        a(1, 42, chVarArr, 1);
        if (chVarArr != null) {
            chVarArr[0].fg(3);
            ci.gX(33);
        }
        ft(3);
    }

    void qd() {
        if (!ci.ha(1) && aog) {
            this.aoO = null;
            ci.awX = 0;
            ci.DJ();
            aog = false;
            ci.fE(42);
        }
        if (this.aoO == null || !this.aoO.lQ() || (aoe != -1 && aoe >= aof)) {
            if (this.aoO == null || !this.aoO.lQ() || aoe >= aof) {
            }
            return;
        }
        this.aoO.Y(0, 1);
        int iBy = this.aoO.aaO.by(0);
        int iBz = this.aoO.aaO.bz(0);
        this.aol = ((GLLib.L(iBy, GLLib.kH() - iBy) + ci.aZj.aol) * 100) / ci.aYP;
        this.aom = ((ci.aZj.aom + GLLib.L(iBz, GLLib.kI() - iBz)) * 100) / ci.aYP;
        if (ci.apC[ci.apB] == 14) {
            this.aot = ((this.aol * ci.aYP) / 100) - ci.aZj.aol;
            this.aou = ((this.aom * ci.aYP) / 100) - ci.aZj.aom;
        }
        this.aoO.aaY = GLLib.L(0, this.aoO.aaO.hw());
        if (aoe > -1) {
            aoe++;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:4:0x000d  */
    void fp(int i) {
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
                int iL2 = GLLib.L(1, 100);
                if (fz(32)) {
                    i5 = iL2 < 70 ? 5 : 7;
                } else {
                    i5 = iL2 < 70 ? 0 : 2;
                    if (this.anw) {
                        if (!fz(131072) || ci.axE) {
                            String str = " time_dog_idle = " + ci.axf;
                            String str2 = " cGame.time_dog_idle_start = " + ci.axe;
                            if (iL2 < 40) {
                                if (iL2 < 20) {
                                    ci.axE = true;
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
                int iL3 = (!fz(131072) || (ci.axE && this.anw)) ? GLLib.L(1, 5) : -1;
                if (i5 == 23 || i5 == 22) {
                    z = false;
                    iL = GLLib.L(1, 3);
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
                    i2 = GLLib.L(1, 100) >= 70 ? 7 : 5;
                }
                break;
            case 3:
                fy(1);
                int i10 = fz(32) ? 6 : 1;
                ci.axi = true;
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
                    int iL4 = GLLib.L(1, 100);
                    int i11 = fz(32) ? iL4 < 70 ? 5 : 7 : iL4 < 70 ? 0 : 2;
                    z = false;
                    iL = GLLib.L(1, 5);
                    i2 = i11;
                } else {
                    int i12 = GLLib.L(1, 100) < 70 ? 0 : 2;
                    z = false;
                    iL = GLLib.L(1, 5);
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
                ci.axh = true;
                ci.aa(ci.aZL[118], 1, 44);
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
                    this.aoS = new aa(ci.aZf[171], this.aot, this.aou);
                    this.aoS.Y(12, 3);
                } else if (this.anp == 2) {
                    this.aoS = new aa(ci.aZf[172], this.aot, this.aou);
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
                ci.axh = true;
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
                if (ci.aYR == -1) {
                    if (alt) {
                        e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 23, -63, -117, 0, 1}));
                        alt = false;
                    }
                    if (!b.Kf) {
                        qq();
                    }
                    if (!b.Kc || b.Kg) {
                        if (b.JU || (ci.bbd != 0 && ci.MD() - ci.bbd < alu)) {
                            b.Kd = false;
                            qq();
                        } else if (ci.qO()) {
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
                                ci.dE("enter_section_video_bubble");
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
                    ci.aCh = 3;
                }
                break;
            case 3:
                if (this.aoO.lQ()) {
                    ci.aCl = false;
                    fg(0);
                    ci.n(300, 301, 15, 0, 1);
                    if (ci.qN()) {
                        String str = "character jump. manor restored.:" + ci.aZD;
                        if (ci.aZD == 3 && ci.aTK) {
                            ci.aLV = 14;
                        }
                    }
                    ci.awX = 0;
                }
                break;
        }
    }

    void pH() {
        ch chVarFn;
        String strDt;
        ch chVarHA = null;
        switch (this.Bj) {
            case 0:
                if (this.anw && ci.axh && qs() && ci.aZB == 0) {
                    if (this.aoO.aaQ == 6 && this.aoO.aaP == 23) {
                        ci.gX(64);
                        ci.axh = false;
                    } else if (this.aoO.aaQ == 1 && (this.aoO.aaP == 2 || this.aoO.aaP == 22)) {
                        ci.gX(75);
                        ci.axh = false;
                    }
                }
                if (this.anw && this.aoO.aaQ == 0) {
                    ci.axh = true;
                }
                if (!ci.axE || !this.anw) {
                    if (this.aoO.lQ()) {
                        if (ci.avD != 0 && ci.avD != 1 && ci.avD != 3 && ((!ci.ss() || ci.aZo != this) && ((qs() || fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) && !ci.bad && this.anp == 0))) {
                            if (GLLib.L(0, 100) >= (!this.anw ? 20 : 0)) {
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
                    ci.f(new int[]{this.aol, this.aom}, iArr);
                    if (ci.ss() && ci.aZo == this) {
                        this.aol = i;
                        this.aom = i2;
                        fp(0);
                    }
                    if (!ci.bD(iArr[0], iArr[1]) && !this.anw && !this.anj && !this.ank) {
                        this.aol = i;
                        this.aom = i2;
                        fp(0);
                        break;
                    } else if (this.aol == this.aoE && this.aom == this.aoF && this.aoE != 0 && this.aoF != 0) {
                        if (((this.anj && !this.anl) || (this.ank && !this.anm)) && ci.axt != 0) {
                            fp(9);
                        } else if (this.anw && this.ann) {
                            this.ann = false;
                            if (ci.axt == 1) {
                                chVarHA = ci.hA(73);
                            } else if (ci.axt == 2) {
                                chVarHA = ci.hA(74);
                            }
                            if (chVarHA != null) {
                                c(true, chVarHA.anj);
                            }
                            fp(8);
                            if (!ci.avp) {
                                ci.DJ();
                                ci.g(3, 68, false);
                                ci.avp = true;
                            }
                            int i3 = ci.axt == 2 ? 4 : 3;
                            this.aoS = new aa(ci.aZf[13], this.aot, this.aou);
                            this.aoS.Y(i3, 5);
                            this.aoP = this.amZ;
                            this.aoQ = this.ana;
                        } else {
                            fp(0);
                            if (this.anj || this.ank) {
                                fy(8);
                                ci.axt = 0;
                                ci.bbp = 0;
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
                if (this.aoO.aaQ == 2 && ci.axi) {
                    ci.gX(78);
                    ci.axi = false;
                }
                if (this.aoO.lQ() && !fz(GLURLConnection.BUFFER_SIZE)) {
                    String str = "ANIMAL EATING animation over. step:" + ci.awW + ",guide cow:" + ci.awO;
                    if (!ci.awO && ci.qD() == 19) {
                        ci.awO = true;
                        ci.axW = false;
                        ci.axV = false;
                        ci.aYR = 33;
                    }
                    if (this.anw) {
                        if (fz(32)) {
                            pR();
                            if (ci.aYS == 1019) {
                                ci.axW = true;
                            }
                        }
                        ci.c(this, true);
                    }
                    fp(0);
                    if (this.anw && pC() <= 5) {
                        fq(131072);
                        break;
                    }
                }
                break;
            case 5:
                if (!(this.anw && !ci.lM(ci.aZQ[298])) && !fz(GLURLConnection.BUFFER_SIZE)) {
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
                        ci.c(this, false);
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
                        ch chVarHA2 = ci.axt == 1 ? ci.hA(73) : ci.axt == 2 ? ci.hA(74) : null;
                        if (chVarHA2 != null) {
                            boolean zA = ci.auj ? true : ci.a(this, chVarHA2);
                            au(false);
                            ci.f(false, false);
                            ci.DJ();
                            ci.avp = false;
                            if (!ci.auj) {
                                ci.gX(76);
                            }
                            if (ci.auj) {
                                ci.azz[ci.aNI][6] = ci.MD() / 1000;
                                ci.aa((ci.aZJ[0] + ci.aZB) - 1, 1, 24);
                            }
                            chVarHA2.fp(14);
                            if (!ci.awV && ci.aYS != -1) {
                                String[] strArr = new String[2];
                                strArr[0] = ci.aPn[ci.aPp];
                                if (chVarHA2.anj) {
                                    strDt = ci.dt(992);
                                    if (strDt == null) {
                                        strDt = "";
                                    }
                                } else {
                                    strDt = ci.dt(993);
                                    if (strDt == null) {
                                        strDt = "";
                                    }
                                }
                                strArr[1] = strDt;
                                ci.m(ci.a(959, (Object[]) strArr), 7, 0);
                            }
                            int iA = ci.a(this, chVarHA2, -1, (ch) null, zA);
                            if (ci.lN(GLURLConnection.HTTP_FOUND) && (chVarFn = ci.Fn()) != null) {
                                chVarFn.pT();
                                if (chVarFn.fz(2)) {
                                    ci.o(chVarFn);
                                }
                                ci.w(chVarFn);
                                ci.avF = null;
                            }
                            if (ci.auj) {
                                int i4 = 52183;
                                int i5 = 52009;
                                if (ci.aND.equals("")) {
                                    i4 = 52185;
                                    i5 = 0;
                                }
                                ci.a(51811, new String[]{"INT:0", "INT:" + Integer.toString(150855), "INT:" + Integer.toString(i4), "INT:0", "INT:0", "INT:0", "INT:" + Integer.toString(i5), "INT:" + Integer.toString(iA), "INT:" + Integer.toString(ci.FC())});
                            } else {
                                ch chVarFo = ci.Fo();
                                if (!zA && chVarFo != null) {
                                    int[] iArr2 = new int[2];
                                    if (a(chVarFo, this, iArr2)) {
                                        this.ann = true;
                                        aN(iArr2[0], iArr2[1]);
                                        this.ann = false;
                                    }
                                }
                            }
                            if (ci.aYS == 1023 || ci.aYS == 1050 || ci.aYS == 1057 || ci.aYS == 1064) {
                                ci.axW = true;
                            }
                            ci.aa(2999, 1, 43);
                        }
                        break;
                    } else {
                        if (ci.axt == 1) {
                            chVarHA = ci.hA(73);
                        } else if (ci.axt == 2) {
                            chVarHA = ci.hA(74);
                        }
                        if (chVarHA != null && chVarHA.aoO.aaP != 15) {
                            chVarHA.aoO.Y(15, -1);
                            break;
                        }
                    }
                } else {
                    if (!ci.auj) {
                        fp(14);
                    }
                    break;
                }
                break;
            case 9:
                if (!fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
                    if (!fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
                        ci.a(false, (ch) null);
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
                    if (ci.aYS == 1021) {
                        ci.axS++;
                    }
                    if (ci.aYS == 1021 && (!ci.axd || ci.axS == 25)) {
                        ci.axS = 30;
                        ci.axd = false;
                        ci.axW = true;
                        ci.aMw = "";
                        String strDt2 = ci.dt(957);
                        if (strDt2 == null) {
                            strDt2 = "";
                        }
                        ci.m(strDt2, 7, 0);
                    }
                    if (ci.auj && ci.aNb) {
                        long jMD = (ci.MD() / 1000) - (ci.aNa / 1000);
                        String str2 = "elapsedSecTime :: show arrow ::" + jMD;
                        if (jMD > 30) {
                            ci.b(this, 0, -70);
                            ci.aZj.y(this.aol, this.aom, 0);
                            ci.aNb = false;
                            ci.aNa = ci.MD();
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
                    ci.a(this, (ch) null, this.anD, (ch) null, false);
                    fp(0);
                }
                switch (this.anD) {
                    case 64:
                        if (this.aoO.aaQ == 9 && ci.axh) {
                            ci.axh = false;
                            ci.gX(67);
                        }
                        if (this.aoO.aaQ != 9) {
                            ci.axh = true;
                        }
                        break;
                    case 65:
                        if (this.aoO.aaQ == 9 && ci.axh) {
                            ci.gX(67);
                            ci.axh = false;
                        }
                        if (this.aoO.aaQ != 9) {
                            ci.axh = true;
                        }
                        break;
                    case 66:
                        if (this.aoO.aaQ == 9 && ci.axh) {
                            ci.gX(72);
                            ci.axh = false;
                        }
                        if (this.aoO.aaQ != 9) {
                            ci.axh = true;
                        }
                        break;
                    case 67:
                        if (this.aoO.aaQ == 9 && ci.axh) {
                            ci.gX(72);
                            ci.axh = false;
                        }
                        if (this.aoO.aaQ != 9) {
                            ci.axh = true;
                        }
                        break;
                }
                break;
            case 14:
                ci.f(false, false);
                aN(0, 0);
                break;
            case 15:
                if (!ci.atx) {
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
                    if (this.aoO.aaQ == 1 && this.aoO.aaP == 10 && ci.axh) {
                        ci.axh = false;
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (w.Vl) {
                            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                        }
                        if (Math.abs(jCurrentTimeMillis) / 2 == 0) {
                            ci.gX(77);
                        } else {
                            ci.gX(64);
                        }
                    }
                    if (this.aoO.aaP == 2 && this.aoO.aaQ == 1 && ci.axh) {
                        ci.axh = false;
                        ci.gX(75);
                    }
                    if (this.aoO.aaQ != 1 && this.aoO.aaQ != 2) {
                        if (this.aoO.aaP == 10 || this.aoO.aaP == 2) {
                            ci.axh = true;
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
            if ((i3 == 1 || i3 == 6) && ci.atf[1] != null) {
                if (this.aoC == 68) {
                    i = 10;
                } else {
                    i = this.aoC == 75 ? 16 : 0;
                }
                int i4 = this.aot + this.amX + i;
                int i5 = (this.aou + this.amY) - i;
                if (ci.aYP == 100) {
                    ci.atf[1].a(GLLib.Qj, this.anw ? 75 : 34, (float) i4, (float) i5, 0);
                } else {
                    GLLib.l(18, 5, 1);
                    ci.atf[1].b(GLLib.Qj, this.anw ? 75 : 34, i4, i5, 0, ci.aYP);
                    GLLib.l(18, 5, 0);
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
                    a aVar = i6 < 1000 ? ci.atf[i6] : ci.aZf[i6 - 1000];
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
                        if (ci.aYP == 100) {
                            aVar.a(GLLib.Qj, i2, (float) i7, (float) i8, 0);
                        } else {
                            GLLib.l(18, 5, 1);
                            aVar.b(GLLib.Qj, i2, i7, i8, 0, ci.aYP);
                            GLLib.l(18, 5, 0);
                        }
                    }
                }
                if (this.anw && ci.axv) {
                    String strDt = ci.dt(12330);
                    if (strDt == null) {
                        strDt = "";
                    }
                    ci.a(strDt, ci.axx, ci.axy - (ci.axw * 4), 0, 0, 0, 0, 2, 1, false, false);
                    if (ci.axw > 10) {
                        ci.axv = false;
                        if (ci.aYS == 1019) {
                            ci.axW = true;
                        }
                    } else {
                        ci.axw++;
                    }
                }
            }
            if (fz(2048)) {
                int i9 = this.aot + 5;
                int i10 = this.aou - 80;
                if (ci.aYP == 100) {
                    ci.atf[1].a(GLLib.Qj, 64, (float) i9, (float) i10, 0);
                    return;
                }
                GLLib.l(18, 5, 1);
                ci.atf[1].b(GLLib.Qj, 64, i9, i10, 0, ci.aYP);
                GLLib.l(18, 5, 0);
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
        GLLib.c(aaVar.aaO != null, "GLLibPlayer.GetNbanim().sprite is not set");
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
            int i = ci.aYP;
            if (w.Zo) {
                aaVar.aaW = i;
            } else {
                GLLib.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
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
        a aVar = ci.aZf[0];
        if (w.WY) {
            i3 = aVar.GE[0] & 65535;
        } else {
            i3 = aVar.GG[0] & 255;
        }
        a aVar2 = ci.aZf[0];
        if (w.WY) {
            i4 = aVar2.GF[0] & 65535;
        } else {
            i4 = aVar2.GH[0] & 255;
        }
        int i5 = i & 32767;
        a aVar3 = (32768 & i) != 0 ? ci.atf[i5] : ci.aZf[i5];
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
        for (ch chVar = als[i]; chVar != null; chVar = chVar.aoi) {
            if (chVar.Kx == 10) {
                z = true;
            }
            chVar.oR();
            chVar.oQ();
            chVar.oS();
            chVar.oU();
        }
        if (z) {
            aa.a(GLLib.Qj, 0, 0, 0);
        }
    }

    void pd() {
        int i;
        if (this.Bj == 0) {
            if (this.alI > 1.0f) {
                this.aol -= (int) (this.alK * this.alI);
                this.aom -= (int) (this.alL * this.alI);
                this.alI -= (this.alI * this.alJ) * (GLLib.Qy / 100.0f);
            }
            int i2 = this.alE;
            int i3 = this.alG;
            int iKH = this.alF - ((ci.kH() * 100) / ci.aYP);
            int iKI = this.alH - ((ci.kI() * 100) / ci.aYP);
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
            this.alM -= GLLib.Qy;
            int[] iArr = new int[2];
            ci.c(new int[]{ci.kH() >> 1, ci.kI() >> 1}, iArr);
            int i4 = this.aoE - iArr[0];
            int i5 = this.aoF - iArr[1];
            if (this.alM > 0) {
                i = (GLLib.Qy * 100) / this.alM;
                if (i > 100) {
                    i = 100;
                }
            } else {
                this.alM = 0;
                this.Bj = 0;
                if (ci.aYS == 1005) {
                    ci.axW = true;
                }
                if (ci.aum) {
                    String strDt = ci.dt(12393);
                    if (strDt == null) {
                        strDt = "";
                    }
                    ci.aMR = strDt;
                    if (ci.ayK[0]) {
                        String str = ci.aMR;
                        String strDt2 = ci.dt(ci.EI() + 320);
                        if (strDt2 == null) {
                            strDt2 = "";
                        }
                        ci.aMR = ci.l(str, "Acre", strDt2);
                    } else {
                        String str2 = ci.aMR;
                        String strDt3 = ci.dt(320);
                        if (strDt3 == null) {
                            strDt3 = "";
                        }
                        ci.aMR = ci.l(str2, "Acre", strDt3);
                    }
                    ci.b(ci.azd[ci.EI() + 1], 0, -80);
                    ci.m(ci.aMR, 7, 0);
                    ci.aum = false;
                }
                i = 100;
            }
            this.aol = ((i4 * i) / 100) + this.aol;
            this.aom = ((i * i5) / 100) + this.aom;
            int i6 = this.alE;
            int i7 = this.alG;
            int iKH2 = this.alF - ((ci.kH() * 100) / ci.aYP);
            int iKI2 = this.alH - ((ci.kI() * 100) / ci.aYP);
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
        aa.abW = ci.aYP;
    }

    void oR() {
        int i;
        if (this.Kx != 10 || ci.aZj.aom >= 648) {
            return;
        }
        float f = (648 - ci.aZj.aom) / 648.0f;
        if (648.0f - (f * 215.0f) > 0.0f) {
            i = (int) ((648.0f - (f * 215.0f)) + 0.5f);
        } else {
            i = 648.0f - (f * 215.0f) < 0.0f ? (int) ((648.0f - (f * 215.0f)) - 0.5f) : 0;
        }
        int i2 = i - ci.aZj.aom;
        if (ci.Mt()) {
            ci.dk(-15287308);
        } else {
            ci.dk(-15682062);
        }
        GLLib.b(GLLib.Qj, 0.0f, 0.0f, aa.abT[0][5], i2, true);
    }

    void pV() {
        if ((GLLib.L(0, 100) & 1) == 0) {
            this.aol = GLLib.L(aa.abT[0][5] >> 1, aa.abT[0][5]);
            this.aom = aa.abT[0][6];
        } else {
            this.aol = aa.abT[0][5];
            this.aom = GLLib.L(aa.abT[0][6] >> 1, aa.abT[0][6]);
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
                ci.aAQ[i3] = (byte) (ci.aAP[i3] & 1);
            }
            this.aoZ = new z();
            this.aoZ.a(96, 96, ci.aAQ, 14, 10, 10, 8);
        }
        int[] iArr2 = new int[2];
        ci.f(iArr, iArr2);
        iArr2[0] = iArr2[0] < 0 ? 0 : iArr2[0] > 95 ? 95 : iArr2[0];
        iArr2[1] = iArr2[1] < 0 ? 0 : iArr2[1] > 95 ? 95 : iArr2[1];
        String str = "targetGridPos x : " + iArr2[0] + " y : " + iArr2[1];
        int[] iArr3 = new int[2];
        ci.f(new int[]{this.aol, this.aom}, iArr3);
        iArr3[0] = iArr3[0] < 0 ? 0 : iArr3[0] > 95 ? 95 : iArr3[0];
        iArr3[1] = iArr3[1] < 0 ? 0 : iArr3[1] > 95 ? 95 : iArr3[1];
        String str2 = "characterGridPos x : " + iArr3[0] + " y : " + iArr3[1];
        this.aoZ.c(iArr3[0], iArr3[1], 0, iArr2[0], iArr2[1]);
        int iLK = this.aoZ.lK();
        this.apb = GLLib.Rk;
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
            ci.g(new int[]{this.aoZ.dO(this.apa), this.aoZ.dP(this.apa)}, iArr4);
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
                a aVar = ci.aZf[jVarBY.KW[this.aoC][31]];
                int iV = aVar.v(this.amW, 0);
                this.amZ = aVar.bw(iV);
                this.ana = aVar.bx(iV);
                this.aoP = this.amZ;
                this.aoQ = this.ana;
            }
            aa aaVar2 = this.aoR;
            int i4 = ci.aYP;
            if (w.Zo) {
                aaVar2.aaW = i4;
            } else {
                GLLib.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            if (this.aoR.lQ()) {
                if (i == 1) {
                    if (ci.aYR == 18) {
                        ci.axW = true;
                    }
                    if (this.anw && ci.axj != -1) {
                        ci.aa(ci.axj, 1, 21);
                        ci.axj = -1;
                    }
                }
                this.aoR = null;
            } else {
                this.aoR.dV(GLLib.Qy);
            }
        }
        if ((this.aoR == null || this.aoR.aaO != ci.aZf[16]) && this.amP) {
            this.aoR = new aa(ci.aZf[16], this.aot, this.aou);
            this.aoR.Y(0, -1);
            this.aoR.aaY = 1;
        } else if (this.aoR != null && !this.amP && this.aoR.aaO == ci.aZf[16]) {
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
            int i8 = ci.aYP;
            if (w.Zo) {
                aaVar4.aaW = i8;
            } else {
                GLLib.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
            }
            if (this.aoS.lQ()) {
                if (i5 == 1) {
                    if (ci.aYR == 18) {
                        ci.axW = true;
                    }
                    if (this.anw && ci.axj != -1) {
                        ci.aa(ci.axj, 1, 21);
                        ci.axj = -1;
                    }
                }
                this.aoS = null;
            } else {
                this.aoS.dV(GLLib.Qy);
            }
        }
        if (fz(Utility.DEFAULT_STREAM_BUFFER_SIZE)) {
            if (ci.FC() >= 10 && ci.aZB == 0 && !ci.auj && ci.lM(ci.aZQ[298]) && !ci.lN(GLURLConnection.HTTP_FOUND)) {
                String str = "time when active game :: " + (ci.bbp / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
                String str2 = "m_maxTimeThieveAppearPerDay :: " + ci.awo;
                String str3 = "m_timeAppearPerDay :: " + ci.awp;
                if (ci.axt == 1) {
                    if (this.anj && (ci.MD() / 1000) - this.amC >= 86400) {
                        ci.asQ = true;
                        ci.awp = 1;
                        this.amC = ci.MD() / 1000;
                    }
                } else if (ci.axt == 2) {
                    if (this.ank && (ci.MD() / 1000) - this.amC >= 86400) {
                        ci.asQ = true;
                        ci.awp = 1;
                        this.amC = ci.MD() / 1000;
                    }
                } else if (ci.axt == 0 && ci.avD == -1 && (ci.bbp / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS >= ci.awn || ((ci.axk || ci.axl) && ci.bbp / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS > 10))) {
                    if (ci.awm == -1) {
                        int iL = 0;
                        if (ci.aMg != null && ci.aMg.pB() >= 3) {
                            iL = GLLib.L(0, 10);
                        }
                        if (iL <= 5) {
                            ci.awm = 0;
                        } else {
                            ci.awm = 1;
                        }
                    }
                    if (ci.axk) {
                        ci.awm = 0;
                    } else if (ci.axl) {
                        ci.awm = 1;
                    }
                    if (this.anj && ci.awm == 0) {
                        if (this.anl) {
                            boolean z = true;
                            long jMD = (ci.MD() / 1000) - this.amC;
                            if (ci.awp < ci.awo) {
                                ci.awp++;
                                z = false;
                                if (jMD >= 86400) {
                                    ci.asQ = true;
                                    ci.awp = 1;
                                    this.amC = ci.MD() / 1000;
                                }
                            }
                            if (ci.awp >= ci.awo && jMD >= 86400) {
                                ci.asQ = true;
                                ci.awp = 0;
                                this.amC = ci.MD() / 1000;
                            }
                            if (ci.axk && ci.axt == 0) {
                                this.ani = false;
                                ci.axk = false;
                            } else if (ci.axt == 0 && !z) {
                                this.ani = false;
                                fr(151148);
                            }
                            ci.awm = -1;
                        }
                    } else if (this.ank && ci.awm == 1 && this.anm) {
                        boolean z2 = true;
                        long jMD2 = (ci.MD() / 1000) - this.amC;
                        if (ci.awp < ci.awo) {
                            ci.awp++;
                            z2 = false;
                            if (jMD2 >= 86400) {
                                ci.asQ = true;
                                ci.awp = 1;
                                this.amC = ci.MD() / 1000;
                            }
                        }
                        if (ci.awp >= ci.awo && jMD2 >= 86400) {
                            ci.asQ = true;
                            ci.awp = 0;
                            this.amC = ci.MD() / 1000;
                        }
                        if (ci.axl && ci.axt == 0) {
                            this.ani = false;
                            ci.axl = false;
                        } else if (ci.axt == 0 && !z2) {
                            this.ani = false;
                            fr(151149);
                        }
                        ci.awm = -1;
                    }
                }
                if (ci.asQ) {
                    ci.asQ = false;
                    ch chVarHA = ci.hA(73);
                    ch chVarHA2 = ci.hA(74);
                    if (chVarHA.amC > chVarHA2.amC) {
                        chVarHA2.amC = chVarHA.amC;
                    } else {
                        chVarHA.amC = chVarHA2.amC;
                    }
                }
                if (ci.bbp / Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS > ci.awn) {
                    ci.bbp = ci.awn * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
                }
            } else if ((ci.aYS == 1004 || ci.aYS == 1046) && this.anj) {
                ci.axW = true;
                this.ani = false;
            } else if (ci.auj) {
                if (this.anj && ci.auk == 0 && !ci.aul && this.aol == 0 && this.aom == 0) {
                    ci.aul = true;
                    this.ani = false;
                    if (ci.aYS == 1055) {
                        ci.axW = true;
                    }
                    fr(151148);
                } else if (this.ank && ci.auk == 1 && !ci.aul && this.aol == 0 && this.aom == 0) {
                    ci.aul = true;
                    this.ani = false;
                    fr(151149);
                }
            } else if (((ci.lM(ci.aZQ[298]) && !ci.lN(GLURLConnection.HTTP_FOUND)) || ci.FC() < 10) && ci.aZB <= 10 && (this.aol != 0 || this.aom != 0)) {
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
            if (!this.amT && ci.avE != null && ci.avE != this && this.aol > ci.aZW + 150 && this.aol < ci.aZW + 530 && this.aom > ci.aZX + 340 && this.aom < ci.aZX + 570) {
                this.amT = true;
                aM(ci.aZW + 150, ci.aZX + 446);
            }
            if (this.amT && this.aol == this.aoE && this.aom == this.aoF) {
                this.amT = false;
            }
            if (this.amU && this.aol == this.aoE && this.aom == this.aoF) {
                this.amU = false;
            }
        }
        pH();
        if (!fz(Utility.DEFAULT_STREAM_BUFFER_SIZE) && !fz(131072) && this.Bj != 13 && this.Bj != 15 && this.Bj != 8 && (!this.anw || !ci.axK)) {
            if (!fz(32) || fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
                if (!fz(4) && !fz(16) && !fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST)) {
                    long jMD3 = ci.MD();
                    if (ci.aYR == 33) {
                        this.amB = jMD3;
                        String str4 = "set last animal care delay:" + (jMD3 / 1000);
                    }
                    long j = (jMD3 - this.amB) / 1000;
                    if (!fz(256) && (ci.lN(20) || ci.lM(ci.aZQ[20]))) {
                        if (j > (ci.ii(this.amE) * 3) / 4 && this.amB > 0 && !this.amM) {
                            this.amM = true;
                            if (!this.amL) {
                                int iL2 = GLLib.L(0, 100);
                                String str5 = "Check animal request clean.!!!!!" + iL2;
                                if (iL2 < 35) {
                                    fq(256);
                                    this.amL = true;
                                }
                            }
                        } else if (j > (ci.ii(this.amE) >> 1) && this.amB > 0 && !this.amN) {
                            this.amN = true;
                            if (!this.amL) {
                                int iL3 = GLLib.L(0, 100);
                                String str6 = "Check animal request clean2.!!!!!" + iL3;
                                if (iL3 < 35) {
                                    fq(256);
                                    this.amL = true;
                                }
                            }
                        } else if (j > (ci.ii(this.amE) >> 2) && this.amB > 0 && !this.amO) {
                            this.amO = true;
                            if (!this.amL) {
                                int iL4 = GLLib.L(0, 100);
                                String str7 = "Check animal request clean3.!!!!!" + iL4;
                                if (iL4 < 35) {
                                    fq(256);
                                    this.amL = true;
                                }
                            }
                        }
                    }
                    if (!this.anw && j >= ci.ii(this.amE) && this.amB != 0) {
                        if (ci.aYR == 35) {
                            ci.axW = true;
                        }
                        if (ci.aYR != 34) {
                            String str8 = j + " -------------------------------ANIMAL_READY_PRODUCE------------------------- " + this.amE;
                            if (j != 0 || ci.ii(this.amE) >= 0) {
                                fq(16);
                                ci.a(false, (ch) null);
                            } else {
                                pM();
                            }
                        }
                    }
                    if (this.anw && j >= ci.ii(this.amE) && this.amB != 0 && ci.aYR != 34) {
                        pM();
                        fp(2);
                    }
                    if (ci.aZo == this && ci.ii(this.amE) != 0) {
                        ci.ba((int) j, ci.ii(this.amE));
                        this.aoX = A(this.amH, (int) (((long) ci.ii(this.amE)) - j), ci.ii(this.amE));
                    }
                }
            } else if (!this.anw) {
                long jMD4 = ci.MD();
                long j2 = (jMD4 / 1000) - this.amJ;
                if (this.and && this.ane > 0) {
                    this.amI = ci.ih((int) (((jMD4 - this.ane) / 1000) + ((long) (j.bY(0).KW[this.aoC][14] * 60))));
                }
                if (ci.ii(this.amI) > 0 && ci.aZo == this) {
                    ci.ba((int) j2, ci.ii(this.amI));
                    this.aoX = A(this.amH, (int) (((long) ci.ii(this.amI)) - j2), ci.ii(this.amI));
                }
                if (j2 >= ci.ii(this.amI)) {
                    pR();
                }
            } else if (ci.avD == -1 && !fz(4) && this.Bj == 0 && ci.aZB == 0) {
                pM();
                fp(2);
            }
            if (fz(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) && ci.axt != 0 && !this.anw && (this.aoM == null || this.aoM.Kx == -1)) {
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
            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        if (jCurrentTimeMillis - this.amv <= 7000 || ci.qD() != 18) {
            return;
        }
        if (ci.avE == null || ci.avE != this) {
            if (!this.anw && GLLib.L(0, 100) < 10) {
                ci.gY(this.aoY);
            }
            this.amv = ci.MD();
        }
    }

    void pf() {
        if (this.aoC == 18) {
            return;
        }
        if ((this.aoC == 12 || this.aoC == 11) && ci.aZB == 0 && fz(AdRequest.MAX_CONTENT_URL_LENGTH)) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
            }
            long j = (jCurrentTimeMillis / 1000) - this.alV;
            if (j < ci.ii(alU) && ci.aZo == this) {
                ci.ba((int) j, ci.ii(alU));
                this.aoX = ci.gf((int) (((long) ci.ii(alU)) - j));
            }
            if (this.alW || j < ci.ii(alU)) {
                return;
            }
            this.alW = true;
            fx(16);
            e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
            if (ci.ss()) {
                ci.a(false, (ch) null);
                return;
            }
            return;
        }
        if (this.aoC == 15 && ci.aTK) {
            aa aaVar = this.aoO;
            if (ci.EN() || ci.EM()) {
                if (this.alZ >= 0) {
                    this.alZ -= GLLib.Qy;
                    if (this.alZ < 0) {
                        this.aoO = new aa(ci.aZf[this.aoG], this.aot, this.aou);
                        this.aoO.Y(0, 1);
                    }
                } else if (this.aoO != null) {
                    this.aoO.dV(GLLib.Qy);
                    if (this.aoO.lQ()) {
                        this.aoO = null;
                        this.alZ = GLLib.L(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, 4000);
                    }
                }
            } else if (ci.aNU > 0 || ci.aNY > 0) {
                if (this.alZ >= 0) {
                    this.alZ -= GLLib.Qy;
                    if (this.alZ < 0) {
                        this.aoO = new aa(ci.aZf[this.aoG], this.aot, this.aou);
                        this.aoO.Y(0, 1);
                    }
                } else if (this.aoO != null) {
                    this.aoO.dV(GLLib.Qy);
                    if (this.aoO.lQ()) {
                        this.aoO = null;
                        this.alZ = GLLib.L(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, 4000);
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
            if (ci.aZB != 0 || !ci.EQ()) {
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
            if (!this.ama && ci.lM(ci.aZQ[0]) && ci.lM(ci.aZQ[1])) {
                this.ama = true;
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
            }
            if (this.alZ >= 0) {
                this.alZ -= GLLib.Qy;
                if (this.alZ < 0) {
                    this.aoO = new aa(ci.aZf[this.aoG], this.aot, this.aou);
                    this.aoO.Y(0, 1);
                    return;
                }
                return;
            }
            if (this.aoO != null) {
                this.aoO.dV(GLLib.Qy);
                if (this.aoO.lQ()) {
                    this.alZ = GLLib.L(10000, 30000);
                    this.aoO = null;
                    return;
                }
                return;
            }
            return;
        }
        if (this.aoC == 2) {
            if (!ci.GT()) {
                if (this.aoR != null) {
                    this.aoR = null;
                    return;
                }
                return;
            }
            if (this.aoR == null) {
                a aVar = ci.aZf[this.aoG];
                int iV = aVar.v(1, 0);
                this.aoP = aVar.bw(iV);
                this.aoQ = aVar.bx(iV);
                this.aoR = new aa(ci.aZf[147], this.aot, this.aou);
            }
            if (this.alZ >= 0) {
                this.alZ -= GLLib.Qy;
                if (this.alZ < 0) {
                    this.aoR.Y(0, 1);
                    return;
                }
                return;
            }
            this.aoR.dV(GLLib.Qy);
            if (this.aoR.lQ()) {
                this.alZ = GLLib.L(3000, 4000);
                return;
            }
            return;
        }
        if (this.aoC == 1) {
            if (ci.aZB == 0) {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                }
                long j2 = jCurrentTimeMillis2 / 1000;
                if (!ci.IL() || !ci.aTK) {
                    if (this.aoR != null) {
                        this.aoR = null;
                        return;
                    }
                    return;
                } else {
                    if (this.aoR != null) {
                        this.aoR.dV(GLLib.Qy);
                        return;
                    }
                    a aVar2 = ci.aZf[this.aoG];
                    int iV2 = aVar2.v(ci.aZD < 4 ? 4 : 1, 0);
                    this.aoP = aVar2.bw(iV2);
                    this.aoQ = aVar2.bx(iV2);
                    this.aoR = new aa(ci.aZf[148], this.aot, this.aou);
                    this.aoR.Y(0, -1);
                    return;
                }
            }
            return;
        }
        if (this.aoC == 28) {
            if (!this.alW && ci.Hi()) {
                this.alW = true;
                fx(16);
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
            }
            long jCurrentTimeMillis3 = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis3 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
            }
            long j3 = jCurrentTimeMillis3 / 1000;
            boolean z = false;
            for (int i = 0; !z && i < ci.aGm; i++) {
                z = ci.aGo[i] && ci.aGs[i] > 0 && j3 < ci.aGs[i] + ((long) ci.ii(ci.aGr[i]));
            }
            if (z) {
                if (this.aoO != null) {
                    this.aoO.dV(GLLib.Qy);
                    return;
                } else {
                    this.aoO = new aa(ci.aZf[this.aoG], this.aot, this.aou);
                    this.aoO.Y(1, -1);
                    return;
                }
            }
            if (ci.bL(0, 119) >= ci.aGa) {
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
            if (!this.alW && ci.Hh()) {
                this.alW = true;
                fx(16);
                e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
            }
            long jCurrentTimeMillis4 = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis4 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
            }
            long j4 = jCurrentTimeMillis4 / 1000;
            boolean z2 = false;
            for (int i2 = 0; !z2 && i2 < ci.GY(); i2++) {
                z2 = ci.aHa[i2] > 0 && j4 < ci.aHa[i2] + ((long) ci.ii(ci.aGZ[i2]));
            }
            if (!z2) {
                if (this.aoO != null) {
                    this.aoO = null;
                    return;
                }
                return;
            } else if (this.aoO != null) {
                this.aoO.dV(GLLib.Qy);
                return;
            } else {
                this.aoO = new aa(ci.aZf[this.aoG], this.aot, this.aou);
                this.aoO.Y(1, -1);
                return;
            }
        }
        if (this.aoC == 17) {
            if (this.alZ >= 0) {
                this.alZ -= GLLib.Qy;
                if (this.alZ < 0) {
                    this.aoO = new aa(ci.aZf[this.aoG], this.aot, this.aou);
                    this.aoO.Y(0, 1);
                    if (qs()) {
                        ci.gX(29);
                    }
                }
            } else if (this.aoO != null) {
                this.aoO.dV(GLLib.Qy);
                if (this.aoO.lQ()) {
                    this.aoO = null;
                    this.alZ = GLLib.L(10000, 30000);
                }
            }
            if (ci.aZB == 0) {
                long jCurrentTimeMillis5 = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis5 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                }
                ci.aUl = jCurrentTimeMillis5;
                if (fz(AdRequest.MAX_CONTENT_URL_LENGTH) && ((this.aoM == null || this.aoM.Kx == -1) && ci.aUl > ci.aUm)) {
                    e(b(33, 1, new int[]{this.aol, this.aom, 150, 10, 13, -63, -117, 0, 1}));
                } else {
                    if (!fz(AdRequest.MAX_CONTENT_URL_LENGTH) || this.aoM == null || this.aoM.Kx == -1 || ci.aUl > ci.aUm) {
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
            GLLib.l(18, 5, 1);
            if ((((this.aol + this.aow) - ci.aZj.aol) * ci.aYP) / 100.0f > 0.0f) {
                i2 = (int) (((((this.aol + this.aow) - ci.aZj.aol) * ci.aYP) / 100.0f) + 0.5f);
            } else {
                i2 = ((float) (((this.aol + this.aow) - ci.aZj.aol) * ci.aYP)) / 100.0f < 0.0f ? (int) (((((this.aol + this.aow) - ci.aZj.aol) * ci.aYP) / 100.0f) - 0.5f) : 0;
            }
            if ((((this.aom + this.aox) - ci.aZj.aom) * ci.aYP) / 100.0f > 0.0f) {
                i3 = (int) (((((this.aom + this.aox) - ci.aZj.aom) * ci.aYP) / 100.0f) + 0.5f);
            } else {
                i3 = ((float) (((this.aom + this.aox) - ci.aZj.aom) * ci.aYP)) / 100.0f < 0.0f ? (int) (((((this.aom + this.aox) - ci.aZj.aom) * ci.aYP) / 100.0f) - 0.5f) : 0;
            }
            ci.a((i > 0 ? "+" : "") + ci.a(i, ci.aYU, " "), i2 + 10, i3 + 10, 0, 0, 0, 0, 2, 1, false, false);
            GLLib.l(18, 5, 0);
        }
    }

    void aE(int i, int i2) {
        if (oT()) {
            return;
        }
        int i3 = i & 32767;
        a aVar = (32768 & i) != 0 ? ci.atf[i3] : ci.aZf[i3];
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
            if (ci.aYP == 100 || this.aoo == 2) {
                aVar.a(GLLib.Qj, i2, (float) this.aot, (float) this.aou, 0);
                return;
            }
            GLLib.l(18, 5, 1);
            aVar.b(GLLib.Qj, i2, this.aot, this.aou, 0, ci.aYP);
            GLLib.l(18, 5, 0);
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
                int i7 = ci.aYP;
                if (w.Zo) {
                    aaVar2.aaW = i7;
                } else {
                    GLLib.be("SetScale: Scaling is not enabled, you must set pfx_useSpriteEffectFreeRotateScale to TRUE!");
                }
                GLLib.l(18, 5, 1);
                this.aoO.lR();
                GLLib.l(18, 5, 0);
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
        if (!this.aoT || ci.avE != this) {
            return;
        }
        if (ci.t(this) && aF(14, this.aoA)) {
            int i8 = this.aot;
            int i9 = this.aou;
            if (ci.j(this)) {
                i9 += 108;
            }
            r(14, this.aoA, i8, i9);
        }
        if (!ci.j(ci.avE)) {
            return;
        }
        if (ci.avE.aoC == 20) {
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
        int[] iArr = {ci.avE.aol, ci.avE.aom};
        int[] iArr2 = {i + 1, i};
        ci.f(iArr, iArr2);
        int i10 = iArr2[0];
        int i11 = iArr2[1];
        int i12 = 0;
        while (true) {
            int i13 = i12;
            if (i13 >= 6) {
                iArr2[0] = i10;
                iArr2[1] = i11 + 1;
                for (int i14 = 0; i14 < 6; i14++) {
                    ci.g(iArr2, iArr);
                    r(i2, 0, iArr[0] - ci.aZj.aol, iArr[1] - ci.aZj.aom);
                    iArr2[1] = iArr2[1] + 2;
                }
                iArr2[0] = i10 + 1;
                iArr2[1] = i11 + 12;
                for (int i15 = 0; i15 < 6; i15++) {
                    ci.g(iArr2, iArr);
                    r(i, 0, iArr[0] - ci.aZj.aol, iArr[1] - ci.aZj.aom);
                    iArr2[0] = iArr2[0] + 2;
                }
                iArr2[0] = i10 + 12;
                iArr2[1] = i11 + 1;
                for (int i16 = 0; i16 < 6; i16++) {
                    ci.g(iArr2, iArr);
                    r(i2, 0, iArr[0] - ci.aZj.aol, iArr[1] - ci.aZj.aom);
                    iArr2[1] = iArr2[1] + 2;
                }
                if (z) {
                    iArr2[0] = i10 + 3;
                    iArr2[1] = i11;
                    for (int i17 = 0; i17 < 6; i17 += 2) {
                        ci.g(iArr2, iArr);
                        r(168, 2, iArr[0] - ci.aZj.aol, iArr[1] - ci.aZj.aom);
                        iArr2[0] = iArr2[0] + 4;
                    }
                    iArr2[0] = i10;
                    iArr2[1] = i11 + 3;
                    for (int i18 = 0; i18 < 6; i18 += 2) {
                        ci.g(iArr2, iArr);
                        r(168, 3, iArr[0] - ci.aZj.aol, iArr[1] - ci.aZj.aom);
                        iArr2[1] = iArr2[1] + 4;
                    }
                    iArr2[0] = i10 + 3;
                    iArr2[1] = i11 + 12;
                    for (int i19 = 0; i19 < 6; i19 += 2) {
                        ci.g(iArr2, iArr);
                        r(168, 2, iArr[0] - ci.aZj.aol, iArr[1] - ci.aZj.aom);
                        iArr2[0] = iArr2[0] + 4;
                    }
                    iArr2[0] = i10 + 12;
                    iArr2[1] = i11 + 3;
                    for (int i20 = 0; i20 < 6; i20 += 2) {
                        ci.g(iArr2, iArr);
                        r(168, 3, iArr[0] - ci.aZj.aol, iArr[1] - ci.aZj.aom);
                        iArr2[1] = iArr2[1] + 4;
                    }
                    int[] iArr3 = {0, 0, 4, 0, 8, 0, 12, 0, 12, 4, 0, 4, 0, 8, 12, 8, 12, 12, 8, 12, 4, 12, 0, 12};
                    for (int i21 = 0; i21 < 12; i21++) {
                        iArr2[0] = iArr3[i21 * 2] + i10;
                        iArr2[1] = iArr3[(i21 * 2) + 1] + i11;
                        ci.g(iArr2, iArr);
                        r(168, (i21 % 2) + 0, iArr[0] - ci.aZj.aol, (iArr[1] - ci.aZj.aom) + 13);
                    }
                    return;
                }
                return;
            }
            ci.g(iArr2, iArr);
            if (i13 == 0) {
                iArr[1] = iArr[1] - 3;
                i4 = i3;
            } else {
                i4 = i;
            }
            r(i4, 0, iArr[0] - ci.aZj.aol, iArr[1] - ci.aZj.aom);
            iArr2[0] = iArr2[0] + 2;
            i12 = i13 + 1;
        }
    }

    void r(int i, int i2, int i3, int i4) {
        if (ci.aYP != 100) {
            GLLib.l(18, 5, 1);
            ci.aZf[i].b(GLLib.Qj, i2, i3, i4, 0, ci.aYP);
            GLLib.l(18, 5, 0);
            return;
        }
        ci.aZf[i].a(GLLib.Qj, i2, (float) i3, (float) i4, 0);
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
        if (this.Bj != 3 || ci.avP != 1) {
            if (this.Bj != 5 || GLLib.dy(0) == 3) {
                return false;
            }
            ci.a(4, this);
            return true;
        }
        if (ci.aAY) {
            return false;
        }
        String str = "m_countToStore : " + ci.aAX;
        int i = this.aoC != 23 ? 3 : 4;
        if (ci.aZB == 0) {
            if (fe(3) + ci.hF(5) + i <= ci.Fw()) {
                if (ci.aZB == 0 && i + ci.hF(5) + ci.aAX > ci.Fw()) {
                    if (ci.aYS != 1069) {
                        ci.aAY = true;
                        ci.c(false, 1, ci.avR);
                        ci.awH = false;
                        ci.avP = -1;
                        if (ci.hF(5) < ci.Fw()) {
                            strDt = ci.dt(486);
                            if (strDt == null) {
                                strDt = "";
                            }
                            String strL = ci.l(strDt, "%slots", "3");
                            strDt2 = ci.dt(485);
                            if (strDt2 == null) {
                                strDt2 = "";
                            }
                            ci.g(strDt2, strL, 7, 0);
                        } else {
                            ci.H(481, 487, 7, 0);
                        }
                        return false;
                    }
                }
            } else if (ci.aYS != 1069) {
                ci.aAY = true;
                ci.c(false, 1, ci.avR);
                ci.awH = false;
                ci.avP = -1;
                if (ci.hF(5) < ci.Fw()) {
                    strDt = ci.dt(486);
                    if (strDt == null) {
                        strDt = "";
                    }
                    String strL2 = ci.l(strDt, "%slots", "3");
                    strDt2 = ci.dt(485);
                    if (strDt2 == null) {
                        strDt2 = "";
                    }
                    ci.g(strDt2, strL2, 7, 0);
                } else {
                    ci.H(481, 487, 7, 0);
                }
                return false;
            }
        } else if (ci.aZB == 0) {
            if (ci.aYS != 1069) {
                ci.aAY = true;
                ci.c(false, 1, ci.avR);
                ci.awH = false;
                ci.avP = -1;
                if (ci.hF(5) < ci.Fw()) {
                    strDt = ci.dt(486);
                    if (strDt == null) {
                        strDt = "";
                    }
                    String strL3 = ci.l(strDt, "%slots", "3");
                    strDt2 = ci.dt(485);
                    if (strDt2 == null) {
                        strDt2 = "";
                    }
                    ci.g(strDt2, strL3, 7, 0);
                } else {
                    ci.H(481, 487, 7, 0);
                }
                return false;
            }
        }
        ci.a(1, this);
        return true;
    }

    void pb() {
        int i = 50;
        if (this.Bj == 0) {
            if (GLLib.dB(0)) {
                this.alv = this.aol;
                this.alw = this.aom;
                this.alx = GLLib.dG(0);
                this.aly = GLLib.dH(0);
                this.alA = true;
            } else if (this.alB || !GLLib.dC(0) || !this.alA || ci.awH) {
                if (GLLib.dA(0)) {
                    if (this.alA && ci.Fb() > 50) {
                        pe();
                    }
                    this.alA = false;
                }
            } else {
                this.aol = this.alv + (((this.alx - GLLib.dG(0)) * 100) / ci.aYP);
                this.aom = this.alw + (((this.aly - GLLib.dH(0)) * 100) / ci.aYP);
                if (ci.avP == 0 && !ci.awH) {
                    ci.avP = -1;
                    ci.c(false, 0, ci.avR);
                }
            }
            if (!GLLib.dF(0) || !GLLib.dF(1)) {
                if (this.alB) {
                    this.alB = false;
                    this.alA = false;
                    return;
                }
                return;
            }
            int iKH = ci.kH() >> 1;
            int iKI = ci.kI() >> 1;
            if (!this.alB) {
                this.alB = true;
                this.alz = ci.aYP;
                this.alC = Math.abs(GLLib.dG(0) - GLLib.dG(1));
                this.alD = Math.abs(GLLib.dH(0) - GLLib.dH(1));
                this.alv = this.aol + ((iKH * 100) / ci.aYP);
                this.alw = this.aom + ((iKI * 100) / ci.aYP);
            }
            int iAbs = Math.abs(GLLib.dG(0) - GLLib.dG(1));
            int iAbs2 = Math.abs(GLLib.dH(0) - GLLib.dH(1));
            int i2 = ci.aYP;
            if (this.alz + ((int) (((double) (iAbs - this.alC)) * 0.1d)) + ((int) (((double) (iAbs2 - this.alD)) * 0.1d)) >= 50) {
                i = (this.alz + ((int) (((double) (iAbs - this.alC)) * 0.1d))) + ((int) (((double) (iAbs2 - this.alD)) * 0.1d)) > 100 ? 100 : this.alz + ((int) (((double) (iAbs - this.alC)) * 0.1d)) + ((int) (((double) (iAbs2 - this.alD)) * 0.1d));
            }
            ci.aYP = i;
            if (ci.getDeviceName().equals("LG-P700") && ci.aYP <= 65) {
                ci.aYP = 65;
            }
            if (i2 != ci.aYP) {
                this.aol = this.alv - ((iKH * 100) / ci.aYP);
                this.aom = this.alw - ((iKI * 100) / ci.aYP);
            }
        }
    }

    void pc() {
        if (this.Bj == 0 && GLLib.dC(0)) {
            if (GLLib.dG(0) < 40) {
                this.aol -= GLLib.Qy * 1;
            } else if (GLLib.dG(0) > GLLib.kH() - 40) {
                this.aol += GLLib.Qy * 1;
            }
            if (GLLib.dH(0) < 40) {
                this.aom -= GLLib.Qy * 1;
            } else if (GLLib.dH(0) > GLLib.kI() - 40) {
                this.aom += GLLib.Qy * 1;
            }
        }
    }

    boolean oZ() {
        boolean z;
        if (this.Bj == 3 || ci.aCg != 0 || ci.Fb() >= 50) {
            return false;
        }
        int[] iArr = new int[2];
        int[] iArr2 = {GLLib.dG(0), GLLib.dH(0)};
        ci.c(iArr2, iArr);
        ci.f(iArr, iArr2);
        if (!GLLib.dA(0) || ci.avD != -1) {
            if (ci.aZB != 0 || ci.awX != 0 || !GLLib.dF(0) || ci.hB(ci.bE(iArr2[0], iArr2[1])) || ci.bE(iArr2[0], iArr2[1]) <= 0 || ci.avD != -1) {
                return false;
            }
            int iBE = ci.bE(iArr2[0], iArr2[1]);
            if (ci.bE(iArr2[0], iArr2[1]) <= 0 || ci.aYR != -1 || ci.ayM != null) {
                return false;
            }
            String str = "pressed expandable area... quest 1:" + ci.lM(ci.aZQ[0]) + ",q2:" + ci.lM(ci.aZQ[1]);
            ci.ayJ = iBE;
            ci.aze = true;
            return false;
        }
        if (ci.bD(iArr2[0], iArr2[1])) {
            aI(GLLib.dG(0), GLLib.dH(0));
            return true;
        }
        if (ci.aZB != 0 || ci.awX != 0 || ci.bE(iArr2[0], iArr2[1]) <= 0 || ci.hh(ci.aYS)) {
            return false;
        }
        int iBE2 = ci.bE(iArr2[0], iArr2[1]);
        String str2 = "clickGridPos[0] : " + iArr2[0] + " clickGridPos[1] : " + iArr2[1];
        String str3 = "Grid_GetZoneNumber : " + iBE2;
        if (iBE2 < 1 || ci.ayK[iBE2]) {
            return false;
        }
        ch[] chVarArr = new ch[10];
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
        if (ci.aYR != -1 || ci.ayM != null || z) {
            return false;
        }
        int iLz = ci.Lz();
        if (iLz == -1 || ci.aTH) {
            ci.ayJ = iBE2;
            ci.fE(13);
            return false;
        }
        ci.aVF = iLz;
        ci.fE(27);
        ci.awC = false;
        ci.awD = (char) 0;
        ci.e(1, 54, false);
        ci.f(1, 54, false);
        return false;
    }

    boolean oY() {
        int[] iArr = new int[2];
        ci.c(new int[]{GLLib.dG(0), GLLib.dH(0)}, iArr);
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
        ci.f(iArr2, iArr);
        iArr[0] = iArr[0] - (this.aoy >> 1);
        iArr[1] = iArr[1] - (this.aoz >> 1);
        int[] iArr3 = {GLLib.dG(0), GLLib.dH(0)};
        ci.c(iArr3, iArr2);
        ci.f(iArr2, iArr3);
        return iArr3[0] >= iArr[0] && iArr3[0] < iArr[0] + this.aoy && iArr3[1] >= iArr[1] && iArr3[1] < iArr[1] + this.aoz;
    }

    boolean qj() {
        if (this.aoG != -1 && this.aoH != -1 && (this.aoG & (-65536)) == 0) {
            if (((this.aoG & 32768) != 0 ? ci.atf[this.aoG & 32767].bG(this.aoH) : ci.aZf[this.aoG].bG(this.aoH)) > 0) {
                int[] iArr = new int[4];
                int[] iArr2 = new int[2];
                ci.c(new int[]{GLLib.dG(0), GLLib.dH(0)}, iArr2);
                if ((this.aoG & 32768) != 0) {
                    ci.atf[this.aoG & 32767].a(this.aoH, 0, iArr, 0);
                } else {
                    ci.aZf[this.aoG].a(this.aoH, 0, iArr, 0);
                }
                int i = this.aol + iArr[0];
                int i2 = this.aom + iArr[1];
                return iArr2[0] + 20 >= i && iArr2[0] + (-20) <= iArr[2] + i && iArr2[1] + 20 >= i2 && iArr2[1] + (-20) <= iArr[3] + i2;
            }
        }
        return false;
    }
}
