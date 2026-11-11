package com.google.android.gms.internal.ads;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzakb extends zzane {
    public static void v(String str) {
        if (zzqp()) {
            Log.v("Ads", str);
        }
    }

    public static boolean zzqp() {
        if (isLoggable(2)) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzazr)).booleanValue()) {
                return true;
            }
        }
        return false;
    }
}
