package com.gameloft.android2d.iap;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.android2d.iap.a.a.ai;
import com.gameloft.glads.AndroidWebView;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.microedition.midlet.MIDlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
public class b implements a {
    public static JSONObject bkq;
    public static Set<String> bkw;
    javax.microedition.lcdui.q bjJ;
    private com.gameloft.android2d.iap.utils.w bjq;
    Activity mainActivity;
    private static MIDlet bjn = null;
    private static String bjo = null;
    private static final b bjp = new b();
    static int bjy = 0;
    private static String bjA = "IAP.Version: 1.2.0";
    public static String bjI = null;
    public static String bjK = null;
    public static boolean bjL = false;
    public static String bjM = null;
    public static boolean bjN = false;
    public static boolean bjO = false;
    public static boolean Lz = false;
    public static boolean bjP = false;
    public static boolean bjQ = false;
    public static boolean bjR = false;
    public static boolean bjS = false;
    public static boolean bjT = false;
    private static String bjU = null;
    private static String Ma = null;
    private static long bjV = -2000;
    private static long bjW = 0;
    private static long bjX = 0;
    public static boolean bjY = false;
    public static boolean bjZ = false;
    public static boolean bka = false;
    private static boolean bkb = false;
    private static int bkc = 0;
    public static boolean bkd = false;
    public static boolean bke = false;
    public static boolean bkf = false;
    public static boolean bkg = false;
    public static boolean bkh = false;
    private static boolean bki = false;
    private static boolean bkj = false;
    public static long bkk = 0;
    public static boolean bkl = false;
    public static String bkm = "";
    public static String bkn = "";
    public static String bko = "";
    public static String bkp = "";
    public static String bkr = "application/com.gameloft.ecomm.android.iap-v1.3+plain";
    public static String bks = "text/html";
    public static boolean bkt = false;
    public static boolean bku = false;
    public static boolean bkv = true;
    public static int bkx = -1;
    private static boolean bky = false;
    private static int bkz = 0;
    private static int bkA = 0;
    private static int bkB = 0;
    private static int bkC = 0;
    private static String bkD = null;
    private static com.gameloft.android2d.iap.a.a.o bkE = null;
    private static boolean bkF = false;
    public static int bkG = -1;
    public static ArrayList<String> bkK = new ArrayList<>();
    private static String bkL = null;
    public static int bkM = 0;
    public static String bkN = null;
    public static String bkO = null;
    public static boolean bkP = false;
    private static JSONArray bkQ = new JSONArray();
    public static final String[] bkR = {"40412|40407|40404|40424|40482|40419|40478|40422|405799|40487|40489|40456|405852|405850|40444|40414|405848|405853|405845|405849|405846"};
    private boolean enabled = true;
    private boolean bjr = false;
    private String bjs = "";
    private int state = 0;
    private String bjt = "";
    private int bju = -1;
    private String bjv = "";
    private String bjw = "";
    private int bjx = 0;
    private boolean bjz = false;
    private String bjB = "";
    private boolean bjC = false;
    private boolean bjD = false;
    private boolean bjE = false;
    private int bjF = 0;
    private String bjG = "";
    private String bjH = "";
    private boolean bkH = false;
    private boolean bkI = false;
    private boolean Lr = false;
    private boolean bkJ = false;

    private b() {
    }

    public static void Qn() {
        Lz = true;
        setResult(10);
        mB(0);
        if (bkl) {
            ae.ME();
            bkl = false;
        }
        if (iD()) {
            setResult(2);
        }
        QU();
    }

    public static void setResult(int i) {
        int i2 = bjp.state;
        bjp.state = i;
        if (bjp.state == 2 || bjp.state == 10) {
            QH();
        }
        if (bjp.state == 11) {
            bkm = "";
            bkl = false;
        }
        if (i2 == 7) {
            com.gameloft.android2d.iap.utils.u.D("", 39);
        }
    }

    public static Activity Qo() {
        return bjp.mainActivity;
    }

    public static MIDlet Qp() {
        return bjn;
    }

    public static int Qq() {
        return bjp.state;
    }

    public static void mB(int i) {
        bjp.bjx = i;
    }

    public static com.gameloft.android2d.iap.utils.x Qr() {
        return bjp.bjq.Qr();
    }

    public static String Qs() {
        bjp.bjw = com.gameloft.android2d.iap.utils.u.Tu();
        return bjp.bjw;
    }

    public static String Qt() {
        return com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgY));
    }

    public static String Oe() {
        return bjp.bjt;
    }

    public static String Qu() {
        return bjp.bjs;
    }

    public static boolean Qv() {
        return com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgV)) != null && com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgV)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
    }

    public static boolean Qw() {
        return com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgU)) != null && com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgU)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
    }

    public static boolean Qx() {
        return com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgX)) != null && com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgX)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
    }

    public static boolean Qy() {
        return com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgW)) != null && com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgW)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
    }

    public static boolean Qz() {
        return bjp.bjC;
    }

    public static boolean QA() {
        return bjp.bjD;
    }

    public static boolean QB() {
        return bjp.bjE;
    }

    public static String QC() {
        return eL(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgm));
    }

    public static boolean QD() {
        return bky;
    }

    public static void eD(String str) {
        bjU = str;
        if (str == null || str.indexOf("anonymous") == -1) {
        }
    }

    public static String QE() {
        return bjo;
    }

    public static boolean QF() {
        return bjP;
    }

    public static void QG() {
        bjP = true;
    }

    public static void QH() {
        bjP = false;
    }

    public static void QI() {
        bjp.bkI = false;
        bjp.bkH = false;
        bjp.bjw = com.gameloft.android2d.iap.utils.v.cn(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, 9999);
        com.gameloft.android2d.iap.utils.u.af(bjj[1], bjp.bjw);
        bjp.Lr = false;
        com.gameloft.android2d.iap.utils.u.af(bjj[0], AppEventsConstants.EVENT_PARAM_VALUE_NO);
        bjp.bkJ = false;
        com.gameloft.android2d.iap.utils.u.af(bjj[4], AppEventsConstants.EVENT_PARAM_VALUE_NO);
        com.gameloft.android2d.iap.utils.u.af(bjj[5], AppEventsConstants.EVENT_PARAM_VALUE_NO);
        QH();
    }

    public static void a(Activity activity, boolean z, String str, boolean z2, boolean z3, int i, boolean z4, String str2, boolean z5, boolean z6, boolean z7, boolean z8, int i2, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, MIDlet mIDlet, boolean z14) {
        b bVar = bjp;
        bkb = z8;
        b bVar2 = bjp;
        bkc = i2;
        b bVar3 = bjp;
        bkt = z14;
        if (Build.VERSION.SDK_INT > 22) {
            b bVar4 = bjp;
            bkc = 2;
        }
        com.gameloft.android2d.iap.utils.v.an(activity);
        bjp.mainActivity = (Activity) com.gameloft.android2d.iap.utils.v.getContext();
        bjp.bjC = z;
        bjp.bjD = z2;
        bjp.bjE = z3;
        bjp.bjF = i;
        bjn = mIDlet;
        Rd();
        iD();
        Locale locale = Locale.getDefault();
        bjp.bjG = locale.getLanguage().toLowerCase(Locale.US);
        bjp.bjH = locale.toString().toLowerCase(Locale.US);
        bjp.bjB = com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgT));
        bjp.bjt = com.gameloft.android2d.iap.utils.v.TK();
        bjp.bjs = com.gameloft.android2d.iap.utils.v.TL();
        bjp.bjw = Qs();
        if (bjp.bjw.equals("")) {
            bjp.bjw = com.gameloft.android2d.iap.utils.v.cn(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, 9999);
            com.gameloft.android2d.iap.utils.u.af(bjj[1], bjp.bjw);
        }
        bjI = str;
        bkd = z12;
        bke = z13;
        bkf = false;
        bjL = z4;
        bjM = str2;
        bjN = z5;
        bjO = z6;
        bjY = z7;
        bjZ = z10;
        bka = z11;
        if (!Qv()) {
            bjO = true;
            bjY = false;
            bjZ = false;
            bka = false;
        }
        String strGh = com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgC));
        bky = strGh != null && strGh.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
        if (z9) {
            bki = true;
            bkj = true;
        }
        bjp.bjz = true;
        Lz = false;
        if (bjA != null) {
            bjA += "";
        }
        bjp.bjq = new com.gameloft.android2d.iap.utils.w();
        com.gameloft.android2d.iap.utils.d.SA();
        bjS = false;
    }

    public static boolean QJ() {
        return bjp.enabled && !com.gameloft.android2d.iap.utils.u.Tv();
    }

    public static boolean QK() {
        return bkj;
    }

    public static int QL() {
        if (bjp == null) {
            return 0;
        }
        b bVar = bjp;
        return bkc;
    }

    public static void iE() {
        if (!bjp.bjz) {
            setResult(11);
            mB(-8);
            return;
        }
        if (Qt() == null || Qt().equals("")) {
            setResult(11);
            mB(-15);
            return;
        }
        if (Lz) {
            setResult(10);
            mB(0);
            if (iD()) {
                setResult(2);
            }
            new Thread(new c()).start();
            return;
        }
        bkm = "";
        bkk = System.currentTimeMillis();
        b bVar = bjp;
        bVar.getClass();
        new Thread(new ac(bVar, null)).start();
        if (bke && !bkf) {
            new k().start();
        }
        setResult(9);
    }

    public static String b(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.SM().RB();
        } catch (Exception e) {
            return null;
        }
    }

    public static String f(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.SM().Rw();
        } catch (Exception e) {
            return null;
        }
    }

    public static int iz() {
        if (bjp.bju == -1) {
            bjp.bju = com.gameloft.android2d.iap.utils.u.Tj();
        }
        return bjp.bju;
    }

    public static int QM() {
        return i(iz(), iH());
    }

    public static long iI() {
        return com.gameloft.android2d.iap.utils.u.Tq();
    }

    public static int i(int i, String str) {
        int i2 = 0;
        if (i == -1) {
            return -1;
        }
        int iAB = aB(str);
        try {
            if (str.equalsIgnoreCase(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgz))) {
                while (i2 < iAB) {
                    if (bjp.bjq.Qr().mK(i2) == i) {
                        return i2;
                    }
                    i2++;
                }
                i2 = -1;
                return i2;
            }
            if (str.equalsIgnoreCase(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgA))) {
                while (i2 < iAB) {
                    if (bjp.bjq.Qr().mL(i2) == i) {
                        return i2;
                    }
                    i2++;
                }
                return -1;
            }
            if (bjp.bjq.Qr().gG(str)) {
                return i;
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static String P(int i, String str) {
        return AppEventsConstants.EVENT_PARAM_VALUE_NO;
    }

    public static String iH() {
        if (bjp.bjv.equals("")) {
            return com.gameloft.android2d.iap.utils.u.Tk();
        }
        return bjp.bjv;
    }

    public static boolean eE(String str) {
        return str.equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgO)) || str.equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgP)) || str.equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgN));
    }

    public static void ab(String str, String str2) {
    }

    public static void b(int i, String str, long j) {
        try {
            setResult(1);
            bjp.bju = i;
            bjp.bjv = str;
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(bjp.bjv.toLowerCase()).get(Integer.valueOf(bjp.bju));
            int iRT = rVar.SM().RT();
            String strF = f(i, str);
            if ((!com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgo).equals(strF) && !com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgG).equals(strF) && !eE(strF)) || ((iRT == 255 && !eE(strF)) || com.gameloft.android2d.iap.utils.u.Tt() || (eE(strF) && rVar.SN() == 1))) {
                c(bjp.bju, bjp.bjv, j);
                return;
            }
            bjp.bjq.Qr().RT();
            bjp.mainActivity.runOnUiThread(new n(i, str, j));
            bjp.mainActivity.runOnUiThread(new s());
        } catch (Exception e) {
            setResult(3);
        }
    }

    public static void c(int i, String str, long j) {
        try {
            if (!QF()) {
                if (!com.gameloft.android2d.iap.utils.u.Tt()) {
                    ae.lv();
                }
                QG();
                setResult(1);
                new Thread(new t(str, i, j)).start();
            }
        } catch (Exception e) {
            setResult(3);
        }
    }

    public static boolean cg(int i) {
        return L(i, false);
    }

    public static boolean L(int i, boolean z) {
        boolean zAh = false;
        bjp.bjw = Qs();
        if (bjp.bjw != null && bjp.bjw.length() > 0) {
            zAh = com.gameloft.android2d.iap.utils.v.ah(String.valueOf(i), bjp.bjw);
        }
        if (zAh) {
            if (bjp.state == 2 && com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgG).equals(com.gameloft.android2d.iap.utils.u.Ty())) {
                com.gameloft.android2d.iap.utils.u.D(com.gameloft.android2d.iap.utils.v.gj(i + ""), 39);
            }
            if (!z) {
                setResult(7);
            }
            QI();
        }
        return zAh;
    }

    public static boolean eF(String str) {
        return n(str, false);
    }

    public static boolean n(String str, boolean z) {
        boolean zAh = false;
        bjp.bjw = Qs();
        if (bjp.bjw != null && bjp.bjw.length() > 0) {
            zAh = com.gameloft.android2d.iap.utils.v.ah(str, bjp.bjw);
        }
        if (zAh) {
            if (bjp.state == 2 && com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgG).equals(com.gameloft.android2d.iap.utils.u.Ty())) {
                com.gameloft.android2d.iap.utils.u.D(com.gameloft.android2d.iap.utils.v.gj(str + ""), 39);
            }
            if (!z) {
                setResult(7);
            }
            QI();
        }
        return zAh;
    }

    public static void iA() {
        bjp.bkJ = true;
        com.gameloft.android2d.iap.utils.u.af(bjj[4], AppEventsConstants.EVENT_PARAM_VALUE_YES);
        com.gameloft.android2d.iap.a.a aVarFo = com.gameloft.android2d.iap.a.b.fo(com.gameloft.android2d.iap.utils.u.Ty());
        aVarFo.fl(com.gameloft.android2d.iap.utils.u.TJ());
        int iTG = com.gameloft.android2d.iap.utils.u.TG();
        boolean zEqualsIgnoreCase = aVarFo.RS().equalsIgnoreCase(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cga));
        if (iTG != 7 && !zEqualsIgnoreCase) {
            aVarFo.eP(aVarFo.RY());
            return;
        }
        setResult(1);
        bjp.mainActivity.runOnUiThread(new u(zEqualsIgnoreCase, iTG, aVarFo));
        bjp.mainActivity.runOnUiThread(new y());
    }

    public static int iC() {
        int i = bjp.state;
        if (bjp.bjz) {
            iD();
            if (i == 3 || i == 7) {
                bjp.state = 0;
                com.gameloft.android2d.iap.utils.u.af(bjj[5], AppEventsConstants.EVENT_PARAM_VALUE_NO);
                QH();
                if (i == 7) {
                    QQ();
                    Rc();
                }
                if (i != 3 || getError() != -1) {
                    hM();
                }
            } else if (i == 11) {
                bjp.state = 0;
            } else if (i == 0) {
                com.gameloft.android2d.iap.utils.u.af(bjj[5], AppEventsConstants.EVENT_PARAM_VALUE_NO);
            }
        }
        return i;
    }

    public static void QN() {
        Lz = false;
        bkm = "";
    }

    public static int aB(String str) {
        try {
            return bjp.bjq.Qr().gm(str.toLowerCase()).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public static int j(int i, String str) {
        int iGF = -1;
        try {
            if (str.equalsIgnoreCase(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgz))) {
                iGF = bjp.bjq.Qr().mK(i);
            } else if (str.equalsIgnoreCase(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgA))) {
                iGF = bjp.bjq.Qr().mL(i);
            } else if (str.equalsIgnoreCase(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgy))) {
                iGF = bjp.bjq.Qr().mM(i);
            } else if (str.equalsIgnoreCase(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgB))) {
                iGF = bjp.bjq.Qr().mN(i);
            } else if (bjp.bjq.Qr().gG(str)) {
                iGF = bjp.bjq.Qr().gF(str);
            }
        } catch (Exception e) {
        }
        return iGF;
    }

    public static boolean iD() {
        bjp.Lr = com.gameloft.android2d.iap.utils.u.Ts();
        bjp.bkJ = com.gameloft.android2d.iap.utils.u.Tt();
        return bjp.Lr || bjp.bkJ;
    }

    public static int getError() {
        return bjp.bjx;
    }

    public static String QO() {
        com.gameloft.android2d.iap.utils.r rVarNextElement;
        try {
            Hashtable<String, Hashtable<Integer, com.gameloft.android2d.iap.utils.r>> hashtableTV = bjp.bjq.Qr().TV();
            Enumeration<String> enumerationKeys = hashtableTV.keys();
            if (enumerationKeys != null) {
                while (enumerationKeys.hasMoreElements()) {
                    String strNextElement = enumerationKeys.nextElement();
                    if (strNextElement != null && !strNextElement.equals("")) {
                        Enumeration<com.gameloft.android2d.iap.utils.r> enumerationElements = hashtableTV.get(strNextElement).elements();
                        if (enumerationElements.hasMoreElements() && (rVarNextElement = enumerationElements.nextElement()) != null) {
                            return rVarNextElement.SM().Rx();
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void setLanguage(String str) {
        if (bjp.bjG != null && !bjp.bjG.equalsIgnoreCase(str)) {
            Lz = false;
            setResult(0);
        }
        bjp.bjG = str;
    }

    public static String getLanguage() {
        return bjp.bjG;
    }

    public static String getLocale() {
        return bjp.bjH;
    }

    public static int QP() {
        try {
            int iRT = bjp.bjq.Qr().RT();
            if (iRT == 17 || iRT == 16) {
                return iRT;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void QQ() {
        try {
            String strTn = com.gameloft.android2d.iap.utils.u.Tn();
            if (strTn != null && !strTn.equals("") && !com.gameloft.android2d.iap.utils.u.Ty().equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgb))) {
                float fGb = com.gameloft.android2d.iap.utils.v.gb(strTn);
                float fGb2 = 0.0f;
                String strTm = com.gameloft.android2d.iap.utils.u.Tm();
                if (strTm != null) {
                    fGb2 = com.gameloft.android2d.iap.utils.v.gb(strTm);
                }
                com.gameloft.android2d.iap.utils.u.ag(String.valueOf(fGb2 + fGb), com.gameloft.android2d.iap.utils.u.To());
            }
        } catch (Exception e) {
        }
    }

    public static String eG(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            String strTm = com.gameloft.android2d.iap.utils.u.Tm();
            if (strTm != null) {
                String str2 = str + "&" + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cix) + com.gameloft.android2d.iap.utils.aa.n(com.gameloft.android2d.iap.utils.aa.c(strTm.getBytes(), eL(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.ciy))));
                String strTl = com.gameloft.android2d.iap.utils.u.Tl();
                if (strTl == null || strTl.equals("")) {
                    str = str2 + "&" + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.civ) + com.gameloft.android2d.iap.utils.aa.n(com.gameloft.android2d.iap.utils.aa.c(com.gameloft.android2d.iap.utils.u.Tp().getBytes(), eL(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.ciy))));
                } else {
                    str = str2 + "&" + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.ciw) + strTl;
                }
            }
            return str;
        } catch (Exception e) {
            return str;
        }
    }

    public static long g(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return 0L;
            }
            return rVar.SO();
        } catch (Exception e) {
            return -1L;
        }
    }

    public static long l(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return 0L;
            }
            return rVar.SP();
        } catch (Exception e) {
            return -1L;
        }
    }

    public static String QR() {
        return bjp.bjq.Qr().TY();
    }

    public static String QS() {
        return bjp.bjq.Qr().TZ();
    }

    public static String k(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.SR();
        } catch (Exception e) {
            return null;
        }
    }

    public static float c(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return -1.0f;
            }
            return com.gameloft.android2d.iap.utils.v.gb(rVar.SM().getPrice());
        } catch (Exception e) {
            return -1.0f;
        }
    }

    public static float Q(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return -1.0f;
            }
            String strSQ = rVar.SQ();
            if (strSQ == null || strSQ.equals("")) {
                return -1.0f;
            }
            return com.gameloft.android2d.iap.utils.v.gb(strSQ);
        } catch (Exception e) {
            return -1.0f;
        }
    }

    public static long QT() {
        long jElapsedRealtime = (bjV - (SystemClock.elapsedRealtime() - bjW)) + 2000;
        if (bjX > 0 && jElapsedRealtime <= 0) {
            QN();
        }
        bjX = jElapsedRealtime;
        return jElapsedRealtime;
    }

    public static String R(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.getName();
        } catch (Exception e) {
            return null;
        }
    }

    public static void QU() {
        try {
            if (bkh) {
                bkh = false;
            } else {
                int iIz = iz();
                String strIH = iH();
                if (iIz >= 0 && strIH != null) {
                    String strTy = com.gameloft.android2d.iap.utils.u.Ty();
                    if (com.gameloft.android2d.iap.utils.v.gf(strTy)) {
                        com.gameloft.android2d.iap.a.a aVarFo = com.gameloft.android2d.iap.a.b.fo(strTy);
                        if (!aVarFo.RW() && aVarFo.RV()) {
                            if (!com.gameloft.android2d.iap.utils.d.Pd()) {
                                setResult(11);
                                mB(-1);
                            } else {
                                aVarFo.RU();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void hM() {
        com.gameloft.android2d.iap.utils.u.fR("");
        com.gameloft.android2d.iap.utils.u.fY("");
        com.gameloft.android2d.iap.utils.u.fS("");
        com.gameloft.android2d.iap.utils.u.fU("");
        com.gameloft.android2d.iap.utils.u.fT("");
        com.gameloft.android2d.iap.utils.u.fX("");
        com.gameloft.android2d.iap.utils.u.fV("");
    }

    public static boolean a(int i, int i2, Intent intent) {
        try {
            if (!bkj && (bjp.bjq.TT().bnP instanceof com.gameloft.android2d.iap.a.a.a)) {
                return com.gameloft.android2d.iap.a.a.a.a(i, i2, intent);
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static void onDestroy() {
        try {
            if (!bkj && (bjp.bjq.TT().bnP instanceof com.gameloft.android2d.iap.a.a.a)) {
                com.gameloft.android2d.iap.a.a.a.onDestroy();
            }
        } catch (Exception e) {
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0084 A[Catch: Exception -> 0x0036, TryCatch #0 {Exception -> 0x0036, blocks: (B:6:0x000f, B:8:0x0025, B:10:0x0032, B:15:0x003d, B:16:0x0045, B:18:0x004b, B:20:0x0076, B:21:0x007a, B:23:0x0084, B:24:0x0088, B:25:0x0090, B:27:0x0096, B:29:0x00bd), top: B:32:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:24:0x0088 A[Catch: Exception -> 0x0036, TryCatch #0 {Exception -> 0x0036, blocks: (B:6:0x000f, B:8:0x0025, B:10:0x0032, B:15:0x003d, B:16:0x0045, B:18:0x004b, B:20:0x0076, B:21:0x007a, B:23:0x0084, B:24:0x0088, B:25:0x0090, B:27:0x0096, B:29:0x00bd), top: B:32:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:27:0x0096 A[Catch: Exception -> 0x0036, TryCatch #0 {Exception -> 0x0036, blocks: (B:6:0x000f, B:8:0x0025, B:10:0x0032, B:15:0x003d, B:16:0x0045, B:18:0x004b, B:20:0x0076, B:21:0x007a, B:23:0x0084, B:24:0x0088, B:25:0x0090, B:27:0x0096, B:29:0x00bd), top: B:32:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:37:0x00bd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:38:? A[LOOP:1: B:25:0x0090->B:38:?, LOOP_END, SYNTHETIC] */
    public static void QV() {
        Iterator<ActivityManager.RunningAppProcessInfo> it;
        if (bkK.isEmpty()) {
            QW();
            bkM = 1;
            return;
        }
        try {
            ActivityManager activityManager = (ActivityManager) com.gameloft.android2d.iap.utils.v.getContext().getSystemService("activity");
            bkM = 0;
            if (Build.VERSION.SDK_INT <= 25) {
                if (activityManager.getRunningServices(Integer.MAX_VALUE).isEmpty()) {
                    bkM = 1;
                } else {
                    Iterator<ActivityManager.RunningServiceInfo> it2 = activityManager.getRunningServices(Integer.MAX_VALUE).iterator();
                    while (it2.hasNext()) {
                        if (bkK.contains(com.gameloft.android2d.iap.utils.v.ge(it2.next().service.getClassName() + bkL))) {
                            bkM = 1;
                        }
                    }
                    if (activityManager.getRunningAppProcesses().isEmpty()) {
                        bkM = 1;
                    } else {
                        it = activityManager.getRunningAppProcesses().iterator();
                        while (it.hasNext()) {
                            if (bkK.contains(com.gameloft.android2d.iap.utils.v.ge(it.next().processName + bkL))) {
                                bkM = 1;
                                break;
                            }
                        }
                    }
                }
            } else if (activityManager.getRunningAppProcesses().isEmpty()) {
                bkM = 1;
            } else {
                it = activityManager.getRunningAppProcesses().iterator();
                while (it.hasNext()) {
                    if (bkK.contains(com.gameloft.android2d.iap.utils.v.ge(it.next().processName + bkL))) {
                        bkM = 1;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            bkM = 1;
        }
    }

    static void QW() {
        try {
            bkL = eL(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgI));
            if (bkK.isEmpty()) {
                com.gameloft.android2d.iap.utils.y yVar = new com.gameloft.android2d.iap.utils.y(new com.gameloft.android2d.iap.utils.d(), true);
                yVar.gK(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgn));
                while (!yVar.Ul()) {
                    try {
                        Thread.sleep(20L);
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e2) {
        }
    }

    public static String QX() {
        return com.gameloft.android2d.iap.utils.u.Tp();
    }

    public static void eH(String str) {
        bjp.mainActivity.runOnUiThread(new z(str));
    }

    public static String S(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.SM().getCurrency();
        } catch (Exception e) {
            return null;
        }
    }

    public static String T(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.ST();
        } catch (Exception e) {
            return null;
        }
    }

    public static String U(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public static float iJ() {
        return com.gameloft.android2d.iap.utils.v.gb(com.gameloft.android2d.iap.utils.u.Tn());
    }

    public static void QY() {
    }

    public static String b(int i, String str, int i2) {
        String strRR;
        try {
            com.gameloft.android2d.iap.utils.x xVarQr = bjp.bjq.Qr();
            if (xVarQr == null) {
                strRR = null;
            } else if (i2 == 0) {
                if (!getLanguage().equalsIgnoreCase("ar") || (strRR = xVarQr.fk("tnc_title_shop")) == null || strRR.equals("")) {
                    strRR = xVarQr.Ud();
                }
            } else if (i2 == 5) {
                strRR = xVarQr.Ue();
            } else {
                com.gameloft.android2d.iap.a.a aVarSM = xVarQr.gm(str.toLowerCase()).get(Integer.valueOf(i)).SM();
                if (aVarSM == null) {
                    strRR = null;
                } else if (i2 == 1) {
                    strRR = aVarSM.RH();
                } else if (i2 == 2) {
                    strRR = aVarSM.RQ();
                } else {
                    strRR = i2 == 3 ? aVarSM.RR() : null;
                }
            }
            return strRR;
        } catch (Exception e) {
            return null;
        }
    }

    public static void B(String str, String str2, String str3) {
        if (str2 == null) {
            bkP = false;
            return;
        }
        String strReplace = str.replace("|", "");
        String strReplace2 = str3.replace("|", "");
        String strReplace3 = str2.replace("|", "").replace("\\n", System.getProperty("line.separator"));
        AlertDialog.Builder builder = new AlertDialog.Builder(com.gameloft.android2d.iap.utils.v.getContext());
        builder.setTitle(strReplace);
        builder.setMessage(strReplace3);
        builder.setPositiveButton(strReplace2, new d());
        builder.setOnKeyListener(new e());
        AlertDialog alertDialogCreate = builder.create();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialogCreate.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -1;
        alertDialogCreate.getWindow().setAttributes(layoutParams);
        alertDialogCreate.show();
        MIDlet.coD = 1;
        Display defaultDisplay = ((WindowManager) com.gameloft.android2d.iap.utils.v.getContext().getSystemService("window")).getDefaultDisplay();
        int height = defaultDisplay.getHeight();
        alertDialogCreate.getWindow().setLayout(defaultDisplay.getWidth(), height);
    }

    public static String QZ() {
        return com.gameloft.android2d.iap.utils.u.TF();
    }

    public static String Ra() {
        return eL(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgc));
    }

    public static void eI(String str) {
        bkn = str.replace("|", "");
    }

    public static String Rb() {
        return bkn;
    }

    public static void eJ(String str) {
        bko = str;
    }

    public static void eK(String str) {
        bkp = str;
    }

    public static int a(com.gameloft.android2d.iap.utils.r rVar) {
        String strTW = bjp.bjq.Qr().TW();
        if (QD()) {
            strTW = com.gameloft.android2d.iap.utils.v.mJ(com.b.d.ciG) + com.gameloft.android2d.iap.utils.v.TR() + "&" + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chg) + "=" + encodeString(Oe());
        }
        com.gameloft.android2d.iap.a.a aVarSM = rVar.SM();
        String str = "|" + rVar.getId() + "|" + aVarSM.getPrice() + "|" + aVarSM.getCurrency() + "|" + getLanguage().toLowerCase(Locale.US) + "|" + Qs();
        com.gameloft.android2d.iap.utils.y yVar = new com.gameloft.android2d.iap.utils.y(new com.gameloft.android2d.iap.utils.d(aVarSM.RF(), aVarSM.RG()), true);
        yVar.al(strTW, str);
        while (!yVar.Un()) {
            try {
                Thread.sleep(50L);
            } catch (Exception e) {
            }
        }
        if (yVar.Uj() == 403 || yVar.Uj() == 429) {
            ae.dD(yVar.gM("code"));
            eI(yVar.gM(ShareConstants.WEB_DIALOG_PARAM_MESSAGE));
            eJ(yVar.gM("next_transaction_time"));
            eK(yVar.gM("seconds_before_next_transaction"));
            return yVar.Uj();
        }
        if (yVar.Uj() != 0) {
            return -1000;
        }
        return 0;
    }

    public static void Rc() {
        try {
            String strTy = com.gameloft.android2d.iap.utils.u.Ty();
            if (!TextUtils.isEmpty(strTy)) {
                com.gameloft.android2d.iap.a.b.fo(strTy).Rc();
            }
        } catch (Exception e) {
        }
    }

    public static void Rd() {
        try {
            bkq = new JSONObject();
            JSONObject jSONObject = new JSONObject(com.gameloft.android2d.iap.utils.aa.an(new String(com.gameloft.android2d.iap.utils.v.p(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgv), true)), com.gameloft.android2d.iap.utils.v.E(com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cha)), 2)));
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String string = itKeys.next().toString();
                bkq.put(string, com.gameloft.android2d.iap.utils.aa.an(jSONObject.getString(string), com.gameloft.android2d.iap.utils.v.E(com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cha)), 1)));
            }
        } catch (Exception e) {
        }
    }

    public static String eL(String str) {
        try {
            if (bkq.has(str)) {
                return bkq.getString(str);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String encodeString(String str) {
        try {
            return URLEncoder.encode(str, AndroidWebView.UTF_8);
        } catch (Exception e) {
            return str;
        }
    }

    public static void Re() {
        if (!com.gameloft.android2d.iap.utils.d.Pd()) {
            ae.bm(false);
            return;
        }
        String strMJ = "";
        if (bjp.bjq.Qr() != null) {
            strMJ = bjp.bjq.Qr().TX();
        }
        if (!com.gameloft.android2d.iap.utils.v.gf(strMJ)) {
            strMJ = com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgS);
        }
        if (QD() || !com.gameloft.android2d.iap.utils.v.gf(strMJ)) {
            strMJ = com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgR);
        }
        String str = strMJ + com.gameloft.android2d.iap.utils.v.TS();
        com.gameloft.android2d.iap.utils.n.SJ();
        String string = new StringBuffer().toString();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.ciq), Qt());
        } catch (JSONException e) {
        }
        com.gameloft.android2d.iap.utils.n.SJ().a(str, string, jSONObject.toString(), new f());
    }

    public static void Rf() {
        if (bjp.bjq.Qr() != null && !bjp.bjq.Qr().TU()) {
            bkw = new HashSet();
            com.gameloft.android2d.iap.utils.u.D("", 60);
            com.gameloft.android2d.iap.utils.u.D("", 61);
            bjp.mainActivity.runOnUiThread(new g());
            return;
        }
        ae.e(null);
    }

    public static void Rg() {
        if (bkw != null) {
            String[] strArr = bkw.isEmpty() ? null : (String[]) bkw.toArray(new String[bkw.size()]);
            if (strArr != null) {
                for (int i = 0; i < strArr.length; i++) {
                }
            }
            ae.e(strArr);
        }
    }

    public static void k(String str, String str2, String str3, String str4) {
        com.gameloft.android2d.iap.utils.r rVarGn;
        com.gameloft.android2d.iap.a.a aVarSM;
        try {
            if (bjp.bjq.Qr() != null && !bjp.bjq.Qr().TU() && (aVarSM = (rVarGn = bjp.bjq.Qr().gn(str)).SM()) != null) {
                aVarSM.i(rVarGn.getId(), aVarSM.getPrice(), aVarSM.Rz(), aVarSM.getURL(), str2, str3);
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(com.gameloft.android2d.iap.utils.r rVar) {
        int i = com.b.c.cfE;
        if (rVar != null) {
            com.gameloft.android2d.iap.a.a aVarSM = rVar.SM();
            int iRT = aVarSM.RT();
            String strSU = rVar.SU();
            if (!(strSU == null || strSU.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) || rVar.getType().equalsIgnoreCase(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgy))) {
                i = com.b.c.cfF;
            }
            if (iRT == 2 || iRT == 5) {
                return com.b.c.cfJ;
            }
            if (iRT == 17 && com.gameloft.android2d.iap.utils.v.gf(aVarSM.RK())) {
                return com.b.c.cfG;
            }
            return i;
        }
        return i;
    }

    public static void Rh() {
        com.gameloft.android2d.iap.utils.v.getContext().unregisterReceiver(bkE);
    }

    public static void Ri() {
        bkE = new com.gameloft.android2d.iap.a.a.o(new ai());
        IntentFilter intentFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
        if (intentFilter != null) {
            com.gameloft.android2d.iap.utils.v.getContext().registerReceiver(bkE, intentFilter);
        }
        new Thread(new h()).start();
    }

    public static void Rj() {
    }

    public static void Rk() {
    }

    public static void Rl() {
    }

    public static void eM(String str) {
        com.gameloft.android2d.iap.utils.u.D(str, 47);
    }

    public static String Rm() {
        return com.gameloft.android2d.iap.utils.u.mG(47);
    }

    public static void a(String str, HashMap<String, String> map) {
        com.gameloft.android2d.iap.utils.n nVarSJ = com.gameloft.android2d.iap.utils.n.SJ();
        String string = "";
        try {
            string = new JSONObject(map).toString();
        } catch (Exception e) {
        }
        nVarSJ.a(str, "", string, new i());
    }

    public static String Rn() {
        return com.gameloft.android2d.iap.utils.u.TH();
    }

    public static int Ro() {
        if (bkC > 0) {
            return bkC;
        }
        return 0;
    }

    public static String Rp() {
        return bkD != null ? bkD.toLowerCase() : "";
    }

    public static void eN(String str) {
        String str2 = "";
        if (str.equals("android.permission.SEND_SMS")) {
            str2 = ("" + com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfP)) + "\n" + com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfQ);
        } else if (str.equals("android.permission.READ_PHONE_STATE")) {
            str2 = ("" + com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfO)) + "\n" + com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfQ);
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(com.gameloft.android2d.iap.utils.v.getContext()).setNegativeButton(com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfR), new l()).setPositiveButton(com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfM), new j()).setTitle(com.gameloft.android2d.iap.utils.v.mI(com.b.d.cfN)).setMessage(str2).create();
        alertDialogCreate.setCancelable(false);
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setOnKeyListener(new m());
        alertDialogCreate.show();
    }

    public static String a(String str, com.gameloft.android2d.iap.utils.r rVar) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            str = str.replace("<formatted_price>", rVar.SM().RA()).replace("<shortcode>", rVar.SM().RC()).replace("<virtual_currency_name>", rVar.getName()).replace("<content_name>", rVar.getName());
            return str.replace("<iap_pack_amount>", rVar.SO() + "");
        } catch (Exception e) {
            return str;
        }
    }

    public static String V(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.SX();
        } catch (Exception e) {
            return null;
        }
    }

    public static String W(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.SY();
        } catch (Exception e) {
            return null;
        }
    }

    public static String X(int i, String str) {
        try {
            com.gameloft.android2d.iap.utils.r rVar = bjp.bjq.Qr().gm(str.toLowerCase()).get(Integer.valueOf(i));
            if (rVar == null) {
                return null;
            }
            return rVar.SZ();
        } catch (Exception e) {
            return null;
        }
    }

    public static void eO(String str) {
        try {
            bkQ.put(new JSONObject(str));
        } catch (Exception e) {
        }
    }

    public static boolean Rq() {
        String strMG = com.gameloft.android2d.iap.utils.u.mG(59);
        return strMG != null && strMG.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
    }
}
