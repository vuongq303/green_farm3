package com.google.android.gms.common.api.a;

import com.google.android.gms.common.Feature;

/* JADX INFO: loaded from: classes.dex */
final class n {
    private final Feature bIV;
    private final cv<?> bJD;

    private n(cv<?> cvVar, Feature feature) {
        this.bJD = cvVar;
        this.bIV = feature;
    }

    /* synthetic */ n(cv cvVar, Feature feature, bm bmVar) {
        this(cvVar, feature);
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof n)) {
            return false;
        }
        n nVar = (n) obj;
        return com.google.android.gms.common.internal.bd.b(this.bJD, nVar.bJD) && com.google.android.gms.common.internal.bd.b(this.bIV, nVar.bIV);
    }

    public final int hashCode() {
        return com.google.android.gms.common.internal.bd.hashCode(this.bJD, this.bIV);
    }

    public final String toString() {
        return com.google.android.gms.common.internal.bd.C(this).c("key", this.bJD).c("feature", this.bIV).toString();
    }
}
