package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.bf;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public class d<T> implements Iterator<T> {
    protected final c<T> bNa;
    protected int bNb = -1;

    public d(c<T> cVar) {
        this.bNa = (c) bf.checkNotNull(cVar);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.bNb < this.bNa.getCount() + (-1);
    }

    @Override // java.util.Iterator
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException(new StringBuilder(46).append("Cannot advance the iterator beyond ").append(this.bNb).toString());
        }
        c<T> cVar = this.bNa;
        int i = this.bNb + 1;
        this.bNb = i;
        return cVar.get(i);
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
