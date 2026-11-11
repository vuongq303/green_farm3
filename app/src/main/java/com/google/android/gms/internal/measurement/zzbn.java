package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzbn implements Callable<String> {
    private final /* synthetic */ zzbl zzxo;

    zzbn(zzbl zzblVar) {
        this.zzxo = zzblVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() {
        return this.zzxo.zzdq();
    }
}
