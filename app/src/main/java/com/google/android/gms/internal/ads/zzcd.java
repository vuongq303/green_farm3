package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzcd implements Runnable {
    private final /* synthetic */ zzcc zzpx;

    zzcd(zzcc zzccVar) {
        this.zzpx = zzccVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzpx.zzpv != null) {
            return;
        }
        synchronized (zzcc.zzpt) {
            if (this.zzpx.zzpv != null) {
                return;
            }
            boolean zBooleanValue = ((Boolean) zzkb.zzik().zzd(zznk.zzbap)).booleanValue();
            if (zBooleanValue) {
                try {
                    zzcc.zzpu = new zzhx(this.zzpx.zzps.zzrt, "ADSHIELD", null);
                } catch (Throwable th) {
                    zBooleanValue = false;
                }
            }
            this.zzpx.zzpv = Boolean.valueOf(zBooleanValue);
            zzcc.zzpt.open();
        }
    }
}
