package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
abstract class zzhk extends zzhj {
    private boolean zzvj;

    zzhk(zzgl zzglVar) {
        super(zzglVar);
        this.zzacr.zzb(this);
    }

    final boolean isInitialized() {
        return this.zzvj;
    }

    protected final void zzch() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    protected abstract boolean zzhh();

    protected void zzig() {
    }

    public final void zzkd() {
        if (this.zzvj) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzig();
        this.zzacr.zzjz();
        this.zzvj = true;
    }

    public final void zzm() {
        if (this.zzvj) {
            throw new IllegalStateException("Can't initialize twice");
        }
        if (zzhh()) {
            return;
        }
        this.zzacr.zzjz();
        this.zzvj = true;
    }
}
