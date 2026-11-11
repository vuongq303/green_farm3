package com.google.android.gms.common.d;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Debug;
import android.os.DropBoxManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.Utility;
import com.google.android.gms.common.internal.bf;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    private static boolean bQq;
    private static boolean zzzf;
    private static final String[] bQo = {"android.", "com.android.", "dalvik.", "java.", "javax."};
    private static DropBoxManager bQp = null;
    private static boolean zzze = false;
    private static int bQr = -1;

    @GuardedBy("CrashUtils.class")
    private static int bQs = 0;

    @GuardedBy("CrashUtils.class")
    private static int zzzj = 0;

    /* JADX WARN: Multi-variable type inference failed */
    private static synchronized String a(Context context, String str, String str2, int i) {
        StringBuilder sb;
        int i2;
        InputStreamReader inputStreamReader;
        char[] cArr;
        sb = new StringBuilder(1024);
        sb.append("Process: ").append(x.il(str2)).append("\n");
        sb.append("Package: com.google.android.gms");
        int i3 = 12451009;
        String strConcat = "12.4.51 (020308-{{cl}})";
        if (abq()) {
            try {
                PackageInfo packageInfo = com.google.android.gms.common.e.c.aV(context).getPackageInfo(context.getPackageName(), 0);
                i3 = packageInfo.versionCode;
                if (packageInfo.versionName != null) {
                    strConcat = packageInfo.versionName;
                }
            } catch (Exception e) {
                Log.w("CrashUtils", "Error while trying to get the package information! Using static version.", e);
            }
        }
        sb.append(" v").append(i3);
        if (!TextUtils.isEmpty(strConcat)) {
            if (strConcat.contains("(") && !strConcat.contains(")")) {
                if (strConcat.endsWith("-")) {
                    strConcat = String.valueOf(strConcat).concat("111111111");
                }
                strConcat = String.valueOf(strConcat).concat(")");
            }
            sb.append(" (").append(strConcat).append(")");
        }
        sb.append("\n");
        sb.append("Build: ").append(Build.FINGERPRINT).append("\n");
        if (Debug.isDebuggerConnected()) {
            sb.append("Debugger: Connected\n");
        }
        if (i != 0) {
            sb.append("DD-EDD: ").append(i).append("\n");
        }
        sb.append("\n");
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        if (abq()) {
            i2 = bQr >= 0 ? bQr : Settings.Secure.getInt(context.getContentResolver(), "logcat_for_system_app_crash", 0);
        } else {
            i2 = 0;
        }
        if (i2 > 0) {
            sb.append("\n");
            FileReader fileReader = 0;
            try {
                try {
                    try {
                        Process processStart = new ProcessBuilder("/system/bin/logcat", "-v", "time", "-b", "events", "-b", "system", "-b", "main", "-b", "crash", "-t", String.valueOf(i2)).redirectErrorStream(true).start();
                        try {
                            processStart.getOutputStream().close();
                        } catch (IOException e2) {
                        }
                        try {
                            try {
                                processStart.getErrorStream().close();
                                while (true) {
                                    int i4 = inputStreamReader.read(cArr);
                                    if (i4 > 0) {
                                        sb.append(cArr, 0, i4);
                                    } else {
                                        try {
                                            break;
                                        } catch (IOException e3) {
                                        }
                                    }
                                }
                            } catch (IOException e4) {
                            }
                            cArr = new char[Utility.DEFAULT_STREAM_BUFFER_SIZE];
                            inputStreamReader.close();
                        } catch (IOException e5) {
                            e = e5;
                            Log.e("CrashUtils", "Error running logcat", e);
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                } catch (IOException e6) {
                                }
                            }
                        }
                        inputStreamReader = new InputStreamReader(processStart.getInputStream());
                    } catch (Throwable th) {
                        th = th;
                        if (fileReader != 0) {
                            try {
                                fileReader.close();
                            } catch (IOException e7) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException e8) {
                    e = e8;
                    inputStreamReader = null;
                }
            } catch (Throwable th2) {
                th = th2;
                fileReader = "\n";
            }
        }
        return sb.toString();
    }

    private static synchronized boolean a(Context context, String str, String str2, int i, Throwable th) {
        boolean z;
        bf.checkNotNull(context);
        if (!abm() || x.im(str)) {
            z = false;
        } else {
            int iHashCode = str.hashCode();
            int iHashCode2 = th == null ? zzzj : th.hashCode();
            if (bQs == iHashCode && zzzj == iHashCode2) {
                z = false;
            } else {
                bQs = iHashCode;
                zzzj = iHashCode2;
                DropBoxManager dropBoxManager = bQp != null ? bQp : (DropBoxManager) context.getSystemService("dropbox");
                if (dropBoxManager == null || !dropBoxManager.isTagEnabled("system_app_crash")) {
                    z = false;
                } else {
                    dropBoxManager.addText("system_app_crash", a(context, str, str2, i));
                    z = true;
                }
            }
        }
        return z;
    }

    public static boolean a(Context context, Throwable th) {
        return a(context, th, 536870912);
    }

    public static boolean a(Context context, Throwable th, int i) throws Exception {
        boolean zAbq;
        try {
            bf.checkNotNull(context);
            bf.checkNotNull(th);
            if (!abm()) {
                return false;
            }
            if (abq() || (th = b(th)) != null) {
                return a(context, Log.getStackTraceString(th), u.abF(), i, th);
            }
            return false;
        } catch (Exception e) {
            try {
                zAbq = abq();
            } catch (Exception e2) {
                Log.e("CrashUtils", "Error determining which process we're running in!", e2);
                zAbq = false;
            }
            if (zAbq) {
                throw e;
            }
            Log.e("CrashUtils", "Error adding exception to DropBox!", e);
            return false;
        }
    }

    private static boolean abm() {
        if (zzze) {
            return zzzf;
        }
        return false;
    }

    private static boolean abq() {
        if (zzze) {
            return bQq;
        }
        return false;
    }

    private static synchronized Throwable b(Throwable th) {
        Throwable th2;
        boolean z;
        LinkedList linkedList = new LinkedList();
        while (th != null) {
            linkedList.push(th);
            th = th.getCause();
        }
        th2 = null;
        z = false;
        while (!linkedList.isEmpty()) {
            Throwable th3 = (Throwable) linkedList.pop();
            StackTraceElement[] stackTrace = th3.getStackTrace();
            ArrayList arrayList = new ArrayList();
            arrayList.add(new StackTraceElement(th3.getClass().getName(), "<filtered>", "<filtered>", 1));
            boolean z2 = z;
            for (StackTraceElement stackTraceElement : stackTrace) {
                String className = stackTraceElement.getClassName();
                String fileName = stackTraceElement.getFileName();
                boolean z3 = !TextUtils.isEmpty(fileName) && fileName.startsWith(":com.google.android.gms");
                z2 |= z3;
                if (!z3 && !ih(className)) {
                    stackTraceElement = new StackTraceElement("<filtered>", "<filtered>", "<filtered>", 1);
                }
                arrayList.add(stackTraceElement);
            }
            th2 = th2 == null ? new Throwable("<filtered>") : new Throwable("<filtered>", th2);
            th2.setStackTrace((StackTraceElement[]) arrayList.toArray(new StackTraceElement[0]));
            z = z2;
        }
        return !z ? null : th2;
    }

    public static boolean ih(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String str2 : bQo) {
            if (str.startsWith(str2)) {
                return true;
            }
        }
        return false;
    }
}
