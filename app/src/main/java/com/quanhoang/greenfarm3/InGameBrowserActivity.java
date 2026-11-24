package com.quanhoang.greenfarm3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Display;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.adsmanager.JavaUtils;
import com.gameloft.glads.AndroidWebView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.microedition.midlet.MIDlet;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class InGameBrowserActivity extends Activity implements View.OnTouchListener {
    private ImageButton Du;
    private ImageButton Dv;
    private ImageButton Dw;
    private boolean Eb;
    private static InGameBrowserActivity Dt = null;
    private static String Dy = "";
    private static String Dz = "from=FROM&op=OPERATOR&country=COUNTRY&lg=LANG&udid=UDID&androidid=ANDROID_ID&hdidfv=HDIDFV&game_ver=VERSION&d=DEVICE&f=FIRMWARE&anonymous=ANONYMOUS_ACCOUNT&gliveusername=GLIVE_USERNAME&googleplaygames=GOOGLEID&clientid=CLIENT_ID&os=android&user_age=CURRENT_USER_AGE";
    private static String DA = "from=FROM&op=OPERATOR&country=COUNTRY&lg=LANG&udid=UDID&androidid=ANDROID_ID&hdidfv=HDIDFV&game_ver=VERSION&d=DEVICE&f=FIRMWARE&anonymous=ANONYMOUS_ACCOUNT&gliveusername=GLIVE_USERNAME&googleplaygames=GOOGLEID&clientid=CLIENT_ID&os=android&game=GAME_NAME&user_age=CURRENT_USER_AGE";
    private static String DB = "";
    private static String DC = "from=FROM&op=OPERATOR&ctg=FORUM&game_ver=VERSION&lg=LANG&country=COUNTRY&d=DEVICE&f=FIRMWARE&udid=UDID&hdidfv=HDIDFV&androidid=ANDROID_ID&clientid=CLIENT_ID&user_age=CURRENT_USER_AGE";
    private static String DD = "";
    private static String DE = "";
    private static String DF = "";
    public static int DG = 0;
    public static String DH = "";
    public static String DI = "";
    public static String DJ = "";
    public static String DK = "";
    public static String DL = "";
    public static String DM = MIDlet.d(com.gameloft.android.wrapper.au.getActivity(), "GAME-IGP-CODE");
    public static String DN = MIDlet.d(com.gameloft.android.wrapper.au.getActivity(), "MIDlet-Version");
    public static int DO = 0;
    private static boolean DP = true;
    private static String DQ = "";
    private static AtomicBoolean DR = new AtomicBoolean(false);
    private static az DS = az.GENDER_UNKNOWN;
    public static String DT = "";
    private static boolean DU = false;
    public static boolean CK = false;
    private static int DV = -1;
    private static int DW = -1;
    private static ay DX = null;
    private static String[] DY = {"EN", "FR", "DE", "IT", "SP", "JP", "KR", "CN", "BR", "RU", "TR", "AR", "TH", "ID", "VI", "ZT", "PL"};
    public static int[] Dc = {R.string.IGB_CANNOT_GO_BACK_EN, R.string.IGB_CANNOT_GO_BACK_FR, R.string.IGB_CANNOT_GO_BACK_DE, R.string.IGB_CANNOT_GO_BACK_IT, R.string.IGB_CANNOT_GO_BACK_SP, R.string.IGB_CANNOT_GO_BACK_JP, R.string.IGB_CANNOT_GO_BACK_KR, R.string.IGB_CANNOT_GO_BACK_CN, R.string.IGB_CANNOT_GO_BACK_BR, R.string.IGB_CANNOT_GO_BACK_RU, R.string.IGB_CANNOT_GO_BACK_TR, R.string.IGB_CANNOT_GO_BACK_AR, R.string.IGB_CANNOT_GO_BACK_TH, R.string.IGB_CANNOT_GO_BACK_ID, R.string.IGB_CANNOT_GO_BACK_VI, R.string.IGB_CANNOT_GO_BACK_ZT, R.string.IGB_CANNOT_GO_BACK_PL};
    public static String[] DZ = {"en", "fr", "de", "it", "es", "ja", "ko", "cn", "br", "ru", "tr", "ar", "th", ShareConstants.WEB_DIALOG_PARAM_ID, "vi", "zt", "pl"};
    public static boolean CG = false;
    private static Thread Em = null;
    private static Thread En = null;
    private static aw Ep = null;
    private static boolean Eq = false;
    private static boolean Er = false;
    private static String Es = null;
    private static String Et = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
    private static Message Eu = null;
    private static String Ev = null;
    private WebView Dg = null;
    private RelativeLayout Df = null;
    private ImageButton Dx = null;
    private int[] CZ = {R.string.IGB_LOADING_EN, R.string.IGB_LOADING_FR, R.string.IGB_LOADING_DE, R.string.IGB_LOADING_IT, R.string.IGB_LOADING_SP, R.string.IGB_LOADING_JP, R.string.IGB_LOADING_KR, R.string.IGB_LOADING_CN, R.string.IGB_LOADING_BR, R.string.IGB_LOADING_RU, R.string.IGB_LOADING_TR, R.string.IGB_LOADING_AR, R.string.IGB_LOADING_TH, R.string.IGB_LOADING_ID, R.string.IGB_LOADING_VI, R.string.IGB_LOADING_ZT, R.string.IGB_LOADING_PL};
    private int[] Da = {R.string.IGB_NET_ERROR_EN, R.string.IGB_NET_ERROR_FR, R.string.IGB_NET_ERROR_DE, R.string.IGB_NET_ERROR_IT, R.string.IGB_NET_ERROR_SP, R.string.IGB_NET_ERROR_JP, R.string.IGB_NET_ERROR_KR, R.string.IGB_NET_ERROR_CN, R.string.IGB_NET_ERROR_BR, R.string.IGB_NET_ERROR_RU, R.string.IGB_NET_ERROR_TR, R.string.IGB_NET_ERROR_AR, R.string.IGB_NET_ERROR_TH, R.string.IGB_NET_ERROR_ID, R.string.IGB_NET_ERROR_VI, R.string.IGB_NET_ERROR_ZT, R.string.IGB_NET_ERROR_PL};
    private int[] Db = {R.string.IGB_OK_EN, R.string.IGB_OK_FR, R.string.IGB_OK_DE, R.string.IGB_OK_IT, R.string.IGB_OK_SP, R.string.IGB_OK_JP, R.string.IGB_OK_KR, R.string.IGB_OK_CN, R.string.IGB_OK_BR, R.string.IGB_OK_RU, R.string.IGB_OK_TR, R.string.IGB_OK_AR, R.string.IGB_OK_TH, R.string.IGB_OK_ID, R.string.IGB_OK_VI, R.string.IGB_OK_ZT, R.string.IGB_OK_PL};
    private String Ea = "NONE";
    private AbsoluteLayout Ec = null;
    private ImageView Ed = null;
    private InputDevice Ee = null;
    private float Ef = 0.0f;
    private float Eg = 0.0f;
    private float Eh = 0.0f;
    private float Ei = 0.0f;
    private float Ej = 0.0f;
    private float Ek = 0.0f;
    private int CR = 0;
    private int CS = 0;
    private boolean El = false;
    private ProgressDialog Eo = null;

    public static native void aW(int i);

    public static void L(String str) {
        if (str.equalsIgnoreCase("ES")) {
            str = "SP";
        }
        int iIndexOf = Arrays.asList(DY).indexOf(str.toUpperCase());
        if (iIndexOf != -1) {
            DG = iIndexOf;
        } else {
            DG = 0;
        }
    }

    public static void M(String str) {
        if (str != null && !str.isEmpty()) {
            if (!str.endsWith("/")) {
                str = str + "/";
            }
            Dy = str + "redir/ingamebrowser.php";
            DB = str + "redir/?";
            DD = str + "redir/ingamenews.php?action=checkNews&last-id=LAST_ID";
            DE = str + "redir/ingamenews.php?action=saveNews&last-id=LAST_ID";
            DF = str + "redir/ingamenews.php?action=displayNews";
            DU = true;
        }
    }

    public static void N(String str) {
        L(str);
    }

    public static void SetAdServerCustomID(String str) {
        DL = str;
    }

    public static void QuitIGB() {
        try {
            if (Dt != null) {
                InGameBrowserActivity inGameBrowserActivity = Dt;
                if (CG) {
                    if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                        Dt.exit();
                    } else {
                        Dt.Df.post(new ah());
                        while (Eq) {
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String O(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        DI = bb.gV();
        DH = bb.gW();
        DT = bb.gX();
        DO = bb.gY();
        String str2 = "userAge = " + DO;
        if (DG < 0 || DG >= DY.length) {
            DG = 0;
        }
        String strD = MIDlet.d(com.gameloft.android.wrapper.au.getActivity(), "URL-SUPPORT");
        if (strD != null) {
            if (strD == "GAND") {
                strD = "FVGL";
            }
            if (!strD.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                int iIndexOf = strD.indexOf("op");
                strD = strD.substring(iIndexOf + 3, iIndexOf + 7);
            }
        } else {
            strD = "";
        }
        String strReplaceAll = str.replaceAll("FROM", DM).replaceAll("OPERATOR", strD).replaceAll("COUNTRY", Locale.getDefault().getCountry()).replaceAll("LANG", DY[DG]).replaceAll("UDID", com.gameloft.android.wrapper.au.getSerial()).replaceAll("ANDROID_ID", com.gameloft.android.wrapper.au.getAndroidId()).replaceAll("HDIDFV", com.gameloft.android.wrapper.au.Os()).replaceAll("VERSION", DN).replaceAll("DEVICE", Build.MANUFACTURER + "_" + Build.MODEL).replaceAll("FIRMWARE", Build.VERSION.RELEASE).replaceAll("ANONYMOUS_ACCOUNT", DH).replaceAll("GLIVE_USERNAME", DJ).replaceAll("GOOGLEID", DK).replaceAll("GAME_NAME", DM).replaceAll("CLIENT_ID", DT).replaceAll("CURRENT_USER_AGE", "" + DO).replaceAll(" ", "");
        if (DI != null && !DI.equals("")) {
            strReplaceAll = strReplaceAll + "&fbid=" + DI;
        }
        if (DL != null && !DL.equals("")) {
            strReplaceAll = strReplaceAll + "&ad_server_id=" + DL;
        }
        switch (DS) {
            case GENDER_MALE:
                strReplaceAll = strReplaceAll + "&gender=male";
                break;
            case GENDER_FEMALE:
                strReplaceAll = strReplaceAll + "&gender=female";
                break;
            case GENDER_UNKNOWN:
                strReplaceAll = strReplaceAll + "&gender=";
                break;
        }
        return strReplaceAll + "&google_optout=" + com.gameloft.android.wrapper.au.Qe();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MIDlet.coD = 1;
        Dt = this;
        if (!DU) {
            Et = "NotItitialized";
            exit();
            return;
        }
        CG = true;
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27) {
            setRequestedOrientation(-1);
        } else {
            if ((defaultDisplay.getWidth() < defaultDisplay.getHeight() ? (char) 1 : (char) 2) == 1) {
                if (Build.VERSION.SDK_INT >= 18) {
                    setRequestedOrientation(12);
                } else {
                    setRequestedOrientation(7);
                }
            } else if (Build.VERSION.SDK_INT >= 18) {
                setRequestedOrientation(11);
            } else {
                setRequestedOrientation(6);
            }
        }
        setContentView(R.layout.activity_in_game_browser);
        this.CR = defaultDisplay.getWidth();
        this.CS = defaultDisplay.getHeight();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.ingamebrowser_webview);
        this.Dg = new ba(this, this);
        this.Dg.getSettings().setJavaScriptEnabled(true);
        this.Dg.getSettings().setLoadWithOverviewMode(true);
        this.Dg.getSettings().setUseWideViewPort(false);
        this.Dg.getSettings().setBuiltInZoomControls(false);
        this.Dg.getSettings().setSupportZoom(true);
        this.Dg.getSettings().setAllowFileAccess(false);
        this.Dg.setWebViewClient(new as(this));
        this.Dg.setWebChromeClient(new WebChromeClient());
        if (Build.VERSION.SDK_INT >= 21) {
            this.Dg.getSettings().setMixedContentMode(0);
        }
        relativeLayout.addView(this.Dg, -1, -1);
        this.Du = (ImageButton) findViewById(R.id.ingamebrowser_backbutton);
        this.Dv = (ImageButton) findViewById(R.id.ingamebrowser_forwardbutton);
        this.Dw = (ImageButton) findViewById(R.id.ingamebrowser_refreshbutton);
        this.Dx = (ImageButton) findViewById(R.id.ingamebrowser_closebutton);
        this.Du.setOnTouchListener(this);
        this.Dv.setOnTouchListener(this);
        this.Dw.setOnTouchListener(this);
        this.Dx.setOnTouchListener(this);
        String stringExtra = getIntent().getStringExtra("extra_url");
        String strO = O(Dz);
        if (stringExtra.contains("extra_6")) {
            strO = strO + com.gameloft.android2d.iap.b.eG(" ").trim();
        }
        switch (DS) {
            case GENDER_MALE:
                stringExtra = stringExtra + "&gender=male";
                break;
            case GENDER_FEMALE:
                stringExtra = stringExtra + "&gender=female";
                break;
        }
        DK = com.gameloft.android2d.socialnetwork.aj.Vj();
        if (DK == null) {
            DK = "";
        }
        if (Eu != null) {
            try {
                ((WebView.WebViewTransport) Eu.obj).setWebView(this.Dg);
                Eu.sendToTarget();
            } catch (Exception e) {
                Et = "NavigationFailed";
                exit();
            }
        } else if (stringExtra != null) {
            if (stringExtra.equals("forum")) {
                this.Ea = "FORUM";
                this.Dg.loadUrl(DB + "data=" + com.quanhoang.greenfarm3.a.b.crypt(O(DC)) + "&enc=1");
            } else if (stringExtra.equals("news")) {
                this.Ea = "NEWS";
                try {
                    this.Dg.postUrl(DF, strO.getBytes(AndroidWebView.UTF_8));
                } catch (Exception e2) {
                }
            } else if (stringExtra.startsWith("survey:")) {
                String strReplace = stringExtra.replace("survey:", "");
                String str = "Opening survey: " + strReplace;
                CookieManager.getInstance().setAcceptCookie(false);
                this.Dg.loadUrl(strReplace);
            } else if (DP) {
                try {
                    this.Dg.postUrl(stringExtra, strO.getBytes(AndroidWebView.UTF_8));
                } catch (Exception e3) {
                }
                if (stringExtra.contains("ctg=SUPPORT")) {
                    new aj(this).start();
                }
            } else {
                this.Dg.loadUrl(stringExtra);
            }
        } else {
            int intExtra = getIntent().getIntExtra("ban_type", -1);
            if (intExtra >= 0) {
                strO = strO + "&extra_14=" + intExtra;
                this.Ea = "BANNED";
            } else {
                this.Ea = "SUPPORT";
            }
            try {
                this.Dg.postUrl(Dy + "?ctg=" + this.Ea, strO.getBytes(AndroidWebView.UTF_8));
            } catch (Exception e4) {
            }
        }
        this.Df = (RelativeLayout) findViewById(R.id.ingamebrowser_mview);
        View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ak(this, decorView));
        this.Ec = new AbsoluteLayout(this);
        this.Ec.setBackgroundColor(0);
        this.Df.addView(this.Ec, -1, -1);
        this.Ed = new ImageView(this);
        this.Ed.setImageResource(R.drawable.content_cursor);
        this.Ec.addView(this.Ed, new AbsoluteLayout.LayoutParams(-2, -2, this.CR / 2, this.CS / 2));
        this.Ec.setVisibility(8);
        this.Ee = aX(16777232);
        if (this.Ee != null) {
            this.Ec.setVisibility(0);
        }
        new al(this).start();
        new an(this).start();
    }

    @Override // android.app.Activity
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (!this.El) {
            this.Eh = motionEvent.getAxisValue(0);
            this.Ei = motionEvent.getAxisValue(1);
            this.Ej = motionEvent.getAxisValue(11);
            this.Ek = motionEvent.getAxisValue(14);
            this.Ef = motionEvent.getAxisValue(15);
            this.Eg = motionEvent.getAxisValue(16);
            float axisValue = motionEvent.getAxisValue(23);
            float axisValue2 = motionEvent.getAxisValue(22);
            if (axisValue >= 0.7d || axisValue2 >= 0.7d) {
                gK();
            }
            if (this.Ef != 0.0f || this.Eg != 0.0f) {
                this.Eh = 0.0f;
                this.Ei = 0.0f;
                this.Ej = 0.0f;
                this.Ek = 0.0f;
            }
            if (this.Eg == 0.0f || this.Dg == null) {
                return true;
            }
            if (this.Eg == 1.0d) {
                this.Dg.pageDown(false);
                return true;
            }
            this.Dg.pageUp(false);
            return true;
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!this.El) {
            switch (i) {
                case 4:
                case 97:
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
                    if (this.Dg != null && inputMethodManager.hideSoftInputFromWindow(this.Dg.getWindowToken(), 0)) {
                        return true;
                    }
                    onBackPressed();
                    return true;
                case 96:
                case 99:
                case JavaUtils.Constants.NATIVE_HEIGHT_DP_100 /* 100 */:
                case FacebookRequestErrorClassification.EC_INVALID_SESSION /* 102 */:
                case 103:
                case 108:
                case 109:
                    if (this.Ee == null || this.Ee != keyEvent.getDevice()) {
                        return super.onKeyUp(i, keyEvent);
                    }
                    return true;
                default:
                    return super.onKeyUp(i, keyEvent);
            }
        }
        switch (i) {
            case 97:
                InputMethodManager inputMethodManager2 = (InputMethodManager) getSystemService("input_method");
                if (this.Dg != null && inputMethodManager2.hideSoftInputFromWindow(this.Dg.getWindowToken(), 0)) {
                    return true;
                }
                onBackPressed();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!this.El) {
            switch (i) {
                case 19:
                    float y = this.Ed.getY() - 10.0f;
                    if (y >= 0.0f) {
                        this.Ed.setY(y);
                        return true;
                    }
                    this.Dg.dispatchKeyEvent(new KeyEvent(0, 92));
                    this.Dg.dispatchKeyEvent(new KeyEvent(1, 92));
                    return true;
                case 20:
                    float y2 = this.Ed.getY() + 10.0f;
                    if (y2 <= this.CS - this.Ed.getHeight()) {
                        this.Ed.setY(y2);
                        return true;
                    }
                    this.Dg.dispatchKeyEvent(new KeyEvent(0, 93));
                    this.Dg.dispatchKeyEvent(new KeyEvent(1, 93));
                    return true;
                case 21:
                    float x = this.Ed.getX() - 10.0f;
                    if (x < 0.0f) {
                        return true;
                    }
                    this.Ed.setX(x);
                    return true;
                case 22:
                    float x2 = this.Ed.getX() + 10.0f;
                    if (x2 > this.CR - this.Ed.getWidth()) {
                        return true;
                    }
                    this.Ed.setX(x2);
                    return true;
                case 96:
                case FacebookRequestErrorClassification.EC_INVALID_SESSION /* 102 */:
                case 103:
                    gK();
                    return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InputDevice aX(int i) {
        for (int i2 : InputDevice.getDeviceIds()) {
            InputDevice device = InputDevice.getDevice(i2);
            if ((device.getSources() & (-256) & i) != 0 && !device.getName().contains("ats_input")) {
                return device;
            }
        }
        return null;
    }

    private void gK() {
        try {
            long jUptimeMillis = SystemClock.uptimeMillis();
            long jUptimeMillis2 = SystemClock.uptimeMillis() + 100;
            float width = (this.Ed.getWidth() / 2) + this.Ed.getX();
            float height = (this.Ed.getHeight() / 2) + this.Ed.getY();
            this.Df.dispatchTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis2, 0, width, height, 0));
            this.Df.dispatchTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis2, 1, width, height, 0));
        } catch (Exception e) {
        }
    }

    public static void P(String str) {
        c(str, true);
    }

    public static void c(String str, boolean z) {
        String strSubstring = "";
        if (str.indexOf("&lg=") >= 0) {
            strSubstring = str.substring(str.indexOf("&lg=") + 4);
        } else if (str.indexOf("&lang=") >= 0) {
            strSubstring = str.substring(str.indexOf("&lang=") + 6);
        } else if (str.indexOf("?lang=") >= 0) {
            strSubstring = str.substring(str.indexOf("?lang=") + 6);
        }
        if (!strSubstring.equals("")) {
            String lowerCase = strSubstring.substring(0, 2).toLowerCase();
            if (lowerCase.equalsIgnoreCase("es")) {
                lowerCase = "sp";
            }
            if (lowerCase.equalsIgnoreCase("zh")) {
                lowerCase = "zt";
            }
            if (lowerCase.equalsIgnoreCase("ja")) {
                lowerCase = "jp";
            }
            if (lowerCase.equalsIgnoreCase("ko")) {
                lowerCase = "kr";
            }
            if (lowerCase.equalsIgnoreCase("le")) {
                lowerCase = "sp";
            }
            for (int i = 0; i < DY.length; i++) {
                if (lowerCase.equalsIgnoreCase(DY[i])) {
                    DG = i;
                    break;
                }
            }
        }
        try {
            Intent intent = new Intent(com.gameloft.android.wrapper.au.getContext(), (Class<?>) InGameBrowserActivity.class);
            intent.putExtra("extra_url", str);
            intent.setSelector(null);
            DP = z;
            com.gameloft.android.wrapper.au.getContext().startActivity(intent);
        } catch (Exception e) {
            DP = true;
            DR.set(false);
        }
    }

    private static void gL() {
        if (En == null) {
            En = new ap();
            En.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(JSONObject jSONObject, boolean z) {
        if (jSONObject.optBoolean("success", false)) {
            if (z) {
                Q(jSONObject.optString("current-id", "-1"));
            }
            DV = jSONObject.optInt("unread", -1);
            try {
                aW(DV);
            } catch (UnsatisfiedLinkError e) {
            }
            if (DX != null) {
                DX.aZ(DV);
            }
        }
    }

    private static void Q(String str) {
        try {
            SharedPreferences.Editor editorEdit = com.gameloft.android.wrapper.au.getContext().getSharedPreferences("InGameBrowser_lastNewsIndex", 0).edit();
            editorEdit.putString("LAST_NEWS_INDEX", str);
            editorEdit.commit();
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String gM() {
        try {
            return com.gameloft.android.wrapper.au.getContext().getSharedPreferences("InGameBrowser_lastNewsIndex", 0).getString("LAST_NEWS_INDEX", "-1");
        } catch (Exception e) {
            return "-1";
        }
    }

    private static String a(HashMap<String, String> map) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        boolean z2 = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (z2) {
                z = false;
            } else {
                sb.append("&");
                z = z2;
            }
            sb.append(URLEncoder.encode(entry.getKey(), AndroidWebView.UTF_8));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue(), AndroidWebView.UTF_8));
            z2 = z;
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String g(String str, String str2) {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
            httpsURLConnection.setReadTimeout(15000);
            httpsURLConnection.setConnectTimeout(15000);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            HashMap map = new HashMap();
            for (String str3 : str2.split("[&]")) {
                int iIndexOf = str3.indexOf("=");
                if (iIndexOf != -1 && iIndexOf != str3.length() - 1) {
                    map.put(str3.substring(0, iIndexOf), str3.substring(iIndexOf + 1));
                } else if (iIndexOf == str3.length() - 1) {
                    map.put(str3.substring(0, iIndexOf), "");
                }
            }
            OutputStream outputStream = httpsURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, AndroidWebView.UTF_8));
            bufferedWriter.write(a((HashMap<String, String>) map));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            if (httpsURLConnection.getResponseCode() == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String str4 = null;
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        str4 = str4 + line;
                    } else {
                        return str4;
                    }
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.Dg.canGoBack()) {
            this.Dg.goBack();
        } else {
            Et = "UserCancel";
            exit();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        if (this.Ea.equals("NEWS")) {
            gL();
        }
        if (this.Dg != null) {
            try {
                ViewGroup viewGroup = (ViewGroup) this.Dg.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(this.Dg);
                }
                if (Build.VERSION.SDK_INT >= 11) {
                    this.Dg.onPause();
                }
                this.Dg.loadUrl("about:blank");
                this.Dg.destroyDrawingCache();
                this.Dg.destroy();
                this.Dg = null;
            } catch (Exception e) {
            }
        }
        MIDlet.coD = 2;
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onPause() {
        if (this.Dg != null && Build.VERSION.SDK_INT >= 11) {
            this.Dg.onPause();
        }
        super.onPause();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.Dg != null && Build.VERSION.SDK_INT >= 11) {
            this.Dg.onResume();
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()) {
            case 0:
                ((ImageButton) view).setBackgroundColor(Color.parseColor("#a3a3a3"));
                return true;
            case 1:
                if (x >= 0.0f && x <= view.getWidth() && y >= 0.0f && y <= view.getHeight()) {
                    aY(view.getId());
                }
                ((ImageButton) view).setBackgroundColor(0);
                return true;
            case 2:
                if (x < 0.0f || x > view.getWidth() || y < 0.0f || y > view.getHeight()) {
                    ((ImageButton) view).setBackgroundColor(0);
                    return true;
                }
                ((ImageButton) view).setBackgroundColor(Color.parseColor("#a3a3a3"));
                return true;
            default:
                return false;
        }
    }

    private void aY(int i) {
        switch (i) {
            case R.id.ingamebrowser_closebutton /* 2131492901 */:
                Et = "UserCancel";
                exit();
                break;
            case R.id.ingamebrowser_backbutton /* 2131492903 */:
                this.Dg.goBack();
                break;
            case R.id.ingamebrowser_forwardbutton /* 2131492904 */:
                this.Dg.goForward();
                break;
            case R.id.ingamebrowser_refreshbutton /* 2131492905 */:
                this.Dg.reload();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exit() {
        Dt = null;
        CG = false;
        finish();
        S(DQ);
        DQ = "";
        DP = true;
        gN();
        DR.set(false);
    }

    private void G(String str) {
        runOnUiThread(new aq(this, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gH() {
        G(getString(this.CZ[DG]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gI() {
        runOnUiThread(new ar(this));
    }

    private static boolean R(String str) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            intent.setFlags(268435456);
            intent.setSelector(null);
            if (com.gameloft.android.wrapper.au.getContext().getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() == 0) {
                return false;
            }
            com.gameloft.android.wrapper.au.getContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void H(String str) {
        R(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void I(String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.setFlags(268435456);
        intent.setSelector(null);
        if (getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() != 0) {
            startActivity(intent);
        } else if (str.contains("www.amazon.com")) {
            try {
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str.replaceAll("amzn://", "http://")));
                intent2.setFlags(268435456);
                startActivity(intent2);
            } catch (Exception e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void J(String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.setFlags(268435456);
        if (getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() != 0) {
            startActivity(intent);
            return;
        }
        try {
            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str.replaceAll("market://details?", "https://play.google.com/store/apps/details?").replaceAll("market://search?", "https://play.google.com/store/search?")));
            intent2.setFlags(268435456);
            startActivity(intent2);
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void K(String str) {
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage("com.skt.skaf.A000Z00040");
        if (launchIntentForPackage != null) {
            launchIntentForPackage.addFlags(536870912);
            launchIntentForPackage.setClassName("com.skt.skaf.A000Z00040", "com.skt.skaf.A000Z00040.A000Z00040");
            launchIntentForPackage.setAction("COLLAB_ACTION");
            launchIntentForPackage.putExtra("com.skt.skaf.COL.URI", ("PRODUCT_VIEW/" + str + "/0").getBytes());
            launchIntentForPackage.putExtra("com.skt.skaf.COL.REQUESTER", "A000Z00040");
        } else {
            launchIntentForPackage = new Intent("android.intent.action.VIEW", Uri.parse("http://tsto.re/" + str));
            launchIntentForPackage.setFlags(268435456);
        }
        startActivity(launchIntentForPackage);
    }

    private static void S(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                String[] strArrSplit = str.split(":");
                if (strArrSplit.length == 1) {
                    bb.d(strArrSplit[0], true);
                } else if (strArrSplit.length == 3 && strArrSplit[1].equals("delivered")) {
                    bb.d(strArrSplit[0], strArrSplit[2].equals(AppEventsConstants.EVENT_PARAM_VALUE_YES));
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void T(String str) {
        if (Ep != null) {
            Ep.ModalResponse(Es, str);
            Er = true;
        }
    }

    private static void gN() {
        if (Eq && !Er) {
            T("modalwebviewerror:" + Et);
        }
        Ep = null;
        Eq = false;
        Er = false;
        Es = null;
        Et = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        Eu = null;
        Ev = null;
    }

    public static void a(String str, aw awVar, Message message) {
        try {
            JSONObject jSONObject = new JSONObject(URLDecoder.decode(str));
            String strU = U(jSONObject.optString("link", ""));
            Es = U(jSONObject.optString("exitURL", ""));
            if (TextUtils.isEmpty(Es) || TextUtils.isEmpty(strU)) {
                throw new Exception("ParseRequestError");
            }
            if (!DR.compareAndSet(false, true)) {
                throw new Exception("AlreadyInUse");
            }
            Ep = awVar;
            Eq = true;
            Er = false;
            Eu = message;
            Ev = U(jSONObject.optString("onModalWebviewLoad", ""));
            c(strU, false);
        } catch (Exception e) {
            if (TextUtils.isEmpty(Es)) {
                Es = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            }
            awVar.ModalResponse(Es, "modalwebviewerror:" + e.getMessage());
            DR.set(false);
        }
    }

    private static String U(String str) {
        try {
            return new String(Base64.decode(str, 0), AndroidWebView.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }
}
