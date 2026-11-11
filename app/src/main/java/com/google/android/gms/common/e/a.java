package com.google.android.gms.common.e;

import android.content.Context;
import com.google.android.gms.common.d.s;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static Context bQX;
    private static Boolean bQY;

    public static synchronized boolean aT(Context context) {
        boolean zBooleanValue;
        Context applicationContext = context.getApplicationContext();
        if (bQX == null || bQY == null || bQX != applicationContext) {
            bQY = null;
            if (s.ax()) {
                bQY = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
            } else {
                try {
                    context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                    bQY = true;
                } catch (ClassNotFoundException e) {
                    bQY = false;
                }
            }
            bQX = applicationContext;
            zBooleanValue = bQY.booleanValue();
        } else {
            zBooleanValue = bQY.booleanValue();
        }
        return zBooleanValue;
    }
}
