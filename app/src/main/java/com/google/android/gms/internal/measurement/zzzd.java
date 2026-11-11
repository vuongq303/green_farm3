package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public class zzzd {
    private static final zzyn zzbvi = zzyn.zztw();
    private zzyb zzbvj;
    private volatile zzzq zzbvk;
    private volatile zzyb zzbvl;

    private final zzzq zzb(zzzq zzzqVar) {
        if (this.zzbvk == null) {
            synchronized (this) {
                if (this.zzbvk == null) {
                    try {
                        this.zzbvk = zzzqVar;
                        this.zzbvl = zzyb.zzbtj;
                    } catch (zzyy e) {
                        this.zzbvk = zzzqVar;
                        this.zzbvl = zzyb.zzbtj;
                    }
                }
            }
        }
        return this.zzbvk;
    }

    private final zzyb zzuh() {
        if (this.zzbvl != null) {
            return this.zzbvl;
        }
        synchronized (this) {
            if (this.zzbvl != null) {
                return this.zzbvl;
            }
            if (this.zzbvk == null) {
                this.zzbvl = zzyb.zzbtj;
            } else {
                this.zzbvl = this.zzbvk.zzuh();
            }
            return this.zzbvl;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzzd)) {
            return false;
        }
        zzzd zzzdVar = (zzzd) obj;
        zzzq zzzqVar = this.zzbvk;
        zzzq zzzqVar2 = zzzdVar.zzbvk;
        if (zzzqVar == null && zzzqVar2 == null) {
            return zzuh().equals(zzzdVar.zzuh());
        }
        if (zzzqVar == null || zzzqVar2 == null) {
            return zzzqVar != null ? zzzqVar.equals(zzzdVar.zzb(zzzqVar.zzur())) : zzb(zzzqVar2.zzur()).equals(zzzqVar2);
        }
        return zzzqVar.equals(zzzqVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final zzzq zzc(zzzq zzzqVar) {
        zzzq zzzqVar2 = this.zzbvk;
        this.zzbvj = null;
        this.zzbvl = null;
        this.zzbvk = zzzqVar;
        return zzzqVar2;
    }
}
