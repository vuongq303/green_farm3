package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzabd;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzabd<M extends zzabd<M>> extends zzabj {
    protected zzabf zzbzh;

    @Override // com.google.android.gms.internal.measurement.zzabj
    public /* synthetic */ Object clone() {
        zzabd zzabdVar = (zzabd) super.clone();
        zzabh.zza(this, zzabdVar);
        return zzabdVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzabj
    protected int zza() {
        int iZza = 0;
        if (this.zzbzh == null) {
            return 0;
        }
        int i = 0;
        while (true) {
            int i2 = iZza;
            if (i >= this.zzbzh.size()) {
                return i2;
            }
            iZza = this.zzbzh.zzaw(i).zza() + i2;
            i++;
        }
    }

    public final <T> T zza(zzabe<M, T> zzabeVar) {
        zzabg zzabgVarZzav;
        if (this.zzbzh == null || (zzabgVarZzav = this.zzbzh.zzav(zzabeVar.tag >>> 3)) == null) {
            return null;
        }
        return (T) zzabgVarZzav.zzb(zzabeVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzabj
    public void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzbzh == null) {
            return;
        }
        for (int i = 0; i < this.zzbzh.size(); i++) {
            this.zzbzh.zzaw(i).zza(zzabbVar);
        }
    }

    protected final boolean zza(zzaba zzabaVar, int i) throws zzabi {
        int position = zzabaVar.getPosition();
        if (!zzabaVar.zzam(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzabl zzablVar = new zzabl(i, zzabaVar.zzc(position, zzabaVar.getPosition() - position));
        zzabg zzabgVarZzav = null;
        if (this.zzbzh == null) {
            this.zzbzh = new zzabf();
        } else {
            zzabgVarZzav = this.zzbzh.zzav(i2);
        }
        if (zzabgVarZzav == null) {
            zzabgVarZzav = new zzabg();
            this.zzbzh.zza(i2, zzabgVarZzav);
        }
        zzabgVarZzav.zza(zzablVar);
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzabj
    /* JADX INFO: renamed from: zzvz */
    public final /* synthetic */ zzabj clone() {
        return (zzabd) clone();
    }
}
