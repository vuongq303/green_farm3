package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzgz implements Callable<List<zzef>> {
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ String zzaoa;
    private final /* synthetic */ String zzaob;
    private final /* synthetic */ String zzaoc;

    zzgz(zzgq zzgqVar, String str, String str2, String str3) {
        this.zzany = zzgqVar;
        this.zzaoc = str;
        this.zzaoa = str2;
        this.zzaob = str3;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzef> call() {
        this.zzany.zzacr.zzka();
        return this.zzany.zzacr.zzga().zzc(this.zzaoc, this.zzaoa, this.zzaob);
    }
}
