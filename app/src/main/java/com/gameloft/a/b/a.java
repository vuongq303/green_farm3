package com.gameloft.a.b;

import com.facebook.appevents.UserDataStore;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.gameloft.android.wrapper.au;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.UnsupportedEncodingException;
import org.json.JSONObject;

/* JADX INFO: compiled from: JW_Federation.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private static String LZ;
    private static String Ma;
    private static String Mn;
    private static String Mp;
    private static String Mq;
    private static String Mr;
    private static String Ms;
    private static String Mt;
    private static String Mu;
    private static String Mv;
    private static String Mx;
    private static String Nb;
    private static String Nc;
    private static String Nd;
    private static String Nf;
    private static String Ng;
    private static String Nh;
    private static String Ni;
    private static String Nj;
    private static String Nk;
    private static String bhQ;
    private static String bwJ;
    private static String bwK;
    private static String bwL;
    private static e bwO;
    private static boolean Ne = false;
    private static long Mm = System.currentTimeMillis() / 1000;
    private static d bwM = null;
    private static boolean bwN = true;

    public static void bP(boolean z) {
        bwN = z;
    }

    public static boolean Wh() {
        return bwN;
    }

    public static void a(d dVar) {
        bwM = dVar;
    }

    public static void ho(String str) {
        bwJ = str;
        Nk = null;
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

    public static void f(String str, boolean z) {
        if (z) {
            try {
                h.kM();
                h.A(str, null);
                str = h.kP();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        LZ = str;
    }

    public static void aJ(String str) {
        Mx = str;
    }

    public static void hp(String str) {
        bwK = str;
    }

    public static String gX() {
        if (Nk == null) {
            if (Nf == null) {
            }
            Nk = "" + Nf + ":" + Ng + ":" + Nh + ":" + Ni;
            if (bwJ != null) {
                Nk = bwJ + ":" + Nk;
            }
            if (Nj != null && !"".equals(Nj)) {
                Nk += ":" + Nj;
            }
        }
        return Nk;
    }

    public static String iS() {
        return Mx;
    }

    public static String Wi() {
        return bwK;
    }

    public static String Wj() {
        return Mv;
    }

    private static String iV() {
        return Mn;
    }

    private static String Wk() {
        return bwL;
    }

    public static boolean fZ() {
        return Ne;
    }

    private static String aL(String str) {
        String str2 = "https://eve.gameloft.com/";
        if ("https://eve.gameloft.com/" != 0 && "https://eve.gameloft.com/".endsWith("/")) {
            str2 = "" + "https://eve.gameloft.com/".substring(0, "https://eve.gameloft.com/".length() - 1);
        }
        String str3 = str2 + "/config/" + gX();
        if (str != null) {
            return str3 + "/datacenters/" + str + "/urls";
        }
        return str3;
    }

    public static void a(String str, String str2, int i, e eVar) {
        if (Wh()) {
            eVar.c(str, str2, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void Wl() {
        if (bwO != null) {
            bwO.Bg = 0;
            bwO.acH = null;
            bwO.acI = null;
            bwO.BT = false;
            bwO.BX = 0L;
            bwO.bM();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(e eVar) {
        return eVar != null && eVar.BT;
    }

    private static void a(g gVar, e eVar) {
        gVar.bxa = eVar.BM;
        gVar.bxb = eVar.Bg;
    }

    public static String Wm() {
        String str = null;
        e eVar = new e();
        a(aL(null) + "/datacenters", "", 0, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (bwM != null) {
                    bwM.a(0, null, eVar.Bg, eVar.acH, eVar.acI, null);
                }
                Wl();
                if (eVar != null) {
                }
            }
            return str;
        } while (eVar.BM == null);
        if (bwM != null) {
            bwM.a(0, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, null);
        }
        str = eVar.BM;
        b(eVar);
        Wl();
        if (eVar != null) {
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String Wn() {
        e eVar = new e();
        a(aL(Mx), "", 0, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (eVar.Bg == 403) {
                    bP(false);
                }
                if (bwM != null) {
                    bwM.a(1, null, eVar.Bg, eVar.acH, eVar.acI, null);
                }
                Wl();
                if (eVar != null) {
                }
                return null;
            }
        } while (eVar.BM == null);
        String strHu = h.hu(h.ht(eVar.BM));
        b(eVar);
        try {
            JSONObject jSONObject = new JSONObject(h.hs(strHu));
            Mn = jSONObject.getString("pandora");
            Mp = jSONObject.getString("gllive-ope") + "social_player.php";
            Mq = jSONObject.getString("tapjoy");
            Mr = jSONObject.getString("offline_items");
            Ms = jSONObject.getString("game_portal");
            Mt = jSONObject.getString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS);
            Mu = jSONObject.getString("crm_iap");
            Mv = jSONObject.getString("ads_agency");
            bhQ = jSONObject.getString("marketing_site");
            au.ek(bhQ);
            bwL = jSONObject.getString("online_connectivity");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (bwM != null) {
            bwM.a(1, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, null);
        }
        Wl();
        if (eVar != null) {
        }
        return strHu;
    }

    public static boolean Wo() {
        String str;
        if (LZ == null) {
            return false;
        }
        String strG = h.g(hq("auth"), true);
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(2, null, bwO.Bg, bwO.acH, bwO.acI, null);
            }
            Wl();
            return false;
        }
        e eVar = new e();
        String[] strArr = {"authorize"};
        String[] strArr2 = {"clientId", "username", "password", "scope", "device_id", "access_token_only"};
        String[] strArr3 = {js(), ju(), "storage auth config asset transaction message", LZ, null, null, null, null, null};
        for (int i = 0; i < strArr.length; i++) {
            if ("clientId".equals(strArr[i])) {
                strG = strG + "/" + h.bc(gX());
            } else {
                strG = strG + "/" + h.bc(strArr[i]);
            }
        }
        if (strArr2 == null) {
            str = "";
        } else {
            String strA = "";
            int i2 = 0;
            for (int i3 = 0; i3 < strArr2.length; i3++) {
                if ("clientId".equals(strArr2[i3])) {
                    strA = h.a(strA, "client_id", gX(), true);
                } else {
                    if (strArr3[i2] != null) {
                        strA = h.a(strA, strArr2[i3], strArr3[i2], true);
                    }
                    i2++;
                }
            }
            str = "" + strA;
        }
        a(strG, str, 1, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (bwM != null) {
                    bwM.a(2, null, eVar.Bg, eVar.acH, eVar.acI, null);
                }
                Wl();
                if (eVar != null) {
                }
                return false;
            }
        } while (eVar.BM == null);
        b(eVar);
        String strHu = h.hu(h.ht(eVar.BM));
        try {
            Ma = new JSONObject(h.hs(strHu)).getString("access_token");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (bwM != null) {
            bwM.a(2, strHu, eVar.Bg, eVar.acH, eVar.acI, null);
        }
        Wl();
        if (eVar != null) {
        }
        return true;
    }

    public static g a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        String str9;
        String strG = h.g(hq("auth"), true);
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(11, null, bwO.Bg, bwO.acH, bwO.acI, str8);
            }
            Wl();
            return null;
        }
        int i = 0;
        e eVar = new e();
        String[] strArr = {"devices", "mydevice"};
        String[] strArr2 = {"access_token", "model", "carrier", UserDataStore.COUNTRY, "language", "download_code", "resolution", "firmware"};
        String[] strArr3 = {Wp(), str4, str2, str3, str, str5, str6, str7};
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if ("clientId".equals(strArr[i2])) {
                strG = strG + "/" + h.bc(gX());
            } else {
                strG = strG + "/" + h.bc(strArr[i2]);
            }
        }
        if (strArr2 == null) {
            str9 = "";
        } else {
            String strA = "";
            for (int i3 = 0; i3 < strArr2.length; i3++) {
                if ("clientId".equals(strArr2[i3])) {
                    strA = h.a(strA, "client_id", gX(), true);
                } else {
                    if (strArr3[i] != null) {
                        strA = h.a(strA, strArr2[i3], strArr3[i], true);
                    }
                    i++;
                }
            }
            str9 = "" + strA;
        }
        a(strG, str9, 1, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (bwM != null) {
                    bwM.a(11, null, eVar.Bg, eVar.acH, eVar.acI, str8);
                }
                g gVar = new g();
                a(gVar, eVar);
                Wl();
                if (eVar != null) {
                }
                return gVar;
            }
        } while (eVar.BM == null);
        if (bwM != null) {
            bwM.a(11, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, str8);
        }
        b(eVar);
        g gVar2 = new g();
        a(gVar2, eVar);
        Wl();
        if (eVar != null) {
        }
        return gVar2;
    }

    public static g d(String str, int i, String str2) {
        String str3;
        g gVar = null;
        int i2 = 0;
        String strG = h.g(hq("auth"), true);
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(13, null, bwO.Bg, bwO.acH, bwO.acI, str2);
            }
            Wl();
        } else {
            e eVar = new e();
            String[] strArr = {"users", "me", "compliance"};
            String[] strArr2 = {"access_token", UserDataStore.COUNTRY, "age"};
            String[] strArr3 = {Wp(), str, "" + i};
            for (int i3 = 0; i3 < strArr.length; i3++) {
                if ("clientId".equals(strArr[i3])) {
                    strG = strG + "/" + h.bc(gX());
                } else {
                    strG = strG + "/" + h.bc(strArr[i3]);
                }
            }
            if (strArr2 == null) {
                str3 = "";
            } else {
                String strA = "";
                int i4 = 0;
                while (true) {
                    int i5 = i2;
                    if (i4 >= strArr2.length) {
                        break;
                    }
                    if ("clientId".equals(strArr2[i4])) {
                        strA = h.a(strA, "client_id", gX(), true);
                        i2 = i5;
                    } else {
                        if (strArr3[i5] != null) {
                            strA = h.a(strA, strArr2[i4], strArr3[i5], true);
                        }
                        i2 = i5 + 1;
                    }
                    i4++;
                }
                str3 = "" + strA;
            }
            a(strG, str3, 1, eVar);
            do {
                try {
                    Thread.sleep(100L);
                } catch (Exception e) {
                }
                if (a(eVar)) {
                    if (bwM != null) {
                        bwM.a(11, null, eVar.Bg, eVar.acH, eVar.acI, str2);
                    }
                    gVar = new g();
                    a(gVar, eVar);
                    Wl();
                    if (eVar != null) {
                    }
                }
            } while (eVar.BM == null);
            if (bwM != null) {
                bwM.a(11, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, str2);
            }
            b(eVar);
            gVar = new g();
            a(gVar, eVar);
            Wl();
            if (eVar != null) {
            }
            i.Wv().b(gVar);
        }
        return gVar;
    }

    public static void jn() {
        bwO = new e();
        new b().start();
    }

    public static void aM(String str) {
        if (str != null && !str.equals("")) {
            new c(str).start();
        }
    }

    private static boolean a(String str, e eVar) {
        String strIV = iV();
        if (strIV == null || strIV.length() == 0) {
            return false;
        }
        String str2 = strIV + "/locate/" + str;
        if ("alert".equals(str)) {
            str2 = str2 + "?client_id=" + h.bc(gX()) + "&credential=" + h.bc(js());
        }
        a(str2, "", 0, eVar);
        return true;
    }

    public static String hq(String str) {
        String strHu = null;
        e eVar = new e();
        a(str, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (eVar != null) {
                }
            }
            return strHu;
        } while (eVar.BM == null);
        b(eVar);
        strHu = h.hu(h.ht(eVar.BM));
        if (eVar != null) {
        }
        return strHu;
    }

    public static String j(boolean z, boolean z2) {
        String strKL;
        String str;
        String strPx = "";
        int iOR = au.OR();
        String packageName = au.getPackageName();
        String strSubstring = packageName.substring("com.gameloft.android.".length(), packageName.length());
        String str2 = strSubstring + "_fed_anon_name";
        if (!z2) {
            strKL = h.kL();
        } else {
            if (iOR == 2) {
                if (com.gameloft.android.wrapper.e.getContext() == null) {
                    com.gameloft.android.wrapper.e.init(au.getContext());
                }
                String strEc = com.gameloft.android.wrapper.e.ec(str2);
                if (strEc == null || strEc.equals("")) {
                    strPx = au.Os();
                } else {
                    return strEc;
                }
            } else if (iOR != 1 || !z) {
                if ("".equals("")) {
                    strPx = au.getAndroidId();
                }
                if (strPx.equals("")) {
                    strPx = au.getSerial();
                }
                if (strPx.equals("")) {
                    strPx = au.OP();
                }
                if (strPx.equals("")) {
                    strPx = au.Px();
                }
            } else {
                strPx = au.Os();
            }
            strKL = strPx + strSubstring;
        }
        h.kM();
        try {
            h.A(strKL, null);
            String strSubstring2 = h.kP().substring(0, 16);
            if (iOR == 2) {
                str = (("android_v2_" + strSubstring + "_") + String.valueOf(System.currentTimeMillis() / 1000)) + "_" + strSubstring2;
            } else {
                str = "android_" + strSubstring2;
            }
            String strE = h.e(str.getBytes());
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

    public static String Wp() {
        return Ma;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(e eVar) {
        Mm = eVar.BX;
    }

    public static long iR() {
        return Mm;
    }

    public static g m(String str, String str2, String str3, String str4) {
        String str5;
        String strG = h.g(hq("storage"), true);
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(8, null, bwO.Bg, bwO.acH, bwO.acI, str4);
            }
            Wl();
            return null;
        }
        int i = 0;
        e eVar = new e();
        String[] strArr = {"profiles", "me", "myprofile"};
        String[] strArr2 = {"access_token"};
        String[] strArr3 = {Wp()};
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if ("clientId".equals(strArr[i2])) {
                strG = strG + "/" + h.bc(gX());
            } else {
                strG = strG + "/" + h.bc(strArr[i2]);
            }
        }
        if (strArr2 == null) {
            str5 = "";
        } else {
            String strA = "";
            for (int i3 = 0; i3 < strArr2.length; i3++) {
                if ("clientId".equals(strArr2[i3])) {
                    strA = h.a(strA, "client_id", gX(), true);
                } else {
                    if (strArr3[i] != null) {
                        strA = h.a(strA, strArr2[i3], strArr3[i], true);
                    }
                    i++;
                }
            }
            str5 = "" + strA;
        }
        a(strG, str5, 0, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (bwM != null) {
                    bwM.a(8, null, eVar.Bg, eVar.acH, eVar.acI, str4);
                }
                g gVar = new g();
                a(gVar, eVar);
                Wl();
                if (eVar != null) {
                }
                return gVar;
            }
        } while (eVar.BM == null);
        if (bwM != null) {
            bwM.a(8, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, str4);
        }
        b(eVar);
        g gVar2 = new g();
        a(gVar2, eVar);
        Wl();
        if (eVar != null) {
        }
        return gVar2;
    }

    public static g j(String str, String str2, String str3, String str4, String str5, String str6) {
        g gVar = null;
        e eVar = new e();
        String strG = h.g(hq("storage"), true);
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(9, null, eVar.Bg, eVar.acH, eVar.acI, str6);
            }
            Wl();
        } else {
            String str7 = strG + "/profiles/me/myprofile";
            String str8 = "access_token=" + Wp();
            if (str3 != null) {
                str8 = str8 + "&object=" + str3;
            }
            if (str5 != null) {
                str8 = str8 + "&operation=" + str5;
            }
            a(str7, str8, 1, eVar);
            do {
                try {
                    Thread.sleep(100L);
                } catch (Exception e) {
                }
                if (a(eVar)) {
                    if (bwM != null) {
                        bwM.a(9, null, eVar.Bg, eVar.acH, eVar.acI, str6);
                    }
                    gVar = new g();
                    a(gVar, eVar);
                    Wl();
                    if (eVar != null) {
                    }
                }
            } while (eVar.BM == null);
            if (bwM != null) {
                bwM.a(9, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, str6);
            }
            b(eVar);
            gVar = new g();
            a(gVar, eVar);
            Wl();
            if (eVar != null) {
            }
        }
        return gVar;
    }

    public static g hr(String str) {
        g gVar = null;
        e eVar = new e();
        String strG = h.g(hq("storage"), true);
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(10, null, eVar.Bg, eVar.acH, eVar.acI, str);
            }
            Wl();
        } else {
            a(strG + "/profiles/me/myprofile/delete", "access_token=" + Wp(), 1, eVar);
            do {
                try {
                    Thread.sleep(100L);
                } catch (Exception e) {
                }
                if (a(eVar)) {
                    if (bwM != null) {
                        bwM.a(10, null, eVar.Bg, eVar.acH, eVar.acI, str);
                    }
                    gVar = new g();
                    a(gVar, eVar);
                    Wl();
                    if (eVar != null) {
                    }
                }
            } while (eVar.BM == null);
            if (bwM != null) {
                bwM.a(10, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, str);
            }
            b(eVar);
            gVar = new g();
            a(gVar, eVar);
            Wl();
            if (eVar != null) {
            }
        }
        return gVar;
    }

    public static g k(String str, String str2, String str3, String str4, String str5, String str6) {
        String str7;
        g gVar = new g();
        String strG = h.g(hq("config"), true);
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(15, null, bwO.Bg, bwO.acH, bwO.acI, str6);
            }
            Wl();
            return null;
        }
        int i = 0;
        e eVar = new e();
        String[] strArr = {"configs", "users", "me"};
        String[] strArr2 = {"access_token", "now"};
        String[] strArr3 = {Wp(), str4};
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if ("clientId".equals(strArr[i2])) {
                strG = strG + "/" + h.bc(gX());
            } else {
                strG = strG + "/" + h.bc(strArr[i2]);
            }
        }
        if (strArr2 == null) {
            str7 = "";
        } else {
            String strA = "";
            for (int i3 = 0; i3 < strArr2.length; i3++) {
                if ("clientId".equals(strArr2[i3])) {
                    strA = h.a(strA, "client_id", gX(), true);
                } else {
                    if (strArr3[i] != null) {
                        strA = h.a(strA, strArr2[i3], strArr3[i], true);
                    }
                    i++;
                }
            }
            str7 = "" + strA;
        }
        if (str5 != null && !str5.isEmpty()) {
            eVar.C("If-None-Match", str5);
        }
        eVar.bQ("Etag");
        a(strG, str7, 0, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (bwM != null) {
                    bwM.a(15, null, eVar.Bg, eVar.acH, eVar.acI, str6);
                }
                a(gVar, eVar);
                Wl();
                if (eVar != null) {
                }
                return gVar;
            }
        } while (eVar.BM == null);
        if (bwM != null) {
            bwM.a(15, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, str6);
        }
        b(eVar);
        a(gVar, eVar);
        gVar.bxc = eVar.bwZ.get("Etag");
        Wl();
        if (eVar != null) {
        }
        return gVar;
    }

    public static g j(String str, String str2, String str3, String str4, String str5) {
        String str6;
        String strG = h.g(hq("transaction"), true);
        String str7 = "JW_Federation: Mercury_ReceiveSecureGift: Service Url: " + strG;
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(22, null, bwO.Bg, bwO.acH, bwO.acI, str5);
            }
            Wl();
            return null;
        }
        int i = 0;
        e eVar = new e();
        String[] strArr = {"transactions", "me", "gifts"};
        String[] strArr2 = {"access_token", "pointcut_action_id", "item", FirebaseAnalytics.Param.QUANTITY, "delivery_type"};
        String[] strArr3 = {Wp(), str, str2, str3, str4};
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if ("clientId".equals(strArr[i2])) {
                strG = strG + "/" + h.bc(gX());
            } else {
                strG = strG + "/" + h.bc(strArr[i2]);
            }
        }
        if (strArr2 == null) {
            str6 = "";
        } else {
            String strA = "";
            for (int i3 = 0; i3 < strArr2.length; i3++) {
                if ("clientId".equals(strArr2[i3])) {
                    strA = h.a(strA, "client_id", gX(), true);
                } else {
                    if (strArr3[i] != null) {
                        strA = h.a(strA, strArr2[i3], strArr3[i], true);
                    }
                    i++;
                }
            }
            str6 = "" + strA;
        }
        a(strG, str6, 1, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (bwM != null) {
                    bwM.a(22, null, eVar.Bg, eVar.acH, eVar.acI, str5);
                }
                g gVar = new g();
                a(gVar, eVar);
                Wl();
                return gVar;
            }
        } while (eVar.BM == null);
        if (bwM != null) {
            bwM.a(22, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, str5);
        }
        b(eVar);
        g gVar2 = new g();
        a(gVar2, eVar);
        Wl();
        return gVar2;
    }

    public static g n(String str, String str2, String str3, String str4) {
        String str5;
        int i = 0;
        String strG = h.g(hq("transaction"), true);
        String str6 = "JW_Federation: Mercury_ReceiveSecureBundle: Service Url: " + strG;
        if (strG == null || strG.equals("")) {
            if (bwM != null) {
                bwM.a(23, null, bwO.Bg, bwO.acH, bwO.acI, str4);
            }
            Wl();
            return null;
        }
        e eVar = new e();
        String[] strArr = {"transactions", "me", "gift_bundles"};
        String[] strArr2 = {"access_token", "pointcut_action_id", "bundle", "delivery_type"};
        String[] strArr3 = {Wp(), str, str2, str3};
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if ("clientId".equals(strArr[i2])) {
                strG = strG + "/" + h.bc(gX());
            } else {
                strG = strG + "/" + h.bc(strArr[i2]);
            }
        }
        if (strArr2 == null) {
            str5 = "";
        } else {
            String strA = "";
            int i3 = 0;
            while (true) {
                int i4 = i;
                if (i3 >= strArr2.length) {
                    break;
                }
                if ("clientId".equals(strArr2[i3])) {
                    strA = h.a(strA, "client_id", gX(), true);
                    i = i4;
                } else {
                    if (strArr3[i4] != null) {
                        strA = h.a(strA, strArr2[i3], strArr3[i4], true);
                    }
                    i = i4 + 1;
                }
                i3++;
            }
            str5 = "" + strA;
        }
        a(strG, str5, 1, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (bwM != null) {
                    bwM.a(23, null, eVar.Bg, eVar.acH, eVar.acI, str4);
                }
                g gVar = new g();
                a(gVar, eVar);
                Wl();
                return gVar;
            }
        } while (eVar.BM == null);
        if (bwM != null) {
            bwM.a(23, eVar.BM, eVar.Bg, eVar.acH, eVar.acI, str4);
        }
        b(eVar);
        g gVar2 = new g();
        a(gVar2, eVar);
        Wl();
        return gVar2;
    }

    public static boolean a(String str, int i, String str2, String str3, String str4) {
        String str5;
        if (LZ == null) {
            return false;
        }
        String strWk = Wk();
        if (strWk == null || strWk.equals("")) {
            if (bwM != null) {
                bwM.a(2, null, bwO.Bg, bwO.acH, bwO.acI, null);
            }
            Wl();
            return false;
        }
        int i2 = 0;
        e eVar = new e();
        com.gameloft.android2d.d.b bVarQm = com.gameloft.android2d.d.b.Qm();
        String[] strArr = {"connectionstatus"};
        String[] strArr2 = {"type", AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, NativeProtocol.WEB_DIALOG_ACTION, "ggi", "domain", "connectionstatus", "game_version", "device_id", "port", "client_id"};
        String[] strArr3 = {str, "" + i, "logConnectStatus", Ng, str2, str4, Nh, bVarQm.ga(), str3, gX()};
        for (int i3 = 0; i3 < strArr.length; i3++) {
            if ("clientId".equals(strArr[i3])) {
                strWk = strWk + "/" + h.bc(gX());
            } else {
                strWk = strWk + "/" + h.bc(strArr[i3]);
            }
        }
        if (strArr2 == null) {
            str5 = "";
        } else {
            String strA = "";
            for (int i4 = 0; i4 < strArr2.length; i4++) {
                if ("clientId".equals(strArr2[i4])) {
                    strA = h.a(strA, "client_id", gX(), true);
                } else {
                    if (strArr3[i2] != null) {
                        strA = h.a(strA, strArr2[i4], strArr3[i2], true);
                    }
                    i2++;
                }
            }
            str5 = "" + strA;
        }
        a(strWk, str5, 1, eVar);
        do {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
            }
            if (a(eVar)) {
                if (bwM != null) {
                    bwM.a(27, null, eVar.Bg, eVar.acH, eVar.acI, null);
                }
                Wl();
                if (eVar != null) {
                }
                return false;
            }
        } while (eVar.BM == null);
        b(eVar);
        String strHu = h.hu(h.ht(eVar.BM));
        if (bwM != null) {
            bwM.a(27, strHu, eVar.Bg, eVar.acH, eVar.acI, null);
        }
        Wl();
        if (eVar != null) {
        }
        return true;
    }
}
