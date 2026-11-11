package com.google.android.gms.internal.ads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes.dex */
public final class zzdt implements Callable {
    private final zzcz zzps;
    private final zzba zztq;

    public zzdt(zzcz zzczVar, zzba zzbaVar) {
        this.zzps = zzczVar;
        this.zztq = zzbaVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: zzat, reason: merged with bridge method [inline-methods] */
    public final Void call() throws ExecutionException, InterruptedException {
        if (this.zzps.zzak() != null) {
            this.zzps.zzak().get();
        }
        zzba zzbaVarZzaj = this.zzps.zzaj();
        if (zzbaVarZzaj == null) {
            return null;
        }
        try {
            synchronized (this.zztq) {
                zzbfi.zza(this.zztq, zzbfi.zzb(zzbaVarZzaj));
            }
            return null;
        } catch (zzbfh e) {
            return null;
        }
    }
}
