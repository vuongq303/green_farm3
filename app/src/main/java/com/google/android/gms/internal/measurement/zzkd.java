package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzkd extends zzabd<zzkd> {
    public Integer zzasc = null;
    public String zzasd = null;
    public Boolean zzase = null;
    public String[] zzasf = zzabm.zzcac;

    public zzkd() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzabj
    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final zzkd zzb(zzaba zzabaVar) throws zzabi {
        while (true) {
            int iZzvo = zzabaVar.zzvo();
            switch (iZzvo) {
                case 0:
                    break;
                case 8:
                    int position = zzabaVar.getPosition();
                    try {
                        int iZzvs = zzabaVar.zzvs();
                        if (iZzvs < 0 || iZzvs > 6) {
                            throw new IllegalArgumentException(new StringBuilder(41).append(iZzvs).append(" is not a valid enum MatchType").toString());
                        }
                        this.zzasc = Integer.valueOf(iZzvs);
                    } catch (IllegalArgumentException e) {
                        zzabaVar.zzao(position);
                        zza(zzabaVar, iZzvo);
                    }
                    break;
                case 18:
                    this.zzasd = zzabaVar.readString();
                    break;
                case 24:
                    this.zzase = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                case 34:
                    int iZzb = zzabm.zzb(zzabaVar, 34);
                    int length = this.zzasf == null ? 0 : this.zzasf.length;
                    String[] strArr = new String[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzasf, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = zzabaVar.readString();
                        zzabaVar.zzvo();
                        length++;
                    }
                    strArr[length] = zzabaVar.readString();
                    this.zzasf = strArr;
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
        if (!(obj instanceof zzkd)) {
            return false;
        }
        zzkd zzkdVar = (zzkd) obj;
        if (this.zzasc == null) {
            if (zzkdVar.zzasc != null) {
                return false;
            }
        } else if (!this.zzasc.equals(zzkdVar.zzasc)) {
            return false;
        }
        if (this.zzasd == null) {
            if (zzkdVar.zzasd != null) {
                return false;
            }
        } else if (!this.zzasd.equals(zzkdVar.zzasd)) {
            return false;
        }
        if (this.zzase == null) {
            if (zzkdVar.zzase != null) {
                return false;
            }
        } else if (!this.zzase.equals(zzkdVar.zzase)) {
            return false;
        }
        if (!zzabh.equals(this.zzasf, zzkdVar.zzasf)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkdVar.zzbzh == null || zzkdVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkdVar.zzbzh);
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((((this.zzase == null ? 0 : this.zzase.hashCode()) + (((this.zzasd == null ? 0 : this.zzasd.hashCode()) + (((this.zzasc == null ? 0 : this.zzasc.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31) + zzabh.hashCode(this.zzasf)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZzfp;
        int iZza = super.zza();
        if (this.zzasc != null) {
            iZza += zzabb.zzf(1, this.zzasc.intValue());
        }
        if (this.zzasd != null) {
            iZza += zzabb.zzd(2, this.zzasd);
        }
        if (this.zzase != null) {
            this.zzase.booleanValue();
            iZza += zzabb.zzas(3) + 1;
        }
        if (this.zzasf == null || this.zzasf.length <= 0) {
            return iZza;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < this.zzasf.length) {
            String str = this.zzasf[i];
            if (str != null) {
                i3++;
                iZzfp = zzabb.zzfp(str) + i2;
            } else {
                iZzfp = i2;
            }
            i++;
            i2 = iZzfp;
        }
        return iZza + i2 + (i3 * 1);
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzasc != null) {
            zzabbVar.zze(1, this.zzasc.intValue());
        }
        if (this.zzasd != null) {
            zzabbVar.zzc(2, this.zzasd);
        }
        if (this.zzase != null) {
            zzabbVar.zza(3, this.zzase.booleanValue());
        }
        if (this.zzasf != null && this.zzasf.length > 0) {
            for (int i = 0; i < this.zzasf.length; i++) {
                String str = this.zzasf[i];
                if (str != null) {
                    zzabbVar.zzc(4, str);
                }
            }
        }
        super.zza(zzabbVar);
    }
}
