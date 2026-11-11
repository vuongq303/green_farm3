package com.moat.analytics.mobile.glft.a.b;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public final class a<T> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final a<?> f6a = new a<>();
    private final T b;

    private a() {
        this.b = null;
    }

    private a(T t) {
        if (t == null) {
            throw new NullPointerException("Optional of null value.");
        }
        this.b = t;
    }

    public static <T> a<T> a() {
        return (a<T>) f6a;
    }

    public static <T> a<T> a(T t) {
        return new a<>(t);
    }

    public static <T> a<T> b(T t) {
        return t == null ? a() : a(t);
    }

    public T b() {
        if (this.b == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.b;
    }

    public T c(T t) {
        return this.b != null ? this.b : t;
    }

    public boolean c() {
        return this.b != null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return this.b == aVar.b || !(this.b == null || aVar.b == null || !this.b.equals(aVar.b));
    }

    public int hashCode() {
        if (this.b == null) {
            return 0;
        }
        return this.b.hashCode();
    }

    public String toString() {
        return this.b != null ? String.format("Optional[%s]", this.b) : "Optional.empty";
    }
}
