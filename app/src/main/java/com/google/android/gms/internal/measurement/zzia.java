package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zzia implements Runnable {
    private final /* synthetic */ AtomicReference zzaoo;
    private final /* synthetic */ zzhm zzaop;

    zzia(zzhm zzhmVar, AtomicReference atomicReference) {
        this.zzaop = zzhmVar;
        this.zzaoo = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            AtomicReference atomicReference = this.zzaoo;
            zzeh zzehVarZzgi = this.zzaop.zzgi();
            atomicReference.set(Double.valueOf(zzehVarZzgi.zzc(zzehVarZzgi.zzfv().zzah(), zzew.zzahw)));
        } finally {
            this.zzaoo.notify();
        }
    }
}
