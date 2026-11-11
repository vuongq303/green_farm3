package com.google.android.gms.internal.measurement;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public final class zzcb {
    public static int version() {
        try {
            return Integer.parseInt(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            zzcl.zzf("Invalid version number", Build.VERSION.SDK);
            return 0;
        }
    }
}
