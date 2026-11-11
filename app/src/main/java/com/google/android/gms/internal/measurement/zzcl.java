package com.google.android.gms.internal.measurement;

import android.util.Log;
import com.google.android.gms.a.c;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class zzcl {
    private static volatile c zzaap = new zzbv();

    public static c getLogger() {
        return zzaap;
    }

    private static boolean isLoggable(int i) {
        return zzaap != null && zzaap.getLogLevel() <= i;
    }

    public static void setLogger(c cVar) {
        zzaap = cVar;
    }

    public static void v(String str) {
        zzcm zzcmVarZzes = zzcm.zzes();
        if (zzcmVarZzes != null) {
            zzcmVarZzes.zzq(str);
        } else if (isLoggable(0)) {
            Log.v(zzcc.zzyh.get(), str);
        }
        c cVar = zzaap;
        if (cVar != null) {
            cVar.verbose(str);
        }
    }

    public static void zzab(String str) {
        zzcm zzcmVarZzes = zzcm.zzes();
        if (zzcmVarZzes != null) {
            zzcmVarZzes.zzt(str);
        } else if (isLoggable(2)) {
            Log.w(zzcc.zzyh.get(), str);
        }
        c cVar = zzaap;
        if (cVar != null) {
            cVar.warn(str);
        }
    }

    public static void zzf(String str, Object obj) {
        String string;
        zzcm zzcmVarZzes = zzcm.zzes();
        if (zzcmVarZzes != null) {
            zzcmVarZzes.zze(str, obj);
        } else if (isLoggable(3)) {
            if (obj != null) {
                String strValueOf = String.valueOf(obj);
                string = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(strValueOf).length()).append(str).append(":").append(strValueOf).toString();
            } else {
                string = str;
            }
            Log.e(zzcc.zzyh.get(), string);
        }
        c cVar = zzaap;
        if (cVar != null) {
            cVar.error(str);
        }
    }
}
