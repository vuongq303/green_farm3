package com.gameloft.android.wrapper;

import android.text.TextUtils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/* JADX INFO: compiled from: Tracking.java */
/* JADX INFO: loaded from: classes.dex */
final class as implements Runnable {
    as() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            ar.bhy = au.ey(ar.bhy);
            String strEh = ar.eh(ar.bhy);
            if (ar.bhF.equals("ANMP")) {
                if ((au.getContext().getApplicationInfo().flags & 128) != 0) {
                    strEh = strEh + "&appType=2";
                } else if ((au.getContext().getApplicationInfo().flags & 1) != 0) {
                    strEh = strEh + "&appType=1";
                } else {
                    strEh = strEh + "&appType=3";
                }
                if (!TextUtils.isEmpty(au.PA())) {
                    strEh = strEh + "&injected_igp=" + au.PA();
                }
                if (!TextUtils.isEmpty(au.PC())) {
                    strEh = strEh + "&d=" + au.PC();
                }
            }
            String str = strEh + "&check=2";
            String str2 = "serverURL " + str;
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(ar.bhx);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Connection", "close");
            String str3 = "response: " + httpURLConnection.getResponseCode();
        } catch (UnknownHostException e) {
        } catch (Exception e2) {
        }
    }
}
