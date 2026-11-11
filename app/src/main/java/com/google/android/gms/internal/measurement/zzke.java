package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzke extends zzabd<zzke> {
    private static volatile zzke[] zzasg;
    public String name = null;
    public Boolean zzash = null;
    public Boolean zzasi = null;
    public Integer zzasj = null;

    public zzke() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzke[] zzla() {
        if (zzasg == null) {
            synchronized (zzabh.zzbzr) {
                if (zzasg == null) {
                    zzasg = new zzke[0];
                }
            }
        }
        return zzasg;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzke)) {
            return false;
        }
        zzke zzkeVar = (zzke) obj;
        if (this.name == null) {
            if (zzkeVar.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzkeVar.name)) {
            return false;
        }
        if (this.zzash == null) {
            if (zzkeVar.zzash != null) {
                return false;
            }
        } else if (!this.zzash.equals(zzkeVar.zzash)) {
            return false;
        }
        if (this.zzasi == null) {
            if (zzkeVar.zzasi != null) {
                return false;
            }
        } else if (!this.zzasi.equals(zzkeVar.zzasi)) {
            return false;
        }
        if (this.zzasj == null) {
            if (zzkeVar.zzasj != null) {
                return false;
            }
        } else if (!this.zzasj.equals(zzkeVar.zzasj)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkeVar.zzbzh == null || zzkeVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkeVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((this.zzasj == null ? 0 : this.zzasj.hashCode()) + (((this.zzasi == null ? 0 : this.zzasi.hashCode()) + (((this.zzash == null ? 0 : this.zzash.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31;
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
        if (this.zzash != null) {
            this.zzash.booleanValue();
            iZza += zzabb.zzas(2) + 1;
        }
        if (this.zzasi != null) {
            this.zzasi.booleanValue();
            iZza += zzabb.zzas(3) + 1;
        }
        return this.zzasj != null ? iZza + zzabb.zzf(4, this.zzasj.intValue()) : iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.name != null) {
            zzabbVar.zzc(1, this.name);
        }
        if (this.zzash != null) {
            zzabbVar.zza(2, this.zzash.booleanValue());
        }
        if (this.zzasi != null) {
            zzabbVar.zza(3, this.zzasi.booleanValue());
        }
        if (this.zzasj != null) {
            zzabbVar.zze(4, this.zzasj.intValue());
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
                case 16:
                    this.zzash = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                case 24:
                    this.zzasi = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                case 32:
                    this.zzasj = Integer.valueOf(zzabaVar.zzvs());
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
