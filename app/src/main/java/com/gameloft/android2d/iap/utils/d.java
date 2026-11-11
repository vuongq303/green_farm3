package com.gameloft.android2d.iap.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.webkit.WebView;
import com.facebook.appevents.AppEventsConstants;
import java.io.File;
import java.util.Locale;
import java.util.MissingResourceException;

/* JADX INFO: compiled from: Device.java */
/* JADX INFO: loaded from: classes.dex */
public class d {
    private static boolean bnh;
    private static b bnj;
    private static String bno;
    private static String bnp;
    public static final String[][] bnf = {new String[]{"eng", "en"}, new String[]{"fra", "fr"}, new String[]{"deu", "de"}, new String[]{"esl", "es"}, new String[]{"spa", "es"}, new String[]{"ita", "it"}, new String[]{"jpn", "jp"}, new String[]{"por", "br"}, new String[]{"por", "pt"}};
    private static String bhS = null;
    private static String bng = null;
    private static String userAgent = null;
    private static String Ap = null;
    private static String Aq = null;
    private static String Ar = null;
    private static String As = null;
    private static String bhE = null;
    private static String At = null;
    private static String Au = null;
    private static WebView bnk = null;
    static ConnectivityManager bnn = null;
    private final String bni = "4";
    private final String bnl = "V007";
    private final String bnm = "UNLOCK";
    public final String VERSION = AppEventsConstants.EVENT_PARAM_VALUE_YES;
    private String bnq = "";

    public d() {
        SA();
    }

    public d(String str, String str2) {
        SA();
        if (str != null && !str.equals("") && str2 != null && !str2.equals("")) {
            bnj.bI(true);
            bnj.fi(str);
            bnj.mE(Integer.parseInt(str2));
        }
    }

    public static String getDeviceId() {
        return fA("IMEI");
    }

    public static String fA(String str) {
        return str.equalsIgnoreCase("hdidfv") ? System.getProperty("HDIDFV") : System.getProperty("IMEI");
    }

    public static void SA() {
        String str;
        if (bnn == null) {
            bnn = (ConnectivityManager) v.getContext().getSystemService("connectivity");
        }
        TelephonyManager telephonyManager = (TelephonyManager) v.getContext().getSystemService("phone");
        switch (telephonyManager.getSimState()) {
            case 1:
                str = "SIM_ABSENT";
                break;
            case 2:
                str = "SIM_PIN_REQUIRED";
                break;
            case 3:
                str = "SIM_PUK_REQUIRED";
                break;
            default:
                str = "SIM_ERROR_UNKNOWN";
                break;
        }
        if (bhS == null && com.gameloft.android2d.iap.b.QL() != 0) {
            bhS = fA("HDIDFV");
        }
        if (Ap == null) {
            Ap = telephonyManager.getNetworkOperator();
        }
        if (Ap.trim().length() == 0) {
            Ap = str;
        }
        if (Aq == null) {
            Aq = telephonyManager.getNetworkOperatorName();
        }
        if (Aq.trim().length() == 0) {
            Aq = str;
        }
        if (Ar == null) {
            Ar = telephonyManager.getSimOperator();
        }
        if (Ar.trim().length() == 0) {
            Ar = str;
        }
        if (As == null) {
            As = telephonyManager.getSimOperatorName();
        }
        if (As.trim().length() == 0) {
            As = str;
        }
        if (bng == null && com.gameloft.android2d.iap.b.QL() != 2) {
            bng = fA("IMEI");
        }
        if (bhE == null || bhE.equals("00")) {
            bhE = Pb();
        }
        if (At == null) {
            At = telephonyManager.getNetworkCountryIso();
        }
        if (Au == null) {
            Au = telephonyManager.getSimCountryIso();
        }
        bnh = telephonyManager.isNetworkRoaming();
        try {
            bno = getLanguage(Locale.getDefault().getISO3Language());
        } catch (MissingResourceException e) {
            bno = bnf[0][0];
        }
        if (!com.gameloft.android2d.iap.b.QK()) {
            bnp = Pp();
            try {
                ((Activity) v.getContext()).runOnUiThread(new e());
            } catch (Exception e2) {
                userAgent = "GL_EMU_001";
            }
        }
        bnj = new b();
    }

    public static String SB() {
        String str;
        if (bnn == null) {
            bnn = (ConnectivityManager) v.getContext().getSystemService("connectivity");
        }
        TelephonyManager telephonyManager = (TelephonyManager) v.getContext().getSystemService("phone");
        switch (telephonyManager.getSimState()) {
            case 1:
                str = "SIM_ABSENT";
                break;
            case 2:
                str = "SIM_PIN_REQUIRED";
                break;
            case 3:
                str = "SIM_PUK_REQUIRED";
                break;
            default:
                str = "SIM_ERROR_UNKNOWN";
                break;
        }
        if (Ar == null) {
            Ar = telephonyManager.getSimOperator();
        }
        if (Ar.trim().length() == 0) {
            Ar = str;
        }
        if (v.Pm()) {
            Ar = "SIM_ERROR_UNKNOWN";
        }
        String str2 = com.gameloft.android2d.iap.b.bkO;
        return (str2 == null || str2.trim().equals("")) ? Ar : str2;
    }

    public static void SC() {
        bng = null;
        Ap = null;
        Aq = null;
        Ar = null;
        As = null;
        bhE = null;
        At = null;
        Au = null;
    }

    private static String Pp() {
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState == "removed" || externalStorageState == "unmounted" || externalStorageState == "bad_removal") {
            return v.getContext().getFilesDir().getAbsolutePath();
        }
        File file = new File(Environment.getExternalStorageDirectory(), ".gameloft");
        file.mkdirs();
        return file.getAbsolutePath();
    }

    private static String getLanguage(String str) {
        for (int i = 0; i < bnf.length; i++) {
            if (str.compareToIgnoreCase(bnf[i][0]) == 0) {
                return bnf[i][1];
            }
        }
        return "en";
    }

    public String NK() {
        if (bng == null) {
            bng = getDeviceId();
        }
        return bng;
    }

    public String Os() {
        return bhS;
    }

    public static String getUserAgent() {
        return userAgent;
    }

    public String getNetworkOperator() {
        return v.Pm() ? "SIM_ERROR_UNKNOWN" : Ap;
    }

    public String getNetworkOperatorName() {
        return v.Pm() ? "SIM_ERROR_UNKNOWN" : Aq;
    }

    public String getSimOperator() {
        return v.Pm() ? "SIM_ERROR_UNKNOWN" : Ar;
    }

    public String getSimOperatorName() {
        return v.Pm() ? "SIM_ERROR_UNKNOWN" : As;
    }

    public static String Pb() {
        return "00";
    }

    public String getNetworkCountryIso() {
        return v.Pm() ? "" : At;
    }

    public String getSimCountryIso() {
        return v.Pm() ? "" : Au;
    }

    public String SD() {
        return Build.MODEL;
    }

    public String getDevice() {
        return Build.DEVICE;
    }

    public boolean SE() {
        return bnh;
    }

    public b SF() {
        return bnj;
    }

    public static boolean Pd() {
        NetworkInfo activeNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) v.getContext().getSystemService("connectivity");
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                return activeNetworkInfo.isConnected();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
