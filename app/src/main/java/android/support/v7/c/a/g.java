package android.support.v7.c.a;

/* JADX INFO: compiled from: BaseWrapper.java */
/* JADX INFO: loaded from: classes.dex */
class g<T> {
    final T pt;

    g(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.pt = t;
    }
}
