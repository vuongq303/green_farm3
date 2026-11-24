package com.quanhoang.greenfarm3;

import com.quanhoang.greenfarm3.S800x480.ci;
import java.util.Hashtable;

/* JADX INFO: compiled from: GameInstances.java */
/* JADX INFO: loaded from: classes.dex */
public class h {
    static ci cGame800x480;

    public static void a(Hashtable hashtable) {
        if (com.gameloft.android.wrapper.au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.a(hashtable);
        }
        com.quanhoang.greenfarm3.PushNotification.q.hj();
    }

    public static void a(int i, int i2, Hashtable hashtable) {
        if (com.gameloft.android.wrapper.au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.a(i, i2, hashtable);
        }
    }

    public static void b(Hashtable hashtable) {
        try {
            if (com.gameloft.android.wrapper.au.bhM.indexOf("800x480") > -1) {
                ci ciVar = cGame800x480;
                ci.b(hashtable);
            }
        } catch (Exception e) {
            String str = "Exception:" + e.toString();
        }
    }

    public static String fV() {
        if (com.gameloft.android.wrapper.au.bhM.indexOf("800x480") <= -1) {
            return null;
        }
        return com.quanhoang.greenfarm3.S800x480.s.iV();
    }

    public static String getClientId() {
        if (com.gameloft.android.wrapper.au.bhM.indexOf("800x480") <= -1) {
            return null;
        }
        return com.quanhoang.greenfarm3.S800x480.s.gX();
    }
}
