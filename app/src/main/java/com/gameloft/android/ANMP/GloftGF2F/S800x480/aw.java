package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.gameloft.glads.AndroidWebView;
import java.io.InputStream;
import java.util.Hashtable;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: IGP.java */
/* JADX INFO: loaded from: classes.dex */
public class aw implements Runnable, javax.microedition.lcdui.p {
    public static String adZ;
    private static int aeA;
    private static int aeB;
    private static int aeC;
    private static int aeD;
    private static int aeE;
    private static int aeF;
    private static int aeG;
    private static int aeH;
    private static int aeI;
    private static int aeJ;
    private static int aeK;
    private static int aeL;
    private static javax.microedition.lcdui.u aea;
    private static int aei;
    private static int ael;
    private static int aem;
    private static int aen;
    private static int aeo;
    private static int aep;
    private static int aeq;
    private static int aer;
    private static int aes;
    private static int aet;
    private static int aeu;
    private static int aev;
    private static int aew;
    private static int aex;
    private static int aey;
    private static int aez;
    private static int afH;
    private static int[] afI;
    private static byte[][] afJ;
    private static int afK;
    private static byte[] afL;
    private static int afM;
    private static ay[] afR;
    private static int afS;
    private static int afT;
    private static int[] afU;
    private static int afV;
    private static int afW;
    private static short[] agA;
    private static String[] agB;
    private static String[] agC;
    private static String[] agD;
    private static int agE;
    private static boolean agF;
    private static boolean agG;
    private static int[][] agM;
    public static MIDlet agP;
    private static javax.microedition.lcdui.f agQ;
    private static String agR;
    private static int agZ;
    private static String agp;
    private static boolean agq;
    private static boolean agr;
    private static String ags;
    public static String agv;
    private static int agy;
    private static String[] agz;
    private static int ahA;
    private static String ahC;
    private static String ahD;
    private static String ahE;
    private static String ahG;
    private static String ahH;
    private static int ahJ;
    private static int ahK;
    private static int ahL;
    private static int ahM;
    private static javax.microedition.lcdui.o ahN;
    private static javax.microedition.lcdui.o ahO;
    private static int aha;
    private static int ahb;
    private static int ahc;
    private static int ahd;
    private static int ahe;
    private static a[] ahf;
    private static a ahg;
    private static a ahh;
    private static ay ahi;
    private static ay ahj;
    private static String[] ahm;
    private static int[] ahn;
    private static boolean[] aho;
    private static int ahr;
    private static boolean ahs;
    private static byte aht;
    private static byte ahu;
    private static int ahv;
    private static int ahw;
    private static int ahx;
    private static int ahy;
    private static int ahz;
    public static String adW = "2.4";
    public static String adX = new StringBuffer().append("IGP-Signature=").append(adW).toString();
    private static String DATA_VERSION = "";
    private static int adY = 0;
    private static String aeb = "URL";
    private static String aec = "IGP";
    private static int aed = 2;
    private static int aee = 2;
    private static int aef = 0;
    private static boolean[] aeg = new boolean[1];
    private static int[] aeh = new int[1];
    public static int aej = -1;
    public static int aek = 8;
    private static String aeM = "ZVIP";
    private static String aeN = "SCFR";
    private static String aeO = "GMCL";
    private static String aeP = "CCTL";
    private static String aeQ = "TITLE-FREEMIUM";
    private static String aeR = "TITLE-GLCLUB";
    private static String aeS = "URL-OPERATOR";
    private static int aeT = 0;
    private static int aeU = 1;
    private static int aeV = 4;
    private static int aeW = 8;
    private static int aeX = 9;
    private static int aeY = 10;
    private static int aeZ = 11;
    private static int afa = 14;
    private static int afb = 15;
    private static int afc = 12;
    private static int afd = 13;
    private static int afe = 16;
    private static int aff = 17;
    private static int afg = 18;
    private static int afh = 0;
    private static int afi = 1;
    private static int afj = 2;
    private static int afk = 3;
    private static int afl = 4;
    private static int afm = 5;
    private static int afn = 6;
    private static int afo = 7;
    private static int afp = 8;
    private static int afq = 9;
    private static int afr = 10;
    private static int afs = 11;
    private static int aft = 12;
    private static int afu = 13;
    private static int afv = 14;
    private static int afw = 15;
    private static int afx = 16;
    private static int afy = 17;
    private static int afz = 18;
    private static int afA = 19;
    private static int afB = 20;
    private static int afC = 21;
    private static int afD = 22;
    private static int afE = 23;
    private static int afF = 24;
    private static int afG = 25;
    private static int afN = 0;
    private static int afO = 1;
    private static int afP = 2;
    private static int afQ = 3;
    private static String afX = "IGP-CATEGORIES";
    private static String afY = "IGP-PROMOS";
    private static String afZ = "IGP-FREEMIUM";
    private static String aga = "URL-TEMPLATE-GAME";
    private static String agb = "more_games_url";
    private static String agc = "more_games_status";
    private static String agd = null;
    private static String age = null;
    private static String agf = "on";
    private static String agg = "XXXX";
    private static String agh = new StringBuffer().append("&ctg=").append(agg).toString();
    private static String agi = "&ctg=";
    private static String agj = "&lg=";
    private static String agk = "SC";
    private static String agl = "FR";
    private static String agm = "CCTL";
    private static String agn = "201205igp.gameloft.com/redir";
    private static String ago = ";";
    private static boolean agt = false;
    private static boolean agu = false;
    private static boolean agw = true;
    public static String[] agx = new String[0];
    private static boolean agH = false;
    private static boolean agI = false;
    private static boolean agJ = true;
    private static long agK = 0;
    private static boolean agL = true;
    private static boolean agN = false;
    private static boolean agO = false;
    private static boolean agS = false;
    private static javax.microedition.lcdui.p agT = null;
    public static aw agU = null;
    private static boolean agV = false;
    private static String agW = null;
    private static boolean agX = false;
    private static boolean agY = false;
    private static int ahk = -1;
    private static int[] ahl = new int[2];
    private static int ahp = 0;
    private static int ahq = 0;
    private static boolean ahB = true;
    private static String ahF = "";
    private static boolean ahI = false;
    private static int ahP = 0;
    private static int ahQ = 0;
    private static boolean ahR = false;
    private static boolean ahS = false;
    private static boolean ahT = false;
    private static boolean ahU = false;
    private static boolean ahV = false;
    private static int ahW = -1;
    private static int ahX = -1;
    private static int ahY = -1;
    private static int ahZ = -1;
    private static int aia = -1;
    private static int aib = -1;
    static int aic = -1;
    static int aid = -1;
    private static Hashtable aie = new Hashtable();
    private static boolean aif = false;
    private static int aig = 0;
    private static boolean aih = false;
    private static int aii = -1;
    private static int aij = -1;
    private static int aik = 0;
    private static int ail = 0;

    public static final InputStream a(Class cls, String str) {
        return com.gameloft.android.wrapper.au.getResourceAsStream(str);
    }

    private static boolean mP() {
        mQ();
        try {
            InputStream inputStreamBZ = bZ("/dataIGP");
            afH = inputStreamBZ.read() & 255;
            afH += (inputStreamBZ.read() & 255) << 8;
            afI = new int[afH];
            for (int i = 0; i < afH; i++) {
                afI[i] = inputStreamBZ.read() & 255;
                int[] iArr = afI;
                iArr[i] = iArr[i] + ((inputStreamBZ.read() & 255) << 8);
                int[] iArr2 = afI;
                iArr2[i] = iArr2[i] + ((inputStreamBZ.read() & 255) << 16);
                int[] iArr3 = afI;
                iArr3[i] = iArr3[i] + ((inputStreamBZ.read() & 255) << 24);
            }
            inputStreamBZ.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void mQ() {
        afI = null;
        afJ = (byte[][]) null;
        afH = 0;
        if (0 == 0) {
        }
    }

    private static byte[] eC(int i) {
        int i2;
        byte[] bArr = null;
        if (i < 0 || i >= afH - 1 || (i2 = afI[i + 1] - afI[i]) == 0) {
            return null;
        }
        if (afJ != null) {
            return afJ[i];
        }
        try {
            InputStream inputStreamBZ = bZ("/dataIGP");
            inputStreamBZ.skip((afH * 4) + 2 + afI[i]);
            bArr = new byte[i2];
            for (int length = bArr.length; length > 0; length -= inputStreamBZ.read(bArr)) {
            }
            inputStreamBZ.close();
            return bArr;
        } catch (Exception e) {
            return bArr;
        }
    }

    private static int f(byte[] bArr) {
        int i = afK;
        afK = i + 1;
        int i2 = bArr[i] & 255;
        int i3 = afK;
        afK = i3 + 1;
        return i2 + ((bArr[i3] & 255) << 8);
    }

    private static String getString(int i) {
        String string = new StringBuffer().append("").append(agz[i]).toString();
        if (agx[ahA].equals("AR")) {
            return bW(string);
        }
        return string;
    }

    public static String bW(String str) {
        char[] cArr = new char[str.length()];
        str.getChars(0, str.length(), cArr, 0);
        GLLib.a(cArr, cArr.length);
        return String.valueOf(cArr);
    }

    private static void a(int i, aj ajVar, int i2, int i3, int i4) {
        a(getString(i), ajVar, ahJ, i2, i3, i4, false);
    }

    private static void a(int i, aj ajVar, int i2, int i3, int i4, boolean z) {
        a(getString(i), ajVar, ahJ, i2, i3, i4, z);
    }

    private static void a(String str, aj ajVar, int i, int i2, int i3, int i4) {
        a(str, ajVar, i, i2, i3, i4, 10, false);
    }

    private static void a(String str, aj ajVar, int i, int i2, int i3, int i4, boolean z) {
        a(str, ajVar, i, i2, i3, i4, 10, z);
    }

    private static void a(String str, aj ajVar, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (z) {
            if (afT == 0) {
                ahg.bQ(afS);
                ahg.a(ajVar, str, ahg.a(str, i, 0, a.Iy), i2, i3, 0, i5, i4, -1);
                return;
            } else {
                ahh.bQ(afS);
                ahh.a(ajVar, str, ahh.a(str, i, 0, a.Iy), i2, i3, 0, i5, i4, -1);
                return;
            }
        }
        if (afT == 0) {
            ahg.bQ(afS);
            ahg.a(ajVar, str, i2, i3, i4);
        } else {
            ahh.bQ(afS);
            ahh.a(ajVar, str, i2, i3, i4);
        }
    }

    public static void a(MIDlet mIDlet, javax.microedition.lcdui.f fVar, int i, int i2) {
        a(mIDlet, fVar, i, i2, (javax.microedition.lcdui.p) null);
    }

    public static void a(MIDlet mIDlet, javax.microedition.lcdui.f fVar, int i, int i2, javax.microedition.lcdui.p pVar) {
        log(new StringBuffer().append("initialize(midlet = ").append(mIDlet).append(", game = ").append(fVar).append(", screenWidth = ").append(i).append(", screenHeight = ").append(i2).append(", cmdListener = ").append(pVar).append(")").toString());
        ahJ = i;
        ahK = i2;
        ahL = ahJ >> 1;
        ahM = ahK >> 1;
        ahB = ahK >= 160;
        if (2 > ahJ) {
            aed = 2;
        }
        if (2 > ahJ) {
            aee = 2;
        }
        if (agP != null) {
            log("MIDlet instance was already initialized");
            return;
        }
        if (mIDlet == null) {
            log("MIDlet instance can't be null");
        }
        if (fVar == null) {
            log("Canvas instance can't be null");
            return;
        }
        if (pVar != null) {
            agS = true;
            if (agU == null) {
                agU = new aw();
            }
            agT = pVar;
        }
        agP = mIDlet;
        agQ = fVar;
        mR();
        new StringBuffer().append(adX).append("").toString();
        agY = mS();
        if (agY && ahI) {
            agY = false;
        }
        agn = null;
        agp = null;
        aga = null;
        afX = null;
        aeQ = null;
        aeR = null;
        aeS = null;
        aeb = null;
        aec = null;
        agg = null;
        agh = null;
        agk = null;
        agi = null;
        agm = null;
        ago = null;
        afY = null;
        agv = null;
    }

    private static boolean q(String str, int i) {
        boolean z = false;
        if (str == null) {
            return (i & 1) == 0;
        }
        String strTrim = str.trim();
        if (((i & 1) == 0 || strTrim.length() != 0) && (((i & 2) == 0 || strTrim.toUpperCase().compareTo("DEL") != 0) && ((i & 4) == 0 || (strTrim.toUpperCase().compareTo("NO") != 0 && strTrim.toUpperCase().compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) != 0)))) {
            z = true;
        }
        return z;
    }

    private static String n(String str, String str2, String str3) {
        String strTrim = "";
        if (str3 != null && str != null && str2 != null) {
            try {
                int iIndexOf = str.indexOf(new StringBuffer().append(str2).append("=").toString());
                String strTrim2 = str3.trim();
                if (iIndexOf >= 0 && strTrim2.length() > 0) {
                    int length = iIndexOf + str2.length() + 1;
                    int iIndexOf2 = str.indexOf(ago, length);
                    if (iIndexOf2 < 0) {
                        iIndexOf2 = str.length();
                    }
                    strTrim = str.substring(length, iIndexOf2).trim();
                    if (strTrim.length() == 0 || strTrim.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) == 0 || strTrim.toUpperCase().compareTo("NO") == 0) {
                        return "";
                    }
                    if (strTrim.toUpperCase().compareTo("DEL") != 0 && str2.compareTo("OP") != 0) {
                        int iIndexOf3 = strTrim2.indexOf(agg);
                        if (iIndexOf3 >= 0) {
                            return new StringBuffer().append(strTrim2.substring(0, iIndexOf3)).append(strTrim).append(strTrim2.substring(iIndexOf3 + agg.length())).toString();
                        }
                        return strTrim2;
                    }
                }
            } catch (Exception e) {
                return "";
            }
        }
        return strTrim;
    }

    private static void a(int i, String str, int i2, String str2, String str3, int i3) {
        String strBY;
        String strBY2;
        try {
            String strBY3 = "";
            if (agr) {
                strBY = n(bY(str2), str, str3);
                if (str.equals(aeN)) {
                    strBY3 = n(bY(afZ), agB[i], str3);
                }
            } else {
                strBY = str.equals(aeP) ? ahF : bY(new StringBuffer().append(aeb).append("-").append(str).toString());
            }
            if (!str.equals(aeN)) {
                strBY2 = strBY;
            } else if (agr) {
                int iIndexOf = strBY.indexOf(aeN);
                if (iIndexOf >= 0) {
                    strBY = new StringBuffer().append(strBY.substring(0, iIndexOf)).append(agB[i]).append(strBY.substring(iIndexOf + agB[i].length())).toString();
                }
                strBY2 = strBY;
            } else {
                strBY2 = bY(new StringBuffer().append(aeb).append("-").append(aeN).append("-").append(agB[i]).toString());
                strBY3 = bY(new StringBuffer().append(aeb).append("-").append(aeN).append("-").append(agB[i]).toString());
            }
            boolean zQ = q(strBY2, i2);
            boolean zQ2 = str.equals(aeN) ? q(strBY3, i2) : true;
            if (zQ && zQ2) {
                if (strBY2.toUpperCase().compareTo("NO") != 0 || strBY2.toUpperCase().compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) != 0) {
                    aho[i] = true;
                    ahm[i] = strBY2;
                    if (aho[i]) {
                        ahn[i] = i3;
                        switch (i3) {
                            case 4:
                                ahp++;
                                if (agq) {
                                    StringBuffer stringBuffer = new StringBuffer();
                                    String[] strArr = ahm;
                                    strArr[i] = stringBuffer.append(strArr[i]).append(agi).append(agk).append(ahp < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO : "").append(ahp).toString();
                                }
                                break;
                            case 6:
                                ahq++;
                                if (agq) {
                                    StringBuffer stringBuffer2 = new StringBuffer();
                                    String[] strArr2 = ahm;
                                    strArr2[i] = stringBuffer2.append(strArr2[i]).append(agi).append(aeN).append(ahq < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO : "").append(ahq).toString();
                                }
                                break;
                            case 7:
                                if (agq) {
                                    StringBuffer stringBuffer3 = new StringBuffer();
                                    String[] strArr3 = ahm;
                                    strArr3[i] = stringBuffer3.append(strArr3[i]).append(agi).append(aeO).toString();
                                }
                                break;
                            case 8:
                                if (agq) {
                                    StringBuffer stringBuffer4 = new StringBuffer();
                                    String[] strArr4 = ahm;
                                    strArr4[i] = stringBuffer4.append(strArr4[i]).append(agi).append(aeP).toString();
                                }
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private static String[] g(byte[] bArr) {
        String[] strArr = new String[f(bArr)];
        for (int i = 0; i < strArr.length; i++) {
            int iF = f(bArr);
            strArr[i] = new String(bArr, afK, iF);
            afK = iF + afK;
        }
        return strArr;
    }

    private static void a(String[] strArr, String[] strArr2) {
        int length = strArr2.length + (-1) > 0 ? strArr2.length - 1 : 0;
        aeA = length + 1;
        aez = length + (strArr.length + (-1) > 0 ? strArr.length - 1 : 0) + 1 + 1;
        aeo = aez + 1;
        aep = aeo + 1;
        aeq = aep + 1;
        aer = aeq + 1;
        aes = aer + 1;
        aet = aes + 1;
        aeu = aet + 1;
        aev = aeu + 1;
        aew = aev + 1;
        aex = aew + 1;
        aeB = aex + 1;
        aeC = aeB + 1;
        aeD = aeC + 1;
        aeE = aeD + 1;
        aeF = aeE + 1;
        aeG = aeF + 1;
        aeH = aeG + 1;
        aeI = aeH + 1;
        aeJ = aeI + 1;
        aeK = aeJ + 1;
        aey = aeK + 1;
        aeL = aey + 1;
    }

    private static void mR() {
        try {
            if (!mP()) {
                log("dataIGP file can't be loaded or found.");
                agX = false;
                return;
            }
            byte[] bArrEC = eC(0);
            f(bArrEC);
            int iF = f(bArrEC);
            agv = new String(bArrEC, afK, iF);
            afK = iF + afK;
            int iF2 = f(bArrEC);
            ags = new String(bArrEC, afK, iF2);
            afK = iF2 + afK;
            afK = f(bArrEC) + afK;
            afK = f(bArrEC) + afK;
            afK = f(bArrEC) + afK;
            agt = f(bArrEC) == 1;
            agu = f(bArrEC) == 1;
            agw = f(bArrEC) == 1;
            agx = g(bArrEC);
            agB = g(bArrEC);
            agC = g(bArrEC);
            agD = g(bArrEC);
            for (int i = 0; i < aeg.length; i++) {
                aeg[i] = f(bArrEC) == 1;
            }
            try {
                DATA_VERSION = new String(bArrEC, afK, f(bArrEC));
                log(new StringBuffer().append("IGP dataIGP version: ").append(DATA_VERSION).toString());
                if (DATA_VERSION.equals(new StringBuffer().append(adW).append("z").toString())) {
                    aig = 2;
                    afO = 2;
                    afP = 4;
                    afQ = 5;
                }
                if (!DATA_VERSION.startsWith(adW)) {
                    log(new StringBuffer().append("Invalid dataIGP file, dataIGP file IGP Version : ").append(DATA_VERSION).toString());
                    log(new StringBuffer().append("IGP Class version : ").append(adW).toString());
                }
            } catch (Exception e) {
                agX = false;
            }
            mQ();
            a(agC, agB);
            agy = agB.length + agC.length + 1 + 1;
            for (int i2 = 0; i2 < aeg.length; i2++) {
                if (aeg[i2]) {
                    int[] iArr = aeh;
                    int i3 = aek + 1;
                    aek = i3;
                    iArr[i2] = i3;
                } else {
                    int[] iArr2 = aeh;
                    int i4 = aej - 1;
                    aej = i4;
                    iArr2[i2] = i4;
                }
            }
            int i5 = aek + 1;
            aek = i5;
            aei = i5;
            ahm = new String[agy];
            aho = new boolean[agy];
            ahn = new int[agy];
            for (int i6 = 0; i6 < aho.length; i6++) {
                aho[i6] = false;
            }
            ael = agB.length;
            aem = ael + agC.length;
            aen = aem + 1;
            agd = bY(agb);
            age = bY(agc);
            if (agd != null && age.toLowerCase().equals(agf.toLowerCase())) {
                ahH = agd;
                ahI = true;
                agX = true;
                return;
            }
            try {
                agp = bY(aga);
                if (agp != null) {
                    agp = agp.trim();
                    agr = true;
                    if (agp.indexOf(agn) != -1) {
                        agq = true;
                    }
                }
            } catch (Exception e2) {
            }
            for (int i7 = 0; i7 < agB.length; i7++) {
                a(i7, aeN, 7, afX, agp, 6);
            }
            for (int i8 = 0; i8 < agC.length; i8++) {
                a(ael + i8, agC[i8], 7, afY, agp, 4);
            }
            try {
                String strTrim = bY(aeS).trim();
                if (q(strTrim, 7)) {
                    ahF = strTrim;
                }
            } catch (Exception e3) {
            }
            a(aem, aeO, 7, afX, agp, 7);
            a(aen, aeP, 7, afX, agp, 8);
            ahC = bX(bY(aeQ));
            ahD = bX(bY(aeR));
            ahl = mU();
            ahk = mV();
            if (ahk > 0) {
                agX = true;
            } else {
                log("There are no valid pages");
            }
            log(new StringBuffer().append("isAvailable = ").append(agX).toString());
        } catch (Exception e4) {
            agX = false;
        }
    }

    public static String bX(String str) {
        int i = 0;
        if (str == null) {
            return AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
        String string = "";
        String upperCase = str.toUpperCase();
        int i2 = ahJ;
        boolean z = i2 >= ahK && i2 == 320;
        char[] charArray = upperCase.toCharArray();
        while (true) {
            int i3 = i;
            if (i3 < charArray.length) {
                char c = charArray[i3];
                if (!z || c != '\n') {
                    string = new StringBuffer().append((c != '\n' || i3 <= 0 || i3 >= charArray.length + (-1) || charArray[i3 + (-1)] == ' ' || charArray[i3 + 1] == ' ') ? string : new StringBuffer().append(string).append(' ').toString()).append(c).toString();
                }
                i = i3 + 1;
            } else {
                return string;
            }
        }
    }

    public static boolean isAvailable() {
        return agX;
    }

    public static void r(String str, int i) {
        log(new StringBuffer().append("enterIGP(loadingMsg = ").append(str).append(", appLanguage = ").append(i).append(" (").append(agx[i]).append(")").toString());
        aik = 0;
        agZ = 0;
        if (ahI) {
            log("Using OVI feature, standard IGP is deactivated!");
            mT();
            agV = true;
            agW = ahH;
        } else {
            mZ();
            aif = false;
            s(str, i);
            ahe = agy + 4;
            int iMW = mW();
            ahb = iMW;
            ail = iMW;
            if (agS) {
                agQ.a(agU);
            }
        }
        ne();
    }

    private static boolean mS() {
        boolean z = false;
        for (int i = 0; i < agx.length; i++) {
            if (agx[i].equals("SP")) {
                z = true;
            }
        }
        if (!z) {
            return false;
        }
        try {
            String strBY = bY(afX);
            if (strBY != null && strBY.indexOf(aeM) != -1) {
                int iIndexOf = strBY.indexOf(aeM) + aeM.length() + 1;
                int length = aeM.length() + iIndexOf;
                if (length >= strBY.length() || !strBY.substring(iIndexOf, length).equals(aeM)) {
                    return false;
                }
                ahG = ahF;
                if (agq) {
                    ahG = new StringBuffer().append(ahG).append(agh).toString();
                    int iIndexOf2 = ahG.indexOf(agg);
                    if (iIndexOf2 >= 0) {
                        ahG = new StringBuffer().append(ahG.substring(0, iIndexOf2)).append(aeM).append(ahG.substring(iIndexOf2 + agg.length())).toString();
                    }
                    if (ahG.length() == 0) {
                        return false;
                    }
                }
                return true;
            }
            ahG = bY(new StringBuffer().append(aeb).append("-").append(aeM).toString());
            return ahG != null;
        } catch (Exception e) {
            return false;
        }
    }

    private static void s(String str, int i) {
        if (i >= 0 && i < agx.length) {
            if (i > agx.length) {
                i = 0;
            }
            ahA = i;
            if (str.indexOf(124) > 0) {
                ahE = new StringBuffer().append(str.substring(0, str.indexOf(124))).append(str.substring(str.indexOf(124) + 1)).toString();
            } else {
                ahE = str;
            }
            ahd = -1;
            agZ = 0;
            ahc = -1;
            ahb = 0;
            afU = new int[10];
            agV = true;
            if (ahJ >= 480 || ahK >= 480) {
                aea = javax.microedition.lcdui.u.ag(0, 0, 16);
            } else {
                aea = javax.microedition.lcdui.u.ag(0, 0, 8);
            }
            aht = (byte) 0;
            ahu = (byte) 0;
            mT();
        }
    }

    private static void mT() {
        log("initializePlatformRequestServer()");
        if (agU == null) {
            agU = new aw();
        }
        new Thread(agU).start();
    }

    private static int[] mU() {
        int[] iArr = {0, 0};
        for (int i = 0; i < aho.length; i++) {
            if (aho[i]) {
                if (i <= ael) {
                    iArr[0] = iArr[0] + 1;
                } else {
                    iArr[1] = iArr[1] + 1;
                }
            }
        }
        return iArr;
    }

    private static int mV() {
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 < aho.length) {
                i = aho[i2] ? i3 + 1 : i3;
                i2++;
            } else {
                return i3;
            }
        }
    }

    private static int eD(int i) {
        if (ahn[i] == 6) {
            return ahn[i];
        }
        if (ahn[i] == 4) {
            return 4;
        }
        if (ahn[i] == 7) {
            return 7;
        }
        return ahn[i] == 8 ? 8 : -1;
    }

    private static int mW() {
        for (int i = 0; i < aho.length; i++) {
            if (aho[i]) {
                return i;
            }
        }
        return -1;
    }

    private static void eE(int i) {
        try {
            afK = 0;
            switch (i) {
                case -1:
                    ahf = new a[afg];
                    break;
                case 0:
                case 4:
                case 5:
                case 7:
                case 8:
                    break;
                case 1:
                    mP();
                    byte[] bArrEC = eC(i);
                    for (int i2 = 0; i2 < ahA; i2++) {
                        afK = f(bArrEC) + afK;
                    }
                    f(bArrEC);
                    int iF = f(bArrEC);
                    agz = new String[iF];
                    byte[] bArr = new byte[iF];
                    System.arraycopy(bArrEC, afK, bArr, 0, iF);
                    afK += iF;
                    f(bArrEC);
                    int i3 = afK;
                    afK = i3 + 1;
                    int i4 = bArrEC[i3] & 255;
                    int i5 = afK;
                    afK = i5 + 1;
                    int i6 = ((bArrEC[i5] & 255) << 8) | i4;
                    agA = new short[i6];
                    for (int i7 = 0; i7 < i6 - 1; i7++) {
                        short[] sArr = agA;
                        int i8 = afK;
                        afK = i8 + 1;
                        int i9 = bArrEC[i8] & 255;
                        int i10 = afK;
                        afK = i10 + 1;
                        sArr[i7] = (short) (i9 + ((bArrEC[i10] & 255) << 8));
                    }
                    agA[i6 - 1] = (short) iF;
                    int i11 = 0;
                    while (i11 < i6) {
                        int i12 = i11 == 0 ? 0 : agA[i11 - 1] & 65535;
                        int i13 = (agA[i11] & 65535) - i12;
                        if (i13 != 0) {
                            try {
                                agz[i11] = new String(bArr, i12, i13, AndroidWebView.UTF_8);
                            } catch (Exception e) {
                            }
                        }
                        i11++;
                    }
                    if (agS) {
                        ahN = new javax.microedition.lcdui.o(getString(aes), 4, 1);
                        ahO = new javax.microedition.lcdui.o(getString(aet), 2, 1);
                        b(true, true);
                    }
                    break;
                case 2:
                    GLLib.bk("/dataIGPSprites");
                    int i14 = aeY;
                    int i15 = afc;
                    if (agx[ahA].equals("AR")) {
                        i14 = afa;
                        i15 = afe;
                        a.aa(true);
                    } else {
                        a.aa(false);
                    }
                    ahg = b(i14, 3, true, false);
                    ahg.e((int[]) GLLib.de(i15));
                    afM = ahg.hC();
                    a aVar = ahg;
                    a.d('|');
                    int i16 = aeZ;
                    int i17 = afd;
                    if (agx[ahA].equals("AR")) {
                        i16 = afb;
                        i17 = aff;
                        a.aa(true);
                    } else {
                        a.aa(false);
                    }
                    ahh = b(i16, 3, true, false);
                    ahh.e((int[]) GLLib.de(i17));
                    a aVar2 = ahh;
                    a.d('|');
                    if (ahK <= 160) {
                        ahg.IR = 2;
                        if (agx[ahA].equals("AR")) {
                            ahg.IP = 1;
                        } else {
                            ahg.IP = 0;
                        }
                    } else if (ahK > 220) {
                        if (ahK <= 320) {
                            ahg.IQ = -1;
                            if (ahJ == 240 || agt) {
                                ahg.IQ = 0;
                            }
                            ahh.IR = 3;
                            ahh.IP = 0;
                            ahg.IP = -4;
                        } else if (ahK <= 360) {
                            ahh.IQ = -1;
                            if (ahJ == 480) {
                                ahh.IQ = 0;
                            }
                        } else if (ahK <= 480) {
                            ahh.IQ = 0;
                            ahh.IR = 6;
                            if (ahJ == 320) {
                                ahh.IQ = 0;
                            }
                        }
                    }
                    if (agx[ahA].equals("AR")) {
                        ahg.IQ = 1;
                        if (ahh != null) {
                            ahh.IQ = 1;
                        }
                    }
                    GLLib.ke();
                    break;
                case 3:
                    GLLib.bk("/dataIGPSprites");
                    for (int i18 = 0; i18 < afg; i18++) {
                        if (i18 != aeY && i18 != afc && i18 != aeZ && i18 != afd && i18 != afa && i18 != afe && i18 != afb && i18 != aff) {
                            ahf[i18] = b(i18, 1, true, true);
                        }
                    }
                    GLLib.ke();
                    break;
                case 6:
                    if (ahb == ail && aii == -1 && aij == -1) {
                        aii = ai(aeV + ahb, 0);
                        aij = aj(aeV + ahb, 0);
                        break;
                    }
                    break;
                default:
                    if (i == aei) {
                    }
                    break;
            }
        } catch (Exception e2) {
        }
    }

    private static void an(boolean z) {
        for (int i = 0; i < afg; i++) {
            if (ahf[i] != null && i != aeY && i != afc && i != aeZ && i != afd && i != aeT) {
                ahf[i].hs();
                ahf[i] = null;
            }
        }
        if (z) {
            mQ();
            GLLib.ke();
            afL = null;
            afR = null;
            agA = null;
            agz = null;
            afU = null;
            ahE = null;
            for (int i2 = 0; i2 < afg; i2++) {
                if (ahf[i2] != null) {
                    ahf[i2].hs();
                    ahf[i2] = null;
                }
            }
            ahf = null;
            ahg.hs();
            ahg = null;
            ahh.hs();
            ahh = null;
            ahi = null;
        }
        if (0 == 0) {
        }
    }

    public static void ao(boolean z) {
        if (z) {
            if (agZ == 0 || agZ == 2) {
                ahc = agZ;
                agZ = 5;
            }
            mZ();
            return;
        }
        if (agZ == 5) {
            agZ = ahc;
            ahd = -1;
        }
    }

    public static int mX() {
        return agZ;
    }

    public static boolean eF(int i) {
        String string;
        if (ahI) {
            return true;
        }
        if (aif) {
            return !agY || agZ == 4;
        }
        if (!agX) {
            return true;
        }
        if (ahR) {
            ahR = false;
        } else {
            aha = i;
        }
        switch (agZ) {
            case 0:
                if (ahd >= ahe) {
                    agZ = 1;
                    na();
                    mY();
                } else {
                    eE(ahd);
                }
                ahd++;
                break;
            case 1:
                switch (aha) {
                    case 23:
                        if (ahl[ahb >= aem ? (char) 1 : (char) 0] > 1) {
                            if (ahb == 0) {
                                ahb = aem - 1;
                            } else if (ahb == aem) {
                                ahb = agy - 1;
                            } else {
                                ahb--;
                            }
                            while (!aho[ahb]) {
                                if (ahb == 0) {
                                    ahb = aem - 1;
                                } else if (ahb == aem) {
                                    ahb = agy - 1;
                                } else {
                                    ahb--;
                                }
                            }
                            agF = true;
                            na();
                        }
                        break;
                    case 24:
                        if (ahl[ahb >= aem ? (char) 1 : (char) 0] > 1) {
                            if (!agF) {
                                if (ahb == aem - 1) {
                                    ahb = 0;
                                } else if (ahb == agy - 1) {
                                    ahb = aem;
                                } else {
                                    ahb++;
                                }
                                while (!aho[ahb]) {
                                    if (ahb == aem - 1) {
                                        ahb = 0;
                                    } else if (ahb == agy - 1) {
                                        ahb = aem;
                                    } else {
                                        ahb++;
                                    }
                                }
                                agG = true;
                            }
                            na();
                        }
                        break;
                    case Constants.MAX_TREE_DEPTH /* 25 */:
                    case 27:
                        agZ = 6;
                        break;
                    case 26:
                        agZ = 4;
                        break;
                    case 28:
                        ahb = 0;
                        while (!aho[ahb]) {
                            if (ahb == aem - 1) {
                                ahb = 0;
                            } else {
                                ahb++;
                            }
                        }
                        na();
                        break;
                    case 29:
                        ahb = aem;
                        while (!aho[ahb]) {
                            if (ahb == agy - 1) {
                                ahb = aem;
                            } else {
                                ahb++;
                            }
                        }
                        na();
                        break;
                }
                break;
            case 2:
                if (aih) {
                    aih = false;
                } else {
                    an(false);
                    ahd = eD(ahb);
                    eE(ahd);
                    agZ = 1;
                }
                break;
            case 3:
                switch (aha) {
                    case Constants.MAX_TREE_DEPTH /* 25 */:
                    case 30:
                        agW = agR;
                        break;
                    case 26:
                        agZ = 1;
                        agR = null;
                        break;
                }
                break;
            case 4:
                an(true);
                if (agS) {
                    agQ.a(agT);
                    nd();
                }
                agV = false;
                return true;
            case 6:
                if (agN) {
                    string = ahm[aen];
                } else {
                    string = ahm[ahb];
                }
                if (string != null && string.length() > 0) {
                    if (agq) {
                        int iIndexOf = string.indexOf(agj);
                        if (iIndexOf == -1) {
                            string = new StringBuffer().append(string).append(agj).append(agx[ahA]).toString();
                        } else {
                            string = new StringBuffer().append(string.substring(0, iIndexOf)).append(agj).append(agx[ahA]).append(string.substring(iIndexOf + agj.length() + 2)).toString();
                        }
                    }
                    agW = string;
                }
                break;
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:79:0x0600 A[PHI: r4
  0x0600: PHI (r4v37 int) = (r4v22 int), (r4v25 int), (r4v28 int), (r4v33 int), (r4v35 int), (r4v38 int) binds: [B:77:0x05f9, B:71:0x05e7, B:65:0x05cc, B:55:0x05aa, B:50:0x0591, B:45:0x0583] A[DONT_GENERATE, DONT_INLINE]] */
    private static void mY() {
        int i;
        int i2 = ahJ;
        int i3 = ahK;
        agM = new int[afG][];
        for (int i4 = 0; i4 < afG; i4++) {
            agM[i4] = new int[2];
            agM[i4][0] = 0;
            agM[i4][1] = 0;
        }
        int iAi = ai(aeT, 0);
        int iAj = aj(aeT, 0);
        int iAi2 = ai(aeU, 0);
        int iAj2 = aj(aeU, 0);
        int iAj3 = aj(aeT, 9);
        int iAi3 = ai(aeT, 10);
        int iAj4 = aj(aeT, 10);
        int iAj5 = aj(aeT, 15);
        int iAj6 = aj(aeT, 29);
        ahg.a(getString(aeK), (char[]) null);
        short[] sArrA = ahg.a(getString(aeK), i2 - iAi, 999, a.Iy);
        a aVar = ahg;
        afW = a.IJ;
        int i5 = 4;
        if (w.XG) {
            i5 = 5;
        }
        if (w.XE) {
            i5++;
        }
        afV = sArrA[(i5 * 0) + 4];
        int i6 = afV;
        if (iAj > afV) {
            i6 = iAj;
        }
        if (iAj4 > i6) {
            i6 = iAj4;
        }
        int i7 = afM;
        int i8 = (((i3 - (i6 + ((i7 + iAj3) + iAj2))) - 3) - iAj5) >> 2;
        int i9 = -3;
        if (i3 <= 160) {
            i = 2;
        } else if (i3 <= 220) {
            i = 1;
        } else if (i3 <= 240) {
            i9 = 0;
            if (agx[ahA].equals("AR")) {
                i = 1;
            } else {
                i = i9;
            }
        } else if (i3 <= 320) {
            i9 = 0;
            if (i2 == 240) {
                i = 1;
            } else {
                i = i9;
            }
        } else if (i3 <= 360) {
            i9 = 3;
            if (agx[ahA].equals("AR")) {
                i = 1;
            } else {
                i = i9;
            }
        } else if (i3 <= 480) {
            i9 = 6;
            if (agx[ahA].equals("AR")) {
                if (i2 == 800) {
                    i9 = 3;
                } else {
                    i9 = 0;
                }
            }
            if (i2 == 320) {
                i = 0;
            } else {
                i = i9;
            }
        } else if (i3 <= 800) {
            i9 = 3;
            if (agx[ahA].equals("AR")) {
                i = 2;
            } else {
                i = i9;
            }
        } else if (i3 > 854 && i3 > 1280) {
            i = i9;
        } else {
            i = 3;
        }
        int iAi4 = ai(aeT, 15);
        int iAi5 = ai(aeT, 17);
        if ((i2 == 240 && i3 == 320) || (i2 == 480 && i3 == 854)) {
            iAi4 = ai(aeT, 31);
            iAi5 = ai(aeT, 33);
        }
        if (i2 > 800 && i3 >= 480) {
            iAi4 = ai(aeT, 42);
            iAi5 = ai(aeT, 44);
        }
        agM[afj][0] = (i2 - iAi4) >> 2;
        agM[afj][1] = 3;
        agM[afk][0] = ((i2 - iAi5) >> 2) * 3;
        agM[afk][1] = agM[afj][1];
        int iBF = ahf[aeT].bF(15);
        a aVar2 = ahf[aeT];
        int iBx = aVar2.bx((iBF - 1) + aVar2.GM[15]);
        agM[afl][0] = i2 >> 2;
        agM[afl][1] = (iBx >> 1) + agM[afj][1] + i;
        agM[afm][0] = (i2 >> 1) + (i2 >> 2);
        agM[afm][1] = agM[afl][1];
        agM[afh][0] = i2 >> 1;
        agM[afh][1] = (((i7 >> 1) + i8) + i) - 2;
        int[] iArr = agM[afh];
        iArr[1] = iAj5 + agM[afj][1] + iArr[1];
        agM[afn][0] = 0;
        agM[afn][1] = (((i7 >> 1) + (agM[afh][1] + i8)) - i) - (-2);
        agM[afi][0] = i2 >> 1;
        agM[afi][1] = ((agM[afn][1] + (iAj3 >> 1)) + i) - 2;
        int iAi6 = ai(aeT, 2);
        int iAj7 = aj(aeT, 2);
        agM[afu][0] = 5;
        agM[afu][1] = (agM[afn][1] + (iAj3 >> 1)) - (iAj7 >> 1);
        agM[afv][0] = (i2 - 5) - iAi6;
        agM[afv][1] = agM[afu][1];
        agM[afs][0] = (i2 - aee) - iAi;
        agM[afs][1] = (i3 - 2) - iAj;
        agM[afF][0] = aee;
        agM[afF][1] = (i3 - 2) - iAj;
        int i10 = agM[afs][0] - (iAi3 * 2);
        agM[afx][0] = i10 / 3;
        agM[afx][1] = (i3 - 2) - iAj4;
        agM[afz][0] = agM[afx][0] + (iAi3 >> 1) + (iAi3 % 2);
        agM[afz][1] = agM[afx][1] + (iAj4 >> 1) + i;
        agM[afw][0] = (i10 / 3) + agM[afx][0] + iAi3;
        agM[afw][1] = agM[afx][1];
        agM[afA][0] = agM[afw][0] + (iAi3 >> 1) + (iAi3 % 2);
        agM[afA][1] = agM[afw][1] + (iAj4 >> 1) + i;
        agM[afy][0] = (i2 >> 1) - (iAi3 >> 1);
        agM[afy][1] = agM[afx][1];
        agM[afB][0] = agM[afy][0] + (iAi3 >> 1) + (iAi3 % 2);
        agM[afB][1] = (iAj4 >> 1) + agM[afy][1] + i;
        agM[afo][0] = (i2 >> 1) - (iAi2 >> 1);
        agM[afo][1] = agM[afn][1] + iAj3 + i8;
        agM[afC][0] = (i2 - iAi) >> 1;
        agM[afC][1] = agM[afo][1] + iAj2 + (((i3 - agM[afo][1]) - iAj2) >> 1);
        agM[afC][0] = (i2 - iAi3) >> 2;
        if (i3 > i2) {
            agM[afC][1] = agM[afo][1] + iAj2 + ((agM[afy][1] - (agM[afo][1] + iAj2)) >> 1);
            agM[afC][0] = i2 >> 1;
        }
        agM[afp][0] = i2 >> 1;
        agM[afp][1] = agM[afo][1] + ((iAj2 * 2) / 3);
        agM[afq][0] = 5;
        agM[afq][1] = (agM[afo][1] + (iAj2 >> 1)) - (aj(aeT, 6) >> 1);
        agM[afr][0] = (i2 - ai(aeT, 4)) - 5;
        agM[afr][1] = agM[afq][1];
        agM[aft][0] = (iAi2 + agM[afo][0]) - ai(aeT, 8);
        agM[aft][1] = (iAj2 + agM[afo][1]) - aj(aeT, 8);
        agM[afD][0] = 0;
        agM[afD][1] = agM[afh][1] - (iAj6 >> 1);
        int iBF2 = ahf[aeT].bF(15);
        a aVar3 = ahf[aeT];
        agM[afD][1] = ((aVar3.bx((iBF2 - 1) + aVar3.GM[15]) >> 1) + agM[afj][1]) - (iAj6 >> 1);
    }

    private static int ai(int i, int i2) {
        if (ahf == null || ahf[i] == null) {
            return 0;
        }
        return ahf[i].by(i2);
    }

    private static int aj(int i, int i2) {
        if (ahf == null || ahf[i] == null) {
            return 0;
        }
        return ahf[i].bz(i2);
    }

    public static void ak(int i, int i2) {
        int i3;
        if (agZ != 0) {
            int iAn = an(i, i2);
            int i4 = ahJ;
            int i5 = ahK;
            int iAj = aj(aeT, 10);
            int iAj2 = aj(aeT, 15);
            int iAi = ai(aeT, 6);
            int iAj3 = aj(aeT, 6);
            if (Math.abs(ahW - i) <= (i4 * 10) / 100 || i2 >= i5 - iAj || i2 <= iAj2 || ((ahW < iAi && ahX > agM[afq][1] && ahX < agM[afq][1] + iAj3) || ((ahW > i4 - iAi && ahX > agM[afr][1] && ahX < agM[afr][1] + iAj3) || ahX < iAj2 || ahX > i5 - iAj))) {
                i3 = iAn;
            } else if (ahW - i < 0) {
                i3 = 23;
                ahU = true;
            } else {
                i3 = 24;
                ahU = true;
            }
            if (i3 != 0) {
                aha = i3;
                ahT = false;
                ahR = true;
            }
            ahP = 0;
            ahQ = 0;
            ahS = false;
            ahV = false;
            ahW = -1;
            ahX = -1;
        }
    }

    public static void al(int i, int i2) {
        if (agZ != 0) {
            int iAn = an(i, i2);
            if (iAn != 0) {
                ahP = iAn;
            }
            int i3 = ahJ;
            int i4 = ahK;
            if (ahW == -1 || ahX == -1) {
                ahW = i;
                ahX = i2;
            }
        }
    }

    public static void am(int i, int i2) {
        if (agZ != 0) {
            aha = 0;
            ahP = 0;
            ahS = false;
            ahV = false;
            al(i, i2);
        }
    }

    private static int an(int i, int i2) {
        int i3 = ahJ;
        int i4 = ahK;
        if (i < 0 || i2 < 0) {
            return 0;
        }
        if (agZ == 0 || !agV) {
            return 0;
        }
        if (i2 > agM[afj][1] && i2 < agM[afj][1] + aj(aeT, 15) + 0) {
            if (i < (i3 >> 1)) {
                if (ahb > (agB.length + agC.length) - 1) {
                    return 28;
                }
            } else if (ahb < agB.length + agC.length) {
                return 29;
            }
        }
        int iAbs = Math.abs(((int) ((System.currentTimeMillis() / 80) % 8)) - 4) + 1;
        if (i > agM[afq][0] + iAbs && i < agM[afq][0] + ai(aeT, 6) + iAbs && i2 > agM[afq][1] && i2 < agM[afq][1] + aj(aeT, 6)) {
            return 23;
        }
        if (i > agM[afr][0] - iAbs && i < (agM[afr][0] + ai(aeT, 4)) - iAbs && i2 > agM[afr][1] && i2 < agM[afr][1] + aj(aeT, 4)) {
            return 24;
        }
        if (ahn[ahb] == 7 || ahn[ahb] == 8) {
            if (i > agM[afy][0] && i < agM[afy][0] + ai(aeT, 10) && i2 > agM[afy][1] && i2 < agM[afy][1] + aj(aeT, 10)) {
                ahS = true;
                return 25;
            }
        } else if (i > agM[afy][0] && i < agM[afy][0] + ai(aeT, 10) && i2 > agM[afy][1] && i2 < agM[afy][1] + aj(aeT, 10)) {
            ahS = true;
            return 25;
        }
        if (i <= agM[afs][0] + 0 || i >= agM[afs][0] + ai(aeT, 0) + 0 || i2 <= agM[afs][1] + 0 || i2 >= agM[afs][1] + aj(aeT, 0) + 0) {
            return (i <= agM[afo][0] || i >= ai(aeU, 0) + agM[afo][0] || i2 <= agM[afo][1] || i2 >= aj(aeU, 0) + agM[afo][1]) ? 0 : 25;
        }
        ahV = true;
        return 26;
    }

    private static void mZ() {
        aha = 0;
        ahP = 0;
        ahQ = 0;
        ahR = true;
        ahS = false;
        ahV = false;
        ahT = false;
        agE = 0;
        agF = false;
        agG = false;
        ahW = -1;
        ahX = -1;
    }

    private static void na() {
        ahr = ahb;
        ahv = aeu;
        if (ahn[ahb] == 4 || ahn[ahb] == 6 || ahn[ahb] == 7) {
            ahv = aev;
        }
        if (ahn[ahb] == 8) {
            ahv = aev;
        }
    }

    public static void nb() {
        int i = ahJ;
        ahJ = ahK;
        ahK = i;
        ahL = ahJ >> 1;
        ahM = ahK >> 1;
        if (agZ == 1) {
            na();
        }
    }

    public static void b(aj ajVar) {
        if (ahI) {
        }
        if (agX || aif) {
            if (adZ != null) {
            }
            if (aif) {
                return;
            }
            c(ajVar, 0, 0, ahJ, ahK);
            switch (agZ) {
                case 0:
                    GLLib.dk(0);
                    GLLib.b(ajVar, 0.0f, 0.0f, ahJ, ahK, true);
                    b(ajVar, ahM, (ahJ * 3) / 4, ahd, ahe);
                    if (ahE != null && !ahE.trim().equals("")) {
                        GLLib.dk(16777215);
                        GLLib.Qk = aea;
                        GLLib.a(ahE, ahL, ahM - 5, 33);
                        break;
                    }
                    break;
                case 1:
                    c(ajVar);
                    if (System.currentTimeMillis() % 1000 > 500 || (ahP == 27 && !ahS)) {
                        if (ahb == aen) {
                            a(ajVar, false);
                        } else {
                            a(ajVar, true);
                        }
                    }
                    break;
                case 2:
                    if (aik > 1) {
                        int i = afM;
                        int i2 = (ahM - i) - 5;
                        GLLib.dk(6324884);
                        GLLib.b(ajVar, 0.0f, i2, ahJ, i * 2, true);
                        int i3 = afS;
                        afS = 0;
                        a(aeL, ajVar, ahL, (i >> 1) + i2, 65);
                        afS = i3;
                    }
                    break;
                case 3:
                    c(ajVar);
                    int i4 = ahK;
                    int i5 = ahJ;
                    int i6 = (i4 * 40) / 100;
                    int iAi = ai(aeT, 0) - 5;
                    c(ajVar, 0, i6 - 1, i5, i4 - ((i6 - 1) * 2), -1);
                    c(ajVar, 0, i6, i5, i4 - (i6 * 2), -220209185);
                    agJ = false;
                    GLLib.dk(16777215);
                    GLLib.b(ajVar, 0.0f, i4 - (iAi + 1), i5, iAi + 1, true);
                    GLLib.dk(-220209185);
                    GLLib.b(ajVar, 0.0f, i4 - iAi, i5, iAi, true);
                    afS = 1;
                    a(aey, ajVar, i5 >> 1, i4 >> 1, 3, true);
                    d(ajVar);
                    break;
            }
        }
    }

    private static void c(aj ajVar, int i, int i2, int i3, int i4, int i5) {
        int[] iArr = new int[i3 * i4];
        for (int i6 = 0; i6 < iArr.length; i6++) {
            iArr[i6] = i5;
        }
        GLLib.a(ajVar, iArr, 0, i3, i, i2, i3, i4, true, true, 0);
    }

    private static void c(aj ajVar) {
        int i;
        int i2;
        int i3 = ahJ;
        int i4 = ahK;
        nc();
        GLLib.dk(16777215);
        if (aeg[0]) {
            GLLib.dk(0);
        }
        if (ahj != null) {
            GLLib.a(ajVar, ahj, 0, 0, 0, true);
        } else {
            GLLib.dk(16777215);
            GLLib.b(ajVar, 0.0f, 0.0f, i3, i4, true);
        }
        int i5 = agM[afh][0];
        int i6 = agM[afh][1];
        int i7 = agM[afi][0];
        int i8 = agM[afi][1];
        int i9 = agM[afo][0];
        int i10 = agM[afo][1];
        int i11 = agM[aft][0];
        int i12 = agM[aft][1];
        int i13 = agM[afn][0];
        int i14 = agM[afn][1];
        int i15 = agM[afu][0];
        int i16 = agM[afu][1];
        int i17 = agM[afv][0];
        int i18 = agM[afv][1];
        int i19 = agM[afC][0];
        int i20 = agM[afC][1];
        int i21 = agM[afD][0];
        int i22 = agM[afD][1];
        d(ajVar, aeT, 9, i13, i14, 20);
        afS = 1;
        afT = 0;
        if (ahn[ahb] == 6) {
            if (!ahC.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                if (agx[ahA].equals("AR") && ((i3 == 640 || i3 == 800) && i4 == 480)) {
                    if (afT == 0) {
                        ahg.IR = 10;
                    } else {
                        ahh.IR = 10;
                    }
                }
                a(ahC, ajVar, i3, i5, i6, 3);
                if (agx[ahA].equals("AR") && ((i3 == 640 || i3 == 800) && i4 == 480)) {
                    if (afT == 0) {
                        ahg.IR = 3;
                    } else {
                        ahh.IR = 3;
                    }
                }
            }
            a(getString(ahr), ajVar, i3, i7, i8, 3);
            d(ajVar, ahb + aeV, 0, i9, i10, 18);
            d(ajVar, aeT, 8, i11, i12, 18);
            if (ags == null || !ags.equals("TMUS")) {
                int i23 = afT;
                afT = 1;
                a(getString(aeK), ajVar, (i4 > i3 ? i3 : ((i3 - aee) - ai(aeT, 10)) >> 1) - 1, i19, i20, 3, true);
                afT = i23;
            }
        } else if (ahn[ahb] == 7) {
            if (!ahD.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                if (agx[ahA].equals("AR") && ((i3 == 640 || i3 == 800) && i4 == 480)) {
                    if (afT == 0) {
                        ahg.IR = 10;
                    } else {
                        ahh.IR = 10;
                    }
                }
                a(ahD, ajVar, i3, i5, i6, 3);
                if (agx[ahA].equals("AR") && ((i3 == 640 || i3 == 800) && i4 == 480)) {
                    if (afT == 0) {
                        ahg.IR = 3;
                    } else {
                        ahh.IR = 3;
                    }
                }
            }
            a(getString(aeC), ajVar, i3, i7, i8, 3);
            d(ajVar, aeW, 0, i9, i10, 18);
        } else if (ahn[ahb] == 8) {
            a(getString(aeD), ajVar, i3, i5, i6, 3);
            a(getString(aez), ajVar, (i3 * 3) / 4, i7, i8, 3);
            d(ajVar, aeX, 0, i9, i10, 18);
        } else {
            afS = 1;
            a(getString(aeB), ajVar, i3, i5, i6, 3);
            a(getString(ahr), ajVar, (i3 * 3) / 4, i7, i8, 3);
            d(ajVar, (ahb - ael) + aeU, 0, i9, i10, 18);
        }
        d(ajVar, aeT, 2, i15, i16, 20);
        d(ajVar, aeT, 3, i17, i18, 20);
        int i24 = agM[afj][0];
        int i25 = agM[afj][1];
        int i26 = agM[afk][0];
        int i27 = agM[afk][1];
        int i28 = agM[afy][0];
        int i29 = agM[afy][1];
        int i30 = agM[afl][0];
        int i31 = agM[afl][1];
        int i32 = agM[afm][0];
        int i33 = agM[afm][1];
        int i34 = agM[afB][0];
        int i35 = agM[afB][1];
        if (ahn[ahb] == 7 || ahn[ahb] == 8) {
            int i36 = 15;
            int i37 = 18;
            if ((i3 == 240 && i4 == 320) || (i3 == 480 && i4 == 854)) {
                i36 = 31;
                i37 = 34;
            }
            if (i3 <= 800 || i4 < 480) {
                i = i37;
            } else {
                i36 = 42;
                i = 45;
            }
            d(ajVar, aeT, i36, i24, i25, 18);
            d(ajVar, aeT, i, i26, i27, 18);
        } else {
            int i38 = 17;
            int i39 = 16;
            if ((i3 == 240 && i4 == 320) || (i3 == 480 && i4 == 854)) {
                i38 = 33;
                i39 = 32;
            }
            if (i3 > 800 && i4 >= 480) {
                i38 = 44;
                i39 = 43;
            }
            d(ajVar, aeT, i38, i26, i27, 18);
            d(ajVar, aeT, i39, i24, i25, 18);
        }
        int iAi = ai(aeT, 19);
        int iAi2 = ai(aeT, 10);
        int iAj = aj(aeT, 10);
        d(ajVar, aeT, ahS ? 11 : 13, i28, i29, 18);
        afS = 0;
        if (ahS) {
            afS = 1;
        }
        short[] sArrA = ahg.a(getString(aeJ), (iAi2 - iAi) - 4, 0, a.Iy);
        int i40 = w.XG ? 5 : 4;
        if (w.XE) {
            i40++;
        }
        afW = sArrA[(i40 * 0) + 3];
        if (sArrA[0] > 1) {
            afW = (iAi2 - iAi) - 8;
        }
        int i41 = iAi2 - ((afW + iAi) + 4);
        int i42 = i41 < 0 ? 0 : i41 >> 1;
        d(ajVar, aeT, 19, (i34 - (iAi2 >> 1)) + i42, ((iAj >> 1) + i29) - (aj(aeT, 19) >> 1), 18);
        if (agx[ahA].equals("AR") && i3 == 320 && i4 == 240) {
            if (afT == 0) {
                ahg.IR = 2;
            } else {
                ahh.IR = 2;
            }
        }
        if (sArrA[0] > 1) {
            a(getString(aeJ), ajVar, (iAi2 - iAi) - 4, (iAi >> 1) + i34 + 4, i35 - 2, 3, true);
        } else {
            a(getString(aeJ), ajVar, i3, (i34 - (iAi2 >> 1)) + i42 + iAi + 2, i35 - 2, 6);
        }
        if (agx[ahA].equals("AR") && i3 == 320 && i4 == 240) {
            if (afT == 0) {
                ahg.IR = 3;
            } else {
                ahh.IR = 3;
            }
        }
        afS = 0;
        a(getString(aeG), ajVar, i3, i30, i31, 3);
        a(getString(aeH), ajVar, i3, i32, i33, 3);
        short[] sArrA2 = agx[ahA].equals("FR") ? ahg.a(new StringBuffer().append(getString(aeH)).append(" ").toString(), i3, 0, a.Iy) : ahg.a(getString(aeH), i3, 0, a.Iy);
        int i43 = w.XG ? 5 : 4;
        if (w.XE) {
            i43++;
        }
        afW = sArrA2[(i43 * 0) + 3];
        if (agx[ahA].equals("AR")) {
            i2 = i4 >= 720 ? 12 : 6;
        } else {
            i2 = 3;
        }
        if (agx[ahA].equals("AR")) {
            d(ajVar, aeT, 29, (i32 - (afW >> 1)) - i2, i22, 18);
        } else {
            d(ajVar, aeT, 29, (afW >> 1) + i32 + i2, i22, 18);
        }
        if (ahl[ahb >= aem ? (char) 1 : (char) 0] > 1) {
            int i44 = 6;
            int i45 = 4;
            int iAbs = Math.abs(((int) ((System.currentTimeMillis() / 80) % 8)) - 4) + 1;
            int i46 = agM[afq][0];
            int i47 = agM[afq][1];
            int i48 = agM[afr][0];
            int i49 = agM[afr][1];
            if (agF || ahP == 23) {
                i44 = ahU ? 6 : 7;
                agE++;
            }
            int i50 = i44;
            if (agG || ahP == 24) {
                i45 = ahU ? 4 : 5;
                agE++;
            }
            d(ajVar, aeT, i50, i46 + iAbs, i47, 36);
            d(ajVar, aeT, i45, i48 - iAbs, i49, 36);
            if (agE > 4) {
                agF = false;
                agG = false;
                agE = 0;
                ahU = false;
            }
        }
        d(ajVar);
    }

    private static void nc() {
        if (ahb != aen) {
            ahy = agM[afp][0];
            ahz = agM[afp][1];
            return;
        }
        ahy = agM[afp][0];
        int iAj = aj(aeX, 0);
        ahz = ((aj(aeU, 0) - iAj) / 2) + agM[afo][1] + iAj;
    }

    private static void a(aj ajVar, boolean z) {
        if (ahs) {
            int i = ahJ;
            int i2 = ahK;
            int iAi = !z ? i : ((agM[afr][0] - agM[afq][0]) - ai(aeT, 6)) - 16;
            int iAi2 = ai(aeT, 20);
            int iAj = aj(aeT, 20);
            int iAi3 = ai(aeT, 25);
            int iAi4 = ai(aeT, 25);
            ai(aeT, 26);
            aj(aeT, 26);
            int i3 = afS;
            afS = 0;
            ahg.a(getString(ahv), (char[]) null);
            short[] sArrA = ahg.a(getString(ahv), iAi, 999, a.Iy);
            a aVar = ahg;
            afW = a.IJ;
            if (agx[ahA].equals("AR") && afW >= aii && aii > 0) {
                afW = aii - 30;
            }
            int i4 = 4;
            if (w.XG) {
                i4 = 5;
            }
            if (w.XE) {
                i4++;
            }
            afV = sArrA[(i4 * 0) + 4];
            ahw = afW;
            ahx = afV;
            if (ahw % iAi3 != 0) {
                ahw = ((ahw / iAi3) + 1) * iAi3;
            }
            int i5 = ahw / iAi3;
            int iAj2 = aj(aeT, 10);
            if (ahx < iAj2) {
                ahx = iAj2;
            }
            ahw += iAi2 << 1;
            if (ahz + ahx + (iAi4 << 1) > i2) {
                ahz = (i2 - ahx) - (iAi4 << 1);
            }
            int i6 = ahy - (ahw >> 1);
            int i7 = ahz + (ahx >> 1);
            GLLib.dk(-1030069);
            GLLib.b(ajVar, i6, i7 + iAj, ahw, ahx - (iAj << 1), true);
            d(ajVar, aeT, 20, i6, i7, 17);
            d(ajVar, aeT, 23, i6, (ahx + i7) - iAi4, 17);
            int i8 = i6 + iAi2;
            for (int i9 = 0; i9 < i5; i9++) {
                d(ajVar, aeT, 22, i8, i7, 17);
                d(ajVar, aeT, 25, i8, (ahx + i7) - iAi4, 17);
                i8 += iAi3;
            }
            d(ajVar, aeT, 21, i8, i7, 17);
            d(ajVar, aeT, 24, i8, (ahx + i7) - iAi4, 17);
            a(getString(ahv), ajVar, iAi, ahy, ahz + ahx + iAj, 3, true);
            afS = i3;
        }
    }

    private static void d(aj ajVar) {
        int i = agM[afs][0];
        int i2 = agM[afs][1];
        boolean z = ahV;
        if (ahS) {
        }
        d(ajVar, aeT, z, i, i2, 20);
    }

    private static void b(boolean z, boolean z2) {
        if (ahO != null) {
            agQ.b(ahO);
        }
        if (ahN != null) {
            agQ.b(ahN);
        }
        if (z) {
            agQ.a(ahN);
        }
        if (z2) {
            agQ.a(ahO);
        }
    }

    private static void nd() {
        if (ahO != null) {
            agQ.b(ahO);
            ahO = null;
        }
        if (ahN != null) {
            agQ.b(ahN);
            ahN = null;
        }
    }

    private static void b(aj ajVar, int i, int i2, int i3, int i4) {
        if (i3 > i4) {
            i3 = i4;
        }
        int i5 = (ahJ - i2) / 2;
        c(ajVar, i5, 0, i2 + 2, ahK);
        GLLib.dk(16777215);
        GLLib.b(GLLib.Qj, i5, i, i2, 6.0f, true);
        GLLib.b(ajVar, i5, i, i2, 6.0f, true);
        GLLib.dk(0);
        GLLib.b(ajVar, i5 + 1 + 1, i + 1 + 1, (i2 - 2) - 2, 3.0f, true);
        GLLib.dk(16711680);
        GLLib.b(ajVar, i5 + 1 + 1, i + 1 + 1, ((((i2 - 2) - 2) * i3) / i4) + 1, 3.0f, true);
    }

    private static void d(aj ajVar, int i, int i2, int i3, int i4, int i5) {
        ahf[i].a(ajVar, i2, i3, i4, 0);
    }

    private static void c(aj ajVar, int i, int i2, int i3, int i4) {
        GLLib.a(ajVar, Math.max(i, 0), Math.max(i2, 0), Math.min(i3, ahJ), Math.min(i4, ahK), true);
    }

    @Override // java.lang.Runnable
    public void run() {
        log("platformRequest Thread alive!");
        log(new StringBuffer().append("Thread.activeCount() = ").append(Thread.activeCount()).toString());
        while (agV) {
            try {
                if (agW != null) {
                    adZ = agW;
                    nf();
                    agW = null;
                    if (aif) {
                        agV = false;
                    }
                }
                Thread.sleep(1000L);
            } catch (Exception e) {
            }
        }
        log("platformRequest Thread dead!");
    }

    private static void ne() {
        agH = true;
        agI = true;
        javax.microedition.b.b bVarT = null;
        try {
            bVarT = javax.microedition.b.b.t("igp19", false);
        } catch (Exception e) {
            try {
                bVarT = javax.microedition.b.b.t("igp19", true);
            } catch (Exception e2) {
            }
        }
        if (bVarT != null) {
            try {
                bVarT.akW();
            } catch (Exception e3) {
            }
        }
    }

    private static void nf() {
        if (adZ != null && adZ.length() > 0) {
            agN = false;
            String str = adZ;
            adZ = null;
            log(new StringBuffer().append("urlPlatformRequest = ").append(str).toString());
            try {
                agP.jf(str);
                Thread.sleep(200L);
            } catch (Exception e) {
            }
            if (!aif) {
                agZ = 1;
            } else {
                agZ = 4;
            }
        }
    }

    @Override // javax.microedition.lcdui.p
    public void a(javax.microedition.lcdui.o oVar, javax.microedition.lcdui.t tVar) {
        if (agS) {
            if (oVar == ahN) {
                eF(25);
            } else if (oVar == ahO) {
                eF(26);
            }
        }
    }

    private static final String bY(String str) {
        return agP.bY(str);
    }

    private static void log(String str) {
        a(str, adY);
    }

    private static void a(String str, int i) {
    }

    private static final void ng() {
        aie.put("URL-TEMPLATE-GAME", "URL-TEMPLATE-GAME-XXX");
        aie.put("URL-OPERATOR", "URL-OPERATOR-XXX");
        aie.put("URL-ORANGE", "URL-ORANGE-XXX");
        aie.put("IGP-PROMOS", "IGP-PROMOS-XXX");
        aie.put("IGP-FREEMIUM", "IGP-FREEMIUM-XXX");
        aie.put("IGP-CATEGORIES", "IGP-CATEGORIES-XXX");
        aie.put("IGP-VERSION", "IGP-VERSION-XXX");
        aie.put("TITLE-FREEMIUM", "TITLE-FREEMIUM-XXX");
        aie.put("TITLE-GLCLUB", "TITLE-GLCLUB-XXX");
    }

    private static InputStream bZ(String str) {
        return a("a".getClass(), str);
    }

    private static a b(int i, int i2, boolean z, boolean z2) {
        return a(i, i2, 0, 0, z, z2, false);
    }

    private static a a(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
        a aVar = new a();
        aVar.d(GLLib.db(i), 0);
        for (int i5 = 0; (i2 >> i5) != 0; i5++) {
            if (((i2 >> i5) & 1) != 0) {
                if (i3 != 0) {
                    if (i3 == 1) {
                        aVar.D(i5, i4);
                    } else if (i3 == 2) {
                        aVar.bT(i5);
                    }
                }
                if (z) {
                }
            }
        }
        if (z && z2) {
            aVar.X(true);
        }
        return aVar;
    }
}
