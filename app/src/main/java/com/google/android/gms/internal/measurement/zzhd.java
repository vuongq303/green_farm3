package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzhd implements Callable<byte[]> {
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ String zzaoc;
    private final /* synthetic */ zzeu zzaod;

    zzhd(zzgq zzgqVar, zzeu zzeuVar, String str) {
        this.zzany = zzgqVar;
        this.zzaod = zzeuVar;
        this.zzaoc = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ byte[] call() {
        this.zzany.zzacr.zzka();
        return this.zzany.zzacr.zza(this.zzaod, this.zzaoc);
    }
}
