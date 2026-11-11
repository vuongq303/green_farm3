package com.google.android.gms.common.api.a;

import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class bg extends q {
    private WeakReference<bc> bKX;

    bg(bc bcVar) {
        this.bKX = new WeakReference<>(bcVar);
    }

    @Override // com.google.android.gms.common.api.a.q
    public final void zzv() {
        bc bcVar = this.bKX.get();
        if (bcVar == null) {
            return;
        }
        bcVar.resume();
    }
}
