package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzki extends zzabd<zzki> {
    private static volatile zzki[] zzasu;
    public zzkj[] zzasv = zzkj.zzle();
    public String name = null;
    public Long zzasw = null;
    public Long zzasx = null;
    public Integer count = null;

    public zzki() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzki[] zzld() {
        if (zzasu == null) {
            synchronized (zzabh.zzbzr) {
                if (zzasu == null) {
                    zzasu = new zzki[0];
                }
            }
        }
        return zzasu;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzki)) {
            return false;
        }
        zzki zzkiVar = (zzki) obj;
        if (!zzabh.equals(this.zzasv, zzkiVar.zzasv)) {
            return false;
        }
        if (this.name == null) {
            if (zzkiVar.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzkiVar.name)) {
            return false;
        }
        if (this.zzasw == null) {
            if (zzkiVar.zzasw != null) {
                return false;
            }
        } else if (!this.zzasw.equals(zzkiVar.zzasw)) {
            return false;
        }
        if (this.zzasx == null) {
            if (zzkiVar.zzasx != null) {
                return false;
            }
        } else if (!this.zzasx.equals(zzkiVar.zzasx)) {
            return false;
        }
        if (this.count == null) {
            if (zzkiVar.count != null) {
                return false;
            }
        } else if (!this.count.equals(zzkiVar.count)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkiVar.zzbzh == null || zzkiVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkiVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((this.count == null ? 0 : this.count.hashCode()) + (((this.zzasx == null ? 0 : this.zzasx.hashCode()) + (((this.zzasw == null ? 0 : this.zzasw.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + zzabh.hashCode(this.zzasv)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzasv != null && this.zzasv.length > 0) {
            for (int i = 0; i < this.zzasv.length; i++) {
                zzkj zzkjVar = this.zzasv[i];
                if (zzkjVar != null) {
                    iZza += zzabb.zzb(1, zzkjVar);
                }
            }
        }
        if (this.name != null) {
            iZza += zzabb.zzd(2, this.name);
        }
        if (this.zzasw != null) {
            iZza += zzabb.zzc(3, this.zzasw.longValue());
        }
        if (this.zzasx != null) {
            iZza += zzabb.zzc(4, this.zzasx.longValue());
        }
        return this.count != null ? iZza + zzabb.zzf(5, this.count.intValue()) : iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzasv != null && this.zzasv.length > 0) {
            for (int i = 0; i < this.zzasv.length; i++) {
                zzkj zzkjVar = this.zzasv[i];
                if (zzkjVar != null) {
                    zzabbVar.zza(1, zzkjVar);
                }
            }
        }
        if (this.name != null) {
            zzabbVar.zzc(2, this.name);
        }
        if (this.zzasw != null) {
            zzabbVar.zzb(3, this.zzasw.longValue());
        }
        if (this.zzasx != null) {
            zzabbVar.zzb(4, this.zzasx.longValue());
        }
        if (this.count != null) {
            zzabbVar.zze(5, this.count.intValue());
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
                    int iZzb = zzabm.zzb(zzabaVar, 10);
                    int length = this.zzasv == null ? 0 : this.zzasv.length;
                    zzkj[] zzkjVarArr = new zzkj[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzasv, 0, zzkjVarArr, 0, length);
                    }
                    while (length < zzkjVarArr.length - 1) {
                        zzkjVarArr[length] = new zzkj();
                        zzabaVar.zza(zzkjVarArr[length]);
                        zzabaVar.zzvo();
                        length++;
                    }
                    zzkjVarArr[length] = new zzkj();
                    zzabaVar.zza(zzkjVarArr[length]);
                    this.zzasv = zzkjVarArr;
                    break;
                case 18:
                    this.name = zzabaVar.readString();
                    break;
                case 24:
                    this.zzasw = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 32:
                    this.zzasx = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 40:
                    this.count = Integer.valueOf(zzabaVar.zzvs());
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
