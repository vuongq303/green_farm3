package com.google.android.gms.common.data;

import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class a<T> implements c<T> {
    protected final DataHolder mDataHolder;

    protected a(DataHolder dataHolder) {
        this.mDataHolder = dataHolder;
    }

    @Deprecated
    public final void close() {
        release();
    }

    @Override // com.google.android.gms.common.data.c
    public int getCount() {
        if (this.mDataHolder == null) {
            return 0;
        }
        return this.mDataHolder.getCount();
    }

    @Deprecated
    public boolean isClosed() {
        return this.mDataHolder == null || this.mDataHolder.isClosed();
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new d(this);
    }

    @Override // com.google.android.gms.common.api.ac
    public void release() {
        if (this.mDataHolder != null) {
            this.mDataHolder.close();
        }
    }
}
