package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzauj {
    private static final CopyOnWriteArrayList<zzaui> zzdhj = new CopyOnWriteArrayList<>();

    public static zzaui zzdx(String str) throws GeneralSecurityException {
        for (zzaui zzauiVar : zzdhj) {
            if (zzauiVar.zzdv(str)) {
                return zzauiVar;
            }
        }
        String strValueOf = String.valueOf(str);
        throw new GeneralSecurityException(strValueOf.length() != 0 ? "No KMS client does support: ".concat(strValueOf) : new String("No KMS client does support: "));
    }
}
