package com.google.android.gms.common.api.a;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public abstract class j<L> implements x<L> {
    private final DataHolder mDataHolder;

    protected j(DataHolder dataHolder) {
        this.mDataHolder = dataHolder;
    }

    @Override // com.google.android.gms.common.api.a.x
    public void YU() {
        if (this.mDataHolder != null) {
            this.mDataHolder.close();
        }
    }

    protected abstract void a(L l, DataHolder dataHolder);

    @Override // com.google.android.gms.common.api.a.x
    public final void z(L l) {
        a(l, this.mDataHolder);
    }
}
