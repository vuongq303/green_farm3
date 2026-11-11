package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzkn extends zzabd<zzkn> {
    private static volatile zzkn[] zzauh;
    public Long zzaui = null;
    public String name = null;
    public String zzajf = null;
    public Long zzasz = null;
    private Float zzaqw = null;
    public Double zzaqx = null;

    public zzkn() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzkn[] zzlg() {
        if (zzauh == null) {
            synchronized (zzabh.zzbzr) {
                if (zzauh == null) {
                    zzauh = new zzkn[0];
                }
            }
        }
        return zzauh;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkn)) {
            return false;
        }
        zzkn zzknVar = (zzkn) obj;
        if (this.zzaui == null) {
            if (zzknVar.zzaui != null) {
                return false;
            }
        } else if (!this.zzaui.equals(zzknVar.zzaui)) {
            return false;
        }
        if (this.name == null) {
            if (zzknVar.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzknVar.name)) {
            return false;
        }
        if (this.zzajf == null) {
            if (zzknVar.zzajf != null) {
                return false;
            }
        } else if (!this.zzajf.equals(zzknVar.zzajf)) {
            return false;
        }
        if (this.zzasz == null) {
            if (zzknVar.zzasz != null) {
                return false;
            }
        } else if (!this.zzasz.equals(zzknVar.zzasz)) {
            return false;
        }
        if (this.zzaqw == null) {
            if (zzknVar.zzaqw != null) {
                return false;
            }
        } else if (!this.zzaqw.equals(zzknVar.zzaqw)) {
            return false;
        }
        if (this.zzaqx == null) {
            if (zzknVar.zzaqx != null) {
                return false;
            }
        } else if (!this.zzaqx.equals(zzknVar.zzaqx)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzknVar.zzbzh == null || zzknVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzknVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((this.zzaqx == null ? 0 : this.zzaqx.hashCode()) + (((this.zzaqw == null ? 0 : this.zzaqw.hashCode()) + (((this.zzasz == null ? 0 : this.zzasz.hashCode()) + (((this.zzajf == null ? 0 : this.zzajf.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + (((this.zzaui == null ? 0 : this.zzaui.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzaui != null) {
            iZza += zzabb.zzc(1, this.zzaui.longValue());
        }
        if (this.name != null) {
            iZza += zzabb.zzd(2, this.name);
        }
        if (this.zzajf != null) {
            iZza += zzabb.zzd(3, this.zzajf);
        }
        if (this.zzasz != null) {
            iZza += zzabb.zzc(4, this.zzasz.longValue());
        }
        if (this.zzaqw != null) {
            this.zzaqw.floatValue();
            iZza += zzabb.zzas(5) + 4;
        }
        if (this.zzaqx == null) {
            return iZza;
        }
        this.zzaqx.doubleValue();
        return iZza + zzabb.zzas(6) + 8;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzaui != null) {
            zzabbVar.zzb(1, this.zzaui.longValue());
        }
        if (this.name != null) {
            zzabbVar.zzc(2, this.name);
        }
        if (this.zzajf != null) {
            zzabbVar.zzc(3, this.zzajf);
        }
        if (this.zzasz != null) {
            zzabbVar.zzb(4, this.zzasz.longValue());
        }
        if (this.zzaqw != null) {
            zzabbVar.zza(5, this.zzaqw.floatValue());
        }
        if (this.zzaqx != null) {
            zzabbVar.zza(6, this.zzaqx.doubleValue());
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
                    this.zzaui = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 18:
                    this.name = zzabaVar.readString();
                    break;
                case 26:
                    this.zzajf = zzabaVar.readString();
                    break;
                case 32:
                    this.zzasz = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 45:
                    this.zzaqw = Float.valueOf(Float.intBitsToFloat(zzabaVar.zzvu()));
                    break;
                case 49:
                    this.zzaqx = Double.valueOf(Double.longBitsToDouble(zzabaVar.zzvv()));
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
