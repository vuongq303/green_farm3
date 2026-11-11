package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: GCMBroadcastReceiver.java */
/* JADX INFO: loaded from: classes.dex */
public class f extends BroadcastReceiver {
    private static boolean Fm = false;
    private final g Fn = new g("GCMBroadcastReceiver", "[" + getClass().getName() + "]: ");

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.Fn.a(2, "onReceive: %s", intent.getAction());
        if (!Fm) {
            Fm = true;
            h.j(context, getClass().getName());
        }
        String strZ = z(context);
        this.Fn.a(2, "GCM IntentService class: %s", strZ);
        if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
            if (intent.getStringExtra("message_type") == null) {
                com.gameloft.android.ANMP.GloftGF2F.g.b(context, intent);
            }
        } else {
            e.a(context, intent, strZ);
        }
        setResult(-1, null, null);
    }

    protected String z(Context context) {
        return x(context);
    }

    static final String x(Context context) {
        return context.getPackageName() + ".GCMIntentService";
    }
}
