package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzkk extends zzabd<zzkk> {
    public zzkl[] zzata = zzkl.zzlf();

    public zzkk() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkk)) {
            return false;
        }
        zzkk zzkkVar = (zzkk) obj;
        if (!zzabh.equals(this.zzata, zzkkVar.zzata)) {
            return false;
        }
        if (this.zzbzh == null || this.zzbzh.isEmpty()) {
            return zzkkVar.zzbzh == null || zzkkVar.zzbzh.isEmpty();
        }
        return this.zzbzh.equals(zzkkVar.zzbzh);
    }

    public final int hashCode() {
        return ((this.zzbzh == null || this.zzbzh.isEmpty()) ? 0 : this.zzbzh.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + zzabh.hashCode(this.zzata)) * 31);
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzata != null && this.zzata.length > 0) {
            for (int i = 0; i < this.zzata.length; i++) {
                zzkl zzklVar = this.zzata[i];
                if (zzklVar != null) {
                    iZza += zzabb.zzb(1, zzklVar);
                }
            }
        }
        return iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzata != null && this.zzata.length > 0) {
            for (int i = 0; i < this.zzata.length; i++) {
                zzkl zzklVar = this.zzata[i];
                if (zzklVar != null) {
                    zzabbVar.zza(1, zzklVar);
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
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    int iZzb = zzabm.zzb(zzabaVar, 10);
                    int length = this.zzata == null ? 0 : this.zzata.length;
                    zzkl[] zzklVarArr = new zzkl[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzata, 0, zzklVarArr, 0, length);
                    }
                    while (length < zzklVarArr.length - 1) {
                        zzklVarArr[length] = new zzkl();
                        zzabaVar.zza(zzklVarArr[length]);
                        zzabaVar.zzvo();
                        length++;
                    }
                    zzklVarArr[length] = new zzkl();
                    zzabaVar.zza(zzklVarArr[length]);
                    this.zzata = zzklVarArr;
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
