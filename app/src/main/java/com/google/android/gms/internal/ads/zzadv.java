package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.d.a;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzadv {
    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzc(Context context, boolean z) {
        if (!z) {
            return true;
        }
        int iE = a.E(context, "com.google.android.gms.ads.dynamite");
        return iE != 0 && iE <= a.D(context, "com.google.android.gms.ads.dynamite");
    }
}
