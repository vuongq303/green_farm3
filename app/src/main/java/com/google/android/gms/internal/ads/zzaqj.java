package com.google.android.gms.internal.ads;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzaqj implements Runnable {
    private final /* synthetic */ String zzcce;
    private final /* synthetic */ String zzdba;
    private final /* synthetic */ int zzdbc;
    private final /* synthetic */ zzaqh zzdbe;

    zzaqj(zzaqh zzaqhVar, String str, String str2, int i) {
        this.zzdbe = zzaqhVar;
        this.zzcce = str;
        this.zzdba = str2;
        this.zzdbc = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        HashMap map = new HashMap();
        map.put("event", "precacheComplete");
        map.put("src", this.zzcce);
        map.put("cachedSrc", this.zzdba);
        map.put("totalBytes", Integer.toString(this.zzdbc));
        this.zzdbe.zza("onPrecacheEvent", (Map<String, String>) map);
    }
}
