package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzhg implements Callable<List<zzju>> {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzgq zzany;

    zzhg(zzgq zzgqVar, zzec zzecVar) {
        this.zzany = zzgqVar;
        this.zzanq = zzecVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzju> call() {
        this.zzany.zzacr.zzka();
        return this.zzany.zzacr.zzga().zzaw(this.zzanq.packageName);
    }
}
