package com.google.android.gms.common.e;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private static c bRa = new c();
    private b bQZ = null;

    public static b aV(Context context) {
        return bRa.aU(context);
    }

    public synchronized b aU(Context context) {
        if (this.bQZ == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.bQZ = new b(context);
        }
        return this.bQZ;
    }
}
