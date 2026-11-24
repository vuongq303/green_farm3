package com.quanhoang.greenfarm3;

import android.os.Build;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import java.net.MalformedURLException;
import java.util.Locale;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: SplashScreenActivity.java */
/* JADX INFO: loaded from: classes.dex */
final class bp implements Runnable {
    final /* synthetic */ int bcM;
    final /* synthetic */ String bcN;

    bp(int i, String str) {
        this.bcM = i;
        this.bcN = str;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        String str;
        if (SplashScreenActivity.bcI) {
            while (SplashScreenActivity.bcI) {
                try {
                    Thread.sleep(100L);
                } catch (Exception e) {
                }
            }
        }
        boolean unused = SplashScreenActivity.bcI = true;
        SplashScreenActivity.CJ = this.bcM;
        if (SplashScreenActivity.CJ < 0 || SplashScreenActivity.CJ >= SplashScreenActivity.Dd.length) {
            SplashScreenActivity.CJ = 0;
        }
        String[] strArrSplit = this.bcN.replaceAll(" ", "").split(",");
        for (int i = 0; i < strArrSplit.length; i++) {
            String str2 = strArrSplit[i];
            com.gameloft.android.wrapper.au.getDeviceId();
            String country = Locale.getDefault().getCountry();
            String str3 = Build.MANUFACTURER + "_" + Build.MODEL;
            String str4 = Build.VERSION.RELEASE;
            Display defaultDisplay = ((WindowManager) com.gameloft.android.wrapper.au.getContext().getSystemService("window")).getDefaultDisplay();
            SplashScreenActivity.CS = defaultDisplay.getHeight();
            SplashScreenActivity.CR = defaultDisplay.getWidth();
            SplashScreenActivity.bcC = (int) (0.8d * ((double) SplashScreenActivity.CR));
            SplashScreenActivity.bcB = (int) (0.835d * ((double) SplashScreenActivity.CS));
            SplashScreenActivity.bcE = SplashScreenActivity.bcF.replace("VERSION", MIDlet.d(null, "MIDlet-Version")).replace("LANG", SplashScreenActivity.Dd[SplashScreenActivity.CJ]).replace("COUNTRY", country).replace("FROM", MIDlet.d(null, "GAME-IGP-CODE")).replace("DEVICE", str3).replace("FIRMWARE", str4).replace("ANDROIDID", com.gameloft.android.wrapper.au.getAndroidId()).replace("UDIDPHONE", com.gameloft.android.wrapper.au.getSerial()).replace("HDIDFV", com.gameloft.android.wrapper.au.Os()).replaceAll(" ", "");
            SplashScreenActivity.bcE += "&width=" + SplashScreenActivity.bcC;
            SplashScreenActivity.bcE += "&height=" + SplashScreenActivity.bcB;
            SplashScreenActivity.bcE += "&id=" + str2;
            SplashScreenActivity.bcE += "&dl=1";
            SplashScreenActivity.bcE += (SplashScreenActivity.bcH ? "&is_pau=1" : "&is_pau=0");
            String[] strArrSplit2 = SplashScreenActivity.bcE.split("[?]");
            SplashScreenActivity.bcE = strArrSplit2[0] + "?data=" + com.quanhoang.greenfarm3.a.b.crypt(strArrSplit2[1]) + "&enc=1";
            if (Environment.getExternalStorageState().equals("mounted")) {
                if (Build.VERSION.SDK_INT >= 8) {
                    str = com.gameloft.android.wrapper.au.getContext().getExternalFilesDir(null) + "/WelcomeScreens/" + str2 + "/" + SplashScreenActivity.Dd[SplashScreenActivity.CJ];
                } else {
                    str = Environment.getDownloadCacheDirectory() + "/WelcomeScreens/" + MIDlet.d(null, "GAME-IGP-CODE") + "/" + str2 + "/" + SplashScreenActivity.Dd[SplashScreenActivity.CJ];
                }
                try {
                    SplashScreenActivity.y(SplashScreenActivity.bcE, str, "welcome.html");
                } catch (MalformedURLException e2) {
                } catch (Exception e3) {
                }
            }
            if (i < strArrSplit.length - 1) {
                try {
                    Thread.sleep(SplashScreenActivity.bcD);
                } catch (Exception e4) {
                }
            }
        }
        boolean unused2 = SplashScreenActivity.bcI = false;
    }
}
