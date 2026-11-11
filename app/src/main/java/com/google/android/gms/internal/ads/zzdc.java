package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzdc implements Runnable {
    private final /* synthetic */ zzcz zzsl;
    private final /* synthetic */ int zzsm;
    private final /* synthetic */ boolean zzsn;

    zzdc(zzcz zzczVar, int i, boolean z) {
        this.zzsl = zzczVar;
        this.zzsm = i;
        this.zzsn = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzba zzbaVarZzb = this.zzsl.zzb(this.zzsm, this.zzsn);
        this.zzsl.zzsb = zzbaVarZzb;
        if (zzcz.zza(this.zzsm, zzbaVarZzb)) {
            this.zzsl.zza(this.zzsm + 1, this.zzsn);
        }
    }
}
