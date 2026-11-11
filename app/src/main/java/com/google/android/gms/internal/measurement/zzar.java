package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzar extends zzaq {
    private boolean zzvj;

    protected zzar(zzat zzatVar) {
        super(zzatVar);
    }

    public final boolean isInitialized() {
        return this.zzvj;
    }

    protected abstract void zzac();

    protected final void zzch() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzm() {
        zzac();
        this.zzvj = true;
    }
}
