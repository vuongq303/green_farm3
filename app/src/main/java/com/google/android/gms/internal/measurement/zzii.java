package com.google.android.gms.internal.measurement;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
final class zzii implements Runnable {
    private final /* synthetic */ boolean zzapl;
    private final /* synthetic */ zzig zzapm;
    private final /* synthetic */ zzik zzapn;
    private final /* synthetic */ zzih zzapo;

    zzii(zzih zzihVar, boolean z, zzig zzigVar, zzik zzikVar) {
        this.zzapo = zzihVar;
        this.zzapl = z;
        this.zzapm = zzigVar;
        this.zzapn = zzikVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzapl && this.zzapo.zzapc != null) {
            this.zzapo.zza(this.zzapo.zzapc);
        }
        if ((this.zzapm != null && this.zzapm.zzapb == this.zzapn.zzapb && zzjv.zzs(this.zzapm.zzapa, this.zzapn.zzapa) && zzjv.zzs(this.zzapm.zzug, this.zzapn.zzug)) ? false : true) {
            Bundle bundle = new Bundle();
            zzih.zza((zzig) this.zzapn, bundle, true);
            if (this.zzapm != null) {
                if (this.zzapm.zzug != null) {
                    bundle.putString("_pn", this.zzapm.zzug);
                }
                bundle.putString("_pc", this.zzapm.zzapa);
                bundle.putLong("_pi", this.zzapm.zzapb);
            }
            this.zzapo.zzfu().zza("auto", "_vs", bundle);
        }
        this.zzapo.zzapc = this.zzapn;
        this.zzapo.zzfx().zza(this.zzapn);
    }
}
