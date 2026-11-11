package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzkc extends zzabd<zzkc> {
    private static volatile zzkc[] zzarz;
    public Integer zzark = null;
    public String zzasa = null;
    public zzka zzasb = null;

    public zzkc() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzkc[] zzkz() {
        if (zzarz == null) {
            synchronized (zzabh.zzbzr) {
                if (zzarz == null) {
                    zzarz = new zzkc[0];
                }
            }
        }
        return zzarz;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkc)) {
            return false;
        }
        zzkc zzkcVar = (zzkc) obj;
        if (this.zzark == null) {
            if (zzkcVar.zzark != null) {
                return false;
            }
        } else if (!this.zzark.equals(zzkcVar.zzark)) {
            return false;
        }
        if (this.zzasa == null) {
            if (zzkcVar.zzasa != null) {
                return false;
            }
        } else if (!this.zzasa.equals(zzkcVar.zzasa)) {
            return false;
        }
        if (this.zzasb == null) {
            if (zzkcVar.zzasb != null) {
                return false;
            }
        } else if (!this.zzasb.equals(zzkcVar.zzasb)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkcVar.zzbzh == null || zzkcVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkcVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = (this.zzasa == null ? 0 : this.zzasa.hashCode()) + (((this.zzark == null ? 0 : this.zzark.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31);
        zzka zzkaVar = this.zzasb;
        int iHashCode3 = ((zzkaVar == null ? 0 : zzkaVar.hashCode()) + (iHashCode2 * 31)) * 31;
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
        if (this.zzasa != null) {
            iZza += zzabb.zzd(2, this.zzasa);
        }
        return this.zzasb != null ? iZza + zzabb.zzb(3, this.zzasb) : iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzark != null) {
            zzabbVar.zze(1, this.zzark.intValue());
        }
        if (this.zzasa != null) {
            zzabbVar.zzc(2, this.zzasa);
        }
        if (this.zzasb != null) {
            zzabbVar.zza(3, this.zzasb);
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
                    this.zzasa = zzabaVar.readString();
                    break;
                case 26:
                    if (this.zzasb == null) {
                        this.zzasb = new zzka();
                    }
                    zzabaVar.zza(this.zzasb);
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
