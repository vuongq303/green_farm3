package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzaby implements Runnable {
    private final /* synthetic */ zzaoj zzcaj;
    private final /* synthetic */ String zzcak;
    private final /* synthetic */ zzabv zzcal;

    zzaby(zzabv zzabvVar, zzaoj zzaojVar, String str) {
        this.zzcal = zzabvVar;
        this.zzcaj = zzaojVar;
        this.zzcak = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzcaj.set(this.zzcal.zzbzz.zzdv().get(this.zzcak));
    }
}
