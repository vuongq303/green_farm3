package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzka extends zzabd<zzka> {
    private static volatile zzka[] zzarp;
    public zzkd zzarq = null;
    public zzkb zzarr = null;
    public Boolean zzars = null;
    public String zzart = null;

    public zzka() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzka[] zzky() {
        if (zzarp == null) {
            synchronized (zzabh.zzbzr) {
                if (zzarp == null) {
                    zzarp = new zzka[0];
                }
            }
        }
        return zzarp;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzka)) {
            return false;
        }
        zzka zzkaVar = (zzka) obj;
        if (this.zzarq == null) {
            if (zzkaVar.zzarq != null) {
                return false;
            }
        } else if (!this.zzarq.equals(zzkaVar.zzarq)) {
            return false;
        }
        if (this.zzarr == null) {
            if (zzkaVar.zzarr != null) {
                return false;
            }
        } else if (!this.zzarr.equals(zzkaVar.zzarr)) {
            return false;
        }
        if (this.zzars == null) {
            if (zzkaVar.zzars != null) {
                return false;
            }
        } else if (!this.zzars.equals(zzkaVar.zzars)) {
            return false;
        }
        if (this.zzart == null) {
            if (zzkaVar.zzart != null) {
                return false;
            }
        } else if (!this.zzart.equals(zzkaVar.zzart)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkaVar.zzbzh == null || zzkaVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkaVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = getClass().getName().hashCode() + 527;
        zzkd zzkdVar = this.zzarq;
        int i = iHashCode2 * 31;
        int iHashCode3 = zzkdVar == null ? 0 : zzkdVar.hashCode();
        zzkb zzkbVar = this.zzarr;
        int iHashCode4 = ((this.zzart == null ? 0 : this.zzart.hashCode()) + (((this.zzars == null ? 0 : this.zzars.hashCode()) + (((zzkbVar == null ? 0 : zzkbVar.hashCode()) + ((iHashCode3 + i) * 31)) * 31)) * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode4 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzarq != null) {
            iZza += zzabb.zzb(1, this.zzarq);
        }
        if (this.zzarr != null) {
            iZza += zzabb.zzb(2, this.zzarr);
        }
        if (this.zzars != null) {
            this.zzars.booleanValue();
            iZza += zzabb.zzas(3) + 1;
        }
        return this.zzart != null ? iZza + zzabb.zzd(4, this.zzart) : iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzarq != null) {
            zzabbVar.zza(1, this.zzarq);
        }
        if (this.zzarr != null) {
            zzabbVar.zza(2, this.zzarr);
        }
        if (this.zzars != null) {
            zzabbVar.zza(3, this.zzars.booleanValue());
        }
        if (this.zzart != null) {
            zzabbVar.zzc(4, this.zzart);
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
                    if (this.zzarq == null) {
                        this.zzarq = new zzkd();
                    }
                    zzabaVar.zza(this.zzarq);
                    break;
                case 18:
                    if (this.zzarr == null) {
                        this.zzarr = new zzkb();
                    }
                    zzabaVar.zza(this.zzarr);
                    break;
                case 24:
                    this.zzars = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                case 34:
                    this.zzart = zzabaVar.readString();
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
