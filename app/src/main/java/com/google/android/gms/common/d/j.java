package com.google.android.gms.common.d;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.google.firebase.analytics.FirebaseAnalytics;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    private static long bQx;
    private static final IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static float bQy = Float.NaN;

    public static boolean a(PowerManager powerManager) {
        return s.abB() ? powerManager.isInteractive() : powerManager.isScreenOn();
    }

    public static int aQ(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent intentRegisterReceiver = context.getApplicationContext().registerReceiver(null, filter);
        boolean z = ((intentRegisterReceiver == null ? 0 : intentRegisterReceiver.getIntExtra("plugged", 0)) & 7) != 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        return ((a(powerManager) ? 1 : 0) << 1) | (z ? 1 : 0);
    }

    public static synchronized float aR(Context context) {
        float f;
        if (SystemClock.elapsedRealtime() - bQx >= 60000 || Float.isNaN(bQy)) {
            Intent intentRegisterReceiver = context.getApplicationContext().registerReceiver(null, filter);
            if (intentRegisterReceiver != null) {
                bQy = intentRegisterReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1) / intentRegisterReceiver.getIntExtra("scale", -1);
            }
            bQx = SystemClock.elapsedRealtime();
            f = bQy;
        } else {
            f = bQy;
        }
        return f;
    }
}
