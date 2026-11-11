package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzxj implements Runnable {
    private final /* synthetic */ zzxh zzbuk;
    private final /* synthetic */ zzanz zzbul;

    zzxj(zzxh zzxhVar, zzanz zzanzVar) {
        this.zzbuk = zzxhVar;
        this.zzbul = zzanzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        for (zzanz zzanzVar : this.zzbuk.zzbug.keySet()) {
            if (zzanzVar != this.zzbul) {
                ((zzxb) this.zzbuk.zzbug.get(zzanzVar)).cancel();
            }
        }
    }
}
