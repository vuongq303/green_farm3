package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzbe extends zzar {
    private final zzu zzsa;

    zzbe(zzat zzatVar) {
        super(zzatVar);
        this.zzsa = new zzu();
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
        zzbw().Xx().zzb(this.zzsa);
        zzde zzdeVarZzca = zzca();
        String strZzaf = zzdeVarZzca.zzaf();
        if (strZzaf != null) {
            this.zzsa.setAppName(strZzaf);
        }
        String strZzag = zzdeVarZzca.zzag();
        if (strZzag != null) {
            this.zzsa.setAppVersion(strZzag);
        }
    }

    public final zzu zzdb() {
        zzch();
        return this.zzsa;
    }
}
