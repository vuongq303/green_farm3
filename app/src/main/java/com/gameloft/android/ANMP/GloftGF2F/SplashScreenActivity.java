package com.gameloft.android.ANMP.GloftGF2F;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import com.facebook.internal.NativeProtocol;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: loaded from: classes.dex */
public class SplashScreenActivity extends javax.microedition.lcdui.q {
    public static String CL;
    public static WebView Dg;
    public static javax.microedition.lcdui.q bcA;
    static int bcB;
    static int bcC;
    public static String bcw;
    public static String bcz;
    public RelativeLayout Df;
    private Display display;
    public static int CJ = 0;
    public static boolean CG = false;
    private static boolean bcu = false;
    public static boolean bcv = false;
    public static boolean bcx = false;
    public static boolean CA = true;
    public static boolean bcy = false;
    static int CR = 800;
    static int CS = 480;
    private static int bcD = 60000;
    public static String bcE = "";
    public static String bcF = "https://201205igp.gameloft.com/redir/ads/splashscreen_view.php?from=FROM&country=COUNTRY&lg=LANG&udid=UDIDPHONE&hdidfv=HDIDFV&androidid=ANDROIDID&d=DEVICE&f=FIRMWARE&game_ver=VERSION&igp_rev=1005&os=android";
    public static String bcG = "https://201205igp.gameloft.com/redir/ads/hocscreen.php?from=FROM&country=COUNTRY&lg=LANG&udid=UDIDPHONE&hdidfv=HDIDFV&androidid=ANDROIDID&d=DEVICE&f=FIRMWARE&game_ver=VERSION&igp_rev=1005&os=android";
    public static String[] Dd = {"EN", "DE", "FR", "IT", "SP", "BR", "BR", "JP", "CN", "KR", "RU", "TR", "PL", "CZ", "NL", "TH", "VI", "AR", "ID", "ZT", "ES"};
    private static boolean bcH = false;
    private static boolean bcI = false;

    public SplashScreenActivity(Context context, int i) {
        super(context, i);
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setCanceledOnTouchOutside(false);
        getWindow().setFlags(66304, 90880);
        bcF = com.gameloft.android.wrapper.au.ey(bcF);
        bcG = com.gameloft.android.wrapper.au.ey(bcG);
        if (MIDlet.cnz == null) {
            Nc();
            return;
        }
        int i = CJ;
        int i2 = (i < 0 || i >= Dd.length) ? 0 : i;
        this.display = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        CS = this.display.getHeight();
        CR = this.display.getWidth();
        this.Df = new RelativeLayout(getContext());
        Dg = new WebView(getContext());
        Dg.getSettings().setJavaScriptEnabled(true);
        Dg.getSettings().setAppCacheEnabled(true);
        Dg.getSettings().setSupportZoom(false);
        Dg.getSettings().setBuiltInZoomControls(false);
        Dg.getSettings().setDefaultTextEncodingName("utf-8");
        Dg.getSettings().setLightTouchEnabled(true);
        Dg.getSettings().setLoadsImagesAutomatically(true);
        Dg.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        Dg.setScrollBarStyle(33554432);
        Dg.setHorizontalScrollBarEnabled(false);
        Dg.getSettings().setLoadWithOverviewMode(true);
        Dg.getSettings().setUseWideViewPort(false);
        if (Build.VERSION.SDK_INT >= 21) {
            Dg.getSettings().setMixedContentMode(0);
        }
        Dg.setBackgroundColor(0);
        Dg.setInitialScale(100);
        Dg.setOnLongClickListener(new bj(this));
        Dg.setWebViewClient(new bq(this, null));
        Dg.setWebChromeClient(new bk(this));
        int iRound = Math.round(0.8f * CR);
        int iRound2 = Math.round(0.835f * CS);
        bcB = 0;
        bcC = 0;
        float f = 1.5079365f;
        if (com.gameloft.android.wrapper.d.beG.compareTo("portrait") == 0) {
            f = 0.6631579f;
        }
        while (bcC <= iRound && bcB <= iRound2) {
            bcB++;
            bcC = Math.round(bcB * f);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.x = 0;
        attributes.height = bcB;
        attributes.width = bcC;
        attributes.y = 0;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(bcC, bcB);
        getWindow().setLayout(-2, bcB);
        getWindow().setAttributes(attributes);
        this.Df.addView(Dg, layoutParams);
        setContentView(this.Df, layoutParams);
        CG = true;
        if (bcv && bcw != null) {
            Dg.loadUrl("file://" + bcw);
        } else {
            show(i2);
        }
    }

    void show(int i) {
        CJ = i;
        if (CJ < 0 || CJ >= Dd.length) {
            CJ = 0;
        }
        com.gameloft.android.wrapper.au.getDeviceId();
        bcE = bcF.replace("VERSION", MIDlet.cnN).replace("LANG", Dd[CJ]).replace("COUNTRY", Locale.getDefault().getCountry()).replace("FROM", MIDlet.d(this, "GAME-IGP-CODE")).replace("DEVICE", Build.MANUFACTURER + "_" + Build.MODEL).replace("FIRMWARE", Build.VERSION.RELEASE).replace("ANDROIDID", com.gameloft.android.wrapper.au.getAndroidId()).replace("UDIDPHONE", com.gameloft.android.wrapper.au.getSerial()).replace("HDIDFV", com.gameloft.android.wrapper.au.Os()).replaceAll(" ", "");
        bcE += "&width=" + bcC;
        bcE += "&height=" + bcB;
        bcE += "&ingamebrowser=1";
        bcE += (bcH ? "&is_pau=1" : "&is_pau=0");
        String[] strArrSplit = bcE.split("[?]");
        bcE = strArrSplit[0] + "?data=" + com.gameloft.android.ANMP.GloftGF2F.a.b.crypt(strArrSplit[1]) + "&enc=1";
        Dg.loadUrl(bcE);
    }

    public static void mr(int i) {
        ms(i);
    }

    public static void ms(int i) {
        CJ = i;
        if (CJ < 0 || CJ >= Dd.length) {
            CJ = 0;
        }
        com.gameloft.android.wrapper.au.getDeviceId();
        String country = Locale.getDefault().getCountry();
        String str = Build.MANUFACTURER + "_" + Build.MODEL;
        String str2 = Build.VERSION.RELEASE;
        Display defaultDisplay = ((WindowManager) com.gameloft.android.wrapper.au.getContext().getSystemService("window")).getDefaultDisplay();
        CS = defaultDisplay.getHeight();
        CR = defaultDisplay.getWidth();
        int iRound = Math.round(0.8f * CR);
        int iRound2 = Math.round(0.835f * CS);
        bcB = 0;
        bcC = 0;
        float f = 1.5079365f;
        if (com.gameloft.android.wrapper.d.beG.compareTo("portrait") == 0) {
            f = 0.6631579f;
        }
        while (bcC <= iRound && bcB <= iRound2) {
            bcB++;
            bcC = Math.round(bcB * f);
        }
        bcE = bcF.replace("VERSION", MIDlet.cnN).replace("LANG", Dd[CJ]).replace("COUNTRY", country).replace("FROM", MIDlet.d(null, "GAME-IGP-CODE")).replace("DEVICE", str).replace("FIRMWARE", str2).replace("ANDROIDID", com.gameloft.android.wrapper.au.getAndroidId()).replace("UDIDPHONE", com.gameloft.android.wrapper.au.getSerial()).replace("HDIDFV", com.gameloft.android.wrapper.au.Os()).replaceAll(" ", "");
        bcE += "&width=" + bcC;
        bcE += "&height=" + bcB;
        bcE += "&dl=1";
        bcE += "&ingamebrowser=1";
        bcE += (bcH ? "&is_pau=1" : "&is_pau=0");
        String[] strArrSplit = bcE.split("[?]");
        bcE = strArrSplit[0] + "?data=" + com.gameloft.android.ANMP.GloftGF2F.a.b.crypt(strArrSplit[1]) + "&enc=1";
        new Thread(new bl(i)).start();
    }

    public static void mt(int i) {
        new Thread(new bn(i)).start();
    }

    private static void Q(String str, String str2) {
        try {
            FileWriter fileWriter = new FileWriter(new File(str2, "saved.etags"), false);
            fileWriter.write(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    private static String dJ(String str) {
        try {
            FileReader fileReader = new FileReader(new File(str, "saved.etags"));
            String line = new BufferedReader(fileReader).readLine();
            fileReader.close();
            return line;
        } catch (Exception e) {
            return null;
        }
    }

    public static int mu(int i) {
        String str;
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                str = com.gameloft.android.wrapper.au.getContext().getExternalFilesDir(null) + "/welcome";
            } else {
                str = com.gameloft.android.wrapper.au.getContext().getFilesDir() + "/welcome";
            }
            File file = new File(str);
            return ((file.exists() && file.list() != null && file.list().length != 0) && CA && !bcI) ? 1 : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void dK(String str) {
        String str2;
        if (Environment.getExternalStorageState().equals("mounted")) {
            str2 = com.gameloft.android.wrapper.au.getContext().getExternalFilesDir(null) + "/welcome";
        } else {
            str2 = com.gameloft.android.wrapper.au.getContext().getFilesDir() + "/welcome";
        }
        c(new File(str2));
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0077  */
    /* JADX WARN: Code duplicated, block: B:27:0x007f  */
    /* JADX WARN: Code duplicated, block: B:29:0x0084  */
    /* JADX WARN: Code duplicated, block: B:65:? A[SYNTHETIC] */
    static void y(String str, String str2, String str3) throws Throwable {
        Throwable th;
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream;
        HttpURLConnection httpURLConnection;
        Exception e;
        File file = new File(str2);
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                String strDJ = dJ(str2);
                if (strDJ != null) {
                    httpURLConnection2.setRequestProperty("If-None-Match", strDJ);
                }
                if (httpURLConnection2.getResponseCode() == 200) {
                    c(file);
                    file.mkdirs();
                    String headerField = httpURLConnection2.getHeaderField("Etag");
                    if (headerField != null) {
                        Q(headerField, str2);
                    }
                    bufferedInputStream = new BufferedInputStream(httpURLConnection2.getInputStream());
                    try {
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2 + "/" + str3));
                        while (true) {
                            try {
                                int i = bufferedInputStream.read();
                                if (i == -1) {
                                    break;
                                } else {
                                    bufferedOutputStream.write(i);
                                }
                            } catch (Exception e2) {
                                e = e2;
                                httpURLConnection = httpURLConnection2;
                                try {
                                    c(file);
                                    throw e;
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (bufferedOutputStream != null) {
                                        bufferedOutputStream.flush();
                                        bufferedOutputStream.close();
                                    }
                                    if (bufferedInputStream != null) {
                                        bufferedInputStream.close();
                                    }
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                        throw th;
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                httpURLConnection = httpURLConnection2;
                                if (bufferedOutputStream != null) {
                                    bufferedOutputStream.flush();
                                    bufferedOutputStream.close();
                                }
                                if (bufferedInputStream != null) {
                                    bufferedInputStream.close();
                                }
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                    throw th;
                                }
                                throw th;
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        bufferedOutputStream = null;
                        httpURLConnection = httpURLConnection2;
                    } catch (Throwable th4) {
                        th = th4;
                        bufferedOutputStream = null;
                        httpURLConnection = httpURLConnection2;
                    }
                } else {
                    bufferedOutputStream = null;
                    bufferedInputStream = null;
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
            } catch (Exception e4) {
                e = e4;
                bufferedOutputStream = null;
                bufferedInputStream = null;
                httpURLConnection = httpURLConnection2;
            } catch (Throwable th5) {
                th = th5;
                bufferedOutputStream = null;
                bufferedInputStream = null;
                httpURLConnection = httpURLConnection2;
            }
        } catch (Exception e5) {
            e = e5;
            bufferedOutputStream = null;
            bufferedInputStream = null;
            httpURLConnection = null;
        } catch (Throwable th6) {
            th = th6;
            bufferedOutputStream = null;
            bufferedInputStream = null;
            httpURLConnection = null;
        }
    }

    static void c(File file) {
        try {
            if (file.isDirectory()) {
                for (String str : file.list()) {
                    c(new File(file, str));
                }
            }
            File file2 = new File(file.getAbsolutePath() + System.currentTimeMillis());
            file.renameTo(file2);
            file2.delete();
        } catch (Exception e) {
        }
    }

    void Nc() {
        try {
            CG = false;
            Dg.loadUrl("about:blank");
            dismiss();
            if (Build.VERSION.SDK_INT >= 19) {
                com.gameloft.android.wrapper.z.k(com.gameloft.android.wrapper.au.getActivity());
                com.gameloft.android.wrapper.au.getActivity().getWindow().getDecorView().setSystemUiVisibility(4102);
            }
        } catch (Exception e) {
        }
    }

    public static void Nd() {
        try {
            CG = false;
            Dg.post(new bo());
            bcA.dismiss();
            if (Build.VERSION.SDK_INT >= 19) {
                com.gameloft.android.wrapper.z.k(com.gameloft.android.wrapper.au.getActivity());
                com.gameloft.android.wrapper.au.getActivity().getWindow().getDecorView().setSystemUiVisibility(4102);
            }
        } catch (Exception e) {
        }
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        Nc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dL(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (com.gameloft.android.wrapper.au.getActivity().getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() == 0) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/watch?v=" + str.replace("vnd.youtube:", "")));
        }
        com.gameloft.android.wrapper.au.getActivity().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dM(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (com.gameloft.android.wrapper.au.getActivity().getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() == 0) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("http://market.android.com/" + str.replace("market://", "")));
        }
        com.gameloft.android.wrapper.au.getActivity().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dN(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (com.gameloft.android.wrapper.au.getActivity().getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() == 0) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse(str.replace("amzn://", "http://")));
        }
        com.gameloft.android.wrapper.au.getActivity().startActivity(intent);
    }

    public void dO(String str) {
        try {
            new Intent("android.intent.action.MAIN");
            Intent launchIntentForPackage = com.gameloft.android.wrapper.au.getActivity().getPackageManager().getLaunchIntentForPackage(str);
            launchIntentForPackage.addCategory("android.intent.category.LAUNCHER");
            com.gameloft.android.wrapper.au.getActivity().startActivity(launchIntentForPackage);
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:29:0x0051  */
    public String dP(String str) throws Throwable {
        Throwable th;
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2;
        String str2;
        try {
            httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setAllowUserInteraction(false);
                httpURLConnection2.setInstanceFollowRedirects(false);
                httpURLConnection2.setRequestMethod("GET");
                httpURLConnection2.connect();
                String headerField = httpURLConnection2.getHeaderField("Location");
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                    str2 = headerField;
                } else {
                    str2 = headerField;
                }
            } catch (MalformedURLException e) {
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                    str2 = null;
                } else {
                    str2 = null;
                }
            } catch (Exception e2) {
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                    str2 = null;
                } else {
                    str2 = null;
                }
            } catch (Throwable th2) {
                th = th2;
                httpURLConnection = httpURLConnection2;
                if (httpURLConnection == null) {
                    throw th;
                }
                httpURLConnection.disconnect();
                throw th;
            }
        } catch (MalformedURLException e3) {
            httpURLConnection2 = null;
        } catch (Exception e4) {
            httpURLConnection2 = null;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnection = null;
        }
        com.gameloft.android.wrapper.au.Qi();
        return str2;
    }

    public static void Ne() {
        bcx = false;
        CG = false;
        Nd();
    }

    public static void x(String str, int i) {
        new Thread(new bp(i, str)).start();
    }

    public static int y(String str, int i) {
        String str2;
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= 8) {
            str2 = com.gameloft.android.wrapper.au.getContext().getExternalFilesDir(null) + "/WelcomeScreens/" + str + "/" + Dd[i];
        } else {
            str2 = Environment.getDownloadCacheDirectory() + "/WelcomeScreens/" + MIDlet.d(null, "GAME-IGP-CODE") + "/" + str + "/" + Dd[i];
        }
        File file = new File(str2);
        return file.exists() && file.list() != null && file.list().length != 0 ? 1 : 0;
    }

    public static void dQ(String str) {
        String str2;
        for (String str3 : str.replaceAll(" ", "").split(",")) {
            if (Environment.getExternalStorageState().equals("mounted")) {
                if (Build.VERSION.SDK_INT >= 8) {
                    str2 = com.gameloft.android.wrapper.au.getContext().getExternalFilesDir(null) + "/WelcomeScreens/" + str3;
                } else {
                    str2 = Environment.getDownloadCacheDirectory() + "/WelcomeScreens/" + MIDlet.d(null, "GAME-IGP-CODE") + "/" + str3;
                }
                c(new File(str2));
            }
        }
    }
}
