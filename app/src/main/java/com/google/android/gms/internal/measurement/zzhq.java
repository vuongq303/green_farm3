package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
final class zzhq implements Callable<String> {
    private final /* synthetic */ zzhm zzaop;

    zzhq(zzhm zzhmVar) {
        this.zzaop = zzhmVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws TimeoutException {
        String strZziw = this.zzaop.zzgh().zziw();
        if (strZziw == null) {
            zzhm zzhmVarZzfu = this.zzaop.zzfu();
            if (zzhmVarZzfu.zzgf().zzjg()) {
                zzhmVarZzfu.zzgg().zzil().log("Cannot retrieve app instance id from analytics worker thread");
                strZziw = null;
            } else {
                zzhmVarZzfu.zzgf();
                if (zzgg.isMainThread()) {
                    zzhmVarZzfu.zzgg().zzil().log("Cannot retrieve app instance id from main thread");
                    strZziw = null;
                } else {
                    long jElapsedRealtime = zzhmVarZzfu.zzbt().elapsedRealtime();
                    strZziw = zzhmVarZzfu.zzae(120000L);
                    long jElapsedRealtime2 = zzhmVarZzfu.zzbt().elapsedRealtime() - jElapsedRealtime;
                    if (strZziw == null && jElapsedRealtime2 < 120000) {
                        strZziw = zzhmVarZzfu.zzae(120000 - jElapsedRealtime2);
                    }
                }
            }
            if (strZziw == null) {
                throw new TimeoutException();
            }
            this.zzaop.zzgh().zzbm(strZziw);
        }
        return strZziw;
    }
}
