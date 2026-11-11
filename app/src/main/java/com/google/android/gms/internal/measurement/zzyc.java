package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
final class zzyc implements Iterator {
    private final int limit;
    private int position = 0;
    private final /* synthetic */ zzyb zzbtl;

    zzyc(zzyb zzybVar) {
        this.zzbtl = zzybVar;
        this.limit = this.zzbtl.size();
    }

    private final byte nextByte() {
        try {
            zzyb zzybVar = this.zzbtl;
            int i = this.position;
            this.position = i + 1;
            return zzybVar.zzag(i);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.position < this.limit;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        return Byte.valueOf(nextByte());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
