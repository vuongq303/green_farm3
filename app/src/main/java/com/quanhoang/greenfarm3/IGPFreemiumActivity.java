package com.quanhoang.greenfarm3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.facebook.internal.NativeProtocol;
import java.util.Locale;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: loaded from: classes.dex */
public class IGPFreemiumActivity extends javax.microedition.lcdui.q {
    public static boolean CG = false;
    public static boolean CH = false;
    public static boolean CI = false;
    public static int CJ = 0;
    public static boolean CK = false;
    public static String CL = "TEST";
    private static int CR = 800;
    private static int CS = 480;
    public static int[] CZ = {R.string.IGP_LOADING_EN, R.string.IGP_LOADING_DE, R.string.IGP_LOADING_FR, R.string.IGP_LOADING_IT, R.string.IGP_LOADING_SP, R.string.IGP_LOADING_BR, R.string.IGP_LOADING_BR, R.string.IGP_LOADING_JP, R.string.IGP_LOADING_CN, R.string.IGP_LOADING_KR, R.string.IGP_LOADING_RU, R.string.IGP_LOADING_TR, R.string.IGP_LOADING_PL, R.string.IGP_LOADING_EN, R.string.IGP_LOADING_EN, R.string.IGP_LOADING_TH, R.string.IGP_LOADING_VI, R.string.IGP_LOADING_AR, R.string.IGP_LOADING_ID, R.string.IGP_LOADING_ZT};
    public static int[] Da = {R.string.IGP_NET_ERROR_EN, R.string.IGP_NET_ERROR_DE, R.string.IGP_NET_ERROR_FR, R.string.IGP_NET_ERROR_IT, R.string.IGP_NET_ERROR_SP, R.string.IGP_NET_ERROR_BR, R.string.IGP_NET_ERROR_BR, R.string.IGP_NET_ERROR_JP, R.string.IGP_NET_ERROR_CN, R.string.IGP_NET_ERROR_KR, R.string.IGP_NET_ERROR_RU, R.string.IGP_NET_ERROR_TR, R.string.IGP_NET_ERROR_PL, R.string.IGP_NET_ERROR_EN, R.string.IGP_NET_ERROR_EN, R.string.IGP_NET_ERROR_TH, R.string.IGP_NET_ERROR_VI, R.string.IGP_NET_ERROR_AR, R.string.IGP_NET_ERROR_ID, R.string.IGP_NET_ERROR_ZT};
    public static int[] Db = {R.string.IGP_OK_EN, R.string.IGP_OK_DE, R.string.IGP_OK_FR, R.string.IGP_OK_IT, R.string.IGP_OK_SP, R.string.IGP_OK_BR, R.string.IGP_OK_BR, R.string.IGP_OK_JP, R.string.IGP_OK_CN, R.string.IGP_OK_KR, R.string.IGP_OK_RU, R.string.IGP_OK_TR, R.string.IGP_OK_PL, R.string.IGP_OK_EN, R.string.IGP_OK_EN, R.string.IGP_OK_TH, R.string.IGP_OK_VI, R.string.IGP_OK_AR, R.string.IGP_OK_ID, R.string.IGP_OK_ZT};
    public static int[] Dc = {R.string.IGP_CANNOT_GO_BACK_EN, R.string.IGP_CANNOT_GO_BACK_DE, R.string.IGP_CANNOT_GO_BACK_FR, R.string.IGP_CANNOT_GO_BACK_IT, R.string.IGP_CANNOT_GO_BACK_SP, R.string.IGP_CANNOT_GO_BACK_BR, R.string.IGP_CANNOT_GO_BACK_BR, R.string.IGP_CANNOT_GO_BACK_JP, R.string.IGP_CANNOT_GO_BACK_CN, R.string.IGP_CANNOT_GO_BACK_KR, R.string.IGP_CANNOT_GO_BACK_RU, R.string.IGP_CANNOT_GO_BACK_TR, R.string.IGP_CANNOT_GO_BACK_PL, R.string.IGP_CANNOT_GO_BACK_EN, R.string.IGP_CANNOT_GO_BACK_EN, R.string.IGP_CANNOT_GO_BACK_TH, R.string.IGP_CANNOT_GO_BACK_VI, R.string.IGP_CANNOT_GO_BACK_AR, R.string.IGP_CANNOT_GO_BACK_ID, R.string.IGP_CANNOT_GO_BACK_ZT};
    public static String[] Dd = {"EN", "DE", "FR", "IT", "SP", "BR", "BR", "JP", "CN", "KR", "RU", "TR", "PL", "CZ", "NL", "TH", "VI", "AR", "ID", "ZT"};
    static javax.microedition.lcdui.q De = null;
    public static IGPFreemiumActivity Dh;
    private boolean CM;
    private RelativeLayout CN;
    private VideoView CO;
    private boolean CP;
    private boolean CQ;
    private String CT;
    private String CU;
    private String CV;
    private String CW;
    private String CX;
    private String CY;
    private RelativeLayout Df;
    private WebView Dg;
    private javax.microedition.lcdui.ap Di;
    private Display display;

    public IGPFreemiumActivity(Context context, int i) {
        super(context, i);
        this.CM = false;
        this.CN = null;
        this.CO = null;
        this.CP = false;
        this.CQ = false;
        this.CT = "https://201205igp.gameloft.com/redir/freemium/hdfreemium.php?from=GAME_CODE&country=COUNTRY_DETECTED&lg=LANG&udid=UDIDPHONE&hdidfv=HDIDFV&androidid=ANDROIDID&d=DEVICE&f=FIRMWARE&game_ver=VERSION&os=android&igp_rev=1006&conn=CONNSPEED";
        this.CU = "";
        this.CV = "https://signal-back.com";
        this.CW = "https://201205igp.gameloft.com/redir/android/index.php?page=gameinformation";
        this.CX = "https://201205igp.gameloft.com/redir/?from=";
        this.CY = null;
        this.Di = null;
        Dh = this;
    }

    public static void gD() {
        MIDlet.coD = 1;
        CG = true;
        com.gameloft.android.wrapper.au.getActivity().runOnUiThread(new s());
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        MIDlet.coD = 0;
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
    }

    private void gE() {
        this.Dg.getSettings().setJavaScriptEnabled(true);
        this.Dg.getSettings().setAppCacheEnabled(true);
        this.Dg.getSettings().setSupportZoom(false);
        this.Dg.getSettings().setBuiltInZoomControls(false);
        this.Dg.getSettings().setDefaultTextEncodingName("utf-8");
        this.Dg.getSettings().setLightTouchEnabled(true);
        this.Dg.getSettings().setLoadsImagesAutomatically(true);
        this.Dg.setWebChromeClient(new x(this));
        this.Dg.setWebViewClient(new y(this));
        this.Dg.getSettings().setPluginState(WebSettings.PluginState.ON);
        this.Dg.getSettings().setLoadWithOverviewMode(true);
        this.Dg.getSettings().setUseWideViewPort(false);
        this.Dg.setBackgroundColor(Color.argb(1, 255, 255, 255));
        this.Dg.setVerticalScrollbarOverlay(true);
        this.Dg.addJavascriptInterface(new w(this, Dh.getContext(), this.Df), "Android");
        this.Dg.getSettings().setDatabaseEnabled(true);
        this.Dg.getSettings().setDomStorageEnabled(true);
        this.Dg.getSettings().setCacheMode(2);
        if (Build.VERSION.SDK_INT >= 19) {
            this.Dg.getSettings().setUseWideViewPort(true);
            this.Dg.getSettings().setLoadWithOverviewMode(true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            this.Dg.setLayerType(2, null);
        } else if (Build.VERSION.SDK_INT > 10) {
            this.Dg.setLayerType(1, null);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.Dg.getSettings().setMixedContentMode(0);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            this.Dg.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setCanceledOnTouchOutside(false);
        getWindow().setFlags(66304, 66304);
        if (com.gameloft.android.wrapper.au.getContext() == null) {
            gG();
            return;
        }
        this.display = ((WindowManager) com.gameloft.android.wrapper.au.getActivity().getSystemService("window")).getDefaultDisplay();
        int i = CJ;
        CL = MIDlet.d(this, "GAME-IGP-CODE");
        int i2 = (i < 0 || i >= Dd.length) ? 0 : i;
        if (CK) {
            com.gameloft.android.wrapper.au.getActivity().setRequestedOrientation(1);
            CS = this.display.getHeight();
            CR = this.display.getWidth();
        } else {
            CS = this.display.getHeight();
            CR = this.display.getWidth();
        }
        setContentView(R.layout.webview);
        this.Df = new RelativeLayout(getContext());
        this.Dg = (WebView) findViewById(R.id.webview);
        gE();
        if (i2 < 0 || i2 > Dd.length) {
            i2 = 0;
        }
        CG = true;
        a(i2, CL);
    }

    @Override // javax.microedition.lcdui.q, android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        CI = z;
        if (CI) {
            De.getWindow().getDecorView().setSystemUiVisibility(4102);
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 84) {
            return true;
        }
        if (i == 82) {
            keyEvent.startTracking();
            return true;
        }
        return false;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 84) {
            return true;
        }
        if (i == 4) {
            if (!this.CP) {
                if (this.CQ && this.Dg != null) {
                    this.Dg.loadUrl("javascript:onBackPressed()");
                    return true;
                }
                if (CH) {
                    return true;
                }
                gG();
                return true;
            }
            if (CH) {
                return true;
            }
            gG();
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return i != 82;
    }

    private void gF() {
        CG = false;
        if (this.CP && this.CO != null) {
            try {
                this.CP = false;
                this.CO.stopPlayback();
                this.CO = null;
            } catch (Exception e) {
            }
        }
        if (this.Dg != null) {
            com.gameloft.android.wrapper.au.getActivity().runOnUiThread(new t(this));
        }
    }

    public void gG() {
        try {
            gF();
            dismiss();
            if (Build.VERSION.SDK_INT >= 19) {
                com.gameloft.android.wrapper.z.k(com.gameloft.android.wrapper.au.getActivity());
                com.gameloft.android.wrapper.au.getActivity().getWindow().getDecorView().setSystemUiVisibility(4102);
            }
        } catch (Exception e) {
        }
    }

    void a(int i, String str) {
        CI = true;
        CG = true;
        CJ = i;
        String deviceId = com.gameloft.android.wrapper.au.getDeviceId();
        String country = Locale.getDefault().getCountry();
        String str2 = Build.MANUFACTURER + "_" + Build.MODEL;
        String str3 = Build.VERSION.RELEASE;
        if (str2.toLowerCase().contains("kindle") && deviceId.length() <= 1) {
            Settings.Secure.getString(com.gameloft.android.wrapper.au.getActivity().getContentResolver(), "android_id");
        }
        this.CT = com.gameloft.android.wrapper.au.ey(this.CT);
        this.CU = this.CT.replace("LANG", Dd[CJ]);
        this.CU = this.CU.replace("GAME_CODE", str);
        this.CU = this.CU.replace("COUNTRY_DETECTED", country);
        this.CU = this.CU.replace("ANDROIDID", "");
        this.CU = this.CU.replace("UDIDPHONE", "");
        this.CU = this.CU.replace("HDIDFV", com.gameloft.android.wrapper.au.Os());
        this.CU = this.CU.replace("DEVICE", str2);
        this.CU = this.CU.replace("FIRMWARE", str3);
        this.CU = this.CU.replace("VERSION", MIDlet.cnN);
        this.CU = this.CU.replace("CONNSPEED", gJ() ? "fast" : "slow");
        this.CU += "&google_optout=" + com.gameloft.android.wrapper.au.Qe();
        this.CU = this.CU.replaceAll(" ", "");
        Display defaultDisplay = ((WindowManager) com.gameloft.android.wrapper.au.getActivity().getSystemService("window")).getDefaultDisplay();
        this.CU += "&width=" + defaultDisplay.getWidth();
        this.CU += "&height=" + defaultDisplay.getHeight();
        String[] strArrSplit = this.CU.split("[?]");
        this.CU = strArrSplit[0] + "?data=" + com.quanhoang.greenfarm3.a.b.crypt(strArrSplit[1]) + "&enc=1";
        if (Build.VERSION.SDK_INT == 11 || Build.VERSION.SDK_INT == 12) {
            CS -= 48;
        } else if (str2.toLowerCase().contains("kindle")) {
            CS -= 20;
        }
        new RelativeLayout.LayoutParams(-1, -1);
        if (CK) {
            LinearLayout linearLayout = new LinearLayout(com.gameloft.android.wrapper.au.getContext());
            new LinearLayout(com.gameloft.android.wrapper.au.getContext());
            LinearLayout linearLayout2 = new LinearLayout(com.gameloft.android.wrapper.au.getContext());
            new LinearLayout.LayoutParams(-1, (int) TypedValue.applyDimension(1, 25.0f, com.gameloft.android.wrapper.au.getActivity().getResources().getDisplayMetrics()));
            linearLayout2.setBackgroundColor(Color.parseColor("#80000000"));
            linearLayout.setOrientation(1);
            this.Df.requestFocus();
        } else {
            this.Df.setBackgroundColor(Color.parseColor("#80000000"));
        }
        this.Dg.loadUrl(this.CU);
    }

    private void G(String str) {
        com.gameloft.android.wrapper.au.getActivity().runOnUiThread(new u(this, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gH() {
        G(com.gameloft.android.wrapper.au.getActivity().getString(CZ[CJ]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gI() {
        com.gameloft.android.wrapper.au.getActivity().runOnUiThread(new v(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void H(String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.setFlags(268435456);
        if (intent != null && com.gameloft.android.wrapper.au.getActivity().getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() != 0) {
            com.gameloft.android.wrapper.au.getActivity().startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void I(String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.setFlags(268435456);
        if (com.gameloft.android.wrapper.au.getActivity().getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() != 0) {
            com.gameloft.android.wrapper.au.getActivity().startActivity(intent);
        } else if (str.contains("www.amazon.com")) {
            try {
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str.replaceAll("amzn://", "http://")));
                intent2.setFlags(268435456);
                com.gameloft.android.wrapper.au.getActivity().startActivity(intent2);
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
        if (com.gameloft.android.wrapper.au.getActivity().getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() != 0) {
            com.gameloft.android.wrapper.au.getActivity().startActivity(intent);
            return;
        }
        try {
            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str.replaceAll("market://details", "https://play.google.com/store/apps/details").replaceAll("market://search", "https://play.google.com/store/search")));
            intent2.setFlags(268435456);
            com.gameloft.android.wrapper.au.getActivity().startActivity(intent2);
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void K(String str) {
        Intent launchIntentForPackage = com.gameloft.android.wrapper.au.getActivity().getPackageManager().getLaunchIntentForPackage("com.skt.skaf.A000Z00040");
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
        com.gameloft.android.wrapper.au.getActivity().startActivity(launchIntentForPackage);
    }

    public boolean gJ() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) com.gameloft.android.wrapper.au.getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        int type = activeNetworkInfo.getType();
        int subtype = activeNetworkInfo.getSubtype();
        if (type == 1) {
            return true;
        }
        if (type != 0) {
            return false;
        }
        switch (subtype) {
            case 0:
                return false;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return true;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return false;
            case 8:
                return true;
            case 9:
                return true;
            case com.facebook.R.styleable.CardView_contentPaddingRight /* 10 */:
                return true;
            default:
                return aV(subtype);
        }
    }

    private boolean aV(int i) {
        if (Build.VERSION.SDK_INT >= 8 && i == 11) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 9 && i == 12) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 11 || (i != 14 && i != 13)) {
            return Build.VERSION.SDK_INT >= 13 && i == 15;
        }
        return true;
    }
}
