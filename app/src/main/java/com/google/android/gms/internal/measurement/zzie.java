package com.google.android.gms.internal.measurement;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
final class zzie implements Runnable {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzaoa;
    private final /* synthetic */ String zzaoc;
    private final /* synthetic */ zzhm zzaop;
    private final /* synthetic */ long zzaor;
    private final /* synthetic */ Bundle zzaow;
    private final /* synthetic */ boolean zzaox;
    private final /* synthetic */ boolean zzaoy;
    private final /* synthetic */ boolean zzaoz;

    zzie(zzhm zzhmVar, String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        this.zzaop = zzhmVar;
        this.zzaoa = str;
        this.val$name = str2;
        this.zzaor = j;
        this.zzaow = bundle;
        this.zzaox = z;
        this.zzaoy = z2;
        this.zzaoz = z3;
        this.zzaoc = str3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zzb(this.zzaoa, this.val$name, this.zzaor, this.zzaow, this.zzaox, this.zzaoy, this.zzaoz, this.zzaoc);
    }
}
