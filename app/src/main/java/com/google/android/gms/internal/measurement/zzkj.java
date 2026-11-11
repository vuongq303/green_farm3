package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzkj extends zzabd<zzkj> {
    private static volatile zzkj[] zzasy;
    public String name = null;
    public String zzajf = null;
    public Long zzasz = null;
    private Float zzaqw = null;
    public Double zzaqx = null;

    public zzkj() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzkj[] zzle() {
        if (zzasy == null) {
            synchronized (zzabh.zzbzr) {
                if (zzasy == null) {
                    zzasy = new zzkj[0];
                }
            }
        }
        return zzasy;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkj)) {
            return false;
        }
        zzkj zzkjVar = (zzkj) obj;
        if (this.name == null) {
            if (zzkjVar.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzkjVar.name)) {
            return false;
        }
        if (this.zzajf == null) {
            if (zzkjVar.zzajf != null) {
                return false;
            }
        } else if (!this.zzajf.equals(zzkjVar.zzajf)) {
            return false;
        }
        if (this.zzasz == null) {
            if (zzkjVar.zzasz != null) {
                return false;
            }
        } else if (!this.zzasz.equals(zzkjVar.zzasz)) {
            return false;
        }
        if (this.zzaqw == null) {
            if (zzkjVar.zzaqw != null) {
                return false;
            }
        } else if (!this.zzaqw.equals(zzkjVar.zzaqw)) {
            return false;
        }
        if (this.zzaqx == null) {
            if (zzkjVar.zzaqx != null) {
                return false;
            }
        } else if (!this.zzaqx.equals(zzkjVar.zzaqx)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkjVar.zzbzh == null || zzkjVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkjVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((this.zzaqx == null ? 0 : this.zzaqx.hashCode()) + (((this.zzaqw == null ? 0 : this.zzaqw.hashCode()) + (((this.zzasz == null ? 0 : this.zzasz.hashCode()) + (((this.zzajf == null ? 0 : this.zzajf.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.name != null) {
            iZza += zzabb.zzd(1, this.name);
        }
        if (this.zzajf != null) {
            iZza += zzabb.zzd(2, this.zzajf);
        }
        if (this.zzasz != null) {
            iZza += zzabb.zzc(3, this.zzasz.longValue());
        }
        if (this.zzaqw != null) {
            this.zzaqw.floatValue();
            iZza += zzabb.zzas(4) + 4;
        }
        if (this.zzaqx == null) {
            return iZza;
        }
        this.zzaqx.doubleValue();
        return iZza + zzabb.zzas(5) + 8;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.name != null) {
            zzabbVar.zzc(1, this.name);
        }
        if (this.zzajf != null) {
            zzabbVar.zzc(2, this.zzajf);
        }
        if (this.zzasz != null) {
            zzabbVar.zzb(3, this.zzasz.longValue());
        }
        if (this.zzaqw != null) {
            zzabbVar.zza(4, this.zzaqw.floatValue());
        }
        if (this.zzaqx != null) {
            zzabbVar.zza(5, this.zzaqx.doubleValue());
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
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.name = zzabaVar.readString();
                    break;
                case 18:
                    this.zzajf = zzabaVar.readString();
                    break;
                case 24:
                    this.zzasz = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 37:
                    this.zzaqw = Float.valueOf(Float.intBitsToFloat(zzabaVar.zzvu()));
                    break;
                case 41:
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
