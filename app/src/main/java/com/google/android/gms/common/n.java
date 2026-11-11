package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.internal.bf;
import javax.annotation.CheckReturnValue;

/* JADX INFO: loaded from: classes.dex */
@CheckReturnValue
public class n {
    private static n bIp;
    private final Context mContext;

    private n(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private static i a(PackageInfo packageInfo, i... iVarArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        s sVar = new s(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < iVarArr.length; i++) {
            if (iVarArr[i].equals(sVar)) {
                return iVarArr[i];
            }
        }
        return null;
    }

    public static n aB(Context context) {
        bf.checkNotNull(context);
        synchronized (n.class) {
            if (bIp == null) {
                h.init(context);
                bIp = new n(context);
            }
        }
        return bIp;
    }

    private final x b(PackageInfo packageInfo) {
        boolean zHonorsDebugCertificates = m.honorsDebugCertificates(this.mContext);
        if (packageInfo == null) {
            return x.io("null pkg");
        }
        if (packageInfo.signatures.length != 1) {
            return x.io("single cert required");
        }
        s sVar = new s(packageInfo.signatures[0].toByteArray());
        String str = packageInfo.packageName;
        x xVarA = h.a(str, sVar, zHonorsDebugCertificates);
        if (!xVarA.bRg || packageInfo.applicationInfo == null || (packageInfo.applicationInfo.flags & 2) == 0) {
            return xVarA;
        }
        return (!zHonorsDebugCertificates || h.a(str, sVar, false).bRg) ? x.io("debuggable release cert app rejected") : xVarA;
    }

    private final x hT(String str) {
        try {
            return b(com.google.android.gms.common.e.c.aV(this.mContext).getPackageInfo(str, 64));
        } catch (PackageManager.NameNotFoundException e) {
            String strValueOf = String.valueOf(str);
            return x.io(strValueOf.length() != 0 ? "no pkg ".concat(strValueOf) : new String("no pkg "));
        }
    }

    private final x nw(int i) {
        String[] packagesForUid = com.google.android.gms.common.e.c.aV(this.mContext).getPackagesForUid(i);
        if (packagesForUid == null || packagesForUid.length == 0) {
            return x.io("no pkgs");
        }
        x xVarHT = null;
        for (String str : packagesForUid) {
            xVarHT = hT(str);
            if (xVarHT.bRg) {
                return xVarHT;
            }
        }
        return xVarHT;
    }

    public boolean a(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (a(packageInfo, false)) {
            return true;
        }
        if (!a(packageInfo, true)) {
            return false;
        }
        if (m.honorsDebugCertificates(this.mContext)) {
            return true;
        }
        Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        return false;
    }

    public boolean a(PackageInfo packageInfo, boolean z) {
        if (packageInfo != null && packageInfo.signatures != null) {
            if ((z ? a(packageInfo, u.bRe) : a(packageInfo, u.bRe[0])) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean nv(int i) {
        x xVarNw = nw(i);
        xVarNw.zzi();
        return xVarNw.bRg;
    }
}
