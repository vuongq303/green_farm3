package com.google.android.gms.common.api.a;

import android.os.Looper;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class z {
    private final Set<v<?>> bJU = Collections.newSetFromMap(new WeakHashMap());

    public static <L> v<L> b(L l, Looper looper, String str) {
        com.google.android.gms.common.internal.bf.d(l, "Listener must not be null");
        com.google.android.gms.common.internal.bf.d(looper, "Looper must not be null");
        com.google.android.gms.common.internal.bf.d(str, (Object) "Listener type must not be null");
        return new v<>(looper, l, str);
    }

    public final <L> v<L> a(L l, Looper looper, String str) {
        v<L> vVarB = b(l, looper, str);
        this.bJU.add(vVarB);
        return vVarB;
    }

    public final void release() {
        Iterator<v<?>> it = this.bJU.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.bJU.clear();
    }
}
