package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;

/* JADX INFO: loaded from: classes.dex */
final class al implements Runnable {
    private final /* synthetic */ zzi bDt;
    private final /* synthetic */ zzaji zzwg;

    al(zzi zziVar, zzaji zzajiVar) {
        this.bDt = zziVar;
        this.zzwg = zzajiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bDt.zzb(new zzajh(this.zzwg, null, null, null, null, null, null, null));
    }
}
