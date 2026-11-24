package com.quanhoang.greenfarm3;

import android.os.Build;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import java.util.Locale;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: SplashScreenActivity.java */
/* JADX INFO: loaded from: classes.dex */
final class bn implements Runnable {
    final /* synthetic */ int bcM;

    bn(int i) {
        this.bcM = i;
    }

    @Override // java.lang.Runnable
    public void run() {
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
        com.gameloft.android.wrapper.au.getDeviceId();
        String country = Locale.getDefault().getCountry();
        String str2 = Build.MANUFACTURER + "_" + Build.MODEL;
        String str3 = Build.VERSION.RELEASE;
        Display defaultDisplay = ((WindowManager) com.gameloft.android.wrapper.au.getContext().getSystemService("window")).getDefaultDisplay();
        SplashScreenActivity.CS = defaultDisplay.getHeight();
        SplashScreenActivity.CR = defaultDisplay.getWidth();
        int iRound = Math.round(0.8f * SplashScreenActivity.CR);
        int iRound2 = Math.round(0.835f * SplashScreenActivity.CS);
        SplashScreenActivity.bcB = 0;
        SplashScreenActivity.bcC = 0;
        float f = 1.5079365f;
        if (com.gameloft.android.wrapper.d.beG.compareTo("portrait") == 0) {
            f = 0.6631579f;
        }
        while (SplashScreenActivity.bcC <= iRound && SplashScreenActivity.bcB <= iRound2) {
            SplashScreenActivity.bcB++;
            SplashScreenActivity.bcC = Math.round(SplashScreenActivity.bcB * f);
        }
        SplashScreenActivity.bcE = SplashScreenActivity.bcF.replace("VERSION", MIDlet.cnN).replace("LANG", SplashScreenActivity.Dd[SplashScreenActivity.CJ]).replace("COUNTRY", country).replace("FROM", MIDlet.d(null, "GAME-IGP-CODE")).replace("DEVICE", str2).replace("FIRMWARE", str3).replace("ANDROIDID", com.gameloft.android.wrapper.au.getAndroidId()).replace("UDIDPHONE", com.gameloft.android.wrapper.au.getSerial()).replace("HDIDFV", com.gameloft.android.wrapper.au.Os()).replaceAll(" ", "");
        SplashScreenActivity.bcE += "&width=" + SplashScreenActivity.bcC;
        SplashScreenActivity.bcE += "&height=" + SplashScreenActivity.bcB;
        SplashScreenActivity.bcE += "&dl=1";
        SplashScreenActivity.bcE += "&ingamebrowser=1";
        SplashScreenActivity.bcE += (SplashScreenActivity.bcH ? "&is_pau=1" : "&is_pau=0");
        String[] strArrSplit = SplashScreenActivity.bcE.split("[?]");
        SplashScreenActivity.bcE = strArrSplit[0] + "?data=" + com.quanhoang.greenfarm3.a.b.crypt(strArrSplit[1]) + "&enc=1";
        if (Environment.getExternalStorageState().equals("mounted")) {
            str = com.gameloft.android.wrapper.au.getContext().getExternalFilesDir(null) + "/welcome";
        } else {
            str = com.gameloft.android.wrapper.au.getContext().getFilesDir() + "/welcome";
        }
        SplashScreenActivity.c(new File(str));
        SplashScreenActivity.CA = true;
        SplashScreenActivity.bcy = false;
        try {
            q qVar = new q(SplashScreenActivity.bcE, str, "welcome.html");
            qVar.gx();
            if (!qVar.gz()) {
                SplashScreenActivity.bcw = str + "/welcome.html";
                SplashScreenActivity.bcv = true;
            } else {
                SplashScreenActivity.bcv = false;
                SplashScreenActivity.bcw = "";
                SplashScreenActivity.bcy = true;
                SplashScreenActivity.CA = qVar.gy();
            }
        } catch (Exception e2) {
        }
        boolean unused2 = SplashScreenActivity.bcI = false;
    }
}
