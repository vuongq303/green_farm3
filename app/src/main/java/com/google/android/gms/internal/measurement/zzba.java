package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzba implements Runnable {
    private final /* synthetic */ zzci zzwn;
    private final /* synthetic */ zzaz zzwo;

    zzba(zzaz zzazVar, zzci zzciVar) {
        this.zzwo = zzazVar;
        this.zzwn = zzciVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzwo.zzwk.isConnected()) {
            return;
        }
        this.zzwo.zzwk.zzr("Connected to service after a timeout");
        this.zzwo.zzwk.zza(this.zzwn);
    }
}
