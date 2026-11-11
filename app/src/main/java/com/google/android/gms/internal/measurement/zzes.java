package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class zzes implements Iterator<String> {
    private Iterator<String> zzafx;
    private final /* synthetic */ zzer zzafy;

    zzes(zzer zzerVar) {
        this.zzafy = zzerVar;
        this.zzafx = this.zzafy.zzafw.keySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzafx.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zzafx.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }
}
