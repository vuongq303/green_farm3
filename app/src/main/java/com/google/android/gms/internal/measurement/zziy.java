package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zziy implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ boolean zzaos;
    private final /* synthetic */ zzil zzapy;
    private final /* synthetic */ AtomicReference zzapz;

    zziy(zzil zzilVar, AtomicReference atomicReference, zzec zzecVar, boolean z) {
        this.zzapy = zzilVar;
        this.zzapz = atomicReference;
        this.zzanq = zzecVar;
        this.zzaos = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzapz) {
            try {
                try {
                    zzey zzeyVar = this.zzapy.zzaps;
                    if (zzeyVar == null) {
                        this.zzapy.zzgg().zzil().log("Failed to get user properties");
                        this.zzapz.notify();
                    } else {
                        this.zzapz.set(zzeyVar.zza(this.zzanq, this.zzaos));
                        this.zzapy.zzcu();
                        this.zzapz.notify();
                    }
                } catch (RemoteException e) {
                    this.zzapy.zzgg().zzil().zzg("Failed to get user properties", e);
                    this.zzapz.notify();
                }
            } catch (Throwable th) {
                this.zzapz.notify();
                throw th;
            }
        }
    }
}
