package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.os.SystemClock;
import com.facebook.appevents.AppEventsConstants;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class n {
    public static q[] LD;
    private static long LE;
    public static int LF;
    public static String LG;
    public static int Lp = 0;
    public static int Lq = 10000;
    public static boolean Lr = false;
    public static String[] Ls = {"isSMSSent", "packageID", "packageType"};
    public static String Lt = "FAKE_IAP";
    public static int Lu = 0;
    public static int Lv = 1;
    public static int Lw = 2;
    public static am Lx = new am();
    public static long Ly = 0;
    public static boolean Lz = false;
    public static String LA = "en";
    public static String[][] LB = {new String[]{"Cash", "5000", "VND", AppEventsConstants.EVENT_PARAM_VALUE_YES, " Input terms and condition here .....", "50"}, new String[]{"Cash", "10000", "VND", "2", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "100"}, new String[]{"Cash", "15000", "VND", "3", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "200"}, new String[]{"Cash", "20000", "VND", "4", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "300"}, new String[]{"Cash", "25000", "VND", "5", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "400"}, new String[]{"Cash", "30000", "VND", "6", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "500"}, new String[]{"Coin", "5000", "VND", AppEventsConstants.EVENT_PARAM_VALUE_YES, "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "1000"}, new String[]{"Coin", "10000", "VND", "2", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "2000"}, new String[]{"Coin", "15000", "VND", "3", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "3000"}, new String[]{"Coin", "20000", "VND", "4", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "4000"}, new String[]{"Coin", "25000", "VND", "5", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "5000"}, new String[]{"Coin", "30000", "VND", "6", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", "6000"}, new String[]{"direct iap 1", "30000", "VND", AppEventsConstants.EVENT_PARAM_VALUE_YES, "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", AppEventsConstants.EVENT_PARAM_VALUE_NO}, new String[]{"direct iap 2", "30000", "VND", AppEventsConstants.EVENT_PARAM_VALUE_YES, "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607", AppEventsConstants.EVENT_PARAM_VALUE_NO}};
    private static String[][] LC = {new String[]{"EN", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"DE", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"FR", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"IT", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"ES", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"PT", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"RU", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"TR", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"PL", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"TH", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"VI", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}, new String[]{"US", "The cost of these additional features varies depending on the item you wish to purchase and your mobile operator. Message & data rates may apply depending on your plan. In-app purchases that are consumed during the use of the game cannot be transferred among devices, can only be downloaded once, and cannot be replaced after they have been downloaded. This is not a subscription service. Complete Terms and Conditions: gameloft.com/conditions. For help, email: android.support@gameloft.com or call: +56-225708607"}};
    public static r LH = new r(null);

    static String a(String str, String str2, String str3, int[] iArr) {
        int iIndexOf = str.indexOf(str2, iArr[1]);
        if (iIndexOf == -1) {
            return "";
        }
        iArr[0] = iIndexOf + str2.length();
        iArr[1] = str.indexOf(str3, iArr[0]);
        return str.substring(iArr[0], iArr[1]);
    }

    public static int aB(String str) {
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 >= LD.length) {
                debug("FakeIAPLib:: GetItemNumber. number = " + i3 + ", type = " + str);
                return i3;
            }
            i = LD[i2].type.toUpperCase().equals(str.toUpperCase()) ? i3 + 1 : i3;
            i2++;
        }
    }

    static ItemFeed[] aC(String str) {
        int iIndexOf = 0;
        q[] qVarArr = new q[50];
        int[] iArr = new int[2];
        String strA = a(str, "<promo_endtime>", "<", iArr);
        if (strA.equals("")) {
            LH.LQ = "";
        } else {
            LH.LQ = strA;
        }
        String strA2 = a(str, "<server_time>", "<", iArr);
        if (strA2.equals("")) {
            LH.LR = "";
        } else {
            LH.LR = strA2;
        }
        while (true) {
            int iIndexOf2 = str.indexOf("<content id=", iIndexOf);
            if (iIndexOf2 == -1) {
                break;
            }
            iIndexOf = str.indexOf("</content>", "<content id=".length() + iIndexOf2);
            qVarArr[iIndexOf] = aD(str.substring(iIndexOf2, iIndexOf));
            iIndexOf++;
        }
        q[] qVarArr2 = new q[iIndexOf];
        for (int i = 0; i < iIndexOf; i++) {
            qVarArr2[i] = qVarArr[i];
        }
        while (iIndexOf < iIndexOf - 1) {
            if (qVarArr2[iIndexOf].LL == "") {
                qVarArr2[iIndexOf].LL = "-1";
            }
            for (int i2 = iIndexOf + 1; i2 < iIndexOf; i2++) {
                if (qVarArr2[i2].LL == "") {
                    qVarArr2[i2].LL = "-1";
                }
                if (Integer.parseInt(qVarArr2[iIndexOf].LL) > Integer.parseInt(qVarArr2[i2].LL)) {
                    q qVar = qVarArr2[iIndexOf];
                    qVarArr2[iIndexOf] = qVarArr2[i2];
                    qVarArr2[i2] = qVar;
                }
            }
            iIndexOf++;
        }
        return qVarArr2;
    }

    static ItemFeed aD(String str) {
        int[] iArr = new int[2];
        q qVar = new q(null);
        qVar.type = a(str, "type=\"", "\">", iArr);
        qVar.name = a(str, "name\"><![CDATA[", "]]>", iArr);
        qVar.LP = a(str, "old_amount\">", "<", iArr);
        qVar.LO = a(str, "amount\">", "<", iArr);
        qVar.LJ = a(str, "old_price\">", "<", iArr);
        qVar.LK = a(str, "effective_discount\">", "<", iArr);
        qVar.LL = a(str, "<price_point>", "<", iArr);
        qVar.LI = a(str, "<price>", "<", iArr);
        qVar.currency = a(str, "<currency>", "<", iArr);
        qVar.LN = a(str, "<formatted_price><![CDATA[", "]]>", iArr);
        qVar.LM = a(str, "<tnc><![CDATA[", "]]>", iArr);
        return qVar;
    }

    protected static long aE(String str) {
        if (str.equals("")) {
            return 0L;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Integer.parseInt(str.substring(0, 4)));
        calendar.set(2, Integer.parseInt(str.substring(5, 7)) - 1);
        calendar.set(5, Integer.parseInt(str.substring(8, 10)));
        calendar.set(11, Integer.parseInt(str.substring(11, 13)));
        calendar.set(12, Integer.parseInt(str.substring(14, 16)));
        calendar.set(13, Integer.parseInt(str.substring(17, 19)));
        calendar.set(14, 0);
        return calendar.getTime().getTime();
    }

    public static String b(int i, String str) {
        return h(i, str).LN;
    }

    public static float c(int i, String str) {
        try {
            return Float.parseFloat(h(i, str).LI);
        } catch (Exception e) {
            return 0.0f;
        }
    }

    public static boolean cg(int i) {
        if (i != 12345) {
            GLLib.be("\n\n **************** Verify code failed, please use fake code: 12345 ****************\n\n");
            return false;
        }
        GLLib.be("\n\n **************** Verify code success with fake code: 12345 ****************\n\n");
        setResult(7);
        return true;
    }

    public static float d(int i, String str) {
        try {
            return Float.parseFloat(h(i, str).LJ);
        } catch (Exception e) {
            return 0.0f;
        }
    }

    public static void debug(String str) {
    }

    public static void e(int i, String str) {
        LF = i;
        LG = str;
        debug("\n\n **************** FAKE_IAP::IAP_REQUEST_WAITING ****************\n\n");
        debug("FakeIAPLib:: SendRequest: pricePoint = " + i + "   itemType = " + str);
        setResult(1);
        new Thread(new o()).start();
    }

    public static String f(int i, String str) {
        return "sms_2d";
    }

    public static long g(int i, String str) {
        ItemFeed itemFeedH = h(i, str);
        if (itemFeedH.type.equals("")) {
            return 0L;
        }
        return Integer.parseInt(itemFeedH.LO);
    }

    public static int getError() {
        return 0;
    }

    public static ItemFeed h(int i, String str) {
        if (LD == null) {
            iF();
        }
        String lowerCase = str.toLowerCase();
        for (int i2 = 0; i2 < LD.length; i2++) {
            if (Integer.parseInt(LD[i2].LL) == i && LD[i2].type.toLowerCase().equals(lowerCase)) {
                return LD[i2];
            }
        }
        return new q(null);
    }

    public static int i(int i, String str) {
        int i2 = 0;
        if (LD == null) {
            return -1;
        }
        String lowerCase = str.toLowerCase();
        int i3 = 0;
        while (true) {
            int i4 = i2;
            if (i3 >= LD.length) {
                return -1;
            }
            if (!LD[i3].type.toLowerCase().equals(lowerCase)) {
                i2 = i4;
            } else {
                if (Integer.parseInt(LD[i3].LL) == i) {
                    return i4;
                }
                i2 = i4 + 1;
            }
            i3++;
        }
    }

    public static void iA() {
        e(LF, LG);
    }

    public static boolean iB() {
        String strBY = GLLib.Qu.bY("IAP-Show-Redeem");
        return (strBY == null || strBY.equals("")) ? w.ZH : strBY.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
    }

    public static boolean iD() {
        iG();
        return Lr;
    }

    public static void iE() {
        if (Lz) {
            setResult(10);
            if (Lr) {
                setResult(2);
                return;
            }
            return;
        }
        if (Lp != 9) {
            debug("FakeIAPLib:: GetFeed----------- just show location of IAP_GetFeed ----------()");
            debug("\n\n **************** IAP_WAITING_FOR_FEED ****************\n\n");
            setResult(9);
            new Thread(new p()).start();
        }
    }

    public static void iG() {
        byte[] bArrBF = GLLib.bF(Lt);
        if (bArrBF == null || bArrBF.length == 0) {
            return;
        }
        Lr = true;
        LF = bArrBF[Lv];
        LG = bArrBF[Lv] == 1 ? "Cash" : "Coin";
    }

    public static String iH() {
        return LG;
    }

    public static long iI() {
        return g(LF, LG);
    }

    public static float iJ() {
        return c(LF, LG);
    }

    public static int iz() {
        return w.ZD ? i(LF, LG) : LF;
    }

    public static int j(int i, String str) {
        if (LD == null) {
            return -1;
        }
        q qVar = null;
        String lowerCase = str.toLowerCase();
        int i2 = 0;
        for (int i3 = 0; i3 < LD.length && qVar == null; i3++) {
            if (LD[i3].type.toLowerCase().equals(lowerCase)) {
                if (i == i2 || (!str.equals("Coin") && !str.equals("Cash"))) {
                    qVar = LD[i3];
                }
                i2++;
            }
        }
        if (qVar != null) {
            return Integer.parseInt(qVar.LL);
        }
        return -1;
    }

    public static String k(int i, String str) {
        if (w.ZI) {
            return h(i, str).LJ;
        }
        return null;
    }

    public static long l(int i, String str) {
        if (!w.ZI || h(i, str).LP.equals("")) {
            return 0L;
        }
        return Long.parseLong(h(i, str).LP);
    }

    static String m(int i, String str) {
        return h(i, str).currency;
    }

    public static void setLanguage(String str) {
        if (LA != null) {
            Lz = false;
            Lp = 0;
        }
        LA = str.toLowerCase();
        String upperCase = str.toUpperCase();
        for (int i = 0; i < LC.length; i++) {
            if (LC[i][0].equals(upperCase)) {
                for (int i2 = 0; i2 < LB.length; i2++) {
                    LB[i2][4] = LC[i][1];
                }
                return;
            }
        }
    }

    public static void setResult(int i) {
        Lp = i;
    }

    public static void iF() {
        debug("FakeIAPLib:: Init_Feed");
        Lz = false;
        String strBY = GLLib.Qu.bY("IAP-Feed-URL");
        String strBY2 = GLLib.Qu.bY("IAP-Feed-Query");
        String strBY3 = GLLib.Qu.bY("IAP-Feed-Imei");
        if (strBY == null || strBY2 == null || strBY.equals("") || strBY2.equals("")) {
            debug("FakeIAPLib:: Feed Url is null, use fake IAP.");
            LD = new q[LB.length];
            for (int i = 0; i < LB.length; i++) {
                q qVar = new q(null);
                qVar.type = LB[i][0];
                qVar.LI = LB[i][1];
                qVar.LJ = "" + ((int) ((Float.parseFloat(qVar.LI) * 2.0f) / 3.0f));
                qVar.currency = LB[i][2];
                qVar.LK = "30";
                qVar.LL = LB[i][3];
                qVar.name = qVar.type + "_" + qVar.LL;
                qVar.LM = LB[i][4];
                qVar.LN = qVar.LI + qVar.currency;
                qVar.LO = LB[i][5];
                qVar.LP = "" + ((int) (Float.parseFloat(qVar.LO) / 2.0f));
                LD[i] = qVar;
            }
        } else {
            if (strBY3 != null && !strBY3.equals("")) {
                strBY2 = strBY2 + "&tester_imei=" + strBY3;
            }
            int iIndexOf = strBY2.indexOf("&lang=");
            String str = strBY2.substring(0, iIndexOf) + "&lang=" + LA + strBY2.substring(strBY2.indexOf("&", iIndexOf + 1), strBY2.length());
            debug("FakeIAPLib:: Feed Url:" + strBY + "?" + str);
            Lx.c(strBY, str, 0);
            long jCurrentTimeMillis = 0;
            while (Lx.isInProgress()) {
                try {
                    Thread.sleep(50L);
                } catch (Exception e) {
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                }
                if (jCurrentTimeMillis2 - jCurrentTimeMillis > 1500) {
                    debug("[sendProfileRequest]Waiting for response");
                    jCurrentTimeMillis = System.currentTimeMillis();
                    if (w.Vl) {
                        jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                    }
                }
            }
            debug("IAP Request link:" + Lx.toString());
            debug("IAP Response:" + Lx.BM);
            if (Lx.BM != null) {
                LD = aC(Lx.BM);
            }
        }
        long jCurrentTimeMillis3 = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis3 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        Ly = jCurrentTimeMillis3;
        if (LH.LQ.equals("")) {
            LH.LS = 0L;
        } else {
            LH.LS = aE(LH.LQ) - aE(LH.LR);
        }
    }

    public static int iC() {
        if (GLLib.Qp) {
            LD = null;
        }
        int i = Lp;
        if (Lp == 7) {
            setResult(0);
            return i;
        }
        if (Lp == 1) {
            debug("\n\n **************** IAP Waiting.... ****************\n\n");
        }
        if (Lp == 12) {
            GLLib.be("\n\n **************** Trying to Silent Unlock ****************\n\n");
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
            }
            if (jCurrentTimeMillis < LE + 5000 || LE == 0) {
                if (iB()) {
                    GLLib.be("\n\n **************** Silent Unlock failed switch to IAP_REQUEST_SENT ****************\n\n");
                    setResult(2);
                } else {
                    GLLib.be("\n\n **************** IAP_ITEM_PURCHASED ****************\n\n");
                    setResult(7);
                }
            }
        }
        return i;
    }
}
