package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public class bc {

    @GuardedBy("sLock")
    private static boolean bOs;
    private static int bOt;
    private static Object sLock = new Object();
    private static String zzuj;

    public static String aJ(Context context) {
        aL(context);
        return zzuj;
    }

    public static int aK(Context context) {
        aL(context);
        return bOt;
    }

    private static void aL(Context context) {
        synchronized (sLock) {
            if (bOs) {
                return;
            }
            bOs = true;
            try {
                Bundle bundle = com.google.android.gms.common.e.c.aV(context).getApplicationInfo(context.getPackageName(), 128).metaData;
                if (bundle == null) {
                    return;
                }
                zzuj = bundle.getString("com.google.app.id");
                bOt = bundle.getInt(com.google.android.gms.common.m.KEY_METADATA_GOOGLE_PLAY_SERVICES_VERSION);
            } catch (PackageManager.NameNotFoundException e) {
                Log.wtf("MetadataValueReader", "This should never happen.", e);
            }
        }
    }
}
