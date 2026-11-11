package com.a.a.a.a;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static b cdC = new b();

    public static boolean b(String str, Context context) {
        return cdC.c(str, context.getApplicationContext());
    }

    public static String getVersion() {
        return cdC.ahA();
    }

    public static boolean isActive() {
        return cdC.b();
    }
}
