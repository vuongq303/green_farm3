package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzgx implements Callable<List<zzju>> {
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ String zzaoa;
    private final /* synthetic */ String zzaob;
    private final /* synthetic */ String zzaoc;

    zzgx(zzgq zzgqVar, String str, String str2, String str3) {
        this.zzany = zzgqVar;
        this.zzaoc = str;
        this.zzaoa = str2;
        this.zzaob = str3;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzju> call() {
        this.zzany.zzacr.zzka();
        return this.zzany.zzacr.zzga().zzb(this.zzaoc, this.zzaoa, this.zzaob);
    }
}
