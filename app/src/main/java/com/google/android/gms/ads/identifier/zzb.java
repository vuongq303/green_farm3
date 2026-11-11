package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.common.m;

/* JADX INFO: loaded from: classes.dex */
public final class zzb {
    private SharedPreferences bzO;

    public zzb(Context context) {
        try {
            Context remoteContext = m.getRemoteContext(context);
            this.bzO = remoteContext == null ? null : remoteContext.getSharedPreferences("google_ads_flags", 0);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while getting SharedPreferences ", th);
            this.bzO = null;
        }
    }

    public final boolean getBoolean(String str, boolean z) {
        try {
            if (this.bzO == null) {
                return false;
            }
            return this.bzO.getBoolean(str, false);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return false;
        }
    }

    final float getFloat(String str, float f) {
        try {
            if (this.bzO == null) {
                return 0.0f;
            }
            return this.bzO.getFloat(str, 0.0f);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return 0.0f;
        }
    }

    final String getString(String str, String str2) {
        try {
            return this.bzO == null ? str2 : this.bzO.getString(str, str2);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return str2;
        }
    }
}
