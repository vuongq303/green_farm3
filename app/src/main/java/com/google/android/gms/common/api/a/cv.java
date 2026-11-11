package com.google.android.gms.common.api.a;

import com.google.android.gms.common.api.e;

/* JADX INFO: loaded from: classes.dex */
public final class cv<O extends com.google.android.gms.common.api.e> {
    private final boolean bLZ;
    private final int bMa;
    private final com.google.android.gms.common.api.a<O> mApi;
    private final O zzcl;

    private cv(com.google.android.gms.common.api.a<O> aVar) {
        this.bLZ = true;
        this.mApi = aVar;
        this.zzcl = null;
        this.bMa = System.identityHashCode(this);
    }

    private cv(com.google.android.gms.common.api.a<O> aVar, O o) {
        this.bLZ = false;
        this.mApi = aVar;
        this.zzcl = o;
        this.bMa = com.google.android.gms.common.internal.bd.hashCode(this.mApi, this.zzcl);
    }

    public static <O extends com.google.android.gms.common.api.e> cv<O> a(com.google.android.gms.common.api.a<O> aVar, O o) {
        return new cv<>(aVar, o);
    }

    public static <O extends com.google.android.gms.common.api.e> cv<O> c(com.google.android.gms.common.api.a<O> aVar) {
        return new cv<>(aVar);
    }

    public final String ZI() {
        return this.mApi.getName();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof cv)) {
            return false;
        }
        cv cvVar = (cv) obj;
        return !this.bLZ && !cvVar.bLZ && com.google.android.gms.common.internal.bd.b(this.mApi, cvVar.mApi) && com.google.android.gms.common.internal.bd.b(this.zzcl, cvVar.zzcl);
    }

    public final int hashCode() {
        return this.bMa;
    }
}
