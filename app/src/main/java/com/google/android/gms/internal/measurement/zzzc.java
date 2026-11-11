package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzzc<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zzbvh;

    public zzzc(Iterator<Map.Entry<K, Object>> it) {
        this.zzbvh = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzbvh.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, Object> next = this.zzbvh.next();
        return next.getValue() instanceof zzyz ? new zzzb(next) : next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zzbvh.remove();
    }
}
