package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzou implements Runnable {
    private final /* synthetic */ zzos zzbir;

    zzou(zzos zzosVar) {
        this.zzbir = zzosVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzbir.zzbij != null) {
            this.zzbir.zzbij.zzkq();
            this.zzbir.zzbij.zzkp();
        }
        zzos.zza(this.zzbir, null);
    }
}
