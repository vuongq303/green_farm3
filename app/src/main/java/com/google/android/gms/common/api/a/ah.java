package com.google.android.gms.common.api.a;

import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class ah {
    private final Map<g<?>, Boolean> bJW = Collections.synchronizedMap(new WeakHashMap());
    private final Map<com.google.android.gms.f.g<?>, Boolean> bJX = Collections.synchronizedMap(new WeakHashMap());

    private final void a(boolean z, Status status) {
        HashMap map;
        HashMap map2;
        synchronized (this.bJW) {
            map = new HashMap(this.bJW);
        }
        synchronized (this.bJX) {
            map2 = new HashMap(this.bJX);
        }
        for (Map.Entry entry : map.entrySet()) {
            if (z || ((Boolean) entry.getValue()).booleanValue()) {
                ((g) entry.getKey()).zzb(status);
            }
        }
        for (Map.Entry entry2 : map2.entrySet()) {
            if (z || ((Boolean) entry2.getValue()).booleanValue()) {
                ((com.google.android.gms.f.g) entry2.getKey()).h(new com.google.android.gms.common.api.q(status));
            }
        }
    }

    final boolean Zq() {
        return (this.bJW.isEmpty() && this.bJX.isEmpty()) ? false : true;
    }

    public final void Zr() {
        a(false, l.bJc);
    }

    final void a(g<? extends com.google.android.gms.common.api.ae> gVar, boolean z) {
        this.bJW.put(gVar, Boolean.valueOf(z));
        gVar.addStatusListener(new ai(this, gVar));
    }

    final <TResult> void a(com.google.android.gms.f.g<TResult> gVar, boolean z) {
        this.bJX.put(gVar, Boolean.valueOf(z));
        gVar.ZK().a(new aj(this, gVar));
    }

    public final void zzal() {
        a(true, cn.bLL);
    }
}
