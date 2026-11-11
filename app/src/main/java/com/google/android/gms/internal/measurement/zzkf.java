package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzkf extends zzabd<zzkf> {
    public Long zzask = null;
    public String zzadh = null;
    private Integer zzasl = null;
    public zzkg[] zzasm = zzkg.zzlb();
    public zzke[] zzasn = zzke.zzla();
    public zzjy[] zzaso = zzjy.zzkw();

    public zzkf() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkf)) {
            return false;
        }
        zzkf zzkfVar = (zzkf) obj;
        if (this.zzask == null) {
            if (zzkfVar.zzask != null) {
                return false;
            }
        } else if (!this.zzask.equals(zzkfVar.zzask)) {
            return false;
        }
        if (this.zzadh == null) {
            if (zzkfVar.zzadh != null) {
                return false;
            }
        } else if (!this.zzadh.equals(zzkfVar.zzadh)) {
            return false;
        }
        if (this.zzasl == null) {
            if (zzkfVar.zzasl != null) {
                return false;
            }
        } else if (!this.zzasl.equals(zzkfVar.zzasl)) {
            return false;
        }
        if (zzabh.equals(this.zzasm, zzkfVar.zzasm) && zzabh.equals(this.zzasn, zzkfVar.zzasn) && zzabh.equals(this.zzaso, zzkfVar.zzaso)) {
            if (this.zzbzh == null || this.zzbzh.isEmpty()) {
                return zzkfVar.zzbzh == null || zzkfVar.zzbzh.isEmpty();
            }
            return this.zzbzh.equals(zzkfVar.zzbzh);
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((((((((this.zzasl == null ? 0 : this.zzasl.hashCode()) + (((this.zzadh == null ? 0 : this.zzadh.hashCode()) + (((this.zzask == null ? 0 : this.zzask.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31) + zzabh.hashCode(this.zzasm)) * 31) + zzabh.hashCode(this.zzasn)) * 31) + zzabh.hashCode(this.zzaso)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzask != null) {
            iZza += zzabb.zzc(1, this.zzask.longValue());
        }
        if (this.zzadh != null) {
            iZza += zzabb.zzd(2, this.zzadh);
        }
        if (this.zzasl != null) {
            iZza += zzabb.zzf(3, this.zzasl.intValue());
        }
        if (this.zzasm != null && this.zzasm.length > 0) {
            int iZzb = iZza;
            for (int i = 0; i < this.zzasm.length; i++) {
                zzkg zzkgVar = this.zzasm[i];
                if (zzkgVar != null) {
                    iZzb += zzabb.zzb(4, zzkgVar);
                }
            }
            iZza = iZzb;
        }
        if (this.zzasn != null && this.zzasn.length > 0) {
            int iZzb2 = iZza;
            for (int i2 = 0; i2 < this.zzasn.length; i2++) {
                zzke zzkeVar = this.zzasn[i2];
                if (zzkeVar != null) {
                    iZzb2 += zzabb.zzb(5, zzkeVar);
                }
            }
            iZza = iZzb2;
        }
        if (this.zzaso != null && this.zzaso.length > 0) {
            for (int i3 = 0; i3 < this.zzaso.length; i3++) {
                zzjy zzjyVar = this.zzaso[i3];
                if (zzjyVar != null) {
                    iZza += zzabb.zzb(6, zzjyVar);
                }
            }
        }
        return iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzask != null) {
            zzabbVar.zzb(1, this.zzask.longValue());
        }
        if (this.zzadh != null) {
            zzabbVar.zzc(2, this.zzadh);
        }
        if (this.zzasl != null) {
            zzabbVar.zze(3, this.zzasl.intValue());
        }
        if (this.zzasm != null && this.zzasm.length > 0) {
            for (int i = 0; i < this.zzasm.length; i++) {
                zzkg zzkgVar = this.zzasm[i];
                if (zzkgVar != null) {
                    zzabbVar.zza(4, zzkgVar);
                }
            }
        }
        if (this.zzasn != null && this.zzasn.length > 0) {
            for (int i2 = 0; i2 < this.zzasn.length; i2++) {
                zzke zzkeVar = this.zzasn[i2];
                if (zzkeVar != null) {
                    zzabbVar.zza(5, zzkeVar);
                }
            }
        }
        if (this.zzaso != null && this.zzaso.length > 0) {
            for (int i3 = 0; i3 < this.zzaso.length; i3++) {
                zzjy zzjyVar = this.zzaso[i3];
                if (zzjyVar != null) {
                    zzabbVar.zza(6, zzjyVar);
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
                    this.zzask = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 18:
                    this.zzadh = zzabaVar.readString();
                    break;
                case 24:
                    this.zzasl = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 34:
                    int iZzb = zzabm.zzb(zzabaVar, 34);
                    int length = this.zzasm == null ? 0 : this.zzasm.length;
                    zzkg[] zzkgVarArr = new zzkg[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzasm, 0, zzkgVarArr, 0, length);
                    }
                    while (length < zzkgVarArr.length - 1) {
                        zzkgVarArr[length] = new zzkg();
                        zzabaVar.zza(zzkgVarArr[length]);
                        zzabaVar.zzvo();
                        length++;
                    }
                    zzkgVarArr[length] = new zzkg();
                    zzabaVar.zza(zzkgVarArr[length]);
                    this.zzasm = zzkgVarArr;
                    break;
                case 42:
                    int iZzb2 = zzabm.zzb(zzabaVar, 42);
                    int length2 = this.zzasn == null ? 0 : this.zzasn.length;
                    zzke[] zzkeVarArr = new zzke[iZzb2 + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzasn, 0, zzkeVarArr, 0, length2);
                    }
                    while (length2 < zzkeVarArr.length - 1) {
                        zzkeVarArr[length2] = new zzke();
                        zzabaVar.zza(zzkeVarArr[length2]);
                        zzabaVar.zzvo();
                        length2++;
                    }
                    zzkeVarArr[length2] = new zzke();
                    zzabaVar.zza(zzkeVarArr[length2]);
                    this.zzasn = zzkeVarArr;
                    break;
                case 50:
                    int iZzb3 = zzabm.zzb(zzabaVar, 50);
                    int length3 = this.zzaso == null ? 0 : this.zzaso.length;
                    zzjy[] zzjyVarArr = new zzjy[iZzb3 + length3];
                    if (length3 != 0) {
                        System.arraycopy(this.zzaso, 0, zzjyVarArr, 0, length3);
                    }
                    while (length3 < zzjyVarArr.length - 1) {
                        zzjyVarArr[length3] = new zzjy();
                        zzabaVar.zza(zzjyVarArr[length3]);
                        zzabaVar.zzvo();
                        length3++;
                    }
                    zzjyVarArr[length3] = new zzjy();
                    zzabaVar.zza(zzjyVarArr[length3]);
                    this.zzaso = zzjyVarArr;
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
