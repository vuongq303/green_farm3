package com.google.android.gms.common.api;

import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public abstract class al {

    @GuardedBy("sLock")
    private static final Map<Object, al> bMS = new WeakHashMap();
    private static final Object sLock = new Object();

    public abstract void remove(int i);
}
