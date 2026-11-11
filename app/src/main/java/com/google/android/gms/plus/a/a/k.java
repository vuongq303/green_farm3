package com.google.android.gms.plus.a.a;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.plus.zzac;
import com.google.android.gms.internal.plus.zzr;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class k extends com.google.android.gms.common.data.a<a> {
    private final com.google.android.gms.common.data.f<zzr> bYf;

    public k(DataHolder dataHolder) {
        super(dataHolder);
        if (dataHolder.ZW() == null || !dataHolder.ZW().getBoolean("com.google.android.gms.plus.IsSafeParcelable", false)) {
            this.bYf = null;
        } else {
            this.bYf = new com.google.android.gms.common.data.f<>(dataHolder, zzr.CREATOR);
        }
    }

    @Override // com.google.android.gms.common.data.c
    @Deprecated
    /* JADX INFO: renamed from: oq, reason: merged with bridge method [inline-methods] */
    public final a get(int i) {
        return this.bYf != null ? (a) this.bYf.get(i) : new zzac(this.mDataHolder, i);
    }
}
