package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzkb extends zzabd<zzkb> {
    public Integer zzaru = null;
    public Boolean zzarv = null;
    public String zzarw = null;
    public String zzarx = null;
    public String zzary = null;

    public zzkb() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzabj
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzkb zzb(zzaba zzabaVar) throws zzabi {
        while (true) {
            int iZzvo = zzabaVar.zzvo();
            switch (iZzvo) {
                case 0:
                    break;
                case 8:
                    int position = zzabaVar.getPosition();
                    try {
                        int iZzvs = zzabaVar.zzvs();
                        if (iZzvs < 0 || iZzvs > 4) {
                            throw new IllegalArgumentException(new StringBuilder(46).append(iZzvs).append(" is not a valid enum ComparisonType").toString());
                        }
                        this.zzaru = Integer.valueOf(iZzvs);
                    } catch (IllegalArgumentException e) {
                        zzabaVar.zzao(position);
                        zza(zzabaVar, iZzvo);
                    }
                    break;
                case 16:
                    this.zzarv = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                case 26:
                    this.zzarw = zzabaVar.readString();
                    break;
                case 34:
                    this.zzarx = zzabaVar.readString();
                    break;
                case 42:
                    this.zzary = zzabaVar.readString();
                    break;
                default:
                    if (!super.zza(zzabaVar, iZzvo)) {
                    }
                    break;
            }
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkb)) {
            return false;
        }
        zzkb zzkbVar = (zzkb) obj;
        if (this.zzaru == null) {
            if (zzkbVar.zzaru != null) {
                return false;
            }
        } else if (!this.zzaru.equals(zzkbVar.zzaru)) {
            return false;
        }
        if (this.zzarv == null) {
            if (zzkbVar.zzarv != null) {
                return false;
            }
        } else if (!this.zzarv.equals(zzkbVar.zzarv)) {
            return false;
        }
        if (this.zzarw == null) {
            if (zzkbVar.zzarw != null) {
                return false;
            }
        } else if (!this.zzarw.equals(zzkbVar.zzarw)) {
            return false;
        }
        if (this.zzarx == null) {
            if (zzkbVar.zzarx != null) {
                return false;
            }
        } else if (!this.zzarx.equals(zzkbVar.zzarx)) {
            return false;
        }
        if (this.zzary == null) {
            if (zzkbVar.zzary != null) {
                return false;
            }
        } else if (!this.zzary.equals(zzkbVar.zzary)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkbVar.zzbzh == null || zzkbVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkbVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((this.zzary == null ? 0 : this.zzary.hashCode()) + (((this.zzarx == null ? 0 : this.zzarx.hashCode()) + (((this.zzarw == null ? 0 : this.zzarw.hashCode()) + (((this.zzarv == null ? 0 : this.zzarv.hashCode()) + (((this.zzaru == null ? 0 : this.zzaru.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzaru != null) {
            iZza += zzabb.zzf(1, this.zzaru.intValue());
        }
        if (this.zzarv != null) {
            this.zzarv.booleanValue();
            iZza += zzabb.zzas(2) + 1;
        }
        if (this.zzarw != null) {
            iZza += zzabb.zzd(3, this.zzarw);
        }
        if (this.zzarx != null) {
            iZza += zzabb.zzd(4, this.zzarx);
        }
        return this.zzary != null ? iZza + zzabb.zzd(5, this.zzary) : iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzaru != null) {
            zzabbVar.zze(1, this.zzaru.intValue());
        }
        if (this.zzarv != null) {
            zzabbVar.zza(2, this.zzarv.booleanValue());
        }
        if (this.zzarw != null) {
            zzabbVar.zzc(3, this.zzarw);
        }
        if (this.zzarx != null) {
            zzabbVar.zzc(4, this.zzarx);
        }
        if (this.zzary != null) {
            zzabbVar.zzc(5, this.zzary);
        }
        super.zza(zzabbVar);
    }
}
