package com.google.a;

/* JADX INFO: compiled from: Dimension.java */
/* JADX INFO: loaded from: classes.dex */
public final class b {
    private final int height;
    private final int width;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return this.width == bVar.width && this.height == bVar.height;
    }

    public int hashCode() {
        return (this.width * 32713) + this.height;
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}
