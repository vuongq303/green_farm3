package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.e.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzct {
    static Object lock = new Object();
    static a zzabg;
    private static Boolean zzqo;

    public static void onReceive(Context context, Intent intent) {
        zzcm zzcmVarZzbu = zzat.zzc(context).zzbu();
        if (intent == null) {
            zzcmVarZzbu.zzt("AnalyticsReceiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzcmVarZzbu.zza("Local AnalyticsReceiver got", action);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zZze = zzcu.zze(context);
            Intent intent2 = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent2.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (lock) {
                context.startService(intent2);
                if (zZze) {
                    try {
                        if (zzabg == null) {
                            a aVar = new a(context, 1, "Analytics WakeLock");
                            zzabg = aVar;
                            aVar.setReferenceCounted(false);
                        }
                        zzabg.acquire(1000L);
                    } catch (SecurityException e) {
                        zzcmVarZzbu.zzt("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                }
            }
        }
    }

    public static boolean zza(Context context) {
        bf.checkNotNull(context);
        if (zzqo != null) {
            return zzqo.booleanValue();
        }
        boolean zZza = zzdd.zza(context, "com.google.android.gms.analytics.AnalyticsReceiver", false);
        zzqo = Boolean.valueOf(zZza);
        return zZza;
    }
}
