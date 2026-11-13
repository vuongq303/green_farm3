package com.quanhoang.greenfarm3.installer;

import com.gameloft.android.wrapper.ar;
import com.gameloft.android.wrapper.au;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/* JADX INFO: compiled from: IReferrerReceiver.java */
/* JADX INFO: loaded from: classes.dex */
final class a implements Runnable {
    a() {
    }

    @Override // java.lang.Runnable
    public void run() {
        IReferrerReceiver.beB.lock();
        try {
            try {
                String str = (ar.eh(ar.bhy) + "&check=2") + "&appType=3";
                String strAl = IReferrerReceiver.al(au.getContext());
                if (strAl == null || strAl.compareTo("") == 0) {
                    try {
                        Thread.sleep(3000L);
                    } catch (Exception e) {
                    }
                    strAl = IReferrerReceiver.al(au.getContext());
                }
                if (strAl == null || strAl.compareTo("") == 0) {
                    IReferrerReceiver.beB.unlock();
                    return;
                }
                String str2 = str + "&action=InstallReferrer&" + strAl;
                String str3 = "Referrer info: " + strAl;
                String str4 = "serverURL " + str2;
                if (au.A("trc_LI_ver", "0.0.0", ar.bht).compareTo(ar.bhv) != 0) {
                    au.Y("trc_SentIR", ar.bht);
                }
                if (au.b("trc_SentIR", false, ar.bht)) {
                    IReferrerReceiver.beB.unlock();
                    return;
                }
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
                httpURLConnection.setConnectTimeout(ar.bhx);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Connection", "close");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode != 200) {
                    String str5 = "error " + responseCode;
                }
                au.a("trc_SentIR", (Object) true, ar.bht);
                au.a("trc_LI_ver", ar.bhv, ar.bht);
                IReferrerReceiver.beB.unlock();
            } catch (Exception e2) {
                String str6 = "Exception:" + e2.toString();
            }
        } catch (UnknownHostException e3) {
        }
    }
}
