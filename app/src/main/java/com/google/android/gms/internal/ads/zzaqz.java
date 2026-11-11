package com.google.android.gms.internal.ads;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class zzaqz implements Runnable {
    private final /* synthetic */ View val$view;
    private final /* synthetic */ zzait zzdcg;
    private final /* synthetic */ int zzdch;
    private final /* synthetic */ zzaqx zzdci;

    zzaqz(zzaqx zzaqxVar, View view, zzait zzaitVar, int i) {
        this.zzdci = zzaqxVar;
        this.val$view = view;
        this.zzdcg = zzaitVar;
        this.zzdch = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzdci.zza(this.val$view, this.zzdcg, this.zzdch - 1);
    }
}
