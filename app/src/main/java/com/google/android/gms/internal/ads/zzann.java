package com.google.android.gms.internal.ads;

import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
final class zzann implements zzanl {
    private final /* synthetic */ String zzcvi;

    zzann(String str) {
        this.zzcvi = str;
    }

    @Override // com.google.android.gms.internal.ads.zzanl
    public final void zzb(Throwable th) {
        com.google.android.gms.ads.internal.zzbv.zzeo().zza(th, this.zzcvi);
    }

    @Override // com.google.android.gms.internal.ads.zzanl
    public final void zzh(@Nullable Object obj) {
    }
}
