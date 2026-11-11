package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zzhz implements Runnable {
    private final /* synthetic */ AtomicReference zzaoo;
    private final /* synthetic */ zzhm zzaop;

    zzhz(zzhm zzhmVar, AtomicReference atomicReference) {
        this.zzaop = zzhmVar;
        this.zzaoo = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            AtomicReference atomicReference = this.zzaoo;
            zzeh zzehVarZzgi = this.zzaop.zzgi();
            atomicReference.set(Integer.valueOf(zzehVarZzgi.zzb(zzehVarZzgi.zzfv().zzah(), zzew.zzahv)));
        } finally {
            this.zzaoo.notify();
        }
    }
}
