package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzgo implements zzfm {
    private final /* synthetic */ zzgl zzanp;
    private final /* synthetic */ String zzanr;

    zzgo(zzgl zzglVar, String str) {
        this.zzanp = zzglVar;
        this.zzanr = str;
    }

    @Override // com.google.android.gms.internal.measurement.zzfm
    public final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        this.zzanp.zza(i, th, bArr, this.zzanr);
    }
}
