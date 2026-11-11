package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzpi implements com.google.android.gms.ads.internal.gmsg.zzv<Object> {
    private final /* synthetic */ zzacm zzbji;
    private final /* synthetic */ zzpf zzbjj;

    zzpi(zzpf zzpfVar, zzacm zzacmVar) {
        this.zzbjj = zzpfVar;
        this.zzbji = zzacmVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        zzaqw zzaqwVar = (zzaqw) this.zzbjj.zzbjg.get();
        if (zzaqwVar == null) {
            this.zzbji.zzb("/showOverlay", this);
        } else {
            zzaqwVar.getView().setVisibility(0);
        }
    }
}
