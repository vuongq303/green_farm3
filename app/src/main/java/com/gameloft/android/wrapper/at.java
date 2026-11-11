package com.gameloft.android.wrapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/* JADX INFO: compiled from: Tracking.java */
/* JADX INFO: loaded from: classes.dex */
final class at implements Runnable {
    final /* synthetic */ int bhJ;
    final /* synthetic */ int bhK;

    at(int i, int i2) {
        this.bhJ = i;
        this.bhK = i2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String unused = ar.bhz = au.ey(ar.bhz);
            String unused2 = ar.bhz = ar.bhz.replace("http:", "https:");
            String strB = ar.B(ar.bhz, this.bhJ);
            switch (this.bhK) {
                case 0:
                case 2:
                case 3:
                    strB = strB + "&t=facebook";
                    break;
                case 1:
                    strB = strB + "&ctg=FBOOK";
                    break;
            }
            if (au.OW().compareTo("-1") != 0) {
                strB = strB + "&user_age=" + au.OW();
                if (au.OX() != null && au.OX().trim().length() != 0) {
                    strB = strB + "&gender=" + au.OX();
                }
            }
            String str = "serverURL " + strB;
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strB).openConnection();
            httpURLConnection.setConnectTimeout(ar.bhx);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Connection", "close");
            String str2 = "response: " + httpURLConnection.getResponseCode();
        } catch (UnknownHostException e) {
        } catch (Exception e2) {
        }
    }
}
