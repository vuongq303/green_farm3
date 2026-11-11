package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static volatile a bPL;
    private final List<String> bPN = Collections.EMPTY_LIST;
    private final List<String> bPO = Collections.EMPTY_LIST;
    private final List<String> bPP = Collections.EMPTY_LIST;
    private final List<String> bPQ = Collections.EMPTY_LIST;
    private static final Object bOh = new Object();
    private static boolean bPM = false;

    private a() {
    }

    private static boolean a(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i, boolean z) {
        if (z) {
            ComponentName component = intent.getComponent();
            if (component == null ? false : com.google.android.gms.common.d.d.A(context, component.getPackageName())) {
                Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
                return false;
            }
        }
        return context.bindService(intent, serviceConnection, i);
    }

    public static a aaY() {
        if (bPL == null) {
            synchronized (bOh) {
                if (bPL == null) {
                    bPL = new a();
                }
            }
        }
        return bPL;
    }

    public void a(Context context, ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
    }

    public void a(Context context, ServiceConnection serviceConnection, String str, Intent intent) {
    }

    public boolean a(Context context, Intent intent, ServiceConnection serviceConnection, int i) {
        return a(context, context.getClass().getName(), intent, serviceConnection, i);
    }

    public boolean a(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i) {
        return a(context, str, intent, serviceConnection, i, true);
    }

    public void b(Context context, ServiceConnection serviceConnection) {
    }
}
