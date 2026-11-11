package com.google.android.gms.ads.internal;

import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.android.gms.internal.ads.zzaqw;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
final class l implements zzv<zzaqw> {
    private final /* synthetic */ CountDownLatch zzwd;

    l(CountDownLatch countDownLatch) {
        this.zzwd = countDownLatch;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzaqw zzaqwVar, Map map) {
        this.zzwd.countDown();
        zzaqwVar.getView().setVisibility(0);
    }
}
