package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.os.SystemClock;
import com.facebook.appevents.UserDataStore;
import com.google.android.gms.ads.formats.NativeContentAd;

/* JADX INFO: loaded from: classes.dex */
public class ah {
    static int Bj = 0;
    static long acr = 0;
    static long acs = 0;
    static boolean act = false;
    static boolean acu = false;
    static boolean acv = false;

    static void d(bc bcVar) {
    }

    static void et(int i) {
        String strDr = GloftGF2F.acw.dr(i);
        String strBY = GloftGF2F.acx.bY("microedition.locale.simcountry");
        String str = "UpdateDeviceInfo - country " + strBY + " - " + s.js();
        if (strBY == null || strBY.equals("")) {
            strBY = GloftGF2F.acx.bY("microedition.locale.country");
        }
        s.b(strDr.toLowerCase(), com.gameloft.android.wrapper.au.Pn(), strBY, com.gameloft.android.wrapper.au.getDeviceName(), "hestiaSetDeviceInfo");
        if (strDr.toLowerCase().equals("es_latam")) {
            strDr = "le";
        }
        if (strDr.toLowerCase().equals("kr") || strDr.toLowerCase().equals("ko")) {
            strDr = "ko";
        }
        if (strDr.toLowerCase().equals("jp") || strDr.toLowerCase().equals("ja")) {
            strDr = "ja";
        }
        if (bz.om().fZ()) {
            bc bcVar = new bc();
            bcVar.a("language", strDr.toLowerCase());
            bcVar.a(UserDataStore.COUNTRY, strBY);
            bz.om().i(bcVar);
        }
    }

    static void mc() {
        Bj = 0;
    }

    static void mf() {
        bc bcVarMu;
        switch (Bj) {
            case 0:
                Bj = 1;
                break;
            case 1:
                mh();
                Bj = 2;
                break;
            case 2:
                if (ao.ms().mx() && !act && (bcVarMu = ao.ms().mu()) != null) {
                    d(bcVarMu);
                    act = true;
                    break;
                }
                break;
        }
    }

    static void mh() {
        ao.ms().m(NativeContentAd.ASSET_HEADLINE, "/InitialFeed.bin", "en");
    }

    static void mi() {
        ci.qI();
    }

    static void md() {
        i.ic().h(GLLib.Qy);
        ao aoVarMs = ao.ms();
        if (aoVarMs.mw() && !acv) {
            acv = true;
            mi();
        }
        int iMy = aoVarMs.my();
        if (iMy == 0) {
            mi();
            String str = "GameCRMManager.Update =CRM inited.";
        }
        if (iMy == 1) {
            if (aoVarMs.mv() == 0) {
                ci.aqB = true;
                String str2 = "GameCRMManager.Update =Refresh config success";
                mi();
                bc bcVarMu = aoVarMs.mu();
                if (bcVarMu != null) {
                    d(bcVarMu);
                }
            } else {
                String str3 = "GameCRMManager.Update =" + ("Refresh config get error:" + aoVarMs.mv());
            }
        }
        if (s.fZ() && !acu && bz.om().fZ()) {
            acu = true;
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (w.Vl) {
                jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
            }
            acs = jCurrentTimeMillis;
            aoVarMs.ev(2);
        }
    }

    static void me() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        if (jCurrentTimeMillis - acs <= 1800000 || !acu) {
            return;
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        acs = jCurrentTimeMillis2;
        String str = "GameCRMManager._UpdateRefreshTimer =" + ao.ms().ev(2);
    }

    static void mg() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        acr = jCurrentTimeMillis;
        md();
        me();
        mf();
    }
}
