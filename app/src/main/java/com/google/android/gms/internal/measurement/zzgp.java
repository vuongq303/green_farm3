package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzgp implements zzfm {
    private final /* synthetic */ zzgl zzanp;

    zzgp(zzgl zzglVar) {
        this.zzanp = zzglVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzfm
    public final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        this.zzanp.zzb(str, i, th, bArr, map);
    }
}
