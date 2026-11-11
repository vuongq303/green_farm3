package com.google.android.gms.common.api.a;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.c;

/* JADX INFO: loaded from: classes.dex */
public abstract class ad<A extends com.google.android.gms.common.api.c, ResultT> {
    private final Feature[] zzlz;
    private final boolean zzma;

    @Deprecated
    public ad() {
        this.zzlz = null;
        this.zzma = false;
    }

    private ad(Feature[] featureArr, boolean z) {
        this.zzlz = featureArr;
        this.zzma = z;
    }

    public static <A extends com.google.android.gms.common.api.c, ResultT> ae<A, ResultT> builder() {
        return new ae<>();
    }

    protected abstract void doExecute(A a2, com.google.android.gms.f.g<ResultT> gVar);

    public boolean shouldAutoResolveMissingFeatures() {
        return this.zzma;
    }

    public final Feature[] zzca() {
        return this.zzlz;
    }
}
