package javax.microedition.midlet;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.PowerManager;
import android.os.Process;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import com.gameloft.adsmanager.AdsManager;
import com.gameloft.android.ANMP.GloftGF2F.bx;
import com.gameloft.android.wrapper.NativeUtils;
import com.gameloft.android.wrapper.ae;
import com.gameloft.android.wrapper.ar;
import com.gameloft.android.wrapper.au;
import java.io.File;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Timer;

/* JADX INFO: loaded from: classes.dex */
public abstract class MIDlet extends BaseActivity {
    public static String VERSION;
    public static Toast bit;
    public static String callingPackage;
    public static Activity cnA;
    public static boolean cnC;
    public static boolean cnD;
    public static int cnE;
    public static boolean cnF;
    public static boolean cnG;
    public static boolean cnH;
    public static boolean cnJ;
    public static boolean cnK;
    public static boolean cnL;
    public static String cnM;
    public static String cnN;
    public static boolean cnO;
    public static boolean cnP;
    public static Hashtable cnQ;
    public static boolean cnR;
    public static boolean cnS;
    public static boolean cnT;
    public static boolean cnU;
    public static boolean cnV;
    public static int cnW;
    public static boolean cnX;
    public static boolean cnY;
    public static String cnZ;
    public static MIDlet cnz;
    public static boolean coA;
    public static int coC;
    public static int coD;
    public static PowerManager coE;
    public static KeyguardManager coF;
    public static boolean coG;
    public static boolean coH;
    public static boolean coI;
    public static boolean coJ;
    public static boolean coK;
    public static int coL;
    public static boolean coM;
    public static boolean coN;
    public static boolean coO;
    public static boolean coP;
    public static int coQ;
    public static boolean coR;
    public static AudioManager coS;
    public static boolean coT;
    public static boolean coa;
    public static boolean cob;
    public static boolean coc;
    public static boolean cod;
    public static boolean coe;
    public static boolean cof;
    public static boolean cog;
    public static String coh;
    public static boolean coi;
    public static boolean coj;
    public static boolean cok;
    public static boolean col;

    /* JADX INFO: renamed from: com, reason: collision with root package name */
    public static boolean f24com;
    public static boolean con;
    public static boolean coo;
    public static boolean cop;
    public static boolean coq;
    public static boolean cor;
    public static boolean cos;
    public static boolean cot;
    public static double cov;
    public static int cow;
    public static int cox;
    public static int coy;
    public static int coz;
    public Configuration cnB;
    protected PowerManager.WakeLock cnI;
    public boolean coB;
    public javax.microedition.lcdui.t cou = null;

    protected abstract void al(boolean z);

    protected abstract void mj();

    protected abstract void mk();

    static {
        int i;
        int i2;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int i3 = displayMetrics.widthPixels;
        int i4 = displayMetrics.heightPixels;
        if (com.gameloft.android.wrapper.d.beG.compareTo("sensorLandscape") == 0 && i3 < i4) {
            i = i3;
            i2 = i4;
        } else if (com.gameloft.android.wrapper.d.beG.compareTo("portrait") != 0 || i3 <= i4) {
            i = i4;
            i2 = i3;
        } else {
            i = i3;
            i2 = i4;
        }
        String str = "static Width = " + i2 + " Height = " + i;
        cnC = false;
        cnD = false;
        cnE = 2;
        callingPackage = null;
        cnF = false;
        cnG = false;
        cnH = false;
        cnJ = false;
        cnK = false;
        cnL = false;
        cnM = "";
        VERSION = "";
        cnN = "";
        cnO = false;
        cnP = false;
        cnQ = new Hashtable();
        cnR = false;
        cnS = false;
        cnT = false;
        cnU = false;
        cnV = false;
        cnW = 0;
        cnX = false;
        cnY = false;
        cnZ = null;
        coa = false;
        cob = false;
        coc = false;
        cod = false;
        coe = false;
        cof = false;
        cog = false;
        coh = null;
        coi = false;
        coj = false;
        cok = false;
        col = false;
        f24com = false;
        con = false;
        coo = false;
        cop = false;
        coq = false;
        cor = false;
        cos = false;
        cot = false;
        cov = -1.0d;
        cow = -1;
        cox = -1;
        coy = -1;
        coz = -1;
        coA = false;
        coC = -1;
        coD = 0;
        coG = false;
        coH = false;
        coI = false;
        coJ = false;
        coK = false;
        coL = 1;
        coM = false;
        coN = false;
        coO = false;
        coP = false;
        coQ = -1;
        coR = false;
        coT = false;
    }

    public final void akD() {
        cnC = true;
        if (coi) {
            if (coj) {
                try {
                    javax.microedition.b.b.akV();
                } catch (Exception e) {
                }
            }
            if (Build.VERSION.SDK_INT >= 21 && ((ActivityManager) getSystemService("activity")).isInLockTaskMode()) {
                stopLockTask();
            }
            try {
                Thread.sleep(1000L);
            } catch (Exception e2) {
            }
            akA();
            if (isFinishing()) {
                setResult(274);
            }
            if (cnR) {
                try {
                    al(true);
                    return;
                } catch (m e3) {
                    e3.printStackTrace();
                    return;
                }
            }
            return;
        }
        setResult(cnE);
        if (au.bhL) {
        }
        try {
            Thread.sleep(1000L);
        } catch (Exception e4) {
        }
        finish();
        if (cnR) {
            try {
                al(true);
            } catch (m e5) {
                e5.printStackTrace();
            }
        }
        System.exit(0);
    }

    public final String bY(String str) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        if (!cnO && !cnP) {
            str = str.replace("-", "_");
        }
        if (str.toLowerCase().compareTo("microedition.locale") == 0) {
            return Locale.getDefault().toString();
        }
        if (str.toLowerCase().compareTo("microedition.device") == 0) {
            return Build.MODEL;
        }
        if (str.toLowerCase().compareTo("microedition.device.manufacturer") == 0) {
            return Build.MANUFACTURER;
        }
        if (str.toLowerCase().compareTo("microedition.device.firmware") == 0) {
            return Build.VERSION.RELEASE;
        }
        if (str.toLowerCase().compareTo("mac") == 0) {
            return au.ON();
        }
        if (str.toLowerCase().compareTo("microedition.inch") == 0) {
            return "" + cov;
        }
        if (str.toLowerCase().compareTo("microedition.widthreal") == 0) {
            return "" + cow;
        }
        if (str.toLowerCase().compareTo("microedition.heightreal") == 0) {
            return "" + cox;
        }
        if (str.toLowerCase().compareTo("microedition.width") == 0) {
            return "" + coy;
        }
        if (str.toLowerCase().compareTo("microedition.height") == 0) {
            return "" + coz;
        }
        if (str.toLowerCase().compareTo("microedition.screen.virtual") == 0) {
            return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
        }
        if (str.toLowerCase().compareTo("conectivity") == 0 || str.toLowerCase().compareTo("connectivity") == 0) {
            if (au.Pd()) {
                return "on";
            }
            return "off";
        }
        if (str.toLowerCase().compareTo("type.connectivity") == 0) {
            return "" + au.Pg();
        }
        if (str.toLowerCase().compareTo("injectedserialkey") == 0) {
            return au.PC();
        }
        if (str.toLowerCase().compareTo("injectedigp") == 0) {
            return au.PA();
        }
        if (str.toLowerCase().compareTo("deviceuptimemillis") == 0) {
            return au.Ph();
        }
        if (str.compareTo("clock") == 0) {
            return "" + NativeUtils.getClock();
        }
        if (str.compareTo("timemillis") == 0) {
            return "" + NativeUtils.getClockMillis();
        }
        if (str.compareTo("timenano") == 0) {
            return "" + NativeUtils.getClockNano();
        }
        if (str.compareTo("getAvailableSpace") == 0) {
            return "" + au.a(getFilesDir().getPath(), au.getContext());
        }
        if (str.compareTo("gameversionletter") == 0) {
            return cnN;
        }
        if (str.compareTo("microedition.locale.country") == 0) {
            return au.Pl().toUpperCase();
        }
        if (str.compareTo("microedition.locale.simcountry") == 0) {
            return au.Po().toUpperCase();
        }
        if (str.compareTo("URL-TERMS") == 0) {
            return au.ev(au.Pk());
        }
        if (str.compareTo("URL-PRIVACY") == 0) {
            return au.eu(au.Pk());
        }
        if (str.compareTo("DeviceID") == 0) {
            return au.NK();
        }
        if (str.compareTo("MAC") == 0) {
            return au.Px();
        }
        if (str.compareTo("hasVibrator") == 0) {
            if (au.PS()) {
                return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
            }
            return "false";
        }
        if (str.compareTo("hasSim") == 0) {
            if (au.PT()) {
                return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
            }
            return "false";
        }
        if (str.compareTo("AndroidID") == 0) {
            return au.OQ();
        }
        if (str.compareTo("SerialNumber") == 0) {
            return au.getSerialNumber();
        }
        return d(this, str);
    }

    public static String d(Object obj, String str) {
        Activity activity;
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        if (obj instanceof Activity) {
            activity = (Activity) obj;
        } else {
            if (au.getContext() == null) {
                return null;
            }
            activity = (Activity) au.getContext();
        }
        if (str.compareTo("microedition.locale.usercountry") == 0) {
            return au.PM().toUpperCase();
        }
        if (!cnO && !cnP) {
            str = str.replace("-", "_");
        }
        if (!cnO && !cnP) {
            int identifier = activity.getResources().getIdentifier(str, "string", activity.getPackageName());
            if (identifier == 0) {
                return null;
            }
            return activity.getResources().getString(identifier);
        }
        if (cnP) {
            e(activity, "res/raw/content.bin");
        }
        if (cnO) {
            e(activity, "META-INF/services/Game");
        }
        if (!cnQ.containsKey(str)) {
            return null;
        }
        if (str.compareTo("URL-SUPPORT") == 0) {
            String str2 = (String) cnQ.get(str);
            if (str2.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) != 0) {
                String[] strArrSplit = au.V(au.OV(), str2).split("from=");
                strArrSplit[1] = "" + d(cnz, "GAME-IGP-CODE") + strArrSplit[1].substring(strArrSplit[1].indexOf("&"), strArrSplit[1].length());
                return strArrSplit[0] + "from=" + strArrSplit[1];
            }
            return str2;
        }
        if (str.compareTo("URL-TEMPLATE-GAME") == 0 || str.compareTo("URL-OPERATOR") == 0) {
            String str3 = (String) cnQ.get(str);
            if (str3.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_NO) != 0) {
                return au.V(au.OV(), str3);
            }
            return str3;
        }
        return (String) cnQ.get(str);
    }

    public final boolean jf(String str) {
        if ("MIDlet://minimize".equals(str)) {
            moveTaskToBack(true);
            return false;
        }
        if (str.startsWith("GlotInitLogger:")) {
            String strSubstring = str.substring(15);
            strSubstring.substring(15).split(":");
            int iIndexOf = strSubstring.indexOf(":");
            if (iIndexOf > 0) {
                au.a(strSubstring.substring(0, iIndexOf).trim(), strSubstring.substring(iIndexOf + 1).trim().equals("TRUE"), "");
            }
            return false;
        }
        if (str.startsWith("GlotLogger:")) {
            au.eo(str.substring(11));
            return false;
        }
        if ("MIDlet://stayAwake".equals(str)) {
            au.getActivity().runOnUiThread(new i(this));
            return false;
        }
        if ("MIDlet://allowSleep".equals(str)) {
            au.getActivity().runOnUiThread(new j(this));
            return false;
        }
        if (str.contains("Show_Toast")) {
            eC(str.split("Show_Toast_")[1]);
            return false;
        }
        if (str.equals("Cancel_Toast")) {
            Ql();
            return false;
        }
        if (str.equals("FACEBOOK")) {
            akJ();
            return false;
        }
        if (str.equals("FACEBOOK_quicklogin")) {
            akK();
            return false;
        }
        if (str.equals("GLLIVE")) {
            akL();
            return false;
        }
        if (str.equals("GLLIVE_HTML5")) {
            akN();
            return false;
        }
        if (str.equals("GLLIVE_HTML5_quicklogin")) {
            akM();
            return false;
        }
        if (str.equals("LAUNCH_AUTOUPDATE")) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(au.response.substring(au.response.indexOf("http"))));
                au.response = null;
                startActivity(intent);
                coD = 2;
                return false;
            } catch (Exception e) {
                return false;
            }
        }
        if (str.equals("isDeviceRooted")) {
            return NativeUtils.CheckRoot();
        }
        if (str.equals("isConnectedWifi")) {
            return au.Pe();
        }
        if (str.equals("isEnableVibration")) {
            return ((AudioManager) au.getContext().getApplicationContext().getSystemService("audio")).getRingerMode() == 1;
        }
        if (str.equals("isEnableRotation")) {
            return Settings.System.getInt(getContentResolver(), "accelerometer_rotation", 0) == 1;
        }
        if (str.equals("isConnectedMobile")) {
            return au.Pf();
        }
        if (str.equals("openWifiSetting")) {
            au.PX();
            return false;
        }
        if (str.contains("openSetting")) {
            au.PY();
            return false;
        }
        if (str.contains("openWirelessSetting")) {
            au.PZ();
            return false;
        }
        if (str.contains("SCREEN_ORIENTATION")) {
            int orientation = getWindowManager().getDefaultDisplay().getOrientation();
            if (str.contains("SCREEN_ORIENTATION_LANDSCAPE")) {
                if (orientation == 3) {
                    setRequestedOrientation(8);
                } else {
                    setRequestedOrientation(0);
                }
                coJ = true;
                return false;
            }
            setRequestedOrientation(6);
            coJ = false;
            return false;
        }
        if (str.contains("LOCK_ORIENTATION_90")) {
            setRequestedOrientation(0);
            coJ = true;
            return false;
        }
        if (str.contains("restartApplication")) {
            au.Qd();
            return false;
        }
        if (str.contains("isUserMusicActive")) {
            return akB();
        }
        if (str.contains("stopAllSound")) {
            akC();
            return false;
        }
        Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (com.gameloft.android.wrapper.d.bff) {
            if (ex("com.android.chrome")) {
                intent2.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.google.android.apps.chrome.Main"));
            } else if (ex("com.chrome.beta")) {
                intent2.setComponent(ComponentName.unflattenFromString("com.chrome.beta/com.google.android.apps.chrome.Main"));
            } else if (ex("org.mozilla.firefox")) {
                intent2.setComponent(ComponentName.unflattenFromString("org.mozilla.firefox/org.mozilla.firefox.App"));
            } else if (ex("org.mozilla.firefox_beta")) {
                intent2.setComponent(ComponentName.unflattenFromString("org.mozilla.firefox_beta/org.mozilla.firefox_beta.App"));
            } else if (ex("com.opera.browser")) {
                intent2.setComponent(ComponentName.unflattenFromString("com.opera.browser/com.opera.Opera"));
            }
        }
        try {
            coD = 2;
            startActivity(intent2);
            return false;
        } catch (Exception e2) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            throw new javax.microedition.a.b();
        }
    }

    public boolean ex(String str) {
        try {
            return getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void cq(boolean z) {
        try {
            if (coS == null) {
                coS = (AudioManager) au.getContext().getSystemService("audio");
            }
            if (Build.VERSION.SDK_INT >= 23) {
                coS.adjustStreamVolume(3, z ? -100 : 100, 0);
            } else {
                coS.setStreamMute(3, z);
            }
        } catch (Exception e) {
            if (au.bhL) {
            }
        }
    }

    public static void jg(String str) {
        javax.microedition.b.b bVarT = null;
        try {
            bVarT = javax.microedition.b.b.t("rmsLanguage", true);
            byte[] bytes = str.getBytes();
            if (bVarT.akX() >= 1) {
                bVarT.c(1, bytes, 0, bytes.length);
            } else {
                bVarT.n(bytes, 0, bytes.length);
            }
        } catch (Exception e) {
        }
        if (bVarT != null) {
            try {
                bVarT.akW();
            } catch (Exception e2) {
            }
        }
    }

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Intent intent;
        super.onCreate(bundle);
        if (!coH) {
            cnz = this;
            au.an(this);
            if (Build.VERSION.SDK_INT >= 24) {
                Display defaultDisplay = ((WindowManager) getSystemService("window")).getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                au.b("Screen_Width", Integer.valueOf(point.x));
                au.b("Screen_Height", Integer.valueOf(point.y));
            }
            au.PU();
            if (!com.gameloft.android.wrapper.d.bfh || au.Qc()) {
                coM = true;
                cnM = bY("GAME-IGP-CODE");
                VERSION = bY("MIDlet-Version");
                String strBY = bY("GAME_VERSION_LETTER");
                if (strBY != null) {
                    cnN = VERSION + strBY;
                } else {
                    cnN = VERSION;
                }
                String str = "GAME_VERSION_NAME_LETTER:" + cnN;
                callingPackage = getCallingPackage();
                SharedPreferences preferences = au.getActivity().getPreferences(0);
                if (cos) {
                    SharedPreferences.Editor editorEdit = preferences.edit();
                    editorEdit.putString("is_multiplejar", AppEventsConstants.EVENT_PARAM_VALUE_YES);
                    editorEdit.commit();
                }
                String string = preferences.getString("is_multiplejar", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                if (!com.gameloft.android.wrapper.d.beD || (string.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_YES) == 0 && (au.bhM == null || au.bhM == ""))) {
                    Intent intent2 = new Intent();
                    intent2.putExtra("StartFromOutMem", "Yes");
                    intent2.setClassName(getPackageName(), getPackageName() + ".Start");
                    intent2.addFlags(268468224);
                    startActivity(intent2);
                    finish();
                    onDestroy();
                    return;
                }
                if (coi) {
                    String str2 = "Config.callOnCreateTime= " + com.gameloft.android.wrapper.d.beE;
                    if (getIntent().hasExtra("FIX_CRASH_BUG_WHEN_CHANGE_STYLE_FOR_HUAHEI")) {
                        if (com.gameloft.android.wrapper.d.beE > 0) {
                            com.gameloft.android.wrapper.d.beE++;
                            finish();
                            onDestroy();
                            return;
                        }
                        com.gameloft.android.wrapper.d.beE++;
                    }
                    coj = getIntent().hasExtra("IS_DEMO");
                    cok = getIntent().hasExtra("BLOCK_RECORD");
                    if (coj) {
                        try {
                            javax.microedition.b.b.akV();
                        } catch (Exception e) {
                        }
                    }
                }
                if (!cnH) {
                    getWindow().setFlags(1536, 1536);
                } else {
                    getWindow().setFlags(1664, 1664);
                }
                requestWindowFeature(1);
                setVolumeControlStream(3);
                this.cnB = new Configuration();
                this.cnB.setToDefaults();
                this.cnB.orientation = -1;
                cnC = false;
                if (cnD) {
                    Debug.startMethodTracing(getPackageName());
                }
                if (getResources().getDrawable(getResources().getIdentifier("ic_launcher", "drawable", getPackageName())) == null) {
                    throw new RuntimeException();
                }
                ba(this);
                String stringExtra = getIntent().getStringExtra("LANGUAGE");
                if (stringExtra != null) {
                    au.an(this);
                    jg(stringExtra);
                }
                if (col && (intent = getIntent()) != null) {
                    long longExtra = intent.getLongExtra("DEMO_TIME", -1L);
                    if (longExtra != -1) {
                        l lVar = new l(this);
                        lVar.akP();
                        new Timer().schedule(lVar, longExtra);
                    }
                }
                int intExtra = getIntent().getIntExtra("SCREEN_ORIENTATION", -1);
                if (intExtra != -1) {
                    setRequestedOrientation(intExtra);
                }
                this.coB = false;
                bit = Toast.makeText(this, "", 0);
                if (cnJ) {
                    this.cnI = ((PowerManager) getSystemService("power")).newWakeLock(26, "My Tag");
                    this.cnI.acquire();
                }
                if (cor) {
                    Process.setThreadPriority(Process.myTid(), -19);
                }
                coF = (KeyguardManager) au.getContext().getSystemService("keyguard");
                coE = (PowerManager) getSystemService("power");
                System.setProperty("microedition.sensor.version", "1.0");
                System.setProperty("microedition.device", au.getDeviceName());
                System.setProperty("microedition.device.version", au.Pw());
                System.setProperty("HDIDFV", au.Os());
                System.setProperty("AndroidID", au.getAndroidId());
                System.setProperty("Serial", au.getSerial());
                System.setProperty("DebugMode", com.gameloft.android.wrapper.d.beL ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
                System.setProperty("microetition.device.carrier", au.Pn());
                String str3 = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                if (NativeUtils.CheckRoot()) {
                    str3 = AppEventsConstants.EVENT_PARAM_VALUE_YES;
                }
                System.setProperty("isDeviceRooted", str3);
                if (cnK) {
                    System.setProperty("supports.mixing", "false");
                } else {
                    System.setProperty("supports.mixing", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                }
                System.setProperty("microedition.media.version", "1.1");
                System.setProperty("microedition.device.volume", au.Pi() + "");
                if (au.Pj() != null) {
                    System.setProperty("download.code", au.Pj());
                }
                System.setProperty("glot.debug.infor", "" + au.Pc());
                System.setProperty("com.gameloft.Signature", "" + au.Pu());
                System.setProperty("microedition.width", "" + coy);
                System.setProperty("microedition.height", "" + coz);
                System.setProperty("microedition.screen.virtual", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                n.onCreate(bundle);
                z.onCreate(bundle);
                y.onCreate(bundle);
                x.onCreate(bundle);
                w.onCreate(bundle);
                v.onCreate(bundle);
                u.onCreate(bundle);
                t.onCreate(bundle);
                r.onCreate(bundle);
                q.onCreate(bundle);
                p.onCreate(bundle);
                o.onCreate(bundle);
                System.setProperty("injectedserialkey", au.PD());
                System.setProperty("injectedigp", au.PB());
                if (com.gameloft.android.wrapper.d.bfe) {
                    File file = new File(au.getContext().getExternalFilesDir(null).getAbsolutePath());
                    try {
                        au.a(file, new File(au.getContext().getFilesDir().getAbsolutePath()));
                        au.d(file);
                    } catch (Exception e2) {
                        String str4 = "copyDirectory Exception = " + e2.toString();
                    }
                }
            }
        }
    }

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    protected void onDestroy() {
        if (cnJ) {
            this.cnI.release();
        }
        if (cnR) {
            try {
                al(true);
            } catch (m e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
        coD = 0;
        coC = -1;
        setResult(cnE);
        cnC = true;
        if (callingPackage == null) {
            System.exit(0);
        }
        n.onDestroy();
        z.onDestroy();
        y.onDestroy();
        x.onDestroy();
        w.onDestroy();
        v.onDestroy();
        u.onDestroy();
        t.onDestroy();
        r.onDestroy();
        q.onDestroy();
        p.onDestroy();
        o.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        Intent intent;
        super.onPause();
        if (!coH) {
            if (this.cou != null) {
                if (!com.gameloft.android.wrapper.d.beF) {
                    javax.microedition.lcdui.f.clA.ajT().onPause();
                }
                this.cou.T(0, false);
            }
            if (f24com && (intent = getIntent()) != null && intent.getBooleanExtra("DEMO", false)) {
                akD();
            }
            n.onPause();
            z.onPause();
            y.onPause();
            x.onPause();
            w.onPause();
            v.onPause();
            u.onPause();
            t.onPause();
            r.onPause();
            q.onPause();
            p.onPause();
            o.onPause();
            com.gameloft.android.wrapper.z.m(cnz);
        }
    }

    public void akE() {
        finish();
        onDestroy();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        n.onSaveInstanceState(bundle);
        z.onSaveInstanceState(bundle);
        y.onSaveInstanceState(bundle);
        x.onSaveInstanceState(bundle);
        w.onSaveInstanceState(bundle);
        v.onSaveInstanceState(bundle);
        u.onSaveInstanceState(bundle);
        t.onSaveInstanceState(bundle);
        r.onSaveInstanceState(bundle);
        q.onSaveInstanceState(bundle);
        p.onSaveInstanceState(bundle);
        o.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        coD = 0;
        if (!coH) {
            n.onRestart();
            z.onRestart();
            y.onRestart();
            x.onRestart();
            w.onRestart();
            v.onRestart();
            u.onRestart();
            t.onRestart();
            r.onRestart();
            q.onRestart();
            p.onRestart();
            o.onRestart();
        }
    }

    public static boolean isLocked() {
        return (coF.inKeyguardRestrictedInputMode() && !coG) || !coE.isScreenOn();
    }

    public static void akF() {
        coG = true;
        cnz.getWindow().addFlags(524288);
    }

    public static void akG() {
        cnz.getWindow().clearFlags(524288);
        coG = false;
    }

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    protected void onResume() {
        akO();
        try {
            int intExtra = getIntent().getIntExtra("SCREEN_ORIENTATION", 1);
            if (com.gameloft.android.wrapper.d.bfl) {
                if (jf("isEnableRotation")) {
                    setRequestedOrientation(6);
                } else if (getWindowManager().getDefaultDisplay().getOrientation() == 3) {
                    setRequestedOrientation(8);
                } else {
                    setRequestedOrientation(0);
                }
            } else if (!coJ && intExtra == 6) {
                if (Settings.System.getInt(getContentResolver(), "accelerometer_rotation", 0) == 0) {
                    setRequestedOrientation(0);
                } else {
                    setRequestedOrientation(6);
                }
            }
        } catch (Exception e) {
        }
        super.onResume();
        if ((!com.gameloft.android.wrapper.d.bfh || au.Qc()) && !coH && !au.u(au.getContext(), au.Qa())) {
            if (this.cou != null && !com.gameloft.android.wrapper.d.beF) {
                javax.microedition.lcdui.f.clA.ajT().onResume();
            }
            if (!this.coB) {
                try {
                    mj();
                    this.coB = true;
                } catch (m e2) {
                    e2.printStackTrace();
                }
            }
            if (this.cou != null) {
                this.cou.T(0, true);
            }
            if (con) {
                akJ();
            }
            n.onResume();
            z.onResume();
            y.onResume();
            x.onResume();
            w.onResume();
            v.onResume();
            u.onResume();
            t.onResume();
            r.onResume();
            q.onResume();
            p.onResume();
            o.onResume();
            com.gameloft.android.wrapper.z.m(this);
            au.Qf();
            au.Qg();
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        if (!coH) {
            au.an(this);
            au.ao(this);
            au.bG(true);
            onWindowFocusChanged(true);
            System.setProperty("microedition.locale.country", au.Pl().toUpperCase());
            System.setProperty("microedition.locale.simcountry", au.Po().toUpperCase());
            System.setProperty("microedition.locale.usercountry", au.PM().toUpperCase());
            System.setProperty("microedition.locale", au.Pk());
            n.onStart();
            z.onStart();
            y.onStart();
            x.onStart();
            w.onStart();
            v.onStart();
            u.onStart();
            t.onStart();
            r.onStart();
            q.onStart();
            p.onStart();
            o.onStart();
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        if (!coH) {
            n.onStop();
            z.onStop();
            y.onStop();
            x.onStop();
            w.onStop();
            v.onStop();
            u.onStop();
            t.onStop();
            r.onStop();
            q.onStop();
            p.onStop();
            o.onStop();
        }
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        au.bie = true;
        au.g(intent);
        n.onNewIntent(intent);
        z.onNewIntent(intent);
        y.onNewIntent(intent);
        x.onNewIntent(intent);
        w.onNewIntent(intent);
        v.onNewIntent(intent);
        u.onNewIntent(intent);
        t.onNewIntent(intent);
        r.onNewIntent(intent);
        q.onNewIntent(intent);
        p.onNewIntent(intent);
        o.onNewIntent(intent);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        n.onActivityResult(i, i2, intent);
        z.onActivityResult(i, i2, intent);
        y.onActivityResult(i, i2, intent);
        x.onActivityResult(i, i2, intent);
        w.onActivityResult(i, i2, intent);
        v.onActivityResult(i, i2, intent);
        u.onActivityResult(i, i2, intent);
        t.onActivityResult(i, i2, intent);
        r.onActivityResult(i, i2, intent);
        q.onActivityResult(i, i2, intent);
        p.onActivityResult(i, i2, intent);
        o.onActivityResult(i, i2, intent);
        if (i == 60) {
            if (i2 == -1) {
                au.biO = false;
                return;
            }
            if (i2 == 1) {
                try {
                    Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent2.setData(Uri.parse("package:" + getPackageName()));
                    intent2.setFlags(268435456);
                    startActivity(intent2);
                } catch (Exception e) {
                    String str = "error when open setting app screen:" + e.toString();
                }
                au.biO = false;
                return;
            }
            if (i2 == 0) {
                try {
                    Intent intent3 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent3.setData(Uri.parse("package:" + getPackageName()));
                    intent3.setFlags(268435456);
                    startActivity(intent3);
                } catch (Exception e2) {
                    String str2 = "error when open setting app screen:" + e2.toString();
                }
                finish();
                akD();
                return;
            }
            if (i2 == -2) {
                finish();
                akD();
            }
        }
    }

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!com.gameloft.android.wrapper.d.bfh || au.Qc()) {
            boolean zBE = com.gameloft.android.wrapper.y.bE(z);
            String str = "MIDlet.onWindowFocusChanged: " + zBE;
            if (isLocked()) {
                String str2 = "lock guard: false";
                zBE = false;
            }
            if (this.cou != null) {
                this.cou.cl(zBE);
            }
            if (zBE) {
                if (cnD) {
                    Debug.startMethodTracing(getPackageName());
                }
                if (this.cou != null) {
                    this.cou.T(1, zBE);
                }
            }
            if (!zBE) {
                if (cnD) {
                    Debug.stopMethodTracing();
                }
                if (!cnC) {
                    if (cnG) {
                        cq(!zBE);
                    }
                    if (this.cou != null) {
                        this.cou.T(1, zBE);
                    } else {
                        finish();
                    }
                }
            }
            cot = false;
            n.onWindowFocusChanged(zBE);
            z.onWindowFocusChanged(zBE);
            y.onWindowFocusChanged(zBE);
            x.onWindowFocusChanged(zBE);
            w.onWindowFocusChanged(zBE);
            v.onWindowFocusChanged(zBE);
            u.onWindowFocusChanged(zBE);
            t.onWindowFocusChanged(zBE);
            r.onWindowFocusChanged(zBE);
            q.onWindowFocusChanged(zBE);
            p.onWindowFocusChanged(zBE);
            o.onWindowFocusChanged(zBE);
            if (zBE) {
                com.gameloft.android.wrapper.z.k(this);
                try {
                    int intExtra = getIntent().getIntExtra("SCREEN_ORIENTATION", 1);
                    if (com.gameloft.android.wrapper.d.bfl) {
                        if (jf("isEnableRotation")) {
                            setRequestedOrientation(6);
                        } else if (getWindowManager().getDefaultDisplay().getOrientation() == 3) {
                            setRequestedOrientation(8);
                        } else {
                            setRequestedOrientation(0);
                        }
                    } else if (!coJ && intExtra == 6) {
                        if (Settings.System.getInt(getContentResolver(), "accelerometer_rotation", 0) == 0) {
                            setRequestedOrientation(0);
                        } else {
                            setRequestedOrientation(6);
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                com.gameloft.android.wrapper.z.m(this);
            }
            if (zBE) {
                if (bx.ND() != null) {
                    bx.ND().NG();
                }
            } else if (bx.ND() != null) {
                bx.ND().NH();
            }
        }
    }

    public void akH() {
        try {
            mj();
            this.coB = true;
        } catch (m e) {
            e.printStackTrace();
        }
        au.OO();
        if (coM) {
            coN = au.b("isFirstActivation", false, "FirstActivation");
            String[] strArr = {""};
            if (!coR) {
                strArr = new String[]{"android.permission.READ_PHONE_STATE"};
            }
            if (coP) {
                strArr = new String[]{"android.permission.SEND_SMS"};
                if (coR) {
                    strArr = new String[]{"android.permission.SEND_SMS", "android.permission.READ_PHONE_STATE"};
                }
            }
            if (!au.biO) {
                au.k(strArr);
            }
            coM = false;
            String str = "m_firstLaunch:" + coM;
            ar.a(this);
            System.setProperty("IMEI", au.getDeviceId());
        }
        if (cnL) {
            ar.OM();
        }
        au.Qh();
    }

    public void akI() {
        au.bG(false);
        try {
            mk();
        } catch (m e) {
            e.printStackTrace();
        }
        try {
            com.gameloft.android.wrapper.z.m(this);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        com.gameloft.android.wrapper.z.a(this, i);
        if (this.cou == null || this.cou.ajT() == null) {
            return false;
        }
        return this.cou.ajT().onKeyEvent(keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        if (this.cou == null || this.cou.ajT() == null) {
            return false;
        }
        return this.cou.ajT().onKeyEvent(keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.cou != null && this.cou.ajT() != null) {
            if (i == 4) {
                if (AdsManager.IsFullScreenAdOnScreen()) {
                    AdsManager.HandleBackKey();
                    return true;
                }
                String str = "IsFullScreenAdOnScreen = " + AdsManager.IsFullScreenAdOnScreen();
            }
            return this.cou.ajT().onKeyEvent(keyEvent);
        }
        return false;
    }

    @Override // android.app.Activity
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (this.cou == null || !(this.cou instanceof javax.microedition.lcdui.f)) {
            return false;
        }
        return ((javax.microedition.lcdui.f) this.cou).k(motionEvent.getX(), motionEvent.getY());
    }

    @Override // android.app.Activity
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (this.cou == null || this.cou.ajT() == null) {
        }
        return false;
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        int i;
        int i2;
        super.onConfigurationChanged(configuration);
        try {
            Display defaultDisplay = ((WindowManager) au.getContext().getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            int i3 = point.x;
            int i4 = point.y;
            String str = "onSizeChange: " + i3 + ", " + i4 + " orientation:" + com.gameloft.android.wrapper.d.beG;
            if (Build.VERSION.SDK_INT >= 24) {
                int iC = au.C("Screen_Width", ae.bga);
                int iC2 = au.C("Screen_Height", ae.bgb);
                au.b("Screen_Width", Integer.valueOf(i3));
                au.b("Screen_Height", Integer.valueOf(i4));
                String str2 = "onSizeChange update_screen: " + iC + ", " + iC2 + " orientation:" + com.gameloft.android.wrapper.d.beG;
                if ((i3 != iC && i3 != iC2) || (i4 != iC2 && i4 != iC)) {
                    au.Qd();
                    return;
                }
            }
            if (com.gameloft.android2d.e.a.isRunning()) {
                com.gameloft.android2d.e.a.Ut();
            }
            this.cnB.updateFrom(configuration);
            if ((com.gameloft.android.wrapper.d.beG.compareTo("sensorLandscape") != 0 || i3 >= i4) && (com.gameloft.android.wrapper.d.beG.compareTo("portrait") != 0 || i3 <= i4)) {
                i = i4;
                i2 = i3;
            } else {
                String str3 = "onSizeChange after change: " + i4 + ", " + i3 + " orientation:" + com.gameloft.android.wrapper.d.beG;
                i = i3;
                i2 = i4;
            }
            this.cou.cI(i2, i);
        } catch (Exception e) {
        }
        n.onConfigurationChanged(configuration);
        z.onConfigurationChanged(configuration);
        y.onConfigurationChanged(configuration);
        x.onConfigurationChanged(configuration);
        w.onConfigurationChanged(configuration);
        v.onConfigurationChanged(configuration);
        u.onConfigurationChanged(configuration);
        t.onConfigurationChanged(configuration);
        r.onConfigurationChanged(configuration);
        q.onConfigurationChanged(configuration);
        p.onConfigurationChanged(configuration);
        o.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        javax.microedition.lcdui.t tVarAkf = javax.microedition.lcdui.r.b(this).akf();
        if (tVarAkf != null) {
            int i = 0;
            for (javax.microedition.lcdui.o oVar : tVarAkf.clw) {
                oVar.cmk = menu.add(0, i, 0, oVar.getLabel()).getItemId();
                i++;
            }
            return true;
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        javax.microedition.lcdui.t tVarAkf = javax.microedition.lcdui.r.b(this).akf();
        if (tVarAkf != null && tVarAkf.cmr != null) {
            for (javax.microedition.lcdui.o oVar : tVarAkf.clw) {
                if (oVar.cmk == menuItem.getItemId()) {
                    tVarAkf.cmr.a(oVar, tVarAkf);
                    return true;
                }
            }
        }
        return false;
    }

    private static void e(Object obj, String str) {
        obj.getClass().getClassLoader();
        if (cnQ.size() <= 0) {
            Enumeration enumeration = null;
            try {
                if (0 == 0) {
                    try {
                        enumeration = Collections.enumeration(au.PV());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                while (enumeration.hasMoreElements()) {
                    jh(((String) enumeration.nextElement()).trim());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Enumeration enumerationKeys = cnQ.keys();
            Iterator it = cnQ.values().iterator();
            while (enumerationKeys.hasMoreElements()) {
                String str2 = ((String) enumerationKeys.nextElement()) + "^:^" + ((String) it.next());
            }
        }
    }

    private static void jh(String str) {
        int iIndexOf = str.indexOf(":");
        if (iIndexOf > 0) {
            cnQ.put(str.substring(0, iIndexOf).trim(), str.substring(iIndexOf + 1).trim());
        }
    }

    public void eC(String str) {
        runOnUiThread(new k(this, str));
    }

    public void Ql() {
        if (bit != null) {
            bit.cancel();
        }
    }

    public void akJ() {
        try {
            Intent intent = new Intent();
            intent.setClassName("_GAME_PKG_", "_GAME_PKG_.Facebook_Wrapper");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void akK() {
        try {
            Intent intent = new Intent();
            intent.setClassName("_GAME_PKG_", "_GAME_PKG_.Facebook_Wrapper");
            intent.putExtra("quicklogin", true);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void akL() {
        try {
            Intent intent = new Intent();
            intent.setClassName("_GAME_PKG_", "_GAME_PKG_.Gllive_Login");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void akM() {
        try {
            Intent intent = new Intent();
            intent.setClassName("_GAME_PKG_", "_GAME_PKG_.GLiveHTML.GLLiveActivity");
            intent.putExtra("quicklogin", true);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void akN() {
        try {
            Intent intent = new Intent();
            intent.setClassName("_GAME_PKG_", "_GAME_PKG_.GLiveHTML.GLLiveActivity");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ba(Context context) {
        try {
            String[] strArrFileList = context.fileList();
            if (strArrFileList != null) {
                for (int i = 0; i < strArrFileList.length; i++) {
                    String str = "file found: " + strArrFileList[i];
                    String str2 = strArrFileList[i];
                    if (str2 != null && str2.endsWith(".snddmp") && au.getContext().getFileStreamPath(str2).delete()) {
                        String str3 = "Cleaning temp file " + str2;
                    }
                }
            }
        } catch (Exception e) {
            String str4 = "Error while cleaning temp files: " + e;
            e.printStackTrace();
        }
    }

    public void akO() {
        coQ = getWindowManager().getDefaultDisplay().getOrientation();
        String str = "SetOrientation() m_Orientation:" + coQ;
    }
}
