package com.google.android.gms.common.internal;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class ae {
    public static final int bOm = 15;
    private static final String zzub = null;
    private final String bOn;
    private final String zzud;

    public ae(String str) {
        this(str, null);
    }

    public ae(String str, String str2) {
        bf.d(str, (Object) "log tag cannot be null");
        bf.b(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", str, 23);
        this.bOn = str;
        if (str2 == null || str2.length() <= 0) {
            this.zzud = null;
        } else {
            this.zzud = str2;
        }
    }

    private final String ic(String str) {
        return this.zzud == null ? str : this.zzud.concat(str);
    }

    public final void a(String str, String str2, Throwable th) {
        if (nO(5)) {
            Log.w(str, ic(str2), th);
        }
    }

    public final void ax(String str, String str2) {
        if (nO(5)) {
            Log.w(str, ic(str2));
        }
    }

    public final void ay(String str, String str2) {
        if (nO(6)) {
            Log.e(str, ic(str2));
        }
    }

    public final void b(String str, String str2, Throwable th) {
        if (nO(6)) {
            Log.e(str, ic(str2), th);
        }
    }

    public final boolean nO(int i) {
        return Log.isLoggable(this.bOn, i);
    }
}
