package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzap implements Callable<Void> {
    private final /* synthetic */ zzai zzvc;

    zzap(zzai zzaiVar) {
        this.zzvc = zzaiVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ Void call() {
        this.zzvc.zzva.zzdh();
        return null;
    }
}
