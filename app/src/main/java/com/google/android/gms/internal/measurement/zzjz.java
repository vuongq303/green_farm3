package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzjz extends zzabd<zzjz> {
    private static volatile zzjz[] zzarj;
    public Integer zzark = null;
    public String zzarl = null;
    public zzka[] zzarm = zzka.zzky();
    private Boolean zzarn = null;
    public zzkb zzaro = null;

    public zzjz() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzjz[] zzkx() {
        if (zzarj == null) {
            synchronized (zzabh.zzbzr) {
                if (zzarj == null) {
                    zzarj = new zzjz[0];
                }
            }
        }
        return zzarj;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzjz)) {
            return false;
        }
        zzjz zzjzVar = (zzjz) obj;
        if (this.zzark == null) {
            if (zzjzVar.zzark != null) {
                return false;
            }
        } else if (!this.zzark.equals(zzjzVar.zzark)) {
            return false;
        }
        if (this.zzarl == null) {
            if (zzjzVar.zzarl != null) {
                return false;
            }
        } else if (!this.zzarl.equals(zzjzVar.zzarl)) {
            return false;
        }
        if (!zzabh.equals(this.zzarm, zzjzVar.zzarm)) {
            return false;
        }
        if (this.zzarn == null) {
            if (zzjzVar.zzarn != null) {
                return false;
            }
        } else if (!this.zzarn.equals(zzjzVar.zzarn)) {
            return false;
        }
        if (this.zzaro == null) {
            if (zzjzVar.zzaro != null) {
                return false;
            }
        } else if (!this.zzaro.equals(zzjzVar.zzaro)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzjzVar.zzbzh == null || zzjzVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzjzVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = (this.zzarn == null ? 0 : this.zzarn.hashCode()) + (((((this.zzarl == null ? 0 : this.zzarl.hashCode()) + (((this.zzark == null ? 0 : this.zzark.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + zzabh.hashCode(this.zzarm)) * 31);
        zzkb zzkbVar = this.zzaro;
        int iHashCode3 = ((zzkbVar == null ? 0 : zzkbVar.hashCode()) + (iHashCode2 * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode3 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzark != null) {
            iZza += zzabb.zzf(1, this.zzark.intValue());
        }
        if (this.zzarl != null) {
            iZza += zzabb.zzd(2, this.zzarl);
        }
        if (this.zzarm != null && this.zzarm.length > 0) {
            int iZzb = iZza;
            for (int i = 0; i < this.zzarm.length; i++) {
                zzka zzkaVar = this.zzarm[i];
                if (zzkaVar != null) {
                    iZzb += zzabb.zzb(3, zzkaVar);
                }
            }
            iZza = iZzb;
        }
        if (this.zzarn != null) {
            this.zzarn.booleanValue();
            iZza += zzabb.zzas(4) + 1;
        }
        return this.zzaro != null ? iZza + zzabb.zzb(5, this.zzaro) : iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzark != null) {
            zzabbVar.zze(1, this.zzark.intValue());
        }
        if (this.zzarl != null) {
            zzabbVar.zzc(2, this.zzarl);
        }
        if (this.zzarm != null && this.zzarm.length > 0) {
            for (int i = 0; i < this.zzarm.length; i++) {
                zzka zzkaVar = this.zzarm[i];
                if (zzkaVar != null) {
                    zzabbVar.zza(3, zzkaVar);
                }
            }
        }
        if (this.zzarn != null) {
            zzabbVar.zza(4, this.zzarn.booleanValue());
        }
        if (this.zzaro != null) {
            zzabbVar.zza(5, this.zzaro);
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
                    this.zzark = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 18:
                    this.zzarl = zzabaVar.readString();
                    break;
                case 26:
                    int iZzb = zzabm.zzb(zzabaVar, 26);
                    int length = this.zzarm == null ? 0 : this.zzarm.length;
                    zzka[] zzkaVarArr = new zzka[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzarm, 0, zzkaVarArr, 0, length);
                    }
                    while (length < zzkaVarArr.length - 1) {
                        zzkaVarArr[length] = new zzka();
                        zzabaVar.zza(zzkaVarArr[length]);
                        zzabaVar.zzvo();
                        length++;
                    }
                    zzkaVarArr[length] = new zzka();
                    zzabaVar.zza(zzkaVarArr[length]);
                    this.zzarm = zzkaVarArr;
                    break;
                case 32:
                    this.zzarn = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                case 42:
                    if (this.zzaro == null) {
                        this.zzaro = new zzkb();
                    }
                    zzabaVar.zza(this.zzaro);
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
