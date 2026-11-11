package com.google.android.gms.common.d;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.WorkSource;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ab {
    private static final int bQL = Process.myUid();
    private static final Method bQM = abH();
    private static final Method bQN = abI();
    private static final Method bQO = abJ();
    private static final Method bQP = abK();
    private static final Method bQQ = abL();
    private static final Method bQR = abM();
    private static final Method bQS = abN();

    public static WorkSource B(Context context, String str) {
        if (context == null || context.getPackageManager() == null || str == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = com.google.android.gms.common.e.c.aV(context).getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return Z(applicationInfo.uid, str);
            }
            String strValueOf = String.valueOf(str);
            Log.e("WorkSourceUtil", strValueOf.length() != 0 ? "Could not get applicationInfo from package: ".concat(strValueOf) : new String("Could not get applicationInfo from package: "));
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            String strValueOf2 = String.valueOf(str);
            Log.e("WorkSourceUtil", strValueOf2.length() != 0 ? "Could not find package: ".concat(strValueOf2) : new String("Could not find package: "));
            return null;
        }
    }

    private static int C(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = com.google.android.gms.common.e.c.aV(context).getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return applicationInfo.uid;
            }
            String strValueOf = String.valueOf(str);
            Log.e("WorkSourceUtil", strValueOf.length() != 0 ? "Could not get applicationInfo from package: ".concat(strValueOf) : new String("Could not get applicationInfo from package: "));
            return -1;
        } catch (PackageManager.NameNotFoundException e) {
            String strValueOf2 = String.valueOf(str);
            Log.e("WorkSourceUtil", strValueOf2.length() != 0 ? "Could not find package: ".concat(strValueOf2) : new String("Could not find package: "));
            return -1;
        }
    }

    public static WorkSource Z(int i, String str) {
        WorkSource workSource = new WorkSource();
        a(workSource, i, str);
        return workSource;
    }

    public static int a(WorkSource workSource) {
        if (bQO != null) {
            try {
                return ((Integer) bQO.invoke(workSource, new Object[0])).intValue();
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }

    public static String a(WorkSource workSource, int i) {
        if (bQQ != null) {
            try {
                return (String) bQQ.invoke(workSource, Integer.valueOf(i));
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return null;
    }

    public static void a(WorkSource workSource, int i, String str) {
        if (bQN != null) {
            if (str == null) {
                str = "";
            }
            try {
                bQN.invoke(workSource, Integer.valueOf(i), str);
                return;
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
                return;
            }
        }
        if (bQM != null) {
            try {
                bQM.invoke(workSource, Integer.valueOf(i));
            } catch (Exception e2) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e2);
            }
        }
    }

    public static boolean aS(Context context) {
        return (context == null || context.getPackageManager() == null || com.google.android.gms.common.e.c.aV(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) != 0) ? false : true;
    }

    private static Method abH() {
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method abI() {
        if (!s.abz()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE, String.class);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method abJ() {
        try {
            return WorkSource.class.getMethod("size", new Class[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method abK() {
        try {
            return WorkSource.class.getMethod("get", Integer.TYPE);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method abL() {
        if (!s.abz()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod("getName", Integer.TYPE);
        } catch (Exception e) {
            return null;
        }
    }

    private static final Method abM() {
        if (!s.ay()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod("createWorkChain", new Class[0]);
        } catch (Exception e) {
            Log.w("WorkSourceUtil", "Missing WorkChain API createWorkChain", e);
            return null;
        }
    }

    private static final Method abN() {
        if (!s.ay()) {
            return null;
        }
        try {
            return Class.forName("android.os.WorkSource$WorkChain").getMethod("addNode", Integer.TYPE, String.class);
        } catch (Exception e) {
            Log.w("WorkSourceUtil", "Missing WorkChain class", e);
            return null;
        }
    }

    public static List<String> b(WorkSource workSource) {
        int iA = workSource == null ? 0 : a(workSource);
        if (iA == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < iA; i++) {
            String strA = a(workSource, i);
            if (!x.im(strA)) {
                arrayList.add(strA);
            }
        }
        return arrayList;
    }

    public static WorkSource g(Context context, String str, String str2) {
        WorkSource workSource = null;
        if (context == null || context.getPackageManager() == null || str2 == null || str == null) {
            Log.w("WorkSourceUtil", "Unexpected null arguments");
        } else {
            int iC = C(context, str);
            if (iC >= 0) {
                workSource = new WorkSource();
                if (bQR == null || bQS == null) {
                    a(workSource, iC, str);
                } else {
                    try {
                        Object objInvoke = bQR.invoke(workSource, new Object[0]);
                        if (iC != bQL) {
                            bQS.invoke(objInvoke, Integer.valueOf(iC), str);
                        }
                        bQS.invoke(objInvoke, Integer.valueOf(bQL), str2);
                    } catch (Exception e) {
                        Log.w("WorkSourceUtil", "Unable to assign chained blame through WorkSource", e);
                    }
                }
            }
        }
        return workSource;
    }
}
