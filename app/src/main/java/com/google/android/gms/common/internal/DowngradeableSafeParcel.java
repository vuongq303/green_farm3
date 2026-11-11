package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable implements ReflectedParcelable {
    private static final Object bNM = new Object();
    private static ClassLoader bNN = null;
    private static Integer bNO = null;
    private boolean bNP = false;

    protected static ClassLoader aaq() {
        ClassLoader classLoader;
        synchronized (bNM) {
            classLoader = bNN;
        }
        return classLoader;
    }

    protected static Integer aar() {
        Integer num;
        synchronized (bNM) {
            num = bNO;
        }
        return num;
    }

    protected static boolean hZ(String str) {
        ClassLoader classLoaderAaq = aaq();
        if (classLoaderAaq == null) {
            return true;
        }
        try {
            return zza(classLoaderAaq.loadClass(str));
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean zza(Class<?> cls) {
        try {
            return SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
        } catch (IllegalAccessException e) {
            return false;
        } catch (NoSuchFieldException e2) {
            return false;
        }
    }

    protected boolean aas() {
        return this.bNP;
    }
}
