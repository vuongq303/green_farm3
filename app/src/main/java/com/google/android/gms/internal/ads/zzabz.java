package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzabz implements com.google.android.gms.ads.internal.gmsg.zzv<Object> {
    private final /* synthetic */ zzabv zzcal;
    private final /* synthetic */ zzos zzcam;

    zzabz(zzabv zzabvVar, zzos zzosVar) {
        this.zzcal = zzabvVar;
        this.zzcam = zzosVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        this.zzcal.zzc(this.zzcam, map.get("asset"));
    }
}
