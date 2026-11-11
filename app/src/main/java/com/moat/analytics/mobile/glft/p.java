package com.moat.analytics.mobile.glft;

import android.content.Context;
import android.media.AudioManager;
import android.os.AsyncTask;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;

/* JADX INFO: loaded from: classes.dex */
class p {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static String f14a;

    p() {
    }

    static void a(final Context context) {
        try {
            AsyncTask.execute(new Runnable() { // from class: com.moat.analytics.mobile.glft.p.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                        if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                            n.a(3, "MoatUtil", this, "User has limited ad tracking");
                        } else {
                            String unused = p.f14a = advertisingIdInfo.getId();
                            n.a(3, "MoatUtil", this, "Retrieved Advertising ID = " + p.f14a);
                        }
                    } catch (Exception e) {
                        l.j(e);
                    }
                }
            });
        } catch (Exception e) {
            l.j(e);
        }
    }

    static double aii() {
        try {
            AudioManager audioManager = (AudioManager) a.ajG().getSystemService("audio");
            return ((double) d()) / ((double) audioManager.getStreamMaxVolume(3));
        } catch (Exception e) {
            l.j(e);
            return 0.0d;
        }
    }

    static String b() {
        return f14a;
    }

    private static int d() {
        try {
            return ((AudioManager) a.ajG().getSystemService("audio")).getStreamVolume(3);
        } catch (Exception e) {
            l.j(e);
            return 0;
        }
    }
}
