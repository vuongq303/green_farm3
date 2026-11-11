package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
public final class w<L> {
    private final L bJQ;
    private final String bJS;

    w(L l, String str) {
        this.bJQ = l;
        this.bJS = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof w)) {
            return false;
        }
        w wVar = (w) obj;
        return this.bJQ == wVar.bJQ && this.bJS.equals(wVar.bJS);
    }

    public final int hashCode() {
        return (System.identityHashCode(this.bJQ) * 31) + this.bJS.hashCode();
    }
}
