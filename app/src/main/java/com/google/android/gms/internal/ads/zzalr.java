package com.google.android.gms.internal.ads;

import com.google.android.gms.common.internal.bd;

/* JADX INFO: loaded from: classes.dex */
public final class zzalr {
    public final int count;
    public final String name;
    private final double zzcsz;
    private final double zzcta;
    public final double zzctb;

    public zzalr(String str, double d, double d2, double d3, int i) {
        this.name = str;
        this.zzcta = d;
        this.zzcsz = d2;
        this.zzctb = d3;
        this.count = i;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzalr)) {
            return false;
        }
        zzalr zzalrVar = (zzalr) obj;
        return bd.b(this.name, zzalrVar.name) && this.zzcsz == zzalrVar.zzcsz && this.zzcta == zzalrVar.zzcta && this.count == zzalrVar.count && Double.compare(this.zzctb, zzalrVar.zzctb) == 0;
    }

    public final int hashCode() {
        return bd.hashCode(this.name, Double.valueOf(this.zzcsz), Double.valueOf(this.zzcta), Double.valueOf(this.zzctb), Integer.valueOf(this.count));
    }

    public final String toString() {
        return bd.C(this).c("name", this.name).c("minBound", Double.valueOf(this.zzcta)).c("maxBound", Double.valueOf(this.zzcsz)).c("percent", Double.valueOf(this.zzctb)).c("count", Integer.valueOf(this.count)).toString();
    }
}
