package com.google.android.gms.ads.internal.gmsg;

import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzue;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class t implements Runnable {
    private final /* synthetic */ Map bAf;
    final /* synthetic */ zzue bAg;
    private final /* synthetic */ HttpClient bAh;

    t(HttpClient httpClient, Map map, zzue zzueVar) {
        this.bAh = httpClient;
        this.bAf = map;
        this.bAg = zzueVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzakb.zzck("Received Http request.");
        try {
            JSONObject jSONObjectSend = this.bAh.send(new JSONObject((String) this.bAf.get("http_request")));
            if (jSONObjectSend == null) {
                zzakb.e("Response should not be null.");
            } else {
                zzakk.zzcrm.post(new u(this, jSONObjectSend));
            }
        } catch (Exception e) {
            zzakb.zzb("Error converting request to json.", e);
        }
    }
}
