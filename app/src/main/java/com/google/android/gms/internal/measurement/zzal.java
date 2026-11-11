package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzal implements Runnable {
    private final /* synthetic */ zzai zzvc;
    private final /* synthetic */ String zzve;
    private final /* synthetic */ Runnable zzvf;

    zzal(zzai zzaiVar, String str, Runnable runnable) {
        this.zzvc = zzaiVar;
        this.zzve = str;
        this.zzvf = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvc.zzva.zzy(this.zzve);
        if (this.zzvf != null) {
            this.zzvf.run();
        }
    }
}
