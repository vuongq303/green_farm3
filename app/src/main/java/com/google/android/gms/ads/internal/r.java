package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;

/* JADX INFO: loaded from: classes.dex */
final class r implements Runnable {
    private final /* synthetic */ zzbc bBN;
    private final /* synthetic */ zzaji zzwg;

    r(zzbc zzbcVar, zzaji zzajiVar) {
        this.bBN = zzbcVar;
        this.zzwg = zzajiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bBN.zzb(new zzajh(this.zzwg, null, null, null, null, null, null, null));
    }
}
