package com.google.android.gms.auth.api.signin.internal;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static int zzad = 31;
    private int bGZ = 1;

    public int Yr() {
        return this.bGZ;
    }

    public b bX(boolean z) {
        this.bGZ = (z ? 1 : 0) + (this.bGZ * zzad);
        return this;
    }

    public b u(Object obj) {
        this.bGZ = (obj == null ? 0 : obj.hashCode()) + (this.bGZ * zzad);
        return this;
    }
}
