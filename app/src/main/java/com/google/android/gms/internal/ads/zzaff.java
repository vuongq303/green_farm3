package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzaff implements Runnable {
    private final /* synthetic */ zzafa zzcgj;

    zzaff(zzafa zzafaVar) {
        this.zzcgj = zzafaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzcgj.zzcgi != null) {
            this.zzcgj.zzcgi.release();
            this.zzcgj.zzcgi = null;
        }
    }
}
