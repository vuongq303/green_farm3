package com.google.android.gms.internal.ads;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class zztx {
    private final Object[] mParams;

    zztx(zzjj zzjjVar, String str, int i) {
        this.mParams = com.google.android.gms.ads.internal.zzp.zza((String) zzkb.zzik().zzd(zznk.zzaza), zzjjVar, str, i, null);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zztx) {
            return Arrays.equals(this.mParams, ((zztx) obj).mParams);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.mParams);
    }

    public final String toString() {
        String string = Arrays.toString(this.mParams);
        return new StringBuilder(String.valueOf(string).length() + 24).append("[InterstitialAdPoolKey ").append(string).append("]").toString();
    }
}
