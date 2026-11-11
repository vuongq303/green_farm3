package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzho implements Runnable {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzaoa;
    private final /* synthetic */ zzhm zzaop;
    private final /* synthetic */ Object zzaoq;
    private final /* synthetic */ long zzaor;

    zzho(zzhm zzhmVar, String str, String str2, Object obj, long j) {
        this.zzaop = zzhmVar;
        this.zzaoa = str;
        this.val$name = str2;
        this.zzaoq = obj;
        this.zzaor = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zza(this.zzaoa, this.val$name, this.zzaoq, this.zzaor);
    }
}
