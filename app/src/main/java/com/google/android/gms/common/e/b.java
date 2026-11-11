package com.google.android.gms.common.e;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.d.s;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private final Context bJi;

    public b(Context context) {
        this.bJi = context;
    }

    public boolean aa(int i, String str) {
        if (s.abA()) {
            try {
                ((AppOpsManager) this.bJi.getSystemService("appops")).checkPackage(i, str);
                return true;
            } catch (SecurityException e) {
                return false;
            }
        }
        String[] packagesForUid = this.bJi.getPackageManager().getPackagesForUid(i);
        if (str == null || packagesForUid == null) {
            return false;
        }
        for (String str2 : packagesForUid) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public boolean abO() {
        String nameForUid;
        if (Binder.getCallingUid() == Process.myUid()) {
            return a.aT(this.bJi);
        }
        if (!s.ax() || (nameForUid = this.bJi.getPackageManager().getNameForUid(Binder.getCallingUid())) == null) {
            return false;
        }
        return this.bJi.getPackageManager().isInstantApp(nameForUid);
    }

    public int checkCallingOrSelfPermission(String str) {
        return this.bJi.checkCallingOrSelfPermission(str);
    }

    public int checkPermission(String str, String str2) {
        return this.bJi.getPackageManager().checkPermission(str, str2);
    }

    public ApplicationInfo getApplicationInfo(String str, int i) {
        return this.bJi.getPackageManager().getApplicationInfo(str, i);
    }

    public PackageInfo getPackageInfo(String str, int i) {
        return this.bJi.getPackageManager().getPackageInfo(str, i);
    }

    public String[] getPackagesForUid(int i) {
        return this.bJi.getPackageManager().getPackagesForUid(i);
    }

    public CharSequence in(String str) {
        return this.bJi.getPackageManager().getApplicationLabel(this.bJi.getPackageManager().getApplicationInfo(str, 0));
    }
}
