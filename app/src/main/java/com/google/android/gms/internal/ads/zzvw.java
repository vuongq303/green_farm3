package com.google.android.gms.internal.ads;

import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzvw extends zzaop<zzuu> {
    private zzalo<zzuu> zzbpz;
    private final Object mLock = new Object();
    private boolean zzbqt = false;
    private int zzbqu = 0;

    public zzvw(zzalo<zzuu> zzaloVar) {
        this.zzbpz = zzaloVar;
    }

    private final void zzmc() {
        synchronized (this.mLock) {
            bf.bZ(this.zzbqu >= 0);
            if (this.zzbqt && this.zzbqu == 0) {
                zzakb.v("No reference is left (including root). Cleaning up engine.");
                zza(new zzvz(this), new zzaon());
            } else {
                zzakb.v("There are still references to the engine. Not destroying.");
            }
        }
    }

    public final zzvs zzlz() {
        zzvs zzvsVar = new zzvs(this);
        synchronized (this.mLock) {
            zza(new zzvx(this, zzvsVar), new zzvy(this, zzvsVar));
            bf.bZ(this.zzbqu >= 0);
            this.zzbqu++;
        }
        return zzvsVar;
    }

    protected final void zzma() {
        synchronized (this.mLock) {
            bf.bZ(this.zzbqu > 0);
            zzakb.v("Releasing 1 reference for JS Engine");
            this.zzbqu--;
            zzmc();
        }
    }

    public final void zzmb() {
        synchronized (this.mLock) {
            bf.bZ(this.zzbqu >= 0);
            zzakb.v("Releasing root reference. JS Engine will be destroyed once other references are released.");
            this.zzbqt = true;
            zzmc();
        }
    }
}
