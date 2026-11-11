package com.c.a.a.a.g;

import android.os.Build;
import android.view.View;

/* JADX INFO: compiled from: AvidViewUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class e {
    public static boolean ad(View view) {
        if (view.getVisibility() != 0) {
            return false;
        }
        return Build.VERSION.SDK_INT < 11 || ((double) view.getAlpha()) > 0.0d;
    }
}
