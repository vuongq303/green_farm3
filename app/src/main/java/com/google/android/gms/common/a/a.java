package com.google.android.gms.common.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class a<T> {
    private T bMV = null;
    protected final T mDefaultValue;
    protected final String mKey;
    private static final Object sLock = new Object();
    private static b bMT = null;
    private static int zzmv = 0;
    private static String bMU = "com.google.android.providers.gsf.permission.READ_GSERVICES";

    protected a(String str, T t) {
        this.mKey = str;
        this.mDefaultValue = t;
    }

    public static a<Float> a(String str, Float f) {
        return new f(str, f);
    }

    public static a<Integer> a(String str, Integer num) {
        return new e(str, num);
    }

    public static a<Long> a(String str, Long l) {
        return new d(str, l);
    }

    public static a<String> av(String str, String str2) {
        return new g(str, str2);
    }

    public static a<Boolean> r(String str, boolean z) {
        return new c(str, Boolean.valueOf(z));
    }
}
