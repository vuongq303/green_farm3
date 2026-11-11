package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzkg extends zzabd<zzkg> {
    private static volatile zzkg[] zzasp;
    public String zznt = null;
    public String value = null;

    public zzkg() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzkg[] zzlb() {
        if (zzasp == null) {
            synchronized (zzabh.zzbzr) {
                if (zzasp == null) {
                    zzasp = new zzkg[0];
                }
            }
        }
        return zzasp;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkg)) {
            return false;
        }
        zzkg zzkgVar = (zzkg) obj;
        if (this.zznt == null) {
            if (zzkgVar.zznt != null) {
                return false;
            }
        } else if (!this.zznt.equals(zzkgVar.zznt)) {
            return false;
        }
        if (this.value == null) {
            if (zzkgVar.value != null) {
                return false;
            }
        } else if (!this.value.equals(zzkgVar.value)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkgVar.zzbzh == null || zzkgVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkgVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((this.value == null ? 0 : this.value.hashCode()) + (((this.zznt == null ? 0 : this.zznt.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zznt != null) {
            iZza += zzabb.zzd(1, this.zznt);
        }
        return this.value != null ? iZza + zzabb.zzd(2, this.value) : iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zznt != null) {
            zzabbVar.zzc(1, this.zznt);
        }
        if (this.value != null) {
            zzabbVar.zzc(2, this.value);
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
                    this.zznt = zzabaVar.readString();
                    break;
                case 18:
                    this.value = zzabaVar.readString();
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
