package com.quanhoang.greenfarm3.S800x480;

import android.os.SystemClock;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.UserDataStore;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.glads.GLURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import org.json.JSONException;

/* JADX INFO: compiled from: Federation.java */
/* JADX INFO: loaded from: classes.dex */
public class s {
    // Preserve DEX exception behavior despite missing binary Throws metadata.
    private static byte[] decodeResponse(String value) throws IOException {
        return GLLib.bJ(value);
    }

    @SuppressWarnings("unchecked")
    private static <Failure extends Throwable> RuntimeException propagateChecked(Throwable failure) throws Failure {
        throw (Failure) failure;
    }

    private static bx LU;
    private static boolean LV;
    private static String LX;
    private static String LZ;
    private static String MC;
    private static boolean MD;
    private static boolean MF;
    private static String MG;
    private static Object[][] MH;
    private static String[][] MI;
    private static String[][] MJ;
    private static String MM;
    private static Exception MN;
    private static String MO;
    private static Hashtable MP;
    private static int[] MQ;
    private static Object[][] MR;
    private static String[][] MS;
    private static String[][] MT;
    private static String[] MU;
    private static String[] MV;
    private static long[] MW;
    private static boolean MX;
    private static boolean MY;
    private static int[] MZ;
    private static String Ma;
    private static long Mb;
    private static String Mc;
    private static String Md;
    private static String Me;
    private static String Mf;
    private static int[] Mg;
    private static int[] Mh;
    private static long[] Mi;
    private static String[] Mj;
    private static bx Mk;
    private static am[] Ml;
    static long Mm;
    private static String Mn;
    private static String Mo;
    private static String Mp;
    private static String Mq;
    private static String Mr;
    private static String Ms;
    private static String Mt;
    private static String Mu;
    private static String Mv;
    private static String Mx;
    private static am My;
    private static String Mz;
    private static int[] Na;
    private static String Nb;
    private static String Nc;
    private static String Nd;
    private static boolean Ne;
    private static String Nf;
    private static String Ng;
    private static String Nh;
    private static String Ni;
    private static String Nj;
    private static String Nk;
    private static final String[] LT = {ShareConstants.WEB_DIALOG_PARAM_MESSAGE, "asset", "auth", "leaderboard", "social", "storage", "lobby", "matchmaker", "gllive", "config", "chat", "", "alert"};
    private static int LW = w.YS;
    private static String LY = "";
    private static int Mw = -1;
    public static final String MA = null;
    static final String MB = null;
    private static int MK = w.Yf;
    private static int ML = GLURLConnection.HTTP_OK;
    private static char[] Nl = {' ', '{', '}', ':', '*', ',', '|', '\"', '+', '-', '/', '$', '='};

    private static String aL(String str) {
        String str2 = "https://eve.gameloft.com/";
        if ("https://eve.gameloft.com/" != null && "https://eve.gameloft.com/".endsWith("/")) {
            str2 = "" + "https://eve.gameloft.com/".substring(0, "https://eve.gameloft.com/".length() - 1);
        }
        String str3 = str2 + "/config/" + gX();
        return str != null ? str3 + "/datacenters/" + str + "/urls" : str3;
    }

    public static String g(String str, boolean z) {
        if (str.startsWith("http")) {
            return str;
        }
        return !z ? "https://" + str : "https://" + str;
    }

    private static String ch(int i) {
        String[] strArr = (String[]) cv(i);
        am amVarCr = cr(i);
        ct(i);
        if (I(i, 0)) {
            a(strArr[0], "", 0, amVarCr);
            H(i, 1);
        }
        if (!I(i, 1) || !b(amVarCr)) {
            return null;
        }
        H(i, 3);
        return strArr[0];
    }

    private static String ci(int i) {
        c(i, 10, false);
        if (I(i, 0)) {
            LU = new bx(aW(Mj[10]), false);
            LU.connect();
            iK();
            aF("Online");
            iL();
            H(i, 1);
        }
        if (!I(i, 1)) {
            return null;
        }
        LV = true;
        H(i, 3);
        return "";
    }

    private static String cj(int i) {
        byte[] bArrOj;
        ct(i);
        int iCC = cC(i);
        if (I(i, 0)) {
            String[] strArr = (String[]) cv(i);
            String[] strArr2 = MI[iCC];
            String str = "";
            if (strArr2 != null) {
                int i2 = 0;
                for (int i3 = 0; i3 < strArr2.length; i3++) {
                    if (strArr2[i3] != null) {
                        str = str + strArr2[i3];
                    } else {
                        if (strArr[i2] != null) {
                            str = str + strArr[i2];
                        }
                        i2++;
                    }
                }
            }
            LW = w.YS;
            LU.i(str.getBytes());
            H(i, 1);
        }
        if (!I(i, 1) || (bArrOj = LU.oj()) == null) {
            return null;
        }
        H(i, 3);
        return new String(bArrOj);
    }

    private static void iK() {
        LU.i("<?xml version='1.0'?> <stream:stream to='pegasus' xml:lang='en' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams'>".getBytes());
    }

    public static void aF(String str) {
        LX = str;
        LU.i(("<presence><status>" + str + "</status></presence>").getBytes());
    }

    private static void iL() {
        LU.i(((("<iq type='set'><query xmlns='jabber:iq:auth'><username>" + bc(js()) + "</username>") + "<password>" + Ma + "</password>") + "<resource>resource</resource></query></iq>").getBytes());
    }

    public static void ab(boolean z) {
        a(z, "");
    }

    public static void ac(boolean z) {
        b(z, "");
    }

    public static void a(String str, String str2, String str3, String str4, String str5) {
        a("inbox", str, str2, str3, str4, str5, "");
    }

    public static void l(String str, String str2) {
        d(str, str2, "");
    }

    public static void c(String str, String str2, String str3) {
        a(str, str2, str3, "");
    }

    public static void m(String str, String str2) {
        e(str, str2, "");
    }

    public static void a(boolean z, String str) {
        GLLib.c(w.YJ, "You need to enable federation_useHermes_Messages in GLLibConfig!");
        aI(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        String[] strArr = new String[1];
        strArr[0] = z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : null;
        a(t.Pa, strArr, str);
    }

    public static void b(boolean z, String str) {
        GLLib.c(w.YJ, "You need to enable federation_useHermes_Messages in GLLibConfig!");
        aI(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        String[] strArr = new String[1];
        strArr[0] = z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : null;
        a(t.Pd, strArr, str);
    }

    public static String n(String str, String str2) {
        return a(str, str2, null, null, null, null, null, null);
    }

    public static String a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String[] strArr) {
        String strA = a(a(a("", "from", str, true), "body", str2, true), "reply_to", js(), true);
        if (str4 != null) {
            strA = a(strA, "sound", str4, true);
        }
        if (str5 != null) {
            strA = a(strA, "launch_button", str5, true);
        }
        if (str6 != null) {
            strA = a(strA, MessengerShareContentUtility.ATTACHMENT_TEMPLATE_TYPE, str6, true);
        }
        if (str7 != null) {
            strA = a(strA, "template_args", str7, true);
        }
        if (strArr != null) {
            for (int i = 0; i < strArr.length; i += 2) {
                strA = a(strA, strArr[i], strArr[i + 1], true);
            }
        }
        return strA;
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        GLLib.c(w.YJ, "You need to enable federation_useHermes_Messages in GLLibConfig!");
        aI(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        a(t.Pe, new String[]{str, str2, str3, str4, str5, str6}, str7);
    }

    public static void d(String str, String str2, String str3) {
        GLLib.c(w.YH, "You need to enable federation_useHermes_RegisterDevice in GLLibConfig!");
        aI(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        a(t.Pg, new String[]{str, str2}, str3);
    }

    public static void a(String str, String str2, String str3, String str4) {
        GLLib.c(w.YJ, "You need to enable federation_useHermes_Messages in GLLibConfig!");
        aI(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        a(t.Pi, new String[]{str, str2, str3}, str4);
    }

    public static void e(String str, String str2, String str3) {
        GLLib.c(w.YJ, "You need to enable federation_useHermes_Messages in GLLibConfig!");
        aI(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        a(t.Pj, new String[]{str, str2}, str3);
    }

    public static void b(String str, String str2, String str3, String str4, String str5, String str6) {
        GLLib.c(w.YO, "You need to enable federation_useHestia_Config in GLLibConfig!");
        aI("config");
        a(t.Pz, new String[]{str, str2, str3, str4}, (String[]) null, new String[]{str5}, (String) null, str6);
    }

    private static void aG(String str) {
        if (str == null) {
            Md = "";
        } else if (!aH(str)) {
            Md += str + " ";
        }
    }

    private static void iM() {
        if (w.YT) {
            aG("alert");
        }
        if (w.YR) {
            aG("chat");
        }
        if (w.Yn) {
            aG("social_group");
        }
        if (w.Yo) {
            aG("social_event");
        }
        if (w.Yq) {
            aG("social_trophies");
        }
        if (w.YD) {
            aG("storage_admin");
        }
        if (w.YG) {
            aG("leaderboard_clear");
        }
        if (w.YF) {
            aG("leaderboard");
            aG("social");
        }
        if (w.YE) {
            aG("leaderboard");
        }
        if (w.Yk || w.Yl || w.Ym || w.Yn || w.Yo || w.Yp || w.Yq) {
            aG("social");
        }
        if (w.YB || w.YC) {
            aG("storage");
        }
        if (w.Ys || w.Yv || w.Yx || w.Yt) {
            aG("auth");
        }
        if (w.Yz) {
            aG("approval_mail");
        }
        if (w.YJ || w.YH || w.YI || w.YL) {
            aG(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        }
        if (w.YN) {
            aG("gllive");
        }
        if (w.YO) {
            aG("config");
        }
    }

    private static boolean aH(String str) {
        return Md.indexOf(new StringBuilder().append(str).append(" ").toString()) != -1;
    }

    public static void b(String str, String str2, String str3, String str4, String str5) {
        GLLib.c(w.Ys, "You need to enable federation_useJanus_DeviceInfo in GLLibConfig!");
        aI("auth");
        a(t.No, new String[]{str, str2, str3, str4}, str5);
    }

    public static void f(String str, String str2, String str3) {
        GLLib.c(w.Yv, "You need to enable federation_useJanus_Link in GLLibConfig!");
        aI("auth");
        a(t.Np, new String[]{str, str2, str3}, "");
    }

    public static void aI(String str) {
        a(str, (String) null, (String) null, (String) null, (String) null, (String) null, (String[]) null);
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6, String[] strArr) {
        String[] strArr2 = null;
        GLLib.c(w.Yr, "You need to enable federation_useJanus_Authorize in GLLibConfig!");
        if (jr() == null) {
            GLLib.b("You didn't set device id. Please note that it's optional to set device id (for backward compatiblity) but is required for all new games.", "Federation", 3);
        }
        boolean zAH = aH(str.trim());
        if (iN() && !iO()) {
            Ma = null;
            o(null, Mc);
            return;
        }
        if ((!zAH || strArr != null || str2 != null || Ma == null) && !iO()) {
            if (jr() != null && strArr != null) {
                String[] strArr3 = new String[strArr.length];
                for (int i = 0; i < strArr.length; i++) {
                    if (i % 2 == 0) {
                        if (!strArr[i].startsWith("device_")) {
                            strArr3[i] = "device_" + strArr[i];
                        }
                    } else {
                        strArr3[i] = strArr[i];
                    }
                }
                strArr2 = strArr3;
            }
            if (!zAH) {
                aG(str);
            }
            a(t.Nn, new String[]{js(), ju(), Md.trim(), jr(), str2, str3, str4, str5, str6}, strArr2, "");
        }
    }

    private static final boolean iO() {
        return (cz(t.Nn) == -1 && cz(t.Nt) == -1) ? false : true;
    }

    public static void o(String str, String str2) {
        GLLib.c(w.Yr, "You need to enable federation_useJanus_Authorize in GLLibConfig!");
        a(t.Nt, new String[]{str, "refresh_token", str2}, "");
    }

    private static void iP() {
        if (Mk != null) {
            Mk.disconnect();
            Mk = null;
        }
    }

    private static String ck(int i) {
        byte[] bArrOj;
        int iCC = cC(i);
        if (iCC == t.OF) {
            String str = ((String[]) cv(i))[0];
            if (str == null) {
                c(i, 6, false);
            } else {
                ct(i);
                Mj[6] = str;
            }
        } else {
            ct(i);
        }
        if (I(i, 0)) {
            if (iCC == t.OF) {
                Mk = new bx(aW(Mj[6]), false);
                Mk.connect();
                H(i, 1);
                return null;
            }
            bc bcVar = new bc();
            if (iCC == t.OG) {
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "login");
                bcVar.a("access_token", Me);
                bcVar.a("username", jt());
            } else if (iCC == t.OH) {
                String[] strArr = (String[]) cv(i);
                String str2 = strArr[0];
                String str3 = strArr[1];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "join room");
                bcVar.a("room_id", str2);
                if (str3 != null) {
                    bcVar.a("reservation_code", str3);
                }
            } else if (iCC == t.OI) {
                String[] strArr2 = (String[]) cv(i);
                String str4 = strArr2[0];
                String str5 = strArr2[1];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "leave room");
                bcVar.a("room_id", str4);
                bcVar.a("credential", str5);
            } else if (iCC == t.OL) {
                String str6 = ((String[]) cv(i))[0];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "launch game");
                bcVar.a("room_id", str6);
            } else if (iCC == t.OP) {
                String[] strArr3 = (String[]) cv(i);
                String str7 = strArr3[0];
                String str8 = strArr3[1];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "update user");
                bcVar.a("room_id", str7);
                bcVar.a("credential", str8);
            } else if (iCC == t.OO) {
                String[] strArr4 = (String[]) cv(i);
                String str9 = strArr4[0];
                String str10 = strArr4[1];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "update room");
                bcVar.a("room_id", str9);
                if (str10 != null) {
                    bcVar.a("name", str10);
                }
            } else if (iCC == t.OM) {
                String str11 = ((String[]) cv(i))[0];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "get game controller");
                bcVar.a("room_id", str11);
            } else if (iCC == t.ON) {
                String str12 = ((String[]) cv(i))[0];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "get room info");
                bcVar.a("room_id", str12);
            } else if (iCC == t.OJ) {
                Object[] objArrCv = cv(i);
                String str13 = ((String[]) objArrCv[0])[0];
                String str14 = ((String[]) objArrCv[0])[1];
                String[] strArr5 = (String[]) objArrCv[1];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "create room");
                bcVar.a("name", str13);
                if (!"-1".equals(str14)) {
                    bcVar.a("capacity", str14);
                }
                if (strArr5 != null) {
                    bcVar.a("reservation_codes", strArr5);
                }
            }
            String[] strArrCw = cw(i);
            if (strArrCw != null) {
                for (int i2 = 0; i2 < strArrCw.length; i2 += 2) {
                    if (strArrCw[i2] != null) {
                        bcVar.a(strArrCw[i2], strArrCw[i2 + 1]);
                    }
                }
            }
            Mk.i((bcVar.toString() + "\n").getBytes());
            H(i, 1);
            return null;
        }
        if (!I(i, 1) || (bArrOj = Mk.oj()) == null) {
            return null;
        }
        String str15 = new String(bArrOj);
        if (iCC != t.OO && iCC != t.OP && iCC != t.ON) {
            bc bcVar2 = new bc(str15);
            try {
                if (iCC == t.OF) {
                    str15 = bcVar2.ch("nonce");
                } else if (iCC == t.OJ) {
                    str15 = bcVar2.ch("room_id");
                } else if (iCC == t.OL) {
                    str15 = a(i, bcVar2) ? g(bcVar2.ch("controller_host"), bcVar2.ce("controller_tcp_port")) : null;
                } else if (iCC == t.OM) {
                    if (a(i, bcVar2)) {
                        str15 = f(bcVar2.ch("controller_host"), bcVar2.ce("controller_tcp_port"));
                    }
                } else if (iCC == t.OG || iCC == t.OH || iCC == t.OI) {
                    a(i, bcVar2);
                }
            } catch (Exception e) {
                GLLib.d(e);
                str15 = null;
            }
        }
        if (!I(i, 2)) {
            H(i, 3);
        }
        return str15 == null ? "" : str15;
    }

    private static final boolean a(int i, bc bcVar) throws Exception {
        boolean zCk = bcVar.ck("success");
        if (!zCk) {
            if (w.XZ) {
                GLLib.b("Anubis error.", "Federation", 3);
            }
            String strCh = bcVar.ch("reason");
            n(i, strCh);
            if (w.XZ) {
                GLLib.b("Reason: " + strCh, "Federation", 3);
            }
        }
        return zCk;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:29:0x00b6 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:40:0x00d1  */
    private static String cl(int i) {
        byte[] bArrOj;
        String strCh;
        int iCC = cC(i);
        ct(i);
        Object[] objArrCv = cv(i);
        bx bxVar = (bx) objArrCv[0];
        if (I(i, 0)) {
            if (iCC == t.OS) {
                bxVar.connect();
                H(i, 1);
                return null;
            }
            bc bcVar = new bc();
            if (iCC == t.OT) {
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "login");
                bcVar.a("access_token", Me);
                bcVar.a("username", jt());
            } else if (iCC == t.OU) {
                String str = (String) objArrCv[1];
                bcVar.a(NativeProtocol.WEB_DIALOG_ACTION, "connect game");
                bcVar.a("room_id", str);
            }
            bxVar.i((bcVar.toString() + "\n").getBytes());
            H(i, 1);
            return null;
        }
        if (!I(i, 1) || (bArrOj = bxVar.oj()) == null) {
            return null;
        }
        bc bcVar2 = new bc(new String(bArrOj));
        try {
            if (iCC == t.OS) {
                if (a(i, bcVar2)) {
                    strCh = bcVar2.ch("nonce");
                } else {
                    strCh = null;
                }
                if (!I(i, 2)) {
                    H(i, 3);
                }
                if (strCh == null) {
                    return "";
                }
                return strCh;
            }
            if (iCC == t.OG || iCC == t.OT || iCC == t.OU) {
                a(i, bcVar2);
            }
        } catch (Exception e) {
            GLLib.d(e);
        }
        strCh = null;
        if (!I(i, 2)) {
            H(i, 3);
        }
        if (strCh == null) {
            return "";
        }
        return strCh;
    }

    public static void iQ() {
        GLLib.c(w.YM, "You need to enable federation_useServerTimestamp in GLLibConfig!");
        a(t.OZ, (Object[]) null, "");
    }

    private static long cm(int i) {
        c(i, 2, true);
        if (!I(i, 0)) {
            return -1L;
        }
        H(i, 3);
        return iR();
    }

    public static long iR() {
        return Mm;
    }

    public static boolean cn(int i) {
        return Mh[i] != 0;
    }

    public static String b(String str, int i, int i2) {
        String strD;
        am amVarCr = cr(i);
        if (Mh[i2] == 0) {
            a(str, amVarCr);
            Mh[i2] = 1;
        }
        if (Mh[i2] == 1) {
            if (b(amVarCr) && (strD = d(amVarCr)) != null) {
                Na[i2] = 0;
                Mh[i2] = 0;
                return strD;
            }
            if (cu(i)) {
                Mh[i2] = 0;
                if (w.XZ) {
                    GLLib.b("Federation ERROR: Can't find service location " + str, "Federation", 3);
                    GLLib.b("Error code: " + jl(), "Federation", 3);
                    GLLib.b("Error message: " + jm(), "Federation", 3);
                }
            }
        }
        return null;
    }

    private static boolean a(String str, am amVar) {
        String strIV = iV();
        if (strIV == null || strIV.length() == 0) {
            if (w.XZ) {
                GLLib.b("Get pandora url error", "Federation", 3);
            }
            return false;
        }
        String str2 = strIV + "/locate/" + str;
        if ("alert".equals(str)) {
            str2 = str2 + "?client_id=" + bc(gX()) + "&credential=" + bc(js());
        }
        a(str2, "", 0, amVar);
        return true;
    }

    public static void a(String str, String str2, String str3, boolean z, String str4, String str5) {
        a(str, str2, str3, z, str4, str5, "");
    }

    public static void c(String str, String str2, String str3, String str4, String str5) {
        d(str, str2, str3, str4, str5, "");
    }

    public static void c(String str, String str2, String str3, String str4, String str5, String str6) {
        b(str, str2, str3, str4, str5, str6, "");
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String[] strArr) {
        a(str, str2, str3, str4, str5, str6, str7, str8, str9, strArr, "");
    }

    public static void p(String str, String str2) {
        g(str, str2, "");
    }

    public static void a(String str, String str2, String str3, boolean z, String str4, String str5, String str6) {
        GLLib.c(w.YE, "You need to enable federation_useOlympus_MyScores in GLLibConfig!");
        GLLib.c((z && str4 == null) || !z, "Don't use aroundMe and aroundEntry at the same time, it have no sense");
        aI("leaderboard");
        String[] strArr = new String[6];
        strArr[0] = str5;
        strArr[1] = str;
        strArr[2] = z ? "me" : null;
        strArr[3] = str4;
        strArr[4] = str2;
        strArr[5] = str3;
        a(t.OV, strArr, str6);
    }

    public static void d(String str, String str2, String str3, String str4, String str5, String str6) {
        GLLib.c(w.YF, "You need to enable federation_useOlympus_FriendScores in GLLibConfig!");
        aI("leaderboard social");
        a(t.OW, new String[]{str5, str, str2, str3, str4}, str6);
    }

    public static void b(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        GLLib.c(w.YE, "You need to enable federation_useOlympus_MyScores in GLLibConfig!");
        a(str, str2, "me", str3, null, str4, null, str5, str6, null, str7);
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String[] strArr, String str10) {
        GLLib.c(w.YE, "You need to enable federation_useOlympus_MyScores in GLLibConfig!");
        GLLib.c(str8 == null || str9 == null, "Don't use aroundMe and aroundEntry at the same time, it have no sense");
        aI("leaderboard");
        a(t.OX, new String[]{str2, str, str3, str4, str5, str6, str7, str8, str9}, strArr, str10);
    }

    public static void g(String str, String str2, String str3) {
        GLLib.c(w.YG, "You need to enable federation_useOlympus_ClearLeaderboard in GLLibConfig!");
        aI("leaderboard_clear");
        a(t.OY, new String[]{str2, str}, str3);
    }

    public static String iS() {
        return Mx;
    }

    public static void aJ(String str) {
        Mx = str;
    }

    public static void aK(String str) {
        Mz = str;
        Mw = 0;
        My = new am(false);
        My.bP("Eve");
    }

    private static String iT() {
        if (Mw == 0) {
            a(aL(null) + "/datacenters", "", 0, My);
            Mw = 1;
        }
        if (Mw != 1) {
            return null;
        }
        if (a(My)) {
            Mw = -1;
            return "";
        }
        if (My.BM == null) {
            return null;
        }
        Mw = -1;
        return My.BM;
    }

    private static void iU() {
        Mo = aL(Mx);
        a(t.NA, (Object[]) null, "");
    }

    private static String co(int i) {
        String strD;
        am amVarCr = cr(i);
        ct(i);
        if (I(i, 0)) {
            a(Mo, "", 0, amVarCr);
            H(i, 1);
        } else if (I(i, 1) && b(amVarCr) && (strD = d(amVarCr)) != null) {
            MZ[i] = 0;
            bc bcVar = new bc(strD);
            try {
                Mn = bcVar.ch("pandora");
                Mp = bcVar.ch("gllive-ope") + "social_player.php";
                Mq = bcVar.ch("tapjoy");
                Mr = bcVar.ch("offline_items");
                Ms = bcVar.ch("game_portal");
                Mt = bcVar.ch(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS);
                Mu = bcVar.ch("crm_iap");
                Mv = bcVar.ch("ads_agency");
            } catch (Exception e) {
            }
            H(i, 3);
            return strD;
        }
        return null;
    }

    public static String iV() {
        return Mn;
    }

    public static String iW() {
        GLLib.b("Federation EVE => status of clientID : " + Mt, "Federation", 3);
        return Mt;
    }

    public static void aM(String str) {
        r(str, "");
    }

    public static void aN(String str) {
        q(str, "");
    }

    public static void aO(String str) {
        s(str, "");
    }

    public static void q(String str, String str2) {
        a(t.Pt, new String[]{str}, str2);
    }

    public static void r(String str, String str2) {
        a(t.Ps, new String[]{str}, str2);
    }

    public static void h(String str, String str2, String str3) {
        a(t.Pu, new String[]{str, str2}, str3);
    }

    private static String cp(int i) {
        String strD;
        am amVarCr = cr(i);
        ct(i);
        if (I(i, 0)) {
            a(((String[]) cv(i))[0], "", 0, amVarCr);
            H(i, 1);
        }
        if (!I(i, 1) || !b(amVarCr) || (strD = d(amVarCr)) == null) {
            return null;
        }
        MZ[i] = 0;
        H(i, 3);
        return strD;
    }

    private static String cq(int i) {
        am amVarCr = cr(i);
        ct(i);
        if (I(i, 0)) {
            Object[] objArr = MH[cC(i)];
            int iIntValue = ((Integer) objArr[0]).intValue();
            String str = LT[iIntValue];
            boolean zBooleanValue = ((Boolean) objArr[1]).booleanValue();
            int iIntValue2 = ((Integer) objArr[2]).intValue();
            if (str != null) {
                c(i, iIntValue, false);
            } else {
                ct(i);
            }
            String str2 = g(Mj[iIntValue], zBooleanValue) + "/assets/" + bc(gX()) + "/" + ((String[]) cv(i))[0];
            amVarCr.am(true);
            a(str2, "", iIntValue2, amVarCr);
            H(i, 1);
        }
        if (I(i, 1) && c(amVarCr)) {
            amVarCr.am(false);
            GLLib.b("Iris_DownloadAsset_Exec:Result", "Federation", 3);
            byte[] bArrE = e(amVarCr);
            String[] strArr = (String[]) cv(i);
            if (bArrE != null) {
                MZ[i] = 0;
                H(i, 3);
                try {
                    b(strArr[1], bArrE);
                } catch (Exception e) {
                    GLLib.d(e);
                }
                return strArr[0];
            }
        }
        return null;
    }

    public static void s(String str, String str2) {
        a(t.Pr, new String[]{str}, str2);
    }

    private static boolean b(String str, byte[] bArr) throws Exception {
        try {
            File file = new File(str);
            GLLib.b("FilePath:" + file.getPath(), "Federation", 3);
            if (!file.exists()) {
                GLLib.b("Not Exist", "Federation", 3);
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr, 0, bArr.length);
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void e(String str, String str2, String str3, String str4, String str5, String str6) {
        c(str, str2, str3, str4, str5, str6, "");
    }

    public static void i(String str, String str2, String str3) {
        b(str, str2, str3, "");
    }

    public static void c(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        GLLib.c(w.Yk, "You need to enable federation_useOsiris_Connections in GLLibConfig!");
        aI("social");
        a(t.NF, new String[]{str, str2, str3, str4, str5, str6}, str7);
    }

    public static void b(String str, String str2, String str3, String str4) {
        GLLib.c(w.Yk, "You need to enable federation_useOsiris_Connections in GLLibConfig!");
        aI("social");
        a(t.Oc, new String[]{str, str2, str3}, str4);
    }

    public static void t(String str, String str2) {
        GLLib.c(w.YB, "You need to enable federation_useSeshat_Storage in GLLibConfig!");
        aI("storage");
        a(t.Ou, new String[]{str}, str2);
    }

    public static void d(String str, String str2, String str3, String str4, String str5) {
        GLLib.c(w.YB, "You need to enable federation_useSeshat_Storage in GLLibConfig!");
        aI("storage");
        a(t.Ot, new String[]{str, str2, str3, str4}, str5);
    }

    public static void j(String str, String str2, String str3) {
        c(str, str2, null, str3);
    }

    public static void c(String str, String str2, String str3, String str4) {
        GLLib.c(w.YB, "You need to enable federation_useSeshat_Storage in GLLibConfig!");
        aI("storage");
        a(t.Os, new String[]{str, str2}, (String[]) null, (String[]) null, str3, str4);
    }

    public static void f(String str, String str2, String str3, String str4, String str5, String str6) {
        GLLib.c(w.YC, "You need to enable federation_useSeshat_StandardProfile in GLLibConfig!");
        aI("storage");
        a(t.Ov, new String[]{str, str2, str3, str4, str5}, str6);
    }

    public static void d(String str, String str2, String str3, String str4) {
        GLLib.c(w.YC, "You need to enable federation_useSeshat_StandardProfile in GLLibConfig!");
        aI("storage");
        a(t.Ow, new String[]{str, str2, str3}, str4);
    }

    public static void k(String str, String str2, String str3) {
        GLLib.c(w.YC, "You need to enable federation_useSeshat_StandardProfile in GLLibConfig!");
        aI("storage");
        a(t.Ox, new String[]{str, str2}, str3);
    }

    public static void aP(String str) {
        GLLib.c(w.YC, "You need to enable federation_useSeshat_StandardProfile in GLLibConfig!");
        aI("storage");
        a(t.Oy, (Object[]) null, str);
    }

    public static void u(String str, String str2) {
        GLLib.c(w.YC, "You need to enable federation_useSeshat_StandardProfile in GLLibConfig!");
        aI("storage");
        a(t.Oz, new String[]{str}, str2);
    }

    public static void e(String str, String str2, String str3, String str4) {
        d(str, str2, str3, str4, "");
    }

    public static void v(String str, String str2) {
        j(str, str2, "");
    }

    public static void e(String str, String str2, String str3, String str4, String str5) {
        f(str, str2, str3, str4, str5, "");
    }

    public static void w(String str, String str2) {
        k(str, str2, "");
    }

    public static void iX() {
        aP("");
    }

    public static void aQ(String str) {
        u(str, "");
    }

    public static String iY() {
        return ad(true);
    }

    public static String ad(boolean z) {
        String strKL;
        String str;
        String strPx = "";
        int iOR = com.gameloft.android.wrapper.au.OR();
        String packageName = com.gameloft.android.wrapper.au.getPackageName();
        String strSubstring = packageName.substring("com.gameloft.android.".length(), packageName.length());
        String str2 = strSubstring + "_fed_anon_name";
        if (!w.Yj) {
            strKL = GLLib.kL();
        } else {
            if (iOR == 2) {
                if (com.gameloft.android.wrapper.e.getContext() == null) {
                    com.gameloft.android.wrapper.e.init(com.gameloft.android.wrapper.au.getContext());
                }
                String strEc = com.gameloft.android.wrapper.e.ec(str2);
                if (strEc == null || strEc.equals("")) {
                    strPx = com.gameloft.android.wrapper.au.Os();
                } else {
                    return strEc;
                }
            } else if (iOR != 1 || !z) {
                if ("".equals("")) {
                    strPx = com.gameloft.android.wrapper.au.getAndroidId();
                }
                if (strPx.equals("")) {
                    strPx = com.gameloft.android.wrapper.au.getSerial();
                }
                if (strPx.equals("")) {
                    strPx = com.gameloft.android.wrapper.au.OP();
                }
                if (strPx.equals("")) {
                    strPx = com.gameloft.android.wrapper.au.Px();
                }
            } else {
                strPx = com.gameloft.android.wrapper.au.Os();
            }
            strKL = strPx + strSubstring;
        }
        GLLib.kM();
        try {
            GLLib.A(strKL, (String) null);
            String strSubstring2 = GLLib.kP().substring(0, 16);
            if (iOR == 2) {
                String str3 = "android_v2_" + strSubstring + "_";
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                }
                str = (str3 + String.valueOf(jCurrentTimeMillis / 1000)) + "_" + strSubstring2;
            } else {
                str = "android_" + strSubstring2;
            }
            String strE = GLLib.e(str.getBytes());
            if (iOR == 2) {
                com.gameloft.android.wrapper.e.S(str2, strE);
                return strE;
            }
            return strE;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String aR(String str) {
        String str2 = "";
        for (int i = 0; i < 16; i++) {
            if (i < str.length()) {
                str2 = str2 + ((char) ((str.charAt(i) % 25) + 97));
            }
        }
        return str2;
    }

    private static void iZ() {
        Ml = new am[w.Yf];
        Mg = new int[w.Yf];
        MZ = new int[w.Yf];
        Mh = new int[13];
        Na = new int[13];
        Mj = new String[13];
        Mi = new long[13];
        for (int i = 0; i < Ml.length; i++) {
            Ml[i] = new am(false);
            H(i, 3);
            Mh[i] = 0;
        }
    }

    private static void ja() {
        MH = new Object[t.max][];
        MI = new String[t.max][];
        MJ = new String[t.max][];
        Boolean bool = Boolean.TRUE;
        Boolean bool2 = Boolean.FALSE;
        Integer num = new Integer(0);
        Integer num2 = new Integer(1);
        Integer num3 = new Integer(2);
        MH[t.Qc] = new Object[]{null, bool2, num, bool2};
        if (w.YT) {
            int i = t.Qd;
            MH[i] = new Object[]{new Integer(12), bool2, num, bool2};
            MI[i] = new String[]{"alerts", "me"};
            MJ[i] = new String[]{"access_token", "content_type", "push_method", "alert_types"};
        }
        if (w.YQ) {
            int i2 = t.PD;
            MH[i2] = new Object[]{new Integer(11), bool2, num, bool2};
            MJ[i2] = new String[]{NativeProtocol.WEB_DIALOG_ACTION, "ggi", "domain", "port", "type", "connectionstatus", "mac_addr", "game_version"};
        }
        if (w.YR) {
            MI[t.PF] = new String[]{"<presence to='", null, "@conference.pegasus/", "'>", "<x xmlns='http://jabber.org/protocol/muc'>", "<history maxchars='0'/></x>", "</presence>"};
        }
        if (w.YR) {
            MI[t.PG] = new String[]{"<presence to='", null, "@conference.pegasus/", null, "'>", "<x xmlns='http://jabber.org/protocol/muc'>", "<history maxchars='0'/></x>", "</presence>"};
        }
        if (w.YR) {
            MI[t.PH] = new String[]{"<message from='", null, "@pegasus' ", "to='", null, "@pegasus' type='chat' xml:lang='en'>", "<body>", null, "</body></message>"};
        }
        if (w.YR) {
            MI[t.PI] = new String[]{"<message to='", null, "@conference.pegasus' ", "type='groupchat' xml:lang='en'>", "<body>", null, "</body></message>"};
        }
        if (w.YR) {
            MI[t.PJ] = new String[]{"<message to='", null, "@conference.pegasus/", null, "' ", "type='chat' xml:lang='en'>", "<body>", null, "</body></message>"};
        }
        if (w.YR) {
            MI[t.PK] = new String[]{"<iq to='", null, "@conference.pegasus' type='set'>", "<query xmlns='http://jabber.org/protocol/muc#owner'>", "<destroy jid='", null, "@conference.pegasus'/></query></iq>"};
        }
        if (w.YR) {
            MI[t.PL] = new String[]{"<presence to='", null, "@conference.pegasus' type='unavailable'/>"};
        }
        if (w.YR) {
            MI[t.PM] = new String[]{"<iq to='", null, "@conference.pegasus' type='set'>", "<query xmlns='http://jabber.org/protocol/muc#admin'>", "<item affiliation='member' jid='", null, "@pegasus'/>", "</query></iq>"};
        }
        if (w.YR) {
            MI[t.PN] = new String[]{"<iq to='", null, "@conference.pegasus' type='set'>", "<query xmlns='http://jabber.org/protocol/muc#admin'>", "<item affiliation='none' jid='", null, "@pegasus'/>", "</query></iq>"};
        }
        if (w.YR) {
            MI[t.PO] = new String[]{"<iq to='", null, "@conference.pegasus' type='get'>", "<query xmlns='http://jabber.org/protocol/muc#admin'>", "<item affiliation='member'/></query></iq>"};
        }
        if (w.YR) {
            MI[t.PP] = new String[]{"<message to='", null, "@conference.pegasus'>", "<x xmlns='http://jabber.org/protocol/muc#user'>", "<invite to='", null, "@pegasus'/></x></message>"};
        }
        if (w.YR) {
            MI[t.PQ] = new String[]{"<message to='", null, "@conference.pegasus'>", "<x xmlns='http://jabber.org/protocol/muc#user'>", "<decline to='", null, "@pegasus'/></x></message>"};
        }
        if (w.YR) {
            MI[t.PR] = new String[]{"<iq to='", null, "@conference.pegasus' type='get'>", "<query xmlns='http://jabber.org/protocol/muc#owner'/></iq>"};
        }
        if (w.YR) {
            MI[t.PS] = new String[]{"<iq type='set' to='", null, "@conference.pegasus'>", "<query xmlns='http://jabber.org/protocol/muc#owner'><x xmlns='jabber:x:data' type='submit'/>", "</query></iq>"};
        }
        if (w.YR) {
            MI[t.PT] = new String[]{"<iq to='", null, "@conference.pegasus' type='set'>", "<query xmlns='http://jabber.org/protocol/muc#owner'>", "<x xmlns='jabber:x:data' type='submit'>", "<field type='hidden' var='FORM_TYPE'>", "<value>http://jabber.org/protocol/muc#roomconfig</value></field>", "<field var='muc#roomconfig_roomname'><value>ChatRoom</value></field>", "<field var='muc#roomconfig_roomdesc'><value>This is Test room</value></field>", "<field var='muc#roomconfig_persistentroom'><value>1</value></field>", "<field var='muc#roomconfig_publicroom'><value>1</value></field>", "<field var='muc#roomconfig_maxusers'><value>200</value></field>", "<field var='muc#roomconfig_whois'><value>moderators</value></field>", "<field var='muc#roomconfig_membersonly'><value>1</value></field>", "<field var='muc#roomconfig_moderatedroom'><value>1</value></field>", "<field var='muc#roomconfig_changesubject'><value>1</value></field>", "<field var='muc#roomconfig_allowinvites'><value>0</value></field>", "<field var='muc#roomconfig_allowvisitorstatus'><value>1</value></field>", "<field var='muc#roomconfig_allowvisitornickchange'><value>0</value></field>", "</x></query></iq>"};
        }
        if (w.YR) {
            MI[t.PU] = new String[]{"<iq to='", null, "@conference.pegasus' type='set'>", "<query xmlns='http://jabber.org/protocol/muc#admin'>", "<item nick='", null, "' role='none'/></query></iq>"};
        }
        if (w.YR) {
            MI[t.PV] = new String[]{"<iq to='", null, "@conference.pegasus' type='get'>", "<query xmlns='jabber:iq:register'/></iq>"};
        }
        if (w.YR) {
            MI[t.PW] = new String[]{"<iq to='", null, "@conference.pegasus' type='set'>", "<x xmlns='jabber:x:data' type='submit'>", "<field var='FORM_TYPE' type='hidden'><value>http://jabber.org/protocol/muc#register</value></field>", "<field var='muc#register_allow'><value>", null, "</value></field>", "</x></query></iq>"};
        }
        if (w.YR) {
            MI[t.PX] = new String[]{"<iq to='", null, "@conference.pegasus' type='set'>", "<query xmlns='http://jabber.org/protocol/muc#admin'>", "<item affiliation='admin' jid='", null, "@pegasus'/>", "</query></iq>"};
        }
        if (w.YR) {
            MI[t.PY] = new String[]{"<iq to='", null, "@conference.pegasus' type='set'>", "<query xmlns='http://jabber.org/protocol/muc#admin'>", "<item affiliation='member' jid='", null, "@pegasus'/>", "</query></iq>"};
        }
        if (w.YR) {
            MI[t.PZ] = new String[]{"<presence to='", null, "@pegasus' xml:lang='en'><show>away</show>", "<status>", null, "</status>", "<priority>1</priority></presence>"};
        }
        if (w.YR) {
            MI[t.Qa] = new String[]{"</stream:stream>"};
        }
        if (w.YO) {
            int i3 = t.Pz;
            MH[i3] = new Object[]{new Integer(9), bool, num, bool2};
            MI[i3] = new String[]{"configs", "users", "me", null};
            MJ[i3] = new String[]{"access_token", "profile_name", "clusters_space", "now"};
        }
        if (w.YO) {
            int i4 = t.PA;
            MH[i4] = new Object[]{new Integer(9), bool, num2, bool2};
            MI[i4] = new String[]{"configs", "clusters", null, null, null};
            MJ[i4] = new String[]{"clusters_space", "line", "access_token", "end_date", "start_date", "campaign_name", "is_enable"};
        }
        if (w.YO) {
            int i5 = t.PB;
            MH[i5] = new Object[]{new Integer(9), bool, num, bool2};
            MI[i5] = new String[]{"configs", "clusters", null, null, null};
            MJ[i5] = new String[]{"clusters_space", "access_token"};
        }
        if (w.YO) {
            int i6 = t.PC;
            MH[i6] = new Object[]{new Integer(9), bool, num, bool2};
            MI[i6] = new String[]{"configs"};
            MJ[i6] = new String[]{"clusters_space", "access_token", "campaign_name"};
        }
        if (w.YN) {
            int i7 = t.Pp;
            MH[i7] = new Object[]{new Integer(8), bool, num2, bool2};
            MI[i7] = new String[]{"users"};
            MJ[i7] = new String[]{"username", "password", "email"};
        }
        if (w.YP) {
            int i8 = t.OE;
            MH[i8] = new Object[]{new Integer(7), bool, num, bool2};
            MI[i8] = new String[]{"rooms", null};
            MJ[i8] = new String[]{"&&"};
        }
        if (w.YJ) {
            int i9 = t.Pa;
            MH[i9] = new Object[]{new Integer(0), bool, num, bool2};
            MI[i9] = new String[]{"messages", "secured", "me"};
            MJ[i9] = new String[]{"access_token", "delete"};
        }
        if (w.YJ) {
            int i10 = t.Pb;
            MH[i10] = new Object[]{new Integer(0), bool, num, bool2};
            MI[i10] = new String[]{"messages", "secured", "me", null};
            MJ[i10] = new String[]{"access_token"};
        }
        if (w.YJ) {
            int i11 = t.Pd;
            MH[i11] = new Object[]{new Integer(0), bool, num, bool2};
            MI[i11] = new String[]{"messages", "inbox", "me"};
            MJ[i11] = new String[]{"access_token", "delete"};
        }
        if (w.YJ) {
            int i12 = t.Pc;
            MH[i12] = new Object[]{new Integer(0), bool, num3, bool2};
            MI[i12] = new String[]{"messages", "secured", "me", null};
            MJ[i12] = new String[]{"access_token"};
        }
        if (w.YJ) {
            int i13 = t.Pe;
            MH[i13] = new Object[]{new Integer(0), bool, num2, bool2};
            MI[i13] = new String[]{"messages", null, null};
            MJ[i13] = new String[]{"access_token", "&&", "replace_label", "delay", MessengerShareContentUtility.ATTACHMENT_PAYLOAD};
        }
        if (w.YH) {
            int i14 = t.Pf;
            MH[i14] = new Object[]{new Integer(0), bool, num, bool2};
            MI[i14] = new String[]{"transports", null, "endpoints"};
            MJ[i14] = new String[]{"access_token"};
        }
        if (w.YH) {
            int i15 = t.Pg;
            MH[i15] = new Object[]{new Integer(0), bool, num2, bool2};
            MI[i15] = new String[]{"transports", null, "endpoints", null};
            MJ[i15] = new String[]{"access_token"};
        }
        if (w.YI) {
            int i16 = t.Ph;
            MH[i16] = new Object[]{new Integer(0), bool, num2, bool2};
            MI[i16] = new String[]{"transports", null, "endpoints", "delete"};
            MJ[i16] = new String[]{"access_token"};
        }
        if (w.YJ) {
            int i17 = t.Pi;
            MH[i17] = new Object[]{new Integer(0), bool, num2, bool2};
            MI[i17] = new String[]{"messages", "inbox", "multicast"};
            MJ[i17] = new String[]{"access_token", "credentials", "&&", MessengerShareContentUtility.ATTACHMENT_PAYLOAD};
        }
        if (w.YJ) {
            int i18 = t.Pj;
            MH[i18] = new Object[]{new Integer(0), bool, num, bool2};
            MI[i18] = new String[]{"messages", null, "me", null};
            MJ[i18] = new String[]{"access_token"};
        }
        if (w.YJ) {
            int i19 = t.Pk;
            MH[i19] = new Object[]{new Integer(0), bool, num3, bool2};
            MI[i19] = new String[]{"messages", null, "me", null};
            MJ[i19] = new String[]{"access_token"};
        }
        if (w.YJ) {
            int i20 = t.Pl;
            MH[i20] = new Object[]{new Integer(0), bool, num3, bool2};
            MI[i20] = new String[]{"messages", null, "me"};
            MJ[i20] = new String[]{"access_token", "msgids"};
        }
        if (w.YL) {
            int i21 = t.Pm;
            MH[i21] = new Object[]{new Integer(0), bool, num2, bool2};
            MI[i21] = new String[]{"lists", null, "me"};
            MJ[i21] = new String[]{"access_token"};
        }
        if (w.YL) {
            int i22 = t.Pn;
            MH[i22] = new Object[]{new Integer(0), bool, num2, bool2};
            MI[i22] = new String[]{"lists", null, "me", "unsubscribe"};
            MJ[i22] = new String[]{"access_token"};
        }
        if (w.YL) {
            int i23 = t.Po;
            MH[i23] = new Object[]{new Integer(0), bool, num, bool2};
            MI[i23] = new String[]{"lists", "me"};
            MJ[i23] = new String[]{"access_token"};
        }
        if (w.Yr) {
            int i24 = t.Nm;
            MH[i24] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i24] = new String[]{"authorize"};
            MJ[i24] = new String[]{"clientId", "username", "password", "scope", "device_id", "access_token_only"};
        }
        if (w.Yr) {
            int i25 = t.Nn;
            MH[i25] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i25] = new String[]{"authorize"};
            MJ[i25] = new String[]{"clientId", "username", "password", "scope", "device_id", "access_token_only", "for_username", "for_credential_type", "grant_type", ServerProtocol.DIALOG_PARAM_REDIRECT_URI};
        }
        if (w.Ys) {
            int i26 = t.No;
            MH[i26] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i26] = new String[]{"devices", "mydevice"};
            MJ[i26] = new String[]{"access_token", "language", "carrier", UserDataStore.COUNTRY, "model"};
        }
        if (w.Yv) {
            int i27 = t.Np;
            MH[i27] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i27] = new String[]{"users", "me", "credentials"};
            MJ[i27] = new String[]{"access_token", "credential", "password", "if_exists"};
        }
        if (w.Yv) {
            int i28 = t.Nq;
            MH[i28] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i28] = new String[]{"users", "me", "credentials", null, "unlink"};
            MJ[i28] = new String[]{"access_token"};
        }
        if (w.Yx) {
            int i29 = t.Nr;
            MH[i29] = new Object[]{new Integer(2), bool, num, bool2};
            MI[i29] = new String[]{"users", "me"};
            MJ[i29] = new String[]{"access_token"};
        }
        if (w.Yx) {
            int i30 = t.Ns;
            MH[i30] = new Object[]{new Integer(2), bool, num, bool2};
            MI[i30] = new String[]{"users", null};
            MJ[i30] = new String[]{"access_token"};
        }
        if (w.Yr) {
            int i31 = t.Nt;
            MH[i31] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i31] = new String[]{"authorize"};
            MJ[i31] = new String[]{"clientId", "scope", "grant_type", "refresh_token"};
        }
        if (w.Yr) {
            int i32 = t.Nu;
            MH[i32] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i32] = new String[]{"encrypt_token"};
            MJ[i32] = new String[]{"access_token", "nonce"};
        }
        if (w.Yr) {
            int i33 = t.Nv;
            MH[i33] = new Object[]{new Integer(2), bool, num, bool2};
            MI[i33] = new String[]{null, "authenticate"};
            MJ[i33] = new String[]{"password"};
        }
        if (w.Yt) {
            int i34 = t.Nw;
            MH[i34] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i34] = new String[]{"games", "mygame", "alias"};
            MJ[i34] = new String[]{"access_token"};
        }
        if (w.Yt) {
            int i35 = t.Nx;
            MH[i35] = new Object[]{new Integer(2), bool, num, bool2};
            MI[i35] = new String[]{"games", "mygame", "alias", null};
            MJ[i35] = new String[]{"access_token"};
        }
        if (w.Yz) {
            int i36 = t.Ny;
            MH[i36] = new Object[]{new Integer(2), bool, num2, bool2};
            MI[i36] = new String[]{"users", "me", "approvals", null, ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID};
            MJ[i36] = new String[]{"access_token", "recipient"};
        }
        if (w.Yz) {
            int i37 = t.Nz;
            MH[i37] = new Object[]{new Integer(2), bool, num, bool2};
            MI[i37] = new String[]{"users", "me", "approvals", null, "set"};
            MJ[i37] = new String[]{"access_token", "granted"};
        }
        int i38 = t.Pr;
        MH[i38] = new Object[]{new Integer(1), bool, num, bool2};
        MI[i38] = new String[]{"assets", "clientId", null, "url"};
        int i39 = t.Pv;
        MH[i39] = new Object[]{new Integer(1), bool, num, bool2};
        MI[i39] = new String[]{"assets", "clientId", null, "metadata"};
        int i40 = t.Pw;
        MH[i40] = new Object[]{new Integer(1), bool, num2, bool2};
        MI[i40] = new String[]{"assets", "clientId", null};
        MJ[i40] = new String[]{"access_token", "override", "onlyThisClient", ShareConstants.WEB_DIALOG_PARAM_DATA};
        int i41 = t.Pt;
        MH[i41] = new Object[]{new Integer(1), bool, num, bool2};
        MI[i41] = new String[]{"assets", "clientId", null};
        int i42 = t.Pu;
        MH[i42] = new Object[]{new Integer(1), bool, num, bool2};
        MI[i42] = new String[]{"assets", "clientId", null};
        int i43 = t.Px;
        MH[i43] = new Object[]{new Integer(1), bool, num, bool2};
        MI[i43] = new String[]{"coupons", "clientId", null};
        int i44 = t.Py;
        MH[i44] = new Object[]{new Integer(1), bool, num2, bool2};
        MI[i44] = new String[]{"coupons", "clientId"};
        MJ[i44] = new String[]{"num", "length", "uses", ShareConstants.WEB_DIALOG_PARAM_DATA, "access_token"};
        if (w.YE) {
            int i45 = t.OV;
            MH[i45] = new Object[]{new Integer(3), bool, num, bool2};
            MI[i45] = new String[]{"leaderboards", null, null, null, null};
            MJ[i45] = new String[]{"access_token", "offset", "limit"};
        }
        if (w.YF) {
            int i46 = t.OW;
            MH[i46] = new Object[]{new Integer(3), bool, num, bool2};
            MI[i46] = new String[]{"leaderboards", null, null, "me", NativeProtocol.AUDIENCE_FRIENDS};
            MJ[i46] = new String[]{"access_token", "offset", "limit", "connection_type"};
        }
        if (w.YE) {
            int i47 = t.OX;
            MH[i47] = new Object[]{new Integer(3), bool, num2, bool2};
            MI[i47] = new String[]{"leaderboards", null, null, null};
            MJ[i47] = new String[]{"access_token", "score", "replace_score_if", "display_name", "credential", "expiration_date", "expiration_duration"};
        }
        if (w.YG) {
            int i48 = t.OY;
            MH[i48] = new Object[]{new Integer(3), bool, num2, bool2};
            MI[i48] = new String[]{"leaderboards", null, null, "clear"};
            MJ[i48] = new String[]{"access_token"};
        }
        if (w.YB) {
            int i49 = t.Ou;
            MH[i49] = new Object[]{new Integer(5), bool, num2, bool2};
            MI[i49] = new String[]{ShareConstants.WEB_DIALOG_PARAM_DATA, "me", null, "delete"};
            MJ[i49] = new String[]{"access_token"};
        }
        if (w.YB) {
            int i50 = t.Os;
            MH[i50] = new Object[]{new Integer(5), bool, num, bool2};
            MI[i50] = new String[]{ShareConstants.WEB_DIALOG_PARAM_DATA, null, null};
            MJ[i50] = new String[]{"access_token"};
        }
        if (w.YB) {
            int i51 = t.Ot;
            MH[i51] = new Object[]{new Integer(5), bool, num2, bool2};
            MI[i51] = new String[]{ShareConstants.WEB_DIALOG_PARAM_DATA, null, null};
            MJ[i51] = new String[]{"access_token", ShareConstants.WEB_DIALOG_PARAM_DATA, "visibility"};
        }
        if (w.YC) {
            int i52 = t.Ov;
            MH[i52] = new Object[]{new Integer(5), bool, num2, bool2};
            MI[i52] = new String[]{"profiles", null, "myprofile", null};
            MJ[i52] = new String[]{"access_token", "object", "visibility", "operation"};
        }
        if (w.YC) {
            int i53 = t.Ow;
            MH[i53] = new Object[]{new Integer(5), bool, num, bool2};
            MI[i53] = new String[]{"profiles", null, "myprofile", null};
            MJ[i53] = new String[]{"access_token", "include_fields"};
        }
        if (w.YC) {
            int i54 = t.Ox;
            MH[i54] = new Object[]{new Integer(5), bool, num, bool2};
            MI[i54] = new String[]{"profiles"};
            MJ[i54] = new String[]{"access_token", "credentials", "include_fields"};
        }
        if (w.YC) {
            int i55 = t.Oy;
            MH[i55] = new Object[]{new Integer(5), bool, num2, bool2};
            MI[i55] = new String[]{"profiles", "me", "myprofile", "delete"};
            MJ[i55] = new String[]{"access_token"};
        }
        if (w.YC) {
            int i56 = t.Oz;
            MH[i56] = new Object[]{new Integer(5), bool, num2, bool2};
            MI[i56] = new String[]{"profiles", "me", "myprofile", "visibility"};
            MJ[i56] = new String[]{"access_token", "visibility"};
        }
        if (w.YC) {
            int i57 = t.OA;
            MH[i57] = new Object[]{new Integer(5), bool, num, bool2};
            MI[i57] = new String[]{"profiles", "me", "myprofile", "visibility"};
            MJ[i57] = new String[]{"access_token"};
        }
        if (w.YC) {
            int i58 = t.OB;
            MH[i58] = new Object[]{new Integer(5), bool, num, bool2};
            MI[i58] = new String[]{"profiles", "matchers", null, "matches"};
            MJ[i58] = new String[]{"access_token", "limit", "include_fields"};
        }
        if (w.YD) {
            int i59 = t.OC;
            MH[i59] = new Object[]{new Integer(5), bool, num2, bool2};
            MI[i59] = new String[]{"profiles", "matchers", null};
            MJ[i59] = new String[]{"access_token", "condition"};
        }
        if (w.YD) {
            int i60 = t.OD;
            MH[i60] = new Object[]{new Integer(5), bool, num2, bool2};
            MI[i60] = new String[]{"profiles", "matchers", null, "delete"};
            MJ[i60] = new String[]{"access_token"};
        }
        if (w.Yk) {
            int i61 = t.NE;
            MH[i61] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i61] = new String[]{"accounts", "me", "connections", null};
            MJ[i61] = new String[]{"access_token", "target_credential"};
        }
        if (w.Yk) {
            int i62 = t.NF;
            MH[i62] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i62] = new String[]{"accounts", "me", "connections", null};
            MJ[i62] = new String[]{"access_token", "limit", "offset", "game", "seconds_since_last_login", "online"};
        }
        if (w.Yk) {
            int i63 = t.NG;
            MH[i63] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i63] = new String[]{"accounts", "me", "connections", null, "count"};
            MJ[i63] = new String[]{"access_token", "game", "seconds_since_last_login", "online"};
        }
        if (w.Yl) {
            int i64 = t.NH;
            MH[i64] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i64] = new String[]{"accounts", "me", "requests"};
            MJ[i64] = new String[]{"access_token", "limit", "offset", "request_type", AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS};
        }
        if (w.Yl) {
            int i65 = t.NI;
            MH[i65] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i65] = new String[]{"accounts", "me", "requests", "sent"};
            MJ[i65] = new String[]{"access_token", "limit", "offset", "request_type"};
        }
        if (w.Yl) {
            int i66 = t.NJ;
            MH[i66] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i66] = new String[]{"accounts", "me", "requests", "sent", null, "cancel"};
            MJ[i66] = new String[]{"access_token"};
        }
        if (w.Yl) {
            int i67 = t.NK;
            MH[i67] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i67] = new String[]{"accounts", "me", "requests", null, "accept"};
            MJ[i67] = new String[]{"access_token"};
        }
        if (w.Yl) {
            int i68 = t.NL;
            MH[i68] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i68] = new String[]{"accounts", "me", "requests", null, "reject"};
            MJ[i68] = new String[]{"access_token"};
        }
        if (w.Yl) {
            int i69 = t.NM;
            MH[i69] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i69] = new String[]{"accounts", "me", "requests", null, "ignore"};
            MJ[i69] = new String[]{"access_token"};
        }
        if (w.Ym) {
            int i70 = t.NN;
            MH[i70] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i70] = new String[]{"accounts", "me"};
            MJ[i70] = new String[]{"access_token", "name", "language", UserDataStore.COUNTRY};
        }
        if (w.Ym) {
            int i71 = t.NO;
            MH[i71] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i71] = new String[]{"accounts", "me", "status_line"};
            MJ[i71] = new String[]{"access_token", "status_line"};
        }
        if (w.Ym) {
            int i72 = t.NP;
            MH[i72] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i72] = new String[]{"accounts", null};
            MJ[i72] = new String[]{"access_token"};
        }
        if (w.Yn) {
            int i73 = t.NQ;
            MH[i73] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i73] = new String[]{"groups"};
            MJ[i73] = new String[]{"access_token", "description", "category", "name", "membership", "member_limit"};
        }
        if (w.Yn) {
            int i74 = t.NR;
            MH[i74] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i74] = new String[]{"groups", null};
            MJ[i74] = new String[]{"access_token"};
        }
        if (w.Yn) {
            int i75 = t.NS;
            MH[i75] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i75] = new String[]{"groups"};
            MJ[i75] = new String[]{"access_token", "limit", "category", "offset", "keywords"};
        }
        if (w.Yn) {
            int i76 = t.NT;
            MH[i76] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i76] = new String[]{"groups", null, "members"};
            MJ[i76] = new String[]{"access_token", "credential"};
        }
        if (w.Yn) {
            int i77 = t.NU;
            MH[i77] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i77] = new String[]{"groups", null, "members"};
            MJ[i77] = new String[]{"access_token", "limit", "offset"};
        }
        if (w.Yo) {
            int i78 = t.NV;
            MH[i78] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i78] = new String[]{"events"};
            MJ[i78] = new String[]{"access_token", "end_date", "start_date", "description", "category", "name", "group_id"};
        }
        if (w.Yo) {
            int i79 = t.NW;
            MH[i79] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i79] = new String[]{"events", null, "tournament:"};
            MJ[i79] = new String[]{"access_token", "type", "leaderboard"};
        }
        if (w.Yo) {
            int i80 = t.NX;
            MH[i80] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i80] = new String[]{"events", null};
            MJ[i80] = new String[]{"access_token"};
        }
        if (w.Yo) {
            int i81 = t.NY;
            MH[i81] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i81] = new String[]{"events"};
            MJ[i81] = new String[]{"access_token", "limit", "category", "offset", "keywords", AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS};
        }
        if (w.Yk) {
            int i82 = t.NZ;
            MH[i82] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i82] = new String[]{"accounts", "me", "connections", null, null, "delete"};
            MJ[i82] = new String[]{"access_token"};
        }
        if (w.Yk) {
            int i83 = t.Oa;
            MH[i83] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i83] = new String[]{"accounts", "me", "connections", null, null};
            MJ[i83] = new String[]{"access_token"};
        }
        if (w.Yk) {
            int i84 = t.Ob;
            MH[i84] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i84] = new String[]{"accounts", "me", "connections", null, "import"};
            MJ[i84] = new String[]{"access_token", "from_credential", "secret"};
        }
        if (w.Yk) {
            int i85 = t.Oc;
            MH[i85] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i85] = new String[]{"accounts", "me", "import", null};
            MJ[i85] = new String[]{"access_token", "from_credential", "secret"};
        }
        if (w.Yn) {
            int i86 = t.Od;
            MH[i86] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i86] = new String[]{"groups", null};
            MJ[i86] = new String[]{"access_token", "description", "category", "name", "membership", "member_limit"};
        }
        if (w.Yn) {
            int i87 = t.Oe;
            MH[i87] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i87] = new String[]{"groups", null, "delete"};
            MJ[i87] = new String[]{"access_token"};
        }
        if (w.Yn) {
            int i88 = t.Of;
            MH[i88] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i88] = new String[]{"groups", null, "members", null};
            MJ[i88] = new String[]{"access_token"};
        }
        if (w.Yn) {
            int i89 = t.Og;
            MH[i89] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i89] = new String[]{"groups", null, "members", null};
            MJ[i89] = new String[]{"access_token"};
        }
        if (w.Yn) {
            int i90 = t.Oh;
            MH[i90] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i90] = new String[]{"groups", null, "members", null, "delete"};
            MJ[i90] = new String[]{"access_token"};
        }
        if (w.Yo) {
            int i91 = t.Oi;
            MH[i91] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i91] = new String[]{"events", null};
            MJ[i91] = new String[]{"access_token", "end_date", "start_date", "description", "category", "name", "group_id"};
        }
        if (w.Yo) {
            int i92 = t.Oj;
            MH[i92] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i92] = new String[]{"events", null, "delete"};
            MJ[i92] = new String[]{"access_token"};
        }
        if (w.Yo) {
            int i93 = t.Ok;
            MH[i93] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i93] = new String[]{"events", null, "awards"};
            MJ[i93] = new String[]{"start_rank", "access_token", "end_rank", "gifts"};
        }
        if (w.Yo) {
            int i94 = t.Ol;
            MH[i94] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i94] = new String[]{"events", null, "awards", null, "delete"};
            MJ[i94] = new String[]{"access_token"};
        }
        if (w.Yp) {
            int i95 = t.Om;
            MH[i95] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i95] = new String[]{null, null, "wall"};
            MJ[i95] = new String[]{"access_token", "language", "text"};
        }
        if (w.Yp) {
            int i96 = t.On;
            MH[i96] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i96] = new String[]{null, null, "wall", null, "vote"};
            MJ[i96] = new String[]{"access_token", "activity_type"};
        }
        if (w.Yp) {
            int i97 = t.Oo;
            MH[i97] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i97] = new String[]{null, null, "wall"};
            MJ[i97] = new String[]{"access_token", "language", "sort_type"};
        }
        if (w.Yp) {
            int i98 = t.Op;
            MH[i98] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i98] = new String[]{"accounts", null, "feed"};
            MJ[i98] = new String[]{"access_token", "connection_type", "language", "sort_type"};
        }
        if (w.Yq) {
            int i99 = t.Oq;
            MH[i99] = new Object[]{new Integer(4), bool, num2, bool2};
            MI[i99] = new String[]{"accounts", null, "trophies"};
            MJ[i99] = new String[]{"access_token", "trophy_id"};
        }
        if (w.Yq) {
            int i100 = t.Or;
            MH[i100] = new Object[]{new Integer(4), bool, num, bool2};
            MI[i100] = new String[]{"accounts", null, "trophies"};
            MJ[i100] = new String[]{"access_token", "game"};
        }
    }

    private static boolean jb() {
        return MQ != null;
    }

    private static void jc() {
        MQ = new int[w.Yh];
        MR = new Object[w.Yh][];
        MS = new String[w.Yh][];
        MT = new String[w.Yh][];
        MU = new String[w.Yh];
        MV = new String[w.Yh];
        MW = new long[w.Yf];
        for (int i = 0; i < MQ.length; i++) {
            MQ[i] = -1;
        }
    }

    private static am cr(int i) {
        if (i < w.Yf) {
            return Ml[i];
        }
        return null;
    }

    private static boolean jd() {
        return MH != null;
    }

    private static String cs(int i) {
        String string;
        String string2;
        int iCC = cC(i);
        Object[] objArr = MH[iCC];
        int iIntValue = ((Integer) objArr[0]).intValue();
        String str = LT[iIntValue];
        boolean zBooleanValue = ((Boolean) objArr[1]).booleanValue();
        int iIntValue2 = ((Integer) objArr[2]).intValue();
        am amVarCr = cr(i);
        if (str != null) {
            c(i, iIntValue, false);
        } else {
            ct(i);
        }
        if (I(i, 0)) {
            String[] strArr = (String[]) cv(i);
            String[] strArrCw = cw(i);
            String[] strArr2 = MI[iCC];
            String[] strArr3 = MJ[iCC];
            String strG = g(Mj[iIntValue], zBooleanValue);
            int i2 = 0;
            if (strArr2 != null) {
                for (int i3 = 0; i3 < strArr2.length; i3++) {
                    if (strArr2[i3] != null) {
                        if ("clientId".equals(strArr2[i3])) {
                            strG = strG + "/" + bc(gX());
                        } else {
                            strG = strG + "/" + bc(strArr2[i3]);
                        }
                    } else {
                        if (strArr[i2] != null) {
                            strG = strG + "/" + bc(strArr[i2]);
                        }
                        i2++;
                    }
                }
            }
            String str2 = strG;
            if (strArr3 == null) {
                string = "";
            } else {
                String strA = "";
                for (int i4 = 0; i4 < strArr3.length; i4++) {
                    if (strArr3[i4] != null) {
                        if ("clientId".equals(strArr3[i4])) {
                            strA = a(strA, "client_id", gX(), true);
                        } else if ("access_token".equals(strArr3[i4])) {
                            if (cx(i) != null) {
                                strA = a(strA, "access_token", cx(i), true);
                            } else {
                                strA = a(strA, "access_token", Ma, true);
                            }
                        } else if ("nonce".equals(strArr3[i4])) {
                            strA = a(strA, "nonce", Mf, true);
                        } else if ("&&".equals(strArr3[i4])) {
                            if (strArr[i2] != null) {
                                strA = a(strA, (String) null, strArr[i2], false);
                            }
                            i2++;
                        } else {
                            if (strArr[i2] != null) {
                                if (strArr3[i4].startsWith("&")) {
                                    strA = a(strA, strArr3[i4], GLLib.e(strArr[i2].getBytes(), 0, strArr[i2].getBytes().length), true);
                                } else {
                                    strA = a(strA, strArr3[i4], strArr[i2], true);
                                }
                            }
                            i2++;
                        }
                    }
                }
                StringBuilder sbAppend = new StringBuilder().append("");
                if (iIntValue2 != 1) {
                    strA = "?" + strA;
                }
                string = sbAppend.append(strA).toString();
            }
            if (strArrCw != null) {
                string2 = strArr3 != null ? string : "";
                for (int i5 = 0; i5 < strArrCw.length; i5 += 2) {
                    string2 = a(string2, strArrCw[i5], strArrCw[i5 + 1], true);
                }
                if (strArr3 == null) {
                    StringBuilder sbAppend2 = new StringBuilder().append(string);
                    if (iIntValue2 != 1) {
                        string2 = "?" + string2;
                    }
                    string2 = sbAppend2.append(string2).toString();
                }
            } else {
                string2 = string;
            }
            if (iCC == t.Pz) {
                String[] strArrCy = cy(i);
                if (strArrCy.length > 0) {
                    String str3 = strArrCy[0];
                    if (str3 != null) {
                        amVarCr.C("If-None-Match", str3);
                    }
                    amVarCr.bQ("Etag");
                }
            }
            a(str2, string2, iIntValue2, amVarCr);
            H(i, 1);
        }
        if (I(i, 1) && b(amVarCr)) {
            boolean zBooleanValue2 = ((Boolean) objArr[3]).booleanValue();
            String strD = d(amVarCr);
            if (iCC == t.Pz) {
                MP = amVarCr.acN;
            }
            if (strD != null) {
                MZ[i] = 0;
                H(i, 3);
                if (zBooleanValue2) {
                    try {
                        return new String(decodeResponse(strD));
                    } catch (IOException e) {
                        GLLib.d(e);
                    }
                }
                return strD;
            }
        }
        return null;
    }

    public static synchronized boolean je() {
        boolean z = false;
        synchronized (s.class) {
            if (MQ != null) {
                for (int i = 0; i < MQ.length; i++) {
                    if (MQ[i] == -1) {
                    }
                }
                z = true;
            } else {
                z = true;
            }
        }
        return z;
    }

    private static void ct(int i) {
        if (I(i, 3)) {
            H(i, 0);
        }
    }

    private static void c(int i, int i2, boolean z) {
        if (I(i, 3)) {
            if (i2 == 11) {
                Mj[i2] = Mp;
                H(i, 0);
                return;
            }
            if (cn(i2)) {
                H(i, 5);
            } else {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                }
                int i3 = (int) (jCurrentTimeMillis - Mi[i2]);
                if (!z && i3 <= 15000 && Mi[i2] != 0) {
                    H(i, 0);
                    return;
                } else {
                    Mj[i2] = null;
                    H(i, 4);
                }
            }
        }
        if (I(i, 4)) {
            Mj[i2] = b(LT[i2], i, i2);
            if (Mj[i2] != null) {
                long[] jArr = Mi;
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                }
                jArr[i2] = jCurrentTimeMillis2;
                H(i, 0);
            }
        }
        if (!I(i, 5) || Mj[i2] == null) {
            return;
        }
        H(i, 0);
    }

    private static boolean cu(int i) {
        return I(i, 2);
    }

    private static Object[] cv(int i) {
        return MR[i];
    }

    private static String[] cw(int i) {
        return MS[i];
    }

    private static String cx(int i) {
        return MV[i];
    }

    private static String[] cy(int i) {
        return MT[i];
    }

    private static int jf() {
        return cz(-1);
    }

    private static synchronized int cz(int i) {
        for (int i2 = 0; i2 < MQ.length; i2++) {
            if (MQ[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    private static void F(int i, int i2) {
        long[] jArr = MW;
        jArr[i] = jArr[i] + ((long) i2);
    }

    private static void cA(int i) {
        MW[i] = 0;
    }

    private static long cB(int i) {
        return MW[i];
    }

    private static synchronized int cC(int i) {
        return MQ[i];
    }

    public static synchronized void cD(int i) {
        MQ[i] = Integer.MAX_VALUE;
    }

    public static synchronized void jg() {
        int i = 0;
        while (i < MQ.length) {
            if (cC(i) == Integer.MAX_VALUE) {
                cH(i);
            } else {
                i++;
            }
        }
    }

    private static void a(int i, Object[] objArr, String str) {
        a(i, objArr, (String[]) null, str);
    }

    private static synchronized void a(int i, Object[] objArr, String[] strArr, String str) {
        a(i, objArr, strArr, (String[]) null, (String) null, str);
    }

    private static synchronized void a(int i, Object[] objArr, String[] strArr, String[] strArr2, String str, String str2) {
        int iJf = jf();
        if (iJf != -1) {
            MQ[iJf] = i;
            MR[iJf] = objArr;
            MU[iJf] = str2;
            MS[iJf] = strArr;
            MT[iJf] = strArr2;
            MV[iJf] = str;
            ML = GLURLConnection.HTTP_OK;
            MO = "";
            if (iJf < w.Yf) {
                cA(iJf);
            }
            MX = true;
        } else {
            GLLib.c(false, "Federation: You are trying to add too many requests to the request pool. Eigther increase pool size (GLLibConfiguration ) or decrease requests amount");
        }
    }

    public static void ae(boolean z) {
        byte[] bArrOj;
        String strIT;
        if (Mw != -1 && (strIT = iT()) != null) {
            a(new u(t.NB, strIT, null, Mz, ML, MM, MN));
            My = null;
        }
        if (MD && !MF) {
            a(t.Qd, new String[]{"plain", "long_polling", MC}, MG);
            MF = true;
        }
        if (!je() || fZ()) {
            if (!z) {
                GLLib.c(false, "Federation.Update() now called automatically inside GLLib. Please make sure that GLLibConfiguration.useFederation=true (it's true by default for all platforms except midp1) and remove Federation.Update() from your code.");
            }
            if (!jd()) {
                GLLib.c(false, "You have to call Federation.Federation_Init() before you will be able to send any Federation request");
            }
            jk();
            if (MX) {
                c(MQ, t.Nt);
                c(MQ, t.Nn);
                c(MQ, t.NA);
                c(Mg, 4);
                MX = false;
            }
            int iJi = ji() + 1;
            for (int i = 0; i < iJi; i++) {
                boolean z2 = (cC(i) == t.Nt && Mn != null) | (cC(i) == t.Nn && Mn != null) | (cC(i) == t.NA);
                if (cC(i) != Integer.MAX_VALUE && cC(i) != -1 && (z2 || Ma != null)) {
                    F(i, GLLib.Qy);
                    if (!MY && (cB(i) < w.Yi || w.Yi == 0)) {
                        cG(i);
                        cK(i);
                        if (cE(cC(i))) {
                            a(Mk, i);
                        }
                        if (w.YR && cF(cC(i))) {
                            a(LU, i);
                        }
                    } else {
                        ML = 504;
                        MM = "Federation timeout";
                        MN = new RuntimeException(MM);
                        int iCL = cL(cC(i));
                        if (iCL != -1) {
                            Mh[iCL] = 0;
                        }
                        H(i, 2);
                    }
                    if (cu(i) || MO != null) {
                        int iCC = cC(i);
                        String strCJ = cJ(i);
                        if (w.YH && iCC == t.Pg) {
                            try {
                                G(iCC, ML);
                            } catch (JSONException failure) {
                                throw s.<RuntimeException>propagateChecked(failure);
                            }
                        }
                        cD(i);
                        if (w.Wm) {
                            GLLib.a(iCC, MO, strCJ, ML);
                        }
                        a(new u(iCC, MO, null, strCJ, ML, MM, MN));
                    }
                    MY = false;
                }
            }
            if (a(Mk, -1)) {
                a(new u(-1, "", null, "", ML, MM, MN));
            }
            if (w.YR) {
                if (a(LU, -1)) {
                    a(new u(-2, "", null, "", ML, MM, MN));
                } else if (LU != null && LV && (bArrOj = LU.oj()) != null) {
                    a(new u(t.Qb, null, bArrOj, "", ML, MM, MN));
                }
            }
            jg();
            if (LV && LW <= 0) {
                aF(LX);
                LW = w.YS;
            }
            LW -= GLLib.Qy;
        }
    }

    private static void a(u uVar) {
        int iJw = uVar.jw();
        String strJB = uVar.jB();
        String strJA = uVar.jA();
        int iJy = uVar.jy();
        String strJz = uVar.jz();
        Exception excJx = uVar.jx();
        if (strJA.startsWith("hestia")) {
            ao.ms().a(iJw, strJB, strJA, iJy, strJz, excJx);
            return;
        }
        if (strJA.startsWith("cloud")) {
            bq.a(iJw, strJB, strJA, iJy, strJz, excJx);
        } else if (strJA.startsWith("std")) {
            bz.om().a(iJw, strJB, strJA, iJy, strJz, excJx);
        } else {
            GLLib.QD.b(new u(iJw, strJB, null, strJA, iJy, strJz, excJx));
        }
    }

    private static void G(int i, int i2) throws JSONException {
        if (i2 != 200 && i2 != 202) {
            return;
        }
        try {
            if (com.gameloft.android2d.d.b.Qm() == null) {
                GLLib.be("TrackingManager.GetManager(): null");
                return;
            }
        } catch (NoClassDefFoundError e) {
            GLLib.be(e.toString());
            GLLib.be("Call function: GlotAddEventPNTrackingEvent: Please enable GLOTv3 feature in Java-Android Wrapper(SET USE_GLOT3=1).");
            return;
        }
        String[] strArr = (String[]) cv(cz(i));
        String str2 = strArr[0];
        String str3 = strArr[1];
        String str;
        if ("c2dm".equals(str2)) {
            str = "166046";
        } else if ("gcm".equals(str2)) {
            str = "166050";
        } else {
            str = "166067";
        }
        if (str3 == null || str3.equals("")) {
            str3 = AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
        com.gameloft.android2d.d.b.Qm().b(51935, new String[]{str3, "INT:" + str});
    }

    private static boolean cE(int i) {
        return i >= t.OF && i <= t.OU;
    }

    private static boolean cF(int i) {
        return i >= t.PE && i <= t.Qa;
    }

    private static void cG(int i) {
        int iCC = cC(i);
        am amVarCr = cr(i);
        MO = null;
        if (iCC == t.Qc) {
            MO = ch(i);
        } else if (iCC == t.Ps) {
            MO = cp(i);
        } else if (iCC == t.Pu) {
            MO = cq(i);
        } else if (w.YR && iCC == t.PE) {
            MO = ci(i);
        } else if (w.YR && (iCC == t.PF || iCC == t.PG || iCC == t.PH || iCC == t.PI || iCC == t.PJ || iCC == t.PK || iCC == t.PL || iCC == t.PM || iCC == t.PN || iCC == t.PO || iCC == t.PP || iCC == t.PQ || iCC == t.PR || iCC == t.PS || iCC == t.PT || iCC == t.PU || iCC == t.PV || iCC == t.PW || iCC == t.PX || iCC == t.PY || iCC == t.PZ || iCC == t.Qa)) {
            MO = cj(i);
        } else if (iCC == t.OZ && w.YM) {
            long jCm = cm(i);
            if (jCm != -1) {
                MO = Long.toString(jCm);
            }
        } else if (iCC == t.OT || iCC == t.OU || iCC == t.OS) {
            MO = cl(i);
        } else if (iCC == t.OQ) {
            iP();
            MO = "";
        } else if (iCC == t.OG || iCC == t.OH || iCC == t.OI || iCC == t.OJ || iCC == t.OL || iCC == t.OM || iCC == t.OO || iCC == t.OP || iCC == t.ON || iCC == t.OF) {
            MO = ck(i);
        } else if (iCC == t.NA) {
            MO = co(i);
        } else if (iCC == t.ND) {
            GLLib.c(Mn != null, "Federation. EnableOnline field in Jad is not set.");
            if (Mn != null) {
                Ne = true;
                MO = "";
            }
        } else if (iCC != -1) {
            MO = cs(i);
        }
        if (iCC == t.Nm && w.Yr) {
            Ma = MO;
            return;
        }
        if (iCC == t.Nu && w.Yr) {
            Me = MO;
            return;
        }
        if (iCC == t.Qd && w.YT) {
            if (MO != null) {
                MF = false;
                return;
            }
            return;
        }
        if ((iCC == t.Nn || iCC == t.Nt) && w.Yr) {
            String str = MO;
            if (str != null) {
                bc bcVar = new bc(str);
                try {
                    Ma = bcVar.ch("access_token");
                    Mc = bcVar.ch("refresh_token");
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (w.Vl) {
                        jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                    }
                    Mb = jCurrentTimeMillis;
                    if (w.XZ) {
                        GLLib.b("--Token received", "Federation", 3);
                        GLLib.b("--s_accessToken = " + Ma, "Federation", 3);
                        GLLib.b("--s_refreshToken = " + Mc, "Federation", 3);
                        GLLib.b("--s_accessToken_Timestamp = " + Mb, "Federation", 3);
                        return;
                    }
                    return;
                } catch (Exception e) {
                    GLLib.d(e);
                    return;
                }
            }
            return;
        }
        if (iCC == t.Nv && w.Yr) {
            if (MO != null) {
                if (amVarCr.Bg > 200) {
                    MO = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_FAILED;
                    return;
                } else {
                    MO = "Successful";
                    return;
                }
            }
            return;
        }
        if (iCC == t.OS) {
            Mf = MO;
        } else if (iCC == t.OF) {
            Mf = MO;
        }
    }

    public static synchronized void jh() {
        synchronized (s.class) {
            if (MQ != null) {
                for (int i = 0; i < MQ.length; i++) {
                    MQ[i] = -1;
                }
                for (int i2 = 0; i2 < Mg.length; i2++) {
                    H(i2, 3);
                }
                for (int i3 = 0; i3 < Mh.length; i3++) {
                    Mh[i3] = 0;
                }
                for (int i4 = 0; i4 < Ml.length; i4++) {
                    Ml[i4].cancel();
                    Ml[i4] = null;
                    Ml[i4] = new am(false);
                }
            }
        }
    }

    private static int ji() {
        for (int i = MK - 1; i >= 0; i--) {
            if (cC(i) != -1) {
                return i;
            }
        }
        return -1;
    }

    private static void c(int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length - 1; i2++) {
            if (iArr[i2] != i) {
                for (int i3 = i2 + 1; i3 < iArr.length - 1; i3++) {
                    if (iArr[i3] == i) {
                        d(MZ, i2, i3);
                        d(Mg, i2, i3);
                        d(MQ, i2, i3);
                        a(MU, i2, i3);
                        a(MR, i2, i3);
                        a(MS, i2, i3);
                        a(MT, i2, i3);
                        a(MW, i2, i3);
                        a(MV, i2, i3);
                        a(Ml, i2, i3);
                        break;
                    }
                }
            }
        }
    }

    private static void a(Object[][] objArr, int i, int i2) {
        if (i < objArr.length && i2 < objArr.length) {
            Object[] objArr2 = objArr[i];
            objArr[i] = objArr[i2];
            objArr[i2] = objArr2;
        }
    }

    private static void a(String[][] strArr, int i, int i2) {
        if (i < strArr.length && i2 < strArr.length) {
            String[] strArr2 = strArr[i];
            strArr[i] = strArr[i2];
            strArr[i2] = strArr2;
        }
    }

    private static void a(String[] strArr, int i, int i2) {
        if (i < strArr.length && i2 < strArr.length) {
            String str = strArr[i];
            strArr[i] = strArr[i2];
            strArr[i2] = str;
        }
    }

    private static void d(int[] iArr, int i, int i2) {
        if (i < iArr.length && i2 < iArr.length) {
            int i3 = iArr[i];
            iArr[i] = iArr[i2];
            iArr[i2] = i3;
        }
    }

    private static void a(long[] jArr, int i, int i2) {
        if (i < jArr.length && i2 < jArr.length) {
            long j = jArr[i];
            jArr[i] = jArr[i2];
            jArr[i2] = j;
        }
    }

    private static void a(am[] amVarArr, int i, int i2) {
        if (i < amVarArr.length && i2 < amVarArr.length) {
            am amVar = amVarArr[i];
            amVarArr[i] = amVarArr[i2];
            amVarArr[i2] = amVar;
        }
    }

    private static synchronized void cH(int i) {
        for (int i2 = i; i2 < MQ.length - 1; i2++) {
            MQ[i2] = MQ[i2 + 1];
            MR[i2] = null;
            MR[i2] = MR[i2 + 1];
            MS[i2] = MS[i2 + 1];
            MT[i2] = MT[i2 + 1];
            MV[i2] = MV[i2 + 1];
            MU[i2] = MU[i2 + 1];
        }
        if (i < w.Yf) {
            am amVar = Ml[i];
            while (i < Ml.length - 1) {
                Ml[i] = Ml[i + 1];
                Mg[i] = Mg[i + 1];
                MW[i] = MW[i + 1];
                MZ[i] = MZ[i + 1];
                i++;
            }
            Ml[Ml.length - 1] = new am(false);
            Mg[Mg.length - 1] = 3;
        } else {
            GLLib.c(false, "Try to remove " + i + " request from array " + w.Yf + " length");
        }
        MS[MQ.length - 1] = null;
        MT[MQ.length - 1] = null;
        MR[MQ.length - 1] = null;
        MU[MQ.length - 1] = null;
        MQ[MQ.length - 1] = -1;
    }

    private static void H(int i, int i2) {
        Mg[i] = i2;
        cA(i);
    }

    private static int cI(int i) {
        return Mg[i];
    }

    private static boolean I(int i, int i2) {
        return cI(i) == i2;
    }

    public static String cJ(int i) {
        return MU[i];
    }

    public static Hashtable jj() {
        return MP;
    }

    public static void jk() {
        for (int i = 0; i < Mg.length; i++) {
            if (I(i, 2)) {
                H(i, 3);
            }
        }
        MN = null;
        MM = null;
    }

    private static boolean a(bx bxVar, int i) {
        if (bxVar == null || !bxVar.BT) {
            return false;
        }
        ML = 801;
        MM = bxVar.akz;
        MN = bxVar.acI;
        bxVar.acI = null;
        bxVar.BT = false;
        if (i != -1) {
            H(i, 2);
        }
        return true;
    }

    private static boolean a(am amVar) {
        if (amVar == null || !amVar.BT) {
            return false;
        }
        if (w.XZ) {
            GLLib.b("Federation. HTTP error", "Federation", 3);
        }
        ML = amVar.Bg;
        MM = amVar.acH;
        MN = amVar.acI;
        amVar.Bg = 0;
        amVar.acH = null;
        amVar.acI = null;
        amVar.BT = false;
        amVar.bM();
        if (MN != null && (ML == 200 || ML == 0)) {
            ML = 801;
        }
        return true;
    }

    private static void cK(int i) {
        am amVarCr = cr(i);
        if (amVarCr != null && a(amVarCr)) {
            if (MN != null && ("java.net.SocketTimeoutException".equals(MN.getClass().getName()) || "java.io.IOException".equals(MN.getClass().getName()) || "java.net.ConnectException".equals(MN.getClass().getName()) || w.Yb)) {
                int iCL = cL(cC(i));
                boolean zI = I(i, 4);
                if ((!zI && i != -1 && MZ[i] < w.Ya) || (zI && iCL != -1 && Na[iCL] < w.Ya)) {
                    if (w.XZ) {
                        GLLib.b("--Federation. Try to reconnect", "Federation", 3);
                    }
                    if (w.Yc > 0) {
                        try {
                            Thread.sleep(w.Yc);
                        } catch (Exception e) {
                        }
                    }
                    if (zI) {
                        int[] iArr = Na;
                        iArr[iCL] = iArr[iCL] + 1;
                    } else {
                        int[] iArr2 = MZ;
                        iArr2[i] = iArr2[i] + 1;
                    }
                    H(i, 3);
                    if (iCL != -1) {
                        Mh[iCL] = 0;
                        Mi[iCL] = 0;
                        return;
                    }
                    return;
                }
                if (i != -1) {
                    MZ[i] = 0;
                }
                if (iCL != -1) {
                    Na[iCL] = 0;
                }
            }
            if (i != -1 && I(i, 4)) {
                ML = 800;
            }
            H(i, 2);
        }
    }

    private static int cL(int i) {
        Object[] objArr = MH[i];
        if (objArr == null || objArr[0] == null) {
            return -1;
        }
        return ((Integer) objArr[0]).intValue();
    }

    private static void n(int i, String str) {
        H(i, 2);
        MM = str;
        MN = new Exception("Connection exception: " + str);
    }

    public static int jl() {
        return ML;
    }

    public static String jm() {
        return MM;
    }

    public static void jn() {
        aS("");
    }

    public static void aS(String str) {
        if (!fZ()) {
            if (!jb()) {
                jc();
            }
            if (!jd()) {
                ja();
            }
            if (Ml == null) {
                iZ();
            }
            Md = "";
            Ne = false;
            if (jp()) {
                iU();
                iM();
                aI(Md);
                a(t.ND, (Object[]) null, str);
            } else {
                Mn = null;
            }
            am.mn();
        }
    }

    private static void jo() {
        if (Nf == null) {
            aX(GLLib.Qu == null ? null : GLLib.Qu.bY("GAME-PRODUCT-ID"));
        }
        if (Ng == null) {
            aY(GLLib.Qu == null ? null : GLLib.Qu.bY("GAME-GGI"));
        }
        if (Nh == null) {
            aZ(GLLib.Qu == null ? null : GLLib.Qu.bY("MIDlet-Version"));
        }
        if (Ni == null) {
            ba(GLLib.Qu == null ? null : GLLib.Qu.bY("GAME-PLATFORM"));
        }
        if (Nj == null) {
            bb(GLLib.Qu != null ? GLLib.Qu.bY("GAME-SHOP") : null);
        }
    }

    public static boolean jp() {
        return w.XY;
    }

    public static void jq() {
        if (w.XZ) {
            GLLib.b("--Reset federation", "Federation", 3);
        }
        Ne = false;
        jh();
        MM = null;
        MN = null;
        Ma = null;
    }

    public static String jr() {
        return LZ;
    }

    public static void f(String str, boolean z) {
        if (z) {
            try {
                GLLib.kM();
                GLLib.A(str, (String) null);
                str = GLLib.kP();
            } catch (UnsupportedEncodingException e) {
                GLLib.d(e);
            }
        }
        LZ = str;
    }

    public static boolean fZ() {
        return af(true);
    }

    private static boolean af(boolean z) {
        if (!z) {
            GLLib.c(false, "IsFederationInited function is deprecated now. Use IsInitialized() instead");
        }
        return Ne;
    }

    public static String js() {
        return jv() + ":" + jt();
    }

    public static String jt() {
        return Nb;
    }

    public static void aT(String str) {
        Nb = str;
        Ma = null;
    }

    public static String ju() {
        return Nc;
    }

    public static void aU(String str) {
        Nc = str;
        Ma = null;
    }

    public static String jv() {
        return Nd;
    }

    public static void aV(String str) {
        Nd = str;
        Ma = null;
    }

    public static String f(String str, int i) {
        return "socket://" + g(str, i);
    }

    public static String aW(String str) {
        return "socket://" + str;
    }

    public static String g(String str, int i) {
        return str + ":" + i;
    }

    public static void aX(String str) {
        Nf = str;
        Nk = null;
    }

    public static void aY(String str) {
        Ng = str;
        Nk = null;
    }

    public static void aZ(String str) {
        Nh = str;
        Nk = null;
    }

    public static void ba(String str) {
        Ni = str;
        Nk = null;
    }

    public static void bb(String str) {
        Nj = str;
        Nk = null;
    }

    public static String gX() {
        if (Nk == null) {
            if (Nf == null) {
                jo();
            }
            Nk = "" + Nf + ":" + Ng + ":" + Nh + ":" + Ni;
            if (Nj != null && !"".equals(Nj)) {
                Nk += ":" + Nj;
            }
        }
        return Nk;
    }

    public static boolean b(am amVar) {
        return amVar.BM != null;
    }

    public static boolean c(am amVar) {
        return (amVar.BP == null || amVar.isInProgress()) ? false : true;
    }

    private static String a(String str, String str2, String str3, boolean z) {
        if (z) {
            str3 = bc(str3);
        }
        if ("".equals(str)) {
            return str + (str2 != null ? str2 + "=" : "") + str3;
        }
        return str + "&" + (str2 != null ? str2 + "=" : "") + str3;
    }

    public static String d(am amVar) {
        String str = amVar.BM;
        ML = amVar.Bg;
        Mm = amVar.BX;
        return str;
    }

    public static byte[] e(am amVar) {
        ML = amVar.Bg;
        Mm = amVar.BX;
        return amVar.BP;
    }

    public static void a(String str, String str2, int i, am amVar) {
        amVar.c(str, str2, i);
    }

    private static String e(char c) {
        for (int i = 0; i < Nl.length; i++) {
            if (Nl[i] == c) {
                return "%" + Integer.toHexString(c);
            }
        }
        return String.valueOf(c);
    }

    public static String bc(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            stringBuffer.append(e(str.charAt(i)));
        }
        return stringBuffer.toString();
    }

    public static boolean iN() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        return Ma != null && jCurrentTimeMillis - Mb > ((long) (w.YA * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS));
    }
}
