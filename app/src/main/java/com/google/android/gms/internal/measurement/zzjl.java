package com.google.android.gms.internal.measurement;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
final class zzjl extends zzem {
    private final /* synthetic */ zzjk zzaqr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzjl(zzjk zzjkVar, zzgl zzglVar) {
        super(zzglVar);
        this.zzaqr = zzjkVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzem
    public final void run() {
        zzjk zzjkVar = this.zzaqr;
        zzjkVar.zzab();
        zzjkVar.zzgg().zzir().zzg("Session started, time", Long.valueOf(zzjkVar.zzbt().elapsedRealtime()));
        zzjkVar.zzgh().zzakj.set(false);
        zzjkVar.zzfu().zza("auto", "_s", new Bundle());
        zzjkVar.zzgh().zzakk.set(zzjkVar.zzbt().currentTimeMillis());
    }
}
