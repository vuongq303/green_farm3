package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzafv implements com.google.android.gms.ads.internal.gmsg.zzv<Object> {
    private final /* synthetic */ zzaft zzchv;

    zzafv(zzaft zzaftVar) {
        this.zzchv = zzaftVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        synchronized (this.zzchv.mLock) {
            if (this.zzchv.zzchr.isDone()) {
                return;
            }
            zzafz zzafzVar = new zzafz(-2, map);
            if (this.zzchv.zzchp.equals(zzafzVar.zzol())) {
                String url = zzafzVar.getUrl();
                if (url == null) {
                    zzakb.zzdk("URL missing in loadAdUrl GMSG.");
                    return;
                }
                if (url.contains("%40mediation_adapters%40")) {
                    String strReplaceAll = url.replaceAll("%40mediation_adapters%40", zzajw.zzc(this.zzchv.mContext, map.get("check_adapters"), this.zzchv.zzchq));
                    zzafzVar.setUrl(strReplaceAll);
                    String strValueOf = String.valueOf(strReplaceAll);
                    zzakb.v(strValueOf.length() != 0 ? "Ad request URL modified to ".concat(strValueOf) : new String("Ad request URL modified to "));
                }
                this.zzchv.zzchr.set(zzafzVar);
            }
        }
    }
}
