package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzir extends zzem {
    private final /* synthetic */ zzil zzapy;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzir(zzil zzilVar, zzgl zzglVar) {
        super(zzglVar);
        this.zzapy = zzilVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzem
    public final void run() {
        this.zzapy.zzgg().zzin().log("Tasks have been queued for a long time");
    }
}
