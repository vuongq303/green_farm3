package com.google.android.gms.internal.ads;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class zzex implements zzgd {
    private zzoz zzafm;

    public zzex(zzoz zzozVar) {
        this.zzafm = zzozVar;
    }

    @Override // com.google.android.gms.internal.ads.zzgd
    public final View zzgh() {
        if (this.zzafm != null) {
            return this.zzafm.zzkr();
        }
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzgd
    public final boolean zzgi() {
        return this.zzafm == null;
    }

    @Override // com.google.android.gms.internal.ads.zzgd
    public final zzgd zzgj() {
        return this;
    }
}
