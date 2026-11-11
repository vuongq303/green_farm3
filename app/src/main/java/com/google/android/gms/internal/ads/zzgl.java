package com.google.android.gms.internal.ads;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class zzgl implements Runnable {
    private final /* synthetic */ View zzaij;
    private final /* synthetic */ zzgk zzaik;

    zzgl(zzgk zzgkVar, View view) {
        this.zzaik = zzgkVar;
        this.zzaij = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaik.zzk(this.zzaij);
    }
}
