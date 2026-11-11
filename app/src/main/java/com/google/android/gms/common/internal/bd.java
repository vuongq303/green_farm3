package com.google.android.gms.common.internal;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class bd {
    public static be C(Object obj) {
        return new be(obj);
    }

    public static boolean b(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static int hashCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }
}
