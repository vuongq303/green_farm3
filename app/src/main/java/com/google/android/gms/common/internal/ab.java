package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

/* JADX INFO: loaded from: classes.dex */
public abstract class ab {
    private static final Object bOh = new Object();
    private static ab bOi;

    public static ab aH(Context context) {
        synchronized (bOh) {
            if (bOi == null) {
                bOi = new br(context.getApplicationContext());
            }
        }
        return bOi;
    }

    public boolean a(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        return a(new ac(componentName, 129), serviceConnection, str);
    }

    protected abstract boolean a(ac acVar, ServiceConnection serviceConnection, String str);

    public boolean a(String str, String str2, int i, ServiceConnection serviceConnection, String str3) {
        return a(new ac(str, str2, i), serviceConnection, str3);
    }

    public void b(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        b(new ac(componentName, 129), serviceConnection, str);
    }

    protected abstract void b(ac acVar, ServiceConnection serviceConnection, String str);

    public void b(String str, String str2, int i, ServiceConnection serviceConnection, String str3) {
        b(new ac(str, str2, i), serviceConnection, str3);
    }
}
