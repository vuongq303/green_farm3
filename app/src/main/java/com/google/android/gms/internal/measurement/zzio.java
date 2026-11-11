package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zzio implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzil zzapy;
    private final /* synthetic */ AtomicReference zzapz;

    zzio(zzil zzilVar, AtomicReference atomicReference, zzec zzecVar) {
        this.zzapy = zzilVar;
        this.zzapz = atomicReference;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzapz) {
            try {
                try {
                    zzey zzeyVar = this.zzapy.zzaps;
                    if (zzeyVar == null) {
                        this.zzapy.zzgg().zzil().log("Failed to get app instance id");
                        this.zzapz.notify();
                        return;
                    }
                    this.zzapz.set(zzeyVar.zzc(this.zzanq));
                    String str = (String) this.zzapz.get();
                    if (str != null) {
                        this.zzapy.zzfu().zzbm(str);
                        this.zzapy.zzgh().zzaka.zzbn(str);
                    }
                    this.zzapy.zzcu();
                    this.zzapz.notify();
                } catch (RemoteException e) {
                    this.zzapy.zzgg().zzil().zzg("Failed to get app instance id", e);
                    this.zzapz.notify();
                }
            } catch (Throwable th) {
                this.zzapz.notify();
                throw th;
            }
        }
    }
}
