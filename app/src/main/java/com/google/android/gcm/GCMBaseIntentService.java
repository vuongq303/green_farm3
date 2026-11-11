package com.google.android.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class GCMBaseIntentService extends IntentService {
    private static PowerManager.WakeLock Fg;
    private final String[] Fh;
    private static final Object LOCK = GCMBaseIntentService.class;
    private static int Fi = 0;
    private static final Random Fj = new Random();
    private static final int Fk = (int) TimeUnit.SECONDS.toMillis(3600);
    private static final String Fl = Long.toBinaryString(Fj.nextLong());

    protected GCMBaseIntentService() {
        this(getName("DynamicSenderIds"), null);
    }

    private GCMBaseIntentService(String str, String[] strArr) {
        super(str);
        this.Fh = strArr;
    }

    private static String getName(String str) {
        StringBuilder sbAppend = new StringBuilder().append("GCMIntentService-").append(str).append("-");
        int i = Fi + 1;
        Fi = i;
        String string = sbAppend.append(i).toString();
        Log.v("GCMBaseIntentService", "Intent service name: " + string);
        return string;
    }

    static void a(Context context, Intent intent, String str) {
        synchronized (LOCK) {
            if (Fg == null) {
                Fg = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "GCM_LIB");
            }
        }
        Log.v("GCMBaseIntentService", "Acquiring wakelock");
        Fg.acquire();
        intent.setClassName(context, str);
        context.startService(intent);
    }
}
