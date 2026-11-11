package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzvl implements com.google.android.gms.ads.internal.gmsg.zzv<zzwb> {
    private final /* synthetic */ zzuu zzbqj;
    private final /* synthetic */ zzvf zzbqk;
    private final /* synthetic */ zzci zzbql;
    private final /* synthetic */ zzamk zzbqm;

    zzvl(zzvf zzvfVar, zzci zzciVar, zzuu zzuuVar, zzamk zzamkVar) {
        this.zzbqk = zzvfVar;
        this.zzbql = zzciVar;
        this.zzbqj = zzuuVar;
        this.zzbqm = zzamkVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzwb zzwbVar, Map map) {
        synchronized (this.zzbqk.mLock) {
            zzakb.zzdj("JS Engine is requesting an update");
            if (this.zzbqk.zzbqb == 0) {
                zzakb.zzdj("Starting reload.");
                this.zzbqk.zzbqb = 2;
                this.zzbqk.zza(this.zzbql);
            }
            this.zzbqj.zzb("/requestReload", (com.google.android.gms.ads.internal.gmsg.zzv<? super zzwb>) this.zzbqm.get());
        }
    }
}
