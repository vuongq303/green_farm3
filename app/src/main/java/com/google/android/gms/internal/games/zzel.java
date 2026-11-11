package com.google.android.gms.internal.games;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzel {
    private final AtomicReference<zzej> zzkw = new AtomicReference<>();

    public final void flush() {
        zzej zzejVar = this.zzkw.get();
        if (zzejVar != null) {
            zzejVar.flush();
        }
    }

    public final void zza(String str, int i) {
        zzej zzejVarZzbe = this.zzkw.get();
        if (zzejVarZzbe == null) {
            zzejVarZzbe = zzbe();
            if (!this.zzkw.compareAndSet(null, zzejVarZzbe)) {
                zzejVarZzbe = this.zzkw.get();
            }
        }
        zzejVarZzbe.zzg(str, i);
    }

    protected abstract zzej zzbe();
}
