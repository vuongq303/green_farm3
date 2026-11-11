package com.google.android.gms.ads.internal.gmsg;

import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaoj;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaa implements zzv<Object> {
    private final HashMap<String, zzaoj<JSONObject>> bzX = new HashMap<>();

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        String str = map.get("request_id");
        String str2 = map.get("fetched_ad");
        zzakb.zzck("Received ad from the cache.");
        zzaoj<JSONObject> zzaojVar = this.bzX.get(str);
        if (zzaojVar == null) {
            zzakb.e("Could not find the ad request for the corresponding ad response.");
            return;
        }
        try {
            zzaojVar.set(new JSONObject(str2));
        } catch (JSONException e) {
            zzakb.zzb("Failed constructing JSON object from value passed from javascript", e);
            zzaojVar.set(null);
        } finally {
            this.bzX.remove(str);
        }
    }

    public final Future<JSONObject> zzas(String str) {
        zzaoj<JSONObject> zzaojVar = new zzaoj<>();
        this.bzX.put(str, zzaojVar);
        return zzaojVar;
    }

    public final void zzat(String str) {
        zzaoj<JSONObject> zzaojVar = this.bzX.get(str);
        if (zzaojVar == null) {
            zzakb.e("Could not find the ad request for the corresponding ad response.");
            return;
        }
        if (!zzaojVar.isDone()) {
            zzaojVar.cancel(true);
        }
        this.bzX.remove(str);
    }
}
