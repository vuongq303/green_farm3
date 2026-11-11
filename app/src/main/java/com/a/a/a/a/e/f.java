package com.a.a.a.a.e;

import android.text.TextUtils;
import com.a.a.a.a.b.i;

/* JADX INFO: loaded from: classes.dex */
public class f {
    public static void a() {
        if (!com.a.a.a.a.a.isActive()) {
            throw new IllegalStateException("Method called before OMID activation");
        }
    }

    public static void a(com.a.a.a.a.b.f fVar) {
        if (fVar.equals(com.a.a.a.a.b.f.NONE)) {
            throw new IllegalArgumentException("Impression owner is none");
        }
    }

    public static void aC(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(str2);
        }
    }

    public static void b(i iVar) {
        if (iVar.f()) {
            throw new IllegalStateException("AdSession is finished");
        }
    }

    public static void c(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void d(i iVar) {
        if (iVar.ahQ().aij() != null) {
            throw new IllegalStateException("AdEvents already exists for AdSession");
        }
    }

    public static void e(i iVar) {
        if (!iVar.g()) {
            throw new IllegalStateException("Impression event is not expected from the Native AdSession");
        }
    }

    public static void e(String str, int i, String str2) {
        if (str.length() > i) {
            throw new IllegalArgumentException(str2);
        }
    }
}
