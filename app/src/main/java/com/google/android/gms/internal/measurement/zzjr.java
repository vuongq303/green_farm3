package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzjr extends zzem {
    private final /* synthetic */ zzgl zzafi;
    private final /* synthetic */ zzjq zzaqt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzjr(zzjq zzjqVar, zzgl zzglVar, zzgl zzglVar2) {
        super(zzglVar);
        this.zzaqt = zzjqVar;
        this.zzafi = zzglVar2;
    }

    @Override // com.google.android.gms.internal.measurement.zzem
    public final void run() {
        this.zzaqt.cancel();
        this.zzaqt.zzgg().zzir().log("Starting upload from DelayedRunnable");
        this.zzafi.zzjw();
    }
}
