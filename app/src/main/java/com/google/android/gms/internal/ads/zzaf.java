package com.google.android.gms.internal.ads;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class zzaf {
    private static String TAG = "Volley";
    public static boolean DEBUG = Log.isLoggable("Volley", 2);

    final class zza {
        public static final boolean zzbk = zzaf.DEBUG;
        private final List<zzag> zzbl = new ArrayList();
        private boolean zzbm = false;

        zza() {
        }

        protected final void finalize() {
            if (this.zzbm) {
                return;
            }
            zzc("Request on the loose");
            zzaf.e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
        }

        public final synchronized void zza(String str, long j) {
            if (this.zzbm) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.zzbl.add(new zzag(str, j, SystemClock.elapsedRealtime()));
        }

        public final synchronized void zzc(String str) {
            this.zzbm = true;
            long j = this.zzbl.size() == 0 ? 0L : this.zzbl.get(this.zzbl.size() - 1).time - this.zzbl.get(0).time;
            if (j > 0) {
                long j2 = this.zzbl.get(0).time;
                zzaf.d("(%-4d ms) %s", Long.valueOf(j), str);
                long j3 = j2;
                for (zzag zzagVar : this.zzbl) {
                    long j4 = zzagVar.time;
                    zzaf.d("(+%-4d) [%2d] %s", Long.valueOf(j4 - j3), Long.valueOf(zzagVar.zzbn), zzagVar.name);
                    j3 = j4;
                }
            }
        }
    }

    public static void d(String str, Object... objArr) {
        Log.d(TAG, zza(str, objArr));
    }

    public static void e(String str, Object... objArr) {
        Log.e(TAG, zza(str, objArr));
    }

    public static void v(String str, Object... objArr) {
        if (DEBUG) {
            Log.v(TAG, zza(str, objArr));
        }
    }

    private static String zza(String str, Object... objArr) {
        String string;
        if (objArr != null) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {
            if (!stackTrace[i].getClass().equals(zzaf.class)) {
                String className = stackTrace[i].getClassName();
                String strSubstring = className.substring(className.lastIndexOf(46) + 1);
                String strSubstring2 = strSubstring.substring(strSubstring.lastIndexOf(36) + 1);
                String methodName = stackTrace[i].getMethodName();
                string = new StringBuilder(String.valueOf(strSubstring2).length() + 1 + String.valueOf(methodName).length()).append(strSubstring2).append(".").append(methodName).toString();
                return String.format(Locale.US, "[%d] %s: %s", Long.valueOf(Thread.currentThread().getId()), string, str);
            }
        }
        string = "<unknown>";
        return String.format(Locale.US, "[%d] %s: %s", Long.valueOf(Thread.currentThread().getId()), string, str);
    }

    public static void zza(Throwable th, String str, Object... objArr) {
        Log.e(TAG, zza(str, objArr), th);
    }
}
