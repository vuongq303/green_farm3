package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzafu implements com.google.android.gms.ads.internal.gmsg.zzv<Object> {
    private final /* synthetic */ zzaft zzchv;

    zzafu(zzaft zzaftVar) {
        this.zzchv = zzaftVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        synchronized (this.zzchv.mLock) {
            if (this.zzchv.zzchr.isDone()) {
                return;
            }
            if (this.zzchv.zzchp.equals(map.get("request_id"))) {
                zzafz zzafzVar = new zzafz(1, map);
                String type = zzafzVar.getType();
                String strValueOf = String.valueOf(zzafzVar.zzoh());
                zzakb.zzdk(new StringBuilder(String.valueOf(type).length() + 24 + String.valueOf(strValueOf).length()).append("Invalid ").append(type).append(" request error: ").append(strValueOf).toString());
                this.zzchv.zzchr.set(zzafzVar);
            }
        }
    }
}
