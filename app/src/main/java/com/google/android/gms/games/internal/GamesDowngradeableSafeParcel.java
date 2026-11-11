package com.google.android.gms.games.internal;

import com.google.android.gms.common.internal.DowngradeableSafeParcel;

/* JADX INFO: loaded from: classes.dex */
public abstract class GamesDowngradeableSafeParcel extends DowngradeableSafeParcel {
    protected static boolean f(Integer num) {
        if (num == null) {
            return false;
        }
        return com.google.android.gms.common.d.k.od(num.intValue());
    }
}
