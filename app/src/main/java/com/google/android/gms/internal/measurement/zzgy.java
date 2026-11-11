package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzgy implements Callable<List<zzef>> {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ String zzaoa;
    private final /* synthetic */ String zzaob;

    zzgy(zzgq zzgqVar, zzec zzecVar, String str, String str2) {
        this.zzany = zzgqVar;
        this.zzanq = zzecVar;
        this.zzaoa = str;
        this.zzaob = str2;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzef> call() {
        this.zzany.zzacr.zzka();
        return this.zzany.zzacr.zzga().zzc(this.zzanq.packageName, this.zzaoa, this.zzaob);
    }
}
