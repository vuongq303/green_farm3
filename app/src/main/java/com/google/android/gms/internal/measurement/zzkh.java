package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzkh extends zzabd<zzkh> {
    private static volatile zzkh[] zzasq;
    public Integer zzarg = null;
    public zzkm zzasr = null;
    public zzkm zzass = null;
    public Boolean zzast = null;

    public zzkh() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzkh[] zzlc() {
        if (zzasq == null) {
            synchronized (zzabh.zzbzr) {
                if (zzasq == null) {
                    zzasq = new zzkh[0];
                }
            }
        }
        return zzasq;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkh)) {
            return false;
        }
        zzkh zzkhVar = (zzkh) obj;
        if (this.zzarg == null) {
            if (zzkhVar.zzarg != null) {
                return false;
            }
        } else if (!this.zzarg.equals(zzkhVar.zzarg)) {
            return false;
        }
        if (this.zzasr == null) {
            if (zzkhVar.zzasr != null) {
                return false;
            }
        } else if (!this.zzasr.equals(zzkhVar.zzasr)) {
            return false;
        }
        if (this.zzass == null) {
            if (zzkhVar.zzass != null) {
                return false;
            }
        } else if (!this.zzass.equals(zzkhVar.zzass)) {
            return false;
        }
        if (this.zzast == null) {
            if (zzkhVar.zzast != null) {
                return false;
            }
        } else if (!this.zzast.equals(zzkhVar.zzast)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkhVar.zzbzh == null || zzkhVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkhVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = (this.zzarg == null ? 0 : this.zzarg.hashCode()) + ((getClass().getName().hashCode() + 527) * 31);
        zzkm zzkmVar = this.zzasr;
        int i = iHashCode2 * 31;
        int iHashCode3 = zzkmVar == null ? 0 : zzkmVar.hashCode();
        zzkm zzkmVar2 = this.zzass;
        int iHashCode4 = ((this.zzast == null ? 0 : this.zzast.hashCode()) + (((zzkmVar2 == null ? 0 : zzkmVar2.hashCode()) + ((iHashCode3 + i) * 31)) * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode4 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzarg != null) {
            iZza += zzabb.zzf(1, this.zzarg.intValue());
        }
        if (this.zzasr != null) {
            iZza += zzabb.zzb(2, this.zzasr);
        }
        if (this.zzass != null) {
            iZza += zzabb.zzb(3, this.zzass);
        }
        if (this.zzast == null) {
            return iZza;
        }
        this.zzast.booleanValue();
        return iZza + zzabb.zzas(4) + 1;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzarg != null) {
            zzabbVar.zze(1, this.zzarg.intValue());
        }
        if (this.zzasr != null) {
            zzabbVar.zza(2, this.zzasr);
        }
        if (this.zzass != null) {
            zzabbVar.zza(3, this.zzass);
        }
        if (this.zzast != null) {
            zzabbVar.zza(4, this.zzast.booleanValue());
        }
        super.zza(zzabbVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzabj
    public final /* synthetic */ zzabj zzb(zzaba zzabaVar) throws zzabi {
        while (true) {
            int iZzvo = zzabaVar.zzvo();
            switch (iZzvo) {
                case 0:
                    break;
                case 8:
                    this.zzarg = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 18:
                    if (this.zzasr == null) {
                        this.zzasr = new zzkm();
                    }
                    zzabaVar.zza(this.zzasr);
                    break;
                case 26:
                    if (this.zzass == null) {
                        this.zzass = new zzkm();
                    }
                    zzabaVar.zza(this.zzass);
                    break;
                case 32:
                    this.zzast = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                default:
                    if (!super.zza(zzabaVar, iZzvo)) {
                    }
                    break;
            }
        }
        return this;
    }
}
