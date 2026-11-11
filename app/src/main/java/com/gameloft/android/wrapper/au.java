package com.gameloft.android.wrapper;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.cz;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.OrientationEventListener;
import android.view.ViewGroup;
import android.widget.Toast;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AnalyticsEvents;
import com.gameloft.android.hdidfv.HDIDFV;
import com.gameloft.glads.AndroidWebView;
import com.google.firebase.analytics.FirebaseAnalytics;
import dalvik.system.PathClassLoader;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.microedition.midlet.MIDlet;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
public class au {
    public static x biQ;
    public static String[] big;
    private static boolean bil;
    public static Toast bit;
    private static OrientationEventListener biu;
    public static boolean bhL = true;
    private static Context mContext = null;
    public static String bhM = "";
    public static String userAgent = null;
    public static String bhN = "-1";
    public static String bhO = "";
    public static Object bhP = null;
    public static String bhQ = "";
    public static String bhR = "";
    protected static String bhS = "";
    protected static String bhT = "";
    protected static String bhU = "";
    protected static String bhV = "";
    protected static String bhW = "";
    public static String url = null;
    public static String response = null;
    public static long bhX = 0;
    public static String bhY = "glot_log.txt";
    public static String bhZ = "";
    public static String bia = "";
    public static String bib = "";
    public static String bic = "";
    public static String bid = "";
    public static boolean bie = false;
    public static String bif = "";
    public static String bih = "19GmLfT99";
    private static String bii = "";
    private static String bij = "";
    private static String bik = "";
    public static final String[] bim = {"EN", "DE", "FR", "IT", "SP", "BR", "PT", "JP", "CN", "KR", "RU", "TR", "PL", "CZ", "NL", "TH", "VI", "AR", "ID", "ZT", "SC", "ZH", "ES_LATAM"};
    public static int bin = 0;
    private static int bio = -2;
    private static double bip = 0.0d;
    private static double biq = 0.0d;
    private static float bir = 0.0f;
    private static String bis = AppEventsConstants.EVENT_PARAM_VALUE_NO;
    private static int biv = -1;
    public static boolean biw = false;
    public static boolean bix = false;
    public static boolean biy = false;
    public static boolean biz = false;
    public static boolean biA = false;
    public static boolean biB = false;
    public static boolean biC = false;
    public static boolean biD = false;
    public static boolean biE = false;
    public static int biF = 0;
    public static int biG = 0;
    public static int biH = 0;
    public static int biI = 0;
    public static int biJ = 0;
    public static int biK = 0;
    public static int biL = 0;
    public static int biM = 0;
    public static int biN = 0;
    public static boolean biO = false;
    public static boolean biP = false;
    private static String biR = null;
    private static String biS = null;
    public static int biT = 100;
    public static int biU = -1;
    public static Boolean biV = null;

    public static Context getContext() {
        return mContext;
    }

    public static void an(Context context) {
        mContext = context;
        Pa();
    }

    public static void ao(Context context) {
        if (biv < 0) {
            biv = 270;
        }
        if (context != null) {
            biu = new av(context);
        }
    }

    public static void bG(boolean z) {
        if (z) {
            biu.enable();
        } else {
            biu.disable();
        }
    }

    public static Activity getActivity() {
        return (Activity) mContext;
    }

    public static final InputStream getResourceAsStream(String str) {
        if (str.startsWith("/")) {
            str = str.substring(1);
        }
        String lowerCase = str.substring(str.lastIndexOf(47) + 1).toLowerCase();
        int iIndexOf = lowerCase.indexOf(46);
        if (iIndexOf != -1) {
            lowerCase = lowerCase.substring(0, iIndexOf);
        }
        String str2 = str.substring(0, str.lastIndexOf(47) + 1) + "res_" + lowerCase;
        if (bhL) {
            String str3 = "Trying to get protected resource using forward lock. Resource: " + str2;
        }
        int identifier = mContext.getResources().getIdentifier(str2, "drawable", mContext.getPackageName());
        if (identifier != 0) {
            if (bhL) {
                String str4 = "Forward lock protection ok. Protected resource exists: " + str2 + " with ID=" + identifier;
            }
            InputStream inputStreamOpenRawResource = mContext.getResources().openRawResource(identifier);
            if (inputStreamOpenRawResource != null) {
                return inputStreamOpenRawResource;
            }
        }
        if (bhL) {
            String str5 = "Resource: " + str + " is not protected using forward lock";
        }
        AssetManager assets = mContext.getAssets();
        try {
            if (bhL) {
                String str6 = "Reading asset: " + str;
            }
            if (Arrays.asList(assets.list(bhM)).contains(str)) {
                return assets.open(bhM + "/" + str);
            }
            return assets.open("" + str);
        } catch (IOException e) {
            if (bhL) {
                String str7 = "Error reading asset: " + str + " :: " + e;
            }
            return null;
        }
    }

    public static String U(String str, String str2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream inputStreamOpenRawResource = mContext.getResources().openRawResource(mContext.getResources().getIdentifier(str, str2, mContext.getPackageName()));
            for (int i = inputStreamOpenRawResource.read(); i != -1; i = inputStreamOpenRawResource.read()) {
                byteArrayOutputStream.write(i);
            }
            inputStreamOpenRawResource.close();
        } catch (IOException e) {
        } catch (Exception e2) {
        }
        return byteArrayOutputStream.toString();
    }

    public static String ON() {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                String macAddress = ((WifiManager) mContext.getSystemService("wifi")).getConnectionInfo().getMacAddress();
                if (macAddress != null && macAddress.length() > 0) {
                    return macAddress;
                }
            } else {
                try {
                    for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                        if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                            byte[] hardwareAddress = networkInterface.getHardwareAddress();
                            if (hardwareAddress == null) {
                                return "";
                            }
                            StringBuilder sb = new StringBuilder();
                            for (byte b : hardwareAddress) {
                                sb.append(Integer.toHexString(b & 255) + ":");
                            }
                            if (sb.length() > 0) {
                                sb.deleteCharAt(sb.length() - 1);
                            }
                            String str = "Error in getMac on FW >6.0: " + ((Object) sb);
                            return sb.toString();
                        }
                    }
                } catch (Exception e) {
                    String str2 = "Error in getMac EX:" + e.toString();
                }
            }
        } catch (Exception e2) {
            String str3 = "Error in getMac" + e2.toString();
        }
        return "00:00:00:00:00:00";
    }

    public static synchronized String getDeviceId() {
        return getContext() == null ? null : ap(getContext());
    }

    public static void ei(String str) {
        a(new String[]{str}, getActivity());
    }

    public static void k(String[] strArr) {
        a(strArr, getActivity());
    }

    public static void a(String[] strArr, Activity activity) {
        if (Build.VERSION.SDK_INT > 22) {
            biP = n(strArr);
            String[] strArrL = l(strArr);
            if (!biO && strArrL != null && m(strArrL)) {
                biO = true;
                Intent intent = new Intent(activity, (Class<?>) GamePermission.class);
                intent.putExtra("permission", strArrL);
                activity.startActivityForResult(intent, 60);
                while (biO) {
                    try {
                        Thread.sleep(500L);
                    } catch (Exception e) {
                    }
                }
                return;
            }
            return;
        }
        biw = true;
        bix = true;
        biy = true;
        biz = true;
        biA = true;
        biB = true;
        biC = true;
        biD = true;
        biE = true;
    }

    public static void OO() {
        if (Build.VERSION.SDK_INT > 22) {
            biw = true;
            bix = true;
            biy = true;
            biz = true;
            biA = true;
            biB = true;
            biC = true;
            biD = true;
            biE = true;
            if (getActivity().checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                biw = false;
            }
            if (getActivity().checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
                bix = false;
            }
            if (getActivity().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                biy = false;
            }
            if (getActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) {
                biz = false;
            }
            if (getActivity().checkSelfPermission("android.permission.SEND_SMS") != 0) {
                biA = false;
            }
            if (getActivity().checkSelfPermission("android.permission.READ_SMS") != 0) {
                biB = false;
            }
            if (getActivity().checkSelfPermission("android.permission.RECEIVE_SMS") != 0) {
                biC = false;
            }
            if (getActivity().checkSelfPermission("android.permission.GET_ACCOUNTS") != 0) {
                biD = false;
            }
            if (getActivity().checkSelfPermission("android.permission.CAMERA") != 0) {
                biE = false;
            }
            String str = "hasPermisionPhone:" + biw;
            String str2 = "hasPermisionLocation:" + bix;
            String str3 = "hasPermisionStorage:" + biy;
            String str4 = "hasPermisionReadStorage:" + biz;
            String str5 = "hasPermisionSMS:" + biA;
            String str6 = "hasPermisionReadSMS:" + biB;
            String str7 = "hasPermisionReceiveSMS:" + biC;
            String str8 = "hasPermisionAccount:" + biD;
            String str9 = "hasPermisionCamera:" + biE;
            return;
        }
        biw = true;
        bix = true;
        biy = true;
        biz = true;
        biA = true;
        biB = true;
        biC = true;
        biD = true;
        biE = true;
    }

    public static boolean ej(String str) {
        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getPackageName(), 4096);
            if (packageInfo.requestedPermissions == null) {
                return false;
            }
            String[] strArr = packageInfo.requestedPermissions;
            for (String str2 : strArr) {
                if (str2.equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String[] l(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        try {
            for (String str : strArr) {
                if (getActivity().checkSelfPermission(str) != 0) {
                    if (str.equals("android.permission.READ_PHONE_STATE") && ej("android.permission.READ_PHONE_STATE")) {
                        arrayList.add("android.permission.READ_PHONE_STATE");
                        biw = false;
                    }
                    if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE") && ej("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
                        biy = false;
                    }
                    if (str.equals("android.permission.READ_EXTERNAL_STORAGE") && ej("android.permission.READ_EXTERNAL_STORAGE")) {
                        arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
                        biz = false;
                    }
                    if (str.equals("android.permission.ACCESS_COARSE_LOCATION") && ej("android.permission.ACCESS_COARSE_LOCATION")) {
                        arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
                        bix = false;
                    }
                    if (str.equals("android.permission.SEND_SMS") && ej("android.permission.SEND_SMS")) {
                        arrayList.add("android.permission.SEND_SMS");
                        biA = false;
                    }
                    if (str.equals("android.permission.READ_SMS") && ej("android.permission.READ_SMS")) {
                        arrayList.add("android.permission.READ_SMS");
                        biB = false;
                    }
                    if (str.equals("android.permission.RECEIVE_SMS") && ej("android.permission.RECEIVE_SMS")) {
                        arrayList.add("android.permission.RECEIVE_SMS");
                        biC = false;
                    }
                    if (str.equals("android.permission.GET_ACCOUNTS") && ej("android.permission.GET_ACCOUNTS")) {
                        arrayList.add("android.permission.GET_ACCOUNTS");
                        biD = false;
                    }
                    if (str.equals("android.permission.CAMERA") && ej("android.permission.CAMERA")) {
                        arrayList.add("android.permission.CAMERA");
                        biE = false;
                    }
                }
            }
            if (arrayList.size() <= 0) {
                return null;
            }
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean m(String[] strArr) {
        if (strArr != null) {
            try {
                for (String str : strArr) {
                    if (getActivity().checkSelfPermission(str) != 0 && ((str.equals("android.permission.READ_PHONE_STATE") && biF == 0) || ((str.equals("android.permission.WRITE_EXTERNAL_STORAGE") && biG == 0) || ((str.equals("android.permission.READ_EXTERNAL_STORAGE") && biH == 0) || ((str.equals("android.permission.ACCESS_COARSE_LOCATION") && biL == 0) || ((str.equals("android.permission.SEND_SMS") && biI == 0) || ((str.equals("android.permission.READ_SMS") && biJ == 0) || ((str.equals("android.permission.RECEIVE_SMS") && biK == 0) || ((str.equals("android.permission.GET_ACCOUNTS") && biM == 0) || (str.equals("android.permission.CAMERA") && biN == 0)))))))))) {
                        boolean zB = b("game_permission_denied", false, str);
                        if (!getActivity().shouldShowRequestPermissionRationale(str) && zB) {
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return true;
    }

    public static boolean n(String[] strArr) {
        int i = 0;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (strArr[i2].equals("android.permission.READ_PHONE_STATE")) {
                i++;
            }
            if (strArr[i2].equals("android.permission.SEND_SMS")) {
                i++;
            }
        }
        return i == 2;
    }

    public static synchronized String ap(Context context) {
        String strOs;
        if (d.beJ == 2 || !biw || (strOs = ((TelephonyManager) context.getSystemService("phone")).getDeviceId()) == null) {
            strOs = Os();
            String str = "Try using HDIDFV: " + strOs;
            if ((strOs == null || strOs.length() <= 0) && (strOs = Settings.Secure.getString(context.getContentResolver(), "android_id")) != null && strOs.length() > 0) {
            }
        }
        return strOs;
    }

    public static String getAndroidId() {
        return "";
    }

    public static String getSerial() {
        return "";
    }

    public static String OP() {
        return "";
    }

    public static synchronized String OQ() {
        return aq(getContext());
    }

    public static synchronized String aq(Context context) {
        String string;
        string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        String str = "Try using AndroidID: " + string;
        if (string == null || string.length() > 0) {
        }
        return string;
    }

    public static synchronized String getSerialNumber() {
        return "";
    }

    public static int OR() {
        return d.beJ;
    }

    public static String getPackageName() {
        return mContext != null ? mContext.getPackageName() : "";
    }

    public static String OS() {
        if (bhS != null && bhS.length() > 0) {
            return bhS;
        }
        if (e.getContext() == null) {
            e.init(getContext());
        }
        bhS = e.ec("HDIDFV");
        String str = bhS;
        try {
            if (!TextUtils.isEmpty(bhS)) {
                bhS = a.decrypt(bhS).replaceAll(" ", "");
            }
        } catch (Exception e) {
            bhS = "";
        }
        if (!TextUtils.isEmpty(bhS)) {
            String str2 = "Using HDIDFV from DataSharing " + bhS;
            bhT = e.ec("HDIDFVVersion");
            String str3 = "Using HDIDFVVersion from DataSharing " + bhT;
            return bhS;
        }
        bhS = HDIDFV.Os();
        if (d.beJ == 1) {
        }
        bhT = HDIDFV.Ot();
        if (!TextUtils.isEmpty(bhS)) {
            String str4 = "Saving HDIDFV into DataSharing: " + bhS;
            e.S("HDIDFV", a.crypt(bhS));
            e.S("HDIDFVVersion", bhT);
        }
        if (!TextUtils.isEmpty(bhS)) {
            return bhS;
        }
        return "";
    }

    public static synchronized String Os() {
        return ar(getContext());
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00fa  */
    public static synchronized String ar(Context context) {
        String str;
        if (d.beJ != 0) {
            if (bhS != null && bhS.length() > 0) {
                str = bhS;
            } else {
                if (e.getContext() == null) {
                    e.init(context);
                }
                bhS = e.ec("HDIDFV");
                String str2 = bhS;
                try {
                    if (!TextUtils.isEmpty(bhS)) {
                        bhS = a.decrypt(bhS).replaceAll(" ", "");
                    }
                } catch (Exception e) {
                    bhS = "";
                }
                if (!TextUtils.isEmpty(bhS)) {
                    String str3 = "Using HDIDFV from DataSharing " + bhS;
                    bhT = e.ec("HDIDFVVersion");
                    String str4 = "Using HDIDFVVersion from DataSharing " + bhT;
                    str = bhS;
                } else {
                    bhS = HDIDFV.Os();
                    if (d.beJ == 1) {
                    }
                    bhT = HDIDFV.Ot();
                    if (!TextUtils.isEmpty(bhS)) {
                        String str5 = "Saving HDIDFV into DataSharing: " + bhS;
                        e.S("HDIDFV", a.crypt(bhS));
                        e.S("HDIDFVVersion", bhT);
                    }
                    if (!TextUtils.isEmpty(bhS)) {
                        str = bhS;
                    } else {
                        str = "";
                    }
                }
            }
        } else {
            str = "";
        }
        return str;
    }

    public static synchronized int OT() {
        return f(getContext(), true);
    }

    public static synchronized int f(Context context, boolean z) {
        int i;
        if (e.getContext() == null) {
            e.init(context);
        }
        if (z) {
            bhV = e.ec("GenderGate");
        } else {
            bhV = A("GenderGate", "", Pz());
        }
        String str = bhV;
        try {
            if (!TextUtils.isEmpty(bhV)) {
                bhV = a.decrypt(bhV).replaceAll(" ", "");
            }
        } catch (Exception e) {
            bhV = "";
        }
        if (!TextUtils.isEmpty(bhV)) {
            String str2 = "Using GenderGate from DataSharing " + bhV;
            i = Integer.parseInt(bhV);
        } else {
            i = -1;
        }
        return i;
    }

    public static synchronized void mA(int i) {
        b(getContext(), i, true);
    }

    public static synchronized void b(Context context, int i, boolean z) {
        if (e.getContext() == null) {
            e.init(context);
        }
        String string = Integer.toString(i);
        if (!TextUtils.isEmpty(string)) {
            if (z) {
                String str = "Saving GenderGate into DataSharing: " + string;
                e.S("GenderGate", a.crypt(string));
            } else {
                String str2 = "Saving GenderGate: " + string;
                a("GenderGate", a.crypt(string), Pz());
            }
        }
    }

    public static void ek(String str) {
        bhQ = str.replace("http:", "https:");
        String str2 = "m_marketingSite: " + bhQ;
        OU();
    }

    private static void OU() {
        SharedPreferences.Editor editorEdit = getActivity().getPreferences(0).edit();
        editorEdit.putString("IGP_MARKETING_SITE", bhQ);
        String str = "save marketing site = " + bhQ;
        editorEdit.commit();
    }

    public static String OV() {
        return bhQ;
    }

    public static String V(String str, String str2) {
        if (str != null && str != "" && str2.indexOf("/redir") != -1) {
            return str + str2.substring(str2.indexOf("/redir"));
        }
        return str2;
    }

    public static void el(String str) {
        if (Integer.parseInt(str) >= 13) {
            bhN = str;
        } else {
            bhN = AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
    }

    public static String OW() {
        return bhN;
    }

    public static void em(String str) {
        bhO = str;
    }

    public static String OX() {
        return bhO;
    }

    public static String OY() {
        String strD = MIDlet.d(getContext(), "GAME-SPACE");
        String strD2 = MIDlet.d(getContext(), "GAME-PRODUCT-ID");
        String strD3 = MIDlet.d(getContext(), "GAME-GGI");
        String strD4 = MIDlet.d(getContext(), "MIDlet-Version");
        String strD5 = MIDlet.d(getContext(), "GAME-PLATFORM");
        String strD6 = MIDlet.d(getContext(), "GAME-SHOP");
        if (strD == null || strD.trim().length() == 0) {
            return strD2 + ":" + strD3 + ":" + strD4 + ":" + strD5 + ":" + strD6;
        }
        return strD + ":" + strD2 + ":" + strD3 + ":" + strD4 + ":" + strD5 + ":" + strD6;
    }

    public static ViewGroup OZ() {
        return (ViewGroup) ((Activity) getContext()).findViewById(R.id.content);
    }

    public static String en(String str) {
        InputStream inputStream;
        InputStream inputStream2;
        int i = 0;
        url = str;
        bhX = System.currentTimeMillis();
        BufferedReader bufferedReader = null;
        try {
            URL url2 = new URL(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (str.contains("https")) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url2.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setRequestProperty("Connection", "close");
                httpsURLConnection.setRequestProperty("User-Agent", userAgent);
                String strU = U("serialkey", "raw");
                if (strU != null) {
                    httpsURLConnection.setRequestProperty("x-gl-d", strU);
                }
                httpsURLConnection.setRequestProperty("x-android-os-build-model", Build.MODEL);
                httpsURLConnection.setRequestProperty("x-up-gl-subno", Pb());
                httpsURLConnection.setRequestProperty("x-up-gl-imei", Os());
                if (httpsURLConnection.getResponseCode() != 200) {
                    if (0 == 0) {
                        return "";
                    }
                    try {
                        bufferedReader.close();
                        return "";
                    } catch (Exception e) {
                        return "";
                    }
                }
                synchronized (httpsURLConnection) {
                    inputStream2 = httpsURLConnection.getInputStream();
                }
                byte[] bArr = new byte[16];
                while (i != -1) {
                    i = inputStream2.read(bArr, 0, 16);
                    if (i != -1) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                }
                httpsURLConnection.disconnect();
            } else {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Connection", "close");
                httpURLConnection.setRequestProperty("User-Agent", userAgent);
                String strU2 = U("serialkey", "raw");
                if (strU2 != null) {
                    httpURLConnection.setRequestProperty("x-gl-d", strU2);
                }
                httpURLConnection.setRequestProperty("x-android-os-build-model", Build.MODEL);
                httpURLConnection.setRequestProperty("x-up-gl-imei", Os());
                if (httpURLConnection.getResponseCode() != 200) {
                    if (0 == 0) {
                        return "";
                    }
                    try {
                        bufferedReader.close();
                        return "";
                    } catch (Exception e2) {
                        return "";
                    }
                }
                synchronized (httpURLConnection) {
                    inputStream = httpURLConnection.getInputStream();
                }
                byte[] bArr2 = new byte[16];
                while (i != -1) {
                    i = inputStream.read(bArr2, 0, 16);
                    if (i != -1) {
                        byteArrayOutputStream.write(bArr2, 0, i);
                    }
                }
                httpURLConnection.disconnect();
            }
            String string = byteArrayOutputStream.toString();
            if (0 == 0) {
                return string;
            }
            try {
                bufferedReader.close();
                return string;
            } catch (Exception e3) {
                return string;
            }
        } catch (Exception e4) {
            if (0 != 0) {
                try {
                    bufferedReader.close();
                } catch (Exception e5) {
                }
            }
            return "";
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    bufferedReader.close();
                } catch (Exception e6) {
                }
            }
            throw th;
        }
    }

    private static void Pa() {
        try {
            getActivity().runOnUiThread(new bb());
        } catch (ClassCastException e) {
        } catch (Exception e2) {
        }
    }

    public static String getUserAgent() {
        if (userAgent == null || userAgent.equalsIgnoreCase("")) {
            Pa();
        }
        return userAgent;
    }

    public static String Pb() {
        return "";
    }

    public static void eo(String str) {
        a(bhY, true, str);
    }

    public static void a(String str, boolean z, String str2) {
        if (!str.equals("") && str != null) {
            bhY = str;
            try {
                File file = new File(Environment.getExternalStorageDirectory(), "GlotDebug");
                if (file.exists()) {
                    FileWriter fileWriter = new FileWriter(new File(file, bhY), z);
                    fileWriter.append((CharSequence) (str2 + "\n"));
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static String Pc() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "GlotDebug");
            if (!file.exists()) {
                return null;
            }
            File file2 = new File(file, "QADEBUG.txt");
            if (!file2.exists()) {
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                    sb.append('\n');
                } else {
                    return sb.toString();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean Pd() {
        NetworkInfo activeNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
            return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean Pe() {
        NetworkInfo activeNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return false;
            }
            return activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean Pf() {
        NetworkInfo activeNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return false;
            }
            return activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x003a  */
    public static int Pg() {
        int i;
        int i2;
        if (mContext == null || !Pd()) {
            return 0;
        }
        switch (((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo().getType()) {
            case 1:
                i = 1;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            default:
                i = 9;
                break;
            case 6:
                i = 5;
                break;
            case 7:
                i = 2;
                break;
            case 8:
                i = 3;
                break;
            case 9:
                i = 4;
                break;
        }
        if (i == 9) {
            switch (((TelephonyManager) mContext.getSystemService("phone")).getNetworkType()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case com.facebook.R.styleable.CardView_contentPaddingTop /* 11 */:
                    i2 = 6;
                    break;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case com.facebook.R.styleable.CardView_contentPaddingRight /* 10 */:
                case com.facebook.R.styleable.CardView_contentPaddingBottom /* 12 */:
                case 14:
                case 15:
                    i2 = 7;
                    break;
                case 13:
                    i2 = 8;
                    break;
                default:
                    i2 = i;
                    break;
            }
        } else {
            i2 = i;
        }
        if (i2 == 3) {
            return 9;
        }
        return i2;
    }

    public static String Ph() {
        return "" + SystemClock.elapsedRealtime();
    }

    public static int Pi() {
        return ((AudioManager) getContext().getSystemService("audio")).getStreamVolume(3);
    }

    public static boolean W(String str, String str2) {
        String strX = X(str, str2);
        if (strX == null || strX.length() <= 0) {
            return false;
        }
        return Boolean.valueOf(strX).booleanValue();
    }

    public static String X(String str, String str2) {
        String line;
        try {
            FileReader fileReader = new FileReader(str);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            do {
                line = bufferedReader.readLine();
                if (line != null) {
                }
                bufferedReader.close();
                fileReader.close();
                return line;
            } while (!line.startsWith(str2));
            line = line.substring(line.indexOf("=") + 1).trim();
            bufferedReader.close();
            fileReader.close();
            return line;
        } catch (Exception e) {
            String str3 = "Testing configuration file not found.It's path: " + str;
            return null;
        }
    }

    public static void b(String str, Object obj) {
        if (mContext != null) {
            a(str, obj, mContext.getPackageName());
        }
    }

    public static void a(String str, Object obj, String str2) {
        if (getContext() != null) {
            String str3 = "setPreferences(" + str + ", " + obj + ", " + str2 + ")";
            SharedPreferences.Editor editorEdit = getContext().getSharedPreferences(str2, 0).edit();
            if (obj instanceof String) {
                editorEdit.putString(str, (String) obj);
            } else if (obj instanceof Integer) {
                editorEdit.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Boolean) {
                editorEdit.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Long) {
                editorEdit.putLong(str, ((Long) obj).longValue());
            }
            editorEdit.commit();
        }
    }

    public static String A(String str, String str2, String str3) {
        if (getContext() != null) {
            String string = getContext().getSharedPreferences(str3, 0).getString(str, str2);
            String str4 = "getPreferenceString(" + str + ", " + str2 + " " + string + ", " + str3 + ")";
            return string;
        }
        return str2;
    }

    public static int C(String str, int i) {
        return mContext == null ? i : c(str, i, mContext.getPackageName());
    }

    public static int c(String str, int i, String str2) {
        if (getContext() != null) {
            int i2 = getContext().getSharedPreferences(str2, 0).getInt(str, i);
            String str3 = "getPreferenceInt(" + str + ", " + i + " " + i2 + ", " + str2 + ")";
            return i2;
        }
        return i;
    }

    public static boolean b(String str, boolean z, String str2) {
        try {
            if (getContext() != null) {
                boolean z2 = getContext().getSharedPreferences(str2, 0).getBoolean(str, z);
                String str3 = "getPreferenceBoolean(" + str + ", " + z + " " + z2 + ", " + str2 + ")";
                return z2;
            }
            return z;
        } catch (Exception e) {
            String str4 = "getPreferenceBoolean(" + str + ", " + z + " false, " + str2 + ")";
            return false;
        }
    }

    public static void Y(String str, String str2) {
        String str3 = "removePreference(" + str + ", " + str2 + ")";
        SharedPreferences.Editor editorEdit = getContext().getSharedPreferences(str2, 0).edit();
        editorEdit.remove(str);
        editorEdit.commit();
    }

    public static String Pj() {
        try {
            InputStream inputStreamOpenRawResource = mContext.getResources().openRawResource(mContext.getResources().getIdentifier("serialkey", "raw", mContext.getPackageName()));
            int iAvailable = inputStreamOpenRawResource.available();
            byte[] bArr = new byte[iAvailable];
            inputStreamOpenRawResource.read(bArr, 0, iAvailable);
            inputStreamOpenRawResource.close();
            return new String(bArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static String Pk() {
        try {
            mContext.getResources();
            String language = Resources.getSystem().getConfiguration().locale.getLanguage();
            if (language != null && language.length() != 0) {
                return language;
            }
        } catch (Exception e) {
        }
        try {
            String language2 = Locale.getDefault().getLanguage();
            return (language2 == null || language2.length() == 0) ? "XX" : language2;
        } catch (Exception e2) {
        }
    }

    public static String Pl() {
        if (mContext != null) {
            try {
                String country = mContext.getResources().getConfiguration().locale.getCountry();
                if (country != null && country.length() != 0) {
                    return country;
                }
            } catch (Exception e) {
            }
        }
        String country2 = Locale.getDefault().getCountry();
        return (country2 == null || country2.length() == 0) ? "XX" : country2;
    }

    public static boolean Pm() {
        return Settings.System.getInt(getContext().getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public static String Pn() {
        String simOperator;
        try {
            return (mContext == null || Pm() || (simOperator = ((TelephonyManager) mContext.getSystemService("phone")).getSimOperator()) == null || simOperator.length() == 0) ? AppEventsConstants.EVENT_PARAM_VALUE_NO : simOperator;
        } catch (Exception e) {
        }
    }

    public static String Po() {
        try {
            String simCountryIso = ((TelephonyManager) mContext.getSystemService("phone")).getSimCountryIso();
            return (simCountryIso == null || simCountryIso.length() <= 0) ? "" : simCountryIso;
        } catch (Exception e) {
        }
    }

    public static String Pp() {
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState == "removed" || externalStorageState == "unmounted" || externalStorageState == "bad_removal") {
            return getContext().getFilesDir().getAbsolutePath();
        }
        File file = new File(Environment.getExternalStorageDirectory(), ".gameloft");
        file.mkdirs();
        return file.getAbsolutePath();
    }

    public static String Pq() {
        try {
            return getContext().getFilesDir().toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String ep(String str) {
        try {
            File file = new File(str);
            byte[] bArr = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bArr);
            fileInputStream.close();
            return new String(bArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean Z(String str, String str2) {
        try {
            String str3 = "WriteFile(), File = " + str;
            File file = new File(str);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
            }
        } catch (Exception e) {
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append((CharSequence) str2);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            fileOutputStream.close();
            return true;
        } catch (Exception e2) {
            return true;
        }
    }

    public static String eq(String str) {
        try {
            ApplicationInfo applicationInfo = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                return String.valueOf(applicationInfo.metaData.get(str));
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    public static String Pr() {
        try {
            ApplicationInfo applicationInfo = getContext().getPackageManager().getApplicationInfo(getPackageName(), 0);
            if (applicationInfo != null) {
                return String.valueOf(applicationInfo.targetSdkVersion);
            }
        } catch (Exception e) {
        }
        return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
    }

    public static long a(String str, Context context) {
        File file;
        try {
            if (str.contains(context.getPackageName())) {
                file = new File(str.substring(0, str.indexOf(context.getPackageName())));
            } else if (str == Pp()) {
                file = new File("/sdcard/");
            } else {
                file = new File(str);
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            StatFs statFs = new StatFs(file.getAbsolutePath());
            return (((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1048576;
        } catch (Exception e) {
            return 0L;
        }
    }

    public static Vector Ps() {
        Vector vector;
        try {
            int identifier = getContext().getResources().getIdentifier("color_profile", "raw", getContext().getPackageName());
            String lowerCase = Build.MANUFACTURER.toLowerCase();
            String lowerCase2 = Build.MODEL.toLowerCase();
            String strReplaceAll = lowerCase.replaceAll(" ", "-");
            String strReplaceAll2 = lowerCase2.replaceAll(" ", "-");
            String str = Build.VERSION.RELEASE;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(identifier)));
            boolean z = false;
            boolean z2 = false;
            Vector vector2 = null;
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    vector = vector2;
                    break;
                }
                String[] strArrSplit = line.split(" ");
                if (z2) {
                    if (line.equals("begin")) {
                        continue;
                    } else if (line.equals("end")) {
                        if (z) {
                            vector = vector2;
                            break;
                        }
                        z2 = false;
                    } else {
                        for (String str2 : strArrSplit) {
                            vector2.addElement(str2);
                        }
                    }
                } else if (strArrSplit.length == 3) {
                    if (strReplaceAll.equals(strArrSplit[0].toLowerCase()) && strReplaceAll2.equals(strArrSplit[1].toLowerCase())) {
                        vector2 = new Vector();
                        if (strArrSplit[2].equals(str)) {
                            z = true;
                            z2 = true;
                        } else {
                            z2 = true;
                        }
                    }
                } else if (strArrSplit.length > 15 && strReplaceAll.equals(strArrSplit[0].toLowerCase()) && strReplaceAll2.equals(strArrSplit[1].toLowerCase())) {
                    vector2 = new Vector();
                    for (int i = 0; i < 13; i++) {
                        vector2.addElement(strArrSplit[i + 3]);
                    }
                    if (strArrSplit[2].equals(str)) {
                        vector = vector2;
                        break;
                    }
                }
            }
            bufferedReader.close();
            return vector;
        } catch (Exception e) {
            return null;
        }
    }

    private static int[] Pt() {
        int[] iArr = {0, 0, 0, 0};
        try {
            Signature[] signatureArr = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 64).signatures;
            int length = signatureArr.length < 4 ? signatureArr.length : 4;
            for (int i = 0; i < length; i++) {
                iArr[i] = signatureArr[i].hashCode();
            }
            return iArr;
        } catch (Exception e) {
            return iArr;
        }
    }

    public static String l(byte[] bArr) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(bArr);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bArrDigest) {
                stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            return stringBuffer.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static boolean Pu() {
        int[] iArrPt = Pt();
        if (iArrPt == null) {
            return true;
        }
        int[] iArr = {16847, 2021};
        boolean z = true;
        for (int i = 0; i < iArrPt.length; i++) {
            if (i == 0) {
                iArr[1] = iArr[1] * 2;
            }
            if (iArrPt[i] == (iArr[1] * 100000) + (iArr[0] * 3)) {
                return true;
            }
            if (iArrPt[i] > 0) {
                z = false;
            }
        }
        return z;
    }

    public static long Pv() {
        return System.currentTimeMillis() + ((long) TimeZone.getDefault().getRawOffset());
    }

    public static String getDeviceName() {
        return Build.MODEL.toUpperCase();
    }

    public static String Pw() {
        return Build.VERSION.RELEASE.toUpperCase();
    }

    public static boolean isRooted() {
        return NativeUtils.CheckRoot();
    }

    public static String Px() {
        return ON();
    }

    public static String NK() {
        return getDeviceId();
    }

    public static long getClockMillis() {
        return NativeUtils.getClockMillis();
    }

    public static long gm() {
        return System.currentTimeMillis() / 1000;
    }

    public static byte[] aa(String str, String str2) {
        try {
            SecretKey secretKeyGenerateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(c.decodeLines(c.encodeString(str2))));
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, secretKeyGenerateSecret);
            byte[] bytes = str.getBytes();
            int length = bytes.length % 8 == 0 ? bytes.length / 8 : (bytes.length / 8) + 1;
            byte[] bArr = new byte[length * 8];
            for (int i = 0; i < length * 8; i++) {
                bArr[i] = 0;
            }
            System.arraycopy(bytes, 0, bArr, 0, bytes.length);
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static void j(String str, String str2, String str3, String str4) {
        bhZ = str;
        bia = str2;
        bib = str3;
        bic = str4;
        if (biQ != null) {
            biQ.OB();
        }
    }

    public static String getNetworkCountryIso() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService("phone");
            if (telephonyManager != null) {
                return telephonyManager.getNetworkCountryIso();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getNetworkOperator() {
        String networkOperator;
        String str = "SIM_ERROR_UNKNOWN";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService("phone");
            if (telephonyManager != null) {
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
                networkOperator = telephonyManager.getNetworkOperator();
            } else {
                networkOperator = null;
            }
        } catch (Exception e) {
            str = "SIM_ERROR_UNKNOWN";
            networkOperator = null;
        }
        return (networkOperator == null || networkOperator.trim().length() == 0) ? str : networkOperator;
    }

    public static String Py() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService("phone");
            if (telephonyManager != null) {
                switch (telephonyManager.getSimState()) {
                    case 1:
                        return "SIM_ABSENT";
                    case 2:
                        return "SIM_PIN_REQUIRED";
                    case 3:
                        return "SIM_PUK_REQUIRED";
                }
            }
            return "SIM_ERROR_UNKNOWN";
        } catch (Exception e) {
            e.getMessage();
            return "SIM_ERROR_UNKNOWN";
        }
    }

    public static String getSimOperator() {
        String simOperator;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService("phone");
            simOperator = telephonyManager != null ? telephonyManager.getSimOperator() : null;
        } catch (Exception e) {
            simOperator = null;
        }
        if (simOperator == null || simOperator.trim().length() == 0) {
            return Py();
        }
        return simOperator;
    }

    public static String Pz() {
        try {
            String str = "Package name:" + mContext.getPackageName();
            String packageName = mContext.getPackageName();
            if (packageName.indexOf("com.gameloft.android.") != -1) {
                return packageName.substring("com.gameloft.android.".length());
            }
            return packageName;
        } catch (Exception e) {
            return "";
        }
    }

    public static String PA() {
        if (mContext == null) {
            return "";
        }
        if (biR == null) {
            String strPI = PI();
            if (!TextUtils.isEmpty(strPI)) {
                try {
                    String str = "InjectedIGP get from crc.bin:" + strPI;
                    biR = h.Oz().T(strPI, MIDlet.d(getActivity(), "GAME-IGP-CODE"));
                } catch (Exception e) {
                    biR = "";
                }
                String str2 = "InjectedIGP was null, setting to PlainDef:" + biR;
            }
        }
        if (TextUtils.isEmpty(biR)) {
            biR = "";
        }
        return biR;
    }

    public static String PB() {
        return biR == null ? "" : biR;
    }

    public static String PC() {
        if (biS == null) {
            try {
                String strA = A("injectedSerialKey", "", "injectedIGP" + Pz());
                if (TextUtils.isEmpty(strA) && PF()) {
                    strA = PL();
                    if (TextUtils.isEmpty(strA)) {
                        if ((getContext().getApplicationInfo().flags & 128) != 0) {
                            String[] list = new File("/system/app").list();
                            String str = "app folder size " + list.length;
                            for (int i = 0; i < list.length; i++) {
                                if (list[i].endsWith(".apk")) {
                                    String str2 = "/system/app/" + list[i];
                                    if (mContext.getPackageManager().getPackageArchiveInfo(str2, 0).packageName.equals(mContext.getPackageName())) {
                                        InputStream resourceAsStream = new PathClassLoader(str2, null, PathClassLoader.getSystemClassLoader()).getResourceAsStream("res/raw/serialkey.txt");
                                        if (resourceAsStream == null) {
                                            break;
                                        }
                                        strA = a(resourceAsStream, AndroidWebView.UTF_8);
                                        if (!TextUtils.isEmpty(strA)) {
                                            a("injectedSerialKey", strA, "injectedIGP" + Pz());
                                            break;
                                        }
                                        break;
                                    }
                                }
                            }
                            if (TextUtils.isEmpty(strA)) {
                                String[] list2 = new File("/system/priv-app").list();
                                String str3 = "priv-app folder size " + list2.length;
                                for (int i2 = 0; i2 < list2.length; i2++) {
                                    if (list2[i2].endsWith(".apk")) {
                                        String str4 = "/system/priv-app/" + list2[i2];
                                        if (mContext.getPackageManager().getPackageArchiveInfo(str4, 0).packageName.equals(mContext.getPackageName())) {
                                            InputStream resourceAsStream2 = new PathClassLoader(str4, null, PathClassLoader.getSystemClassLoader()).getResourceAsStream("res/raw/serialkey.txt");
                                            if (resourceAsStream2 == null) {
                                                break;
                                            }
                                            strA = a(resourceAsStream2, AndroidWebView.UTF_8);
                                            if (!TextUtils.isEmpty(strA)) {
                                                a("injectedSerialKey", strA, "injectedIGP" + Pz());
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        a("injectedSerialKey", strA, "injectedIGP" + Pz());
                    }
                    PH();
                }
                biS = strA;
                String str5 = "SerialKey Google:" + biS;
            } catch (Exception e) {
                biS = "";
                PH();
            }
        }
        if (TextUtils.isEmpty(biS)) {
            biS = "";
        }
        return biS;
    }

    public static String PD() {
        return biS == null ? "" : biS;
    }

    public static boolean PE() {
        return c("retryNumIGP", 0, new StringBuilder().append("injectedIGP").append(Pz()).toString()) <= 1;
    }

    public static boolean PF() {
        return c("retryNumSerialKey", 0, new StringBuilder().append("injectedIGP").append(Pz()).toString()) <= 1;
    }

    public static void PG() {
        int iC = c("retryNumIGP", 0, "injectedIGP" + Pz()) + 1;
        if (iC < 3) {
            a("retryNumIGP", Integer.valueOf(iC), "injectedIGP" + Pz());
        }
        String str = "Increase IGP retry count to " + iC;
    }

    public static void PH() {
        int iC = c("retryNumSerialKey", 0, "injectedIGP" + Pz()) + 1;
        if (iC < 3) {
            a("retryNumSerialKey", Integer.valueOf(iC), "injectedIGP" + Pz());
        }
        String str = "Increase SerialKey retry count to " + iC;
    }

    private static String PI() {
        String strA;
        int i = 0;
        try {
            String strA2 = A("injectedIGP", "", "injectedIGP" + Pz());
            if (TextUtils.isEmpty(strA2) && PE()) {
                String strPK = PK();
                String str = "Injected IGP from same APK :" + strPK;
                if (!TextUtils.isEmpty(strPK)) {
                    a("injectedIGP", strPK, "injectedIGP" + Pz());
                    strA = strPK;
                } else {
                    if ((getContext().getApplicationInfo().flags & 128) != 0) {
                        String packageName = mContext.getPackageName();
                        String[] list = new File("/system/app").list();
                        String str2 = "app folder size " + list.length;
                        for (int i2 = 0; i2 < list.length; i2++) {
                            if (list[i2].equals(packageName)) {
                                list = new File("/system/app/" + list[i2]).list();
                            }
                        }
                        for (int i3 = 0; i3 < list.length; i3++) {
                            if (list[i3].endsWith(".apk")) {
                                String str3 = "Search injectedIGP in /system/app/" + packageName + "/" + list[i3];
                                String str4 = "/system/app/" + packageName + "/" + list[i3];
                                if (mContext.getPackageManager().getPackageArchiveInfo(str4, 0).packageName.equals(mContext.getPackageName())) {
                                    strPK = a(new PathClassLoader(str4, null, PathClassLoader.getSystemClassLoader()).getResourceAsStream("res/raw/crc.bin"), AndroidWebView.UTF_8);
                                    if (!TextUtils.isEmpty(strPK)) {
                                        String str5 = "Injected IGP from original APK :" + strPK;
                                        a("injectedIGP", strPK, "injectedIGP" + Pz());
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(strPK)) {
                            strA = strPK;
                            break;
                        }
                        String[] list2 = new File("/system/priv-app").list();
                        String str6 = "priv-app folder size " + list2.length;
                        for (int i4 = 0; i4 < list2.length; i4++) {
                            if (list2[i4].equals(packageName)) {
                                list2 = new File("/system/priv-app/" + list2[i4]).list();
                            }
                        }
                        while (true) {
                            if (i < list2.length) {
                                if (list2[i].endsWith(".apk")) {
                                    String str7 = "Search injectedIGP in /system/priv-app/" + packageName + "/" + list2[i];
                                    String str8 = "/system/priv-app/" + packageName + "/" + list2[i];
                                    if (mContext.getPackageManager().getPackageArchiveInfo(str8, 0).packageName.equals(mContext.getPackageName())) {
                                        InputStream resourceAsStream = new PathClassLoader(str8, null, PathClassLoader.getSystemClassLoader()).getResourceAsStream("res/raw/crc.bin");
                                        if (resourceAsStream != null) {
                                            strA = a(resourceAsStream, AndroidWebView.UTF_8);
                                            if (!TextUtils.isEmpty(strA)) {
                                                a("injectedIGP", strA, "injectedIGP" + Pz());
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                }
                                i++;
                            }
                            strA = strPK;
                            break;
                        }
                    }
                    strA = strPK;
                }
                if (TextUtils.isEmpty(strA)) {
                    strA = "";
                }
                PG();
                return strA;
            }
            return strA2;
        } catch (Exception e) {
            PG();
            return "";
        }
    }

    private static String a(InputStream inputStream, String str) {
        return new String(k(inputStream), str);
    }

    private static byte[] k(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static void PJ() {
        String strA = A("versionInstalled", "", "injectedIGP" + Pz());
        String str = "actualVersion: " + strA;
        if (TextUtils.isEmpty(strA)) {
            String str2 = "Initial version, saving " + MIDlet.VERSION;
            a("versionInstalled", MIDlet.VERSION, "injectedIGP" + Pz());
        } else if (!strA.equals(MIDlet.VERSION)) {
            String str3 = "New version detected, saving " + MIDlet.VERSION + " and removing retryNums for IGP and serialKey";
            a("retryNumIGP", (Object) 0, "injectedIGP" + Pz());
            a("retryNumSerialKey", (Object) 0, "injectedIGP" + Pz());
            a("versionInstalled", MIDlet.VERSION, "injectedIGP" + Pz());
        }
    }

    public static String PK() {
        InputStream inputStreamOpenRawResource = getContext().getResources().openRawResource(getContext().getResources().getIdentifier("crc", "raw", getContext().getPackageName()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            for (int i = inputStreamOpenRawResource.read(); i != -1; i = inputStreamOpenRawResource.read()) {
                byteArrayOutputStream.write(i);
            }
            inputStreamOpenRawResource.close();
        } catch (IOException e) {
            String str = "err:" + e.toString();
        }
        return byteArrayOutputStream.toString();
    }

    public static String PL() {
        InputStream inputStreamOpenRawResource = getContext().getResources().openRawResource(getContext().getResources().getIdentifier("serialkey", "raw", getContext().getPackageName()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            for (int i = inputStreamOpenRawResource.read(); i != -1; i = inputStreamOpenRawResource.read()) {
                byteArrayOutputStream.write(i);
            }
            inputStreamOpenRawResource.close();
        } catch (IOException e) {
            String str = "err:" + e.toString();
        }
        return byteArrayOutputStream.toString();
    }

    public static String PM() {
        String country;
        String country2;
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService("phone");
        if (telephonyManager.getPhoneType() != 2) {
            try {
                String simCountryIso = telephonyManager.getSimCountryIso();
                if (simCountryIso != null && simCountryIso.length() > 0) {
                    return simCountryIso;
                }
            } catch (Exception e) {
            }
            try {
                if (mContext != null && (country2 = mContext.getResources().getConfiguration().locale.getCountry()) != null && country2.length() > 0) {
                    return country2;
                }
            } catch (Exception e2) {
            }
            try {
                String country3 = Locale.getDefault().getCountry();
                if (country3 != null && country3.length() > 0) {
                    return country3;
                }
            } catch (Exception e3) {
            }
        } else {
            try {
                String simCountryIso2 = telephonyManager.getSimCountryIso();
                if (simCountryIso2 != null && simCountryIso2.length() > 0) {
                    return simCountryIso2;
                }
            } catch (Exception e4) {
            }
            try {
                if (mContext != null && (country = mContext.getResources().getConfiguration().locale.getCountry()) != null && country.length() > 0) {
                    return country;
                }
            } catch (Exception e5) {
            }
            try {
                String country4 = Locale.getDefault().getCountry();
                if (country4 != null && country4.length() > 0) {
                    return country4;
                }
            } catch (Exception e6) {
            }
        }
        return "";
    }

    public static String Ov() {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), 0);
        } catch (Exception e) {
        }
        return applicationInfo.sourceDir;
    }

    public static long PN() {
        String strD = MIDlet.d(MIDlet.cnA, "REVISION-2408");
        if (strD == null) {
            return 0L;
        }
        try {
            byte[] bArr = {0, 2, 38, -68};
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length);
            for (int i = 0; i < bArr.length; i++) {
                byteBufferAllocate.put(i, bArr[i]);
            }
            return Long.parseLong(strD) ^ ((long) byteBufferAllocate.getInt());
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static void D(long j) {
        try {
            Thread.sleep(j);
        } catch (Exception e) {
        }
    }

    public static boolean er(String str) {
        return getContext().deleteFile(str);
    }

    public static boolean es(String str) {
        return getContext().getFileStreamPath(str).exists();
    }

    public static int PO() {
        return bio;
    }

    public static double PP() {
        return bip;
    }

    public static double PQ() {
        return biq;
    }

    public static float PR() {
        return bir;
    }

    public static boolean PS() {
        if (Build.VERSION.SDK_INT >= 11) {
            return ((Vibrator) getContext().getApplicationContext().getSystemService("vibrator")).hasVibrator();
        }
        return true;
    }

    public static boolean PT() {
        String str;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService("phone");
            if (telephonyManager == null) {
                str = "SIM_ERROR_UNKNOWN";
            } else {
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
                    case 4:
                    default:
                        str = "SIM_ERROR_UNKNOWN";
                        break;
                    case 5:
                        str = "SIM_READY";
                        break;
                }
            }
        } catch (Exception e) {
            str = "SIM_ERROR_UNKNOWN";
        }
        if (str == "SIM_READY") {
            return true;
        }
        return false;
    }

    public static void PU() {
        big = h.Oz().T(PW(), bih).split("(\\r?\\n|\\r)+");
        for (int i = 0; i < big.length; i++) {
            big[i] = big[i].replace("\n", "").replace("\r", "");
        }
    }

    public static ArrayList<String> PV() {
        if (big == null) {
            PU();
        }
        return new ArrayList<>(Arrays.asList(big));
    }

    public static String PW() {
        InputStream inputStreamOpenRawResource = getContext().getResources().openRawResource(getContext().getResources().getIdentifier(FirebaseAnalytics.Param.CONTENT, "raw", getContext().getPackageName()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            for (int i = inputStreamOpenRawResource.read(); i != -1; i = inputStreamOpenRawResource.read()) {
                byteArrayOutputStream.write(i);
            }
            inputStreamOpenRawResource.close();
        } catch (IOException e) {
            String str = "err:" + e.toString();
        }
        return byteArrayOutputStream.toString();
    }

    public static void PX() {
        try {
            getContext().startActivity(new Intent("android.settings.WIFI_SETTINGS"));
        } catch (Exception e) {
            String str = "error when open wifi setting screen:" + e.toString();
        }
    }

    public static void PY() {
        try {
            getContext().startActivity(new Intent("android.settings.SETTINGS"));
        } catch (Exception e) {
            String str = "error when open setting screen:" + e.toString();
        }
    }

    public static void PZ() {
        try {
            getContext().startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
        } catch (Exception e) {
            String str = "error when open wireless setting screen:" + e.toString();
        }
    }

    public static void setGameLanguage(String str) {
        if (str.equalsIgnoreCase("es")) {
            str = "sp";
        }
        if (str.equalsIgnoreCase("le")) {
            str = "es_latam";
        }
        bin = 0;
        for (int i = 0; i < bim.length; i++) {
            if (bim[i].equalsIgnoreCase(str)) {
                bin = i;
                a("save_game_language", bim[i], mContext.getPackageName());
                return;
            }
        }
        a("save_game_language", bim[0], mContext.getPackageName());
    }

    public static String Qa() {
        return bim[bin];
    }

    public static int Qb() {
        return bin;
    }

    public static int et(String str) {
        for (int i = 0; i < bim.length; i++) {
            if (str.equalsIgnoreCase(bim[i])) {
                return i;
            }
        }
        return -1;
    }

    public static String eu(String str) {
        return a(getActivity(), str);
    }

    public static String a(Object obj, String str) {
        Activity activity;
        String str2;
        if (obj instanceof Activity) {
            activity = (Activity) obj;
        } else if (getContext() != null) {
            activity = (Activity) getContext();
        } else {
            return null;
        }
        String str3 = "";
        String strD = MIDlet.d(activity, "GAME-SHOP");
        if (str != null && str.equalsIgnoreCase("ES_LATAM")) {
            str = "ES";
        }
        if (strD.equals("shop") || strD.equals("shop_asp") || strD.equals("shop_b2c") || strD.equals("hep_oem") || strD.equals("hep_carrier")) {
            str3 = "FVGL";
        } else if (strD.equals("googleplay")) {
            str3 = "ANMP";
        } else if (strD.equals("amazon")) {
            str3 = "AMAZ";
        } else if (strD.equals("w00t")) {
            str3 = "W00T";
        }
        String strD2 = MIDlet.d(activity, "GAME-IGP-CODE");
        String strD3 = MIDlet.d(activity, "MIDlet-Version");
        String upperCase = Pl().toUpperCase();
        String strPw = Pw();
        String deviceName = getDeviceName();
        String strOs = "";
        if (d.beJ == 1 || d.beJ == 2) {
            strOs = Os();
        }
        String strEy = ey("https://201205igp.gameloft.com/redir/?from=" + strD2 + "&op=" + str3 + "&ctg=PRIVACY&game_ver=" + strD3 + "&lg=" + str + "&country=" + upperCase + "&f=" + strPw + "&d=" + deviceName + "&os=android");
        if (d.beJ != 0) {
            str2 = strEy + "&udid=&hdidfv=" + strOs + "&androidid=&google_optout=" + Qe();
        } else {
            str2 = strEy + "&udid=";
        }
        String str4 = str2 + "&igp_rev=1005";
        if (OW().compareTo("-1") != 0) {
            str4 = str4 + "&user_age=" + OW();
            if (OX() != null && OX().trim().length() != 0) {
                str4 = str4 + "&gender=" + OX();
            }
        }
        return str4 + "&clientid=" + OY();
    }

    public static String ev(String str) {
        return b(getActivity(), str);
    }

    public static String b(Object obj, String str) {
        Activity activity;
        String str2;
        if (obj instanceof Activity) {
            activity = (Activity) obj;
        } else if (getContext() != null) {
            activity = (Activity) getContext();
        } else {
            return null;
        }
        String str3 = "";
        String strD = MIDlet.d(activity, "GAME-SHOP");
        if (str != null && str.equalsIgnoreCase("ES_LATAM")) {
            str = "ES";
        }
        if (strD.equals("shop") || strD.equals("shop_asp") || strD.equals("shop_b2c") || strD.equals("hep_oem") || strD.equals("hep_carrier")) {
            str3 = "FVGL";
        } else if (strD.equals("googleplay")) {
            str3 = "ANMP";
        } else if (strD.equals("amazon")) {
            str3 = "AMAZ";
        } else if (strD.equals("w00t")) {
            str3 = "W00T";
        }
        String strD2 = MIDlet.d(activity, "GAME-IGP-CODE");
        String strD3 = MIDlet.d(activity, "MIDlet-Version");
        String upperCase = Pl().toUpperCase();
        String strPw = Pw();
        String deviceName = getDeviceName();
        String strOs = "";
        if (d.beJ == 1 || d.beJ == 2) {
            strOs = Os();
        }
        String strEy = ey("https://201205igp.gameloft.com/redir/?from=" + strD2 + "&op=" + str3 + "&ctg=TERMS&game_ver=" + strD3 + "&lg=" + str + "&country=" + upperCase + "&f=" + strPw + "&d=" + deviceName + "&os=android");
        if (d.beJ != 0) {
            str2 = strEy + "&udid=&hdidfv=" + strOs + "&androidid=&google_optout=" + Qe();
        } else {
            str2 = strEy + "&udid=";
        }
        String str4 = str2 + "&igp_rev=1005";
        if (OW().compareTo("-1") != 0) {
            str4 = str4 + "&user_age=" + OW();
            if (OX() != null && OX().trim().length() != 0) {
                str4 = str4 + "&gender=" + OX();
            }
        }
        return str4 + "&clientid=" + OY();
    }

    public static String ew(String str) {
        return m(str, false);
    }

    public static String m(String str, boolean z) {
        String str2;
        if (str != null && str.equalsIgnoreCase("ES_LATAM")) {
            str = "ES";
        }
        String str3 = "";
        String strD = MIDlet.d(getActivity(), "GAME-SHOP");
        if (strD.equals("shop") || strD.equals("shop_asp") || strD.equals("shop_b2c") || strD.equals("hep_oem") || strD.equals("hep_carrier")) {
            str3 = z ? "BFL4" : "FVGL";
        } else if (strD.equals("googleplay")) {
            str3 = "ANMP";
        } else if (strD.equals("amazon")) {
            str3 = "AMAZ";
        } else if (strD.equals("w00t")) {
            str3 = "W00T";
        }
        String strD2 = MIDlet.d(getActivity(), "GAME-IGP-CODE");
        String strD3 = MIDlet.d(getActivity(), "MIDlet-Version");
        String upperCase = Pl().toUpperCase();
        String strPw = Pw();
        String deviceName = getDeviceName();
        String strOs = "";
        if (d.beJ == 1 || d.beJ == 2) {
            strOs = Os();
        }
        String strEy = ey("https://201205igp.gameloft.com/redir/?from=" + strD2 + "&op=" + str3 + "&ctg=EULA&game_ver=" + strD3 + "&lg=" + str + "&country=" + upperCase + "&f=" + strPw + "&d=" + deviceName + "&os=android");
        if (d.beJ != 0) {
            str2 = strEy + "&udid=&hdidfv=" + strOs + "&androidid=&google_optout=" + Qe();
        } else {
            str2 = strEy + "&udid=";
        }
        if (OW().compareTo("-1") != 0) {
            str2 = str2 + "&user_age=" + OW();
            if (OX() != null && OX().trim().length() != 0) {
                str2 = str2 + "&gender=" + OX();
            }
        }
        return (str2 + "&clientid=" + OY()) + "&igp_rev=1005";
    }

    public static void a(File file, File file2) throws IOException {
        if (file.isDirectory()) {
            if (!file2.exists() && !file2.mkdirs()) {
                throw new IOException("Cannot create dir " + file2.getAbsolutePath());
            }
            String[] list = file.list();
            for (int i = 0; i < list.length; i++) {
                a(new File(file, list[i]), new File(file2, list[i]));
            }
            return;
        }
        File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Cannot create dir " + parentFile.getAbsolutePath());
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] bArr = new byte[1024];
        while (true) {
            int i2 = fileInputStream.read(bArr);
            if (i2 > 0) {
                fileOutputStream.write(bArr, 0, i2);
            } else {
                fileInputStream.close();
                fileOutputStream.close();
                return;
            }
        }
    }

    public static boolean d(File file) {
        if (file.exists()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                return true;
            }
            for (int i = 0; i < fileArrListFiles.length; i++) {
                if (fileArrListFiles[i].isDirectory()) {
                    d(fileArrListFiles[i]);
                } else {
                    fileArrListFiles[i].delete();
                }
            }
        }
        return file.delete();
    }

    public static boolean ex(String str) {
        try {
            return getContext().getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean Qc() {
        String string;
        String[] strArrSplit = d.bfi.split(",");
        String[] strArrSplit2 = d.bfj.split(",");
        String str = "";
        int i = 0;
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (!ex(strArrSplit[i2])) {
                str = str + strArrSplit2[i2] + ", ";
                i++;
                String str2 = "listGameNeedToInstall: \"" + str + "\"";
            }
        }
        if (i <= 0) {
            return true;
        }
        String str3 = "listGameNeedToInstall: \"" + str + "\" lengh: " + str.split(", ").length;
        if (i == 1) {
            string = getContext().getResources().getString(getContext().getResources().getIdentifier("PACKAGE_NAME_INVALID_LESS", "string", getContext().getPackageName()));
        } else {
            string = getContext().getResources().getString(getContext().getResources().getIdentifier("PACKAGE_NAME_INVALID", "string", getContext().getPackageName()));
        }
        String strReplace = string.replace("{***}", str.substring(0, str.length() - 2));
        MIDlet mIDlet = MIDlet.cnz;
        new AlertDialog.Builder(getContext()).setMessage(strReplace.replace("####", MIDlet.d(getContext(), "MIDlet-Name"))).setOnKeyListener(new be()).setCancelable(false).setOnCancelListener(new bd()).setNeutralButton("OK", new bc()).create().show();
        return false;
    }

    public static void Qd() {
        try {
            Intent launchIntentForPackage = getContext().getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
            launchIntentForPackage.addFlags(67108864);
            launchIntentForPackage.addFlags(268435456);
            ((Activity) getContext()).startActivity(launchIntentForPackage);
            ((Activity) getContext()).finish();
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public static int Qe() {
        return biU;
    }

    public static void Qf() {
        biU = -1;
    }

    public static void Qg() {
        if (biU == -1) {
            biU = 3;
            new bf().start();
        }
    }

    public static String NB() {
        return TextUtils.isEmpty(bif) ? "" : bif;
    }

    public static boolean u(Context context, String str) {
        String upperCase;
        int i;
        long jA = a(context.getFilesDir().getPath(), context);
        String str2 = " Current memory space: " + jA;
        String str3 = " Minum memory require: " + MIDlet.coL;
        String[] strArr = {"EN", "FR", "DE", "IT", "ES", "JA", "KO", "ZH", "BR", "PT", "RU", "PL", "TR", "AR", "TH", "ID", "VI", "ZT"};
        int[][] iArr = {new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_EN, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_EN}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_FR, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_FR}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_DE, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_DE}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_IT, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_IT}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_SP, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_SP}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_JP, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_JP}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_KR, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_KR}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_CN, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_CN}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_BR, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_BR}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_PT, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_PT}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_RU, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_RU}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_PL, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_PL}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_TR, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_TR}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_AR, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_AR}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_TH, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_TH}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_ID, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_ID}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_VI, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_VI}, new int[]{com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_ZT, com.gameloft.android.ANMP.GloftGF2F.R.string.LOW_MEMORY_OK_ZT}};
        if (jA >= MIDlet.coL) {
            return false;
        }
        Locale locale = Locale.getDefault();
        if (str != null && !str.equals("")) {
            upperCase = str.toUpperCase();
        } else {
            try {
                upperCase = URLEncoder.encode(locale.getLanguage(), AndroidWebView.UTF_8).toUpperCase();
            } catch (Exception e) {
                upperCase = "EN";
            }
        }
        int i2 = 0;
        while (true) {
            if (i2 >= strArr.length) {
                i = -1;
                break;
            }
            if (upperCase.equals(strArr[i2])) {
                i = i2;
                break;
            }
            i2++;
        }
        int i3 = i != -1 ? i : 0;
        try {
            AlertDialog alertDialogCreate = new AlertDialog.Builder(context).setPositiveButton(context.getString(iArr[i3][1]), new bg()).setMessage(context.getString(iArr[i3][0])).create();
            alertDialogCreate.setCancelable(false);
            alertDialogCreate.setCanceledOnTouchOutside(false);
            alertDialogCreate.setOnKeyListener(new aw());
            alertDialogCreate.show();
        } catch (Exception e2) {
        }
        MIDlet.coH = true;
        return true;
    }

    public static String GenerateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String ey(String str) {
        String strOV = OV();
        if (strOV != "" && strOV != null && str.indexOf("/redir") != -1) {
            return strOV + str.substring(str.indexOf("/redir"));
        }
        return str;
    }

    public static void Qh() {
        if (!com.gameloft.a.b.a.fZ()) {
            com.gameloft.a.b.a.ho(MIDlet.d(getContext(), "GAME-SPACE"));
            com.gameloft.a.b.a.aX(MIDlet.d(getContext(), "GAME-PRODUCT-ID"));
            com.gameloft.a.b.a.aY(MIDlet.d(getContext(), "GAME-GGI"));
            com.gameloft.a.b.a.aZ(MIDlet.d(getContext(), "MIDlet-Version"));
            com.gameloft.a.b.a.ba(MIDlet.d(getContext(), "GAME-PLATFORM"));
            com.gameloft.a.b.a.bb(MIDlet.d(getContext(), "GAME-SHOP"));
            com.gameloft.a.b.a.gX();
            String strJ = com.gameloft.a.b.a.j(true, true);
            String strAR = com.gameloft.a.b.a.aR(strJ);
            com.gameloft.a.b.a.aT(strJ);
            com.gameloft.a.b.a.aU(strAR);
            com.gameloft.a.b.a.f(ap(getContext()), true);
            com.gameloft.a.b.a.aV("anonymous");
            com.gameloft.a.b.a.a(new ax());
            new Thread(new ay()).start();
        }
    }

    public static void ez(String str) {
        String str2 = "response = " + str;
        bhR = str;
    }

    public static String eA(String str) {
        if (bhR == null || bhR.equals("")) {
            return "";
        }
        try {
            return new JSONObject(bhR).getString(str);
        } catch (Exception e) {
            return "";
        }
    }

    public static void Qi() {
        System.gc();
    }

    public static String aL(String str) {
        String str2 = "https://eve.gameloft.com/";
        if ("https://eve.gameloft.com/" != 0 && "https://eve.gameloft.com/".endsWith("/")) {
            str2 = "" + "https://eve.gameloft.com/".substring(0, "https://eve.gameloft.com/".length() - 1);
        }
        String str3 = str2 + "/config/" + OY();
        if (str != null) {
            return str3 + "/datacenters/" + str + "/urls";
        }
        return str3;
    }

    public static boolean eB(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        new Thread(new az(str)).start();
        while (biV == null && System.currentTimeMillis() - jCurrentTimeMillis < 2000) {
        }
        if (biV == null) {
            return false;
        }
        boolean zBooleanValue = biV.booleanValue();
        String str2 = "mIsUpgradeRequiredWithWifi=" + biV;
        biV = null;
        return zBooleanValue;
    }

    public static void bH(boolean z) {
        bil = z;
    }

    public static boolean Qj() {
        return bil;
    }

    public static boolean Qk() {
        if (getContext() != null) {
            return cz.c(getContext()).areNotificationsEnabled();
        }
        return true;
    }

    public static void g(Intent intent) {
        if (intent != null && intent.getData() != null) {
            String dataString = intent.getDataString();
            if (intent.getAction().equals("android.intent.action.VIEW") && !TextUtils.isEmpty(dataString)) {
                bid = dataString;
                String str = "Received deep link: " + dataString;
            }
        }
    }

    public static void eC(String str) {
        getActivity().runOnUiThread(new ba(str));
    }

    public static void Ql() {
        if (bit != null) {
            bit.cancel();
        }
    }

    public static boolean a(String str, int i, String str2, String str3, String str4) {
        if (com.gameloft.a.b.a.fZ()) {
            return com.gameloft.a.b.a.a(str, i, str2, str3, str4);
        }
        return false;
    }
}
