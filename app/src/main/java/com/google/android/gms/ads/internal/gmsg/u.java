package com.google.android.gms.ads.internal.gmsg;

import com.google.android.gms.internal.ads.zzakb;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class u implements Runnable {
    private final /* synthetic */ JSONObject bAi;
    private final /* synthetic */ t bAj;

    u(t tVar, JSONObject jSONObject) {
        this.bAj = tVar;
        this.bAi = jSONObject;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bAj.bAg.zza("fetchHttpRequestCompleted", this.bAi);
        zzakb.zzck("Dispatched http response.");
    }
}
