package com.google.android.gms.internal.ads;

import android.content.Context;
import android.text.TextUtils;
import java.math.BigInteger;
import java.util.Locale;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzajw {
    private static final Object sLock = new Object();

    @GuardedBy("sLock")
    private static String zzcqq;

    public static String zzc(Context context, String str, String str2) {
        String str3;
        synchronized (sLock) {
            if (zzcqq != null || TextUtils.isEmpty(str)) {
                str3 = zzcqq;
            } else {
                try {
                    ClassLoader classLoader = context.createPackageContext(str2, 3).getClassLoader();
                    Class<?> cls = Class.forName("com.google.ads.mediation.MediationAdapter", false, classLoader);
                    BigInteger bigInteger = new BigInteger(new byte[1]);
                    String[] strArrSplit = str.split(",");
                    for (int i = 0; i < strArrSplit.length; i++) {
                        com.google.android.gms.ads.internal.zzbv.zzek();
                        if (zzakk.zza(classLoader, cls, strArrSplit[i])) {
                            bigInteger = bigInteger.setBit(i);
                        }
                    }
                    zzcqq = String.format(Locale.US, "%X", bigInteger);
                } catch (Throwable th) {
                    zzcqq = "err";
                }
                str3 = zzcqq;
            }
            throw th;
        }
        return str3;
    }

    public static String zzqn() {
        String str;
        synchronized (sLock) {
            str = zzcqq;
        }
        return str;
    }
}
