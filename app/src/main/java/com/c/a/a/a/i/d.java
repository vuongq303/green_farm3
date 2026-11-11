package com.c.a.a.a.i;

import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: ObjectWrapper.java */
/* JADX INFO: loaded from: classes.dex */
public class d<T> {
    private WeakReference<T> ckI;

    public d(T t) {
        this.ckI = new WeakReference<>(t);
    }

    public T get() {
        return this.ckI.get();
    }

    public void set(T t) {
        this.ckI = new WeakReference<>(t);
    }

    public boolean isEmpty() {
        return get() == null;
    }

    public boolean contains(Object obj) {
        T t = get();
        return (t == null || obj == null || !t.equals(obj)) ? false : true;
    }
}
