package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import android.text.TextUtils;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class zziw implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ String zzaoa;
    private final /* synthetic */ String zzaob;
    private final /* synthetic */ String zzaoc;
    private final /* synthetic */ boolean zzaos;
    private final /* synthetic */ zzil zzapy;
    private final /* synthetic */ AtomicReference zzapz;

    zziw(zzil zzilVar, AtomicReference atomicReference, String str, String str2, String str3, boolean z, zzec zzecVar) {
        this.zzapy = zzilVar;
        this.zzapz = atomicReference;
        this.zzaoc = str;
        this.zzaoa = str2;
        this.zzaob = str3;
        this.zzaos = z;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzapz) {
            try {
                try {
                    zzey zzeyVar = this.zzapy.zzaps;
                    if (zzeyVar == null) {
                        this.zzapy.zzgg().zzil().zzd("Failed to get user properties", zzfg.zzbh(this.zzaoc), this.zzaoa, this.zzaob);
                        this.zzapz.set(Collections.emptyList());
                        this.zzapz.notify();
                    } else {
                        if (TextUtils.isEmpty(this.zzaoc)) {
                            this.zzapz.set(zzeyVar.zza(this.zzaoa, this.zzaob, this.zzaos, this.zzanq));
                        } else {
                            this.zzapz.set(zzeyVar.zza(this.zzaoc, this.zzaoa, this.zzaob, this.zzaos));
                        }
                        this.zzapy.zzcu();
                        this.zzapz.notify();
                    }
                } catch (RemoteException e) {
                    this.zzapy.zzgg().zzil().zzd("Failed to get user properties", zzfg.zzbh(this.zzaoc), this.zzaoa, e);
                    this.zzapz.set(Collections.emptyList());
                    this.zzapz.notify();
                }
            } catch (Throwable th) {
                this.zzapz.notify();
                throw th;
            }
        }
    }
}
