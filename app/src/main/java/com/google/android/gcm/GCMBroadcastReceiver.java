package com.google.android.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class GCMBroadcastReceiver extends BroadcastReceiver {
    private static boolean Fm = false;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.v("GCMBroadcastReceiver", "onReceive: " + intent.getAction());
        if (!Fm) {
            Fm = true;
            String name = getClass().getName();
            if (!name.equals(GCMBroadcastReceiver.class.getName())) {
                GCMRegistrar.hC(name);
            }
        }
        String strZ = z(context);
        Log.v("GCMBroadcastReceiver", "GCM IntentService class: " + strZ);
        GCMBaseIntentService.a(context, intent, strZ);
        setResult(-1, null, null);
    }

    protected String z(Context context) {
        return x(context);
    }

    static final String x(Context context) {
        return context.getPackageName() + ".GCMIntentService";
    }
}
