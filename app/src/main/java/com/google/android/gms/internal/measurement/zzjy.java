package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzjy extends zzabd<zzjy> {
    private static volatile zzjy[] zzarf;
    public Integer zzarg = null;
    public zzkc[] zzarh = zzkc.zzkz();
    public zzjz[] zzari = zzjz.zzkx();

    public zzjy() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzjy[] zzkw() {
        if (zzarf == null) {
            synchronized (zzabh.zzbzr) {
                if (zzarf == null) {
                    zzarf = new zzjy[0];
                }
            }
        }
        return zzarf;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzjy)) {
            return false;
        }
        zzjy zzjyVar = (zzjy) obj;
        if (this.zzarg == null) {
            if (zzjyVar.zzarg != null) {
                return false;
            }
        } else if (!this.zzarg.equals(zzjyVar.zzarg)) {
            return false;
        }
        if (zzabh.equals(this.zzarh, zzjyVar.zzarh) && zzabh.equals(this.zzari, zzjyVar.zzari)) {
            if (this.zzbzh == null || this.zzbzh.isEmpty()) {
                return zzjyVar.zzbzh == null || zzjyVar.zzbzh.isEmpty();
            }
            return this.zzbzh.equals(zzjyVar.zzbzh);
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((((((this.zzarg == null ? 0 : this.zzarg.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + zzabh.hashCode(this.zzarh)) * 31) + zzabh.hashCode(this.zzari)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzarg != null) {
            iZza += zzabb.zzf(1, this.zzarg.intValue());
        }
        if (this.zzarh != null && this.zzarh.length > 0) {
            int iZzb = iZza;
            for (int i = 0; i < this.zzarh.length; i++) {
                zzkc zzkcVar = this.zzarh[i];
                if (zzkcVar != null) {
                    iZzb += zzabb.zzb(2, zzkcVar);
                }
            }
            iZza = iZzb;
        }
        if (this.zzari != null && this.zzari.length > 0) {
            for (int i2 = 0; i2 < this.zzari.length; i2++) {
                zzjz zzjzVar = this.zzari[i2];
                if (zzjzVar != null) {
                    iZza += zzabb.zzb(3, zzjzVar);
                }
            }
        }
        return iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzarg != null) {
            zzabbVar.zze(1, this.zzarg.intValue());
        }
        if (this.zzarh != null && this.zzarh.length > 0) {
            for (int i = 0; i < this.zzarh.length; i++) {
                zzkc zzkcVar = this.zzarh[i];
                if (zzkcVar != null) {
                    zzabbVar.zza(2, zzkcVar);
                }
            }
        }
        if (this.zzari != null && this.zzari.length > 0) {
            for (int i2 = 0; i2 < this.zzari.length; i2++) {
                zzjz zzjzVar = this.zzari[i2];
                if (zzjzVar != null) {
                    zzabbVar.zza(3, zzjzVar);
                }
            }
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
                    int iZzb = zzabm.zzb(zzabaVar, 18);
                    int length = this.zzarh == null ? 0 : this.zzarh.length;
                    zzkc[] zzkcVarArr = new zzkc[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzarh, 0, zzkcVarArr, 0, length);
                    }
                    while (length < zzkcVarArr.length - 1) {
                        zzkcVarArr[length] = new zzkc();
                        zzabaVar.zza(zzkcVarArr[length]);
                        zzabaVar.zzvo();
                        length++;
                    }
                    zzkcVarArr[length] = new zzkc();
                    zzabaVar.zza(zzkcVarArr[length]);
                    this.zzarh = zzkcVarArr;
                    break;
                case 26:
                    int iZzb2 = zzabm.zzb(zzabaVar, 26);
                    int length2 = this.zzari == null ? 0 : this.zzari.length;
                    zzjz[] zzjzVarArr = new zzjz[iZzb2 + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzari, 0, zzjzVarArr, 0, length2);
                    }
                    while (length2 < zzjzVarArr.length - 1) {
                        zzjzVarArr[length2] = new zzjz();
                        zzabaVar.zza(zzjzVarArr[length2]);
                        zzabaVar.zzvo();
                        length2++;
                    }
                    zzjzVarArr[length2] = new zzjz();
                    zzabaVar.zza(zzjzVarArr[length2]);
                    this.zzari = zzjzVarArr;
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
