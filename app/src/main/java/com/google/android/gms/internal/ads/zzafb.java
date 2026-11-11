package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzafb implements Runnable {
    private final /* synthetic */ zzafa zzcgj;
    private final /* synthetic */ zzaji zzwg;

    zzafb(zzafa zzafaVar, zzaji zzajiVar) {
        this.zzcgj = zzafaVar;
        this.zzwg = zzajiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzcgj.zzccf.zza(this.zzwg);
        if (this.zzcgj.zzcgi != null) {
            this.zzcgj.zzcgi.release();
            this.zzcgj.zzcgi = null;
        }
    }
}
