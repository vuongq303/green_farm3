package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzkm extends zzabd<zzkm> {
    public long[] zzauf = zzabm.zzbzy;
    public long[] zzaug = zzabm.zzbzy;

    public zzkm() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkm)) {
            return false;
        }
        zzkm zzkmVar = (zzkm) obj;
        if (zzabh.equals(this.zzauf, zzkmVar.zzauf) && zzabh.equals(this.zzaug, zzkmVar.zzaug)) {
            if (this.zzbzh == null || this.zzbzh.isEmpty()) {
                return zzkmVar.zzbzh == null || zzkmVar.zzbzh.isEmpty();
            }
            return this.zzbzh.equals(zzkmVar.zzbzh);
        }
        return false;
    }

    public final int hashCode() {
        return ((this.zzbzh == null || this.zzbzh.isEmpty()) ? 0 : this.zzbzh.hashCode()) + ((((((getClass().getName().hashCode() + 527) * 31) + zzabh.hashCode(this.zzauf)) * 31) + zzabh.hashCode(this.zzaug)) * 31);
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int length;
        int iZza = super.zza();
        if (this.zzauf == null || this.zzauf.length <= 0) {
            length = iZza;
        } else {
            int iZzap = 0;
            for (int i = 0; i < this.zzauf.length; i++) {
                iZzap += zzabb.zzap(this.zzauf[i]);
            }
            length = iZza + iZzap + (this.zzauf.length * 1);
        }
        if (this.zzaug == null || this.zzaug.length <= 0) {
            return length;
        }
        int iZzap2 = 0;
        for (int i2 = 0; i2 < this.zzaug.length; i2++) {
            iZzap2 += zzabb.zzap(this.zzaug[i2]);
        }
        return length + iZzap2 + (this.zzaug.length * 1);
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzauf != null && this.zzauf.length > 0) {
            for (int i = 0; i < this.zzauf.length; i++) {
                zzabbVar.zza(1, this.zzauf[i]);
            }
        }
        if (this.zzaug != null && this.zzaug.length > 0) {
            for (int i2 = 0; i2 < this.zzaug.length; i2++) {
                zzabbVar.zza(2, this.zzaug[i2]);
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
                    int iZzb = zzabm.zzb(zzabaVar, 8);
                    int length = this.zzauf == null ? 0 : this.zzauf.length;
                    long[] jArr = new long[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzauf, 0, jArr, 0, length);
                    }
                    while (length < jArr.length - 1) {
                        jArr[length] = zzabaVar.zzvt();
                        zzabaVar.zzvo();
                        length++;
                    }
                    jArr[length] = zzabaVar.zzvt();
                    this.zzauf = jArr;
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    int iZzah = zzabaVar.zzah(zzabaVar.zzvs());
                    int position = zzabaVar.getPosition();
                    int i = 0;
                    while (zzabaVar.zzvw() > 0) {
                        zzabaVar.zzvt();
                        i++;
                    }
                    zzabaVar.zzao(position);
                    int length2 = this.zzauf == null ? 0 : this.zzauf.length;
                    long[] jArr2 = new long[i + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzauf, 0, jArr2, 0, length2);
                    }
                    while (length2 < jArr2.length) {
                        jArr2[length2] = zzabaVar.zzvt();
                        length2++;
                    }
                    this.zzauf = jArr2;
                    zzabaVar.zzan(iZzah);
                    break;
                case 16:
                    int iZzb2 = zzabm.zzb(zzabaVar, 16);
                    int length3 = this.zzaug == null ? 0 : this.zzaug.length;
                    long[] jArr3 = new long[iZzb2 + length3];
                    if (length3 != 0) {
                        System.arraycopy(this.zzaug, 0, jArr3, 0, length3);
                    }
                    while (length3 < jArr3.length - 1) {
                        jArr3[length3] = zzabaVar.zzvt();
                        zzabaVar.zzvo();
                        length3++;
                    }
                    jArr3[length3] = zzabaVar.zzvt();
                    this.zzaug = jArr3;
                    break;
                case 18:
                    int iZzah2 = zzabaVar.zzah(zzabaVar.zzvs());
                    int position2 = zzabaVar.getPosition();
                    int i2 = 0;
                    while (zzabaVar.zzvw() > 0) {
                        zzabaVar.zzvt();
                        i2++;
                    }
                    zzabaVar.zzao(position2);
                    int length4 = this.zzaug == null ? 0 : this.zzaug.length;
                    long[] jArr4 = new long[i2 + length4];
                    if (length4 != 0) {
                        System.arraycopy(this.zzaug, 0, jArr4, 0, length4);
                    }
                    while (length4 < jArr4.length) {
                        jArr4[length4] = zzabaVar.zzvt();
                        length4++;
                    }
                    this.zzaug = jArr4;
                    zzabaVar.zzan(iZzah2);
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
