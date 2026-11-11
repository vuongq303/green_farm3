package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zzhx implements Runnable {
    private final /* synthetic */ AtomicReference zzaoo;
    private final /* synthetic */ zzhm zzaop;

    zzhx(zzhm zzhmVar, AtomicReference atomicReference) {
        this.zzaop = zzhmVar;
        this.zzaoo = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            AtomicReference atomicReference = this.zzaoo;
            zzeh zzehVarZzgi = this.zzaop.zzgi();
            String strZzah = zzehVarZzgi.zzfv().zzah();
            zzex<String> zzexVar = zzew.zzaht;
            atomicReference.set(strZzah == null ? zzexVar.get() : zzexVar.get(zzehVarZzgi.zzgd().zzm(strZzah, zzexVar.getKey())));
        } finally {
            this.zzaoo.notify();
        }
    }
}
