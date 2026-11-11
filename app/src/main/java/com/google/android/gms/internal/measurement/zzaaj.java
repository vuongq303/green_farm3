package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: loaded from: classes.dex */
final class zzaaj<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private final /* synthetic */ zzaad zzbwq;
    private boolean zzbwr;
    private Iterator<Map.Entry<K, V>> zzbws;

    private zzaaj(zzaad zzaadVar) {
        this.zzbwq = zzaadVar;
        this.pos = -1;
    }

    /* synthetic */ zzaaj(zzaad zzaadVar, zzaae zzaaeVar) {
        this(zzaadVar);
    }

    private final Iterator<Map.Entry<K, V>> zzvh() {
        if (this.zzbws == null) {
            this.zzbws = this.zzbwq.zzbwk.entrySet().iterator();
        }
        return this.zzbws;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.pos + 1 < this.zzbwq.zzbwj.size() || (!this.zzbwq.zzbwk.isEmpty() && zzvh().hasNext());
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        this.zzbwr = true;
        int i = this.pos + 1;
        this.pos = i;
        return i < this.zzbwq.zzbwj.size() ? (Map.Entry) this.zzbwq.zzbwj.get(this.pos) : zzvh().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzbwr) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzbwr = false;
        this.zzbwq.zzvd();
        if (this.pos >= this.zzbwq.zzbwj.size()) {
            zzvh().remove();
            return;
        }
        zzaad zzaadVar = this.zzbwq;
        int i = this.pos;
        this.pos = i - 1;
        zzaadVar.zzak(i);
    }
}
