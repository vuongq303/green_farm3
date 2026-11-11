package com.gameloft.android.wrapper;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class bf extends Thread {
    bf() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(au.getContext());
            if (advertisingIdInfo != null) {
                au.bif = advertisingIdInfo.getId();
                if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                    au.biU = 1;
                } else {
                    au.biU = 0;
                }
            }
        } catch (com.google.android.gms.common.k e) {
            au.biU = 2;
            au.bif = "";
            String str = "getGoogleAdId: " + e.getMessage();
        } catch (Exception e2) {
            au.biU = 4;
            au.bif = "";
            String str2 = "getGoogleAdId:" + e2.getMessage();
        }
        String str3 = "getGoogleAdId: " + au.bif + "\n GoogleAdIdStatus = " + au.biU;
    }
}
