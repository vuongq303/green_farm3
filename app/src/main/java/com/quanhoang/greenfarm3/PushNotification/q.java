package com.quanhoang.greenfarm3.PushNotification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.android.wrapper.ag;
import com.gameloft.android.wrapper.au;
import com.gameloft.glads.AndroidWebView;
import com.gameloft.glads.GLURLConnection;
import com.google.android.gms.ads.AdRequest;
import com.unity3d.ads.BuildConfig;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.microedition.midlet.MIDlet;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
public class q extends BroadcastReceiver {
    static ArrayList<String> FS;
    public static Bundle FT;
    public static WeakReference<Activity> FU;
    static int FV;
    static final boolean Ge;
    static boolean[] Gf;
    static int[] Gg;
    private static boolean FB = false;
    private static boolean FC = false;
    private static boolean FD = false;
    private static boolean FE = false;
    private static boolean FF = false;
    static boolean FG = false;
    static boolean[] FH = new boolean[2];
    public static boolean FI = false;
    public static String FJ = null;
    public static boolean FK = false;
    public static String FL = null;
    public static boolean FM = false;
    public static String FN = null;
    static q FO = null;
    public static String FP = "PANDORA_URL";
    public static String FQ = "CLIENTID";
    static String FR = "fcm";
    static boolean FW = true;
    public static p FX = null;
    public static String[] FY = {"EN", "FR", "DE", "ES", "IT", "BR", "PT", "TR", "RU", "PL", "ZT", "JA", "ZH", "KO", "TH", "VI", "AR", "TK", "ID", "ES_LATAM"};
    static String FZ = "gllive";
    static String Ga = Constants.PLATFORM;
    static String Gb = "GL_Channel";
    static String Gc = "GL_Channel_Silent";
    static String Gd = "GL_Channel_Custom";

    static {
        Ge = Build.VERSION.SDK_INT < 8;
        Gf = new boolean[7];
        Gg = new int[7];
    }

    static void Y(Context context) {
        z.init(context);
    }

    public static void q(Context context, String str) {
        SharedPreferences sharedPreferencesQ = k.Q(context);
        int iG = G(context);
        long jCurrentTimeMillis = System.currentTimeMillis() + 604800000;
        SharedPreferences.Editor editorEdit = sharedPreferencesQ.edit();
        editorEdit.putString("PN_FCM_regId", str);
        editorEdit.putInt("PN_appVersion", iG);
        editorEdit.putLong("PN_regId_ExpirationTimeMs", jCurrentTimeMillis);
        editorEdit.commit();
    }

    private static String Z(Context context) {
        SharedPreferences sharedPreferencesQ = k.Q(context);
        String string = sharedPreferencesQ.getString("PN_FCM_regId", "");
        if (ai(string) || sharedPreferencesQ.getInt("PN_appVersion", Integer.MIN_VALUE) != G(context) || hh()) {
            return "";
        }
        return string;
    }

    public static void i(Activity activity) {
        FU = new WeakReference<>(activity);
        if (FO == null) {
            FO = new q();
        }
        i.Init();
        FS = new ArrayList<>();
        k.init(activity);
        Y(activity);
        FC = true;
        f(activity.getIntent());
        new r(activity).start();
        SharedPreferences.Editor editorEdit = k.Q(activity).edit();
        editorEdit.putString("PN_GAME_VERSION", MIDlet.cnN);
        editorEdit.commit();
    }

    static void f(Intent intent) {
        int i;
        int i2;
        if (intent != null) {
            Bundle extras = intent.getExtras();
            au.bie = true;
            au.g(intent);
            if (extras != null) {
                for (String str : extras.keySet()) {
                }
                if ((intent.getFlags() & 1048576) == 0) {
                    FE = extras.getBoolean("pn_goto_multiplayer", false);
                    FT = new Bundle();
                    Bundle bundle = extras.getBundle("pn_data_bundle");
                    if (bundle != null) {
                        FT.putAll(bundle);
                    }
                    FD = true;
                    String string = extras.getString("pn_online");
                    if (ai(string)) {
                        i = 0;
                    } else {
                        try {
                            i = Integer.parseInt(string);
                        } catch (Exception e) {
                            i = 0;
                        }
                    }
                    boolean z = extras.getBoolean("discount_price", false);
                    FT.putInt("pn_online", i);
                    FT.putInt("pn_launch_game", FC ? 1 : 0);
                    FT.putInt("discount_price", z ? 1 : 0);
                    FT.putInt("pn_goto_multiplayer", FE ? 1 : 0);
                    FT.putString("pn_title", extras.getString("pn_title"));
                    extras.remove("pn_goto_multiplayer");
                    extras.remove("pn_data_bundle");
                    String string2 = extras.getString("pn_group_ID");
                    if (ai(string2)) {
                        i2 = -1;
                    } else {
                        try {
                            i2 = Integer.parseInt(string2);
                        } catch (Exception e2) {
                            i2 = -1;
                        }
                    }
                    if (i2 > -1) {
                        u(FU.get(), i2);
                    }
                    if (extras.getBoolean("START_FROM_PUSH", false)) {
                        MIDlet.akF();
                        com.quanhoang.greenfarm3.h.a(h(FT));
                        ag.a(h(FT));
                    }
                }
            }
        }
    }

    private static int G(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    private static boolean hh() {
        return System.currentTimeMillis() > k.Q(FU.get()).getLong("PN_regId_ExpirationTimeMs", -1L);
    }

    public static boolean ah(String str) {
        return false;
    }

    public static void e(Context context, Intent intent) {
        try {
            if (au.getContext() == null) {
                au.an(context);
            }
            Intent intent2 = new Intent();
            intent2.setClassName("com.quanhoang.greenfarm3", "com.quanhoang.greenfarm3.Start");
            intent2.setFlags(268435456);
            intent2.replaceExtras(intent);
            FC = true;
            if (FU != null) {
                intent2.setAction("android.intent.action.MAIN");
                intent2.addCategory("android.intent.category.LAUNCHER");
                FC = false;
                f(intent);
            }
            context.startActivity(intent2);
        } catch (Exception e) {
        }
    }

    public static void f(Context context, Intent intent) {
        int i;
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String string = extras.getString("pn_group_ID");
                if (ai(string)) {
                    i = -1;
                } else {
                    try {
                        i = Integer.parseInt(string);
                    } catch (Exception e) {
                        i = -1;
                    }
                }
                if (i > -1) {
                    u(context, i);
                }
            }
        } catch (Exception e2) {
        }
    }

    public static Intent a(Context context, String str, String str2, String str3, Bundle bundle) {
        if (str2.equals("url") || str2.equals("igpcode")) {
            if (!ai(str3)) {
                try {
                    if (str2.equals("igpcode")) {
                        if (au.getContext() == null) {
                            au.an(context);
                        }
                        String strPl = au.Pl();
                        str3 = ((str3 + "&udid=" + au.getSerial()) + "&hdidfv=" + au.OS()) + "&d=" + (Build.MANUFACTURER + "_" + Build.MODEL) + "&f=" + Build.VERSION.RELEASE + "&country=" + strPl + "&lg=" + Locale.getDefault().getLanguage() + "&game_ver=" + k.Q(context).getString("PN_GAME_VERSION", "") + "&igp_rev=1005";
                    }
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str3));
                    intent.setFlags(268435456);
                    return intent;
                } catch (Exception e) {
                }
            }
            Intent intent2 = new Intent();
            intent2.setFlags(268435456);
            return intent2;
        }
        if (str2.equals("play") || str2.equals("launch")) {
            Intent intent3 = new Intent(context, (Class<?>) PushIntentReceiver.class);
            if (bundle != null) {
                intent3.putExtra("pn_data_bundle", bundle);
            }
            if (str2.equals("play")) {
                intent3.putExtra("pn_goto_multiplayer", true);
            }
            intent3.putExtra("START_FROM_PUSH", true);
            return intent3;
        }
        if (str2.equals("autodownload")) {
            return null;
        }
        Intent intent4 = new Intent(context, (Class<?>) PushDeleteReceiver.class);
        if (bundle != null) {
            intent4.putExtra("pn_data_bundle", bundle);
            return intent4;
        }
        return intent4;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x00ab  */
    public static NotificationChannel aa(Context context) {
        AudioAttributes audioAttributesBuild;
        Uri uri;
        int i;
        String str = Gb;
        String str2 = "Gameloft Notification";
        if (!FK || FL == null) {
            audioAttributesBuild = null;
            uri = null;
        } else {
            try {
                if (context.getResources().getIdentifier(FL, "raw", context.getPackageName()) > 0) {
                    uri = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + FL);
                    try {
                        audioAttributesBuild = new AudioAttributes.Builder().setContentType(4).setUsage(6).build();
                        try {
                            str = Gd;
                            str2 = "Gameloft Custom Notification";
                        } catch (Exception e) {
                        }
                    } catch (Exception e2) {
                        audioAttributesBuild = null;
                    }
                } else {
                    audioAttributesBuild = null;
                    uri = null;
                }
            } catch (Exception e3) {
                audioAttributesBuild = null;
                uri = null;
            }
        }
        if (d.v(context)) {
            str = Gc;
            str2 = "Gameloft Silent Notification";
            i = 2;
        } else {
            i = 3;
        }
        NotificationChannel notificationChannel = new NotificationChannel(str, str2, i);
        if (str.equals(Gd)) {
            notificationChannel.setSound(uri, audioAttributesBuild);
        }
        notificationChannel.setDescription("Gameloft");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(-65536);
        notificationChannel.setShowBadge(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        return notificationChannel;
    }

    public static void a(Context context, String str, String str2, String str3, Intent intent, Bundle bundle) {
        int i;
        int i2;
        int i3;
        int i4;
        boolean zEquals;
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i5 = 0;
        int i6 = -1;
        String[] strArr = null;
        Intent intent2 = null;
        String string = bundle.getString("pn_group_ID");
        if (!ai(string)) {
            try {
                i6 = Integer.parseInt(string);
            } catch (Exception e) {
            }
        }
        SharedPreferences sharedPreferencesQ = k.Q(context);
        if ((str3.equals("launch") || str3.equals("info")) && Build.VERSION.SDK_INT >= 13) {
            if (i6 == -1) {
                if (str3.equals("launch")) {
                    i4 = 100;
                } else {
                    i4 = GLURLConnection.HTTP_OK;
                }
                intent.putExtra("pn_online", AppEventsConstants.EVENT_PARAM_VALUE_YES);
                i2 = i4;
            } else {
                if (str3.equals("launch")) {
                    i = 101;
                } else {
                    i = 201;
                }
                intent.putExtra("pn_online", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                i2 = i;
            }
            i3 = i2 + 32;
            int i7 = sharedPreferencesQ.getInt("PN_stackID_".concat(Integer.toString(i3)), 1);
            if (i7 == 0) {
                i7 = 31;
            }
            strArr = new String[i7];
            strArr[i7 - 1] = str;
            for (int i8 = 0; i8 < i7 - 1; i8++) {
                strArr[i8] = sharedPreferencesQ.getString("PN_stackMSG_".concat(Integer.toString(i3)).concat("_" + Integer.toString(i8 + 1)), "");
            }
            intent2 = new Intent(context, (Class<?>) PushDeleteReceiver.class);
            intent2.putExtra("pn_group_ID", Integer.toString(i2));
            intent.putExtra("pn_group_ID", Integer.toString(i2));
            i5 = i7;
        } else {
            i3 = sharedPreferencesQ.getInt("notificationID", 0);
        }
        String string2 = bundle.getString("infosmsdemo");
        if (!ai(string2)) {
            try {
                zEquals = string2.equals("discount_price");
            } catch (Exception e2) {
                zEquals = false;
            }
            intent.putExtra("discount_price", zEquals);
        }
        Y(context);
        intent.putExtra("pn_lib_intent", true);
        intent.putExtra("pn_title", str2);
        l lVarX = l.X(context);
        lVarX.setTitle(str2);
        lVarX.setMessage(str);
        lVarX.setTime(jCurrentTimeMillis);
        lVarX.setAutoCancel(true);
        if (i5 > 1) {
            lVarX.bb(i5);
        }
        if (strArr != null) {
            lVarX.c(strArr);
        }
        if (intent2 != null) {
            lVarX.d(PendingIntent.getBroadcast(context, i3, intent2, 134217728));
        }
        if (str3.equals("play") || str3.equals("launch") || str3.equals("info")) {
            lVarX.c(PendingIntent.getBroadcast(context, i3, intent, 134217728));
        } else {
            lVarX.c(PendingIntent.getActivity(context, i3, intent, 134217728));
        }
        Notification notificationBuild = lVarX.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (Build.VERSION.SDK_INT > 25) {
            notificationManager.createNotificationChannel(aa(context));
        }
        notificationManager.notify(i3, notificationBuild);
        SharedPreferences.Editor editorEdit = sharedPreferencesQ.edit();
        if ((str3.equals("launch") || str3.equals("info")) && Build.VERSION.SDK_INT >= 11) {
            editorEdit.putString("PN_stackMSG_".concat(Integer.toString(i3)).concat("_" + Integer.toString(i5 % 32)), str);
            editorEdit.putInt("PN_stackID_".concat(Integer.toString(i3)), (i5 + 1) % 32);
        } else {
            i3++;
            editorEdit.putInt("notificationID", i3 % 32);
        }
        editorEdit.commit();
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
            bundle2.putString("notificationID", "" + i3);
            com.quanhoang.greenfarm3.h.b(h(bundle2));
        }
    }

    public static void T(boolean z) {
        d(FU.get(), z);
    }

    public static void d(Context context, boolean z) {
        String strBY = MIDlet.cnz.bY("Has-Push-Notification");
        if (strBY != null && strBY.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_YES) != 0) {
            z = false;
        }
        k.b(context, z);
        if (!z) {
            V(true);
        }
    }

    public static void U(boolean z) {
        e(FU.get(), z);
    }

    public static void e(Context context, boolean z) {
        k.c(context, z);
    }

    public static void V(boolean z) {
        W(z);
        hi();
    }

    static void W(boolean z) {
        if (FS != null) {
            synchronized (FS) {
                new ArrayList(FS);
            }
        }
        e("Cancel_ALL", z);
    }

    public static void hi() {
        i.hf();
        List<String> listAc = ac(FU.get());
        if (listAc != null) {
            Iterator<String> it = listAc.iterator();
            while (it.hasNext()) {
                bd(Integer.parseInt(it.next()));
            }
        }
    }

    public static void hj() {
        FD = false;
    }

    public static boolean ai(String str) {
        return str == null || str.length() == 0;
    }

    private static String[] g(Bundle bundle) {
        String str = new String();
        String str2 = new String();
        String str3 = new String();
        String[] strArr = new String[3];
        if (bundle != null) {
            Iterator<String> it = bundle.keySet().iterator();
            while (true) {
                String str4 = str3;
                if (it.hasNext()) {
                    String next = it.next();
                    if (next.equals("body")) {
                        str2 = bundle.getString("body");
                    } else if (next.equals("username")) {
                        str = bundle.getString("username");
                    } else if (!ai(next) && bundle.getString(next) != null) {
                        str4 = str4 + "&X_" + next + "=" + encodeString(bundle.getString(next));
                    }
                    str3 = str4;
                } else {
                    strArr[0] = str;
                    strArr[1] = str2;
                    strArr[2] = str4;
                    return strArr;
                }
            }
        } else {
            return null;
        }
    }

    private static Hashtable h(Bundle bundle) {
        Hashtable hashtable = new Hashtable();
        String str = new String();
        String str2 = new String();
        new String();
        if (bundle != null) {
            Iterator<String> it = bundle.keySet().iterator();
            while (true) {
                String string = str2;
                if (it.hasNext()) {
                    String next = it.next();
                    if (next.equals("body")) {
                        string = bundle.getString("body");
                    } else if (next.equals("username")) {
                        str = bundle.getString("username");
                    } else if (!ai(next) && bundle.get(next) != null) {
                        if (bundle.get(next) instanceof String) {
                            hashtable.put(next, bundle.getString(next));
                        } else {
                            hashtable.put(next, bundle.get(next));
                        }
                    }
                    str2 = string;
                } else {
                    hashtable.put("username", str);
                    hashtable.put("body", string);
                    return hashtable;
                }
            }
        } else {
            return null;
        }
    }

    public static synchronized String c(String str, int i) {
        String strEn;
        try {
            if (FP.equals("PANDORA_URL") && com.quanhoang.greenfarm3.h.fV() != null) {
                FP = com.quanhoang.greenfarm3.h.fV().replace("/" + com.quanhoang.greenfarm3.h.getClientId(), "");
            }
            strEn = au.en(FP + "/locate?service=" + str);
            if (strEn == null || strEn.compareTo("") == 0) {
                strEn = "";
            }
        } catch (Exception e) {
            r(i, 9999);
            strEn = null;
        }
        return strEn;
    }

    public static String a(String str, String str2, String str3, int i) {
        String strC = c("auth", i);
        if (ai(strC)) {
            return "";
        }
        if (FQ.equals("CLIENTID")) {
            FQ = com.quanhoang.greenfarm3.h.getClientId();
            if (ai(FQ)) {
            }
        }
        String str4 = "https://" + strC + "/authorize";
        String str5 = "?client_id=" + FQ + "&username=" + str + "&password=" + str2 + "&credential_type=" + str3 + "&device_id=" + au.Os() + "&scope=message%20auth%20message_config&access_token_only=true";
        Locale.getDefault();
        String strEncodeString = encodeString(Build.MODEL);
        String strEncodeString2 = encodeString(au.getNetworkOperator());
        String upperCase = au.Pl().toUpperCase();
        String str6 = upperCase.equals("") ? "US" : upperCase;
        String strW = k.W(FU.get());
        if (strW.equals("")) {
            strW = "en";
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(a(new URL(str4 + (str5 + "&device_model=" + strEncodeString + "&device_carrier=" + strEncodeString2 + "&device_country=" + str6 + "&device_language=" + strW)), false).getInputStream()));
            String str7 = null;
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                str7 = line;
            }
            if (ai(str7)) {
                r(i, 1002);
            } else {
                return str7;
            }
        } catch (IOException e) {
            r(i, BuildConfig.VERSION_CODE);
        } catch (Exception e2) {
            r(i, 1002);
        }
        return "";
    }

    static boolean a(String str, String str2, int i) {
        String strHn = hn();
        if (ai(strHn)) {
            r(i, 2001);
            return false;
        }
        String strC = c(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, i);
        if (ai(strC)) {
            return false;
        }
        String str3 = "https://" + strC + "/transports/" + FR + "/endpoints/" + strHn;
        String str4 = "access_token=" + str;
        try {
            HttpsURLConnection httpsURLConnectionA = a(new URL(str3), true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnectionA.getOutputStream());
            dataOutputStream.writeBytes(str4);
            dataOutputStream.close();
            int responseCode = httpsURLConnectionA.getResponseCode();
            if (responseCode == 200 || responseCode == 409) {
                return true;
            }
        } catch (Exception e) {
        }
        r(i, 4002);
        return false;
    }

    static boolean aj(String str) {
        return d(str, 6);
    }

    static boolean d(String str, int i) {
        String strC = c(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, i);
        if (ai(strC)) {
            return false;
        }
        String str2 = "access_token=" + str;
        try {
            HttpsURLConnection httpsURLConnectionA = a(new URL("https://" + strC + "/transports/" + FR + "/endpoints/delete"), true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnectionA.getOutputStream());
            dataOutputStream.writeBytes(str2);
            dataOutputStream.close();
            int responseCode = httpsURLConnectionA.getResponseCode();
            if (responseCode == 200 || responseCode == 409) {
                return true;
            }
        } catch (Exception e) {
        }
        r(i, 4002);
        return false;
    }

    static void i(Bundle bundle) {
        bundle.putInt("jrtry", 3);
        FO.j(bundle);
    }

    void j(Bundle bundle) {
        new v(this).f(bundle);
    }

    static String a(String str, String str2, String str3, String str4, String str5, String str6) {
        String str7;
        String strC = c(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, 2);
        if (ai(strC)) {
            return "e";
        }
        String str8 = "https://" + strC + "/messages/" + FR + "/" + str;
        if (!ai(str2)) {
            str7 = "body=" + encodeString(str3) + "&delay=" + str4 + "&replace_label=" + encodeString(str2) + "&access_token=" + str5 + str6;
        } else {
            str7 = "body=" + encodeString(str3) + "&delay=" + str4 + "&access_token=" + str5 + str6;
        }
        try {
            HttpsURLConnection httpsURLConnectionA = a(new URL(str8), true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnectionA.getOutputStream());
            dataOutputStream.writeBytes(str7);
            dataOutputStream.close();
            String string = "e";
            int responseCode = httpsURLConnectionA.getResponseCode();
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnectionA.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuffer.append(line);
                    stringBuffer.append('\r');
                }
                bufferedReader.close();
                try {
                    string = new JSONObject(stringBuffer.toString()).getString(ShareConstants.WEB_DIALOG_PARAM_ID);
                } catch (JSONException e) {
                    try {
                        string = new JSONObject(new JSONArray(stringBuffer.toString()).get(0).toString()).getString(ShareConstants.WEB_DIALOG_PARAM_ID);
                    } catch (JSONException e2) {
                        r(2, 4002);
                    }
                }
                synchronized (FS) {
                    FS.add(string);
                }
                return string;
            }
            if (responseCode == 404) {
                r(2, 4001);
                return "e";
            }
            r(2, 9999);
            return "e";
        } catch (Exception e3) {
            r(2, 9999);
            return "e";
        }
    }

    public static String a(Hashtable hashtable, String str, String str2, boolean z) {
        Bundle bundle = new Bundle();
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            bundle.putString((String) objNextElement, (String) hashtable.get(objNextElement));
        }
        return a(bundle, str, str2, z, 0);
    }

    public static String a(Bundle bundle, String str, String str2, boolean z, int i) {
        return a(FU.get(), bundle, str, str2, z, i);
    }

    public static String a(Context context, Bundle bundle, String str, String str2, boolean z, int i) {
        String strA;
        int i2;
        String string;
        String[] strArrG = g(bundle);
        if (strArrG != null) {
            String str3 = strArrG[0];
            String str4 = strArrG[1];
            String str5 = strArrG[2];
            String str6 = ai(str) ? AppEventsConstants.EVENT_PARAM_VALUE_YES : str;
            try {
                i2 = Integer.parseInt(str6);
            } catch (Exception e) {
                i2 = 0;
            }
            if (i2 > 0) {
                SharedPreferences sharedPreferencesQ = k.Q(context);
                SharedPreferences.Editor editorEdit = sharedPreferencesQ.edit();
                String string2 = null;
                String str7 = null;
                for (String str8 : bundle.keySet()) {
                    if (str8.equals("infopush")) {
                        string2 = bundle.getString("infopush");
                        string = str7;
                    } else {
                        string = str8.equals("per_of_day") ? bundle.getString("per_of_day") : str7;
                    }
                    str7 = string;
                }
                if (string2 != null && str7 != null && !sharedPreferencesQ.contains("info_" + string2)) {
                    editorEdit.putInt("info_" + string2, Integer.parseInt(str7));
                    editorEdit.putInt("count_" + string2, 0);
                    editorEdit.commit();
                }
                if (i2 <= i.Ft) {
                    String string3 = bundle.getString("type");
                    if (!ai(string3)) {
                        if (string3.equals("launch")) {
                            i += 101;
                        } else if (string3.equals("info")) {
                            i += 201;
                        }
                    }
                    bundle.putString("pn_group_ID", Integer.toString(i));
                    bundle.putString("creation_time", i.af(AppEventsConstants.EVENT_PARAM_VALUE_NO));
                    bundle.putString("schedule_time", i.af(Integer.toString(i2)));
                    String strA2 = i.a(bundle, i2);
                    d(context, strA2, Integer.toString(i));
                    w(context, i);
                    return strA2;
                }
                String string4 = sharedPreferencesQ.getString("RandomUserID", null);
                String string5 = sharedPreferencesQ.getString("RandomUserToken", null);
                if (!ai(string5)) {
                    if (!ai(string4)) {
                        String str9 = "anonymous:" + string4;
                        if (z) {
                            FV++;
                            FV %= AdRequest.MAX_CONTENT_URL_LENGTH;
                            String str10 = FV + "";
                            bundle.putString("pn_request_id", str10);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("juid", str9);
                            bundle2.putString("jlbl", str2);
                            bundle2.putString("jbdy", str4);
                            bundle2.putString("jdly", str6);
                            bundle2.putString("jtkn", string5);
                            bundle2.putString("jxtr", str5);
                            bundle2.putString("jrqst", str10);
                            i(bundle2);
                            return "p";
                        }
                        strA = a(str9, str2, str4, str6, string5, str5);
                    } else {
                        r(2, 1001);
                        strA = "e";
                    }
                } else {
                    r(2, 1002);
                }
                if (strA.compareTo("e") == 0) {
                }
                return strA;
            }
            r(2, 1006);
        } else {
            r(2, 1005);
        }
        strA = "e";
        if (strA.compareTo("e") == 0) {
        }
        return strA;
    }

    public static String encodeString(String str) {
        try {
            return URLEncoder.encode(str, AndroidWebView.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    public static int j(String str, String str2) {
        if (Ge) {
            r(0, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
            return -1;
        }
        if (bc(5) != 0 && bc(5) != 2001) {
            r(0, bc(5));
            return -1;
        }
        au.an(FU.get());
        String strOs = au.Os();
        if (!ai(str)) {
            if (!ai(str2)) {
                if (!ai(strOs)) {
                    aj(str2);
                    if (a(str2, strOs, 0)) {
                        SharedPreferences.Editor editorEdit = k.Q(FU.get()).edit();
                        editorEdit.putString("RandomUserID", str);
                        editorEdit.putString("RandomUserToken", str2);
                        editorEdit.commit();
                        FH[0] = true;
                        return 0;
                    }
                } else {
                    r(0, 1003);
                }
            } else {
                r(0, 1002);
            }
        } else {
            r(0, 1001);
        }
        return -1;
    }

    public static int a(String str, String str2, String str3) {
        FZ = str3;
        return k(str, str2);
    }

    public static int k(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("juid", str);
        bundle.putString("jusrpss", str2);
        bundle.putString("jusrcred", FZ);
        bundle.putLong("jbckff", 20000L);
        bundle.putInt("jrtry", 3);
        return FO.l(bundle);
    }

    private int k(Bundle bundle) {
        new y(this).f(bundle);
        return 0;
    }

    public static void ak(String str) {
        al(str);
        new Thread(new s()).start();
    }

    public static void al(String str) {
        int i;
        String str2;
        int i2 = 0;
        String upperCase = str.toUpperCase();
        int i3 = 0;
        while (true) {
            i = i2;
            if (i3 >= FY.length || upperCase.equals(FY[i3])) {
                break;
            }
            i2 = i + 1;
            i3++;
        }
        if (i >= FY.length) {
            str2 = "en";
        } else {
            String lowerCase = str.toLowerCase();
            if (lowerCase.equals("br")) {
                lowerCase = "pt";
            }
            str2 = lowerCase.equals("es_latam") ? "le" : lowerCase;
        }
        k.p(FU.get(), str2);
    }

    public static void hk() {
        FG = true;
        r(5, 0);
    }

    static void r(int i, int i2) {
        Gg[i] = i2;
    }

    public static int bc(int i) {
        return Gg[i];
    }

    private int l(Bundle bundle) {
        new x(this).f(bundle);
        return 0;
    }

    public static int b(String str, String str2, String str3) {
        String strC = c("auth", 1);
        if (ai(strC) || ai(str) || ai(str2) || ai(str3)) {
            r(1, 1001);
            return -1;
        }
        String str4 = ((("access_token=" + str3) + "&credential=" + str) + "&password=" + str2) + "&if_exists=relink";
        try {
            HttpsURLConnection httpsURLConnectionA = a(new URL("https://" + strC + "/users/me/credentials"), true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnectionA.getOutputStream());
            dataOutputStream.writeBytes(str4);
            dataOutputStream.close();
            if (httpsURLConnectionA.getResponseCode() == 200) {
                return 0;
            }
        } catch (Exception e) {
        }
        r(1, 4002);
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String hl() {
        if (ai("com.quanhoang.greenfarm3") || ai(au.Os()) || ai(Build.MANUFACTURER) || ai(Build.MODEL)) {
            return null;
        }
        return ("com.quanhoang.greenfarm3." + Build.MANUFACTURER + "." + Build.MODEL + "." + au.Os()).replace(" ", "_");
    }

    public static int e(String str, boolean z) {
        int iAm;
        if (!ai(k.Q(FU.get()).getString("RandomUserToken", null))) {
            if (!ai(str)) {
                if (str.startsWith("PN_LID_")) {
                    i.ac(str);
                    return 1;
                }
                if (bc(5) != 0 && bc(5) != 2001) {
                    r(3, bc(5));
                    return 0;
                }
                if (z) {
                    Bundle bundle = new Bundle();
                    bundle.putString("jphid", str);
                    bundle.putInt("jrtry", 3);
                    m(bundle);
                    return -1;
                }
                iAm = am(str);
            } else {
                r(3, 1004);
                iAm = 0;
            }
        } else {
            r(3, 1002);
            iAm = 0;
        }
        if (iAm == 0) {
        }
        return iAm;
    }

    static void m(Bundle bundle) {
        FO.n(bundle);
    }

    void n(Bundle bundle) {
        new u(this).f(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int am(String str) {
        String str2;
        String strC = c(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, 3);
        if (ai(strC)) {
            return 0;
        }
        String str3 = "https://" + strC + "/users/me/credentials";
        String string = k.Q(FU.get()).getString("RandomUserToken", null);
        String str4 = "https://" + strC + "/messages/" + FR + "/me";
        if (str.equals("Cancel_ALL")) {
            str2 = "?access_token=" + encodeString(string);
        } else {
            str2 = "?access_token=" + encodeString(string) + "&msgids=" + str;
        }
        try {
            URL url = new URL(str4 + str2);
            FF = true;
            int responseCode = a(url, true).getResponseCode();
            if (responseCode == 200 || responseCode == 404) {
                synchronized (FS) {
                    try {
                        if (str.equals("Cancel_ALL")) {
                            FS.clear();
                        } else {
                            FS.remove(str);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return 1;
            }
        } catch (Exception e) {
            r(3, 9999);
        }
        return 0;
    }

    public static int hm() {
        Bundle bundle = new Bundle();
        bundle.putInt("jrtry", 3);
        bundle.putLong("jbckff", 20000L);
        return FO.o(bundle);
    }

    public int o(Bundle bundle) {
        new w(this).f(bundle);
        return 0;
    }

    public static String hn() {
        String strZ;
        if (!FirebaseInstanceIDService.Ff.equals("")) {
            String str = FirebaseInstanceIDService.Ff;
            q(FU.get(), str);
            strZ = str;
        } else {
            strZ = Z(FU.get());
        }
        return !ai(strZ) ? strZ : "";
    }

    static HttpsURLConnection a(URL url, boolean z) {
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, new TrustManager[]{new t()}, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            try {
                httpsURLConnection.setConnectTimeout(5000);
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setUseCaches(false);
                HttpsURLConnection.setFollowRedirects(true);
                httpsURLConnection.setRequestProperty("Connection", "Keep-Alive");
                if (FF) {
                    FF = false;
                    httpsURLConnection.setDoOutput(false);
                    httpsURLConnection.setRequestMethod("DELETE");
                } else if (z) {
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setDoInput(true);
                } else {
                    httpsURLConnection.setRequestMethod("GET");
                }
                return httpsURLConnection;
            } catch (Exception e) {
                return httpsURLConnection;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void s(int i, int i2) {
        a(i, i2, new Bundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(int i, int i2, Bundle bundle) {
        Activity activity = FU.get();
        if (i2 == -1) {
            int i3 = bundle.getInt("jrtry", 0);
            if (i3 == 0) {
                b(i, -2, bundle);
            } else {
                bundle.putInt("jrtry", i3 - 1);
                Intent intent = new Intent("com.quanhoang.greenfarm3.PN_RETRY_REGITER_ACTION");
                long j = bundle.getLong("jbckff", 20000L);
                long jCurrentTimeMillis = System.currentTimeMillis() + j;
                bundle.putLong("jbckff", j << 1);
                intent.putExtra("pn_rqstType", i);
                intent.putExtra("pn_rqstParams", bundle);
                Gf[i] = true;
                activity.registerReceiver(FO, new IntentFilter("com.quanhoang.greenfarm3.PN_RETRY_REGITER_ACTION"));
                ((AlarmManager) activity.getSystemService("alarm")).set(0, jCurrentTimeMillis, PendingIntent.getBroadcast(activity, i + 1, intent, 134217728));
            }
        } else {
            Gf[i] = false;
            if (!ho()) {
                try {
                    activity.unregisterReceiver(FO);
                } catch (Exception e) {
                }
            }
        }
        b(i, i2, bundle);
    }

    private boolean ho() {
        for (int i = 0; i < 7; i++) {
            if (Gf[i]) {
                return true;
            }
        }
        return false;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().compareTo("com.quanhoang.greenfarm3.PN_RETRY_REGITER_ACTION") == 0) {
            int intExtra = intent.getIntExtra("pn_rqstType", -1);
            Bundle bundleExtra = intent.getBundleExtra("pn_rqstParams");
            switch (intExtra) {
                case 0:
                    o(bundleExtra);
                    break;
                case 1:
                    if (FW) {
                        k(bundleExtra);
                    } else {
                        l(bundleExtra);
                    }
                    break;
                case 2:
                    j(bundleExtra);
                    break;
                case 3:
                    n(bundleExtra);
                    break;
            }
        }
    }

    public static synchronized void b(int i, int i2, Bundle bundle) {
        Hashtable hashtable = new Hashtable();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                if (!ai(str) && bundle.get(str) != null) {
                    if (bundle.get(str) instanceof String) {
                        hashtable.put(str, encodeString(bundle.getString(str)));
                    } else {
                        hashtable.put(str, bundle.get(str));
                    }
                }
            }
        }
        com.quanhoang.greenfarm3.h.a(i, i2, hashtable);
    }

    public static int bd(int i) {
        List<String> listR;
        if (r(FU.get(), Integer.toString(i + 101)) != null) {
            listR = r(FU.get(), Integer.toString(i + 101));
            i += 101;
        } else if (r(FU.get(), Integer.toString(i + 201)) != null) {
            listR = r(FU.get(), Integer.toString(i + 201));
            i += 201;
        } else {
            listR = r(FU.get(), Integer.toString(i));
        }
        if (listR == null) {
            return 1;
        }
        for (String str : listR) {
            try {
                if (ai(str) || !str.startsWith("PN_LID_")) {
                    return 1;
                }
                i.ac(str);
            } catch (Exception e) {
                return 1;
            }
        }
        be(i);
        return 0;
    }

    public static void u(Context context, int i) {
        if (context != null) {
            String strConcat = "PN_stackID_".concat(Integer.toString(i + 32));
            SharedPreferences.Editor editorEdit = k.Q(context).edit();
            editorEdit.putInt(strConcat, 1);
            editorEdit.commit();
            v(context, i);
        }
    }

    private static void v(Context context, int i) {
        SharedPreferences.Editor editorEdit = k.Q(context).edit();
        for (int i2 = 0; i2 < 33; i2++) {
            editorEdit.putString("PN_stackMSG_".concat(Integer.toString(i + 32)).concat("_" + Integer.toString(i2 + 1)), null);
        }
        editorEdit.commit();
    }

    private static void be(int i) {
        String str = "LocalPNType_" + Integer.toString(i);
        SharedPreferences.Editor editorEdit = k.Q(FU.get()).edit();
        editorEdit.putString(str, null);
        editorEdit.commit();
        r(FU.get(), Integer.toString(i));
    }

    private static void d(Context context, String str, String str2) {
        List listR = r(context, str2);
        if (listR == null) {
            listR = new ArrayList();
        }
        listR.add(str);
        String str3 = "";
        Iterator it = listR.iterator();
        while (it.hasNext()) {
            str3 = str3 + ((String) it.next()) + "|";
        }
        SharedPreferences.Editor editorEdit = k.Q(context).edit();
        editorEdit.putString("LocalPNType_" + str2, str3);
        editorEdit.commit();
    }

    private static List<String> r(Context context, String str) {
        ArrayList arrayList = null;
        String string = k.Q(context).getString("LocalPNType_" + str, null);
        if (string != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, "|");
            arrayList = new ArrayList();
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(stringTokenizer.nextToken());
            }
        }
        return arrayList;
    }

    public static void an(String str) {
        boolean z;
        List<String> listAb = ab(FU.get());
        List<String> arrayList = listAb == null ? new ArrayList() : listAb;
        Iterator<String> it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (it.next().equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            arrayList.add(str);
            String str2 = "";
            Iterator<String> it2 = arrayList.iterator();
            while (true) {
                String str3 = str2;
                if (it2.hasNext()) {
                    str2 = str3 + it2.next() + "|";
                } else {
                    SharedPreferences.Editor editorEdit = k.Q(FU.get()).edit();
                    editorEdit.putString("UserID", str3);
                    editorEdit.commit();
                    return;
                }
            }
        }
    }

    public static List<String> ab(Context context) {
        ArrayList arrayList = null;
        String string = k.Q(context).getString("UserID", null);
        if (string != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, "|");
            arrayList = new ArrayList();
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(stringTokenizer.nextToken());
            }
        }
        return arrayList;
    }

    public static boolean s(Context context, String str) {
        List<String> listAb = ab(context);
        if (listAb == null) {
            return false;
        }
        Iterator<String> it = listAb.iterator();
        while (it.hasNext()) {
            if (it.next().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static void w(Context context, int i) {
        boolean z;
        List<String> listAc = ac(context);
        List<String> arrayList = listAc == null ? new ArrayList() : listAc;
        String str = "" + i;
        Iterator<String> it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (it.next().equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            arrayList.add(str);
            String str2 = "";
            Iterator<String> it2 = arrayList.iterator();
            while (true) {
                String str3 = str2;
                if (it2.hasNext()) {
                    str2 = str3 + it2.next() + "|";
                } else {
                    SharedPreferences.Editor editorEdit = k.Q(context).edit();
                    editorEdit.putString("PN_GroupId", str3);
                    editorEdit.commit();
                    return;
                }
            }
        }
    }

    public static List<String> ac(Context context) {
        ArrayList arrayList = null;
        String string = k.Q(context).getString("PN_GroupId", null);
        if (string != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, "|");
            arrayList = new ArrayList();
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(stringTokenizer.nextToken());
            }
        }
        return arrayList;
    }

    public static List<String> ad(Context context) {
        ArrayList arrayList = null;
        String string = k.Q(context).getString("PN_Category", null);
        if (string != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, "|");
            arrayList = new ArrayList();
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(stringTokenizer.nextToken());
            }
        }
        return arrayList;
    }

    public static boolean t(Context context, String str) {
        List<String> listAd = ad(context);
        if (listAd == null) {
            return false;
        }
        Iterator<String> it = listAd.iterator();
        while (it.hasNext()) {
            if (it.next().equals(str)) {
                return true;
            }
        }
        return false;
    }
}
