package com.google.android.gms.common.d;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class f {
    @Deprecated
    public static <T> List<T> D(T t) {
        return Collections.singletonList(t);
    }

    @Deprecated
    public static <T> Set<T> E(T t) {
        return Collections.singleton(t);
    }

    private static <T> Set<T> O(int i, boolean z) {
        return i <= (z ? 128 : 256) ? new android.support.v4.e.c(i) : new HashSet(i, z ? 0.75f : 1.0f);
    }

    private static <K, V> Map<K, V> P(int i, boolean z) {
        return i <= (z ? 128 : 256) ? new android.support.v4.e.a(i) : new HashMap(i, z ? 0.75f : 1.0f);
    }

    private static <K, V> Map<K, V> a(int i, boolean z, K[] kArr, V[] vArr) {
        Map<K, V> mapP = P(i, z);
        a(mapP, kArr, vArr);
        return mapP;
    }

    public static <K, V> Map<K, V> a(K k, V v, K k2, V v2, K k3, V v3) {
        Map mapP = P(3, false);
        mapP.put(k, v);
        mapP.put(k2, v2);
        mapP.put(k3, v3);
        return Collections.unmodifiableMap(mapP);
    }

    private static <T> Set<T> a(int i, boolean z, T[] tArr) {
        Set<T> setO = O(i, z);
        Collections.addAll(setO, tArr);
        return setO;
    }

    @Deprecated
    public static <T> Set<T> a(T t, T t2, T t3, T t4) {
        Set setO = O(4, false);
        setO.add(t);
        setO.add(t2);
        setO.add(t3);
        setO.add(t4);
        return Collections.unmodifiableSet(setO);
    }

    private static <K, V> void a(Map<K, V> map, K[] kArr, V[] vArr) {
        for (int i = 0; i < kArr.length; i++) {
            map.put(kArr[i], vArr[i]);
        }
    }

    @Deprecated
    public static <T> List<T> abn() {
        return Collections.emptyList();
    }

    @Deprecated
    public static <T> Set<T> abo() {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> abp() {
        return Collections.emptyMap();
    }

    private static <K, V> void b(K[] kArr, V[] vArr) {
        if (kArr.length != vArr.length) {
            int length = kArr.length;
            throw new IllegalArgumentException(new StringBuilder(66).append("Key and values array lengths not equal: ").append(length).append(" != ").append(vArr.length).toString());
        }
    }

    public static <K, V> Map<K, V> c(K[] kArr, V[] vArr) {
        b(kArr, vArr);
        switch (kArr.length) {
            case 0:
                return abp();
            case 1:
                return f(kArr[0], vArr[0]);
            default:
                return Collections.unmodifiableMap(a(kArr.length, false, (Object[]) kArr, (Object[]) vArr));
        }
    }

    @Deprecated
    public static <T> Set<T> c(T t, T t2, T t3) {
        Set setO = O(3, false);
        setO.add(t);
        setO.add(t2);
        setO.add(t3);
        return Collections.unmodifiableSet(setO);
    }

    @Deprecated
    public static <T> Set<T> e(T t, T t2) {
        Set setO = O(2, false);
        setO.add(t);
        setO.add(t2);
        return Collections.unmodifiableSet(setO);
    }

    public static <K, V> Map<K, V> f(K k, V v) {
        return Collections.singletonMap(k, v);
    }

    @Deprecated
    public static <T> List<T> w(T... tArr) {
        switch (tArr.length) {
            case 0:
                return abn();
            case 1:
                return D(tArr[0]);
            default:
                return Collections.unmodifiableList(Arrays.asList(tArr));
        }
    }

    @Deprecated
    public static <T> Set<T> x(T... tArr) {
        switch (tArr.length) {
            case 0:
                return abo();
            case 1:
                return E(tArr[0]);
            case 2:
                return e(tArr[0], tArr[1]);
            case 3:
                return c(tArr[0], tArr[1], tArr[2]);
            case 4:
                return a(tArr[0], tArr[1], tArr[2], tArr[3]);
            default:
                return Collections.unmodifiableSet(a(tArr.length, false, (Object[]) tArr));
        }
    }
}
