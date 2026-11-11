package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzyn {
    private static volatile boolean zzbty = false;
    private static final Class<?> zzbtz = zztv();
    static final zzyn zzbua = new zzyn(true);
    private final Map<Object, Object> zzbub;

    zzyn() {
        this.zzbub = new HashMap();
    }

    private zzyn(boolean z) {
        this.zzbub = Collections.emptyMap();
    }

    private static Class<?> zztv() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzyn zztw() {
        return zzym.zztu();
    }
}
