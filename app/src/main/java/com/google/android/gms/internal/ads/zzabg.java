package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzabg implements Runnable {
    private final /* synthetic */ zzabf zzbzj;

    zzabg(zzabf zzabfVar) {
        this.zzbzj = zzabfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzbzj.zzbzi.get()) {
            zzakb.e("Timed out waiting for WebView to finish loading.");
            this.zzbzj.cancel();
        }
    }
}
