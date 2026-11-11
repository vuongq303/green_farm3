package com.google.android.gms.internal.ads;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: loaded from: classes.dex */
final class zzalv<T> implements zzanj<Throwable, T> {
    private final /* synthetic */ zzalz zzcti;

    zzalv(zzalt zzaltVar, zzalz zzalzVar) {
        this.zzcti = zzalzVar;
    }

    @Override // com.google.android.gms.internal.ads.zzanj
    public final /* synthetic */ zzanz zzc(Throwable th) {
        Throwable th2 = th;
        zzakb.zzb("Error occurred while dispatching http response in getter.", th2);
        com.google.android.gms.ads.internal.zzbv.zzeo().zza(th2, "HttpGetter.deliverResponse.1");
        return zzano.zzi(this.zzcti.zzny());
    }
}
