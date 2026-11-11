package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzagf extends zzagh {
    private final Object mLock = new Object();
    private final Context zzaeo;
    private SharedPreferences zzckn;
    private final zzwf<JSONObject, JSONObject> zzcko;

    public zzagf(Context context, zzwf<JSONObject, JSONObject> zzwfVar) {
        this.zzaeo = context.getApplicationContext();
        this.zzcko = zzwfVar;
    }

    final /* synthetic */ Void zzn(JSONObject jSONObject) {
        zznk.zza(this.zzaeo, 1, jSONObject);
        this.zzckn.edit().putLong("js_last_update", com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis()).apply();
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzagh
    public final zzanz<Void> zzop() {
        synchronized (this.mLock) {
            if (this.zzckn == null) {
                this.zzckn = this.zzaeo.getSharedPreferences("google_ads_flags_meta", 0);
            }
        }
        if (com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis() - this.zzckn.getLong("js_last_update", 0L) < ((Long) zzkb.zzik().zzd(zznk.zzbbl)).longValue()) {
            return zzano.zzi(null);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("js", zzang.zzsl().zzcw);
            jSONObject.put("mf", zzkb.zzik().zzd(zznk.zzbbm));
            jSONObject.put("cl", "191880412");
            jSONObject.put("rapid_rc", "dev");
            jSONObject.put("rapid_rollup", "HEAD");
            jSONObject.put("dynamite_version", 279);
            return zzano.zza(this.zzcko.zzf(jSONObject), new zzank(this) { // from class: com.google.android.gms.internal.ads.zzagg
                private final zzagf zzckp;

                {
                    this.zzckp = this;
                }

                @Override // com.google.android.gms.internal.ads.zzank
                public final Object apply(Object obj) {
                    return this.zzckp.zzn((JSONObject) obj);
                }
            }, zzaoe.zzcvz);
        } catch (JSONException e) {
            zzakb.zzb("Unable to populate SDK Core Constants parameters.", e);
            return zzano.zzi(null);
        }
    }
}
