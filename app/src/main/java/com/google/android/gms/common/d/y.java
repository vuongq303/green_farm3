package com.google.android.gms.common.d;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class y {
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
