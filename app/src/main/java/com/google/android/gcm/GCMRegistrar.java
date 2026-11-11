package com.google.android.gcm;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class GCMRegistrar {
    private static String Fr;

    static void hC(String str) {
        Log.v("GCMRegistrar", "Setting the name of retry receiver class to " + str);
        Fr = str;
    }

    private GCMRegistrar() {
        throw new UnsupportedOperationException();
    }
}
